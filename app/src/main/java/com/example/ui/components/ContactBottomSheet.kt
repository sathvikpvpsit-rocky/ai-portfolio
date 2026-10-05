package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onInquirySent: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var copiedEmail by remember { mutableStateOf(false) }

    var senderName by remember { mutableStateOf("") }
    var senderEmail by remember { mutableStateOf("") }
    var projectType by remember { mutableStateOf("Full-Time Engineering Role") }
    var messageText by remember { mutableStateOf("") }
    var submittedState by remember { mutableStateOf(false) }

    fun sendEmailIntent() {
        val subject = "Engineering Inquiry: $projectType from ${senderName.ifBlank { "Recruiter" }}"
        val body = """
Hi Sathvik,

$messageText

---
From: $senderName ($senderEmail)
Sent via Sathvik Portfolio Android App
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${PortfolioRepository.developerEmail}")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Send Email to Sathvik"))
            submittedState = true
            onInquirySent()
        } catch (e: Exception) {
            submittedState = true
            onInquirySent()
        }
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
        modifier = modifier.testTag("contact_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Let's Connect & Build",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Available for high-impact roles & consultancies",
                        style = MaterialTheme.typography.bodySmall,
                        color = Cyan400
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Slate400)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Direct Email Quick Copy Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Slate950,
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.4f), Indigo400.copy(alpha = 0.4f))))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = Cyan400, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Direct Developer Email", style = MaterialTheme.typography.labelSmall, color = Slate400)
                            Text(PortfolioRepository.developerEmail, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(PortfolioRepository.developerEmail))
                            copiedEmail = true
                        }
                    ) {
                        Icon(
                            if (copiedEmail) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = if (copiedEmail) Emerald400 else Cyan400
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (submittedState) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Emerald500.copy(alpha = 0.15f))
                        .border(1.dp, Emerald500.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Emerald400, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Draft Prepared & Email Client Opened!", fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Thank you! Sathvik will respond within 24 hours.", style = MaterialTheme.typography.bodySmall, color = Slate300)
                    }
                }
            } else {
                Text(
                    text = "Fast Inquiry Form",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = senderName,
                    onValueChange = { senderName = it },
                    label = { Text("Your Name or Organization") },
                    placeholder = { Text("e.g. Jane Doe / Google Talent") },
                    modifier = Modifier.fillMaxWidth().testTag("contact_name_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Cyan400,
                        unfocusedBorderColor = Slate700,
                        focusedContainerColor = Slate950,
                        unfocusedContainerColor = Slate950
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = senderEmail,
                    onValueChange = { senderEmail = it },
                    label = { Text("Your Email") },
                    placeholder = { Text("e.g. recruiter@company.com") },
                    modifier = Modifier.fillMaxWidth().testTag("contact_email_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Cyan400,
                        unfocusedBorderColor = Slate700,
                        focusedContainerColor = Slate950,
                        unfocusedContainerColor = Slate950
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    label = { Text("Project Scope / Job Description") },
                    placeholder = { Text("We would love to discuss a Senior Mobile or Distributed Systems engineering opportunity...") },
                    modifier = Modifier.fillMaxWidth().height(110.dp).testTag("contact_message_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Cyan400,
                        unfocusedBorderColor = Slate700,
                        focusedContainerColor = Slate950,
                        unfocusedContainerColor = Slate950
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { sendEmailIntent() },
                    modifier = Modifier.fillMaxWidth().testTag("submit_contact_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Send Message to Sathvik", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
