(ns aoc-2023.day-04-part-2 (:gen-class) (:require [clojure.set :as set]
                                                  [clojure.string :as string]))

(defn parse-nums [s]
  (->> (string/split s #"\s+")
       (filter (complement empty?))
       (map parse-long)
       (set)))

(defn parse-scratchcard [line]
  (let [[_ card-id] (first (re-seq #"Card\s+(\d+):" line))
        [_ nums] (string/split line #":")
        [winning-str nums-str] (string/split nums #"\|")]
    {:id (parse-long card-id)
     :winning (parse-nums winning-str)
     :numbers (parse-nums nums-str)}))

(defn get-won-copies [scratchcard scratchcards]
  (let [points (count (set/intersection (:winning scratchcard) (:numbers scratchcard)))
        current-card-id (:id scratchcard)
        won-idxs (range current-card-id (+ current-card-id points))]
    (filter (complement nil?) (map #(get scratchcards % nil) won-idxs))))

(defn count-won-copies [won-set scratchcards] ;; not the best solution but it's mine :)
  (loop [total [] next-set won-set]
    (if (empty? next-set)
      (count total)
      (recur (concat total next-set) (flatten (map #(get-won-copies % scratchcards) next-set))))))

(defn -main []
  (let [scratchcards
      (->> (slurp *in*)
           (string/split-lines)
           (map parse-scratchcard)
           (vec))
      original-wins (vec (flatten (map #(get-won-copies % scratchcards) scratchcards)))]
  (println (+ (count scratchcards) (count-won-copies original-wins scratchcards)))))