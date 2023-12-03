(defn- profile [day-number part-number]
  {(keyword (format "day-%02d-part-%d" day-number part-number))
   {:main (format "aoc-2023.day-%02d-part-%d" day-number part-number)}})

(defn- gen-profiles-until [max-day max-part]
  (apply merge (for [day (range 1 (inc max-day)) part (range 1 3)
                     :when (not (and (= day max-day) (when max-part (> part max-part))))]
                 (profile day part))))

(defproject aoc-2023 "0.1.0-SNAPSHOT"
  :description "My solutions to Advent of Code 2023"
  :url "https://github.com/atarv/advent-of-code/aoc-2023"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main nil
  :target-path "target/%s"
  :profiles ~(merge (gen-profiles-until 3 2)
                    {:uberjar {:aot :all
                               :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}))
