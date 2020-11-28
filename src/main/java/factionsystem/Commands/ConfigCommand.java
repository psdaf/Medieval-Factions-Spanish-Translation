package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Subsystems.ConfigSubsystem;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand {

    Main main = null;

    public ConfigCommand(Main plugin) {
        main = plugin;
    }

    // args count is at least 1 at this point (/mf config)
    // possible sub-commands are show and set
    public void handleConfigAccess(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("mf.config") || player.hasPermission("mf.admin")) {
                if (args.length > 1) {

                    if (args[1].equalsIgnoreCase("show")) {
                        // no further arguments needed, list config
                        main.config.sendPlayerConfigList(player);
                        return;
                    }

                    if (args[1].equalsIgnoreCase("set")) {

                        // two more arguments needed
                        if (args.length > 3) {

                            String option = args[2];
                            String value = args[3];

                            ConfigSubsystem.setConfigOption(option, value, player, main);
                            return;
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Uso: /mf config set (opción) (valor)");
                            return;
                        }

                    }

                    player.sendMessage(ChatColor.RED + "Sub-comandos válidos: show, set");

                }
                else {
                    player.sendMessage(ChatColor.RED + "Sub-comandos válidos: show, set");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.config'");
            }
        }

    }

}
