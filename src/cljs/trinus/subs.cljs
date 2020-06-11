(ns trinus.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub ::name #(:name %))
(re-frame/reg-sub ::current-route #(:current-route %))
(re-frame/reg-sub ::team-data #(:team-data %))
(re-frame/reg-sub ::task-drawer-open? #(:task-drawer-open? %))
