package fr.peaceandcube.pacdonjons.file;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DonjonsFile extends YamlFile {

	public DonjonsFile(String name, Plugin plugin) {
		super(name, plugin);
	}

	public List<String> getDonjons(String prefix) {
		Set<String> donjonKeys = config.getKeys(false);
		if (!donjonKeys.isEmpty()) {
			List<String> donjons = new ArrayList<String>();
			for (String donjon : donjonKeys) {
				if (donjon.toLowerCase().startsWith(prefix.toLowerCase())) {
					donjons.add(donjon);
				}
			}
			return donjons;
		}
		return List.of();
	}

	public List<String> getSteps(String donjon, String prefix) {
		Set<String> stepKeys = config.getConfigurationSection(donjon).getConfigurationSection("steps").getKeys(false);
		if (!stepKeys.isEmpty()) {
			List<String> steps = new ArrayList<String>();
			for (String step : stepKeys) {
				if (step.toLowerCase().startsWith(prefix.toLowerCase())) {
					steps.add(step);
				}
			}
			return steps;
		}
		return List.of();
	}

	public List<String> getStepInfo(String donjon, String step) {
		ConfigurationSection section = config.getConfigurationSection(donjon).getConfigurationSection("steps").getConfigurationSection(step);
		Set<String> infoKeys = section.getKeys(false);
		if (!infoKeys.isEmpty()) {
			List<String> infos = new ArrayList<String>();
			for (String info : infoKeys) {
				infos.add(section.getString(info));
			}
			return infos;
		}
		return List.of();
	}

	public void add(String name, String author) {
		ConfigurationSection section = config.createSection(name);
		section.set("author", author);
		section.createSection("steps");
		save();
	}

	public void remove(String donjon) {
		config.set(donjon, null);
		save();
	}

	public void addStep(String donjon, String name, String world, int x, int y, int z) {
		ConfigurationSection section = config.getConfigurationSection(donjon).getConfigurationSection("steps");
		ConfigurationSection stepSection = section.createSection(name);
		stepSection.set("world", world);
		stepSection.set("x", x);
		stepSection.set("y", y);
		stepSection.set("z", z);
		save();
	}

	public void removeStep(String donjon, String step) {
		ConfigurationSection section = config.getConfigurationSection(donjon).getConfigurationSection("steps");
		section.set(step, null);
		save();
	}

	public void teleportToDonjonStep(Player player, String donjon, String step) {
		ConfigurationSection section = config.getConfigurationSection(donjon).getConfigurationSection("steps").getConfigurationSection(step);
		World world = Bukkit.getWorld(section.getString("world"));
		int x = section.getInt("x");
		int y = section.getInt("y");
		int z = section.getInt("z");
		player.teleport(new Location(world, x, y, z), TeleportCause.COMMAND);
	}
}
