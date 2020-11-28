package factionsystem.Commands;

import static factionsystem.Subsystems.UtilitySubsystem.createStringFromFirstArgOnwards;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import factionsystem.Objects.Gate;
import factionsystem.Subsystems.UtilitySubsystem;

public class GateCommand {

	Main main = null;
	
	public GateCommand(Main plugin)
	{
		main = plugin;
	}
	
	public void handleGate(CommandSender sender, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			
			if (player.hasPermission("mf.gate") || player.hasPermission("mf.default"))
			{
				if (args.length > 1)
				{
					if (args[1].equalsIgnoreCase("cancel"))
					{
						if (main.creatingGatePlayers.containsKey(player.getUniqueId()))
						{
							main.creatingGatePlayers.remove(player.getUniqueId());
							player.sendMessage(ChatColor.RED + "¡Creación de verja cancelada!");
							return;
						}
					}
					if (args[1].equalsIgnoreCase("create"))
					{
						if (main.creatingGatePlayers.containsKey(player.getUniqueId()))
						{
							main.creatingGatePlayers.remove(player.getUniqueId());
							player.sendMessage(ChatColor.RED + "¡Ya estás creando una verja!");
							return;
						}
						else
						{
							Faction faction = UtilitySubsystem.getPlayersFaction(player.getUniqueId(), main.factions);
							if (faction != null)
							{
								if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
								{
				        			String gateName = "Unnamed Gate";
				        			if (args.length > 2)
				        			{
				        				gateName = UtilitySubsystem.createStringFromArgIndexOnwards(2, args);
				        			}
									UtilitySubsystem.startCreatingGate(main, player, gateName);
									//TODO: Config setting for magic gate tool.
									player.sendMessage(ChatColor.AQUA + "Creando verja '" + gateName + "'.\nSelecciona el primer punto clickeando el bloque con una azada de oro.");
									return;
								}
								else
								{
									player.sendMessage(ChatColor.RED + "Tienes que ser dueño u oficial de una facción para realizar esta función.");
									return;
								}
							}
						}
					}
					else if (args[1].equalsIgnoreCase("list"))
					{
						Faction faction = UtilitySubsystem.getPlayersFaction(player.getUniqueId(), main.factions);
						if (faction != null)
						{
							if (faction.getGates().size() > 0)
							{
								player.sendMessage(ChatColor.AQUA + "Verjas de Facción");
								for (Gate gate : faction.getGates())
								{
									player.sendMessage(ChatColor.AQUA + String.format("%s: %s", gate.getName(), gate.coordsToString()));
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "Tu facción no tiene verjas definidas.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + String.format("No eres miembro de ninguna facción."));
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("remove"))
					{
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
								if (faction != null)
								{
									if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
									{
										faction.removeGate(gate);
										player.sendMessage(ChatColor.AQUA + String.format("Se removió la verja '%s'.", gate.getName()));
										return;
									}
									else
									{
										player.sendMessage(ChatColor.RED + "Tienes que ser dueño u oficial de una facción para usar este comando.");
										return;
									}
								}
								else
								{
									player.sendMessage(ChatColor.RED + String.format("Error: No se pudo encontrar la verja de facción.", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "El bloque seleccionado no es parte de una verja.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Bloque no detectado para el chequeo de verja.");
							return;
						}
					}
					else if (args[1].equalsIgnoreCase("name"))
					{						
						if (player.getTargetBlock(null, 16) != null)
						{
							if (UtilitySubsystem.isGateBlock(player.getTargetBlock(null, 16), main.factions))
							{
								Gate gate = UtilitySubsystem.getGate(player.getTargetBlock(null, 16), main.factions);
								if (args.length > 2)
								{
									Faction faction = UtilitySubsystem.getGateFaction(gate, main.factions);
									if (faction != null)
									{
										if (faction.isOfficer(player.getUniqueId()) || faction.isOwner(player.getUniqueId()))
										{
											String name = UtilitySubsystem.createStringFromArgIndexOnwards(2, args);
											gate.setName(name);
											player.sendMessage(ChatColor.AQUA + String.format("Nombre de verja cambiado a '%s'.", gate.getName()));
											return;
										}
										else
										{
											player.sendMessage(ChatColor.RED + "Tienes que ser dueño u oficial de una facción para hacer esto.);
											return;
										}
									}
									else
									{
										player.sendMessage(ChatColor.RED + "Error: No se encontró la facción de la verja.");
										return;
									}
								}
								else
								{
									player.sendMessage(ChatColor.AQUA + String.format("Verja '%s'.", gate.getName()));
									return;
								}
							}
							else
							{
								player.sendMessage(ChatColor.RED + "El bloque seleccionado no es parte de una verja.");
								return;
							}
						}
						else
						{
							player.sendMessage(ChatColor.RED + "Bloque no detectado para el chequeo de verja.");
							return;
						}
					}						
				}
				else
				{
			        sender.sendMessage(ChatColor.RED + "Sub-comandos:");
			        sender.sendMessage(ChatColor.AQUA + "/mf gate create (<opcional>nombre)");
			        sender.sendMessage(ChatColor.RED + "/mf gate name (<opcional>nombre)");
			        sender.sendMessage(ChatColor.RED + "/mf gate list");
			        sender.sendMessage(ChatColor.RED + "/mf gate remove");
			        sender.sendMessage(ChatColor.RED + "/mf gate cancel");
			        return;
				}
			}
            else {
                player.sendMessage(ChatColor.RED + "Para usar este comando, necesitas el permiso: 'mf.gate'");
            }

		}
	}
	
}
