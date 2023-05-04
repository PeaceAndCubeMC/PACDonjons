package fr.peaceandcube.pacdonjons.command;

import fr.peaceandcube.pacdonjons.PACDonjons;
import fr.peaceandcube.pacdonjons.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DonjonCommand implements CommandExecutor, TabExecutor {
	private static final String PERM_DONJON = "peaceandcube.donjon";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (sender.hasPermission(PERM_DONJON) && sender instanceof Player player) {
			if (args.length == 1) {
				String donjon = args[0];
				if (PACDonjons.playerDataFile.getDonjons(player, "").contains(donjon)) {
					PACDonjons.playerDataFile.teleportToDonjonStep(player, donjon);
				} else {
					sender.sendMessage(Messages.DONJON_NOT_FOUND);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
		if (sender.hasPermission(PERM_DONJON) && sender instanceof Player player) {
			if (args.length == 1) {
				return PACDonjons.playerDataFile.getDonjons(player, args[0]).stream().filter(s -> s.startsWith(args[0])).toList();
			}
		}
		return List.of();
	}
}
