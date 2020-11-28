package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class JoinCommand {

    Main main = null;

    public JoinCommand(Main plugin) {
        main = plugin;
    }

    public boolean joinFaction(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {

                // creating name from arguments 1 to the last one
                String factionName = createStringFromFirstArgOnwards(args);

                for (Faction faction : main.factions) {
                    if (faction.getName().equalsIgnoreCase(factionName)) {
                        if (faction.isInvited(player.getUniqueId())) {

                            // join if player isn't in a faction already
                            if (!(isInFaction(player.getUniqueId(), main.factions))) {
                                faction.addMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());
                                faction.uninvite(player.getUniqueId());
                                try {
                                    sendAllPlayersInFactionMessage(faction, ChatColor.GREEN + player.getName() + " has joined " + faction.getName());
                                } catch (Exception ignored) {

                                }
                                player.sendMessage(ChatColor.GREEN + "¡Te uniste a la facción!");
                                return true;
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Ya perteneces a una facción.");
                                return false;
                            }

                        } else {
                            player.sendMessage(ChatColor.RED + "No fuiste invitado a esta facción.");
                            return false;
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Uso: /mf join (nombre-facción)");
                return false;
            }
        }
        return false;
    }

}
