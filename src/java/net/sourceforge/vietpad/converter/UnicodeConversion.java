/**
 *  Conversion of text and HTML files in various Vietnamese encodings to Unicode Strings.
 *
 *@author     Quan Nguyen
 *@author     Gero Herrmann
 *@created    28 October 2003
 *@version    1.9, 10 September 2008
 *@see        http://unicodeconvert.sourceforge.net
 */

package net.sourceforge.vietpad.converter;

public class UnicodeConversion {
    Converter converter;

    /**
     *  Constructor for the UnicodeConversion object
     *
     *@param  sourceEncoding  One of supported encodings: "VISCII", "VPS", "VNI", "VIQR/Vietnet",
     *                        "TCVN3 (ABC)", "Unicode", "Unicode Composite", "UTF-8", ISC", or "NCR"
     */
    public UnicodeConversion(final String sourceEncoding) {
        this(VietEncodings.valueOf(VietEncodings.class, sourceEncoding));
    }
    /**
     *  Constructor for the UnicodeConversion object
     *
     *@param  sourceEncoding One of VietEncodings enums
     */
    public UnicodeConversion(final VietEncodings sourceEncoding) {
        converter = ConverterFactory.createConverter(sourceEncoding);
    }
    
    /**
     *  Converts a string
     *
     *@param  source    Text to be converted
     *@param  html      True if HTML document
     */    
    public String convert(final String source, final boolean html) {
        return converter.convert(source, html);
    }
}
