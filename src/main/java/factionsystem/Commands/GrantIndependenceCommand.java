package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GrantIndependenceCommand {

    Main main = null;

    public GrantIndependenceCommand(Main plugin) {
        main = plugin;
    }

    public void grantIndependence(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.grantindependence") || player.hasPermission("mf.default")) {

                if (args.length > 1) {

                    String targetFactionName = main.utilities.createStringFromFirstArgOnwards(args);

                    Faction playersFaction = main.utilities.getPlayersFaction(player.getUniqueId(), main.factions);
                    Faction targetFaction = main.utilities.getFaction(targetFactionName, main.factions);

                    if (targetFaction != null) {

                        if (playersFaction != null) {

                            if (playersFaction.isOwner(player.getUniqueId())) {
                                // if target faction is a vassal
                                if (targetFaction.isLiege(playersFaction.getName())) {
                                    targetFaction.setLiege("none");
                                    playersFaction.removeVassal(targetFaction.getName());

                                    // inform all players in that faction that they are now independent
                                    main.utilities.sendAllPlayersInFactionMessage(targetFaction, ChatColor.GREEN + "" + targetFactionName + " declaró a tu facción independiente!");

                                    // inform all players in players faction that a vassal was granted independence
                                    main.utilities.sendAllPlayersInFactionMessage(playersFaction, ChatColor.GREEN + "" + targetFactionName + " ya no es una facción vasalla!");
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "Esa facción no es tu vasalla.");
                                }

                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Tienes que ser dueño de la facción para hacer eso.");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Tienes que ser miembro de una facción para usar ese comando.");
                        }
                    }
                    else {
                        // faction doesn't exist, send message
                        player.sendMessage(ChatColor.RED + "Esa facción no existe.");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "Uso: /mf grantindependence (nombre-facción)");
                }

            }
            else {
                // send perm message
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.grantindependence'");
            }
        }

    }

}
