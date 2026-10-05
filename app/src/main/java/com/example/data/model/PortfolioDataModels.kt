package com.example.data.model

enum class SkillCategory(val displayName: String) {
    MOBILE("Android & Mobile"),
    BACKEND("Cloud & Distributed"),
    CS_CORE("Algorithms & Systems"),
    DEVOPS("Tools & DevOps")
}

data class SkillItem(
    val id: String,
    val name: String,
    val category: SkillCategory,
    val proficiencyPercent: Int, // 0 - 100
    val experienceYears: String,
    val description: String,
    val libraries: List<String>
)

enum class ExperienceType(val label: String) {
    WORK("Experience"),
    EDUCATION("Education"),
    HACKATHON("Awards & Hackathons"),
    LEADERSHIP("Leadership & Open Source")
}

data class TimelineItem(
    val id: String,
    val title: String,
    val organization: String,
    val period: String,
    val location: String,
    val type: ExperienceType,
    val highlights: List<String>,
    val techStack: List<String> = emptyList(),
    val isCurrent: Boolean = false
)

data class Testimonial(
    val id: String,
    val author: String,
    val title: String,
    val relationship: String,
    val quote: String,
    val rating: Int = 5
)

data class ContactInquiry(
    val fullName: String,
    val email: String,
    val subject: String,
    val projectType: String,
    val message: String
)

data class PortfolioAchievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false
)
