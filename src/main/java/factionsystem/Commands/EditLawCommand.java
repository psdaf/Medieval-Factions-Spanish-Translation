package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.getPlayersFaction;
import static factionsystem.Subsystems.UtilitySubsystem.isInFaction;

public class EditLawCommand {

    Main main = null;

    public EditLawCommand(Main plugin) {
        main = plugin;
    }

    public void editLaw(CommandSender sender, String[] args) {
        // player & perm check
        if (sender instanceof Player && ( ((Player) sender).hasPermission("mf.editlaw") || ((Player) sender).hasPermission("mf.default")) ) {

            Player player = (Player) sender;

            if (isInFaction(player.getUniqueId(), main.factions)) {
                Faction playersFaction = getPlayersFaction(player.getUniqueId(), main.factions);

                if (playersFaction.isOwner(player.getUniqueId())) {
                    if (args.length > 1) {
                        int lawToEdit = Integer.parseInt(args[1]) - 1;
                        String newLaw = "";
                        for (int i = 2; i < args.length; i++) {
                            newLaw = newLaw + args[i] + " ";
                        }

                        if (playersFaction.editLaw(lawToEdit, newLaw)) {
                            player.sendMessage(ChatColor.GREEN + "¡Ley " + (lawToEdit + 1) + " editada!");
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Uso: /mf editlaw (número) (ley editada)");
                    }

                }

            }
            else {
                player.sendMessage(ChatColor.RED + "Tienes que ser miembro de una facción para usar este comando.");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.editlaw'");
        }
    }

}
