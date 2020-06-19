(ns trinus.routes
	(:require
		[re-frame.core :as re-frame]
		[trinus.views :as views]
		[trinus.events :as events]
		[reitit.frontend.easy :as rfe]
		[reitit.coercion.spec :as rss]
		[reitit.frontend :as rf]))

(def routes
  ["/"
   [""
    {:name      ::overview
     :view      views/overview
     :link-text "Overview"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering overview page"))
       :stop  (fn [& params] (js/console.log "Leaving overview page"))}]}]
   ["objectives"
    {:name      ::objectives
     :view      views/objectives
     :link-text "Objectives"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering objectives page"))
       :stop  (fn [& params] (js/console.log "Leaving objectives page"))}]}]
   ["plans"
    {:name      ::plans
     :view      views/plans
     :link-text "Plans"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering plans page"))
       :stop  (fn [& params] (js/console.log "Leaving plans page"))}]}]
   ["tasks"
    {:name      ::tasks
     :view      views/tasks
     :link-text "Tasks"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering tasks page"))
       :stop  (fn [& params] (js/console.log "Leaving tasks page"))}]}]
   ["team"
    {:name      ::team
     :view      views/team
     :link-text "Team"
     :controllers
     [{:start (fn [& params] (js/console.log "Entering team page"))
       :stop  (fn [& params] (js/console.log "Leaving team page"))}]}]])

(defn on-navigate [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigated new-match])))

(def router
  (rf/router
   routes
   {:data {:coercion rss/coercion}}))

(defn init-routes! []
  (js/console.log "initializing routes")
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))
