(ns vietpad-clj.core-test
  (:require [clojure.test :refer :all]
            [vietpad-clj.core :refer :all]))

(deftest strip-diacritics-test
  (is (= (strip-diacritics "hôm nay trời đẹp!")
         "hom nay troi dep!")))

(deftest add-diacritics-test
  (is (= (add-diacritics "hom nay troi dep?")
         "hôm nay trời đẹp?")))

(deftest normalize-diacritics-test
  (is (= (normalize-diacritics "oà uỳ tòa tùy" false)
         "oà uỳ toà tuỳ"))
  (is (= (normalize-diacritics "oà uỳ tòa tùy")
         "òa ùy tòa tùy")))

(deftest to-unicode-test
  ;; default is :tcvn
  (is (= (to-unicode "h«m nay trêi ®Ñp")
         "hôm nay trời đẹp"))
  (is (= (to-unicode "hoâm nay trôøi ñeïp" :vni)
         "hôm nay trời đẹp")))

(deftest sort-str-test
  (is (= (sort-str ["a" "a" "a"])
         ["a" "a" "a"]))
  (is (= (sort-str ["a" "a" "a"] true)
         ["a" "a" "a"])))
