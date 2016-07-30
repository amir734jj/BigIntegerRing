package com.hesamian.ring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a utility class that makes it possible to use indexOf(Regex).
 * 
 * @author Seyedamirhossein Hesamian
 * @since 08/30/2016
 */

public class Utility {

    /**
     * This method returns index of pattern in a String.
     * 
     * @param Regex
     *            pattern (String) and source String
     * @param None
     * @return Index of pattern (regex) in a String
     */
    public static int indexOf(String pattern, String str) {
        return indexOf(Pattern.compile(pattern), str);
    }

    /**
     * This method returns index of pattern in a String.
     * 
     * @param Regex
     *            pattern (Pattern) and source String
     * @param None
     * @return Index of pattern (regex) in a String
     */
    public static int indexOf(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.start() : -1;
    }
}
