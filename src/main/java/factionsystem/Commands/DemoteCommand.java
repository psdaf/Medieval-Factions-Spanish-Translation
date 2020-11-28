package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static factionsystem.Subsystems.UtilitySubsystem.*;
import static org.bukkit.Bukkit.getServer;

public class DemoteCommand {

    Main main = null;

    public DemoteCommand(Main plugin) {
        main = plugin;
    }

    public void demotePlayer(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                if (args.length > 1) {
                    for (Faction faction : main.factions) {
                        UUID officerUUID = findUUIDBasedOnPlayerName(args[1]);
                        if (officerUUID != null && faction.isOfficer(officerUUID)) {
                            if (faction.isOwner(player.getUniqueId())) {
                                if (faction.removeOfficer(officerUUID)) {

                                    player.sendMessage(ChatColor.GREEN + "¡Se le bajó el rango al jugador!");

                                    try {
                                        Player target = getServer().getPlayer(officerUUID);
                                        target.sendMessage(ChatColor.RED + "Se te fue revocado el rango de miembro en tu facción.");
                                    }
                                    catch(Exception ignored) {

                                    }
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "¡Ese jugador no es oficial de tu facción!");
                                }
                                return;
                            }
                        }
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Uso: /mf demote (nombre-jugador)");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Tienes que pertenecer a una facción para usar este comando.");
            }
        }
    }
}
