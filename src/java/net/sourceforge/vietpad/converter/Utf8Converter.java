/*
 * UTF8-to-Unicode conversion
 *
 * @author     Quan Nguyen
 * @version    1.3, 28 January 06
 */

package net.sourceforge.vietpad.converter;

public class Utf8Converter extends Converter {
    
    /**
     *  Converts UTF-8 to Unicode.
     *
     * @param str     Source string in UTF-8 encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */
    public String convert(String str, boolean html) {
        try {        
            byte[] aBytes = str.getBytes("ISO8859_1");
            
            // UTF-8 byte strings are frequently corrupted during handling or transmission.
            // Specifically, no-break spaces (0xA0 or 160) usually become regular spaces (0x20). 
            // In the Vietnamese Unicode set, there are only four characters whose UTF-8 
            // representations contain NBSP.
            
            // UTF-8 byte values  Unicode name
            // ------------------------------
            // 195 160        a with grave
            // 225 186 160    A with dot below
            // 198 160        O with horn
            // 225 187 160    O with horn and tilde
            
            // Replace spaces with NBSP where applicable
            for (int i = 1; i < aBytes.length; i++) {
                if (aBytes[i] == 0x20) // space?
                {
                    if (   (aBytes[i-1] == (byte) 0xC3) 
                        || ((i > 1) && (aBytes[i-2] == (byte) 0xE1) && (aBytes[i-1] == (byte) 0xBA)) 
                        || (aBytes[i-1] == (byte) 0xC6) 
                        || ((i > 1) && (aBytes[i-2] == (byte) 0xE1) && (aBytes[i-1] == (byte) 0xBB)) )  
                    {
                        aBytes[i] = (byte) 0xA0; // NBSP
                    }
                }
            }

            return new String(aBytes, "UTF-8");
            
        } catch (java.io.UnsupportedEncodingException exc) {
            throw new RuntimeException("Unsupported encoding.");
        }
    }       
}
