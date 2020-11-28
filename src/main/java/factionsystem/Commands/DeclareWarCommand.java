package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class DeclareWarCommand {

    Main main = null;

    public DeclareWarCommand(Main plugin) {
        main = plugin;
    }

    public void declareWar(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean owner = false;
            for (Faction faction : main.factions) {
                // if player is the owner or officer
                if (faction.isOwner(player.getUniqueId()) || faction.isOfficer(player.getUniqueId())) {
                    owner = true;
                    // if there's more than one argument
                    if (args.length > 1) {

                        // get name of faction
                        String factionName = createStringFromFirstArgOnwards(args);

                        // check if faction exists
                        for (int i = 0; i < main.factions.size(); i++) {
                            if (main.factions.get(i).getName().equalsIgnoreCase(factionName)) {

                                if (!(faction.getName().equalsIgnoreCase(factionName))) {

                                    // check that enemy is not already on list
                                    if (!(faction.isEnemy(factionName))) {

                                        // if trying to declare war on a vassal
                                        if (main.factions.get(i).hasLiege()) {

                                            // if faction is vassal of declarer
                                            if (faction.isVassal(factionName)) {
                                                player.sendMessage(ChatColor.RED + "¡No puedes declarar guerra a tu propio vasallo!");
                                                return;
                                            }

                                            // if lieges aren't the same
                                            if (!main.factions.get(i).getLiege().equalsIgnoreCase(faction.getLiege())) {
                                                player.sendMessage(ChatColor.RED + "No puedes declarar la guerra a esta facción, ya que es vasalla. ¡Tienes que declarar guerra al honorado " + main.factions.get(i).getLiege() + " en cambio!");
                                                return;
                                            }

                                        }

                                        // disallow if trying to declare war on liege
                                        if (faction.isLiege(factionName)) {
                                            player.sendMessage(ChatColor.RED + "¡No puedes declararle la guerra a tu alteza! Intenta '/mf declareindependence' para declarar la independencia.");
                                            return;
                                        }

                                        // check to make sure we're not allied with this faction
                                        if (!faction.isAlly(factionName)) {

                                            // add enemy to declarer's faction's enemyList and the enemyLists of its allies
                                            faction.addEnemy(factionName);

                                            // add declarer's faction to new enemy's enemyList
                                            main.factions.get(i).addEnemy(faction.getName());

                                            for (int j = 0; j < main.factions.size(); j++) {
                                                if (main.factions.get(j).getName().equalsIgnoreCase(factionName)) {
                                                    main.utilities.sendAllPlayersOnServerMessage(ChatColor.RED + faction.getName() + " le declaró la guerra a " + factionName + "!");
                                                }
                                            }

                                            // invoke alliances
                                            invokeAlliances(main.factions.get(i).getName(), faction.getName(), main.factions);
                                        }
                                        else {
                                            player.sendMessage(ChatColor.RED + "¡No puedes declararle la guerra a un aliado!");
                                        }

                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "Tu facción ya está en guerra con " + factionName);
                                    }

                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "No puedes declarar guerra a tu propia facción.");
                                }
                            }
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /mf declarewar (nombre-facción)");
                    }
                }
            }
            if (!owner) {
                player.sendMessage(ChatColor.RED + "Tienes que ser miembro u oficial de una facción para usar este comando.");
            }
        }
    }
}
