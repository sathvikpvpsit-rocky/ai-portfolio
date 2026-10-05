package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Project
import com.example.data.model.ProjectCategory
import com.example.ui.components.ProjectCard
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun ProjectsScreen(
    projects: List<Project>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: ProjectCategory,
    onSelectCategory: (ProjectCategory) -> Unit,
    bookmarkedProjectIds: Set<String>,
    showBookmarkedOnly: Boolean,
    onToggleBookmark: (String) -> Unit,
    onOpenCaseStudy: (Project) -> Unit,
    onLaunchDemo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val filteredProjects = projects.filter { project ->
        val matchesCategory = selectedCategory == ProjectCategory.ALL || project.category == selectedCategory
        val matchesBookmark = !showBookmarkedOnly || bookmarkedProjectIds.contains(project.id)
        val matchesSearch = searchQuery.isBlank() ||
                project.title.contains(searchQuery, ignoreCase = true) ||
                project.subtitle.contains(searchQuery, ignoreCase = true) ||
                project.tags.any { it.contains(searchQuery, ignoreCase = true) }

        matchesCategory && matchesBookmark && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("projects_screen_list")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth().testTag("projects_search_bar"),
                placeholder = { Text("Search by tech, name, or keywords...", color = Slate400, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Cyan400) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Slate400)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Cyan400,
                    unfocusedBorderColor = Slate800,
                    focusedContainerColor = Slate900,
                    unfocusedContainerColor = Slate900
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProjectCategory.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { onSelectCategory(cat) },
                        label = { Text(cat.displayName, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400,
                            containerColor = Slate900,
                            labelColor = Slate400
                        )
                    )
                }
            }

            if (showBookmarkedOnly) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Cyan400.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Filtered by Bookmarks (${filteredProjects.size} saved)",
                        color = Cyan400,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        if (filteredProjects.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No Projects Found", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Try resetting your filters or search keywords", style = MaterialTheme.typography.bodySmall, color = Slate400)
                    }
                }
            }
        } else {
            items(filteredProjects) { project ->
                ProjectCard(
                    project = project,
                    isBookmarked = bookmarkedProjectIds.contains(project.id),
                    onToggleBookmark = { onToggleBookmark(project.id) },
                    onOpenCaseStudy = { onOpenCaseStudy(project) },
                    onLaunchDemo = { onLaunchDemo(project.liveDemoId ?: "algomatrix") },
                    onShare = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, project.title)
                            putExtra(Intent.EXTRA_TEXT, "${project.title}\n${project.subtitle}\nGitHub: ${project.githubUrl}")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Project"))
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
