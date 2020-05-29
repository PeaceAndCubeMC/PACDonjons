package fr.peaceandcube.pacdonjons;

import org.bukkit.plugin.java.JavaPlugin;

import fr.peaceandcube.pacdonjons.command.DonjonCommand;
import fr.peaceandcube.pacdonjons.command.PacDonjonCommand;
import fr.peaceandcube.pacdonjons.file.DonjonsFile;
import fr.peaceandcube.pacdonjons.file.PlayerDataFile;

public class PACDonjons extends JavaPlugin {

	@Override
	public void onEnable() {
		this.getCommand("pacdonjon").setExecutor(new PacDonjonCommand());
		this.getCommand("donjon").setExecutor(new DonjonCommand());
		
		DonjonsFile.load(this, "donjons.yml");
		PlayerDataFile.load(this, "playerdata.yml");
	}
}
