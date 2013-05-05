package net.sourceforge.vietpad.utilities;

import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import net.sourceforge.vietpad.converter.*;

/**
 *  Vietnamese text utilities.
 *
 *@author     Quan Nguyen
 *@author     Gero Herrmann
 *@version    1.3, 23 February 2010
 */
public class VietUtilities {
    private static Map<String, String> map;
    private static long mapLastModified = Long.MIN_VALUE;

    /**
     * Strips accents off words.
     *
     *@param  accented  Accented text to be stripped
     *@return           Plain text
     */
    public static String stripDiacritics(String accented) {
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(Normalizer.normalize(accented, Normalizer.Form.NFD)).replaceAll("").replace('\u0111', 'd').replace('\u0110', 'D');
    }

    /**
     *  Adds diacritics to unmarked Viet text.
     *
     *@param  source  Plain text to be marked
     *@param supportDir Location of wordlist
     *@return         Text with diacritics added
     */
    public static String addDiacritics(String source, final File supportDir) {
        VietUtilities.loadMap(supportDir);

        StringBuilder strB = new StringBuilder(source.toLowerCase());

        // Break text into words
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(source);
        int length = source.length();
        int start = boundary.first();

        // try first to process three words; if no match, try two, then one
        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            if (!Character.isLetter(strB.charAt(start))) {
                continue;
            }

            // read next 1 or 2 words, if possible
            for (int i = 0; i < 4; i++) {
                if (end < length) {
                    end = boundary.next();
                } else {
                    break;
                }

                char ch = strB.charAt(end - 1);
                if (!Character.isLetter(ch) && ch != ' ') {
                    break;
                }
            }

            String key = strB.substring(start, end);

            // trim Key off non-letter characters
            while (!Character.isLetter(key.charAt(key.length() - 1))) {
                end = boundary.previous();// step back last word

                if (start == end) {
                    break;// break out of while loop
                }
                key = strB.substring(start, end);
            }

            if (map.containsKey(key)) {
                // match 3-, 2-, or 1-word sequence
                strB.replace(start, end, map.get(key));
            } else {
                if (end > start) {
                    end = boundary.previous();// step back last word
                }

                if (end > start) {
                    end = boundary.previous();
                }
                key = strB.substring(start, end);

                if (map.containsKey(key)) {
                    // match 2- or 1-word sequence
                    strB.replace(start, end, map.get(key));
                } else {
                    if (end > start) {
                        end = boundary.previous();// step back last word
                    }

                    if (end > start) {
                        end = boundary.previous();
                    }
                    key = strB.substring(start, end);
                    if (map.containsKey(key)) {
                        // match 1-word sequence
                        strB.replace(start, end, map.get(key));
                    }
                }
            }

            end = boundary.next();
        }

        // Capitalize letters that are capital in source
        for (int i = 0; i < length; i++) {
            if (Character.isUpperCase(source.charAt(i))) {
                strB.setCharAt(i, Character.toUpperCase(strB.charAt(i)));
            }
        }
        return strB.toString();
    }

    /**
     *  Reads Vietnamese wordlist for Add Diacritics function.
     */
    private static void loadMap(final File supportDir)  {
        final String wordList = "vietwordlist.txt";

        try {
            File dataFile = new File(supportDir, wordList);
            if (!dataFile.exists()) {
                final ReadableByteChannel input =
                        Channels.newChannel(ClassLoader.getSystemResourceAsStream("dict/" + dataFile.getName()));
                final FileChannel output =
                        new FileOutputStream(dataFile).getChannel();
                output.transferFrom(input, 0, 1000000L);
                input.close();
                output.close();
            }
            long fileLastModified = dataFile.lastModified();
            if (map == null) {
                map = new HashMap<String, String>();
            } else {
                if (fileLastModified <= mapLastModified) {
                    return;// no need to reload map
                }
                map.clear();
            }
            mapLastModified = fileLastModified;
            BufferedReader bs = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
            String accented;

            while ((accented = bs.readLine()) != null) {
                String plain = VietUtilities.stripDiacritics(accented);
                map.put(plain.toLowerCase(), accented);
            }
            bs.close();
        } catch (IOException e) {
            map = null;
            e.printStackTrace();
            System.err.println("Cannot find \"" + wordList + "\" in " + supportDir.toString());
        }
    }

    /**
     *  Normalizes diacritics.
     *
     *@author     Gero Herrmann
     *@param  source  Text to be normalized diacritics (diacritics in improper positions)
     *@return         Text with diacritics normalized (diacritics shifted to proper positions)
     */
    public static String normalizeDiacritics(String source, boolean diacriticsPosClassicOn) {
        final String TONE = "([\u0300\u0309\u0303\u0301\u0323])";
        final boolean isJAVA_1_5 = System.getProperty("java.version").compareTo("1.5") >= 0;

        // Works around an obscure Normalization bug which 
        // erroneously converts D with stroke and d with stroke to D and d,
        // respectively, on certain Windows systems,
        // by substituting them with \00DO and \00F0, respectively,
        // prior to normalization and then reverting them in post-processing.
        // It seems that some other installed applications have interfered with Java.

        String result = Normalizer.normalize(source.replace('\u0110', '\u00D0').replace('\u0111', '\u00F0'), Normalizer.Form.NFD)
                .replaceAll("(?i)" + TONE + "([aeiouy\u0306\u0302\u031B]+)", "$2$1") // quy tac 1+3+4
                .replaceAll("(?i)(?<=[\u0306\u0302\u031B])(.)" + TONE + (isJAVA_1_5 ? "\\b" : "\\B"), "$2$1") // quy tac 2
                .replaceAll("(?i)(?<=[ae])([iouy])" + TONE, "$2$1") // quy tac 5
                .replaceAll("(?i)(?<=[oy])([iuy])" + TONE, "$2$1")
                .replaceAll("(?i)(?<!q)(u)([aeiou])" + TONE, "$1$3$2")
                .replaceAll("(?i)(?<!g)(i)([aeiouy])" + TONE, "$1$3$2");
        if (diacriticsPosClassicOn) {
            result = result.replaceAll("(?i)(?<!q)([ou])([aeoy])" + TONE + "(?!\\w)", "$1$3$2");
        }
        return Normalizer.normalize(result, Normalizer.Form.NFC).replace('\u00D0', '\u0110').replace('\u00F0', '\u0111');
    }

    /**
     *  Sorts array of strings in Vietnamese alphabetical order.
     *
     *@param  words      Array of strings to be sorted
     *@param  reverse    True for reverse sorting
     *@param  delimiter  Delimiter for Left-to-Right sorting; empty if not needed
     */
    public static String[] sort(String[] words, final boolean reverse, final String delimiter) {
        // for Left-to-Right sorting
        if (!delimiter.equals("")) {
            int delimiterLength = delimiter.length();
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < words.length; i++) {
                String[] entries = words[i].split("\\Q" + delimiter);
                sort(entries);
                for (int j = 0; j < entries.length; j++) {
                    result.append(entries[j]).append(delimiter);
                }
                result.setLength(result.length() - delimiterLength);
                words[i] = result.toString();

                result.setLength(0); // clear buffer
            }
        }

        sort(words, reverse);

        return words;
    }

    /**
     *  Sorts array of strings in Vietnamese alphabetical order.
     *
     *@param  words      Array of strings to be sorted
     *@param  reverse    True for reverse sorting
     */
    public static String[] sort(String[] words, final boolean reverse) {
        sort(words);

        if (reverse) {
            // reverse order
            Collections.reverse(Arrays.asList(words));
        }

        return words;
    }

    /**
     *  Sorts array of strings in Vietnamese alphabetical order.
     *
     *@param  words      Array of strings to be sorted
     */
    public static String[] sort(String[] words) {
        VietComparator vietComparator = new VietComparator();

        // sort into ascending alphabetical order
        Arrays.sort(words, vietComparator);

        // reverse sort
//        Arrays.sort(words, Collections.reverseOrder(vietComparator)); // supported since Java 1.5

        return words;
    }

    /**
     *  Converts text from legacy encoding to Unicode.
     *
     *@param  source      Text to be worked on
     *@param  sourceEncoding    Source encoding
     *@param  html        Specifies if the text is HTML source
     */
    public static String convert(String source, String sourceEncoding, boolean html) {
        UnicodeConversion unicon = new UnicodeConversion(sourceEncoding);
        return unicon.convert(source, html);
    }

    /**
     *  Auto-detects file encoding.
     *
     *  Specifically tailored for detecting Vietnamese Unicode characters. Detect file encodings:
     *  Windows Latin 1, UTF-8, -16, -16BE, -16LE. Vietnamese legacy encodings are returned
     *  as Windows Latin 1.
     *
     *@param  file  the file to be examined
     *@return       the encoding detected
     */
    public static String detectEncoding(File file) {

        String encoding = "UTF8"; // UTF-8 as default encoding
        long filesize = file.length();

        if (filesize == 0) {
            return encoding;
        }
        if (filesize > 1024) {
            filesize = 1024;
            // read in 1024 bytes, increase if necessary
        }
        int[] b = new int[(int) filesize];

        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            for (int i = 0; i < filesize; i++) {
                b[i] = in.readUnsignedByte();
            }
            in.close();

            if (b[0] == 0xFE && b[1] == 0xFF) {
                // UTF-16 and -16BE BOM FEFF
                encoding = "UTF-16";
            } else if (b[0] == 0xFF && b[1] == 0xFE) {
                // UTF-16LE BOM FFFE
                encoding = "UTF-16LE";
            } else if (b[0] == 0xEF && b[1] == 0xBB && b[2] == 0xBF) {
                encoding = "UTF8";
            } else {
                for (int i = 0; i < b.length; i++) {
                    if ((b[i] & 0xF0) == 0xC0) {
                        if ((b[i + 1] & 0x80) == 0x80) {
                            encoding = "UTF8";
                            break;
                        }
                    } else if ((b[i] & 0xE1) == 0xE1) {
                        if ((b[i + 1] & 0x80) == 0x80 && (b[i + 2] & 0x80) == 0x80) {
                            encoding = "UTF8";
                            break;
                        }
                    } else if ((b[i] & 0x80) != 0x00) {
                        encoding = "Cp1252";
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Can't open file.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return encoding;
    }
}
