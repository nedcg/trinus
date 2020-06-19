(ns trinus.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub ::name #(:name %))
(re-frame/reg-sub ::current-route #(:current-route %))
(re-frame/reg-sub ::team-data #(:team-data %))
(re-frame/reg-sub ::drawer-open? #(:drawer-open? %))
(re-frame/reg-sub ::current-form #(:current-form %))
(re-frame/reg-sub ::form-data #(:form-data %))
