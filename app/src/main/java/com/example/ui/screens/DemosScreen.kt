package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AlgoMatrixInteractiveDemo
import com.example.ui.components.CloudMeshInteractiveDemo
import com.example.ui.components.CloudflareTokenizationDemo
import com.example.ui.components.CodeCraftInteractiveDemo
import com.example.ui.components.FactForgeInteractiveDemo
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate900

enum class DemoTab(val title: String) {
    CLOUDFLARE_TOKEN("Cloudflare Tokenizer & WAF"),
    FACT_FORGE("FactForge AI (RAG Verifier)"),
    ALGO_MATRIX("AlgoMatrix (120 FPS Visualizer)"),
    CLOUD_MESH("CloudMesh (Load Balancer)"),
    CODE_CRAFT("CodeCraft (Architecture Lab)")
}

@Composable
fun DemosScreen(
    initialDemo: String? = null,
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember {
        mutableStateOf(
            when (initialDemo) {
                "cloudflare_token" -> DemoTab.CLOUDFLARE_TOKEN
                "factforge" -> DemoTab.FACT_FORGE
                "cloudmesh" -> DemoTab.CLOUD_MESH
                "codecraft" -> DemoTab.CODE_CRAFT
                "algomatrix" -> DemoTab.ALGO_MATRIX
                else -> DemoTab.CLOUDFLARE_TOKEN
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("demos_screen_list")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))

            Column {
                Text(
                    text = "Interactive Demo Laboratory",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Live Cloudflare security, AI verification, & distributed engines",
                    style = MaterialTheme.typography.bodySmall,
                    color = Cyan400
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Demo Selector Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DemoTab.values().forEach { tab ->
                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab.title, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400,
                            containerColor = Slate900,
                            labelColor = Slate400
                        )
                    )
                }
            }
        }

        item {
            when (selectedTab) {
                DemoTab.CLOUDFLARE_TOKEN -> CloudflareTokenizationDemo(onSimulateAction = onSimulateAction)
                DemoTab.FACT_FORGE -> FactForgeInteractiveDemo(onSimulateAction = onSimulateAction)
                DemoTab.ALGO_MATRIX -> AlgoMatrixInteractiveDemo(onSimulateAction = onSimulateAction)
                DemoTab.CLOUD_MESH -> CloudMeshInteractiveDemo(onSimulateAction = onSimulateAction)
                DemoTab.CODE_CRAFT -> CodeCraftInteractiveDemo(onSimulateAction = onSimulateAction)
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
