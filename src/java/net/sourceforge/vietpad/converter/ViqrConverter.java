/*
 * VIQR-to-Unicode conversion
 *
 * @author     Quan Nguyen
 * @author     Gero Herrmann
 * @version    1.2, 21 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class ViqrConverter extends Converter {
    
    final String[] VIQR_char = {"y~", "Y~", "y?", "Y?", "y.", "Y.", "y`", "Y`", "u+.", "U+.", "u+~",
            "U+~", "u+?", "U+?", "u+`", "U+`", "u+'", "U+'", "u?", "U?", "u.", "U.", "o+.", "O+.",
            "o+~", "O+~", "o+?", "O+?", "o+`", "O+`", "o+'", "O+'", "o^.", "O^.", "o^~", "O^~", "o^?",
            "O^?", "o^`", "O^`", "o^'", "O^'", "o?", "O?", "o.", "O.", "i.", "I.", "i?", "I?", "e^.",
            "E^.", "e^~", "E^~", "e^?", "E^?", "e^`", "E^`", "e^'", "E^'", "e~", "E~", "e?", "E?", "e.",
            "E.", "a(.", "A(.", "a(~", "A(~", "a(?", "A(?", "a(`", "A(`", "a('", "A('", "a^.", "A^.",
            "a^~", "A^~", "a^?", "A^?", "a^`", "A^`", "a^'", "A^'", "a?", "A?", "a.", "A.", "u+", "U+",
            "o+", "O+", "u~", "U~", "i~", "I~", "dd", "a(", "A(", "y'", "u'", "u`", "o~", "o^", "o'",
            "o`", "i'", "i`", "e^", "e'", "e`", "a~", "a^", "a'", "a`", "Y'", "U'", "U`", "O~", "O^",
            "O'", "O`", "DD", "I'", "I`", "E^", "E'", "E`", "A~", "A^", "A'", "A`"};
    final String[] Unicode_char = {"\u1EF9", "\u1EF8", "\u1EF7", "\u1EF6", "\u1EF5", "\u1EF4",
            "\u1EF3", "\u1EF2", "\u1EF1", "\u1EF0", "\u1EEF", "\u1EEE", "\u1EED", "\u1EEC", "\u1EEB",
            "\u1EEA", "\u1EE9", "\u1EE8", "\u1EE7", "\u1EE6", "\u1EE5", "\u1EE4", "\u1EE3", "\u1EE2",
            "\u1EE1", "\u1EE0", "\u1EDF", "\u1EDE", "\u1EDD", "\u1EDC", "\u1EDB", "\u1EDA", "\u1ED9",
            "\u1ED8", "\u1ED7", "\u1ED6", "\u1ED5", "\u1ED4", "\u1ED3", "\u1ED2", "\u1ED1", "\u1ED0",
            "\u1ECF", "\u1ECE", "\u1ECD", "\u1ECC", "\u1ECB", "\u1ECA", "\u1EC9", "\u1EC8", "\u1EC7",
            "\u1EC6", "\u1EC5", "\u1EC4", "\u1EC3", "\u1EC2", "\u1EC1", "\u1EC0", "\u1EBF", "\u1EBE",
            "\u1EBD", "\u1EBC", "\u1EBB", "\u1EBA", "\u1EB9", "\u1EB8", "\u1EB7", "\u1EB6", "\u1EB5",
            "\u1EB4", "\u1EB3", "\u1EB2", "\u1EB1", "\u1EB0", "\u1EAF", "\u1EAE", "\u1EAD", "\u1EAC",
            "\u1EAB", "\u1EAA", "\u1EA9", "\u1EA8", "\u1EA7", "\u1EA6", "\u1EA5", "\u1EA4", "\u1EA3",
            "\u1EA2", "\u1EA1", "\u1EA0", "\u01B0", "\u01AF", "\u01A1", "\u01A0", "\u0169", "\u0168",
            "\u0129", "\u0128", "\u0111", "\u0103", "\u0102", "\u00FD", "\u00FA", "\u00F9", "\u00F5",
            "\u00F4", "\u00F3", "\u00F2", "\u00ED", "\u00EC", "\u00EA", "\u00E9", "\u00E8", "\u00E3",
            "\u00E2", "\u00E1", "\u00E0", "\u00DD", "\u00DA", "\u00D9", "\u00D5", "\u00D4", "\u00D3",
            "\u00D2", "\u0110", "\u00CD", "\u00CC", "\u00CA", "\u00C9", "\u00C8", "\u00C3", "\u00C2",
            "\u00C1", "\u00C0"};
                   
    /**
     *  Converts VIQR to Unicode.
     *
     * @param str     Source string
     * @param html    True if HTML code
     * @return        Unicode string
     */
    public String convert(String str, boolean html) {
        if (html) {
            str = convertHTML(str);
        }

        // adjust irregular characters to VIQR standard
        str = str.replaceAll("(?i)(?<=[uo])\\*", "+").replaceAll("(?i)(d)([-d])", "$1$1")

        // replace right single quotation mark (\u0092, or \u2019) with apostrophe
                 .replace('\u2019', '\'')

        // Attempt to fix the problem with . and ? punctuation marks becoming tone marks.
        // This, however, is commented out because it may interfere with correct conversions
        // when a proper name (hence capitalized) instead of a capital letter beginning a sentence is encountered.
//                 .replaceAll("(?=[?.]\\s+\\p{Upper})", "\\\\")

        // change tone marks to punctuation marks if ' or . or ? is before a whitespace
        // and after a vowel which in turn is after a vowel and any one or two marks `?~'.^(+ , or ae        
                 .replaceAll("(?i)(?<=(?:(?:[aeiouy][`?~'.^(+]{1,2})|[ae])[aeiouy])(?=[?'.](?:\\s|$|\\p{Punct}))", "\\\\");

        // convert to Unicode
        str = replaceString(str, VIQR_char, Unicode_char);

        // delete redundant '\' characters
        str = str.replaceAll("(?i)\\\\(?=[-.?'d\\\\])", "");
        
        return cleanupURL(str);
    }

    /**
     *  Corrects invalid characters in URLs
     */
    String cleanupURL(String str) {
        StringBuilder sb = new StringBuilder(str);
        int startIndex = 0;
        int foundIndex;

        // correct characters in URLs, they can't be non-ASCII
        try {
            while ((foundIndex = sb.indexOf("://", startIndex)) != -1) {
                startIndex = foundIndex + 3;
                // Look for a pattern to replace
                char ch;

                while ((ch = sb.charAt(startIndex)) != ' ' && ch != '\n') {
                    if (ch >= '\u1EA0') { // A.
                        String replace = null;
                        switch (ch) {
                            case '\u1EA1': replace = "a."; break;
                            case '\u1EB9': replace = "e."; break;
                            case '\u1ECB': replace = "i."; break;
                            case '\u1ECD': replace = "o."; break;
                            case '\u1EE5': replace = "u."; break;
                            case '\u1EF5': replace = "y."; break;
                            case '\u1EA0': replace = "A."; break;
                            case '\u1EB8': replace = "E."; break;
                            case '\u1ECA': replace = "I."; break;
                            case '\u1ECC': replace = "O."; break;
                            case '\u1EE4': replace = "U."; break;
                            case '\u1EF4': replace = "Y."; break;
                            default: break;
                        }
                        if (replace != null) {
                            sb.replace(startIndex, startIndex + 1, replace);
                            startIndex++;
                        }
                    }
                    startIndex++;
                }
            }
        } catch (StringIndexOutOfBoundsException exc) {
//            exc.printStackTrace();
        } finally {
            return sb.toString();
        }
    }    
}
