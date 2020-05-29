package fr.peaceandcube.pacdonjons.command;

import org.bukkit.ChatColor;

public class DonjonMessages {

	public static final String DONJON_NOT_FOUND = ChatColor.RED + "Le donjon n'a pas été trouvé.";
	public static final String STEP_NOT_FOUND = ChatColor.RED + "L'étape du donjon n'a pas été trouvée.";
	
	public static final String DONJON_ADDED = ChatColor.YELLOW + "Le donjon %s a été ajouté.";
	public static final String DONJON_REMOVED = ChatColor.YELLOW + "Le donjon %s a été supprimé.";
	public static final String STEP_ADDED = ChatColor.YELLOW + "L'étape %s a été ajoutée au donjon %s.";
	public static final String STEP_REMOVED = ChatColor.YELLOW + "L'étape %s a été supprimée du donjon %s.";
	public static final String PLAYER_STEP_ADDED = ChatColor.YELLOW + "L'étape %s du donjon %s a été enregistrée pour %s.";
	public static final String PLAYER_STEP_REMOVED = ChatColor.YELLOW + "Le donjon %s n'est plus enregistré pour %s.";
	
	public static final String TELEPORTED = ChatColor.YELLOW + "Tu as téléporté dans le donjon %s !";
}
