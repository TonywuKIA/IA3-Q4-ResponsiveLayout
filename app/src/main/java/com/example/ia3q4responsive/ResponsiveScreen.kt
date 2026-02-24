@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ia3q4responsive

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

data class OptionItem(
    val id: Int, val title: String, val subtitle: String, val icon: ImageVector
)

@Composable
fun ResponsiveLayoutChallenge() {
    val options = remember {
        listOf(
            OptionItem(1, "Profile", "View your info", Icons.Filled.Person),
            OptionItem(id = 2, title = "Messages", subtitle = "Inbox & chats", Icons.Filled.Email),
            OptionItem(3, "Settings", "Preferences", Icons.Filled.Settings),
            OptionItem(4, "Favorites", "Saved items", Icons.Filled.Favorite),
            OptionItem(5, "Security", "2FA & devices", Icons.Filled.Lock),
            OptionItem(id = 6, title = "Help", subtitle = "FAQs & contact", Icons.Filled.Info),
        )
    }

    var selectedId by remember { mutableStateOf(options.first().id) }
    val selected = options.first { it.id == selectedId }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // Responsive breakpoint:
        // Switch between phone (single column) and tablet (two-pane) layouts.
        // Around ~700dp the screen can comfortably support side-by-side content,
        // so navigation becomes persistent instead of stacked.
        val isWide = maxWidth >= 700.dp

        if (isWide) {
            WideTwoPane(
                options = options,
                selectedId = selectedId,
                onSelect = { selectedId = it },
                selected = selected
            )
        } else {
            PhoneSingleColumn(
                options = options,
                selectedId = selectedId,
                onSelect = { selectedId = it },
                selected = selected
            )
        }
    }
}

@Composable
private fun PhoneSingleColumn(
    options: List<OptionItem>, selectedId: Int, onSelect: (Int) -> Unit, selected: OptionItem
) {
    // Phone layout:
    // Content is stacked vertically in a single Column.
    // Navigation list appears first, followed by detail content.
    // This matches typical mobile information hierarchy and scrolling behavior.
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Q4 Responsive Layout") }, actions = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Search"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.MoreVert, contentDescription = "More"
                    )
                }
            })
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "Options",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            // Scrollable option list.
            // LazyColumn composes only visible items and supports large datasets efficiently.
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(options) { item ->
                    OptionListRow(
                        item = item,
                        selected = item.id == selectedId,
                        onClick = { onSelect(item.id) })
                }
            }

            // Detail panel content changes based on selected navigation item.
            // Demonstrates master-detail interaction pattern.
            DetailCard(
                selected = selected, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun WideTwoPane(
    options: List<OptionItem>, selectedId: Int, onSelect: (Int) -> Unit, selected: OptionItem
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Q4 Responsive Layout") }, actions = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Search"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.MoreVert, contentDescription = "More"
                    )
                }
            })
        }) { padding ->
        // Two-pane tablet layout:
        // Left side provides persistent navigation,
        // right side shows detail content.
        // weight() distributes space proportionally across screen sizes.
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // ===== Left Pane =====
            Row(
                modifier = Modifier
                    .weight(0.38f)   // required: weight() between panes
                    .fillMaxHeight() // required: fillMaxHeight()
            ) {
                // Material3 large-screen navigation pattern.
                // NavigationRail keeps destinations always visible instead of using a top app bar menu.
                NavigationRail {
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") })
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Filled.Star, contentDescription = "Star") },
                        label = { Text("Star") })
                    NavigationRailItem(
                        selected = false,
                        onClick = {},
                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") })
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Navigation",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(12.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(options) { item ->
                            OptionListRow(
                                item = item,
                                selected = item.id == selectedId,
                                onClick = { onSelect(item.id) })
                        }
                    }
                }
            }

            // ===== Right Pane =====
            // Box allows overlay elements (e.g., bottom chip)
            // while Column organizes primary content vertically.
            Box(
                modifier = Modifier
                    .weight(0.62f)   // required: weight() between panes
                    .fillMaxHeight() // required: fillMaxHeight()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailHeader(selected)

                    DetailCard(
                        selected = selected, modifier = Modifier.fillMaxWidth()
                    )

                    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Actions", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Button(onClick = {}, modifier = Modifier.weight(1f)) {
                                    Icon(Icons.Filled.Done, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Confirm")
                                }
                                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                                    Text("Cancel")
                                }
                            }
                        }
                    }
                }

                AssistChip(
                    onClick = {},
                    label = { Text("Wide Mode") },
                    leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Composable
private fun OptionListRow(
    item: OptionItem, selected: Boolean, onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(item.title) },
        supportingContent = { Text(item.subtitle) },
        leadingContent = { Icon(item.icon, contentDescription = null) },
        trailingContent = {
            if (selected) Icon(
                Icons.Filled.Check, contentDescription = "Selected"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)   // make row clickable
            .padding(vertical = 2.dp),
        colors = ListItemDefaults.colors(
            containerColor = if (selected) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        )
    )
    HorizontalDivider()
}

@Composable
private fun DetailHeader(selected: OptionItem) {
    ElevatedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(selected.icon, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(selected.title, style = MaterialTheme.typography.titleLarge)
                Text(selected.subtitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun DetailCard(
    selected: OptionItem, modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Detail", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "Selected: ${selected.title}\n\n" + "This detail pane demonstrates Box + Column composition in wide mode, " + "and remains on the same screen in phone mode."
            )
        }
    }
}

