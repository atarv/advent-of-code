(ns aoc-2023.day-01-part-2 (:gen-class) (:require [clojure.string :as string]))

(defn calibration-value [line]
  (let [digits-raw (map last (re-seq #"(?=(\d|one|two|three|four|five|six|seven|eight|nine))" line))
        to-digit #(case %
                       "one" 1
                       "two" 2
                       "three" 3
                       "four" 4
                       "five" 5
                       "six" 6
                       "seven" 7
                       "eight" 8
                       "nine" 9
                       %)
        digits (map to-digit digits-raw)]
    (println line digits)
    (parse-long (str (first digits) (last digits)))))

(defn -main []
  (->> (slurp *in*)
       (string/split-lines)
       (map calibration-value)
       (apply +)
       (println)))