package fr.peaceandcube.pacdonjons.command;

import fr.peaceandcube.pacdonjons.PACDonjons;
import fr.peaceandcube.pacdonjons.util.Messages;
import fr.peaceandcube.pacdonjons.util.SuggestionProviders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PacDonjonCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_PACDONJON = "peaceandcube.pacdonjon";
	private static final List<String> OPERATIONS = List.of("add", "addstep", "info", "list", "reload", "remove", "removestep", "set");

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_PACDONJON)) {
			if (args.length >= 1) {
				switch (args[0]) {
				case "add":
					if (args.length == 3) {
						String name = args[1];
						String author = args[2];
						PACDonjons.donjonsFile.add(name, author);
						sender.sendMessage(Messages.success(String.format(Messages.DONJON_ADDED, name)));
					}
					return true;
				case "remove":
					if (args.length == 2) {
						String donjon = args[1];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							PACDonjons.donjonsFile.remove(donjon);
							sender.sendMessage(Messages.success(String.format(Messages.DONJON_REMOVED, donjon)));
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "addstep":
					if (args.length == 3 && sender instanceof Player player) {
						String donjon = args[1];
						String name = args[2];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							PACDonjons.donjonsFile.addStep(donjon, name, player.getWorld().getName(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
							sender.sendMessage(Messages.success(String.format(Messages.STEP_ADDED, name, donjon)));
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "removestep":
					if (args.length == 3) {
						String donjon = args[1];
						String step = args[2];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							if (PACDonjons.donjonsFile.getSteps(donjon, "").contains(step)) {
								PACDonjons.donjonsFile.removeStep(donjon, step);
								sender.sendMessage(Messages.success(String.format(Messages.STEP_REMOVED, step, donjon)));
							} else {
								sender.sendMessage(Messages.STEP_NOT_FOUND);
							}
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "set":
					if (args.length == 3 || args.length == 4) {
						String target = args[1];
						String donjon = args[2];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							if (args.length == 4) {
								String step = args[3];
								if (PACDonjons.donjonsFile.getSteps(donjon, "").contains(step)) {
									String uuid = Bukkit.getPlayer(target).getUniqueId().toString();
									PACDonjons.playerDataFile.addDonjonStep(uuid, donjon, step);
									sender.sendMessage(Messages.success(String.format(Messages.PLAYER_STEP_ADDED, step, donjon, target)));
								} else {
									sender.sendMessage(Messages.STEP_NOT_FOUND);
								}
							} else {
								String uuid = Bukkit.getPlayer(target).getUniqueId().toString();
								PACDonjons.playerDataFile.removeDonjonStep(uuid, donjon);
								sender.sendMessage(Messages.success(String.format(Messages.PLAYER_STEP_REMOVED, donjon, target)));
							}
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "info":
					if (args.length == 3) {
						String donjon = args[1];
						String step = args[2];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							if (PACDonjons.donjonsFile.getSteps(donjon, "").contains(step)) {
								List<String> infos = PACDonjons.donjonsFile.getStepInfo(donjon, step);
								sender.sendMessage(Messages.success(String.format(Messages.STEP_INFO, step, donjon)).append(Component.text(infos.toString(), TextColor.color(0xFFAA00))));
							} else {
								sender.sendMessage(Messages.STEP_NOT_FOUND);
							}
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
					}
					return true;
				case "list":
					if (args.length == 1) {
						List<String> donjons = PACDonjons.donjonsFile.getDonjons("");
						sender.sendMessage(Messages.success(Messages.DONJON_LIST).append(Component.text(donjons.toString(), TextColor.color(0xFFAA00))));
						return true;
					} else if (args.length == 2) {
						String donjon = args[1];
						if (PACDonjons.donjonsFile.getDonjons("").contains(donjon)) {
							List<String> steps = PACDonjons.donjonsFile.getSteps(donjon, "");
							if (!steps.isEmpty()) {
								sender.sendMessage(Messages.success(String.format(Messages.STEP_LIST, donjon)).append(Component.text(steps.toString(), TextColor.color(0xFFAA00))));
							} else {
								sender.sendMessage(Messages.NO_DONJON_STEP);
							}
						} else {
							sender.sendMessage(Messages.DONJON_NOT_FOUND);
						}
						return true;
					}
				case "reload":
					if (args.length == 1) {
						PACDonjons.reload();
						sender.sendMessage(Messages.RELOAD_SUCCESS);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (sender.hasPermission(PERM_PACDONJON)) {
			if (args.length == 1) {
				return OPERATIONS.stream().filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase())).toList();
			} else if (args.length == 2) {
				if (args[0].equals("set")) {
					return SuggestionProviders.getOnlinePlayers(args[1]);
				} else if (args[0].equals("remove") || args[0].equals("addstep") || args[0].equals("removestep") || args[0].equals("info") || args[0].equals("list")) {
					return PACDonjons.donjonsFile.getDonjons(args[1]);
				}
			} else if (args.length == 3) {
				switch (args[0]) {
					case "add":
						return SuggestionProviders.getOnlinePlayers(args[2]);
					case "removestep":
					case "info":
						return PACDonjons.donjonsFile.getSteps(args[1], args[2]);
					case "set":
						return PACDonjons.donjonsFile.getDonjons(args[2]);
				}
			} else if (args.length == 4) {
				if (args[0].equals("set")) {
					return PACDonjons.donjonsFile.getSteps(args[2], args[3]);
				}
			}
		}
		return List.of();
	}
}
