package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.isInFaction;

public class ChatCommand {

    Main main = null;

    public ChatCommand(Main plugin) {
        main = plugin;
    }

    public void toggleFactionChat(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.chat") || player.hasPermission("mf.default")) {
                if (isInFaction(player.getUniqueId(), main.factions)) {
                    if (!main.playersInFactionChat.contains(player.getUniqueId())) {
                        main.playersInFactionChat.add(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "¡Ahora estás hablando en el chat de facción!");
                    }
                    else {
                        main.playersInFactionChat.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "¡Ya no estás en el chat de facción!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "¡Tienes que pertenecer a una facción para usar este comando!");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.chat'");
            }
        }
    }

}
