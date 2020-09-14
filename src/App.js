import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
} from "react-router-dom";

import Objectives from './objectives/Index.jsx';
import Tasks from './tasks/Index.jsx';
import Plans from './plans/Index.jsx';

import './App.less';
import { CrownOutlined, TagsOutlined, AppstoreAddOutlined, SettingOutlined } from '@ant-design/icons';
import { Layout, Menu } from 'antd';

const { Content, Sider } = Layout;
const { Item } = Menu;

function App() {

  return (
    <Router>
      <Layout style={{ height: 'inherit' }}>
        <Sider
          height='100vh'
          breakpoint="lg"
          collapsedWidth="0">
          <Menu theme="dark" mode="inline">
            <Item icon={<CrownOutlined />} >
              <Link to="/objectives" >Objectives</Link>
            </Item>
            <Item icon={<TagsOutlined />} >
              <Link to="/plans">Plans</Link>
            </Item>
            <Item icon={<AppstoreAddOutlined />} >
              <Link to="/tasks">Tasks</Link>
            </Item>
            <Item icon={<SettingOutlined />} >
              <Link to="/settings">Settings</Link>
            </Item>
          </Menu>
        </Sider>

        <Switch>
          <Layout style={{ height: 'inherit' }}>
            <Content style={{ position: 'relative' }}>
              <Route exact path="/objectives">
                <Objectives />
              </Route>
              <Route path="/plans">
                <Plans />
              </Route>
              <Route path="/tasks">
                <Tasks />
              </Route>
              <Route path="/settings">
                <h2>Settings</h2>
              </Route>
            </Content>
          </Layout>
        </Switch>
      </Layout>
    </Router >
  );
}

export default App;