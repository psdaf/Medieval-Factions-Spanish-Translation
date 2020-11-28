package factionsystem.Commands;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.PlayerPowerRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class ForceCommand {

    Main main = null;

    public ForceCommand(Main plugin) {
        main = plugin;
    }

    public boolean force(CommandSender sender, String[] args) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("save")) {
                return forceSave(sender);
            }

            if (args[1].equalsIgnoreCase("load")) {
                return forceLoad(sender);
            }

            if (args[1].equalsIgnoreCase("peace")) {
                return forcePeace(sender, args);
            }
            
            if (args[1].equalsIgnoreCase("demote")) {
                return forceDemote(sender, args);
            }

            if (args[1].equalsIgnoreCase("join")) {
                return forceJoin(sender, args);
            }

            if (args[1].equalsIgnoreCase("kick")) {
                return forceKick(sender, args);
            }
            if (args[1].equalsIgnoreCase("power")) {
                return forcePower(sender, args);
            }
        }
        // show usages
        sender.sendMessage(ChatColor.RED + "Sub-comamdps:");
        sender.sendMessage(ChatColor.RED + "/mf force save");
        sender.sendMessage(ChatColor.RED + "/mf force load");
        sender.sendMessage(ChatColor.RED + "/mf force peace 'facción1' 'facción2'");
        sender.sendMessage(ChatColor.RED + "/mf force demote (jugador)");
        sender.sendMessage(ChatColor.RED + "/mf force join 'jugador' 'facción2'");
        sender.sendMessage(ChatColor.RED + "/mf force kick (jugador)");
        sender.sendMessage(ChatColor.RED + "/mf force power 'jugador' 'número'");
        return false;
    }

    private boolean forceSave(CommandSender sender) {
        if (sender.hasPermission("mf.force.save") || sender.hasPermission("mf.force.*") || sender.hasPermission("mf.admin")) {
            sender.sendMessage(ChatColor.GREEN + "Plugin Medieval Factions está guardando...");
            main.storage.save();
            return true;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.save'");
            return false;
        }
    }

    private boolean forceLoad(CommandSender sender) {
        if (sender.hasPermission("mf.force.load") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            sender.sendMessage(ChatColor.GREEN + "Plugin Medieval Factions está cargando...");
            main.storage.load();
            main.reloadConfig();
            return true;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.load'");
            return false;
        }
    }

    private boolean forcePeace(CommandSender sender, String[] args) {

        if (sender.hasPermission("mf.force.peace") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "Facciones no especificadas. Tienen que escribirse dentro de 'apóstrofes'.");
                    return false;
                }

                String factionName1 = singleQuoteArgs.get(0);
                String factionName2 = singleQuoteArgs.get(1);

                Faction faction1 = main.utilities.getFaction(factionName1, main.factions);
                Faction faction2 = main.utilities.getFaction(factionName2, main.factions);

                // force peace
                if (faction1 != null && faction2 != null) {
                    if (faction1.isEnemy(faction2.getName())) {
                        faction1.removeEnemy(faction2.getName());
                    }
                    if (faction2.isEnemy(faction1.getName())) {
                        faction2.removeEnemy(faction1.getName());
                    }

                    // announce peace to all players on server.
                    main.utilities.sendAllPlayersOnServerMessage(ChatColor.GREEN + faction1.getName() + " is now at peace with " + faction2.getName() + "!");
                    return true;
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Una de las facciones especificadas no fue encontrada.");
                    return false;
                }
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Uso: /mf force peace 'facción-1' 'facción-2'");
            return false;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.peace'");
            return false;
        }

    }

    private boolean forceDemote(CommandSender sender, String[] args) { // 1 argument
        if (sender.hasPermission("mf.force.demote") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            if (args.length > 2) {
                String playerName = args[2];
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        for (Faction faction : main.factions) {
                            if (faction.isOfficer(player.getUniqueId())) {
                                faction.removeOfficer(player.getUniqueId());

                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "Se te fue removido el rango de oficial en tu facción " + faction.getName() + " por un admin.");
                                }
                            }
                        }
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "¡Listo! El jugador ya no es oficial de la facción.");
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Uso: /mf force demote (jugador)");
                return false;
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.demote'");
            return false;
        }
    }

    private boolean forceJoin(CommandSender sender, String[] args) { // 2 arguments
        if (sender.hasPermission("mf.force.join") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "Argumentos no válidos. Tienen que estar escritos dentro de 'apóstrofes'.");
                    return false;
                }

                String playerName = singleQuoteArgs.get(0);
                String factionName = singleQuoteArgs.get(1);

                Faction faction = main.utilities.getFaction(factionName, main.factions);

                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {

                        if (faction != null) {
                            if (!(isInFaction(player.getUniqueId(), main.factions))) {
                                faction.addMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());
                                try {
                                    sendAllPlayersInFactionMessage(faction, ChatColor.GREEN + player.getName() + " se ha unido a " + faction.getName());
                                } catch (Exception ignored) {

                                }
                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "¡Fuiste forzado a entrar a la facción " + faction.getName() + "!");
                                }
                                sender.sendMessage(ChatColor.GREEN + "¡Listo! Ahora el jugador pertenece a una facción.");
                                return true;
                            }
                            else {
                                sender.sendMessage(ChatColor.RED + "Ese jugador ya pertenece a una facción.");
                                return false;
                            }
                        }
                        else {
                            sender.sendMessage(ChatColor.RED + "Facción no encontrada.");
                            return false;
                        }
                    }
                }
                sender.sendMessage(ChatColor.RED + "Jugador no encontrado.");
                return false;
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Uso: /mf force join 'jugador' 'facción'");
            return false;
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.join'");
            return false;
        }
    }

    private boolean forceKick(CommandSender sender, String[] args) { // 1 argument
        if (sender.hasPermission("mf.force.kick") || sender.hasPermission("mf.force.*")|| sender.hasPermission("mf.admin")) {
            if (args.length > 2) {
                String playerName = args[2];
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        for (Faction faction : main.factions) {
                            if (faction.isOwner(player.getUniqueId())) {
                                sender.sendMessage(ChatColor.RED + "No podés echar al dueño de una facción. Intenta eliminando la facción.");
                                return false;
                            }

                            if (faction.isMember(player.getUniqueId())) {
                                faction.removeMember(player.getUniqueId(), getPlayersPowerRecord(player.getUniqueId(), main.playerPowerRecords).getPowerLevel());

                                if (player.isOnline()) {
                                    Bukkit.getPlayer(player.getUniqueId()).sendMessage(ChatColor.AQUA + "Fuiste expulsado de la facción " + faction.getName() + " por un admin.");
                                }

                                if (faction.isOfficer(player.getUniqueId())) {
                                    faction.removeOfficer(player.getUniqueId());
                                }
                            }
                        }
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "¡Listo! El jugador ya no pertenece a una facción.");
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Uso: /mf force kick (jugador)");
                return false;
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.kick'");
            return false;
        }
    }

    public boolean forcePower(CommandSender sender, String[] args) {
        if (sender.hasPermission("mf.force.power") || sender.hasPermission("mf.force.*") || sender.hasPermission("mf.admin")) {

            if (args.length >= 4) {

                // get arguments designated by single quotes
                ArrayList<String> singleQuoteArgs = main.utilities.getArgumentsInsideSingleQuotes(args);

                if (singleQuoteArgs.size() < 2) {
                    sender.sendMessage(ChatColor.RED + "El jugador y el poder debe estar escrito dentro de 'apóstrofes'.");
                    return false;
                }

                String player = singleQuoteArgs.get(0);
                int desiredPower = -1;

                try {
                    desiredPower = Integer.parseInt(singleQuoteArgs.get(1));
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "¡El poder tiene que ser un número!");
                    return false;
                }

                PlayerPowerRecord record = getPlayersPowerRecord(findUUIDBasedOnPlayerName(player), main.playerPowerRecords);

                record.setPowerLevel(desiredPower);
                sender.sendMessage(ChatColor.GREEN + "El nivel de poder de '" + player + "' fue cambiado a " + desiredPower);
                return true;
            }

            // send usage
            sender.sendMessage(ChatColor.RED + "Uso: /mf force power 'jugador' 'número'");
            return false;
        } else {
            sender.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.force.power'");
            return false;
        }
    }

}
