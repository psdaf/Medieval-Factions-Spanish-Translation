package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class DisbandCommand {

    Main main = null;

    public DisbandCommand(Main plugin) {
        main = plugin;
    }

    public boolean deleteFaction(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 1) {
                if (player.hasPermission("mf.disband.others") || player.hasPermission("mf.admin")) {

                    String factionName = createStringFromFirstArgOnwards(args);

                    for (int i = 0; i < main.factions.size(); i++) {

                        if (main.factions.get(i).getName().equalsIgnoreCase(factionName)) {

                            removeFaction(i);
                            player.sendMessage(ChatColor.GREEN + factionName + " ha sido eliminada.");
                            return true;

                        }

                    }
                    player.sendMessage(ChatColor.RED + "¡Facción no encontrada!");
                    return false;
                }
                else {
                    player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.disband.others'");
                    return false;
                }

            }

            boolean owner = false;
            for (int i = 0; i < main.factions.size(); i++) {
                if (main.factions.get(i).isOwner(player.getUniqueId())) {
                    owner = true;
                    if (main.factions.get(i).getPopulation() == 1) {
                        main.playersInFactionChat.remove(player.getUniqueId());
                        removeFaction(i);
                        player.sendMessage(ChatColor.GREEN + "La facción ha sido eliminada.");
                        return true;
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Tienes que echar a todos los miembros antes de eliminar tu facción.");
                        return false;
                    }
                }
            }

            if (!owner) {
                player.sendMessage(ChatColor.RED + "Tienes que ser miembro de una facción para usar este comando.");
                return false;
            }
        }
        return false;
    }

    public void removeFaction(int i) {

        // remove claimed land objects associated with this faction
        removeAllClaimedChunks(main.factions.get(i).getName(), main.claimedChunks);

        // remove locks associated with this faction
        removeAllLocks(main.factions.get(i).getName(), main.lockedBlocks);

        // remove records of alliances/wars associated with this faction
        for (Faction faction : main.factions) {
            if (faction.isAlly(main.factions.get(i).getName())) {
                faction.removeAlly(main.factions.get(i).getName());
            }
            if (faction.isEnemy(main.factions.get(i).getName())) {
                faction.removeEnemy(main.factions.get(i).getName());
            }
        }

        main.factions.remove(i);
    }
}
