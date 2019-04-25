package fr.vorion.authguard.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import fr.vorion.authguard.Main;
import fr.vorion.authguard.util.Identifier;
import fr.vorion.authguard.util.JsonBuilder;
import fr.vorion.authguard.util.JsonBuilder.ClickAction;

public class AGCommand implements CommandExecutor
{

	public Main main;

	public JsonBuilder jsonBuilder;

	public AGCommand(Main m)
	{
		main = m;
		jsonBuilder = new JsonBuilder();
	}

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (sender instanceof Player)
        {
        	Player player = (Player) sender;

        	if(args.length == 0)
        	{
        		player.sendMessage("§7---------------[§eAG§7]---------------");
        		player.sendMessage("§e/ag info §7- Permet d'afficher des explications sur AuthGuard.");
        		player.sendMessage("§e/ag enable §7- Permet d'activer AuthGuard.");
        		player.sendMessage("§e/ag disable §7- Permet de désactiver AuthGuard.");
        		player.sendMessage("§7-----------------------------------");
        	}
        	if(args.length >= 1)
        	{
        		/**
        		 * ENABLE
        		 */
        		if(args[0].equalsIgnoreCase("enable"))
        		{
        			if(!main.credentials.getSecretKey(player.getName()).equals("NullPointerExceptionOccured"))
        			{
        				player.sendMessage("§cVous utilisez déjà l'authentification en deux étapes !");
        				return false;
        			}
		        	GoogleAuthenticatorKey authKey = main.googleAuthenticator.createCredentials(player.getName());
		        	player.sendMessage("§aVous venez d'activer l'authentification en deux étapes !");
		        	player.sendMessage("§7Il vous faut désormais enregistrer votre compte sur l'application §eGoogle Authenticator§7.");
		        	jsonBuilder.withText("Vous pouvez utiliser un code scannable en cliquant ").withColor(ChatColor.GRAY).withText("ICI").withColor(ChatColor.YELLOW).withClickEvent(ClickAction.OPEN_URL, GoogleAuthenticatorKey.getQRBarcodeURL(player.getName(), "AuthGuard", main.credentials.getSecretKey(player.getName()))).withText(" ou en entrant le pseudo et la clé suivante sur l'application: ").withColor(ChatColor.GRAY).sendJson(player);
		        	player.sendMessage("§e" + player.getName() + "@AuthGuard §7- §e" + authKey.getKey());
		        	player.sendMessage("§7Voici vos §acodes de secours §7(permettant de se connecter sans l'application): ");

		        	player.sendMessage("§7- §e" + authKey.getScratchCodes().get(0));
		        	player.sendMessage("§7- §e" + authKey.getScratchCodes().get(authKey.getScratchCodes().size() - 1));

		        	player.sendMessage("§7Bon jeu !");
        		}

        		/**
        		 * DISABLE
        		 */
        		if(args[0].equalsIgnoreCase("disable"))
        		{
        			if(main.credentials.getSecretKey(player.getName()).equals("NullPointerExceptionOccured"))
        			{
        				player.sendMessage("§cL'authentification en deux étapes n'est pas activée.");
        				return false;
        			}
        			player.sendMessage("§cVous venez de désactiver l'authentification en deux étapes..");
        			player.sendMessage("§cVotre compte est désormais moins sécurisé.");
        			player.sendMessage("§cVous pouvez toujours la réactiver en utilisant: /ag enable");
        			main.credentials.removeUser(player.getName());
        		}

        		/**
        		 * TYPE
        		 */
        		if(args[0].equalsIgnoreCase("type"))
        		{

        		}

        		/**
        		 * INFO
        		 */
        		if(args[0].equalsIgnoreCase("info"))
        		{
            		player.sendMessage("§7---------------[§eAG§7]---------------");
        			player.sendMessage("§eL'objectif d'AuthGuard est simple, sécuriser votre compte.");
        			player.sendMessage("§eAinsi, après avoir entré votre mot de passe, vous devrez également entrer un code de validation.");
        			player.sendMessage("§eCela permettra ainsi d'éviter tout accès non autorisé à votre compte.");
        			player.sendMessage("§eLe code de validation est obtenu via l'application Google Authenticator (disponible sur mobile) et change toutes les 30 secondes.");
            		player.sendMessage("§7-----------------------------------");
        		}
        }
        else
        {
        	return false;
        }
    }
		return false;
  }
}
