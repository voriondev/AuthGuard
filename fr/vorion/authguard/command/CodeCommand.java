package fr.vorion.authguard.command;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.vorion.authguard.Main;
import fr.xephi.authme.api.NewAPI;

public class CodeCommand implements CommandExecutor
{

	public Main main;

	public CodeCommand(Main m)
	{
		main = m;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player)sender;
		NewAPI authAPI = NewAPI.getInstance();

		if(args.length == 1)
		{

			if(!StringUtils.isNumeric(args[0]))
			{
				player.sendMessage("§cLe code doit être uniquement composé de chiffres.");
				player.sendMessage("§cExemple: /code 012345");
				return false;
			}

			if(args[0].length() != 6)
			{
				player.sendMessage("§cLe code est uniquement composé de 6 chiffres.");
				player.sendMessage("§cExemple: /code 012345");
				return false;
			}
			if(args[0].length() == 8)
			{
				if(!main.credentials.checkEmergencyCode(player.getName(), args[0]))
				{
					player.sendMessage("§cLe code est uniquement composé de 6 chiffres.");
					player.sendMessage("§cExemple: /code 012345");
					return false;
				}
			}
		}

		if(!authAPI.isAuthenticated(player))
		{
			player.sendMessage("§cVous devez d'abord vous authentifier avant d'utiliser cette commande !");
		}

		if(authAPI.isAuthenticated(player) && !main.getUnconfirmedPlayers().contains(player))
		{
			player.sendMessage("§cVous êtes déjà identifié !");
		}

		if(authAPI.isAuthenticated(player) && main.getUnconfirmedPlayers().contains(player))
		{
			int code = main.googleAuthenticator.getTotpPassword(main.credentials.getSecretKey(player.getName()));
			if(code == Integer.parseInt(args[0]))
			{
				main.getUnconfirmedPlayers().remove(player);
				main.credentials.saveAddress(player.getName(), player.getAddress().getAddress().toString().replaceAll("/", ""));
				player.sendMessage("§aVous avez été authentifié avec succès, et votre IP a été enregistrée !");
			}
			else
			{
				if(main.credentials.checkEmergencyCode(player.getName(), args[0]))
				{
					main.getUnconfirmedPlayers().remove(player);
					main.credentials.saveAddress(player.getName(), player.getAddress().getAddress().toString().replaceAll("/", ""));
					player.sendMessage("§aVous avez été authentifié avec succès, et votre IP a été enregistrée !");
				}
				else
				{
					player.sendMessage("§cLe code entré est incorrect, veuillez réessayer.");
				}
			}
		}

		return false;
	}
}
