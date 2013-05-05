/*
 * Converter.java
 *
 * @author     Quan Nguyen
 * @author     Gero Herrmann
 * @version    1.3, 28 January 06
 */

package net.sourceforge.vietpad.converter;

import java.text.Normalizer;

public abstract class Converter {

    // Fonts for HTML font tags
    final static String SERIF = "Times New Roman";
    final static String SANS_SERIF = "Arial";

    /**
     *  Converts legacy text to Unicode. To be implemented by subclass.
     */
    public abstract String convert(String source, boolean html);
    
    /**
     *  Multiple String replacement.
     *
     *@param  text     Text to be performed on
     *@param  pattern  Find text
     *@param  replace  Replace text
     *@return          Result text
     */
    String replaceString(String text, final String[] pattern, final String[] replace) {
        int startIndex;
        int foundIndex;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < pattern.length; i++) {
            startIndex = 0;
            // Clear the buffer
            result.setLength(0);

            // Look for a pattern to replace
            while ((foundIndex = text.indexOf(pattern[i], startIndex)) >= 0) {
                result.append(text.substring(startIndex, foundIndex));
                result.append(replace[i]);
                startIndex = foundIndex + pattern[i].length();
            }
            result.append(text.substring(startIndex));
            text = result.toString();
        }
        return text;
    }

    /**
     *  Changes HTML meta tag for charset to UTF-8.
     */
    String prepareMetaTag(String str) {
                // delete existing charset attribute in meta tag
        return str.replaceAll("(?i)charset=(?:iso-8859-1|windows-1252|windows-1258|us-ascii|x-user-defined)", "")
                // delete the rest of the meta tag
                .replaceAll("(?i)<meta http-equiv=\"?Content-Type\"? content=\"text/html;\\s*\">\\n?", "")
                // insert new meta tag with UTF-8 charset
                .replaceAll("(?i)<head>", "<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
    }


    /**
     *  Translates Character entity references to corresponding Cp1252 characters.
     */
    String htmlToANSI(String str) {
        final String[] extended_ansi_html = {"&trade;", "&#8209;", "&nbsp;", 
                "&iexcl;", "&cent;", "&pound;", "&curren;", "&yen;", "&brvbar;", "&sect;", "&uml;", "&copy;", "&ordf;",
                "&laquo;", "&not;", "&shy;", "&reg;", "&macr;", "&deg;", "&plusmn;", "&sup2;", "&sup3;",
                "&acute;", "&micro;", "&para;", "&middot;", "&cedil;", "&sup1;", "&ordm;", "&raquo;",
                "&frac14;", "&frac12;", "&frac34;", "&iquest;", "&Agrave;", "&Aacute;", "&Acirc;",
                "&Atilde;", "&Auml;", "&Aring;", "&AElig;", "&Ccedil;", "&Egrave;", "&Eacute;", "&Ecirc;",
                "&Euml;", "&Igrave;", "&Iacute;", "&Icirc;", "&Iuml;", "&ETH;", "&Ntilde;", "&Ograve;",
                "&Oacute;", "&Ocirc;", "&Otilde;", "&Ouml;", "&times;", "&Oslash;", "&Ugrave;", "&Uacute;",
                "&Ucirc;", "&Uuml;", "&Yacute;", "&THORN;", "&szlig;", "&agrave;", "&aacute;", "&acirc;",
                "&atilde;", "&auml;", "&aring;", "&aelig;", "&ccedil;", "&egrave;", "&eacute;", "&ecirc;",
                "&euml;", "&igrave;", "&iacute;", "&icirc;", "&iuml;", "&eth;", "&ntilde;", "&ograve;",
                "&oacute;", "&ocirc;", "&otilde;", "&ouml;", "&divide;", "&oslash;", "&ugrave;", "&uacute;",
                "&ucirc;", "&uuml;", "&yacute;", "&thorn;", "&yuml;"};
        final String[] extended_ansi = {"\u0099", "\u2011", "\u00A0",
                "\u00A1", "\u00A2", "\u00A3", "\u00A4", "\u00A5", "\u00A6", "\u00A7", "\u00A8", "\u00A9",
                "\u00AA", "\u00AB", "\u00AC", "\u00AD", "\u00AE", "\u00AF", "\u00B0", "\u00B1", "\u00B2",
                "\u00B3", "\u00B4", "\u00B5", "\u00B6", "\u00B7", "\u00B8", "\u00B9", "\u00BA", "\u00BB",
                "\u00BC", "\u00BD", "\u00BE", "\u00BF", "\u00C0", "\u00C1", "\u00C2", "\u00C3", "\u00C4",
                "\u00C5", "\u00C6", "\u00C7", "\u00C8", "\u00C9", "\u00CA", "\u00CB", "\u00CC", "\u00CD",
                "\u00CE", "\u00CF", "\u00D0", "\u00D1", "\u00D2", "\u00D3", "\u00D4", "\u00D5", "\u00D6",
                "\u00D7", "\u00D8", "\u00D9", "\u00DA", "\u00DB", "\u00DC", "\u00DD", "\u00DE", "\u00DF",
                "\u00E0", "\u00E1", "\u00E2", "\u00E3", "\u00E4", "\u00E5", "\u00E6", "\u00E7", "\u00E8",
                "\u00E9", "\u00EA", "\u00EB", "\u00EC", "\u00ED", "\u00EE", "\u00EF", "\u00F0", "\u00F1",
                "\u00F2", "\u00F3", "\u00F4", "\u00F5", "\u00F6", "\u00F7", "\u00F8", "\u00F9", "\u00FA",
                "\u00FB", "\u00FC", "\u00FD", "\u00FE", "\u00FF"};

        return replaceString(str, extended_ansi_html, extended_ansi);
    }
    
    /**
     *  Converts Numeric Character References and Unicode escape sequences to Unicode.
     */
    String convertNCR(String str) {     
        final String[] NCRs = {"&#x", "&#", "\\u", "U+", "#x", "#"};
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < NCRs.length; i++) {
            int radix;
            int foundIndex;
            int startIndex = 0;        
            final int STR_LENGTH = str.length();
            final String NCR = NCRs[i]; 
            final int NCR_LENGTH = NCR.length();
            
            if (NCR == "&#" || NCR == "#") {
                radix = 10; 
            } else {
                radix = 16;
            }
                                  
            while (startIndex < STR_LENGTH) {
                foundIndex = str.indexOf(NCR, startIndex);

                if (foundIndex == -1) {
                    result.append(str.substring(startIndex));
                    break;
                }

                result.append(str.substring(startIndex, foundIndex));
                if (NCR == "\\u" || NCR == "U+") {
                    startIndex = foundIndex + 6;
                    if (startIndex > str.length()) startIndex = -1; // for invalid Unicode escape sequences
                } else {
                    startIndex = str.indexOf(";", foundIndex);
                }

                if (startIndex == -1) {
                    result.append(str.substring(foundIndex));
                    break;
                }

                String tok = str.substring(foundIndex + NCR_LENGTH, startIndex);

                try {
                    result.append((char) Integer.parseInt(tok, radix));
                } catch (NumberFormatException nfe) {
                    try {
                        if (NCR == "\\u" || NCR == "U+") {
                            result.append(NCR + tok);
                        } else {
                            result.append(NCR + tok + str.charAt(startIndex));                            
                        }
                    } catch (StringIndexOutOfBoundsException sioobe) {
                        result.append(NCR + tok);
                    }
                }

                if (NCR != "\\u" && NCR != "U+") {
                    startIndex++;
                }
            }

            str = result.toString();
            result.setLength(0);
        }
        return str;
    }
    
    /**
     * Converts Cp1252 characters in \u0080-\u009F range to pure hex.
     * This method is required for VISCII and VPS because these encodings
     * utilize characters in this range.
     */    
    String cp1252ToHex(String str) {
        final char[] cha = {'\u20AC', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021',
                            '\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\u017D',
                            '\u2018', '\u2019', '\u201C', '\u201D', '\u2022', '\u2013', '\u2014',
                            '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\u017E', '\u0178'
                            };
        final char[] hex = {'\u0080', '\u0082', '\u0083', '\u0084', '\u0085', '\u0086', '\u0087', 
                            '\u0088', '\u0089', '\u008A', '\u008B', '\u008C', '\u008E',
                            '\u0091', '\u0092', '\u0093', '\u0094', '\u0095', '\u0096', '\u0097', 
                            '\u0098', '\u0099', '\u009A', '\u009B', '\u009C', '\u009E', '\u009F'
                            };
        for (int i = 0; i < hex.length; i++) {
            str = str.replace(cha[i], hex[i]);
        }
        
        return str;
    }
    
    /**
     *  Unicode Composite-to-Unicode Precomposed conversion (NFD -> NFC).
     */    
    String compositeToPrecomposed(String str) {
        // Perform Unicode NFC on NFD string
        return Normalizer.normalize(str, Normalizer.Form.NFC);
    }
    
    /**
     * Converts HTML.
     */
    String convertHTML(String str) {
        return replaceFont(
                prepareMetaTag(
                convertNCR(
                htmlToANSI(str))));
    }
    
    /**
     * Replaces fonts.
     */
    String replaceFont(String str) {
        // to be overridden by subclass when necessary
        return str;
    }
}
