(ns vietpad-clj.core
  (:use [clojure.java.io :only [as-file resource]])
  (:import net.sourceforge.vietpad.utilities.VietUtilities))

(defn strip-diacritics
  "Strips diacritics off words."
  [^String s]
  (VietUtilities/stripDiacritics s))

(defn add-diacritics
  "Adds diacritics to unmarked Viet text."
  [^String s]
  (VietUtilities/addDiacritics
   s
   (as-file (resource "dict"))))

(defn normalize-diacritics
  "Normalizes diacritics."
  ([^String s ^Boolean classic?]
     (VietUtilities/normalizeDiacritics s classic?))
  ([^String s]
     (VietUtilities/normalizeDiacritics s true)))

(defn detect-encoding
  "Auto-detects file encoding."
  [file]
  (VietUtilities/detectEncoding (clojure.java.io/as-file file)))

(defn to-unicode
  "Converts text from legacy encoding to Unicode.
  Supported Vietnamese encodings:
  :isc, :ncr, :tcvn, :unicode, :composite,
  :utf8, :viqr, :viscii, :vni, :vps."
  ([^String s]
     (VietUtilities/convert s "TCVN3 (ABC)" false))
  ([^String s ^clojure.lang.Keyword source-enc]
     (to-unicode s source-enc false))

  ([^String s ^clojure.lang.Keyword source-enc ^Boolean html?]
     (VietUtilities/convert s
                            (case source-enc
                              :isc  "ISC",
                              :ncr  "NCR",
                              :tcvn "TCVN3 (ABC)",
                              :unicode   "Unicode",
                              :composite "Unicode Composite",
                              :utf8   "UTF-8",
                              :viqr   "VIQR",
                              :viscii "VISCII",
                              :vni "VNI",
                              :vps "VPS")
                            html?)))

(defn sort-str
  "Sorts vectors of strings in Vietnamese alphabetical order."
  ([^clojure.lang.APersistentMap words]
     (vec (VietUtilities/sort (into-array words))))
  ([^clojure.lang.APersistentMap words ^Boolean reverse?]
     (vec (VietUtilities/sort (into-array words) reverse?))))