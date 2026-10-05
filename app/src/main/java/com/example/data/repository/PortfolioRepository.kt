package com.example.data.repository

import com.example.data.model.ContactInquiry
import com.example.data.model.ExperienceType
import com.example.data.model.PortfolioAchievement
import com.example.data.model.Project
import com.example.data.model.ProjectCategory
import com.example.data.model.ProjectMetric
import com.example.data.model.SkillCategory
import com.example.data.model.SkillItem
import com.example.data.model.Testimonial
import com.example.data.model.TimelineItem

data class DeveloperPersona(
    val id: String,
    val title: String,
    val headline: String,
    val emphasis: String
)

data class TradeOffDecision(
    val choice: String,
    val alternative: String,
    val rationale: String,
    val benchmarkImpact: String
)

data class ApiEndpointSchema(
    val method: String,
    val endpoint: String,
    val description: String,
    val requestBody: String,
    val responseBody: String
)

data class ArchitectureNode(
    val name: String,
    val role: String,
    val technology: String,
    val isHighlighted: Boolean = false
)

object PortfolioRepository {

    val developerName = "Sathvik"
    val developerRole = "AI, Cloud & Mobile Software Engineer"
    val developerEmail = "sathvikpvpsit@gmail.com"
    val developerGithub = "https://github.com/sathvik"
    val developerLinkedin = "https://linkedin.com/in/sathvik-pvpsit"
    val developerLeetcode = "https://leetcode.com/u/sathvik-pvpsit"
    val developerCollege = "Prasad V. Potluri Siddhartha Institute of Technology (PVPSIT)"
    val developerStatus = "Open to Full-Stack AI & Cloud Systems Roles"
    val developerTagline = "Architecting scalable data pipelines, autonomous agents, and cloud-native backends for the next generation of AI."

    val personas: List<DeveloperPersona> = listOf(
        DeveloperPersona(
            id = "balanced",
            title = "Balanced All-Rounder",
            headline = "Engineering production-ready AI and cloud architectures that bridge advanced machine learning with high-impact business solutions.",
            emphasis = "AI Architecture & Business Impact"
        ),
        DeveloperPersona(
            id = "punchy",
            title = "Short & Punchy",
            headline = "Building scalable intelligence: Full-stack AI and cloud engineering driven by performance.",
            emphasis = "Speed & Performance"
        ),
        DeveloperPersona(
            id = "developer",
            title = "Developer-Focused",
            headline = "Architecting scalable data pipelines, autonomous agents, and cloud-native backends for the next generation of AI.",
            emphasis = "Pipelines & Autonomous Agents"
        ),
        DeveloperPersona(
            id = "outcome",
            title = "Outcome-Focused",
            headline = "Transforming complex data structures and algorithmic research into scalable, user-centric cloud applications.",
            emphasis = "Applied Research & Cloud Apps"
        )
    )

    val quickStats = listOf(
        ProjectMetric("Projects Built", "18+"),
        ProjectMetric("P99 Latency", "< 5ms"),
        ProjectMetric("Test Coverage", "94% CI/CD"),
        ProjectMetric("Edge Security", "Cloudflare WAF")
    )

    val projects: List<Project> = listOf(
        Project(
            id = "edge-tokenvault",
            title = "Cloudflare EdgeShield & TokenVault",
            subtitle = "Zero-trust tokenization pipeline & Cloudflare Turnstile edge verification",
            category = ProjectCategory.CLOUD,
            tags = listOf("Cloudflare Workers", "Tokenization", "Turnstile", "HMAC-SHA256", "WAF", "Zero-Trust"),
            metrics = listOf(
                ProjectMetric("Edge Latency", "1.2ms"),
                ProjectMetric("Token Security", "FIPS 140-2"),
                ProjectMetric("Bot Block Rate", "99.8%")
            ),
            summary = "An ultra-fast edge security and data tokenization layer operating on Cloudflare Workers and Turnstile bot verification to sanitize and tokenize sensitive payloads before hitting origin servers.",
            problemStatement = "Exposing raw PII, sensitive prompts, and session tokens directly across distributed microservices introduces GDPR/PCI non-compliance risks and leaves origin servers vulnerable to volumetric DDoS and automated credential stuffing.",
            architecture = "Cloudflare Edge Workers intercept incoming traffic at 300+ global edge locations. Cloudflare Turnstile executes non-interactive human verification tokens. Payloads undergo reversible or format-preserving tokenization (FPE) using hardware-derived AES-256-SIV before internal routing.",
            solution = "A unified edge gateway enforcing token rotation, rate-limiting, Cloudflare WAF custom rules, and zero-knowledge surrogate keys with cryptographic tamper detection.",
            results = "Shielded origin compute infrastructure from 100% of malicious bot traffic, dropped edge tokenization latency to 1.2ms P95, and achieved seamless SOC2/PCI data isolation.",
            codeSnippet = """
// Cloudflare Edge Worker Turnstile & Tokenizer
export default {
    async fetch(request, env) {
        // 1. Verify Cloudflare Turnstile Token
        const token = request.headers.get("cf-turnstile-response");
        const turnstileOk = await verifyTurnstile(token, env.TURNSTILE_SECRET);
        if (!turnstileOk) {
            return new Response("Forbidden: Bot verification failed", { status: 403 });
        }

        // 2. Format-Preserving Edge Tokenization
        const body = await request.json();
        const tokenizedPayload = await cryptoTokenize(body.sensitiveData, env.AES_KEY);

        return fetch("https://origin.internal.mesh/process", {
            method: "POST",
            body: JSON.stringify({ token: tokenizedPayload, edge_colocation: request.cf.colo })
        });
    }
}
            """.trimIndent(),
            githubUrl = "https://github.com/sathvik/cloudflare-tokenvault",
            liveDemoId = "cloudflare_token",
            isFeatured = true,
            starsCount = 380,
            forksCount = 84
        ),
        Project(
            id = "factforge",
            title = "FactForge AI Verification Engine",
            subtitle = "Autonomous hallucination mitigation & knowledge graph RAG validator",
            category = ProjectCategory.AI_ML,
            tags = listOf("Python", "FastAPI", "Vector DB", "Knowledge Graph", "HNSW", "Docker"),
            metrics = listOf(
                ProjectMetric("Factual Precision", "99.4%"),
                ProjectMetric("Verification Time", "< 80ms"),
                ProjectMetric("Token Savings", "74%")
            ),
            summary = "A production-grade dual-tier validation pipeline combining dense semantic vector search with deterministic knowledge graphs to eliminate LLM hallucinations at wire speed.",
            problemStatement = "Generative LLM systems hallucinate critical citations, numerical statistics, and legal clauses in 12-25% of enterprise queries, blocking enterprise deployment.",
            architecture = "Input prompts pass through an HNSW vector retriever for dense semantic grounding, cross-verified against a Neo4j/NetworkX knowledge graph triples index before returning consensus confidence scores.",
            solution = "A lightweight microservice with sub-80ms P95 latency that annotates AI responses with verifiable truth scores and source entity lineage citations.",
            results = "Increased factual accuracy to 99.4% across 50,000 synthetic test benchmarks while shrinking validation overhead by 74% compared to full-model re-prompting.",
            codeSnippet = """
async def verify_statement(claim: str, context_triples: list[Triple]) -> VerificationResult:
    # 1. Dense Semantic Anchor
    claim_embedding = await embedding_engine.embed(claim)
    relevant_nodes = await vector_index.search(claim_embedding, top_k=5)

    # 2. Graph Traversal Consistency Check
    graph_confidence = knowledge_graph.verify_path(claim, relevant_nodes)
    if graph_confidence >= 0.92:
        return VerificationResult(status="VERIFIED", confidence=graph_confidence)

    return VerificationResult(status="UNCERTAIN", flag="Requires human review")
            """.trimIndent(),
            githubUrl = "https://github.com/sathvik/factforge-ai",
            liveDemoId = "factforge",
            isFeatured = true,
            starsCount = 524,
            forksCount = 112
        ),
        Project(
            id = "nexusflow",
            title = "NexusFlow Distributed Mesh",
            subtitle = "High-throughput event streaming & adaptive load balancer with consistent hashing",
            category = ProjectCategory.CLOUD,
            tags = listOf("Kotlin", "gRPC", "Consistent Hashing", "Coroutines", "Docker", "Redis"),
            metrics = listOf(
                ProjectMetric("Throughput", "120K req/s"),
                ProjectMetric("P99 Latency", "3.8ms"),
                ProjectMetric("Failover", "< 150ms")
            ),
            summary = "An ultra-low latency distributed request distribution and caching mesh built for microsecond response times and seamless node failure rebalancing.",
            problemStatement = "Traditional round-robin load balancers introduce severe hot-spotting during traffic surges and cause massive cache invalidations whenever a worker node crashes or scales horizontally.",
            architecture = "Implemented consistent hashing ring with virtual nodes (vnodes) to guarantee uniform distribution. Integrated heartbeat health checks, token bucket rate limiting, and an adaptive circuit breaker written with Kotlin Coroutines channels.",
            solution = "A lightweight autonomous mesh daemon that routes TCP/gRPC traffic with zero garbage-collector pause bottlenecks, keeping working memory footprint under 48MB per instance.",
            results = "Maintained 99.999% availability during simulated network partitions, reduced cross-datacenter cache invalidation penalties by 82%, and saturated 10Gbps interfaces with sub-5ms P99 latency.",
            codeSnippet = """
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
            """.trimIndent(),
            githubUrl = "https://github.com/sathvik/nexusflow-mesh",
            liveDemoId = "cloudmesh",
            isFeatured = true,
            starsCount = 284,
            forksCount = 67
        ),
        Project(
            id = "algomatrix",
            title = "AlgoMatrix Engine 3D",
            subtitle = "Zero-allocation real-time pathfinding & sorting visualizer built in Jetpack Compose",
            category = ProjectCategory.MOBILE,
            tags = listOf("Jetpack Compose", "Custom Canvas", "A* Search", "Dijkstra", "Performance"),
            metrics = listOf(
                ProjectMetric("Frame Rate", "120 FPS"),
                ProjectMetric("Allocations", "0 in Draw"),
                ProjectMetric("Algorithms", "8 Implemented")
            ),
            summary = "A visual algorithm explorer and pathfinding sandbox engineered in pure Jetpack Compose Canvas, demonstrating how to eliminate GC jitter in rendering intensive mobile apps.",
            problemStatement = "Many algorithmic visualizers suffer from dropped frames and UI stutter due to creating state objects and Paint instances inside the rendering loop, causing frequent Garbage Collection cycles.",
            architecture = "Built with a state-driven MVI pipeline where algorithm execution steps yield state through Kotlin Coroutines Flow. Reusable DrawScope primitive buffers and pooled Point arrays prevent dynamic allocations.",
            solution = "Implemented A* with Euclidean & Manhattan heuristics, Dijkstra, QuickSort with Dutch National Flag partitioning, and MergeSort with step-by-step playback controls, scrubbers, and variable execution speeds.",
            results = "Sustains rock-solid 120 FPS on high refresh rate displays with sub-1ms render passes. Selected as Featured Open Source showcase.",
            codeSnippet = """
fun findPathAStar(grid: Grid, start: Point, target: Point): List<Point> {
    val openSet = PriorityQueue<PathNode>(compareBy { it.fScore })
    val gScores = mutableMapOf(start to 0f)
    openSet.add(PathNode(start, 0f, heuristic(start, target)))

    while (openSet.isNotEmpty()) {
        val current = openSet.poll() ?: break
        if (current.point == target) return reconstructPath(current)

        for (neighbor in grid.getWalkableNeighbors(current.point)) {
            val tentativeG = gScores.getOrDefault(current.point, Float.MAX_VALUE) + 1f
            if (tentativeG < gScores.getOrDefault(neighbor, Float.MAX_VALUE)) {
                gScores[neighbor] = tentativeG
                val fScore = tentativeG + heuristic(neighbor, target)
                openSet.add(PathNode(neighbor, tentativeG, fScore, current))
            }
        }
    }
    return emptyList()
}
            """.trimIndent(),
            githubUrl = "https://github.com/sathvik/algomatrix-compose",
            liveDemoId = "algomatrix",
            isFeatured = true,
            starsCount = 412,
            forksCount = 98
        ),
        Project(
            id = "pulsefit",
            title = "PulseFit Pro Architecture",
            subtitle = "Offline-first biometric motion & sensor analytics engine with MVI & Room",
            category = ProjectCategory.MOBILE,
            tags = listOf("Android", "Kotlin", "Room DB", "Flow", "Clean Arch", "Sensors"),
            metrics = listOf(
                ProjectMetric("Offline Sync", "100%"),
                ProjectMetric("DB Read Time", "1.4ms"),
                ProjectMetric("Test Coverage", "94%")
            ),
            summary = "Production-grade health telemetry and sensor capture system featuring conflict-free offline replication, battery-optimized foreground services, and modular clean architecture.",
            problemStatement = "Capturing continuous 50Hz IMU accelerometer and heart rate data drains mobile batteries and clogs the main UI thread during SQLite disk writes.",
            architecture = "Dual-buffer Ring Buffer in memory with batched Room WAL transactions every 2 seconds. WorkManager for intelligent background syncing when connected to Wi-Fi and charging.",
            solution = "Clean Architecture with separate Domain, Data, and Presentation modules. Strict unidirectional data flow (UDF) with Immutable UI states and single-event Channel channels for snackbars.",
            results = "Achieved 42% battery savings over naive sensor polling, 0 ANRs across 10,000 simulated test cycles, and flawless offline recovery.",
            codeSnippet = """
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
            """.trimIndent(),
            githubUrl = "https://github.com/sathvik/pulsefit-pro",
            liveDemoId = "codecraft",
            isFeatured = false,
            starsCount = 189,
            forksCount = 45
        )
    )

    val tradeOffs: List<TradeOffDecision> = listOf(
        TradeOffDecision(
            choice = "Cloudflare Edge Workers + Turnstile",
            alternative = "Origin reCAPTCHA v3 & Server Firewall",
            rationale = "Cloudflare Turnstile is non-intrusive to humans while Workers terminate malicious bot queries at the edge in 1.2ms without burdening origin compute.",
            benchmarkImpact = "Zero origin CPU spent on bot mitigation, 99.8% bot rejection rate."
        ),
        TradeOffDecision(
            choice = "FAISS + Local HNSW Index",
            alternative = "Pinecone / Cloud Vector SaaS",
            rationale = "Local in-memory HNSW eliminates external network roundtrips, reducing query evaluation overhead from 65ms down to 1.8ms.",
            benchmarkImpact = "97% reduction in query latency, zero per-query vendor bills."
        ),
        TradeOffDecision(
            choice = "Consistent Hashing with Virtual Nodes",
            alternative = "DNS Round-Robin / Weighted Random",
            rationale = "Prevents catastrophic cache invalidation stampedes when nodes scale up or fail; only K/N keys get remapped.",
            benchmarkImpact = "Maintained 84% cache hit rates even during simulated 50% cluster node crashes."
        ),
        TradeOffDecision(
            choice = "Room SQLite WAL with Ring Buffer",
            alternative = "Direct SQLite synchronous writes",
            rationale = "Decouples sensor collection at 50Hz from disk I/O, writing in batched 2-second transactions on background Dispatchers.IO.",
            benchmarkImpact = "Eliminated 100% of UI frame drops and saved 42% battery."
        )
    )

    val apiEndpoints: List<ApiEndpointSchema> = listOf(
        ApiEndpointSchema(
            method = "POST",
            endpoint = "/v1/edge/tokenize",
            description = "Tokenizes sensitive payload via Cloudflare Edge Worker with Turnstile verification.",
            requestBody = """{"data": "user_secret_credentials", "turnstile_token": "0.cf-valid-token"}""",
            responseBody = """{"status": "TOKENIZED", "token_surrogate": "tok_sec_984f_e281", "edge_colocation": "MAA", "turnstile_score": 0.99}"""
        ),
        ApiEndpointSchema(
            method = "POST",
            endpoint = "/v1/factforge/verify",
            description = "Validates an assertion or LLM response against the verifiable Knowledge Graph.",
            requestBody = """{"claim": "Sathvik published research on high-throughput algorithmic scheduling.", "threshold": 0.90}""",
            responseBody = """{"status": "VERIFIED", "confidence": 0.984, "latency_ms": 42.1, "sources": ["PVPSIT Engineering Archives"]}"""
        ),
        ApiEndpointSchema(
            method = "POST",
            endpoint = "/v1/mesh/route",
            description = "Routes a distributed request across active mesh virtual nodes via consistent hashing.",
            requestBody = """{"request_key": "user_sess_89412", "payload_size_kb": 12}""",
            responseBody = """{"routed_node": "node-alpha-vnode-42", "node_load": "24%", "estimated_p99_ms": 3.2}"""
        ),
        ApiEndpointSchema(
            method = "GET",
            endpoint = "/v1/telemetry/health",
            description = "Returns real-time cluster health, cache hit ratios, and CI/CD coverage stats.",
            requestBody = """{}""",
            responseBody = """{"status": "HEALTHY", "cloudflare_waf": "ACTIVE", "test_coverage": "94%", "uptime": "99.999%"}"""
        )
    )

    val architectureNodes: List<ArchitectureNode> = listOf(
        ArchitectureNode("Client Request / Agent", "Origin traffic", "Jetpack Compose / Mobile", false),
        ArchitectureNode("Cloudflare Edge Worker & WAF", "DDoS & Turnstile Gate", "Cloudflare V8 Edge", true),
        ArchitectureNode("EdgeShield Tokenizer", "Zero-Trust Tokenization", "AES-256-SIV / HMAC", true),
        ArchitectureNode("NexusFlow Distributed Mesh", "Load Balancer & Auth", "Consistent Hash Ring", true),
        ArchitectureNode("FactForge Triples Verifier", "Hallucination Defense", "Graph Traversal", true),
        ArchitectureNode("Distributed Storage / Room", "Offline-first Cache", "WAL SQLite / Docker", false)
    )

    val skills: List<SkillItem> = listOf(
        // Mobile
        SkillItem(
            id = "android_compose",
            name = "Android & Jetpack Compose",
            category = SkillCategory.MOBILE,
            proficiencyPercent = 95,
            experienceYears = "4+ years",
            description = "Declarative UI, State Hoisting, Custom Canvas, Animations, Window Size Classes, Performance profiling with Macrobenchmark.",
            libraries = listOf("Compose M3", "Coroutines", "Flow", "ViewModel", "Room", "Hilt", "Navigation")
        ),
        SkillItem(
            id = "kotlin_advanced",
            name = "Modern Kotlin & Coroutines",
            category = SkillCategory.MOBILE,
            proficiencyPercent = 94,
            experienceYears = "4+ years",
            description = "Structured concurrency, SharedFlow, StateFlow, Channels, inline value classes, functional idioms, DSLs.",
            libraries = listOf("Kotlinx Coroutines", "Kotlinx Serialization", "Ktor Client", "Arrow-kt")
        ),
        SkillItem(
            id = "cloudflare_edge",
            name = "Cloudflare Edge & Security Services",
            category = SkillCategory.BACKEND,
            proficiencyPercent = 91,
            experienceYears = "3+ years",
            description = "Cloudflare Workers, Turnstile non-interactive bot verification, WAF rate limiting, zero-trust tokenization, KV store.",
            libraries = listOf("Cloudflare Workers", "Turnstile", "WAF Rules", "Edge KV", "TLS 1.3")
        ),
        SkillItem(
            id = "distributed_systems",
            name = "Distributed Systems & gRPC",
            category = SkillCategory.BACKEND,
            proficiencyPercent = 88,
            experienceYears = "3+ years",
            description = "Consistent hashing, circuit breakers, consensus mechanisms, protocol buffers, microservices.",
            libraries = listOf("gRPC", "Protobuf", "Kafka", "Redis", "Docker", "PostgreSQL")
        ),
        SkillItem(
            id = "dsa",
            name = "Algorithms & Data Structures",
            category = SkillCategory.CS_CORE,
            proficiencyPercent = 93,
            experienceYears = "Academic + Practical",
            description = "Graph traversal (A*, Dijkstra), Dynamic Programming, Spatial Indexing (QuadTrees), Tree structures.",
            libraries = listOf("Graph Theory", "Greedy", "Divide & Conquer", "Tree Decompositions")
        ),
        SkillItem(
            id = "git_ci_cd",
            name = "Git, CI/CD & Automation",
            category = SkillCategory.DEVOPS,
            proficiencyPercent = 90,
            experienceYears = "4+ years",
            description = "GitHub Actions workflows, automated testing, Gradle build scripts (Kotlin DSL), APK packaging.",
            libraries = listOf("GitHub Actions", "Gradle KTS", "Docker", "Fastlane", "Bash")
        )
    )

    val timeline: List<TimelineItem> = listOf(
        TimelineItem(
            id = "work_1",
            title = "Lead Mobile & Systems Engineer",
            organization = "High-Performance Tech Labs",
            period = "2024 - Present",
            location = "Hyderabad / Remote",
            type = ExperienceType.WORK,
            highlights = listOf(
                "Architected core Android client serving 100K+ users with 99.98% crash-free sessions using Jetpack Compose and Kotlin Coroutines.",
                "Integrated Cloudflare Workers & Turnstile edge verification, shielding origin APIs from 99.8% of malicious bot traffic.",
                "Engineered zero-allocation Canvas chart engines rendering 60-120 FPS data feeds with zero dropped frames.",
                "Collaborated with backend engineers to design gRPC-web protocols that slashed payload bandwidth by 44%."
            ),
            techStack = listOf("Jetpack Compose", "Kotlin", "Cloudflare Workers", "Turnstile", "Room", "gRPC"),
            isCurrent = true
        ),
        TimelineItem(
            id = "work_2",
            title = "Software Engineer Intern (Android & Backend)",
            organization = "NextGen Digital Solutions",
            period = "2023 - 2024",
            location = "Vijayawada, India",
            type = ExperienceType.WORK,
            highlights = listOf(
                "Migrated legacy XML Android codebases to 100% Jetpack Compose, cutting UI codebase size by 35%.",
                "Created local Room SQLite caching layers reducing network requests by 58% on low-bandwidth connections.",
                "Authored 150+ unit and Robolectric tests, lifting overall repository test coverage to 89%."
            ),
            techStack = listOf("Android", "Room", "MVI", "Ktor Client", "Kotlin Flow")
        ),
        TimelineItem(
            id = "edu_1",
            title = "B.Tech in Computer Science & Engineering",
            organization = "Prasad V. Potluri Siddhartha Institute of Technology (PVPSIT)",
            period = "2021 - 2025",
            location = "Vijayawada, AP",
            type = ExperienceType.EDUCATION,
            highlights = listOf(
                "Top 5% of class in Computer Science & Engineering with strong coursework in Distributed Systems, Operating Systems, DBMS, and Design of Algorithms.",
                "Lead Organizer of Department Technical Symposium & Coding Hackathons with 500+ participants.",
                "Published research on high-throughput algorithmic scheduling and real-time mobile visual rendering."
            ),
            techStack = listOf("Data Structures", "Algorithms", "Computer Networks", "Database Systems", "OS")
        ),
        TimelineItem(
            id = "award_1",
            title = "1st Place Winner - National Smart Engineering Hackathon",
            organization = "National Tech Summit",
            period = "2024",
            location = "Bengaluru",
            type = ExperienceType.HACKATHON,
            highlights = listOf(
                "Built an emergency biometric alert system for first responders in 36 hours from scratch.",
                "Implemented peer-to-peer mesh synchronization with Wi-Fi Direct and local cryptographic handshakes.",
                "Awarded 1st Prize out of 120+ collegiate and professional teams."
            ),
            techStack = listOf("Android", "Wi-Fi Direct", "Crypto", "Jetpack Compose")
        ),
        TimelineItem(
            id = "award_2",
            title = "Best Architecture Award - Open Source Hackfest",
            organization = "Developer Community",
            period = "2023",
            location = "Virtual",
            type = ExperienceType.HACKATHON,
            highlights = listOf(
                "Designed and launched 'AlgoMatrix Engine' which gained 400+ stars within two weeks of open source release."
            ),
            techStack = listOf("Kotlin", "Canvas", "Algorithms")
        )
    )

    val testimonials: List<Testimonial> = listOf(
        Testimonial(
            id = "t1",
            author = "Dr. K. Ramesh",
            title = "Professor & Head of CSE",
            relationship = "Academic Mentor at PVPSIT",
            quote = "Sathvik stands out as an exceptional engineer with rare mastery over both low-level algorithmic efficiency and high-level architectural patterns. His drive to build top-tier software is inspiring.",
            rating = 5
        ),
        Testimonial(
            id = "t2",
            author = "Vikram Reddy",
            title = "Principal Engineering Architect",
            relationship = "Engineering Lead at Tech Labs",
            quote = "Sathvik doesn't just write code; he crafts bulletproof systems. His work on Cloudflare edge verification, tokenization, and our distributed mesh resolved bottleneck issues that had persisted for months.",
            rating = 5
        ),
        Testimonial(
            id = "t3",
            author = "Ananya Sharma",
            title = "Senior Product Manager",
            relationship = "Collaborator on Mobile Products",
            quote = "Collaborating with Sathvik was a dream. He translates complex security and cloud requirements into fluid, beautifully responsive user experiences, always anticipating edge cases before they happen.",
            rating = 5
        )
    )

    val achievements: List<PortfolioAchievement> = listOf(
        PortfolioAchievement("ach_1", "Code Explorer", "Discovered the architectural breakdown of all projects", "🔍", true),
        PortfolioAchievement("ach_2", "Algorithm Runner", "Executed a live simulation in the interactive demo playground", "⚡", false),
        PortfolioAchievement("ach_3", "Terminal Hacker", "Discovered and executed a command in the secret developer terminal", "💻", false),
        PortfolioAchievement("ach_4", "Edge Security Master", "Tested Cloudflare Turnstile verification and tokenization sandbox", "🛡️", false)
    )
}
