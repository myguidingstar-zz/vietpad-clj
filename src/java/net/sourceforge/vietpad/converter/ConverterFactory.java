/*
 * ConverterFactory.java
 *
 * @author     Quan Nguyen
 * @version    1.1, 16 Octorber 05
 */

package net.sourceforge.vietpad.converter;

public class ConverterFactory {
    public static Converter createConverter(VietEncodings sourceEncoding) {
        Converter converter;
        
        if (sourceEncoding == VietEncodings.VNI) {
            converter = new VniConverter();
        } else if (sourceEncoding == VietEncodings.TCVN3) {
            converter = new Tcvn3Converter();
        } else if (sourceEncoding == VietEncodings.VIQR) {
            converter = new ViqrConverter();
        } else if (sourceEncoding == VietEncodings.VISCII) {
            converter = new VisciiConverter();
        } else if (sourceEncoding == VietEncodings.VPS) {
            converter = new VpsConverter();
        } else if (sourceEncoding == VietEncodings.ISC) {
            converter = new IscConverter();
        } else if (sourceEncoding == VietEncodings.NCR) {
            converter = new NcrConverter();
        } else if (sourceEncoding == VietEncodings.UTF8) {
            converter = new Utf8Converter();
        } else if (sourceEncoding == VietEncodings.Unicode_Composite) {
            converter = new CompositeConverter();
        } else if (sourceEncoding == VietEncodings.Unicode) {
            converter = new UnicodeViqrConverter();
        } else {
            throw new RuntimeException("Unsupported encoding: " + sourceEncoding);
        }
        
        return converter;
    }
}
