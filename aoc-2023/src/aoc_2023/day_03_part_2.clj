(ns aoc-2023.day-03-part-2 (:gen-class) (:require [clojure.string :as string]))

(defn find-part-numbers [line-number line]
  (let [matcher (re-matcher #"\d+" line)
        results (transient [])]
    (while (.find matcher)
      (conj! results
             {:start (.start matcher)
              :end (.end matcher)
              :line-number line-number
              :number (parse-long (.group matcher))}))
    (persistent! results)))

(defn find-gear-symbols [line-number line]
  (let [matcher (re-matcher #"\*" line)
        results (transient [])]
    (while (.find matcher)
      (conj! results
             {:start (.start matcher)
              :end (.end matcher)
              :line-number line-number}))
    (persistent! results)))

(defn overlaps? [pos1 pos2]
  (let [start1 (:start pos1)
        end1   (:end pos1)
        start2 (:start pos2)
        end2   (:end pos2)]
    (or (and (<= start1 end2) (>= end1 start2))
        (and (<= start2 end1) (>= end2 start1)))))

(defn next-to? [gear match]
  (let [neighbor-line-nums (set (map #(% (:line-number gear)) [dec identity inc]))
        gear-match-range {:start (:start gear) :end (:end gear)}]
    (and (neighbor-line-nums (:line-number match)) (overlaps? gear-match-range match))))

(defn add-gear-part-numbers [gear part-numbers]
  (assoc gear :part-numbers (filter #(next-to? gear %) part-numbers)))

(defn gear-ratio [gear]
  (apply * (map :number (:part-numbers gear))))

(defn -main []
  (let [schematic (slurp *in*)
        lines (string/split-lines schematic)
        part-numbers (flatten (map-indexed #(find-part-numbers %1 %2) lines))
        gears (flatten (map-indexed #(find-gear-symbols %1 %2) lines))
        gears-with-part-numbers (filter 
                                 #(= 2 (count (:part-numbers %))) 
                                 (map #(add-gear-part-numbers % part-numbers) gears))]
    (->> gears-with-part-numbers
         (map gear-ratio)
         (apply +)
         (println))))