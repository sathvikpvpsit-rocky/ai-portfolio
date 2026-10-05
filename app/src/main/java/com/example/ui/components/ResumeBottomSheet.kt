package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onViewed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun shareResumeText() {
        val resumeSummary = """
Sathvik - Mobile & Systems Software Engineer
Email: ${PortfolioRepository.developerEmail}
GitHub: ${PortfolioRepository.developerGithub}
LinkedIn: ${PortfolioRepository.developerLinkedin}
Alma Mater: ${PortfolioRepository.developerCollege}

Summary:
${PortfolioRepository.developerTagline}

Top Skills:
• Android & Jetpack Compose (Kotlin Coroutines, Flow, Room, MVI)
• Distributed Systems & gRPC (Kafka, Redis, Docker, Microservices)
• Core CS & Algorithms (A* Search, Dijkstra, Spatial Partitioning)

Featured Projects:
1. NexusFlow Distributed Mesh (120K req/s, 3.8ms P99 latency)
2. AlgoMatrix Engine 3D (Zero-allocation 120 FPS Canvas visualizer)
3. PulseFit Pro Architecture (Offline-first biometrics with Room & Coroutines)
        """.trimIndent()

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Sathvik - Software Engineer Resume")
            putExtra(Intent.EXTRA_TEXT, resumeSummary)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Sathvik's Resume"))
        onViewed()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Slate900,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(width = 44.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Slate700)
            )
        },
        modifier = modifier.testTag("resume_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Curriculum Vitae",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Verified Engineering Credentials",
                        style = MaterialTheme.typography.bodySmall,
                        color = Cyan400
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Slate950)
                    .border(1.dp, CardDefaults.outlinedCardBorder().brush, RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(PortfolioRepository.developerName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(PortfolioRepository.developerRole, style = MaterialTheme.typography.bodyMedium, color = Cyan400)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(PortfolioRepository.developerEmail, style = MaterialTheme.typography.bodySmall, color = Slate400)
                    Text(PortfolioRepository.developerCollege, style = MaterialTheme.typography.bodySmall, color = Slate400)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Experience Section
            Text("Work Experience", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))

            PortfolioRepository.timeline.filter { it.type == com.example.data.model.ExperienceType.WORK }.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Slate800)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.title, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 13.sp)
                        Text(item.period, fontSize = 11.sp, color = Cyan400)
                    }
                    Text(item.organization, fontSize = 12.sp, color = Indigo400)
                    Spacer(modifier = Modifier.height(6.dp))
                    item.highlights.forEach { hl ->
                        Text("• $hl", fontSize = 11.sp, color = Slate300, lineHeight = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Education Section
            Text("Education & Honors", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))

            PortfolioRepository.timeline.filter { it.type != com.example.data.model.ExperienceType.WORK }.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Slate800)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.title, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 13.sp)
                        Text(item.period, fontSize = 11.sp, color = Emerald400)
                    }
                    Text(item.organization, fontSize = 12.sp, color = Slate400)
                    Spacer(modifier = Modifier.height(6.dp))
                    item.highlights.forEach { hl ->
                        Text("• $hl", fontSize = 11.sp, color = Slate300, lineHeight = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            Button(
                onClick = { shareResumeText() },
                modifier = Modifier.fillMaxWidth().testTag("share_resume_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share / Export Full Profile", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
