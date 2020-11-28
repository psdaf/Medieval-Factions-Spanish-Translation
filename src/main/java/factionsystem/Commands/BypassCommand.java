package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassCommand {

    Main main = null;

    public BypassCommand(Main plugin) {
        main = plugin;
    }

    public void toggleBypass(CommandSender sender) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.bypass") || player.hasPermission("mf.admin")) {

                if (!main.adminsBypassingProtections.contains(player.getUniqueId())) {
                    main.adminsBypassingProtections.add(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Tu jugador ignorará las protecciones de Medieval Factions.");
                }
                else {
                    main.adminsBypassingProtections.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Tu jugador ya no ignora las protecciones de Medieval Factions.");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.bypass");
            }
        }

    }
}
