package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.findPlayerNameBasedOnUUID;
import static factionsystem.Subsystems.UtilitySubsystem.findUUIDBasedOnPlayerName;

public class GrantAccessCommand {

    Main main = null;

    public GrantAccessCommand(Main plugin) {
        main = plugin;
    }

    public void grantAccess(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {

                // if args[1] is cancel, cancel this
                if (args[1].equalsIgnoreCase("cancel")) {
                    player.sendMessage(ChatColor.GREEN + "¡Comando cancelado!");
                    return;
                }

                // if not already granting access
                if (!main.playersGrantingAccess.containsKey(player.getUniqueId())) {
                    // save target name and player name in hashmap in main
                    main.playersGrantingAccess.put(player.getUniqueId(), findUUIDBasedOnPlayerName(args[1]));
                    player.sendMessage(ChatColor.GREEN + "Click derecho en un cofre o puerta para darle a " + args[1] + " acceso. Escribe /mf grantaccess cancel para cancelar.");
                }
                else {
                    player.sendMessage(ChatColor.RED + "¡Ya le estás dando acceso a alguien! Escribe /mf grantaccess cancel para cancelar.");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Uso: /grantaccess (nombre-jugador)");
            }

        }
    }

}
