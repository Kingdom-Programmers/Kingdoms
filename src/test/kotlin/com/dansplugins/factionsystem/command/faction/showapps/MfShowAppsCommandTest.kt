
import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.showapps.MfShowAppsCommand
import com.dansplugins.factionsystem.command.faction.showapps.tasks.ShowAppsForPlayersFactionTask
import com.dansplugins.factionsystem.lang.Language
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

class MfShowAppsCommandTest {

    // class under test
    private lateinit var mfShowAppsCommand: MfShowAppsCommand

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
        val logger = mock(Logger::class.java)
        val server = mock(Server::class.java)
        val scheduler = mock(BukkitScheduler::class.java)
        `when`(plugin.language).thenReturn(language)
        `when`(plugin.logger).thenReturn(logger)
        `when`(plugin.server).thenReturn(server)
        `when`(server.scheduler).thenReturn(scheduler)
        mfShowAppsCommand = MfShowAppsCommand(plugin)
    }

    @Test
    fun testOnCommand_senderWithoutPermission() {
        // prepare
        `when`(sender.hasPermission("mf.showapps")).thenReturn(false)
        `when`(language["CommandFactionShowAppsNoPermission"]).thenReturn("No permission")

        // execute
        val result = mfShowAppsCommand.onCommand(sender, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(sender).sendMessage("${net.md_5.bungee.api.ChatColor.RED}No permission")
    }

    @Test
    fun testOnCommand_SenderNotAPlayer() {
        // prepare
        `when`(sender.hasPermission("mf.showapps")).thenReturn(true)
        `when`(language["CommandFactionShowAppsNotAPlayer"]).thenReturn("Not a player")

        // execute
        val result = mfShowAppsCommand.onCommand(sender, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(sender).sendMessage("${net.md_5.bungee.api.ChatColor.RED}Not a player")
    }

    @Test
    fun testOnCommand_ValidPlayerWithPermission() {
        // prepare
        `when`(player.hasPermission("mf.showapps")).thenReturn(true)

        // execute
        val result = mfShowAppsCommand.onCommand(player, command, "label", arrayOf())

        // verify
        assertTrue(result)
        verify(plugin.server.scheduler).runTaskAsynchronously(eq(plugin), any(ShowAppsForPlayersFactionTask::class.java))
    }
}
