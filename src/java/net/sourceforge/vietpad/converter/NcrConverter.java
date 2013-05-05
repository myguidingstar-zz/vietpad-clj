/*
 * Numeric Character References-to-Unicode conversion
 *
 * @author     Quan Nguyen
 * @author     Gero Herrmann
 * @version    1.3, 28 January 06
 */

package net.sourceforge.vietpad.converter;

public class NcrConverter extends Converter {
    /**
     * Converts NCR to Unicode.
     *
     * @param str     Source string in TCVN3 encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */
    public String convert(String str, boolean html) {
        if (html) {
            str = prepareMetaTag(
                    htmlToANSI(str));
        }
                   
        return convertNCR(str);
    }    
}
