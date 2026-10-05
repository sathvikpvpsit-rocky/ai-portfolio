package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.Architecture
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Amber400
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Indigo500
import com.example.ui.theme.Rose400
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DeveloperEngineeringLab(
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    var selectedApiIndex by remember { mutableIntStateOf(0) }
    var isExecutingApi by remember { mutableStateOf(false) }
    var apiResponseOutput by remember { mutableStateOf<String?>(null) }
    var copiedCurl by remember { mutableStateOf(false) }

    val currentEndpoint = PortfolioRepository.apiEndpoints[selectedApiIndex]

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("developer_engineering_lab"),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Section 1: CI/CD & Production Telemetry Live Badges
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(20.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Emerald400.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Emerald400, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("CI/CD Pipeline Telemetry", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                            Text("GitHub Actions • Automated Verification", color = Emerald400, fontSize = 11.sp)
                        }
                    }

                    Surface(shape = RoundedCornerShape(6.dp), color = Emerald500.copy(alpha = 0.2f)) {
                        Text("ALL CHECKS PASSING", color = Emerald400, fontSize = 9.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Telemetry Badges Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TelemetryBadgeItem(title = "Unit & Robolectric", value = "94% Coverage", modifier = Modifier.weight(1f))
                    TelemetryBadgeItem(title = "P99 Response", value = "3.8ms Wire", modifier = Modifier.weight(1f))
                    TelemetryBadgeItem(title = "GC Stutter", value = "0 Frame Jitter", modifier = Modifier.weight(1f))
                }
            }
        }

        // Section 2: Interactive Architecture Diagram Topology
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(20.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400.copy(alpha = 0.5f), Cyan400.copy(alpha = 0.5f))))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Architecture, contentDescription = null, tint = Indigo400, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("System Architecture Topology", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                        Text("High-concurrency dataflow & verification pipeline", color = Indigo400, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Topology pipeline nodes
                PortfolioRepository.architectureNodes.forEachIndexed { index, node ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (node.isHighlighted) Slate950 else Slate800)
                            .border(1.dp, if (node.isHighlighted) Cyan400.copy(alpha = 0.6f) else Slate700, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(node.name, fontWeight = FontWeight.Bold, color = if (node.isHighlighted) Cyan400 else Color.White, fontSize = 13.sp)
                                Text(node.role, fontSize = 11.sp, color = Slate400)
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = Slate900) {
                                Text(node.technology, fontSize = 10.sp, color = Indigo400, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                            }
                        }
                    }

                    if (index < PortfolioRepository.architectureNodes.size - 1) {
                        Box(modifier = Modifier.fillMaxWidth().height(16.dp), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Cyan400.copy(alpha = 0.5f), modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // Section 3: The Engineering Trade-Off Matrix
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(20.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Slate700, Slate800)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CompareArrows, contentDescription = null, tint = Amber400, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("The Trade-Off Matrix", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                        Text("Why specific engineering decisions were made", color = Amber400, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                PortfolioRepository.tradeOffs.forEach { decision ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slate950)
                            .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "✓ ${decision.choice}",
                                    fontWeight = FontWeight.Bold,
                                    color = Emerald400,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "vs. ${decision.alternative}",
                                    fontSize = 11.sp,
                                    color = Slate400
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = decision.rationale,
                                fontSize = 11.sp,
                                color = Slate300,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(shape = RoundedCornerShape(6.dp), color = Slate800) {
                                Text(
                                    text = "Impact: ${decision.benchmarkImpact}",
                                    fontSize = 10.sp,
                                    color = Cyan400,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Section 4: Interactive API Playground (Swagger / CURL tester)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Slate900),
            shape = RoundedCornerShape(20.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Code, contentDescription = null, tint = Cyan400, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("API Sandbox & Schemas", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                            Text("Live endpoint simulation & payload inspector", color = Cyan400, fontSize = 11.sp)
                        }
                    }

                    IconButton(
                        onClick = {
                            val curlCommand = "curl -X ${currentEndpoint.method} https://api.sathvik.dev${currentEndpoint.endpoint} \\\n  -H 'Content-Type: application/json' \\\n  -d '${currentEndpoint.requestBody}'"
                            clipboardManager.setText(AnnotatedString(curlCommand))
                            copiedCurl = true
                        }
                    ) {
                        Icon(
                            if (copiedCurl) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy CURL",
                            tint = if (copiedCurl) Emerald400 else Slate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Endpoint Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PortfolioRepository.apiEndpoints.forEachIndexed { idx, ep ->
                        FilterChip(
                            selected = selectedApiIndex == idx,
                            onClick = {
                                selectedApiIndex = idx
                                apiResponseOutput = null
                                copiedCurl = false
                            },
                            label = { Text("${ep.method} ${ep.endpoint}", fontSize = 11.sp, fontFamily = FontFamily.Monospace) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                                selectedLabelColor = Cyan400
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(currentEndpoint.description, fontSize = 12.sp, color = Slate300)

                Spacer(modifier = Modifier.height(10.dp))

                // Request Payload Box
                Text("Request Payload (JSON):", fontSize = 11.sp, color = Slate400)
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Slate950)
                        .border(1.dp, Slate800, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(currentEndpoint.requestBody, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFF7DD3FC))
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Execute Simulation Button
                Button(
                    onClick = {
                        isExecutingApi = true
                        coroutineScope.launch {
                            delay(450)
                            apiResponseOutput = currentEndpoint.responseBody
                            isExecutingApi = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("api_execute_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (isExecutingApi) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Slate950, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Calling Endpoint...")
                    } else {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Test Request", fontWeight = FontWeight.Bold)
                    }
                }

                // Response Payload View
                AnimatedVisibility(visible = apiResponseOutput != null) {
                    Column(modifier = Modifier.padding(top = 10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Response (HTTP 200 OK):", fontSize = 11.sp, color = Emerald400, fontWeight = FontWeight.Bold)
                            Text("Latency: 38ms", fontSize = 10.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate950)
                                .border(1.dp, Emerald500.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(apiResponseOutput ?: "", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Emerald400)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TelemetryBadgeItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Slate950)
            .border(1.dp, Slate800, RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(title, fontSize = 10.sp, color = Slate400)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Cyan400)
        }
    }
}
