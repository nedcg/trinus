import React from "react";

import { sortableContainer, sortableElement } from 'react-sortable-hoc';
import { EditOutlined, MessageOutlined } from '@ant-design/icons';
import { Tag, Card, Button, Space, Badge } from 'antd';

const SortableItem = sortableElement(({ value }) => (<>{value}</>));
const SortableContainer = sortableContainer(({ children }) => (<div id="objectivesContainer">{children}</div>));

export default function ObjectiveList({ objectives, onEdit, onComments, onSortEnd }) {
    const tagColor = (status) => {
        switch (status) {
            case 'published':
                return 'blue';
            case 'cancelled':
                return 'red';
            case 'completed':
                return 'green';
            default:
                return 'purple';
        }
    }

    return (
        <SortableContainer
            pressDelay={200}
            lockToContainerEdges={true}
            lockOffset="0%"
            onSortEnd={onSortEnd}
            lockAxis="y">
            {objectives.map((value, index) => (
                <SortableItem key={`${value.name}-${index}`} index={index} value={
                    <Card
                        title={
                            <>
                                <Tag color={tagColor(value.status)}>{value.status}</Tag>
                                <Button type="link" onClick={() => onEdit(value)}>{value.name}</Button>
                            </>
                        }
                        extra={
                            <Space direction="horizontal">
                                <Badge>
                                    <MessageOutlined key="messages" onClick={() => onComments(value)} >
                                        <Button type="link"></Button>
                                    </MessageOutlined>
                                </Badge>
                                <EditOutlined key="edit" onClick={() => onEdit(value)} >
                                    <Button type="link"></Button>
                                </EditOutlined>
                            </Space>
                        }
                        style={{ margin: '1em' }}
                        size="small">
                        {value.description}
                    </Card>
                } />
            ))}
        </SortableContainer>)
}