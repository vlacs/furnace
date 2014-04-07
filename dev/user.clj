(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer (javadoc)]
   [clojure.pprint :refer (pprint)]
   [clojure.reflect :refer (reflect)]
   [clojure.repl :refer (apropos dir doc find-doc pst source)]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer (refresh refresh-all)]
   [furnace]
   [ring.adapter.jetty :refer [run-jetty]]
   [net.cgrand.reload]
    ))

(def system
  "A Var containing an object representing the application under
  development."
  nil)

(defn init
  "Creates and initializes the system under development in the Var
  #'system."
  []
  (alter-var-root
    #'system
    (constantly
      {:web-args
        {:port 6543
         :join? false}
       :web-handler (helmsman/compile-routes furnace/helmsman-website)})))

(defn start
  "Starts the system running, updates the Var #'system."
  []
  (alter-var-root #'system assoc :web-server (run-jetty (:web-handler system) (:web-args system))))

(defn stop
  "Stops the system if it is currently running, updates the Var
  #'system."
  []
  (when (not (nil? (:web-server system)))
    (.stop (:web-server system)))
  (alter-var-root #'system dissoc :web-server))

(defn go
  "Initializes and starts the system running."
  []
  (init)
  (start)
  :ready)

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after 'user/go))
