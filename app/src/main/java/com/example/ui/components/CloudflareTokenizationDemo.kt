package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.Amber400
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Indigo500
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.MessageDigest

data class TokenizationSample(
    val title: String,
    val rawPayload: String,
    val tokenType: String
)

@Composable
fun CloudflareTokenizationDemo(
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    val presetPayloads = listOf(
        TokenizationSample("API Key & Secret", "sk_prod_9481b7a2d3c4e5f601a98765", "FPE Surrogate"),
        TokenizationSample("User Identity / PII", "sathvik.engineer.pvpsit@internal.vault", "Zero-Knowledge Hash"),
        TokenizationSample("Payment Token", "4532-8921-7712-9014", "Format-Preserving Token")
    )

    var inputData by remember { mutableStateOf(presetPayloads.first().rawPayload) }
    var edgeColocation by remember { mutableStateOf("MAA (Chennai, India)") }
    var isProcessing by remember { mutableStateOf(false) }
    var turnstileVerified by remember { mutableStateOf(true) }
    var generatedToken by remember { mutableStateOf<String?>("tok_cf_8f3d1b74a2e5_sec") }
    var hmacSignature by remember { mutableStateOf<String?>("sha256:d8a209b1f7e4c3d2") }
    var copiedToken by remember { mutableStateOf(false) }

    fun processTokenization() {
        onSimulateAction()
        isProcessing = true
        generatedToken = null
        hmacSignature = null
        copiedToken = false

        coroutineScope.launch {
            delay(400) // Simulate Cloudflare Turnstile token check and Edge Worker tokenization
            val hash = MessageDigest.getInstance("SHA-256")
                .digest(inputData.toByteArray())
                .joinToString("") { "%02x".format(it) }
            val truncatedToken = "tok_cf_${hash.take(12)}_sec"
            val sig = "sha256:${hash.takeLast(16)}"

            generatedToken = truncatedToken
            hmacSignature = sig
            turnstileVerified = true
            isProcessing = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("cloudflare_token_card"),
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
                        Icon(Icons.Default.Shield, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Cloudflare EdgeShield & Tokenizer",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Turnstile Verification & Zero-Trust Tokenization",
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
                        text = "EDGE ACTIVE",
                        color = Emerald400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Cloudflare Edge Telemetry Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudDone, contentDescription = null, tint = Cyan400, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Edge Node: $edgeColocation", fontSize = 11.sp, color = Slate300)
                }
                Text("Latency: 1.2ms", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = Emerald400, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Preset Payload Selector
            Text("Select or enter sensitive data to tokenize:", style = MaterialTheme.typography.labelSmall, color = Slate400)
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presetPayloads.forEach { item ->
                    FilterChip(
                        selected = inputData == item.rawPayload,
                        onClick = {
                            inputData = item.rawPayload
                            processTokenization()
                        },
                        label = { Text(item.title, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Raw input field
            OutlinedTextField(
                value = inputData,
                onValueChange = { inputData = it },
                label = { Text("Raw Sensitive Payload", fontSize = 12.sp) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("tokenization_input_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Cyan400,
                    unfocusedBorderColor = Slate700,
                    focusedContainerColor = Slate950,
                    unfocusedContainerColor = Slate950
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Turnstile Status & Token Output
            if (isProcessing) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Cyan400, strokeWidth = 3.dp, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Verifying Cloudflare Turnstile token...", fontSize = 11.sp, color = Slate400)
                    }
                }
            } else if (generatedToken != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Slate950)
                        .border(1.dp, Emerald500.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Emerald400, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Turnstile Token: PASS (Human 0.99)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Emerald400)
                            }

                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(generatedToken ?: ""))
                                    copiedToken = true
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    if (copiedToken) Icons.Default.Check else Icons.Default.ContentCopy,
                                    contentDescription = "Copy Token",
                                    tint = if (copiedToken) Emerald400 else Slate400,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Format-Preserved Surrogate Token:", fontSize = 11.sp, color = Slate400)
                        Text(
                            text = generatedToken ?: "",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Cyan400
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("HMAC-SHA256 Cryptographic Signature:", fontSize = 11.sp, color = Slate400)
                        Text(
                            text = hmacSignature ?: "",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Slate300
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Button
            Button(
                onClick = { processTokenization() },
                modifier = Modifier.fillMaxWidth().testTag("tokenize_now_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Execute Cloudflare Edge Tokenization", fontWeight = FontWeight.Bold)
            }
        }
    }
}
