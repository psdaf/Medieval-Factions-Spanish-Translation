package factionsystem.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Objects.Duel;
import factionsystem.Subsystems.UtilitySubsystem;

public class DuelCommand {
	Main main = null;
	
	public DuelCommand(Main plugin) {
		main = plugin;
	}
	
	public void handleDuel(CommandSender sender, String[] args) {
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (player.hasPermission("mf.duel") || player.hasPermission("mf.default"))
			{
				if (args.length > 1)
				{
					if (args[1].equalsIgnoreCase("challenge"))
					{
						if (args.length > 2)
						{
							if (args[2].equalsIgnoreCase(player.getName()))
							{
								player.sendMessage(ChatColor.RED + "No puedes luchar contigo mismo... creo.");
								return;
							}
							if (UtilitySubsystem.isDuelling(player, main))
							{
								player.sendMessage(ChatColor.RED + "¡Ya estás luchando contra alguien!");
								return;
							}
							Player target = Bukkit.getServer().getPlayer(args[2]);
							if (target != null)
							{
								if (!UtilitySubsystem.isDuelling(target, main))
								{
									int timeLimit = 120; // Time limit in seconds. TODO: Make config option.
									if (args.length == 4)
									{
										timeLimit = Integer.parseInt(args[3]);
									}
									UtilitySubsystem.inviteDuel(player, target, timeLimit, main);
									player.sendMessage(ChatColor.AQUA + "¡Retaste a " + target.getName() + " a un duelo!");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + target.getName() + " ya está luchando con alguien.");
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "No se encontró nadie llamado '" + args[2] + "'.");
								return;
							}
						}
					}
					else if (args[1].equalsIgnoreCase("accept"))
					{
						if (UtilitySubsystem.isDuelling(player, main))
						{
							player.sendMessage(ChatColor.RED + "¡Ya estás luchando contra alguien!");
							return;
						}
						// If a name is specified to accept the challenge from, look for that specific name.
						if (args.length > 2)
						{
		                	Player challenger = Bukkit.getServer().getPlayer(args[2]);
		                	Duel duel = UtilitySubsystem.getDuel(challenger, player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "Ya estás luchando contra " + args[2] + "!");
									return;
		                		}
		                		if (duel.isChallenged(player))
		                		{
		                			duel.acceptDuel();
		                		}
		                		else
		                		{
									player.sendMessage(ChatColor.RED + "El jugador '" + args[2] + "' te ha retado a un duelo.");
									return;	
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.RED + "No fuiste retado a un duelo por '" + args[2] + "'.");
								return;
		                	}
						}
						else
						{
		                	Duel duel = UtilitySubsystem.getDuel(player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "¡Ya estás luchando!");
									return;
		                		}
		                		if (duel.isChallenged(player))
		                		{
		                			duel.acceptDuel();
		                		}
		                		else
		                		{
									player.sendMessage(ChatColor.RED + "Nadie te ha retado a un duelo.");
									return;	
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.RED + "Nadie te ha retado a un duelo.");
								return;
		                	}
						}
					}
					else if (args[1].equalsIgnoreCase("cancel"))
					{
		                if (UtilitySubsystem.isDuelling(player, main))
		                {
		                	Duel duel = UtilitySubsystem.getDuel(player, main);
		                	if (duel != null)
		                	{
		                		if (duel.getStatus().equals(Duel.DuelState.DUELLING))
		                		{
									player.sendMessage(ChatColor.RED + "No puedes cancelar un duelo activo.");
									return;
		                		}
		                		else
		                		{
		                			main.duelingPlayers.remove(duel);
									player.sendMessage(ChatColor.AQUA + "Duelo cancelado.");
									return;
		                		}
		                	}
		                	else
		                	{
								player.sendMessage(ChatColor.AQUA + "No hay ningún duelo para cancelar.");
								return;
		                	}
		                }
	                	else
	                	{
							player.sendMessage(ChatColor.AQUA + "No hay ningún duelo para cancelar.");
							return;
	                	}

					}
					else
					{
				        sender.sendMessage(ChatColor.RED + "Sub-comandos:");
				        sender.sendMessage(ChatColor.RED + "/mf duel challenge (jugador)");
				        sender.sendMessage(ChatColor.RED + "/mf duel accept (<opcional>jugador)");
				        sender.sendMessage(ChatColor.RED + "/mf duel cancel");
					}
				}
				else
				{
			        sender.sendMessage(ChatColor.RED + "Sub-comandos:");
			        sender.sendMessage(ChatColor.RED + "/mf duel challenge (jugador) (<opcional>límite de tiempo en segundos)");
			        sender.sendMessage(ChatColor.RED + "/mf duel accept (<opcional>jugador)");
			        sender.sendMessage(ChatColor.RED + "/mf duel cancel");
				}
			}
		}
	}
}
