package fr.vorion.authguard.command;

import fr.vorion.authguard.Main;

public class CommandManager
{

	public Main main;

	public CommandManager(Main m)
	{
		this.main = m;
	}

	public void registerCommands()
	{
		main.getCommand("ag").setExecutor(new AGCommand(main));
		main.getCommand("code").setExecutor(new CodeCommand(main));
	}

}
