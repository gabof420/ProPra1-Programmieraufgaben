(ns de.propra.rover.core
  (:require [clansi.core :refer [style]])
  (:gen-class)
  (:import (de.propra.rover RoverControl IGame Direction)
           (java.util ArrayList)))

(defn- clj->java
  "Konvertiert einen Clojure-Vektor in eine Java-ArrayList."
  [v]
  (let [a (ArrayList.)]
    (doseq [e v]
      (.add a e))
    a))

;; Hier wird das Interface IGame von Clojure implementiert.
(defrecord Game [world direction position]
  IGame
  (execute [this input]
    (let [jgame (RoverControl/control this input)]
      (->Game (vec (map vec (.getWorld jgame)))
              (.getRoverDirection jgame)
              [(.getRoverY jgame) (.getRoverX jgame)])))
  (getRoverY [this] (first (:position this)))
  (getRoverX [this] (second (:position this)))
  (getRoverDirection [this] (:direction this))
  (getWorld [this] (clj->java (map clj->java (:world this)))))

;; ----------------------------------------------------------------------------

(defn- terrain [element]
  (cond ; case doesn't work since it require compile-time literals
    (= element \#)      (style "#" :yellow)
    (= element \space)  " "
    (= element Direction/NORTH) (style "Λ" :red :blink-slow :bright)
    (= element Direction/SOUTH) (style "V" :red :blink-slow :bright)
    (= element Direction/WEST)  (style "<" :red :blink-slow :bright)
    (= element Direction/EAST)  (style ">" :red :blink-slow :bright)))

(defn- print-map [game]
  (let [world (assoc-in (:world game) (:position game) (:direction game))]
    (doseq [row (mapv (partial map terrain) world)]
      (println (apply str row)))))

(defn- play
  "Game Loop. Loopt über die Nutzereingaben und wartet wieder auf die Eingabe für die nächsten Schritte."
  [game]
  (try (loop [game (Game. (:world game) (:direction game) (:position game))]
         (print-map game)
         (println "Where do you want to move? Available commands: l, r, f, b, q (quit)")
         (let [input (read-line)]
           (when-not (= input "q")
             (recur (.execute game input)))))
       (catch Exception e
         (print-map (ex-data e))
         (println (style (.getMessage e) :red)))))

(defn- gen-row
  "Erzeugt eine zufällige Anordnung von freien Feldern und Felsen."
  [length]
  (mapv (fn [_] (rand-nth [\space \space \space \#])) (range length)))

(defn- new-game
  "Startet ein neues Spiel basierend. Größe wird über die Parameter eingestellt."
  [height length]
  (let [start-pos [(/ height 2) (/ length 2)]
        raw-world (mapv (fn [_] (gen-row length)) (range height))
        world (assoc-in raw-world start-pos \space)]
    {:world     world
     :direction Direction/NORTH
     :position  start-pos}))

(defn- play-game
  "Erzeugt ein neues Spielfeld."
  []
  (play (new-game 24 80)))

(defn -main
  "Main-Methode von Clojure."
  []
  (play-game))
