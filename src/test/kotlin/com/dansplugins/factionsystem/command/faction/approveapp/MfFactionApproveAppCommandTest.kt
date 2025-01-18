
import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.approveapp.MfFactionApproveAppCommand
import com.dansplugins.factionsystem.command.faction.approveapp.tasks.ApproveApplicationTask
import com.dansplugins.factionsystem.faction.MfFactionService
import com.dansplugins.factionsystem.lang.Language
import com.dansplugins.factionsystem.player.MfPlayerService
import com.dansplugins.factionsystem.service.Services
import net.md_5.bungee.api.ChatColor
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitScheduler
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.logging.Logger

class MfFactionApproveAppCommandTest {

    // class under test
    private lateinit var mfFactionApproveAppCommand: MfFactionApproveAppCommand

    // dependencies
    private lateinit var plugin: MedievalFactions
    private lateinit var command: Command
    private lateinit var sender: CommandSender
    private lateinit var player: Player
    private lateinit var language: Language

    @BeforeEach
    fun setUp() {
        plugin = mock(MedievalFactions::class.java)
        command = mock(Command::class.java)
        sender = mock(CommandSender::class.java)
        player = mock(Player::class.java)
        language = mock(Language::class.java)
        val services = mock(Services::class.java)
        val factionService = mock(MfFactionService::class.java)
        val playerService = mock(MfPlayerService::class.java)
        val logger = mock(Logger::class.java)
        val server = mock(Server::class.java)
        val scheduler = mock(BukkitScheduler::class.java)
        `when`(plugin.language).thenReturn(language)
        `when`(plugin.services).thenReturn(services)
        `when`(services.factionService).thenReturn(factionService)
        `when`(services.playerService).thenReturn(playerService)
        `when`(plugin.logger).thenReturn(logger)
        `when`(plugin.server).thenReturn(server)
        `when`(server.scheduler).thenReturn(scheduler)
        mfFactionApproveAppCommand = MfFactionApproveAppCommand(plugin)
    }

    @Test
    fun testOnCommand_senderWithoutPermission() {
        // prepare
        `when`(sender.hasPermission("mf.approveapp")).thenReturn(false)
        `when`(language["CommandFactionApproveAppNoPermission"]).thenReturn("No permission")

        // execute
        val result = mfFactionApproveAppCommand.onCommand(sender, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(sender).sendMessage("${ChatColor.RED}No permission")
    }

    @Test
    fun testOnCommand_SenderNotAPlayer() {
        // prepare
        `when`(sender.hasPermission("mf.approveapp")).thenReturn(true)
        `when`(language["CommandFactionApproveAppNotAPlayer"]).thenReturn("Not a player")

        // execute
        val result = mfFactionApproveAppCommand.onCommand(sender, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(sender).sendMessage("${ChatColor.RED}Not a player")
    }

    @Test
    fun testOnCommand_NoArgumentsProvided() {
        // prepare
        `when`(player.hasPermission("mf.approveapp")).thenReturn(true)
        `when`(language["CommandFactionApproveAppUsage"]).thenReturn("Usage: /faction apply <faction>")

        // execute
        val result = mfFactionApproveAppCommand.onCommand(player, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(player).sendMessage("${ChatColor.RED}Usage: /faction apply <faction>")
    }

    @Test
    fun testOnCommand_InitiateApproveApplicationTask() {
        // prepare
        `when`(player.hasPermission("mf.approveapp")).thenReturn(true)
        `when`(language["CommandFactionApproveAppUsage"]).thenReturn("Usage: /faction apply <faction>")
        `when`(player.name).thenReturn("playerName")

        // execute
        val result = mfFactionApproveAppCommand.onCommand(player, command, "label", arrayOf("targetPlayerName"))

        // verify
        assertTrue(result)
        verify(plugin.logger).info("Player playerName is approving application for player targetPlayerName")
        verify(plugin.server.scheduler).runTaskAsynchronously(
            eq(plugin),
            any(ApproveApplicationTask::class.java)
        )
    }
}
