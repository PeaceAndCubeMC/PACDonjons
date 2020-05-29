package fr.peaceandcube.pacdonjons.command;

import org.bukkit.ChatColor;

public class DonjonMessages {

	public static final String DONJON_NOT_FOUND = ChatColor.RED + "Le donjon n'a pas �t� trouv�.";
	public static final String STEP_NOT_FOUND = ChatColor.RED + "L'�tape du donjon n'a pas �t� trouv�e.";
	
	public static final String DONJON_ADDED = ChatColor.YELLOW + "Le donjon %s a �t� ajout�.";
	public static final String DONJON_REMOVED = ChatColor.YELLOW + "Le donjon %s a �t� supprim�.";
	public static final String STEP_ADDED = ChatColor.YELLOW + "L'�tape %s a �t� ajout�e au donjon %s.";
	public static final String STEP_REMOVED = ChatColor.YELLOW + "L'�tape %s a �t� supprim�e du donjon %s.";
	public static final String PLAYER_STEP_ADDED = ChatColor.YELLOW + "L'�tape %s du donjon %s a �t� enregistr�e pour %s.";
	public static final String PLAYER_STEP_REMOVED = ChatColor.YELLOW + "Le donjon %s n'est plus enregistr� pour %s.";
	
	public static final String TELEPORTED = ChatColor.YELLOW + "Tu as t�l�port� dans le donjon %s !";
}
