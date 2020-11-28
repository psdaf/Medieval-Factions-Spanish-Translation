package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class AllyCommand {

    Main main = null;

    public AllyCommand(Main plugin) {
        main = plugin;
    }

    public void requestAlliance(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                Faction playersFaction = getPlayersFaction(player.getUniqueId(), main.factions);

                if (playersFaction.isOwner(player.getUniqueId()) || playersFaction.isOfficer(player.getUniqueId())) {

                    // player is able to do this command

                    if (args.length > 1) {
                        String targetFactionName = createStringFromFirstArgOnwards(args);
                        Faction targetFaction = getFaction(targetFactionName, main.factions);

                        if (!playersFaction.getName().equalsIgnoreCase(targetFactionName)) {

                            if (targetFaction != null) {

                                if (!playersFaction.isAlly(targetFactionName)) {
                                    // if not already ally

                                    if (!playersFaction.isRequestedAlly(targetFactionName)) {
                                        // if not already requested

                                        if (!playersFaction.isEnemy(targetFactionName)) {

                                            playersFaction.requestAlly(targetFactionName);
                                            player.sendMessage(ChatColor.GREEN + "Intentó aliarse con " + targetFactionName);

                                            sendAllPlayersInFactionMessage(targetFaction,ChatColor.GREEN + "" + playersFaction.getName() + " ¡intentó aliarse con " + targetFactionName + "!");

                                            if (playersFaction.isRequestedAlly(targetFactionName) && targetFaction.isRequestedAlly(playersFaction.getName())) {
                                                // ally factions
                                                playersFaction.addAlly(targetFactionName);
                                                getFaction(targetFactionName, main.factions).addAlly(playersFaction.getName());
                                                player.sendMessage(ChatColor.GREEN + "¡Tu facción y " + targetFactionName + " ahora son aliados!");
                                                sendAllPlayersInFactionMessage(targetFaction, ChatColor.GREEN + "¡Tu facción y " + playersFaction.getName() + " ahora son aliados!");
                                            }
                                        }
                                        else {
                                            player.sendMessage(ChatColor.RED + "¡Esa facción es enemiga! Haz la paz antes de tratar aliarte con ellos.");
                                        }

                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "¡Ya haz pedido una alianza a esta facción!");
                                    }

                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "¡Esa facción ya es aliada!");
                                }
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "¡Facción no encontrada!");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "No puedes aliarte con tu propia facción...?");
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /mf ally (nombre-facción)");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "Tienes que ser dueño u oficial de una facción para usar este comando.");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Tienes que ser parte de una facción para usar este comando.");
            }
        }
    }
}
