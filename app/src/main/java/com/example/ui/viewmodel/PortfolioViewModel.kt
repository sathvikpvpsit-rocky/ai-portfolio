package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.Project
import com.example.data.model.ProjectCategory
import com.example.data.repository.DeveloperPersona
import com.example.data.repository.PortfolioRepository
import com.example.ui.screens.AudienceMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class PortfolioTab(val label: String) {
    HOME("Home"),
    PROJECTS("Projects"),
    DEMOS("Demos"),
    SKILLS("Skills"),
    EXPERIENCE("Experience")
}

data class PortfolioUiState(
    val audienceMode: AudienceMode = AudienceMode.RECRUITER,
    val selectedPersona: DeveloperPersona = PortfolioRepository.personas.first(),
    val currentTab: PortfolioTab = PortfolioTab.HOME,
    val searchQuery: String = "",
    val selectedCategory: ProjectCategory = ProjectCategory.ALL,
    val bookmarkedProjectIds: Set<String> = setOf("factforge", "nexusflow"),
    val showBookmarkedOnly: Boolean = false,
    val activeCaseStudy: Project? = null,
    val showTerminalSheet: Boolean = false,
    val showContactSheet: Boolean = false,
    val showResumeSheet: Boolean = false,
    val showCommandPalette: Boolean = false,
    val activeDemoId: String? = null
)

class PortfolioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()

    fun setAudienceMode(mode: AudienceMode) {
        _uiState.update { it.copy(audienceMode = mode) }
    }

    fun selectPersona(persona: DeveloperPersona) {
        _uiState.update { it.copy(selectedPersona = persona) }
    }

    fun selectTab(tab: PortfolioTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCategory(category: ProjectCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun toggleBookmark(projectId: String) {
        _uiState.update { state ->
            val updated = state.bookmarkedProjectIds.toMutableSet()
            if (updated.contains(projectId)) {
                updated.remove(projectId)
            } else {
                updated.add(projectId)
            }
            state.copy(bookmarkedProjectIds = updated)
        }
    }

    fun toggleBookmarkFilter() {
        _uiState.update { it.copy(showBookmarkedOnly = !it.showBookmarkedOnly) }
    }

    fun openCaseStudy(project: Project) {
        _uiState.update { it.copy(activeCaseStudy = project) }
    }

    fun closeCaseStudy() {
        _uiState.update { it.copy(activeCaseStudy = null) }
    }

    fun launchDemo(demoId: String) {
        _uiState.update {
            it.copy(
                activeDemoId = demoId,
                currentTab = PortfolioTab.DEMOS,
                activeCaseStudy = null
            )
        }
    }

    fun setTerminalSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showTerminalSheet = visible) }
    }

    fun setContactSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showContactSheet = visible) }
    }

    fun setResumeSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showResumeSheet = visible) }
    }

    fun setCommandPaletteVisible(visible: Boolean) {
        _uiState.update { it.copy(showCommandPalette = visible) }
    }
}
