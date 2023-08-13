/*
 * @version 0.0 03.05.2009
 * @author Tobse F
 */
package lib.mylib.version;

import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Returns Date and version as string of a "version" file which have to be locatet in src main
 * dir or in jar.</br> version file syntax:
 *
 * <pre>
 * #Build info			#... (info)
 * #Sun May 03 10:40:20 CEST 2009	#... (date)
 * version=v0.6			version=... (version)
 * </pre>
 *
 * @author Tobse
 */
public final class Version {

    private static String version = null;
    private static String date = null;
    private static final String VERSION_FILE = "versionfile";

    private Version() {
    }

    /**
     * Reads the game version out of a special 'version' file in src main dir.
     *
     * @return The version <code>String</code>. null if not available.
     * @see Version
     */
    public static String getVersion() {
        if (version == null) {
            Scanner scanner = getScanner();
            if (scanner != null) {
                try {
                    version = scanner.next().trim();
                } catch (NoSuchElementException e) {
                    Log.warn("Failed loading version file");
                }
            }
        }
        return version;
    }

    /**
     * @return The date <code>String</code>. null if not available.
     * @see Version
     */
    public static String getDate() {
        if (date == null) {
            Scanner scanner = getScanner().useDelimiter("#");
            if (scanner != null) {
                try {
                    scanner.next();
                    date = scanner.next().replaceAll("\n.*", "").trim();
                } catch (NoSuchElementException e) {
                }
            }
            scanner.close();
        }
        return date;
    }

    /**
     * Tries to find the {@link #VERSION_FILE} in classpaph and in source folder. Returns a
     * <code>Scanner</code> loaded with the file, or <code>null</code> if file was not found
     *
     * @return <code>Scanner</code> loaded with the{@link #VERSION_FILE}
     */
    private static Scanner getScanner() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(ResourceLoader.getResourceAsStream(VERSION_FILE));
        } catch (RuntimeException e) {
            e.printStackTrace();
            try {
                scanner = new Scanner(ResourceLoader
                        .getResourceAsStream("src/" + VERSION_FILE));
            } catch (RuntimeException e2) {
                e.printStackTrace();
            }
        }
        return scanner;
    }

}
