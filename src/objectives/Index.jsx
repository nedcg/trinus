import React, { useState, useEffect } from "react";
import {
    useRecoilState
} from 'recoil';

import Comments from '../comments/Index.jsx';
import ObjectiveForm from './ObjectiveForm.jsx';
import ObjectiveList from './ObjectiveList.jsx';
import db from '../db';
import arrayMove from 'array-move';
import { io } from '../commons/sails';

import { PageHeader, Button, Drawer, message } from 'antd';

const initialObjective = { status: 'draft' };

function Objectives() {

    const [objectives, setObjectives] = useRecoilState(db.objectives);
    const [objective, setObjective] = useState(initialObjective);
    const [isDrawerObjectiveOpen, setIsDrawerObjectiveOpen] = useState(false);
    const [isDrawerCommentsOpen, setIsDrawerCommentsOpen] = useState(false);

    const onCreate = () => {
        setObjective(initialObjective);
        setIsDrawerObjectiveOpen(true);
    }

    const onEdit = (objective) => {
        setObjective(objective);
        setIsDrawerObjectiveOpen(true);
    };

    const onComments = (objective) => {
        setObjective(objective);
        setIsDrawerCommentsOpen(true);
    };

    const onSortEnd = ({ oldIndex, newIndex }) => {
        setObjectives((items) => arrayMove(items, oldIndex, newIndex));
    };

    const onDrawerClose = () => {
        setIsDrawerObjectiveOpen(false);
        setIsDrawerCommentsOpen(false);
    };

    const onFormCreate = (objective) => {
        io.socket.post('/objective',
            objective,
            (newObjective) => {
                message.info({
                    content: `Objective "${newObjective.name}" created`,
                });
                setObjectives(items => [...items, newObjective]);
                setObjective(initialObjective);
            });
    };

    const onFormUpdate = (objective) => {
        io.socket.patch(`/objective/${objective.id}`,
            objective,
            (updatedObjective) => {
                message.info({
                    content: `Objective "${updatedObjective.name}" updated`,
                });
                const index = objectives.findIndex((listItem) => listItem.id === updatedObjective.id);
                setObjectives(replaceItemAtIndex(objectives, index, updatedObjective));
            });
    };

    useEffect(() => {
        io.socket.get('/objective', function (objectives) {
            setObjectives(objectives);
        });

        io.socket.on('objective', (objective) => {
            switch (objective.verb) {
                case 'create':
                    setObjectives((items) => [...items, objective.data]);
                    break;
                case 'update':
                    const index = objectives.findIndex((listItem) => listItem.id === objective.data.id);
                    setObjectives(replaceItemAtIndex(objectives, index, objective.data));
                    break;
            }
        });

        return function cleanup() {
            io.socket.off('objective');
        };
    }, []);

    return (
        <>
            <PageHeader
                backIcon={false}
                title="Objectives"
                subTitle="Company wise objectives"
                extra={[
                    <Button key="1" type="primary" onClick={onCreate}>
                        Create Objective
                    </Button>,
                ]} />
            <Drawer
                title={objective.id ? 'Updating Objective' : 'Creating Objective'}
                width='40vw'
                placement="left"
                closable={false}
                onClose={onDrawerClose}
                visible={isDrawerObjectiveOpen}
                getContainer={false}
                style={{ position: 'absolute' }}>
                <ObjectiveForm
                    objective={objective}
                    onCreate={onFormCreate}
                    onUpdate={onFormUpdate} />
            </Drawer>
            {objective.id &&
                <Drawer
                    title={`${objective.name}`}
                    width='40vw'
                    placement="left"
                    closable={false}
                    onClose={onDrawerClose}
                    visible={isDrawerCommentsOpen}
                    getContainer={false}
                    style={{ position: 'absolute' }}>
                    <Comments
                        reference='objective'
                        referenceId={objective.id} />
                </Drawer>}
            <ObjectiveList
                objectives={objectives}
                onEdit={onEdit}
                onComments={onComments}
                onSortEnd={onSortEnd} />
        </>
    );
}

function replaceItemAtIndex(arr, index, newValue) {
    return [...arr.slice(0, index), newValue, ...arr.slice(index + 1)];
}

export default Objectives;