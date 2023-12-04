(ns aoc-2023.day-04-part-1 (:gen-class) (:require [clojure.set :as set]
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

(defn count-winning [scratchcard]
  (let [wins (count (set/intersection (:winning scratchcard) (:numbers scratchcard)))]
    (if (> wins 1)
      (bit-shift-left 1 (dec wins))
      wins)))

(defn -main []
  (->> (slurp *in*)
       (string/split-lines)
       (map parse-scratchcard)
       (map count-winning)
       (apply +)
       (println)))