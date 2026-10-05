package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Amber400
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Rose400
import com.example.ui.theme.Rose500
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class VerificationSample(
    val label: String,
    val claim: String,
    val expectedStatus: String,
    val confidence: Float,
    val latencyMs: Int,
    val citation: String
)

@Composable
fun FactForgeInteractiveDemo(
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val samples = listOf(
        VerificationSample(
            "Alumni & Research",
            "Sathvik published research on high-throughput algorithmic scheduling at PVPSIT.",
            "VERIFIED TRUE",
            0.992f,
            42,
            "PVPSIT Department of CSE Archives & Tech Symposium 2024"
        ),
        VerificationSample(
            "Distributed Systems",
            "Sathvik's NexusFlow distributed mesh supports 120k req/s with sub-4ms P99 latency.",
            "VERIFIED TRUE",
            0.987f,
            38,
            "NexusFlow Performance Benchmark Suite (10Gbps interfaces)"
        ),
        VerificationSample(
            "Hallucination Test",
            "Sathvik has only developed web applications and never written an Android app.",
            "CONTRADICTION DETECTED",
            0.998f,
            31,
            "Refuted: Author has 4+ years native Android & Jetpack Compose experience"
        )
    )

    var currentClaim by remember { mutableStateOf(samples.first().claim) }
    var isVerifying by remember { mutableStateOf(false) }
    var verificationResult by remember { mutableStateOf<VerificationSample?>(samples.first()) }

    fun runVerification(sample: VerificationSample) {
        onSimulateAction()
        isVerifying = true
        verificationResult = null
        currentClaim = sample.claim
        coroutineScope.launch {
            delay(500)
            verificationResult = sample
            isVerifying = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("factforge_demo_card"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.6f), Indigo400.copy(alpha = 0.6f))))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.linearGradient(listOf(Cyan400, Indigo400))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FactCheck, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "FactForge AI Sandbox",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Dual-Tier Hallucination & Fact Checker",
                            style = MaterialTheme.typography.bodySmall,
                            color = Cyan400
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Emerald500.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "NO API KEY NEEDED",
                        color = Emerald400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sample Claim Filter Chips
            Text("Select an assertion to verify in real-time:", style = MaterialTheme.typography.labelSmall, color = Slate400)
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                samples.forEach { sample ->
                    FilterChip(
                        selected = currentClaim == sample.claim,
                        onClick = { runVerification(sample) },
                        label = { Text(sample.label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Statement Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "\"$currentClaim\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Verification Result Card
            if (isVerifying) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Cyan400, strokeWidth = 3.dp, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Traversing Knowledge Graph Triples...", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    }
                }
            } else if (verificationResult != null) {
                val res = verificationResult!!
                val isTrue = res.expectedStatus.startsWith("VERIFIED")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isTrue) Emerald500.copy(alpha = 0.12f) else Rose500.copy(alpha = 0.12f))
                        .border(1.dp, if (isTrue) Emerald500.copy(alpha = 0.4f) else Rose500.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (isTrue) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = if (isTrue) Emerald400 else Rose400,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = res.expectedStatus,
                                    fontWeight = FontWeight.Black,
                                    color = if (isTrue) Emerald400 else Rose400,
                                    fontSize = 13.sp
                                )
                            }

                            Text(
                                text = "Latency: ${res.latencyMs}ms",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = Slate400
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Confidence bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Consensus Confidence Score:", fontSize = 11.sp, color = Slate400)
                            Text("${(res.confidence * 100).toInt()}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isTrue) Emerald400 else Rose400)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { res.confidence },
                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                            color = if (isTrue) Emerald400 else Rose400,
                            trackColor = Slate800
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Lineage Citation: ${res.citation}",
                            fontSize = 11.sp,
                            color = Slate300,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action: Re-verify
            Button(
                onClick = { runVerification(samples.first { it.claim == currentClaim }) },
                modifier = Modifier.fillMaxWidth().testTag("factforge_verify_action_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Re-Verify Triples Graph", fontWeight = FontWeight.Bold)
            }
        }
    }
}
