package com.dansplugins.factionsystem

import com.dansplugins.factionsystem.chat.JooqMfChatChannelMessageRepository
import com.dansplugins.factionsystem.chat.MfChatChannelMessageRepository
import com.dansplugins.factionsystem.chat.MfChatService
import com.dansplugins.factionsystem.claim.JooqMfClaimedChunkRepository
import com.dansplugins.factionsystem.claim.MfClaimService
import com.dansplugins.factionsystem.claim.MfClaimedChunkRepository
import com.dansplugins.factionsystem.command.accessors.MfAccessorsCommand
import com.dansplugins.factionsystem.command.duel.MfDuelCommand
import com.dansplugins.factionsystem.command.faction.MfFactionCommand
import com.dansplugins.factionsystem.command.gate.MfGateCommand
import com.dansplugins.factionsystem.command.lock.MfLockCommand
import com.dansplugins.factionsystem.command.power.MfPowerCommand
import com.dansplugins.factionsystem.command.unlock.MfUnlockCommand
import com.dansplugins.factionsystem.duel.JooqMfDuelInviteRepository
import com.dansplugins.factionsystem.duel.JooqMfDuelRepository
import com.dansplugins.factionsystem.duel.MfDuelId
import com.dansplugins.factionsystem.duel.MfDuelInviteRepository
import com.dansplugins.factionsystem.duel.MfDuelRepository
import com.dansplugins.factionsystem.duel.MfDuelService
import com.dansplugins.factionsystem.dynmap.MfDynmapService
import com.dansplugins.factionsystem.faction.JooqMfFactionRepository
import com.dansplugins.factionsystem.faction.MfFactionRepository
import com.dansplugins.factionsystem.faction.MfFactionService
import com.dansplugins.factionsystem.faction.flag.MfFlags
import com.dansplugins.factionsystem.faction.permission.MfFactionPermissions
import com.dansplugins.factionsystem.gate.JooqMfGateCreationContextRepository
import com.dansplugins.factionsystem.gate.JooqMfGateRepository
import com.dansplugins.factionsystem.gate.MfGate
import com.dansplugins.factionsystem.gate.MfGateCreationContextRepository
import com.dansplugins.factionsystem.gate.MfGateRepository
import com.dansplugins.factionsystem.gate.MfGateService
import com.dansplugins.factionsystem.gate.MfGateStatus.CLOSING
import com.dansplugins.factionsystem.gate.MfGateStatus.OPENING
import com.dansplugins.factionsystem.interaction.JooqMfInteractionStatusRepository
import com.dansplugins.factionsystem.interaction.MfInteractionService
import com.dansplugins.factionsystem.interaction.MfInteractionStatusRepository
import com.dansplugins.factionsystem.lang.Language
import com.dansplugins.factionsystem.law.JooqMfLawRepository
import com.dansplugins.factionsystem.law.MfLawRepository
import com.dansplugins.factionsystem.law.MfLawService
import com.dansplugins.factionsystem.legacy.MfLegacyDataMigrator
import com.dansplugins.factionsystem.listener.AreaEffectCloudApplyListener
import com.dansplugins.factionsystem.listener.AsyncPlayerChatListener
import com.dansplugins.factionsystem.listener.AsyncPlayerPreLoginListener
import com.dansplugins.factionsystem.listener.BlockBreakListener
import com.dansplugins.factionsystem.listener.BlockExplodeListener
import com.dansplugins.factionsystem.listener.BlockPistonExtendListener
import com.dansplugins.factionsystem.listener.BlockPistonRetractListener
import com.dansplugins.factionsystem.listener.BlockPlaceListener
import com.dansplugins.factionsystem.listener.CreatureSpawnListener
import com.dansplugins.factionsystem.listener.EntityDamageByEntityListener
import com.dansplugins.factionsystem.listener.EntityDamageListener
import com.dansplugins.factionsystem.listener.EntityExplodeListener
import com.dansplugins.factionsystem.listener.InventoryMoveItemListener
import com.dansplugins.factionsystem.listener.LingeringPotionSplashListener
import com.dansplugins.factionsystem.listener.PlayerBucketListener
import com.dansplugins.factionsystem.listener.PlayerDeathListener
import com.dansplugins.factionsystem.listener.PlayerInteractEntityListener
import com.dansplugins.factionsystem.listener.PlayerInteractListener
import com.dansplugins.factionsystem.listener.PlayerJoinListener
import com.dansplugins.factionsystem.listener.PlayerMoveListener
import com.dansplugins.factionsystem.listener.PlayerQuitListener
import com.dansplugins.factionsystem.listener.PlayerTeleportListener
import com.dansplugins.factionsystem.listener.PotionSplashListener
import com.dansplugins.factionsystem.locks.JooqMfLockRepository
import com.dansplugins.factionsystem.locks.MfLockRepository
import com.dansplugins.factionsystem.locks.MfLockService
import com.dansplugins.factionsystem.locks.MfRpkLockService
import com.dansplugins.factionsystem.notification.MfNotificationService
import com.dansplugins.factionsystem.notification.mailboxes.MailboxesNotificationService
import com.dansplugins.factionsystem.notification.noop.NoOpNotificationService
import com.dansplugins.factionsystem.notification.rpkit.RpkNotificationService
import com.dansplugins.factionsystem.placeholder.MedievalFactionsPlaceholderExpansion
import com.dansplugins.factionsystem.player.JooqMfPlayerRepository
import com.dansplugins.factionsystem.player.MfPlayerId
import com.dansplugins.factionsystem.player.MfPlayerRepository
import com.dansplugins.factionsystem.player.MfPlayerService
import com.dansplugins.factionsystem.potion.MfPotionService
import com.dansplugins.factionsystem.relationship.JooqMfFactionRelationshipRepository
import com.dansplugins.factionsystem.relationship.MfFactionRelationshipRepository
import com.dansplugins.factionsystem.relationship.MfFactionRelationshipService
import com.dansplugins.factionsystem.service.Services
import com.dansplugins.factionsystem.teleport.MfTeleportService
import com.google.gson.Gson
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ChatColor.GREEN
import net.md_5.bungee.api.ChatMessageType.ACTION_BAR
import net.md_5.bungee.api.chat.TextComponent
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
import org.bukkit.NamespacedKey
import org.bukkit.boss.KeyedBossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.flywaydb.core.Flyway
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import preponderous.ponder.minecraft.bukkit.plugin.registerListeners
import java.time.Duration
import java.time.Instant
import java.time.LocalTime
import java.util.logging.Level.SEVERE
import javax.sql.DataSource
import kotlin.math.floor
import kotlin.math.roundToInt

open class MedievalFactions : JavaPlugin() {

    private lateinit var dataSource: DataSource

    lateinit var flags: MfFlags
    lateinit var factionPermissions: MfFactionPermissions
    lateinit var services: Services
    lateinit var language: Language

    override fun onEnable() {
        val migrator = MfLegacyDataMigrator(this)
        if (config.getString("version")?.startsWith("v4.") == true) {
            migrator.backup()
            saveDefaultConfig()
            reloadConfig()
            config.options().copyDefaults(true)
            config.set("migrateMf4", true)
            saveConfig()
            logger.warning("Shutting down the server due to Medieval Factions 4 migration.")
            logger.warning("If you have a database, please configure it before starting the server again.")
            logger.warning("Otherwise, simply start your server again to begin migration.")
            server.shutdown()
            return
        }

        saveDefaultConfig()
        config.options().copyDefaults(true)
        config.set("version", description.version)
        saveConfig()

        language = Language(this, config.getString("language") ?: "en-US")

        Class.forName("org.h2.Driver")
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = config.getString("database.url")
        val databaseUsername = config.getString("database.username")
        if (databaseUsername != null) {
            hikariConfig.username = databaseUsername
        }
        val databasePassword = config.getString("database.password")
        if (databasePassword != null) {
            hikariConfig.password = databasePassword
        }
        dataSource = HikariDataSource(hikariConfig)
        val oldClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = classLoader
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:com/dansplugins/factionsystem/db/migration")
            .table("mf_schema_history")
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .validateOnMigrate(false)
            .load()
        flyway.migrate()
        Thread.currentThread().contextClassLoader = oldClassLoader

        System.setProperty("org.jooq.no-logo", "true")
        System.setProperty("org.jooq.no-tips", "true")

        val dialect = config.getString("database.dialect")?.let(SQLDialect::valueOf)
        val jooqSettings = Settings().withRenderSchema(false)
        val dsl = DSL.using(
            dataSource,
            dialect,
            jooqSettings
        )

        flags = MfFlags(this)
        factionPermissions = MfFactionPermissions(this)

        val gson = Gson()
        val playerRepository: MfPlayerRepository = JooqMfPlayerRepository(this, dsl)
        val dynmapService = if (server.pluginManager.getPlugin("dynmap") != null && config.getBoolean("dynmap.enableDynmapIntegration")) {
            MfDynmapService(this)
        } else {
            null
        }
        val factionRepository: MfFactionRepository = JooqMfFactionRepository(this, dsl, gson)
        val lawRepository: MfLawRepository = JooqMfLawRepository(dsl)
        val factionRelationshipRepository: MfFactionRelationshipRepository = JooqMfFactionRelationshipRepository(dsl)
        val claimedChunkRepository: MfClaimedChunkRepository = JooqMfClaimedChunkRepository(dsl)
        val lockRepository: MfLockRepository = JooqMfLockRepository(dsl)
        val interactionStatusRepository: MfInteractionStatusRepository = JooqMfInteractionStatusRepository(dsl)
        val gateRepository: MfGateRepository = JooqMfGateRepository(this, dsl)
        val gateCreationContextRepository: MfGateCreationContextRepository = JooqMfGateCreationContextRepository(dsl)
        val chatMessageRepository: MfChatChannelMessageRepository = JooqMfChatChannelMessageRepository(dsl)
        val duelRepository: MfDuelRepository = JooqMfDuelRepository(dsl)
        val duelInviteRepository: MfDuelInviteRepository = JooqMfDuelInviteRepository(dsl)

        val playerService = MfPlayerService(this, playerRepository)
        val factionService = MfFactionService(this, factionRepository)
        val lawService = MfLawService(lawRepository)
        val factionRelationshipService = MfFactionRelationshipService(this, factionRelationshipRepository)
        val claimService = MfClaimService(this, claimedChunkRepository)
        val lockService = MfLockService(this, lockRepository)
        val interactionService = MfInteractionService(interactionStatusRepository)
        val notificationService = setupNotificationService()
        val gateService = MfGateService(this, gateRepository, gateCreationContextRepository)
        val chatService = MfChatService(this, chatMessageRepository)
        val duelService = MfDuelService(this, duelRepository, duelInviteRepository)
        val potionService = MfPotionService(this)
        val teleportService = MfTeleportService(this)

        services = Services(
            playerService,
            factionService,
            lawService,
            factionRelationshipService,
            claimService,
            lockService,
            interactionService,
            notificationService,
            gateService,
            chatService,
            duelService,
            potionService,
            teleportService,
            dynmapService
        )
        setupRpkLockService()

        val metrics = Metrics(this, 8929)
        metrics.addCustomChart(
            SimplePie("language_used") {
                config.getString("language")
            }
        )
        metrics.addCustomChart(
            SimplePie("database_dialect") {
                config.getString("database.dialect")
            }
        )
        metrics.addCustomChart(
            SimplePie("average_claims") {
                factionService.factions
                    .map {
                        claimService.getClaims(it.id).size
                    }
                    .average().roundToInt().toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("total_claims") {
                factionService.factions.sumOf {
                    claimService.getClaims(it.id).size
                }.toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("initial_power") {
                config.getDouble("players.initialPower").toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("max_power") {
                config.getDouble("players.maxPower").toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("hours_to_reach_max_power") {
                config.getDouble("players.hoursToReachMaxPower").toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("hours_to_reach_min_power") {
                config.getDouble("players.hoursToReachMinPower").toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("limit_land") {
                config.getBoolean("factions.limitLand").toString()
            }
        )
        metrics.addCustomChart(
            SimplePie("allow_neutrality") {
                config.getBoolean("factions.allowNeutrality").toString()
            }
        )

        if (config.getBoolean("migrateMf4")) {
            migrator.migrate()
            config.set("migrateMf4", null)
            saveConfig()
        }

        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            MedievalFactionsPlaceholderExpansion(this).register()
        }

        if (config.getBoolean("dynmap.onlyRenderTerritoriesUponStartup")) {
            logger.info(language["DynmapOnlyRenderTerritoriesUponStartupEnabled"])
        }

        if (dynmapService != null) {
            factionService.factions.forEach { faction ->
                dynmapService.scheduleUpdateClaims(faction)
            }
        }

        registerListeners(
            AreaEffectCloudApplyListener(this),
            AsyncPlayerChatListener(this),
            AsyncPlayerPreLoginListener(this),
            BlockBreakListener(this),
            BlockExplodeListener(this),
            BlockPistonExtendListener(this),
            BlockPistonRetractListener(this),
            BlockPlaceListener(this),
            CreatureSpawnListener(this),
            EntityDamageByEntityListener(this),
            EntityDamageListener(this),
            EntityExplodeListener(this),
            InventoryMoveItemListener(this),
            LingeringPotionSplashListener(this),
            PlayerBucketListener(this),
            PlayerDeathListener(this),
            PlayerInteractListener(this),
            PlayerJoinListener(this),
            PlayerMoveListener(this),
            PlayerQuitListener(this),
            PlayerTeleportListener(this),
            PotionSplashListener(this),
            PlayerInteractEntityListener(this)
        )

        getCommand("faction")?.setExecutor(MfFactionCommand(this))
        getCommand("lock")?.setExecutor(MfLockCommand(this))
        getCommand("unlock")?.setExecutor(MfUnlockCommand(this))
        getCommand("accessors")?.setExecutor(MfAccessorsCommand(this))
        getCommand("power")?.setExecutor(MfPowerCommand(this))
        getCommand("gate")?.setExecutor(MfGateCommand(this))
        getCommand("duel")?.setExecutor(MfDuelCommand(this))

        server.scheduler.scheduleSyncRepeatingTask(this, {
            val onlinePlayers = server.onlinePlayers
            val onlineMfPlayerIds = onlinePlayers.map(MfPlayerId.Companion::fromBukkitPlayer)
            val disbandZeroPowerFactions = config.getBoolean("factions.zeroPowerFactionsGetDisbanded")
            val initialPower = config.getDouble("players.initialPower")
            server.scheduler.runTaskAsynchronously(
                this,
                Runnable {
                    onPowerCycle(
                        onlineMfPlayerIds,
                        initialPower,
                        onlinePlayers,
                        disbandZeroPowerFactions
                    )
                }
            )
        }, (15 - (LocalTime.now().minute % 15)) * 60 * 20L, 18000L)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            val gates = gateService.gates
            gates.filter(MfGate::shouldOpen).forEach(MfGate::open)
            gates.filter(MfGate::shouldClose).forEach(MfGate::close)
        }, 20L, 20L)

        server.scheduler.scheduleSyncRepeatingTask(this, {
            gateService.getGatesByStatus(CLOSING).forEach(MfGate::continueClosing)
        }, 20L, 5L)
        server.scheduler.scheduleSyncRepeatingTask(this, {
            gateService.getGatesByStatus(OPENING).forEach(MfGate::continueOpening)
        }, 20L, 20L)

        val bossBars = mutableListOf<KeyedBossBar>()
        server.bossBars.forEach { bossBar ->
            if (bossBar.key.namespace.equals(name, ignoreCase = true)) {
                if (bossBar.key.key.startsWith("duel_")) {
                    val duelId = MfDuelId(bossBar.key.key.replaceFirst("duel_", ""))
                    val duel = duelService.getDuel(duelId)
                    if (duel == null) {
                        bossBars.add(bossBar)
                    }
                }
            }
        }
        bossBars.forEach { bossBar ->
            bossBar.removeAll()
            server.removeBossBar(bossBar.key)
        }

        server.scheduler.scheduleSyncRepeatingTask(this, {
            duelService.duels.forEach { duel ->
                if (Instant.now().isBefore(duel.endTime)) {
                    val bar = server.getBossBar(NamespacedKey(this, "duel_${duel.id.value}"))
                    bar?.progress = Duration.between(Instant.now(), duel.endTime).toMillis()
                        .toDouble() / Duration.parse(config.getString("duels.duration")).toMillis().toDouble()
                } else {
                    server.getBossBar(NamespacedKey(this, "duel_${duel.id.value}"))?.removeAll()
                    server.removeBossBar(NamespacedKey(this, "duel_${duel.id.value}"))
                    val notificationDistance = config.getInt("duels.notificationDistance")
                    val notificationDistanceSquared = notificationDistance * notificationDistance
                    val challengerBukkitPlayer = duel.challengerId.toBukkitPlayer().player
                    val nearbyPlayers = mutableSetOf<Player>()
                    if (challengerBukkitPlayer != null) {
                        challengerBukkitPlayer.activePotionEffects.clear()
                        challengerBukkitPlayer.fireTicks = 0
                        challengerBukkitPlayer.health = duel.challengerHealth
                        duel.challengerLocation?.toBukkitLocation()?.let(challengerBukkitPlayer::teleport)
                        nearbyPlayers += challengerBukkitPlayer.world.players
                            .filter { it.location.distanceSquared(challengerBukkitPlayer.location) <= notificationDistanceSquared }
                    }
                    val challengedBukkitPlayer = duel.challengedId.toBukkitPlayer().player
                    if (challengedBukkitPlayer != null) {
                        challengedBukkitPlayer.activePotionEffects.clear()
                        challengedBukkitPlayer.fireTicks = 0
                        challengedBukkitPlayer.health = duel.challengedHealth
                        duel.challengedLocation?.toBukkitLocation()?.let(challengedBukkitPlayer::teleport)
                        nearbyPlayers += challengedBukkitPlayer.world.players
                            .filter { it.location.distanceSquared(challengedBukkitPlayer.location) <= notificationDistanceSquared }
                    }
                    nearbyPlayers.forEach { notifiedPlayer ->
                        notifiedPlayer.sendMessage(
                            language[
                                "DuelTie",
                                duel.challengerId.toBukkitPlayer().name ?: language["UnknownPlayer"],
                                duel.challengedId.toBukkitPlayer().name ?: language["UnknownPlayer"]
                            ]
                        )
                    }
                    server.scheduler.runTaskAsynchronously(
                        this,
                        Runnable {
                            duelService.delete(duel.id).onFailure {
                                logger.log(SEVERE, "Failed to delete duel: ${it.reason.message}", it.reason.cause)
                                return@Runnable
                            }
                        }
                    )
                }
            }
        }, 20L, 20L)

        if (config.getBoolean("factions.actionBarTerritoryIndicator")) {
            server.scheduler.scheduleSyncRepeatingTask(this, {
                server.onlinePlayers.forEach { player ->
                    val chunk = player.location.chunk
                    val claim = claimService.getClaim(chunk)
                    val faction = claim?.let { factionService.getFaction(it.factionId) }
                    if (faction == null) {
                        player.spigot().sendMessage(
                            ACTION_BAR,
                            *TextComponent.fromLegacyText(
                                "${ChatColor.of(config.getString("wilderness.color"))}${language["Wilderness"]}"
                            )
                        )
                    } else {
                        player.spigot().sendMessage(
                            ACTION_BAR,
                            *TextComponent.fromLegacyText("${ChatColor.of(faction.flags[flags.color])}${faction.name}")
                        )
                    }
                }
            }, 5L, 20L)
        }
    }

    internal fun onPowerCycle(
        onlineMfPlayerIds: List<MfPlayerId>,
        initialPower: Double,
        onlinePlayers: Collection<Player>,
        disbandZeroPowerFactions: Boolean
    ) {
        val playerService = services.playerService
        val factionService = services.factionService

        val originalOnlinePlayerPower =
            onlineMfPlayerIds.associateWith { playerService.getPlayer(it)?.power ?: initialPower }
        playerService.updatePlayerPower(onlineMfPlayerIds).onFailure {
            logger.log(SEVERE, "Failed to update player power: ${it.reason.message}", it.reason.cause)
            return
        }
        val newOnlinePlayerPower =
            onlineMfPlayerIds.associateWith { playerService.getPlayer(it)?.power ?: initialPower }
        server.scheduler.runTask(
            this,
            Runnable {
                onlinePlayers.forEach { onlinePlayer ->
                    val playerId = MfPlayerId.fromBukkitPlayer(onlinePlayer)
                    val newPower = newOnlinePlayerPower[playerId] ?: initialPower
                    val originalPower = originalOnlinePlayerPower[playerId] ?: initialPower
                    val powerIncrease = floor(newPower).roundToInt() - floor(originalPower).roundToInt()
                    if (powerIncrease > 0) {
                        onlinePlayer.sendMessage("$GREEN${language["PowerIncreased", powerIncrease.toString()]}")
                    }
                }
            }
        )
        if (disbandZeroPowerFactions) {
            factionService.factions.forEach { faction ->
                if (faction.power <= 0.0) {
                    faction.sendMessage(
                        language["FactionDisbandedZeroPowerNotificationTitle"],
                        language["FactionDisbandedZeroPowerNotificationBody"]
                    )
                    factionService.delete(faction.id).onFailure {
                        logger.log(SEVERE, "Failed to delete faction: ${it.reason.message}", it.reason.cause)
                        return
                    }
                }
            }
        }
    }

    private fun setupNotificationService(): MfNotificationService = when {
        server.pluginManager.getPlugin("Mailboxes") != null -> MailboxesNotificationService(this)
        server.pluginManager.getPlugin("rpk-notification-lib-bukkit") != null -> RpkNotificationService(this)
        else -> NoOpNotificationService()
    }

    private fun setupRpkLockService() {
        if (server.pluginManager.getPlugin("rpk-lock-lib-bukkit") != null) {
            MfRpkLockService(this)
        }
    }
}
