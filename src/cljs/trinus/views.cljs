(ns trinus.views
	(:require
		[re-frame.core :as re-frame]
		[trinus.subs :as subs]
		[trinus.events :as events]
		[syn-antd.layout :as layout]
		[syn-antd.menu :as menu]
		[syn-antd.button :as button]
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
	 {:title "Objectives"}])

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
																{:title "Status" :dataIndex "status" :key "status"}]
									 :datasource team-data}])])

(defn member []
	[:<>
	 [page-header/page-header
		{:title "Member"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary"}
								"Save"])]}]])

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
