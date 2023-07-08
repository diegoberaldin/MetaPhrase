package com.github.diegoberaldin.metaphrase.core.common.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

/**
 * Custom tab bar.
 *
 * @param modifier Modifier
 * @param tabs Tabs titles
 * @param current Current tab
 * @param onTabSelected On tab selected callback
 * @param rightIcon Right icon (e.g. close button)
 * @param onRightIconClicked On right icon clicked callack
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTabBar(
    modifier: Modifier = Modifier,
    tabs: List<String> = emptyList(),
    current: Int? = null,
    onTabSelected: ((Int) -> Unit)? = null,
    rightIcon: ImageVector? = null,
    onRightIconClicked: ((Int) -> Unit)? = null,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dp.Hairline),
    ) {
        itemsIndexed(tabs) { idx, tab ->
            Tab(
                label = tab,
                selected = idx == current,
                modifier = Modifier.onClick {
                    onTabSelected?.invoke(idx)
                },
                rightIcon = rightIcon,
                onRightIconClicked = {
                    onRightIconClicked?.invoke(idx)
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Tab(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    rightIcon: ImageVector?,
    onRightIconClicked: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.background(
            color = if (selected) Color.White.copy(alpha = 0.1f) else Color.Transparent,
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        ).padding(horizontal = Spacing.s, vertical = Spacing.s),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
            color = MaterialTheme.colorScheme.onBackground,
        )
        if (rightIcon != null) {
            Spacer(Modifier.width(Spacing.m))
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(14.dp)
                    .onClick {
                        onRightIconClicked?.invoke()
                    },
                imageVector = rightIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
