package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SkillCategory
import com.example.data.model.SkillItem
import com.example.data.repository.PortfolioRepository
import com.example.ui.components.RadarAxis
import com.example.ui.components.SkillRadarChart
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@Composable
fun SkillsScreen(
    skills: List<SkillItem>,
    modifier: Modifier = Modifier
) {
    val radarAxes = listOf(
        RadarAxis("Compose & UI", 0.95f),
        RadarAxis("Distributed gRPC", 0.88f),
        RadarAxis("Algorithms & DP", 0.93f),
        RadarAxis("Coroutines & Flow", 0.94f),
        RadarAxis("CI/CD & DevOps", 0.90f),
        RadarAxis("High-Scale Arch", 0.89f)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("skills_screen_list")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = "Skills & Technical Matrix",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Engineering capabilities verified across production apps",
                    style = MaterialTheme.typography.bodySmall,
                    color = Cyan400
                )
            }
        }

        // Radar chart card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Slate900),
                shape = RoundedCornerShape(20.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Full-Spectrum Competency Radar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Touch nodes to inspect proficiency weights",
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate400
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    SkillRadarChart(axes = radarAxes)
                }
            }
        }

        // Skills Grouped by Category
        SkillCategory.values().forEach { category ->
            val categorySkills = skills.filter { it.category == category }
            if (categorySkills.isNotEmpty()) {
                item {
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                items(categorySkills) { skill ->
                    SkillItemCard(skill = skill)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SkillItemCard(skill: SkillItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Slate900)
            .border(1.dp, Slate800, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(skill.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                Surface(shape = RoundedCornerShape(6.dp), color = Slate800) {
                    Text(
                        text = "${skill.proficiencyPercent}% (${skill.experienceYears})",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Cyan400,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { skill.proficiencyPercent / 100f },
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                color = Cyan400,
                trackColor = Slate800
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = skill.description,
                style = MaterialTheme.typography.bodySmall,
                color = Slate300,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                skill.libraries.take(4).forEach { lib ->
                    Surface(shape = RoundedCornerShape(4.dp), color = Slate950) {
                        Text(lib, fontSize = 10.sp, color = Indigo400, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                }
            }
        }
    }
}
