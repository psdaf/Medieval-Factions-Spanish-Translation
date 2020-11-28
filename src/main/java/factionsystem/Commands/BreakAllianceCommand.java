package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;
import static factionsystem.Subsystems.UtilitySubsystem.sendAllPlayersInFactionMessage;

public class BreakAllianceCommand {

    Main main = null;

    public BreakAllianceCommand(Main plugin) {
        main = plugin;
    }


    public void breakAlliance(CommandSender sender, String[] args) {
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
                                    if ((faction.isAlly(factionName))) {
                                        // remove alliance
                                        faction.removeAlly(factionName);
                                        player.sendMessage(ChatColor.GREEN + "¡La alianza ha sido rota con " + factionName + "!");

                                        // add declarer's faction to new enemy's enemyList
                                        main.factions.get(i).removeAlly(faction.getName());
                                        for (int j = 0; j < main.factions.size(); j++) {
                                            if (main.factions.get(j).getName().equalsIgnoreCase(factionName)) {
                                                sendAllPlayersInFactionMessage(main.factions.get(j), ChatColor.RED + faction.getName() + " ha roto su alianza con tu facción.");
                                            }
                                        }
                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "Tu facción ahora está aliada con " + factionName);
                                    }

                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "No puedes hacer eso con tu propia facción.");
                                }
                            }
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /mf breakalliance (nombre-facción)");
                    }
                }
            }
            if (!owner) {
                player.sendMessage(ChatColor.RED + "Tienes que ser dueño u oficial de una facción para usar este comando.");
            }
        }
    }
}
