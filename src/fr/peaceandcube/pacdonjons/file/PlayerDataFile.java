package fr.peaceandcube.pacdonjons.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.peaceandcube.pacdonjons.command.DonjonMessages;

public class PlayerDataFile {
	protected static File file;
	protected static FileConfiguration config;
	
	public static void load(Plugin plugin, String name) {
		file = new File(plugin.getDataFolder(), name);
		
		if (!file.exists()) {
			plugin.getDataFolder().mkdirs();
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	protected static void save() {
		try {
			saveToDisk();
		} catch (IOException ex) {
			ex.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "Unable to save data file!");
		}
	}
	
	private static void saveToDisk() throws IOException {
		if (config != null && file != null) {
			config.save(file);
		}
	}
	
	public static List<String> getDonjons(Player player, String prefix) {
		String uuid = player.getUniqueId().toString();
		ConfigurationSection section = config.getConfigurationSection(uuid);
		if (section != null) {
			Set<String> donjonKeys = section.getKeys(false);
			List<String> donjons = new ArrayList<String>();
			for (String donjon : donjonKeys) {
				if (donjon.toLowerCase().startsWith(prefix.toLowerCase())) {
					donjons.add(donjon);
				}
			}
			return donjons;
		}
		return new ArrayList<String>();
	}
	
	public static void addDonjonStep(String target, String donjon, String step) {
		ConfigurationSection section;
		if (config.getConfigurationSection(target) != null) {
			section = config.getConfigurationSection(target);
		} else {
			section = config.createSection(target);
		}
		section.set(donjon, step);
		save();
	}
	
	public static void removeDonjonStep(String target, String donjon) {
		ConfigurationSection section = config.getConfigurationSection(target);
		if (section != null) {
			if (section.getKeys(false).size() > 1) {
				section.set(donjon, null);
			} else {
				config.set(target, null);
			}
		}
		save();
	}
	
	public static void teleportToDonjonStep(Player player, String donjon) {
		String uuid = player.getUniqueId().toString();
		String step = config.getConfigurationSection(uuid).getString(donjon);
		if (DonjonsFile.getDonjons("").contains(donjon)) {
			if (DonjonsFile.getSteps(donjon, "").contains(step)) {
				DonjonsFile.teleportToDonjonStep(player, donjon, step);
				player.sendMessage(String.format(DonjonMessages.TELEPORTED, donjon));
			} else {
				player.sendMessage(DonjonMessages.STEP_NOT_FOUND);
			}
		} else {
			player.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
		}
	}
}
