(ns furnace
  (:require [helmsman]
            [helmsman.uri :as h-uri]
            [helmsman.navigation :as h-nav]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate example-page "templates/example.html"
  [bootstrap-css-url
   bootstrap-css-theme-url
   bootstrap-js-url]

  [[:link (html/attr-has :href "bootstrap.css")]] (html/set-attr :href bootstrap-css-url)
  [[:link (html/attr-has :href "bootstrap-theme.css")]] (html/set-attr :href bootstrap-css-theme-url)
  [[:script (html/attr-has :src "bootstrap.js")]] (html/set-attr :src bootstrap-js-url))

(html/deftemplate front-page "templates/main.html"
  [bootstrap-css-url
   bootstrap-css-theme-url
   bootstrap-js-url]

  [[:link (html/attr-has :href "bootstrap.css")]] (html/set-attr :href bootstrap-css-url)
  [[:link (html/attr-has :href "bootstrap-theme.css")]] (html/set-attr :href bootstrap-css-theme-url)
  [[:script (html/attr-has :src "bootstrap.js")]] (html/set-attr :src bootstrap-js-url) 
  )

(defn bootstrap-uri
  [request id]
  (let [bootstrap (h-nav/meta-from-request request (h-nav/pred-by-id :bootstrap))]
    (h-uri/relative-uri-str
      request
      (conj (get bootstrap :uri-path)
            (get bootstrap id)))))

(defn load-example-page
  [request]
  (example-page
    (bootstrap-uri request :css)
    (bootstrap-uri request :css-theme)
    (bootstrap-uri request :js)))

(defn load-front-page
  [request]
  (front-page
    (bootstrap-uri request :css)
    (bootstrap-uri request :css-theme)
    (bootstrap-uri request :js)))

(defn front-page-handler
  [request]
  (load-front-page request))

(defn example-page-handler
  [request]
  (load-example-page request))

(def helmsman-website
  [^{:id :bootstrap
     :css ["css" "bootstrap.css"]
     :css-theme ["css" "bootstrap-theme.css"]
     :js ["js" "bootstrap.js"]}
   [:resources "bootstrap" {:root "bootstrap"}]
   [:get "/" front-page-handler]
   [:get "/example" example-page-handler]])

