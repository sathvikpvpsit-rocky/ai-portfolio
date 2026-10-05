package com.example.ui.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Architecture
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseStudyBottomSheet(
    project: Project,
    onDismiss: () -> Unit,
    onLaunchDemo: (String) -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedCode by remember { mutableStateOf(false) }

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
        modifier = modifier.testTag("case_study_bottom_sheet")
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
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Cyan500.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = project.category.displayName,
                        color = Cyan400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = project.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Slate300
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Key Metrics Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                project.metrics.forEach { metric ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(metric.label, style = MaterialTheme.typography.labelSmall, color = Slate400)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(metric.value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Cyan400)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Section 1: Problem Statement
            CaseStudySection(
                icon = { Icon(Icons.Outlined.Warning, contentDescription = null, tint = Amber400) },
                title = "The Engineering Challenge",
                body = project.problemStatement
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section 2: Architecture & Design
            CaseStudySection(
                icon = { Icon(Icons.Outlined.Architecture, contentDescription = null, tint = Indigo400) },
                title = "System Architecture",
                body = project.architecture
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section 3: Solution & Implementation
            CaseStudySection(
                icon = { Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = Cyan400) },
                title = "Technical Solution",
                body = project.solution
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Section 4: Measurable Results
            CaseStudySection(
                icon = { Icon(Icons.Outlined.Analytics, contentDescription = null, tint = Emerald400) },
                title = "Quantitative Impact",
                body = project.results
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Code Preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Core Implementation Code",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(project.codeSnippet))
                        copiedCode = true
                    }
                ) {
                    Icon(
                        if (copiedCode) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = "Copy code",
                        tint = if (copiedCode) Emerald400 else Slate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = project.codeSnippet,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFF7DD3FC)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bottom Actions
            if (project.liveDemoId != null) {
                Button(
                    onClick = {
                        onDismiss()
                        onLaunchDemo(project.liveDemoId)
                    },
                    modifier = Modifier.fillMaxWidth().testTag("sheet_live_demo_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Try Interactive Live Demo", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            OutlinedButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(project.githubUrl))
                },
                modifier = Modifier.fillMaxWidth().testTag("copy_repo_link_btn"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400, Cyan400))),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(18.dp), tint = Indigo400)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Copy GitHub Repo Link")
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun CaseStudySection(
    icon: @Composable () -> Unit,
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Slate800.copy(alpha = 0.6f))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = Slate300,
            lineHeight = 20.sp
        )
    }
}
