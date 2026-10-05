package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Tune
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.example.ui.theme.Amber500
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Indigo400
import com.example.ui.theme.Indigo500
import com.example.ui.theme.NeonGlowCyan
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
import kotlin.random.Random

// -------------------------------------------------------------
// 1. AlgoMatrix Engine Interactive Demo (Sorting & Visualizer)
// -------------------------------------------------------------

enum class SortAlgorithm(val displayName: String, val complexity: String) {
    BUBBLE("Bubble Sort", "O(N²)"),
    QUICK("Quick Sort", "O(N log N)"),
    INSERTION("Insertion Sort", "O(N²)")
}

@Composable
fun AlgoMatrixInteractiveDemo(
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedAlgorithm by remember { mutableStateOf(SortAlgorithm.QUICK) }
    var arraySize by remember { mutableIntStateOf(18) }
    val numbers = remember { mutableStateListOf<Int>() }
    var activeIndices by remember { mutableStateOf(Pair(-1, -1)) }
    var pivotIndex by remember { mutableIntStateOf(-1) }
    var comparisons by remember { mutableIntStateOf(0) }
    var swaps by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var isSorted by remember { mutableStateOf(false) }
    var speedMs by remember { mutableLongStateOf(40L) }

    fun resetArray() {
        isRunning = false
        isSorted = false
        comparisons = 0
        swaps = 0
        activeIndices = Pair(-1, -1)
        pivotIndex = -1
        numbers.clear()
        val randomList = (1..arraySize).map { Random.nextInt(15, 100) }
        numbers.addAll(randomList)
    }

    LaunchedEffect(arraySize) {
        resetArray()
    }

    suspend fun runBubbleSort() {
        val n = numbers.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (!isRunning) return
                activeIndices = Pair(j, j + 1)
                comparisons++
                delay(speedMs)
                if (numbers[j] > numbers[j + 1]) {
                    val temp = numbers[j]
                    numbers[j] = numbers[j + 1]
                    numbers[j + 1] = temp
                    swaps++
                }
            }
        }
        activeIndices = Pair(-1, -1)
        isSorted = true
        isRunning = false
    }

    suspend fun quickSortRecursive(low: Int, high: Int) {
        if (!isRunning || low >= high) return
        val pivot = numbers[high]
        pivotIndex = high
        var i = low - 1
        for (j in low until high) {
            if (!isRunning) return
            activeIndices = Pair(j, high)
            comparisons++
            delay(speedMs)
            if (numbers[j] < pivot) {
                i++
                val temp = numbers[i]
                numbers[i] = numbers[j]
                numbers[j] = temp
                swaps++
            }
        }
        val temp = numbers[i + 1]
        numbers[i + 1] = numbers[high]
        numbers[high] = temp
        swaps++
        val partitionIndex = i + 1
        pivotIndex = -1

        quickSortRecursive(low, partitionIndex - 1)
        quickSortRecursive(partitionIndex + 1, high)
    }

    suspend fun runInsertionSort() {
        for (i in 1 until numbers.size) {
            val key = numbers[i]
            var j = i - 1
            pivotIndex = i
            while (j >= 0 && numbers[j] > key) {
                if (!isRunning) return
                activeIndices = Pair(j, j + 1)
                comparisons++
                delay(speedMs)
                numbers[j + 1] = numbers[j]
                swaps++
                j--
            }
            numbers[j + 1] = key
        }
        pivotIndex = -1
        activeIndices = Pair(-1, -1)
        isSorted = true
        isRunning = false
    }

    fun startSorting() {
        if (isRunning) return
        onSimulateAction()
        isRunning = true
        isSorted = false
        coroutineScope.launch {
            when (selectedAlgorithm) {
                SortAlgorithm.BUBBLE -> runBubbleSort()
                SortAlgorithm.QUICK -> {
                    quickSortRecursive(0, numbers.size - 1)
                    if (isRunning) {
                        isSorted = true
                        isRunning = false
                        activeIndices = Pair(-1, -1)
                    }
                }
                SortAlgorithm.INSERTION -> runInsertionSort()
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("algomatrix_demo_card"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.linearGradient(listOf(Cyan400, Indigo400))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "AlgoMatrix Engine 3D",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Real-Time Canvas Algorithm Visualizer",
                            style = MaterialTheme.typography.bodySmall,
                            color = Cyan400
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isSorted) Emerald500.copy(alpha = 0.2f) else if (isRunning) Amber500.copy(alpha = 0.2f) else Slate800
                ) {
                    Text(
                        text = if (isSorted) "SORTED ✓" else if (isRunning) "RUNNING..." else "READY",
                        color = if (isSorted) Emerald400 else if (isRunning) Amber400 else Slate400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Algorithm Selector Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SortAlgorithm.values().forEach { algo ->
                    FilterChip(
                        selected = selectedAlgorithm == algo,
                        onClick = {
                            if (!isRunning) {
                                selectedAlgorithm = algo
                                resetArray()
                            }
                        },
                        label = { Text("${algo.displayName} (${algo.complexity})", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Canvas Bar Rendering
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    if (numbers.isEmpty()) return@Canvas
                    val totalBars = numbers.size
                    val spacing = 4.dp.toPx()
                    val barWidth = (size.width - (spacing * (totalBars - 1))) / totalBars
                    val maxHeight = size.height - 10.dp.toPx()

                    numbers.forEachIndexed { index, value ->
                        val barHeight = (value / 100f) * maxHeight
                        val left = index * (barWidth + spacing)
                        val top = size.height - barHeight

                        val isCurrentActive = index == activeIndices.first || index == activeIndices.second
                        val isPivot = index == pivotIndex

                        val barBrush = when {
                            isSorted -> Brush.verticalGradient(listOf(Emerald400, Emerald500))
                            isPivot -> Brush.verticalGradient(listOf(Rose400, Rose500))
                            isCurrentActive -> Brush.verticalGradient(listOf(Amber400, Amber500))
                            else -> Brush.verticalGradient(listOf(Cyan400, Indigo500))
                        }

                        drawRoundRect(
                            brush = barBrush,
                            topLeft = Offset(left, top),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Metrics Telemetry
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate800)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Comparisons", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("$comparisons", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Cyan400)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Swaps", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("$swaps", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Amber400)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Elements", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("${numbers.size}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Complexity", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text(selectedAlgorithm.complexity, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Indigo400)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Control Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (isRunning) {
                            isRunning = false
                        } else {
                            if (isSorted) resetArray()
                            startSorting()
                        }
                    },
                    modifier = Modifier.weight(1f).testTag("algomatrix_play_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) Amber500 else Cyan500,
                        contentColor = Slate950
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isRunning) "Pause" else if (isSorted) "Restart" else "Visualize", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { resetArray() },
                    modifier = Modifier.testTag("algomatrix_shuffle_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. CloudMesh Load Balancer Interactive Simulator
// -------------------------------------------------------------

data class ServerNode(
    val id: String,
    val name: String,
    var loadPercentage: Float, // 0f to 1f
    var activeConnections: Int,
    var isHealthy: Boolean = true
)

enum class RoutingStrategy(val label: String) {
    CONSISTENT_HASHING("Consistent Hashing Ring"),
    LEAST_CONNECTIONS("Least Connections"),
    ROUND_ROBIN("Weighted Round Robin")
}

@Composable
fun CloudMeshInteractiveDemo(
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var strategy by remember { mutableStateOf(RoutingStrategy.CONSISTENT_HASHING) }
    var totalRequestsHandled by remember { mutableIntStateOf(1420) }
    var cacheHitRatio by remember { mutableFloatStateOf(0.84f) }
    var avgLatencyMs by remember { mutableFloatStateOf(3.4f) }
    var lastRoutedNode by remember { mutableStateOf<String?>(null) }

    val nodes = remember {
        mutableStateListOf(
            ServerNode("alpha", "Node Alpha (us-east)", 0.28f, 14, true),
            ServerNode("beta", "Node Beta (eu-west)", 0.45f, 22, true),
            ServerNode("gamma", "Node Gamma (ap-south)", 0.32f, 16, true),
            ServerNode("delta", "Node Delta (us-west)", 0.18f, 9, true)
        )
    }

    fun dispatchRequests(count: Int) {
        onSimulateAction()
        coroutineScope.launch {
            for (i in 0 until count) {
                totalRequestsHandled++
                val healthyNodes = nodes.filter { it.isHealthy }
                if (healthyNodes.isNotEmpty()) {
                    val target = when (strategy) {
                        RoutingStrategy.ROUND_ROBIN -> healthyNodes[totalRequestsHandled % healthyNodes.size]
                        RoutingStrategy.LEAST_CONNECTIONS -> healthyNodes.minByOrNull { it.activeConnections } ?: healthyNodes.first()
                        RoutingStrategy.CONSISTENT_HASHING -> {
                            val hash = (totalRequestsHandled * 31 + Random.nextInt(100)).hashCode()
                            healthyNodes[kotlin.math.abs(hash) % healthyNodes.size]
                        }
                    }
                    lastRoutedNode = target.id
                    val nodeIndex = nodes.indexOfFirst { it.id == target.id }
                    if (nodeIndex >= 0) {
                        val current = nodes[nodeIndex]
                        val newConns = current.activeConnections + 1
                        val newLoad = (current.loadPercentage + 0.04f).coerceAtMost(0.98f)
                        nodes[nodeIndex] = current.copy(activeConnections = newConns, loadPercentage = newLoad)
                    }
                }

                // Adjust cache hit & latency slightly for realism
                cacheHitRatio = (cacheHitRatio + (Random.nextFloat() * 0.02f - 0.01f)).coerceIn(0.72f, 0.94f)
                avgLatencyMs = (avgLatencyMs + (Random.nextFloat() * 0.4f - 0.2f)).coerceIn(2.1f, 5.8f)

                delay(30)
            }

            delay(200)
            // Drain load back to equilibrium
            nodes.indices.forEach { idx ->
                val node = nodes[idx]
                val settledLoad = (node.loadPercentage * 0.7f).coerceAtLeast(0.15f)
                val settledConns = (node.activeConnections * 0.7f).toInt().coerceAtLeast(4)
                nodes[idx] = node.copy(loadPercentage = settledLoad, activeConnections = settledConns)
            }
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("cloudmesh_demo_card"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Indigo400.copy(alpha = 0.5f), Cyan400.copy(alpha = 0.5f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.linearGradient(listOf(Indigo400, Cyan400))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Bolt, contentDescription = null, tint = Slate950, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CloudMesh Distributed Engine",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Low-Latency Cache & Node Rebalancing",
                            style = MaterialTheme.typography.bodySmall,
                            color = Indigo400
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Emerald500.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "CLUSTER HEALTHY",
                        color = Emerald400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Strategy Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RoutingStrategy.values().forEach { strat ->
                    FilterChip(
                        selected = strategy == strat,
                        onClick = { strategy = strat },
                        label = { Text(strat.label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Indigo400.copy(alpha = 0.2f),
                            selectedLabelColor = Indigo400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cluster Nodes
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                nodes.forEach { node ->
                    val isJustRouted = node.id == lastRoutedNode
                    val borderAlpha by animateFloatAsState(if (isJustRouted) 1f else 0.2f, label = "border")

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Slate950)
                            .border(1.dp, if (node.isHealthy) Cyan400.copy(alpha = borderAlpha) else Rose500, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (node.isHealthy) Emerald400 else Rose500)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = node.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = if (node.isHealthy) "${(node.loadPercentage * 100).toInt()}% CPU | ${node.activeConnections} conns" else "OFFLINE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (node.isHealthy) Slate400 else Rose400
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { if (node.isHealthy) node.loadPercentage else 0f },
                                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                                color = if (node.loadPercentage > 0.8f) Rose400 else Cyan400,
                                trackColor = Slate800,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Real-Time Telemetry Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate800)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Req", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("$totalRequestsHandled", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Cache Hit", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("${(cacheHitRatio * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Emerald400)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("P99 Latency", style = MaterialTheme.typography.labelSmall, color = Slate400)
                    Text("${"%.1f".format(avgLatencyMs)}ms", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Cyan400)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Simulation Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { dispatchRequests(10) },
                    modifier = Modifier.weight(1f).testTag("cloudmesh_send10_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Cyan500, contentColor = Slate950),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Burst 10 Req", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { dispatchRequests(35) },
                    modifier = Modifier.weight(1f).testTag("cloudmesh_burst_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo500, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.FastForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Spike (35)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val betaIndex = nodes.indexOfFirst { it.id == "beta" }
                        if (betaIndex >= 0) {
                            val curr = nodes[betaIndex]
                            nodes[betaIndex] = curr.copy(isHealthy = !curr.isHealthy)
                        }
                    },
                    modifier = Modifier.testTag("cloudmesh_failover_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.BugReport, contentDescription = "Toggle Outage", modifier = Modifier.size(16.dp), tint = Rose400)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. CodeCraft Architecture & Code Snippet Inspector
// -------------------------------------------------------------

data class CodeSnippetItem(
    val title: String,
    val tag: String,
    val timeComplexity: String,
    val spaceComplexity: String,
    val explanation: String,
    val code: String
)

@Composable
fun CodeCraftInteractiveDemo(
    onSimulateAction: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedToClipboard by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var isSimulatingBenchmark by remember { mutableStateOf(false) }
    var benchmarkLog by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val snippets = remember {
        listOf(
            CodeSnippetItem(
                title = "Consistent Hash Ring with Virtual Nodes",
                tag = "Distributed Systems",
                timeComplexity = "O(log V) where V = virtual nodes",
                spaceComplexity = "O(N * V) nodes",
                explanation = "Guarantees that when a server node fails or scales up, only K/N keys need remapping rather than complete cache thrashing.",
                code = """
class ConsistentHashRing<T>(
    private val vNodesPerPhysical: Int = 160,
    private val hashFn: (String) -> Long = { Murmur3.hash64(it.toByteArray()) }
) {
    private val ring = ConcurrentSkipListMap<Long, T>()

    fun addNode(node: T, identifier: String) {
        for (i in 0 until vNodesPerPhysical) {
            val vKey = "${'$'}{identifier}-vnode-${'$'}i"
            ring[hashFn(vKey)] = node
        }
    }

    fun route(requestKey: String): T? {
        if (ring.isEmpty()) return null
        val hash = hashFn(requestKey)
        val entry = ring.ceilingEntry(hash) ?: ring.firstEntry()
        return entry.value
    }
}
                """.trimIndent()
            ),
            CodeSnippetItem(
                title = "Biometric Telemetry Fast Ring Buffer",
                tag = "Android & Room DB",
                timeComplexity = "O(1) append, O(B) batched flush",
                spaceComplexity = "O(B) memory bounded",
                explanation = "Eliminates main thread UI stalls by buffering high frequency 50Hz sensor events into memory and issuing batched SQLite WAL writes.",
                code = """
class TelemetryBatchCollector(
    private val dao: TelemetryDao,
    private val scope: CoroutineScope
) {
    private val buffer = ArrayList<TelemetrySample>(BATCH_SIZE)
    private val lock = Any()

    fun recordSample(sample: TelemetrySample) {
        synchronized(lock) {
            buffer.add(sample)
            if (buffer.size >= BATCH_SIZE) {
                val chunk = ArrayList(buffer)
                buffer.clear()
                scope.launch(Dispatchers.IO) {
                    dao.insertBatchFast(chunk)
                }
            }
        }
    }
}
                """.trimIndent()
            ),
            CodeSnippetItem(
                title = "Unidirectional Data Flow (MVI) ViewModel",
                tag = "Clean Architecture",
                timeComplexity = "O(1) state transitions",
                spaceComplexity = "O(1) immutable snapshot",
                explanation = "Single source of truth utilizing Kotlin StateFlow for UI rendering and buffered Channels for transient one-off side effects.",
                code = """
@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val repository: ProjectRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PortfolioUiState.Initial)
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()

    private val _events = Channel<UiEffect>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun handleIntent(intent: UserIntent) = viewModelScope.launch {
        when (intent) {
            is UserIntent.SelectProject -> {
                _uiState.update { it.copy(selectedId = intent.id) }
                _events.send(UiEffect.TriggerHaptic)
            }
        }
    }
}
                """.trimIndent()
            )
        )
    }

    val currentSnippet = snippets[selectedIndex]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("codecraft_demo_card"),
        colors = CardDefaults.cardColors(containerColor = Slate900),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Cyan400.copy(alpha = 0.5f), Indigo400.copy(alpha = 0.5f))))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.linearGradient(listOf(Cyan400, Indigo400))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("< / >", fontWeight = FontWeight.Bold, color = Slate950, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CodeCraft Architecture Lab",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Clean Code & Benchmark Inspector",
                            style = MaterialTheme.typography.bodySmall,
                            color = Cyan400
                        )
                    }
                }

                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(currentSnippet.code))
                        copiedToClipboard = true
                    }
                ) {
                    Icon(
                        if (copiedToClipboard) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = "Copy Code",
                        tint = if (copiedToClipboard) Emerald400 else Slate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Selector tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                snippets.forEachIndexed { index, snip ->
                    FilterChip(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            copiedToClipboard = false
                            benchmarkLog = null
                        },
                        label = { Text(snip.tag, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Cyan400.copy(alpha = 0.2f),
                            selectedLabelColor = Cyan400
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Complexity & Info Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate800)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time: ${currentSnippet.timeComplexity}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Cyan400,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Space: ${currentSnippet.spaceComplexity}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Amber400,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currentSnippet.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = Slate300
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Code View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = currentSnippet.code,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFF7DD3FC)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (benchmarkLog != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Emerald500.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = benchmarkLog ?: "",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Emerald400,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    onSimulateAction()
                    isSimulatingBenchmark = true
                    coroutineScope.launch {
                        delay(600)
                        benchmarkLog = "✓ Benchmark OK: 10,000 iterations in 2.14ms | 0 GC pauses | P99: 0.18ms"
                        isSimulatingBenchmark = false
                    }
                },
                modifier = Modifier.fillMaxWidth().testTag("codecraft_benchmark_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = Slate800, contentColor = Cyan400),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (isSimulatingBenchmark) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Cyan400, strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Simulating Profiler...")
                } else {
                    Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Run Microbenchmark", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
