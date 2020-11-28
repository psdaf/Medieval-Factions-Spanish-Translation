package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class LawsCommand {

    Main main = null;

    public LawsCommand(Main plugin) {
        main = plugin;
    }

    public void showLawsToPlayer(CommandSender sender, String[] args) {
        // player & perm check
        if (sender instanceof Player && ( ((Player) sender).hasPermission("mf.laws") || ((Player) sender).hasPermission("mf.default")) ) {

            Player player = (Player) sender;

            Faction faction = null;

            if (args.length == 1) {
                faction = getPlayersFaction(player.getUniqueId(), main.factions);
            }
            else {
                String target = createStringFromFirstArgOnwards(args);
                boolean exists = false;
                for (Faction f : main.factions) {
                    if (f.getName().equalsIgnoreCase(target)) {
                        faction = getFaction(target, main.factions);
                        exists = true;
                    }
                }
                if (!exists) {
                    player.sendMessage(ChatColor.RED + "Facción no encontrada.");
                    return;
                }
            }

            if (faction != null) {

                if (faction.getNumLaws() != 0) {

                    player.sendMessage(ChatColor.AQUA + "\n == Reglas de " + faction.getName() + " == ");

                    // list laws
                    int counter = 1;
                    for (String law : faction.getLaws()) {
                        player.sendMessage(ChatColor.AQUA + "" + counter + ". " + faction.getLaws().get(counter - 1));
                        counter++;
                    }

                }
                else {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Tu facción no tiene ninguna regla.");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Esa facción no tiene ninguna regla.");
                    }

                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Tienes que ser miembro de una facción para usar ese comando");

            }

        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar ese comando, necesitas el permiso: 'mf.laws'");
        }
    }

}
