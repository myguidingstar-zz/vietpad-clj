/*
 * VietEncodings.java
 *
 * Created on March 11, 2005, 12:35 AM
 * @author Quan Nguyen
 * @version    1.2, 10 September 08
 */
package net.sourceforge.vietpad.converter;

import java.util.*;

/**
 * Enum constants for supported Vietnamese encodings.
 */
public enum VietEncodings {
    ISC, NCR, TCVN3, Unicode, Unicode_Composite, UTF8, VIQR, VISCII, VNI, VPS;
//    ISC("ISC"), NCR("NCR"), TCVN3("TCVN3 (ABC)"), Unicode("Unicode"), Unicode_Composite("Unicode Composite"), UTF8("UTF-8"), VIQR("VIQR"), VISCII("VISCII"), VNI("VNI"), VPS("VPS");

//    private final String encoding;

//    private VietEncodings(String encoding) {
//        this.encoding = encoding;
//    }

    /**
     * Returns an array of the names of the enum constants.
     */
    public static String[] getNames() {
        List<String> list = new ArrayList<String>();
        for (VietEncodings enc : VietEncodings.values()) {
            list.add(enc.toString());
        }
        return list.toArray(new String[list.size()]);
    }
    
    /**
     * Returns the enum constant of <code>VietEncodings</code> type with the specified encoding.
     * <br>
     * This method is designed to address the inability to override the static valueOf() method.
     *
     * @param encoding One of supported encodings: "VISCII", "VPS", "VNI", "VIQR",
     *                        "TCVN3 (ABC)", "Unicode", "Unicode Composite", "UTF-8", ISC", or "NCR"
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String encoding) {
        for (T enc : EnumSet.allOf(enumType)) {
	    if (enc.toString().equals(encoding)) {
                return enc;
            }
	}
	throw new IllegalArgumentException("Unknown enum string for " + enumType.getName() + ": " + encoding);
    }
    
    @Override
    public String toString() {
        String s = super.toString();
        return s.replace("_", " ").replace("TCVN3", "TCVN3 (ABC)").replace("UTF8", "UTF-8");
//        return encoding;
    }
}
