import React from "react";
import { PageHeader, List, Card, Button } from 'antd';

function Tasks() {

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

    return (
        <>
            <PageHeader
                backIcon={false}
                title="Tasks"
                subTitle="Tasks stream"
                extra={[
                    <Button key="1" type="primary">
                        Create Task
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
        </>
    );
}

export default Tasks;