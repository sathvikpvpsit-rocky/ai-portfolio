package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Amber400
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Rose400
import com.example.ui.theme.Rose500
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

data class TerminalLog(
    val command: String,
    val output: String,
    val isError: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onTriggerUnlock: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var inputCommand by remember { mutableStateOf("") }
    val logs = remember {
        mutableStateListOf(
            TerminalLog(
                command = "sathvik --version",
                output = "Sathvik DevEngine v2.4.0 (arm64-v8a)\nStatus: Ready. Type 'help' to see available commands."
            )
        )
    }

    fun execute(cmd: String) {
        val trimmed = cmd.trim().lowercase()
        if (trimmed.isEmpty()) return
        onTriggerUnlock()

        when (trimmed) {
            "help" -> {
                logs.add(
                    TerminalLog(
                        command = cmd,
                        output = """
Commands Available:
  • help       - Show this command reference
  • skills     - List core competencies & proficiency
  • projects   - Display featured engineering projects
  • pvpsit     - View academic credentials & college background
  • contact    - Display email & social handles
  • hire       - Fast-track recruiter briefing
  • stats      - Show real-time system performance telemetry
  • clear      - Clear terminal history
                        """.trimIndent()
                    )
                )
            }
            "skills" -> {
                val list = PortfolioRepository.skills.joinToString("\n") {
                    "  [${it.proficiencyPercent}%] ${it.name} (${it.category.displayName})"
                }
                logs.add(TerminalLog(cmd, "Core Technical Stack:\n$list"))
            }
            "projects" -> {
                val list = PortfolioRepository.projects.joinToString("\n\n") {
                    "  ★ ${it.title}\n    ${it.subtitle}\n    Tags: ${it.tags.joinToString(", ")}"
                }
                logs.add(TerminalLog(cmd, "Featured Projects:\n$list"))
            }
            "pvpsit" -> {
                logs.add(
                    TerminalLog(
                        cmd,
                        "Alma Mater: Prasad V. Potluri Siddhartha Institute of Technology (PVPSIT)\nDegree: B.Tech in Computer Science & Engineering\nHonors: Department Tech Lead, 1st Place National Hackathon Champion."
                    )
                )
            }
            "contact", "hire" -> {
                logs.add(
                    TerminalLog(
                        cmd,
                        "Developer: Sathvik\nEmail: ${PortfolioRepository.developerEmail}\nGitHub: ${PortfolioRepository.developerGithub}\nLinkedIn: ${PortfolioRepository.developerLinkedin}\nStatus: ${PortfolioRepository.developerStatus}"
                    )
                )
            }
            "stats" -> {
                logs.add(
                    TerminalLog(
                        cmd,
                        "Telemetry:\n  • Avg Latency: < 4ms P99\n  • Uptime: 99.99%\n  • Clean Architecture: Enforced\n  • Jetpack Compose: 100% Native"
                    )
                )
            }
            "clear" -> {
                logs.clear()
            }
            else -> {
                logs.add(
                    TerminalLog(
                        command = cmd,
                        output = "bash: command not found: $trimmed. Type 'help' for options.",
                        isError = true
                    )
                )
            }
        }
        inputCommand = ""
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Slate950,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(width = 44.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Slate700)
            )
        },
        modifier = modifier.testTag("terminal_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Terminal Window Titlebar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Slate900)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Rose500))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Amber400))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Emerald400))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "sathvik@pvpsit-station:~",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate300
                    )
                }

                IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400, modifier = Modifier.size(16.dp))
                }
            }

            // Terminal Logs Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(Slate950)
                    .border(1.dp, Slate800)
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                logs.forEach { log ->
                    Row {
                        Text("$ ", fontFamily = FontFamily.Monospace, color = Cyan400, fontSize = 12.sp)
                        Text(log.command, fontFamily = FontFamily.Monospace, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = log.output,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = if (log.isError) Rose400 else Emerald400
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Command Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("help", "skills", "projects", "pvpsit", "hire", "stats", "clear").forEach { cmd ->
                    FilterChip(
                        selected = false,
                        onClick = { execute(cmd) },
                        label = { Text(cmd, fontFamily = FontFamily.Monospace, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Slate900,
                            labelColor = Cyan400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Input field
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputCommand,
                    onValueChange = { inputCommand = it },
                    modifier = Modifier.weight(1f).testTag("terminal_input"),
                    placeholder = { Text("type command (e.g. 'help')...", fontFamily = FontFamily.Monospace, fontSize = 12.sp) },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace, color = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { execute(inputCommand) }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Cyan400,
                        unfocusedBorderColor = Slate700,
                        focusedContainerColor = Slate900,
                        unfocusedContainerColor = Slate900
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { execute(inputCommand) },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Cyan400)
                        .testTag("terminal_send_btn")
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Execute", tint = Slate950)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
