/*
 * Unicode Composite-to-Unicode Precomposed conversion (NFD -> NFC)
 *
 * @author     Quan Nguyen
 * @version    1.1, 16 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class CompositeConverter extends Converter {
    /**
     * Converts Unicode Composite to Unicode Precomposed (NFD -> NFC).
     *
     * @param str     Source string in Unicode Composite encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */    
    public String convert(String str, boolean html) {
        return compositeToPrecomposed(str);
    }    
}
