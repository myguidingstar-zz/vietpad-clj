/*
 * VPS-to-Unicode conversion
 *
 * @author     Quan Nguyen
 * @version    1.2, 21 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class VpsConverter extends Converter {
    final char[] VPS_char = {'\u00CF', '\u00B3', '\u009B', '\u00FD', '\u009C', '\u0019', '\u00FF',
            '\u00B2', '\u00BF', '\u0015', '\u00BB', '\u001D', '\u00BA', '\u00B1', '\u00D8', '\u00AF',
            '\u00D9', '\u00AD', '\u00FB', '\u00D1', '\u00F8', '\u0014', '\u00AE', '\u0013', '\u00AB',
            '\u00A6', '\u00AA', '\u009F', '\u00A9', '\u009E', '\u00A7', '\u009D', '\u00B6', '\u0012',
            '\u0087', '\u0099', '\u00B0', '\u0098', '\u00D2', '\u0097', '\u00D3', '\u0096', '\u00D5',
            '\u00BD', '\u0086', '\u0011', '\u00CE', '\u0010', '\u00CC', '\u00B7', '\u008C', '\u0006',
            '\u00CD', '\u0095', '\u008B', '\u0094', '\u008A', '\u0093', '\u0089', '\u0090', '\u00EB',
            '\u00FE', '\u00C8', '\u00DE', '\u00CB', '\u0005', '\u00A5', '\u0004', '\u00A4', '\u00F0',
            '\u00A3', '\u008F', '\u00A2', '\u008E', '\u00A1', '\u008D', '\u00C6', '\u0003', '\u00C5',
            '\u001C', '\u00C4', '\u0085', '\u00C0', '\u0084', '\u00C3', '\u0083', '\u00E4', '\u0081',
            '\u00E5', '\u0002', '\u00DC', '\u00D0', '\u00D6', '\u00F7', '\u00DB', '\u00AC', '\u00EF',
            '\u00B8', '\u00C7', '\u00E6', '\u0088', '\u009A', '\u00A8', '\u00BE', '\u00B9', '\u00BC',
            '\u00F1', '\u00B4', '\u00B5', '\u00D7', '\u0082', '\u0080'};
    final char[] Unicode_char = {'\u1EF9', '\u1EF8', '\u1EF7', '\u1EF6', '\u1EF5', '\u1EF4', '\u1EF3',
            '\u1EF2', '\u1EF1', '\u1EF0', '\u1EEF', '\u1EEE', '\u1EED', '\u1EEC', '\u1EEB', '\u1EEA',
            '\u1EE9', '\u1EE8', '\u1EE7', '\u1EE6', '\u1EE5', '\u1EE4', '\u1EE3', '\u1EE2', '\u1EE1',
            '\u1EE0', '\u1EDF', '\u1EDE', '\u1EDD', '\u1EDC', '\u1EDB', '\u1EDA', '\u1ED9', '\u1ED8',
            '\u1ED7', '\u1ED6', '\u1ED5', '\u1ED4', '\u1ED3', '\u1ED2', '\u1ED1', '\u1ED0', '\u1ECF',
            '\u1ECE', '\u1ECD', '\u1ECC', '\u1ECB', '\u1ECA', '\u1EC9', '\u1EC8', '\u1EC7', '\u1EC6',
            '\u1EC5', '\u1EC4', '\u1EC3', '\u1EC2', '\u1EC1', '\u1EC0', '\u1EBF', '\u1EBE', '\u1EBD',
            '\u1EBC', '\u1EBB', '\u1EBA', '\u1EB9', '\u1EB8', '\u1EB7', '\u1EB6', '\u1EB5', '\u1EB4',
            '\u1EB3', '\u1EB2', '\u1EB1', '\u1EB0', '\u1EAF', '\u1EAE', '\u1EAD', '\u1EAC', '\u1EAB',
            '\u1EAA', '\u1EA9', '\u1EA8', '\u1EA7', '\u1EA6', '\u1EA5', '\u1EA4', '\u1EA3', '\u1EA2',
            '\u1EA1', '\u1EA0', '\u01B0', '\u01AF', '\u01A1', '\u01A0', '\u0169', '\u0168', '\u0129',
            '\u0128', '\u0111', '\u0103', '\u0102', '\u00FD', '\u00D9', '\u00D5', '\u00D3', '\u00D2',
            '\u0110', '\u00CD', '\u00CC', '\u00C8', '\u00C3', '\u00C0'};

    /**
     *  Converts VPS to Unicode.
     *
     * @param str     Source string in VPS encoding
     * @param html    True if HTML code
     * @return        Unicode string
     */
    public String convert(String str, boolean html) {
        if (html) {
            str = convertHTML(str);
        }
        str = cp1252ToHex(str);
        
        for (int i = 0; i < VPS_char.length; i++) {
            str = str.replace(VPS_char[i], Unicode_char[i]);
        }
                
        return str;                
    }
    
    /**
     * Replaces fonts.
     */
    @Override
    String replaceFont(String str) {
        // Replace fonts, longer names first!
        return str.replaceAll("VPS (?:Times|Long An|Nam Dinh|Ninh Binh)(?: Hoa)?", SERIF) 
                  .replaceAll("VPS Helv(?: Hoa)?", SANS_SERIF);                 
    }     
}
