(ns aoc-2023.day-02-part-2 (:gen-class) (:require [clojure.string :as string]))

(defn parse-set [set-str]
  (let [set-strs (string/split set-str #",")]
    (reduce (fn [m [count color]] (assoc m (keyword color) (parse-long count))) {} (map #(string/split (string/trim %) #" ") set-strs))))

(defn parse-game [line]
  (let [[_ game-id game] (re-matches #"Game (\d+): (.*)" line)
        subset-strs (string/split game #"; ?")
        subsets (map parse-set subset-strs)]
    {:game-id (parse-long game-id) :subsets subsets}))

(defn power-set [game]
  (let [subs (:subsets game)
        max-by-color (fn [color]
                       (apply max (filter (complement nil?) (map #(get % color) subs))))]
    (* (max-by-color :red) (max-by-color :green) (max-by-color :blue))))

(defn -main []
  (->> (slurp *in*)
       (string/split-lines)
       (map parse-game)
       (map power-set)
       (apply +)
       (println)))