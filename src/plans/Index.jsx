import React, { useState } from "react";

import { PageHeader, List, Card, Button, Drawer } from 'antd';

function Plans() {

    const data = [
        {
            title: 'Title 1',
        },
        {
            title: 'Title 2',
        },
        {
            title: 'Title 3',
        },
        {
            title: 'Title 4',
        },
        {
            title: 'Title 5',
        },
        {
            title: 'Title 6',
        },
    ];

    const [drawerVisible, setDrawerVisible] = useState(false);

    return (
        <div style={{ margin: '24px 16px 0', }}>
            <PageHeader
                backIcon={false}
                title="Plans"
                subTitle="Plans and milestones"
                extra={[
                    <Button key="1" type="primary" onClick={() => setDrawerVisible(true)}>
                        Create Task
                    </Button>,
                    <Button key="2" type="primary">
                        Create Milestone
                    </Button>,
                ]} />
            <List
                grid={{
                    gutter: 16,
                    xs: 1,
                    sm: 1,
                    md: 1,
                    lg: 1,
                    xl: 1,
                    xxl: 1,
                }}
                dataSource={data}
                renderItem={item => (
                    <List.Item>
                        <Card title={item.title}>Card content</Card>
                    </List.Item>
                )}
            />
            <Drawer
                title="Basic Drawer"
                placement="left"
                closable={true}
                onClose={() => setDrawerVisible(false)}
                visible={drawerVisible}
                getContainer={false}
                style={{ position: 'absolute' }}
            >
                <p>Some contents...</p>
            </Drawer>
        </div>
    );
}

export default Plans;