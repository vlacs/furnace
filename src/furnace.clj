(ns furnace
  (:require [helmsman]
            [helmsman.uri :as h-uri]
            [helmsman.navigation :as h-nav]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate core-page "templates/example.html"
  [bootstrap-css-url
   bootstrap-css-theme-url
   bootstrap-js-url]

  [[:link (html/attr-has :href "bootstrap.css")]] (html/set-attr :href bootstrap-css-url)
  [[:link (html/attr-has :href "bootstrap-theme.css")]] (html/set-attr :href bootstrap-css-theme-url)
  [[:script (html/attr-has :src "bootstrap.js")]] (html/set-attr :src bootstrap-js-url))

(defn load-core-page
  [request]
  (let [path-here (get-in request [:helmsman :uri-path])
        bootstrap (h-nav/meta-from-request request (h-nav/pred-by-id :bootstrap))]
    (core-page
      (h-uri/assemble
        (h-uri/relative-uri
          path-here
          (conj (:uri-path bootstrap) (:css bootstrap))))
      (h-uri/assemble
        (h-uri/relative-uri
          path-here
          (conj (:uri-path bootstrap) (:css-theme bootstrap))))
      (h-uri/assemble
        (h-uri/relative-uri
          path-here
          (conj (:uri-path bootstrap) (:js bootstrap)))))))

(defn front-page-handler
  [request]
  (load-core-page request))

(def helmsman-website
  [^{:id :bootstrap
     :css ["css" "bootstrap.css"]
     :css-theme ["css" "bootstrap-theme.css"]
     :js ["js" "bootstrap.js"]}
   [:resources "bootstrap" {:root "bootstrap"}]
   [:get "/" front-page-handler
    [:get "/nested" front-page-handler
     [:get "/inside" front-page-handler]]]])
