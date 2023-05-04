package fr.peaceandcube.pacdonjons.file;

import fr.peaceandcube.pacdonjons.PACDonjons;
import fr.peaceandcube.pacdonjons.util.Messages;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerDataFile extends YamlFile {

	public PlayerDataFile(String name, Plugin plugin) {
		super(name, plugin);
	}

	public List<String> getDonjons(Player player, String prefix) {
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
		return List.of();
	}

	public void addDonjonStep(String target, String donjon, String step) {
		ConfigurationSection section;
		if (config.getConfigurationSection(target) != null) {
			section = config.getConfigurationSection(target);
		} else {
			section = config.createSection(target);
		}
		section.set(donjon, step);
		save();
	}

	public void removeDonjonStep(String target, String donjon) {
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

	public void teleportToDonjonStep(Player player, String donjon) {
		String uuid = player.getUniqueId().toString();
		String step = config.getConfigurationSection(uuid).getString(donjon);
		if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
			if (PACDonjons.donjonsFile.getSteps(donjon, "").contains(step)) {
				PACDonjons.donjonsFile.teleportToDonjonStep(player, donjon, step);
				player.sendMessage(Messages.success(String.format(Messages.TELEPORTED, donjon)));
			} else {
				player.sendMessage(Messages.STEP_NOT_FOUND);
			}
		} else {
			player.sendMessage(Messages.DONJON_NOT_FOUND);
		}
	}
}
