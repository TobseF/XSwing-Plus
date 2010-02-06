/*
 * @version 0.0 18.02.2009
 * @author Tobse F
 */
package lib.mylib.util;

import static lib.mylib.options.Paths.RES_DIR;
import java.io.*;
import java.util.*;
import org.newdawn.slick.util.*;

public class LanguageSelector {

	private static LanguageSelector selector;
	private static String selectedLanguage = "English";
	private static String languageFileLocation = RES_DIR + "languages/";
	private static String LanguageFileExtension = ".properties";
	private static Properties langProperties;

	private LanguageSelector(String language) {
		langProperties = new Properties();
		try {
			langProperties.load(ResourceLoader.getResourceAsStream(languageFileLocation
					+ language + LanguageFileExtension));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		selectedLanguage = language;
		if (selectedLanguage.equals("German")) {
			Locale.setDefault(Locale.GERMAN);
		} else {
			Locale.setDefault(Locale.ENGLISH);
		}
	}

	public static String getSelectedLanguage() {
		return selectedLanguage;
	}

	public static String setSystemLanguage() {
		String language = System.getProperty("user.language"); // TODO: load mapping from
		// property file
		if (language.equals("de")) {
			setLanguage("German");
			Locale.setDefault(Locale.GERMAN);
		} else {
			setLanguage("English");
			Locale.setDefault(Locale.ENGLISH);
		}
		return selectedLanguage;
	}

	public static String getString(String string) {
		if (selector == null) {
			selector = new LanguageSelector("English");
		}
		return langProperties.getProperty(string);
	}

	public static Locale getLocale() {
		return Locale.getDefault();
	}

	public static void setLanguage(String language) {
		Log.info("Set Language to " + language);
		selector = new LanguageSelector(language);
	}

}