import { atom } from 'recoil'

export const objectives = atom({
    key: 'objectives',
    default: [],
});

export const comments = atom({
    key: 'comments',
    default: [],
});

export default {
    objectives, comments
};