(ns aoc-2023.day-03-part-1 (:gen-class) (:require [clojure.string :as string]))

(defn find-matches [line-number line]
  (let [matcher (re-matcher #"\d+" line)
        results (transient [])]
    (while (.find matcher)
      (conj! results
             {:start (.start matcher)
              :end (.end matcher)
              :line-number line-number
              :number (parse-long (.group matcher))}))
    (persistent! results)))

(defn clamp [value min-value max-value]
  (max min-value (min max-value value)))

(defn adjacent-symbol? [lines match]
  (let [current-line-num (:line-number match)
        max-line-num (dec (count lines))
        max-line-index (dec (count (first lines)))
        line-nums (filter #(and (>= % 0) (<= % max-line-num))
                          [(dec current-line-num) current-line-num (inc current-line-num)])
        neighbor-lines (map #(get lines %) line-nums)
        start (clamp (dec (:start match)) 0 max-line-index)
        end (clamp (inc (:end match)) 0 max-line-index)
        neighbors (map #(subs % start end) neighbor-lines)]
    (some #(not-every? #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \.} %) neighbors)))

(defn -main []
  (let [schematic (slurp *in*)
        lines (string/split-lines schematic)
        matches (flatten (map-indexed #(find-matches %1 %2) lines))
        adjacent-numbers (filter #(adjacent-symbol? lines %) matches)]
    (println (apply + (map :number adjacent-numbers)))))