(ns aoc-2023.day-01-part-1 (:gen-class) (:require [clojure.string :as string]))

(defn calibration-value [line]
  (let [digits (re-seq #"\d" line)]
    (parse-long (str (first digits) (last digits)))))

(defn -main []
  (->> (slurp *in*)
       (string/split-lines)
       (map calibration-value)
       (apply +)
       (println)))