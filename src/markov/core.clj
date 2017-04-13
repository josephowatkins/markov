(ns markov.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))


(def start "BEGIN NOW")
(def end " END")

(def test-line "Gen|1|1| In the beginning God created the heaven and the earth.~")

(defn read-file []
  (with-open [reader (io/reader (io/resource "old_kjb.txt"))]
    (doall (line-seq reader))))

(defn process-start [line]
  (string/replace-first line #"\w+\|\d+\|\d+\|" start))

(defn process-ending [line]
  (string/replace line "~" end))

(defn process-line [line]
  (-> line process-start process-ending (string/split #" ")))

(defn generate-ngrams [n words]
  (partition n 1 words))

(defn line->chains [line]
  (let [trigrams (->> line process-line (generate-ngrams 3))]
    (reduce (fn [chains [w1 w2 word]]
              (let [key [w1 w2]]
                (update chains key (fnil conj []) word)))
            {}
            trigrams)))

(defn build-chains [lines]
  (reduce (fn [chains line]
            (merge-with into chains (line->chains line)))
          {}
          lines))

(defn new-verse*
  ([chains] (new-verse* chains "BEGIN" "NOW"))
  ([chains w1 w2]
   (lazy-seq
    (let [word (rand-nth (get chains [w1 w2]))]
      (when (not= word "END")
        (cons word (new-verse* chains w2 word)))))))

(def chain )

(defn new-verse [chains]
  (string/join " " (new-verse* chains)))
