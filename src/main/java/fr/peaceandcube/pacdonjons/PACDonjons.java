package fr.peaceandcube.pacdonjons;

import fr.peaceandcube.pacdonjons.command.DonjonCommand;
import fr.peaceandcube.pacdonjons.command.PacDonjonCommand;
import fr.peaceandcube.pacdonjons.file.DonjonsFile;
import fr.peaceandcube.pacdonjons.file.PlayerDataFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PACDonjons extends JavaPlugin {
	public static DonjonsFile donjonsFile;
	public static PlayerDataFile playerDataFile;

	@Override
	public void onEnable() {
		this.getCommand("pacdonjon").setExecutor(new PacDonjonCommand());
		this.getCommand("donjon").setExecutor(new DonjonCommand());

		donjonsFile = new DonjonsFile("donjons.yml", this);
		playerDataFile = new PlayerDataFile("playerdata.yml", this);
	}

	public static void reload() {
		donjonsFile.reload();
		playerDataFile.reload();
	}
}
