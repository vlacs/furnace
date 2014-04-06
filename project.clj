(defproject org.vlacs/furnace "0.0.1"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url "http://choosealicense.com/"}
  :dependencies [[enlive "1.1.5"]
                 [ring "1.2.2"]
                 [org.clojure/clojure "1.5.1"]
                 [org.vlacs/helmsman "0.1.8"]]
  :pedantic? :warn
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.4"]]
                   :source-paths ["dev"]}})


