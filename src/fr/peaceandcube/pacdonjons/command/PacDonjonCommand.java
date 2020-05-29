package fr.peaceandcube.pacdonjons.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

import fr.peaceandcube.pacdonjons.file.DonjonsFile;
import fr.peaceandcube.pacdonjons.file.PlayerDataFile;
import fr.peaceandcube.pacpi.player.PlayerSuggestionProviders;

public class PacDonjonCommand implements CommandExecutor, TabExecutor {
	private static final List<String> OPERATIONS = ImmutableList.of("add", "remove", "addstep", "removestep", "set");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pacdonjons.pacdonjon")) {
			if (args.length >= 1) {
				switch (args[0]) {
				case "add":
					if (args.length == 3 && sender instanceof Player) {
						String name = args[1];
						String author = args[2];
						DonjonsFile.add(name, author);
						sender.sendMessage(String.format(DonjonMessages.DONJON_ADDED, name));
					}
					return true;
				case "remove":
					if (args.length == 2) {
						String donjon = args[1];
						if (DonjonsFile.getDonjons("").contains(donjon)) {
							DonjonsFile.remove(donjon);
							sender.sendMessage(String.format(DonjonMessages.DONJON_REMOVED, donjon));
						} else {
							sender.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "addstep":
					if (args.length == 3 && sender instanceof Player) {
						Player player = (Player) sender;
						String donjon = args[1];
						String name = args[2];
						if (DonjonsFile.getDonjons("").contains(donjon)) {
							DonjonsFile.addStep(donjon, name, player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
							sender.sendMessage(String.format(DonjonMessages.STEP_ADDED, name, donjon));
						} else {
							sender.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "removestep":
					if (args.length == 3) {
						String donjon = args[1];
						String step = args[2];
						if (DonjonsFile.getDonjons("").contains(donjon)) {
							if (DonjonsFile.getSteps(donjon, "").contains(step)) {
								DonjonsFile.removeStep(donjon, step);
								sender.sendMessage(String.format(DonjonMessages.STEP_REMOVED, step, donjon));
							} else {
								sender.sendMessage(DonjonMessages.STEP_NOT_FOUND);
							}
						} else {
							sender.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "set":
					if (args.length == 3 || args.length == 4) {
						String target = args[1];
						String donjon = args[2];
						if (DonjonsFile.getDonjons("").contains(donjon)) {
							if (args.length == 4) {
								String step = args[3];
								if (DonjonsFile.getSteps(donjon, "").contains(step)) {
									String uuid = Bukkit.getPlayer(target).getUniqueId().toString();
									PlayerDataFile.addDonjonStep(uuid, donjon, step);
									sender.sendMessage(String.format(DonjonMessages.PLAYER_STEP_ADDED, step, donjon, target));
								} else {
									sender.sendMessage(DonjonMessages.STEP_NOT_FOUND);
								}
							} else {
								String uuid = Bukkit.getPlayer(target).getUniqueId().toString();
								PlayerDataFile.removeDonjonStep(uuid, donjon);
								sender.sendMessage(String.format(DonjonMessages.PLAYER_STEP_REMOVED, donjon, target));
							}
						} else {
							sender.sendMessage(DonjonMessages.DONJON_NOT_FOUND);
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pacdonjons.pacdonjon")) {
			switch (args.length) {
			case 1:
				List<String> ops = new ArrayList<String>();
				for (String op : OPERATIONS) {
					if (op.toLowerCase().startsWith(args[0].toLowerCase())) {
						ops.add(op);
					}
				}
				return ops;
			case 2:
				switch (args[0]) {
				default:
					return new ArrayList<String>();
				case "remove":
				case "addstep":
				case "removestep":
					return DonjonsFile.getDonjons(args[1]);
				case "set":
					return PlayerSuggestionProviders.getOnlinePlayers(args[1]);
				}
			case 3:
				switch (args[0]) {
				default:
					return new ArrayList<String>();
				case "add":
					return PlayerSuggestionProviders.getOnlinePlayers(args[2]);
				case "removestep":
					return DonjonsFile.getSteps(args[1], args[2]);
				case "set":
					return DonjonsFile.getDonjons(args[2]);
				}
			case 4:
				switch (args[0]) {
				default:
					return new ArrayList<String>();
				case "set":
					return DonjonsFile.getSteps(args[2], args[3]);
				}
			}
		}
		return new ArrayList<String>();
	}
}
