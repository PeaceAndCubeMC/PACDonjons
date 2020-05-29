package fr.peaceandcube.pacdonjons.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.peaceandcube.pacdonjons.file.PlayerDataFile;

public class DonjonCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pacdonjons.donjon") && sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 1) {
				String donjon = args[0];
				if (PlayerDataFile.getDonjons(player, "").contains(donjon)) {
					PlayerDataFile.teleportToDonjonStep(player, donjon);
				} else {
					sender.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pacdonjons.donjon") && sender instanceof Player) {
			Player player = (Player) sender;
			switch (args.length) {
			default:
				return new ArrayList<String>();
			case 1:
				return PlayerDataFile.getDonjons(player, args[0]);
			}
		}
		return new ArrayList<>();
	}
}
