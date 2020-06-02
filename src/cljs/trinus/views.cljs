(ns trinus.views
	(:require
		[re-frame.core :as re-frame]
		[trinus.subs :as subs]
		[trinus.events :as events]
		[syn-antd.layout :as layout]
		[syn-antd.input :as input]
		[syn-antd.form :as form]
		[syn-antd.menu :as menu]
		[syn-antd.button :as button]
		[syn-antd.select :as select]
		[syn-antd.breadcrumb :as breadcrumb]
		[syn-antd.page-header :as page-header]
		[syn-antd.table :as table]
		[reitit.frontend.easy :as rfe]
		[reitit.core :as r]))

(defn href
	"Return relative url for given route. Url can be used in HTML links."
	([k]
	 (href k nil nil))
	([k params]
	 (href k params nil))
	([k params query]
	 (rfe/href k params query)))

(defn overview []
	[page-header/page-header
	 {:title "Overview"}])

(defn objectives []
	[page-header/page-header
	 {:title "Objectives"
		:extra [(reagent.core/as-element
							[button/button
							 {:key "1" :type "primary" :on-click #(re-frame/dispatch [::events/navigate :trinus.routes/objective])}
							 "Add Objective"])]}])

(defn plans []
	[page-header/page-header
	 {:title "Plans"}])

(defn tasks []
	[page-header/page-header
	 {:title "Tasks"}])

(defn team []
	[:<>
	 [page-header/page-header
		{:title "Team"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary" :on-click #(re-frame/dispatch [::events/navigate :trinus.routes/member])}
								"Add Member"])]}]
	 (let [team-data @(re-frame/subscribe [::subs/team-data])]
		 [table/table {:columns    [{:title "Name" :dataIndex "name" :key "name"}
																{:title "Email" :dataIndex "email" :key "email"}
																{:title "Role" :dataIndex "role" :key "role"}
																{:title "Status" :dataIndex "status" :key "status"}
																{:title "Actions" :dataIndex "actions" :key "actions"}]
									 :datasource team-data}])])

(defn member []
	[:<>
	 [page-header/page-header
		{:title "Member"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary"}
								"Save"])]}]
	 [form/form
		{:name        "basic"
		 :size        "large"
		 :label-col   {:span 8},
		 :wrapper-col {:span 8}}
		[form/form-item
		 {:label "Name"
			:name  "name"
			:rules [{:required true :message "Please input your name"}]}
		 [input/input]]
		[form/form-item
		 {:label "Email"
			:name  "email"
			:rules [{:required true :message "Please input your email"}]}
		 [input/input]]
		[form/form-item
		 {:label "Role"
			:name  "role"
			:rules [{:required true :message "Please select your roles"}]}
		 [select/select
			{:mode        "multiple"
			 :placeholder "Please select"}
			[select/select-option {:key "1"} "Engineer"]
			[select/select-option {:key "2"} "Designer"]
			[select/select-option {:key "3"} "Manager"]
			[select/select-option {:key "4"} "Executive"]
			[select/select-option {:key "5"} "Obsever"]]]]])

(defn objective []
	[:<>
	 [page-header/page-header
		{:title "Objective"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary"}
								"Save"])]}]
	 [form/form
		{:name        "basic"
		 :size        "large"
		 :label-col   {:span 8},
		 :wrapper-col {:span 8}}
		[form/form-item
		 {:label "Title"
			:name  "title"
			:rules [{:required true :message "Please input the title"}]}
		 [input/input]]
		[form/form-item
		 {:label "Motivation"
			:name  "motivation"
			:rules [{:required true :message "Please define a motivation"}]}
		 [input/input-text-area
			{:placeholder "What is the motivation behind this objective?"}]]
		]])

(defn navigation [& {:keys [router current-route]}]
	[menu/menu
	 {:theme        "dark"
		:mode         "horizontal"
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

(defn main-panel [& {:keys [router]}]
	(let [current-route @(re-frame/subscribe [::subs/current-route])]
		[layout/layout
		 {:style {:height "inherit"}}
		 [layout/layout-header
			[:div.logo]
			[navigation :router router :current-route current-route]]
		 [layout/layout-content {:style {:padding "0 50px" :height "inherit"}}
			(when current-route [(-> current-route :data :view)])]
		 [layout/layout-footer {:style {:text-align "center"}}
			"Trinus ©2020 Created by Eduardo Caceres"]]))
