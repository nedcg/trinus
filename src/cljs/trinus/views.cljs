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
		[syn-antd.icons.message-outlined :as message-outlined]
		[syn-antd.space :as space]
		[syn-antd.button :as button]
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
	[:<>
	 [page-header/page-header
		{:title "Objectives"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary" :on-click #(re-frame/dispatch [::events/navigate :trinus.routes/objective])}
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
																	 [button/button {:type "default"} "Edit"]
																	 [button/button {:type "primary"} "Review"]])}
												motivation]]))}]])

(defn plans []
	[:<>
	 [page-header/page-header
		{:title "Plans"
		 :extra [(reagent.core/as-element
							 [button/button
								{:key "1" :type "primary" :on-click #(re-frame/dispatch [::events/navigate :trinus.routes/objective])}
								"Create Task"])]}]
	 [list/list
		{:size       "small"
		 :bordered   true
		 :itemLayout "vertical"
		 :dataSource [{:id "1" :title "Implement website" :comments-count 10}
									{:id "2" :title "Create css files" :comments-count 3}]
		 :renderItem #(let [item (js->clj % :keywordize-keys true)
												{:keys [id title comments-count]} item]
										(reagent.core/as-element
											[list/list-item
											 {:actions [(reagent.core/as-element
																		[space/space
																		 [message-outlined/message-outlined]
																		 comments-count])]
												:key     id}
											 [list/list-item-meta
												{:title title}]]))}]])

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
			{:placeholder "What is the motivation behind this objective?"}]]]])

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

(defn main-panel [& {:keys [router]}]
	(let [current-route @(re-frame/subscribe [::subs/current-route])]
		[layout/layout
		 {:style {:height "inherit"
							:background "#fff"}}
		 [layout/layout-header
			{:style {:padding 0}}
			[:div.logo]
			[navigation :router router :current-route current-route]]
		 [layout/layout-content {:style {:padding "0 50px" :height "inherit"}
														 :theme "light"}
			(when current-route [(-> current-route :data :view)])]]))
