import React from "react";
import { Form, Button, Input } from 'antd';

const { TextArea } = Input;

export default function CommentForm({ onChange, onSubmit, submitting, value }) {
    return (
        <>
            <Form.Item>
                <TextArea rows={4} onChange={onChange} value={value} />
            </Form.Item>
            <Form.Item>
                <Button htmlType="submit" loading={submitting} onClick={onSubmit} type="primary">
                    Add Comment
                </Button>
            </Form.Item>
        </>
    );
}