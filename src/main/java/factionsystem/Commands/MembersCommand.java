package factionsystem.Commands;

import factionsystem.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static factionsystem.UtilityFunctions.*;

public class MembersCommand {

    public static void showMembers(CommandSender sender, String[] args, ArrayList<Faction> factions) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (isInFaction(player.getName(), factions)) {
                if (args.length == 1) {
                    for (Faction faction : factions) {
                        if (faction.isMember(player.getName())) {
                            sendFactionMembers(player, faction);
                        }
                    }
                }
                else {
                    // creating name from arguments 1 to the last one
                    String name = createStringFromFirstArgOnwards(args);

                    for (Faction faction : factions) {
                        if (faction.getName().equalsIgnoreCase(name)) {
                            sendFactionMembers(player, faction);
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "You need to be in a faction to use this command.");
            }
        }
    }

}