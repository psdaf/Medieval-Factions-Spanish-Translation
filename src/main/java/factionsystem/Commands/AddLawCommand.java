package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class AddLawCommand {

    Main main = null;

    public AddLawCommand(Main plugin) {
        main = plugin;
    }

    public void addLaw(CommandSender sender, String[] args) {
        // player & perm check
        if (sender instanceof Player && ( ((Player) sender).hasPermission("mf.addlaw") || ((Player) sender).hasPermission("mf.default")) ) {

            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                Faction playersFaction = getPlayersFaction(player.getUniqueId(), main.factions);

                if (playersFaction.isOwner(player.getUniqueId())) {
                    if (args.length > 1) {
                        String newLaw = createStringFromFirstArgOnwards(args);

                        playersFaction.addLaw(newLaw);

                        player.sendMessage(ChatColor.GREEN + "¡Ley añadida!");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /mf addlaw (nueva ley)");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "¡Tienes que ser el dueño de la facción para usar este comando!");
                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Tienes que ser parte de una facción para user este comando!");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.addlaw'");
        }

    }
}
