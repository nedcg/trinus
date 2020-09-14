/**
 * Task.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    //  ╔═╗╦═╗╦╔╦╗╦╔╦╗╦╦  ╦╔═╗╔═╗
    //  ╠═╝╠╦╝║║║║║ ║ ║╚╗╔╝║╣ ╚═╗
    //  ╩  ╩╚═╩╩ ╩╩ ╩ ╩ ╚╝ ╚═╝╚═╝
    title: {
      type: 'string',
      required: true,
    },
    description: {
      type: 'string',
      required: true,
    },
    status: {
      type: 'string',
      defaultsTo: 'draft',
      isIn: ['draft', 'ready', 'todo', 'in_progress', 'in_review', 'qa', 'done'],
    },
    estimation: {
      type: 'number',
      allowNull: true,
    },
    assignedTo: {
      type: 'string',
      allowNull: true,
    },
    rank: {
      type: 'string',
    },

    //  ╔═╗╔╦╗╔╗ ╔═╗╔╦╗╔═╗
    //  ║╣ ║║║╠╩╗║╣  ║║╚═╗
    //  ╚═╝╩ ╩╚═╝╚═╝═╩╝╚═╝


    //  ╔═╗╔═╗╔═╗╔═╗╔═╗╦╔═╗╔╦╗╦╔═╗╔╗╔╔═╗
    //  ╠═╣╚═╗╚═╗║ ║║  ║╠═╣ ║ ║║ ║║║║╚═╗
    //  ╩ ╩╚═╝╚═╝╚═╝╚═╝╩╩ ╩ ╩ ╩╚═╝╝╚╝╚═╝
    blockedTasks: {
      collection: 'task',
      via: 'dependsOn',
    },
    dependsOn: {
      model: 'task',
    },
    tags: {
      collection: 'tag',
      via: 'tasks',
    },
    milestones: {
      collection: 'milestone',
      via: 'tasks',
    },
    objectives: {
      collection: 'objective',
      via: 'tasks',
    },
  },

  beforeCreate: async function (recordToCreate, proceed) {
    const next = await sails.helpers.rankNext();
    recordToCreate.rank = next;
    proceed();
  }

};

