import React from "react";

import { Form, Input, Select, Button } from 'antd';
import { useEffect } from "react";
let { Option } = Select;

export default function ObjectiveForm({ objective, onCreate, onUpdate }) {

    const [form] = Form.useForm();

    useEffect(() => {
        if (objective.id) {
            form.setFieldsValue(objective);
        } else {
            form.resetFields();
        }
    }, [objective]);

    return (
        <Form form={form} layout='vertical' initialValues={objective}>
            <Form.Item name="id" label="Id" hidden={true}>
                <Input />
            </Form.Item>
            <Form.Item name="name" label="Name" rules={[{ required: true }]}>
                <Input />
            </Form.Item>
            <Form.Item name="description" label="Description" rules={[{ required: true }]}>
                <Input.TextArea rows={6} />
            </Form.Item>
            <Form.Item name="status" label="Status" rules={[{ required: true }]}>
                <Select placeholder="Select objective current status">
                    <Option value="draft">Draft</Option>
                    <Option value="published">Published</Option>
                    <Option value="completed">Completed</Option>
                    <Option value="cancelled">Cancelled</Option>
                </Select>
            </Form.Item>

            <Form.Item>
                {objective.id ?
                    <Button onClick={() => onUpdate(form.getFieldsValue())} type="primary">
                        Save
                    </Button>
                    :
                    <Button onClick={() => onCreate(form.getFieldsValue())} type="primary">
                        Create
                    </Button>
                }
            </Form.Item>
        </Form>
    );
}