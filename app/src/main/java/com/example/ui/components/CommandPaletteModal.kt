package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

data class PaletteAction(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val category: String,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandPaletteModal(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onNavigateToProject: (String) -> Unit,
    onNavigateToDemo: (String) -> Unit,
    onToggleAudienceMode: () -> Unit,
    onOpenResume: () -> Unit,
    onOpenTerminal: () -> Unit,
    onOpenContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    val allActions = remember {
        listOf(
            PaletteAction("aud_toggle", "Toggle Audience View", "Switch between Recruiter & Developer views", Icons.Default.SwapHoriz, "Mode") {
                onDismiss()
                onToggleAudienceMode()
            },
            PaletteAction("demo_cloudflare", "Cloudflare Tokenizer & WAF", "Edge tokenization & Turnstile security verification", Icons.Default.PlayArrow, "Security") {
                onDismiss()
                onNavigateToDemo("cloudflare_token")
            },
            PaletteAction("demo_factforge", "FactForge AI Sandbox", "Live knowledge graph verification without API keys", Icons.Default.PlayArrow, "Demo") {
                onDismiss()
                onNavigateToDemo("factforge")
            },
            PaletteAction("demo_algomatrix", "AlgoMatrix Engine 3D", "Live sorting & pathfinding canvas visualizer", Icons.Default.PlayArrow, "Demo") {
                onDismiss()
                onNavigateToDemo("algomatrix")
            },
            PaletteAction("demo_cloudmesh", "CloudMesh Load Balancer", "Simulate consistent hashing and node failovers", Icons.Default.PlayArrow, "Demo") {
                onDismiss()
                onNavigateToDemo("cloudmesh")
            },
            PaletteAction("proj_nexusflow", "NexusFlow Distributed Mesh", "120k req/s high-throughput event streamer", Icons.Default.Code, "Project") {
                onDismiss()
                onNavigateToProject("nexusflow")
            },
            PaletteAction("proj_pulsefit", "PulseFit Pro", "Offline-first biometric motion architecture with Room", Icons.Default.Code, "Project") {
                onDismiss()
                onNavigateToProject("pulsefit")
            },
            PaletteAction("act_resume", "View Resume / CV", "Verified engineering credentials and achievements", Icons.Default.Description, "Document") {
                onDismiss()
                onOpenResume()
            },
            PaletteAction("act_terminal", "Open CLI Terminal", "Interactive developer bash command shell", Icons.Default.Terminal, "Tool") {
                onDismiss()
                onOpenTerminal()
            },
            PaletteAction("act_contact", "Hire Sathvik / Contact", "Send direct project or job offer inquiry", Icons.Default.Mail, "Contact") {
                onDismiss()
                onOpenContact()
            }
        )
    }

    val filteredActions = allActions.filter {
        query.isBlank() ||
                it.title.contains(query, ignoreCase = true) ||
                it.subtitle.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
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
        modifier = modifier.testTag("command_palette_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().testTag("palette_search_input"),
                placeholder = { Text("Jump anywhere (e.g. 'demo', 'factforge', 'resume')...", color = Slate400, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Cyan400) },
                trailingIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400)
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Cyan400,
                    unfocusedBorderColor = Slate800,
                    focusedContainerColor = Slate950,
                    unfocusedContainerColor = Slate950
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action Items List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredActions) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Slate950)
                            .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                            .clickable { item.action() }
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Slate800),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, contentDescription = null, tint = Cyan400, modifier = Modifier.size(18.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(item.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                    Text(item.subtitle, fontSize = 11.sp, color = Slate400)
                                }
                            }

                            Surface(shape = RoundedCornerShape(4.dp), color = Slate800) {
                                Text(item.category, fontSize = 10.sp, color = Indigo400, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
