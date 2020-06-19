(ns trinus.views
  (:require
   [re-frame.core :as re-frame]
   [trinus.subs :as subs]
   [trinus.events :as events]
   [syn-antd.layout :as layout]
   [syn-antd.input :as input]
   [syn-antd.form :as form]
   [syn-antd.list :as list]
   [syn-antd.card :as card]
   [syn-antd.menu :as menu]
   [syn-antd.icon :as icon]
   [syn-antd.select :as select]
   [syn-antd.icons.heat-map-outlined :as heat-map-outlined]
   [syn-antd.space :as space]
   [syn-antd.button :as button]
   [syn-antd.page-header :as page-header]
   [syn-antd.table :as table]
   [syn-antd.drawer :as drawer]
   [syn-antd.row :as row]
   [syn-antd.col :as col]
   [reitit.frontend.easy :as rfe]
   [reagent.core :as reagent]
   [reitit.core :as r]))

(defn href
  "Return relative url for given route. Url can be used in HTML links."
  ([k]
   (href k nil nil))
  ([k params]
   (href k params nil))
  ([k params query]
   (rfe/href k params query)))

(defn task-form [mode]
  (let [{:keys [title description]} @(re-frame/subscribe [::subs/form-data])]
    [form/form {:layout "vertical"}
     [row/row {:gutter "16"}
      [col/col {:span "24"}
       [form/form-item
        {:name "title"
         :label "Title"}
        [input/input title]]]]
     [row/row {:gutter "16"}
      [col/col {:span "24"}
       [form/form-item
        {:name "description"
         :label "Description"}
        [input/input-text-area {:rows 10} description]]]]]))

(defn objective-form [mode]
  (let [{:keys [title motivation]} @(re-frame/subscribe [::subs/form-data])]
    [form/form {:layout "vertical"}
     [row/row {:gutter "16"}
      [col/col {:span "24"}
       [form/form-item
        {:name "title"
         :label "Title"}
        [input/input title]]]]
     [row/row {:gutter "16"}
      [col/col {:span "24"}
       [form/form-item
        {:name "motivation"
         :label "Motivation"}
        [input/input-text-area {:rows 10} motivation]]]]]))

(defn member-form [mode]
  (let [{:keys [name email role]} @(re-frame/subscribe [::subs/form-data])]
    [form/form {:layout "vertical"}
     [row/row {:gutter "16"}
      [col/col {:span "24"}
       [form/form-item
        {:label "Name"
         :name  "name"
         :rules [{:required true}]}
        [input/input name]]]
      [col/col {:span "24"}
       [form/form-item
        {:label "Email"
         :name  "email"
         :rules [{:required true}]}
        [input/input email]]]
      [col/col {:span "24"}
       [form/form-item
        {:label "Role"
         :name  "role"
         :rules [{:required true}]}
        [select/select
         {:mode        "multiple"
          :value role
          :placeholder "Please select"}
         [select/select-option {:key "1"} "Engineer"]
         [select/select-option {:key "2"} "Designer"]
         [select/select-option {:key "3"} "Manager"]
         [select/select-option {:key "4"} "Executive"]
         [select/select-option {:key "5"} "Obsever"]]]]]]))

(defn overview []
  [page-header/page-header
   {:title "Overview"}])

(defn objectives []
  [:<>
   [page-header/page-header
    {:title "Objectives"
     :extra [(reagent.core/as-element
              [button/button
               {:key "1"
                :type "primary"
                :onClick (fn []
                           (re-frame/dispatch [::events/set-current-form :objective :new])
                           (re-frame/dispatch [::events/set-drawer-open? true]))}
               "Create Objective"])]}]
   [list/list
    {:grid       {:gutter 16 :column 3}
     :dataSource [{:title "Increase internet presence" :motivation "none"}]
     :renderItem #(let [item (js->clj % :keywordize-keys true)
                        {:keys [title motivation]} item]
                    (reagent.core/as-element
                     [list/list-item
                      [card/card
                       {:title title
                        :extra (reagent.core/as-element
                                [space/space
                                 [button/button
                                  {:type "default"
                                   :on-click (fn []
                                               (re-frame/dispatch [::events/set-current-form :objective :edit])
                                               (re-frame/dispatch [::events/set-drawer-open? true]))}
                                  "Edit"]
                                 [button/button {:type "primary"} "Review"]])}
                       motivation]]))}]])

(defn plans []
  [:<>
   [page-header/page-header
    {:title "Plans"
     :extra [(reagent.core/as-element
              [button/button
               {:key "1"
                :type "primary"
                :on-click (fn []
                            (re-frame/dispatch [::events/set-current-form :task :new])
                            (re-frame/dispatch [::events/set-drawer-open? true]))}
               "Create Task"])]}]
   [list/list
    {:bordered   true}
    (for [{:keys [id title comments-count] :as task} [{:id "1" :title "Implement website" :comments-count 10}
                                                      {:id "2" :title "Create css files" :comments-count 3}]]
      [list/list-item
       {:key     id
        :actions [(reagent.core/as-element
                   [button/button
                    {:key "1"
                     :on-click (fn []
                                 (re-frame/dispatch [::events/set-current-form :task :view])
                                 (re-frame/dispatch [::events/set-drawer-open? true]))}
                    "Detail"])]}
       [list/list-item-meta
        {:title title}]])]])

(defn tasks []
  [page-header/page-header
   {:title "Tasks"}])

(defn team []
  [:<>
   [page-header/page-header
    {:title "Team"
     :extra [(reagent.core/as-element
              [button/button
               {:key "1"
                :type "primary"
                :on-click (fn []
                            (re-frame/dispatch [::events/set-current-form :member :new])
                            (re-frame/dispatch [::events/set-drawer-open? true]))}
               "Add Member"])]}]
   (let [team-data @(re-frame/subscribe [::subs/team-data])]
     [table/table {:columns    [{:title "Name" :dataIndex "name" :key "name"}
                                {:title "Email" :dataIndex "email" :key "email"}
                                {:title "Role" :dataIndex "role" :key "role"}
                                {:title "Status" :dataIndex "status" :key "status"}
                                {:title "Actions" :dataIndex "actions" :key "actions"}]
                   :datasource team-data}])])

(defn navigation [& {:keys [router current-route]}]
  [menu/menu
   {:mode         "horizontal"
    :selectedKeys [(or
                    (-> current-route :data :parent)
                    (-> current-route :data :name))]}
   (for [route-name (r/route-names router)
         :let [route (r/match-by-name router route-name)
               text  (-> route :data :link-text)]]
     [menu/menu-item
      {:key      route-name
       :on-click #(re-frame/dispatch [::events/navigate route-name])}
      text])])

(defn- get-title [form-name mode]
  (let [entity-text (condp = form-name
                      :task "task"
                      :objective "objective"
                      :member "team member"
                      nil)
        action-text (condp = mode
                      :new "New"
                      "Viewing")]
    (str action-text " " action-text)))

(defn main-drawer []
  (let [visible? @(re-frame/subscribe [::subs/drawer-open?])
        [form-name mode] @(re-frame/subscribe [::subs/current-form])]
    [drawer/drawer
     {:title (get-title form-name mode)
      :width "50%"
      :onClose #(re-frame/dispatch [::events/set-drawer-open? false])
      :visible visible?
      :footer [(reagent.core/as-element
                [:div {:style {:textAlign "right"}}
                 [space/space
                  [button/button
                   {:on-click #(re-frame/dispatch [::events/set-drawer-open? false])}
                   "Cancel"]
                  [button/button {:type "primary"}
                   "Save"]]])]}
     (condp = form-name
       :task [task-form mode]
       :objective [objective-form mode]
       :member [member-form mode]
       nil)]))

(defn main-panel [& {:keys [router]}]
  (let [current-route @(re-frame/subscribe [::subs/current-route])]
    [layout/layout
     {:style {:height "inherit"
              :background "#fff"}}
     [layout/layout-header
      {:style {:padding 0}}
      [heat-map-outlined/heat-map-outlined
       {:style {:fontSize "2em"
                :float "left"
                :width "3em"
                :height "31px"
                :padding ".7em"
                :color "#1890ff"}}]
      [navigation :router router :current-route current-route]]
     [layout/layout-content {:style {:padding "0 50px" :height "inherit"}
                             :theme "light"}
      (when current-route [(-> current-route :data :view)])
      [main-drawer]]]))
