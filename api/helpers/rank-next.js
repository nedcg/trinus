const { LexoRank } = require('lexorank');

module.exports = {

  friendlyName: 'Rank next',
  description: '',

  inputs: {

  },

  exits: {

    success: {
      description: 'next rank generated',
    },

  },

  fn: async function () {
    const [{ current, id }] = await Rank.find({});
    const next = LexoRank.parse(current).genNext().toString();
    await Rank.update({ id: id, current: current }).set({ current: next });
    return next;
  }

};

