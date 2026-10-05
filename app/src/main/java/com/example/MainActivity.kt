package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.repository.PortfolioRepository
import com.example.ui.components.CaseStudyBottomSheet
import com.example.ui.components.CommandPaletteModal
import com.example.ui.components.ContactBottomSheet
import com.example.ui.components.PortfolioTopBar
import com.example.ui.components.ResumeBottomSheet
import com.example.ui.components.TerminalBottomSheet
import com.example.ui.screens.DemosScreen
import com.example.ui.screens.ExperienceScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.SkillsScreen
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.viewmodel.PortfolioTab
import com.example.ui.viewmodel.PortfolioViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PortfolioApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioApp(
    viewModel: PortfolioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val caseStudySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val terminalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val contactSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val resumeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val paletteSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Handle back button smoothly
    BackHandler(
        enabled = uiState.activeCaseStudy != null ||
                uiState.showTerminalSheet ||
                uiState.showContactSheet ||
                uiState.showResumeSheet ||
                uiState.showCommandPalette ||
                uiState.currentTab != PortfolioTab.HOME
    ) {
        when {
            uiState.showCommandPalette -> viewModel.setCommandPaletteVisible(false)
            uiState.activeCaseStudy != null -> viewModel.closeCaseStudy()
            uiState.showTerminalSheet -> viewModel.setTerminalSheetVisible(false)
            uiState.showContactSheet -> viewModel.setContactSheetVisible(false)
            uiState.showResumeSheet -> viewModel.setResumeSheetVisible(false)
            uiState.currentTab != PortfolioTab.HOME -> viewModel.selectTab(PortfolioTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PortfolioTopBar(
                onOpenTerminal = { viewModel.setTerminalSheetVisible(true) },
                onOpenContact = { viewModel.setContactSheetVisible(true) },
                showBookmarkedOnly = uiState.showBookmarkedOnly,
                onToggleBookmarkFilter = {
                    viewModel.toggleBookmarkFilter()
                    if (uiState.currentTab != PortfolioTab.PROJECTS) {
                        viewModel.selectTab(PortfolioTab.PROJECTS)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.setCommandPaletteVisible(true) },
                containerColor = Cyan500,
                contentColor = Slate950,
                modifier = Modifier
                    .testTag("universal_palette_fab")
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Universal Command Palette (Cmd + K)")
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Slate950,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBarItem(
                    selected = uiState.currentTab == PortfolioTab.HOME,
                    onClick = { viewModel.selectTab(PortfolioTab.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate950,
                        indicatorColor = Cyan400,
                        selectedTextColor = Cyan400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate400
                    ),
                    modifier = Modifier.testTag("nav_tab_home")
                )

                NavigationBarItem(
                    selected = uiState.currentTab == PortfolioTab.PROJECTS,
                    onClick = { viewModel.selectTab(PortfolioTab.PROJECTS) },
                    icon = { Icon(Icons.Default.Code, contentDescription = "Projects") },
                    label = { Text("Projects", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate950,
                        indicatorColor = Cyan400,
                        selectedTextColor = Cyan400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate400
                    ),
                    modifier = Modifier.testTag("nav_tab_projects")
                )

                NavigationBarItem(
                    selected = uiState.currentTab == PortfolioTab.DEMOS,
                    onClick = { viewModel.selectTab(PortfolioTab.DEMOS) },
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Demos") },
                    label = { Text("Demos", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate950,
                        indicatorColor = Cyan400,
                        selectedTextColor = Cyan400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate400
                    ),
                    modifier = Modifier.testTag("nav_tab_demos")
                )

                NavigationBarItem(
                    selected = uiState.currentTab == PortfolioTab.SKILLS,
                    onClick = { viewModel.selectTab(PortfolioTab.SKILLS) },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "Skills") },
                    label = { Text("Skills", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate950,
                        indicatorColor = Cyan400,
                        selectedTextColor = Cyan400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate400
                    ),
                    modifier = Modifier.testTag("nav_tab_skills")
                )

                NavigationBarItem(
                    selected = uiState.currentTab == PortfolioTab.EXPERIENCE,
                    onClick = { viewModel.selectTab(PortfolioTab.EXPERIENCE) },
                    icon = { Icon(Icons.Default.Work, contentDescription = "Experience") },
                    label = { Text("Experience", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate950,
                        indicatorColor = Cyan400,
                        selectedTextColor = Cyan400,
                        unselectedIconColor = Slate400,
                        unselectedTextColor = Slate400
                    ),
                    modifier = Modifier.testTag("nav_tab_experience")
                )
            }
        },
        containerColor = Slate950
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Slate950)
        ) {
            when (uiState.currentTab) {
                PortfolioTab.HOME -> {
                    HomeScreen(
                        featuredProjects = PortfolioRepository.projects.filter { it.isFeatured },
                        bookmarkedProjectIds = uiState.bookmarkedProjectIds,
                        audienceMode = uiState.audienceMode,
                        onToggleAudienceMode = { viewModel.setAudienceMode(it) },
                        selectedPersona = uiState.selectedPersona,
                        onSelectPersona = { viewModel.selectPersona(it) },
                        onToggleBookmark = { viewModel.toggleBookmark(it) },
                        onOpenCaseStudy = { viewModel.openCaseStudy(it) },
                        onLaunchDemo = { viewModel.launchDemo(it) },
                        onNavigateToProjects = { viewModel.selectTab(PortfolioTab.PROJECTS) },
                        onNavigateToDemos = { viewModel.selectTab(PortfolioTab.DEMOS) },
                        onNavigateToSkills = { viewModel.selectTab(PortfolioTab.SKILLS) },
                        onOpenResume = { viewModel.setResumeSheetVisible(true) },
                        onOpenContact = { viewModel.setContactSheetVisible(true) },
                        onOpenTerminal = { viewModel.setTerminalSheetVisible(true) }
                    )
                }

                PortfolioTab.PROJECTS -> {
                    ProjectsScreen(
                        projects = PortfolioRepository.projects,
                        searchQuery = uiState.searchQuery,
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        selectedCategory = uiState.selectedCategory,
                        onSelectCategory = { viewModel.selectCategory(it) },
                        bookmarkedProjectIds = uiState.bookmarkedProjectIds,
                        showBookmarkedOnly = uiState.showBookmarkedOnly,
                        onToggleBookmark = { viewModel.toggleBookmark(it) },
                        onOpenCaseStudy = { viewModel.openCaseStudy(it) },
                        onLaunchDemo = { viewModel.launchDemo(it) }
                    )
                }

                PortfolioTab.DEMOS -> {
                    DemosScreen(
                        initialDemo = uiState.activeDemoId
                    )
                }

                PortfolioTab.SKILLS -> {
                    SkillsScreen(
                        skills = PortfolioRepository.skills
                    )
                }

                PortfolioTab.EXPERIENCE -> {
                    ExperienceScreen(
                        timeline = PortfolioRepository.timeline
                    )
                }
            }
        }
    }

    // Modal Bottom Sheets
    if (uiState.activeCaseStudy != null) {
        CaseStudyBottomSheet(
            project = uiState.activeCaseStudy!!,
            onDismiss = { viewModel.closeCaseStudy() },
            onLaunchDemo = { viewModel.launchDemo(it) },
            sheetState = caseStudySheetState
        )
    }

    if (uiState.showTerminalSheet) {
        TerminalBottomSheet(
            onDismiss = { viewModel.setTerminalSheetVisible(false) },
            sheetState = terminalSheetState
        )
    }

    if (uiState.showContactSheet) {
        ContactBottomSheet(
            onDismiss = { viewModel.setContactSheetVisible(false) },
            sheetState = contactSheetState
        )
    }

    if (uiState.showResumeSheet) {
        ResumeBottomSheet(
            onDismiss = { viewModel.setResumeSheetVisible(false) },
            sheetState = resumeSheetState
        )
    }

    if (uiState.showCommandPalette) {
        CommandPaletteModal(
            onDismiss = { viewModel.setCommandPaletteVisible(false) },
            sheetState = paletteSheetState,
            onNavigateToProject = {
                val proj = PortfolioRepository.projects.find { p -> p.id == it }
                if (proj != null) viewModel.openCaseStudy(proj)
            },
            onNavigateToDemo = { viewModel.launchDemo(it) },
            onToggleAudienceMode = {
                val nextMode = if (uiState.audienceMode == com.example.ui.screens.AudienceMode.RECRUITER) {
                    com.example.ui.screens.AudienceMode.DEVELOPER
                } else {
                    com.example.ui.screens.AudienceMode.RECRUITER
                }
                viewModel.setAudienceMode(nextMode)
            },
            onOpenResume = { viewModel.setResumeSheetVisible(true) },
            onOpenTerminal = { viewModel.setTerminalSheetVisible(true) },
            onOpenContact = { viewModel.setContactSheetVisible(true) }
        )
    }
}
