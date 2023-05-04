package fr.peaceandcube.pacdonjons.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class Messages {
	public static final TextComponent DONJON_NOT_FOUND = error("Le donjon n'a pas été trouvé.");
	public static final TextComponent STEP_NOT_FOUND = error("L'étape du donjon n'a pas été trouvée.");
	public static final TextComponent NO_DONJON_STEP = error("Le donjon n'a aucune étape.");

	public static final String DONJON_ADDED = "Le donjon %s a été ajouté.";
	public static final String DONJON_REMOVED = "Le donjon %s a été supprimé.";
	public static final String STEP_ADDED = "L'étape %s a été ajoutée au donjon %s.";
	public static final String STEP_REMOVED = "L'étape %s a été supprimée du donjon %s.";
	public static final String PLAYER_STEP_ADDED = "L'étape %s du donjon %s a été enregistrée pour %s.";
	public static final String PLAYER_STEP_REMOVED = "Le donjon %s n'est plus enregistré pour %s.";

	public static final String STEP_INFO = "Infos de l'étape %s du donjon %s : ";

	public static final String DONJON_LIST = "Liste des donjons : ";
	public static final String STEP_LIST = "Liste des étapes du donjon %s : ";

	public static final String TELEPORTED = "Tu as été téléporté dans le donjon %s !";

	public static TextComponent error(String msg) {
		return Component.text(msg, TextColor.color(0xFF5555));
	}

	public static TextComponent success(String msg) {
		return Component.text(msg, TextColor.color(0xFFFF55));
	}
}
