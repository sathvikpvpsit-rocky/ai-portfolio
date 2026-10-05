package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Architecture
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Project
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

@Composable
fun ProjectCard(
    project: Project,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onOpenCaseStudy: () -> Unit,
    onLaunchDemo: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("project_card_${project.id}"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = if (project.isFeatured) {
                Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.6f), Indigo400.copy(alpha = 0.6f)))
            } else {
                Brush.horizontalGradient(listOf(Slate700, Slate800))
            }
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Top row: Category tag & Featured Star + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Cyan500.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = project.category.displayName,
                            color = Cyan400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    if (project.isFeatured) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Amber400.copy(alpha = 0.15f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Amber400, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("FEATURED", color = Amber400, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(36.dp).testTag("share_btn_${project.id}")
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Slate400, modifier = Modifier.size(18.dp))
                    }

                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.size(36.dp).testTag("bookmark_btn_${project.id}")
                    ) {
                        Icon(
                            if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) Cyan400 else Slate400,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Project Title & Subtitle
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = project.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Slate300
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Metric telemetry pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                project.metrics.forEach { metric ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = metric.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = metric.value,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (metric.trendPositive) Cyan400 else Amber400
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tech Stack Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Slate800)
                            .border(0.5.dp, Slate700, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(tag, fontSize = 11.sp, color = Slate300, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Actions row: Launch Demo + Case Study
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (project.liveDemoId != null) {
                    Button(
                        onClick = onLaunchDemo,
                        modifier = Modifier.weight(1f).testTag("launch_demo_${project.id}"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Cyan500,
                            contentColor = Slate950
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Live Demo", fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = onOpenCaseStudy,
                    modifier = Modifier.weight(1f).testTag("case_study_${project.id}"),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400, Cyan400))),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Outlined.Architecture, contentDescription = null, modifier = Modifier.size(18.dp), tint = Indigo400)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Architecture")
                }
            }
        }
    }
}
