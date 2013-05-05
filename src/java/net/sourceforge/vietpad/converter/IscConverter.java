/*
 * ISC-to-Unicode conversion
 *
 * @author     Gero Herrmann
 * @version    1.2, 21 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class IscConverter extends Converter {
    final char[] ISC_char     = {'\u2018', '\u2019', '\u201C', '\u201D', '\u0023', '\u00DA', '\\',     '\u005E', '\u0060', '\u007C', '\u007E', '\uFFFD', '\u00C0', '\u00C5', '\u00E8', '\u00C1', '\u00D1', '\u00D6', '\u00DC', '\u00C8', '\u00E0', '\u00E2', '\u00E9', '\u00E3', '\u00E5', '\u00CC', '\u00E4', '\u00C3', '\u00CD', '\u00EB', '\u00D2', '\u00EC', '\u00D5', '\u00D3', '\u00F1', '\u00F3', '\u00F2', '\u00F4', '\u00F6', '\u00F5', '\u00FA', '\u00D9', '\u00FB', '\u00FC', '\u2020', '\u00B0', '\u00C2', '\u00CA', '\u00F9', '\u2022', '\u00B6', '\u00DF', '\u00AE', '\u00A9', '\u00EA', '\u00B4', '\u00A8', '\u2260', '\u00C6', '\u00D8', '\u221E', '\u00B1', '\u2264', '\u2265', '\u00A5', '\u00B5', '\u2202', '\u2211', '\u00E1', '\u03C0', '\u222B', '\u00AA', '\u00BA', '\u03A9', '\u00E6', '\u00F8', '\u00BF', '\u00A1', '\u00AC', '\u221A', '\u0192', '\u2248', '\u2206', '\u00AB', '\u00BB', '\u2026', '\u00C4', '\u00C7', '\u00EE', '\u0152', '\u0153', '\u00C9', '\u2014', '\u0013', '\u0014', '\u0011', '\u0012', '\u00F7', '\u25CA', '\u00FF', '\u0178', '\u2044', '\u20AC', '\u2039', '\u00ED', '\uFB01', '\uFB02', '\u2021', '\u00B7', '\u201A', '\u201E', '\u2030', '\u00A2', '\u00A3', '\u2013', '\u00CB', '\u220F', '\u2122', '\u00CE', '\u00CF', '\u00E7', '\u00EF', '\u00D4', '\uF8FF', '\u203A', '\u0040', '\u00DB', '\u00A7', '\u0131', '\u02C6', '\u02DC', '\u00AF', '\u02D8', '\u02D9', '\u02DA', '\u00B8', '\u02DD', '\u02DB', '\u02C7'};
    final char[] Unicode_char = {'\u1EC5', '\u1EBF', '\u1EC1', '\u1EC3', '\u1EF0', '\u0169', '\u1EEA', '\u1EEC', '\u1EE8', '\u1EE4', '\u1EEE', '\u007F', '\u1EAD', '\u1EA2', '\u0128', '\u1ED7', '\u1EA0', '\u1EB6', '\u1EAC', '\u1ED9', '\u1EBA', '\u1EBC', '\u1EC8', '\u1EB9', '\u1EC6', '\u1EDB', '\u00E9', '\u00E8', '\u1EDD', '\u1ECA', '\u1EE7', '\u1ECE', '\u1EC2', '\u1EE3', '\u1ECC', '\u1ED8', '\u1EDC', '\u1EDE', '\u1EE0', '\u1EDA', '\u1EE2', '\u1EE5', '\u1EE6', '\u0168', '\u1EA5', '\u0102', '\u1ED3', '\u1ED5', '\u00D9', '\u01A0', '\u01AF', '\u0110', '\u0103', '\u00E2', '\u00CD', '\u00F4', '\u01A1', '\u01B0', '\u0111', '\u1EB0', '\u1EF2', '\u1EF6', '\u1EF8', '\u00DD', '\u1EF4', '\u00E0', '\u1EA3', '\u00E3', '\u00C8', '\u1EA1', '\u1EB2', '\u1EB1', '\u1EB3', '\u1EB5', '\u1EAF', '\u1EB4', '\u1EAE', '\u1EA6', '\u1EA8', '\u1EAA', '\u1EA4', '\u1EC0', '\u1EB7', '\u1EA7', '\u1EA9', '\u1EAB', '\u00C0', '\u00C3', '\u00D5', '\u1EBB', '\u1EBD', '\u00C1', '\u1EB8', '\u201C', '\u201D', '\u2018', '\u2019', '\u1EC7', '\u00EC', '\u1EC9', '\u1EC4', '\u1EBE', '\u1ED2', '\u0129', '\u00D2', '\u1ECB', '\u00F2', '\u1ED4', '\u1ECF', '\u00F5', '\u00F3', '\u1ECD', '\u00C2', '\u00CA', '\u00C9', '\u1ED1', '\u00E1', '\u00EA', '\u1EDF', '\u1EE1', '\u00CC', '\u00D3', '\u00F9', '\u1ED6', '\u00ED', '\u00DA', '\u00FA', '\u00D4', '\u1EEB', '\u1EED', '\u1EEF', '\u1EE9', '\u1EF1', '\u1EF3', '\u1EF7', '\u1EF9', '\u00FD', '\u1EF5', '\u1ED0'};
    
    /**
     *  Converts ISC to Unicode.
     *
     * @param str     Source string in ISC encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */
    public String convert(String str, boolean html) {
        if (html) {
            str = convertHTML(str);
        }
        
        for (int i = 0; i < ISC_char.length; i++) {
            str = str.replace(ISC_char[i], Unicode_char[i]);
        }
        
        return str;
    }
}
