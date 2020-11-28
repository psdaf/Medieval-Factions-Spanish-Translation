package factionsystem.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    // Each page of the help command should have a title and nine commands. This is for ease of use.
    public boolean sendHelpMessage(CommandSender sender, String[] args) {

        if (args.length == 1 || args.length == 0) {
            sendPageOne(sender);
        }

        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("1")) {
                sendPageOne(sender);
            }
            if (args[1].equalsIgnoreCase("2")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 2/6 == " + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf kick - Saca a un jugador de tu facción. " + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf transfer - Cede el liderazgo de la facción a otro jugador.\n");
                sender.sendMessage(ChatColor.AQUA + "/mf disband - Elimina la facción (tienes que ser dueño)." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf declarewar - Declara la guerra a otra facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf makepeace - Intenta hacer la paz con otra facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf claim - Adquiere tierras para tu facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf unclaim - Elimina tierras de tu facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf unclaimall - Elimina todas las tierras de tu facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf checkclaim - Muestra si esa tierra le pertenece a alguien." + "\n");
            }
            if (args[1].equalsIgnoreCase("3")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 3/6 == " + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf autoclaim - Activa/desactiva la adquisición de tierras automática." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf promote - Convierte a un jugador en oficial de la facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf demote - Convierte a un oficial en miembro normal." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf power - Mira tu nivel de poder." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf sethome - Establece el Home de tu facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf home - Teleportarte al Home de tu facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf who - Mira la información de un jugador." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf ally - Intenta aliarte con una facción." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf breakalliance - Rompe la alianza con una facción." + "\n");
            }
            if (args[1].equalsIgnoreCase("4")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 4/6 == " + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf rename - Renombra tu facción" + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf lock - Bloquea un cofre o puerta." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf unlock Desbloquea un cofre o puerta." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf grantaccess - Da acceso a otro usuario al cofre/puerta bloqueado.");
                sender.sendMessage(ChatColor.AQUA + "/mf checkaccess - Mira quién tiene acceso al bloque bloqueado.");
                sender.sendMessage(ChatColor.AQUA + "/mf revokeaccess - Remueve el acceso de un jugador al bloque bloqueado.");
                sender.sendMessage(ChatColor.AQUA + "/mf laws - Mira las leyes de tu facción.");
                sender.sendMessage(ChatColor.AQUA + "/mf addlaw - Añade una ley a tu facción.");
                sender.sendMessage(ChatColor.AQUA + "/mf removelaw - Elimina una ley de tu facción.");
            }
            if (args[1].equalsIgnoreCase("5")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 5/6 == " + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf editlaw - Edita una ley existente de tu facción.");
                sender.sendMessage(ChatColor.AQUA + "/mf chat - Activa/Desactiva el chat de facción.");
                sender.sendMessage(ChatColor.AQUA + "/mf vassalize - Ofrecer convertir facción en vasalla.");
                sender.sendMessage(ChatColor.AQUA + "/mf swearfealty - Jura lealtad a una facción.");
                sender.sendMessage(ChatColor.AQUA + "/mf declareindependence - Declarar independencia de tu alteza.");
                sender.sendMessage(ChatColor.AQUA + "/mf grantindependence - Declarar una facción vasalla independiente.");
		        sender.sendMessage(ChatColor.AQUA + "/mf gate create (<opcional>nombre)");
		        sender.sendMessage(ChatColor.AQUA + "/mf gate name (<opcional>nombre)");
		        sender.sendMessage(ChatColor.AQUA + "/mf gate list");
		        sender.sendMessage(ChatColor.AQUA + "/mf gate remove");
		        sender.sendMessage(ChatColor.AQUA + "/mf gate cancel");
            }
            if (args[1].equalsIgnoreCase("6")) {
                sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 6/6 == " + "\n");
		        sender.sendMessage(ChatColor.AQUA + "/mf duel challenge (jugador) (<opcional>tiempo límite en segundos)");
		        sender.sendMessage(ChatColor.AQUA + "/mf duel accept (<opcional>jugador)");
		        sender.sendMessage(ChatColor.AQUA + "/mf duel cancel");
                sender.sendMessage(ChatColor.AQUA + "/mf bypass - Ignorar protecciones.");
                sender.sendMessage(ChatColor.AQUA + "/mf config show - Mostrar valores de configuración.");
                sender.sendMessage(ChatColor.AQUA + "/mf config set (opción) (valor) - Elegir un valor de configuración.");
                sender.sendMessage(ChatColor.AQUA + "/mf force - Fuerza el plugin a realizar ciertas acciones." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf version - Ver versión del plugin." + "\n");
                sender.sendMessage(ChatColor.AQUA + "/mf resetpowerlevels - Restaurar poderes del jugador y niveles de facción." + "\n");
            }
        }
        return true;
    }

    static void sendPageOne(CommandSender sender) {
        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == Comandos Medieval Factions Página 1/6 == " + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf help # - Muestra lista de comandos." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf list - Muestra todas las facciones del servidor." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf info - Ver información de tu facción u otra especificada." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf members - Ver lista de miembros de tu facción u otra especificada." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf join - Unirte a una facción, si fuiste invitado." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf leave - Abandona tu facción actual." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf create - Crea una nueva facción." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf invite - Invita un jugador a tu facción." + "\n");
        sender.sendMessage(ChatColor.AQUA + "/mf desc - Dale una descripción a tu facción." + "\n");
    }
}
