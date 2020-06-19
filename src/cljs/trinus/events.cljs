(ns trinus.events
  (:require
   [re-frame.core :as re-frame]
   [trinus.db :as db]
   [trinus.fx :as fx]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [reitit.frontend.controllers :as rfc]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced
  [_ _]
  db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced
  [db [_ & route]]
  {::fx/navigate! route}))

(re-frame/reg-event-db
 ::navigated
 (fn-traced
  [db [_ new-match]]
  (let [old-match   (:current-route db)
        controllers (rfc/apply-controllers (:controllers old-match) new-match)]
    (assoc db :current-route (assoc new-match :controllers controllers)))))

(re-frame/reg-event-db
 ::set-drawer-open?
 (fn-traced
  [db [_ open?]]
  (assoc db :drawer-open? open?)))

(re-frame/reg-event-db
 ::set-current-form
 (fn-traced
  [db [_ form-name mode]]
  (assoc db :current-form [form-name mode])))

(re-frame/reg-event-db
 ::set-form-data
 (fn-traced
  [db [_ data]]
  (assoc db :form-data data)))
