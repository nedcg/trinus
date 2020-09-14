import React, { useState, useEffect } from "react";
import { Comment, Avatar } from 'antd';
import CommentList from './CommentList'
import CommentForm from './CommentForm'
import {
    useRecoilState
} from 'recoil';
import db from '../db';
import { io } from '../commons/sails';

function Comments({ reference, referenceId }) {
    const [comments, setComments] = useRecoilState(db.comments);
    const [submitting, setSubmitting] = useState(false);
    const [content, setContent] = useState('');

    const handleSubmit = () => {
        if (!content) {
            return;
        }
        setSubmitting(true);
        io.socket.post('/comment', {
            referenceId,
            reference,
            content,
        },
            (comment) => {
                setComments((currentComments) => [...currentComments, comment]);
                setSubmitting(false);
            });
    };

    const handleChange = e => {
        setContent(e.target.value);
    };

    useEffect(() => {
        if (!referenceId) {
            return;
        }

        io.socket.get(`/comment`, {
            where: {
                reference,
                referenceId,
            },
            sort: 'id desc'
        }, function (newComments) {
            setComments(newComments);
        });

        io.socket.on('comment', (comment) => {
            if (comment.verb === 'create' &&
                comment.reference === reference &&
                comment.referenceId === referenceId) {
                setComments((currentComments) => [...currentComments, comment]);
            }
        });

        return () => {
            io.socket.off('comment');
        };
    }, [reference, referenceId]);

    return (
        <>
            {comments.length > 0 && <CommentList comments={comments} />}
            <Comment
                avatar={
                    <Avatar
                        src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                        alt="Han Solo"
                    />
                }
                content={
                    <CommentForm
                        onChange={handleChange}
                        onSubmit={handleSubmit}
                        submitting={submitting}
                        value={content}
                    />
                }
            />
        </>
    );
}

export default Comments;