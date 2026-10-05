package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.data.repository.PortfolioRepository
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioTopBar(
    onOpenTerminal: () -> Unit,
    onOpenContact: () -> Unit,
    showBookmarkedOnly: Boolean,
    onToggleBookmarkFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("portfolio_top_identity")
            ) {
                // Developer Avatar with online indicator
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Cyan400, Indigo400))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "S",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Slate950
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Emerald400)
                            .border(1.5.dp, Slate900, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = PortfolioRepository.developerName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Emerald400.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "AVAILABLE",
                                color = Emerald400,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "PVPSIT • Systems & Mobile",
                        style = MaterialTheme.typography.labelSmall,
                        color = Slate400,
                        fontSize = 11.sp
                    )
                }
            }
        },
        actions = {
            // Bookmarks toggle
            IconButton(
                onClick = onToggleBookmarkFilter,
                modifier = Modifier.testTag("topbar_bookmarks_toggle")
            ) {
                Icon(
                    if (showBookmarkedOnly) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Saved Projects",
                    tint = if (showBookmarkedOnly) Cyan400 else Slate400
                )
            }

            // Developer Terminal Action
            IconButton(
                onClick = onOpenTerminal,
                modifier = Modifier.testTag("topbar_terminal_btn")
            ) {
                Icon(
                    Icons.Default.Terminal,
                    contentDescription = "Open CLI Terminal",
                    tint = Cyan400
                )
            }

            // Hire / Contact Action
            IconButton(
                onClick = onOpenContact,
                modifier = Modifier.testTag("topbar_contact_btn")
            ) {
                Icon(
                    Icons.Default.Mail,
                    contentDescription = "Contact Sathvik",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Slate950
        ),
        modifier = modifier
    )
}
