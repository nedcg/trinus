import React from "react";
import { Comment, List } from 'antd';
import moment from 'moment';

const mapComment = (comment) => ({
    author: 'Han Solo',
    avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
    content: <p>{comment.content}</p>,
    datetime: moment(comment.createdAt).fromNow(),
});

export default function CommentList({ comments }) {
    return (
        <List
            dataSource={comments.map(mapComment)}
            header={`${comments.length} ${comments.length > 1 ? 'comments' : 'comment'}`}
            itemLayout="horizontal"
            renderItem={props => <Comment {...props} />}
        />
    );
};