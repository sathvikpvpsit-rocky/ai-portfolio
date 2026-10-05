package com.example.data.model

enum class ProjectCategory(val displayName: String) {
    ALL("All Projects"),
    MOBILE("Android & Mobile"),
    CLOUD("Cloud & Systems"),
    AI_ML("AI & High-Performance"),
    FULL_STACK("Full Stack & Web")
}

data class ProjectMetric(
    val label: String,
    val value: String,
    val trendPositive: Boolean = true
)

data class Project(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: ProjectCategory,
    val tags: List<String>,
    val metrics: List<ProjectMetric>,
    val summary: String,
    val problemStatement: String,
    val architecture: String,
    val solution: String,
    val results: String,
    val codeSnippet: String,
    val githubUrl: String = "https://github.com/sathvik",
    val liveDemoId: String? = null,
    val isFeatured: Boolean = false,
    val starsCount: Int = 142,
    val forksCount: Int = 38
)
