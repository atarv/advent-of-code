(defproject aoc-2023 "0.1.0-SNAPSHOT"
  :description "My solutions to Advent of Code 2023"
  :url "https://github.com/atarv/advent-of-code/aoc-2023"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main nil
  :target-path "target/%s"
  :profiles {:day-01-part-1 {:main aoc-2023.day-01-part-1}
             :day-01-part-2 {:main aoc-2023.day-01-part-2}
             :day-02-part-1 {:main aoc-2023.day-02-part-1}
             :day-02-part-2 {:main aoc-2023.day-02-part-2}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
