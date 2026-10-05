package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExperienceType
import com.example.data.model.TimelineItem
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Amber400
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
fun ExperienceScreen(
    timeline: List<TimelineItem>,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf<ExperienceType?>(null) }

    val filteredTimeline = if (selectedFilter == null) {
        timeline
    } else {
        timeline.filter { it.type == selectedFilter }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("experience_screen_list")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(
                    text = "Career & Academic Path",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Verified roles, PVPSIT honors, hackathon championships",
                    style = MaterialTheme.typography.bodySmall,
                    color = Cyan400
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { selectedFilter = null },
                    label = { Text("All Milestones", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                        selectedLabelColor = Cyan400
                    )
                )

                ExperienceType.values().forEach { type ->
                    FilterChip(
                        selected = selectedFilter == type,
                        onClick = { selectedFilter = type },
                        label = { Text(type.label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400
                        )
                    )
                }
            }
        }

        // Timeline items
        items(filteredTimeline) { item ->
            TimelineCard(item = item)
        }

        // Endorsements Section
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Peer & Mentor Testimonials",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(PortfolioRepository.testimonials) { testimonial ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Slate900)
                    .border(1.dp, Slate800, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(testimonial.rating) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Amber400, modifier = Modifier.size(16.dp))
                            }
                        }
                        Surface(shape = RoundedCornerShape(4.dp), color = Slate800) {
                            Text(testimonial.relationship, fontSize = 10.sp, color = Indigo400, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "\"${testimonial.quote}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Slate300,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${testimonial.author} — ${testimonial.title}",
                        fontWeight = FontWeight.Bold,
                        color = Cyan400,
                        fontSize = 12.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TimelineCard(item: TimelineItem) {
    val icon = when (item.type) {
        ExperienceType.WORK -> Icons.Default.BusinessCenter
        ExperienceType.EDUCATION -> Icons.Default.School
        ExperienceType.HACKATHON -> Icons.Default.EmojiEvents
        ExperienceType.LEADERSHIP -> Icons.Default.Star
    }

    val accentColor = when (item.type) {
        ExperienceType.WORK -> Cyan400
        ExperienceType.EDUCATION -> Emerald400
        ExperienceType.HACKATHON -> Amber400
        ExperienceType.LEADERSHIP -> Indigo400
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Slate900)
            .border(1.dp, Slate800, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(accentColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(item.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        Text(item.organization, fontSize = 12.sp, color = accentColor, fontWeight = FontWeight.SemiBold)
                    }
                }

                Surface(shape = RoundedCornerShape(6.dp), color = Slate950) {
                    Text(item.period, fontSize = 11.sp, color = Slate400, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            item.highlights.forEach { hl ->
                Text("• $hl", fontSize = 12.sp, color = Slate300, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (item.techStack.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.techStack.forEach { tech ->
                        Surface(shape = RoundedCornerShape(4.dp), color = Slate800) {
                            Text(tech, fontSize = 10.sp, color = Slate300, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}
