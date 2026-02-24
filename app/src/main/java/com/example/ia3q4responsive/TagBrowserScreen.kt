@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
package com.example.ia3q4responsive
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.saveable.listSaver
@Composable
fun TagBrowserScreen(
    allTags: List<String> = sampleTags()
) {
    // ---- simple state ----
    var query by remember { mutableStateOf("") }
    var onlySelected by remember { mutableStateOf(false) }
    var minLength by remember { mutableFloatStateOf(0f) }
    // Preserve selected tags across configuration changes (e.g., screen rotation)
    val selected = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) {
        mutableStateListOf<String>()
    }
    // simple filter logic
    val filteredTags = remember(allTags, query, onlySelected, minLength, selected) {
        allTags
            .asSequence()
            .filter { it.contains(query, ignoreCase = true) }
            .filter { it.length >= minLength.toInt() }
            .filter { !onlySelected || it in selected }
            .toList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tag Browser") },
                actions = {
                    IconButton(onClick = {
                        query = ""
                        onlySelected = false
                        minLength = 0f
                        selected.clear()
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Reset")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // spacing ✅
        ) {
            // ---- M3 component #1: OutlinedTextField ----
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                label = { Text("Search tags") },
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Selected Tags area (small, updates) ----
            SelectedTagsCard(
                selected = selected,
                onRemove = { selected.remove(it) },
                onClear = { selected.clear() }
            )

            HorizontalDivider()

            Text("All Tags", style = MaterialTheme.typography.titleMedium)

            // ---- FlowRow: chips wrap across screen ----
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                filteredTags.forEach { tag ->
                    val isSelected = tag in selected

                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) selected.remove(tag) else selected.add(tag)
                        },
                        label = { Text(tag) },
                        // selected visual state
                        border = if (isSelected) {
                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            HorizontalDivider()

            Text("Filters (FlowColumn)", style = MaterialTheme.typography.titleMedium)

            // ---- FlowColumn: different purpose ----
            FlowColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachColumn = 3
            ) {
                // M3 component #2: Card
                Card(modifier = Modifier.widthIn(min = 160.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Only selected")
                        Spacer(Modifier.weight(1f))
                        // M3 component #3: Switch
                        Switch(
                            checked = onlySelected,
                            onCheckedChange = { onlySelected = it }
                        )
                    }
                }
                // M3 component #4: Card + Slider
                Card(modifier = Modifier.widthIn(min = 160.dp)) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Min tag length: ${minLength.toInt()}")
                        Slider(
                            value = minLength,
                            onValueChange = { minLength = it },
                            valueRange = 0f..12f
                        )
                    }
                }
                // M3 component #5: Button
                Button(
                    onClick = {
                        query = ""
                        onlySelected = false
                        minLength = 0f
                    },
                    modifier = Modifier.widthIn(min = 160.dp)
                ) {
                    Text("Clear Filters")
                }
            }
        }
    }
}

@Composable
private fun SelectedTagsCard(
    selected: List<String>,
    onRemove: (String) -> Unit,
    onClear: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Selected Tags", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onClear, enabled = selected.isNotEmpty()) {
                    Text("Clear")
                }
            }

            if (selected.isEmpty()) {
                Text("None yet. Tap chips to select.")
            } else {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selected.forEach { tag ->
                        AssistChip(// M3 component #6
                            onClick = { onRemove(tag) },
                            label = { Text(tag) }
                        )
                    }
                }
            }
        }
    }
}

private fun sampleTags(): List<String> = listOf(
    "Kotlin", "Compose", "Material3", "Android", "UI", "UX",
    "FlowRow", "FlowColumn", "State", "Navigation", "Room", "Retrofit",
    "Hilt", "Coroutines", "MVVM", "Firebase", "Testing", "Gradle",
    "Animation", "Accessibility", "Performance", "DarkMode"
)