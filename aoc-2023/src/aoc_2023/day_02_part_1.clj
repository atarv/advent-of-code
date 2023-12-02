(ns aoc-2023.day-02-part-1 (:gen-class) (:require [clojure.string :as string]))

(defn parse-set [set-str]
  (let [set-strs (string/split set-str #",")]
    (reduce (fn [m [count color]] (assoc m (keyword color) (parse-long count))) {} (map #(string/split (string/trim %) #" ") set-strs))))

(defn parse-game [line]
  (let [[_ game-id game] (re-matches #"Game (\d+): (.*)" line)
        subset-strs (string/split game #"; ?")
        subsets (map parse-set subset-strs)]
    {:game-id (parse-long game-id) :subsets subsets}))

(defn possible-game? [game]
  (let [subs (:subsets game)
        color-valid? (fn [sub color max] (or (nil? (get sub color)) (<= (get sub color) max)))]
    (every?
     #(and (color-valid? % :red 12) (color-valid? % :green 13) (color-valid? % :blue 14))
     subs)))

(defn -main []
  (->> (slurp *in*)
       (string/split-lines)
       (map parse-game)
       (filter possible-game?)
       (map :game-id)
       (apply +)
       (println)))