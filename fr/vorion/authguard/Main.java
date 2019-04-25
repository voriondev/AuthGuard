package fr.vorion.authguard;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import fr.vorion.authguard.command.CommandManager;
import fr.vorion.authguard.credentials.Credentials;
import fr.vorion.authguard.event.EventManager;
import fr.vorion.authguard.sql.MySQL;

public class Main extends JavaPlugin
{

	public Main main;
	public MySQL database;
	public Connection sqlConnection;
	public GoogleAuthenticator googleAuthenticator;
	public Credentials credentials;
	public Logger logger;

	public CommandManager commandManager;
	public EventManager eventManager;

	public ArrayList<Player> unconfirmedPlayers = new ArrayList<>();

	public Main()
	{
		main = this;
		database = new MySQL("localhost", "3306", "helloopenworld", "root", "yeeeeeeeee");
		googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(credentials = new Credentials(this));
		logger = getLogger();
	}

	public void onEnable()
	{
		this.logger.info("------ AuthGuard / by Vorion ------");

		this.logger.info(">> Loading configuration..");

	    this.logger.info(">> Registering events..");
	    eventManager = new EventManager(this);
	    eventManager.registerEvents();

	    this.logger.info(">> Listening to custom channel..");

	    this.logger.info(">> Registering commands..");
	    commandManager = new CommandManager(this);
	    commandManager.registerCommands();

	    this.logger.info(">> Etablishing connection to SQL database..");
		try
		{
			sqlConnection = database.openConnection();
		}
		catch (Exception e)
		{
			this.logger.info(">> Unable to connect to the SQL database..");
			e.printStackTrace();
		}


	}

	public void onDisable()
	{

	}

	public ArrayList<Player> getUnconfirmedPlayers()
	{
		return unconfirmedPlayers;
	}

}
