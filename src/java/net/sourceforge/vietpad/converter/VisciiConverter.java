/*
 * VISCII-to-Unicode conversion
 *
 * @author     Quan Nguyen
 * @version    1.2, 21 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class VisciiConverter extends Converter {   
    final char[] VISCII_char = {'\u2011', '\u00C5', '\u00E5', '\u00F0', '\u00CE', '\u00EE', '\u009D',
            '\u00FB', '\u00B4', '\u00BD', '\u00BF', '\u00DF', '\u0080', '\u00D5', '\u00C4', '\u00E4',
            '\u0084', '\u00A4', '\u0085', '\u00A5', '\u0086', '\u00A6', '\u0006', '\u00E7', '\u0087',
            '\u00A7', '\u0081', '\u00A1', '\u0082', '\u00A2', '\u0002', '\u00C6', '\u0005', '\u00C7',
            '\u0083', '\u00A3', '\u0089', '\u00A9', '\u00CB', '\u00EB', '\u0088', '\u00A8', '\u008A',
            '\u00AA', '\u008B', '\u00AB', '\u008C', '\u00AC', '\u008D', '\u00AD', '\u008E', '\u00AE',
            '\u009B', '\u00EF', '\u0098', '\u00B8', '\u009A', '\u00F7', '\u0099', '\u00F6', '\u008F',
            '\u00AF', '\u0090', '\u00B0', '\u0091', '\u00B1', '\u0092', '\u00B2', '\u0093', '\u00B5',
            '\u0095', '\u00BE', '\u0096', '\u00B6', '\u0097', '\u00B7', '\u00B3', '\u00DE', '\u0094',
            '\u00FE', '\u009E', '\u00F8', '\u009C', '\u00FC', '\u00BA', '\u00D1', '\u00BB', '\u00D7',
            '\u00BC', '\u00D8', '\u00FF', '\u00E6', '\u00B9', '\u00F1', '\u009F', '\u00CF', '\u001E',
            '\u00DC', '\u0014', '\u00D6', '\u0019', '\u00DB', '\u00A0'};
    final char[] Unicode_char = {'\u1EF4', '\u0102', '\u0103', '\u0111', '\u0128', '\u0129', '\u0168',
            '\u0169', '\u01A0', '\u01A1', '\u01AF', '\u01B0', '\u1EA0', '\u1EA1', '\u1EA2', '\u1EA3',
            '\u1EA4', '\u1EA5', '\u1EA6', '\u1EA7', '\u1EA8', '\u1EA9', '\u1EAA', '\u1EAB', '\u1EAC',
            '\u1EAD', '\u1EAE', '\u1EAF', '\u1EB0', '\u1EB1', '\u1EB2', '\u1EB3', '\u1EB4', '\u1EB5',
            '\u1EB6', '\u1EB7', '\u1EB8', '\u1EB9', '\u1EBA', '\u1EBB', '\u1EBC', '\u1EBD', '\u1EBE',
            '\u1EBF', '\u1EC0', '\u1EC1', '\u1EC2', '\u1EC3', '\u1EC4', '\u1EC5', '\u1EC6', '\u1EC7',
            '\u1EC8', '\u1EC9', '\u1ECA', '\u1ECB', '\u1ECC', '\u1ECD', '\u1ECE', '\u1ECF', '\u1ED0',
            '\u1ED1', '\u1ED2', '\u1ED3', '\u1ED4', '\u1ED5', '\u1ED6', '\u1ED7', '\u1ED8', '\u1ED9',
            '\u1EDA', '\u1EDB', '\u1EDC', '\u1EDD', '\u1EDE', '\u1EDF', '\u1EE0', '\u1EE1', '\u1EE2',
            '\u1EE3', '\u1EE4', '\u1EE5', '\u1EE6', '\u1EE7', '\u1EE8', '\u1EE9', '\u1EEA', '\u1EEB',
            '\u1EEC', '\u1EED', '\u1EEE', '\u1EEF', '\u1EF0', '\u1EF1', '\u1EF2', '\u1EF3', '\u1EF4',
            '\u1EF5', '\u1EF6', '\u1EF7', '\u1EF8', '\u1EF9', '\u00D5'};
    
    /**
     * Converts VISCII to Unicode
     *
     * @param str     Source string in VISCII encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */                        
    public String convert(String str, boolean html) {
        if (html) {
            str = convertHTML(str);     
        }
        str = cp1252ToHex(str);
        
        for (int i = 0; i < VISCII_char.length; i++) {
            str = str.replace(VISCII_char[i], Unicode_char[i]);
        }
        
        return str;
    }
    
    /**
     * Replaces fonts.
     */
    @Override
    String replaceFont(String str) {
        return str.replaceAll("(?:VI Times|Heo May|HoangYen|MinhQu\\u00E2n|PhuongThao|ThaHuong|UHo\\u00E0i)H?(?: Hoa)?(?: 1\\.1)?", SERIF)
                  .replaceAll("VI Arial", SANS_SERIF);        
    }    
}
