package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckAccessCommand {

    Main main = null;

    public CheckAccessCommand(Main plugin) {
        main = plugin;
    }

    public void checkAccess(CommandSender sender, String[] args) {
        // if sender is player and if player has permission
        if (sender instanceof Player && (((Player) sender).hasPermission("mf.checkaccess") || ((Player) sender).hasPermission("mf.default"))) {

            Player player = (Player) sender;

            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("cancel")) {
                    player.sendMessage(ChatColor.RED + "¡Cancelado!");
                    if (main.playersCheckingAccess.contains(player.getUniqueId())) {
                        main.playersCheckingAccess.remove(player.getUniqueId());
                        return;
                    }
                }
            }

            if (!main.playersCheckingAccess.contains(player.getUniqueId())) {
                main.playersCheckingAccess.add(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "¡Click derecho en un bloque bloqueado para ver quién tiene acceso a él! Escribe '/mf checkaccess cancel' para cancelar.");
            }
            else {
                player.sendMessage(ChatColor.RED + "¡Ya usaste este comando! Escribe '/mf checkaccess cancel' para cancelar.");
            }

        }
    }

}
