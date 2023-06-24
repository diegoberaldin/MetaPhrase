package com.github.diegoberaldin.metaphrase.core.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.awt.Window
import java.io.FilenameFilter

/**
 * Custom open file dialog.
 *
 * @param title Dialog title
 * @param nameFilter Name filter
 * @param parent Parent
 * @param onCloseRequest On close callback
 */
@Composable
fun CustomOpenFileDialog(
    title: String = "",
    nameFilter: (String) -> Boolean = { true },
    parent: Frame? = null,
    onCloseRequest: (result: String?) -> Unit,
) {
    fun createDialog(): Window {
        return object : FileDialog(parent, title, LOAD) {

            override fun getFilenameFilter(): FilenameFilter {
                return FilenameFilter { _, name ->
                    nameFilter(name)
                }
            }

            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    val result = file?.let { directory + file }
                    onCloseRequest(result)
                }
            }
        }
    }

    AwtWindow(
        create = {
            createDialog()
        },
        dispose = {
            it.dispose()
        },
    )
}
