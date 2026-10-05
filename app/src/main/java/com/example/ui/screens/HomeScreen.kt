package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Project
import com.example.data.repository.DeveloperPersona
import com.example.data.repository.PortfolioRepository
import com.example.ui.components.CloudflareTokenizationDemo
import com.example.ui.components.DeveloperEngineeringLab
import com.example.ui.components.FactForgeInteractiveDemo
import com.example.ui.components.ProjectCard
import com.example.ui.components.RadarAxis
import com.example.ui.components.SkillRadarChart
import com.example.ui.theme.Amber400
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Indigo500
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

enum class AudienceMode(val label: String, val subtitle: String) {
    RECRUITER("View as Recruiter", "Business impact, verified sandboxes & fast hiring"),
    DEVELOPER("View as Developer", "API schemas, system topology & trade-off matrix")
}

@Composable
fun HomeScreen(
    featuredProjects: List<Project>,
    bookmarkedProjectIds: Set<String>,
    audienceMode: AudienceMode,
    onToggleAudienceMode: (AudienceMode) -> Unit,
    selectedPersona: DeveloperPersona,
    onSelectPersona: (DeveloperPersona) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onOpenCaseStudy: (Project) -> Unit,
    onLaunchDemo: (String) -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToDemos: () -> Unit,
    onNavigateToSkills: () -> Unit,
    onOpenResume: () -> Unit,
    onOpenContact: () -> Unit,
    onOpenTerminal: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_content")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. THE SWITCH (Prominent Dual Audience Toggle)
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                modifier = Modifier.fillMaxWidth().testTag("audience_switcher_surface"),
                shape = RoundedCornerShape(16.dp),
                color = Slate900,
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    AudienceMode.values().forEach { mode ->
                        val isSelected = audienceMode == mode
                        val bgBrush = if (isSelected) {
                            Brush.horizontalGradient(listOf(Cyan500, Indigo500))
                        } else {
                            Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgBrush)
                                .clickable { onToggleAudienceMode(mode) }
                                .padding(vertical = 11.dp, horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (mode == AudienceMode.RECRUITER) Icons.Default.Work else Icons.Default.Code,
                                    contentDescription = null,
                                    tint = if (isSelected) Slate950 else Slate400,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = mode.label,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                    color = if (isSelected) Slate950 else Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. THE HERO SECTION (First Impression)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.verticalGradient(listOf(Slate900, Slate950)))
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Cyan400.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "FULL-STACK AI & CLOUD ENGINEER",
                                    color = Cyan400,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Sathvik | AI & Cloud Engineer",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "PVPSIT • Systems, Mobile & Cloud Security",
                                style = MaterialTheme.typography.bodySmall,
                                color = Indigo400,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // Avatar badge
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(Cyan400, Indigo500))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SP",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = Slate950
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Context Line (Mandatory Specification)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slate950)
                            .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Cyan400, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(selectedPersona.title, fontSize = 11.sp, color = Cyan400, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "\"${selectedPersona.headline}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Persona Selection Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PortfolioRepository.personas.forEach { persona ->
                            FilterChip(
                                selected = selectedPersona.id == persona.id,
                                onClick = { onSelectPersona(persona) },
                                label = { Text(persona.title, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                                    selectedLabelColor = Cyan400,
                                    containerColor = Slate800,
                                    labelColor = Slate300
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Hero Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onOpenContact,
                            modifier = Modifier.weight(1f).testTag("hero_hire_me_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Mail, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Let's Talk Business", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onOpenResume,
                            modifier = Modifier.weight(1f).testTag("hero_view_resume_btn"),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400, Cyan400))),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp), tint = Indigo400)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Resume (PDF)")
                        }
                    }
                }
            }
        }

        // Quick Impact Telemetry
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Slate900)
                    .border(1.dp, Slate800, RoundedCornerShape(16.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PortfolioRepository.quickStats.forEach { stat ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stat.value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Cyan400)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(stat.label, style = MaterialTheme.typography.labelSmall, color = Slate400, fontSize = 11.sp)
                    }
                }
            }
        }

        // 3. THE BODY (Dynamic Content based on Toggle)
        if (audienceMode == AudienceMode.DEVELOPER) {
            // DEVELOPER VIEW: System Design, Architecture Diagrams, CI/CD, API Endpoints
            item {
                DeveloperEngineeringLab()
            }
        } else {
            // RECRUITER VIEW: Business Impact, FactForge AI Sandbox & Cloudflare Edge Tokenizer
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    FactForgeInteractiveDemo()
                    CloudflareTokenizationDemo()
                }
            }
        }

        // Featured Best Projects
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Featured Best Projects",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "High-impact production systems & algorithmic engines",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                }

                Text(
                    text = "See All (${PortfolioRepository.projects.size})",
                    color = Cyan400,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable { onNavigateToProjects() }
                        .padding(4.dp)
                )
            }
        }

        items(featuredProjects) { project ->
            ProjectCard(
                project = project,
                isBookmarked = bookmarkedProjectIds.contains(project.id),
                onToggleBookmark = { onToggleBookmark(project.id) },
                onOpenCaseStudy = { onOpenCaseStudy(project) },
                onLaunchDemo = { onLaunchDemo(project.liveDemoId ?: "factforge") },
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

        // Competency Radar
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToSkills() }
                    .testTag("home_skills_radar_card"),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(20.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Slate700, Slate800)))
            ) {
                Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Technical Competency Radar", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                            Text("Full-spectrum engineering mastery", color = Slate400, fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Cyan400)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val sampleAxes = listOf(
                        RadarAxis("Compose & UI", 0.95f),
                        RadarAxis("Distributed gRPC", 0.88f),
                        RadarAxis("Algorithms & DP", 0.93f),
                        RadarAxis("Coroutines & Flow", 0.94f),
                        RadarAxis("CI/CD & DevOps", 0.90f),
                        RadarAxis("Cloudflare Edge", 0.91f)
                    )
                    SkillRadarChart(axes = sampleAxes)
                }
            }
        }

        // 4. THE END FORMAT (Footer & Dual-Audience Call to Action)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.verticalGradient(listOf(Slate900, Slate950)))
                    .border(1.dp, Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "READY TO DEPLOY?",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        // Easter Egg prompt button (>_)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Slate800,
                            modifier = Modifier.clickable { onOpenTerminal() }.testTag("footer_terminal_btn")
                        ) {
                            Text(
                                text = " >_ CLI ",
                                fontFamily = FontFamily.Monospace,
                                color = Cyan400,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Two Clear Actionable Columns
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Left Column: Professional & Business (Recruiters)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Slate950)
                                .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text("[ Let's Talk Business ]", fontWeight = FontWeight.Bold, color = Cyan400, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text("• Email: ${PortfolioRepository.developerEmail}", fontSize = 11.sp, color = Slate300)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• LinkedIn: /in/sathvik-pvpsit", fontSize = 11.sp, color = Slate300)
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = onOpenResume,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("📄 Download CV", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Right Column: Technical & Code (Developers)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Slate950)
                                .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text("[ Dive Into the Code ]", fontWeight = FontWeight.Bold, color = Indigo400, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text("• GitHub: /sathvik", fontSize = 11.sp, color = Slate300)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• LeetCode: /sathvik-pvpsit", fontSize = 11.sp, color = Slate300)
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = onOpenTerminal,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400, Cyan400))),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("💻 Open Terminal", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Slate800)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Engineered with Jetpack Compose & Cloudflare Services. Press Cmd+K to navigate.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 10.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = " >_ ",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = Cyan400,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable { onOpenTerminal() }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
