package panelglossary.ui

import kotlinx.coroutines.flow.StateFlow

interface GlossaryComponent {
    val uiState: StateFlow<GlossaryUiState>
}
