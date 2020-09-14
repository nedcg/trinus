/**
 * Seed Function
 * (sails.config.bootstrap)
 *
 * A function that runs just before your Sails app gets lifted.
 * > Need more flexibility?  You can also create a hook.
 *
 * For more information on seeding your app with fake data, check out:
 * https://sailsjs.com/config/bootstrap
 */
const ObjectID = require('bson-objectid');
const { LexoRank } = require('lexorank');

module.exports.bootstrap = async function () {

  await Rank.create({ current: LexoRank.middle().toString() });

  const userId = 1;

  const objectives = [
    { id: 1, name: 'Lorem ipsum dolor sit amet', status: 'draft', createdBy: userId, description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices neque ac magna laoreet, a consequat neque ullamcorper. Curabitur bibendum erat eget neque posuere, eget auctor nisl ultrices. Nulla non magna et nulla tincidunt feugiat. Phasellus et feugiat turpis, eu aliquam odio. Vivamus finibus elit neque, vitae consequat velit accumsan vitae. Integer pretium mollis ligula sit amet condimentum. Nunc mi mauris, condimentum at lobortis id, vehicula non quam. Pellentesque cursus risus ac pretium facilisis. Suspendisse magna enim, commodo vel iaculis nec, mattis id purus. Pellentesque eleifend nunc eu nulla volutpat, id posuere sem molestie.' },
    { id: 2, name: 'Donec eget eleifend metus', status: 'published', createdBy: userId, description: 'Donec eget eleifend metus. In pharetra ligula nec neque commodo, consectetur semper arcu pretium. Nulla consectetur augue a interdum congue. Curabitur a facilisis libero, eu scelerisque neque. Quisque auctor a leo pulvinar efficitur. Sed egestas tortor nec consequat luctus. Proin ligula enim, interdum ut ullamcorper et, lobortis in lorem. Sed luctus cursus ante. Ut non dolor tincidunt odio dapibus pulvinar quis eget est. Ut aliquet suscipit neque, at blandit nisi ultricies in. Sed a malesuada ex. Etiam a diam faucibus, condimentum metus in, blandit nisl. Fusce vulputate ante dictum erat feugiat maximus.' },
    { id: 3, name: 'Curabitur eget efficitur nunc', status: 'published', createdBy: userId, description: 'Curabitur eget efficitur nunc. Sed gravida vitae nulla at mattis. Nam ac massa nec nibh pellentesque eleifend. Cras ac est nulla. Etiam malesuada dolor eget ex maximus, in pretium justo auctor. Fusce non dolor et ante feugiat blandit in eget metus. Phasellus sed velit libero. Duis at vehicula lorem. Curabitur erat elit, convallis vitae pellentesque eu, congue non diam. Maecenas placerat et tortor vel fringilla.' },
    { id: 4, name: 'Aliquam molestie placerat bibendum', status: 'cancelled', createdBy: userId, description: 'Aliquam molestie placerat bibendum. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse potenti. Nam hendrerit, lectus bibendum sollicitudin sagittis, magna est sagittis magna, nec egestas libero ex ut ante. Sed convallis risus id egestas sagittis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin ornare ultricies lorem, at porttitor sem sodales eu. Duis euismod vel lectus sit amet accumsan. Donec a nisl eu diam pharetra ultricies.' },
  ];

  for (const objective of objectives) {
    await Objective.create(objective);
  }

  const objectiveComments = [
    { id: 1, createdBy: userId, reference: 'objective', referenceId: 2, content: 'Phasellus ac tincidunt nisl. Fusce aliquam nunc at risus varius iaculis. Maecenas rhoncus euismod mauris nec imperdiet. Aliquam erat volutpat. Phasellus eget enim fermentum, interdum ligula placerat, feugiat enim. Pellentesque facilisis neque commodo risus dignissim rhoncus. Phasellus tincidunt augue at nibh luctus volutpat' },
    { id: 2, createdBy: userId, reference: 'objective', referenceId: 2, content: 'Sed pellentesque iaculis feugiat. Vestibulum eu enim est. Suspendisse faucibus magna eu mattis placerat. Curabitur ultricies augue ante, ut auctor enim malesuada eget. Nam vitae consectetur metus. Sed porta ex in tortor lobortis pharetra. Sed dignissim, neque vel aliquet scelerisque, lectus tortor commodo nisl, eget rutrum leo nisi non ante' },
    { id: 3, createdBy: userId, reference: 'objective', referenceId: 2, content: 'Donec ac justo vitae felis tristique venenatis. Fusce suscipit, elit id pretium pretium, ligula est rhoncus sem, sed iaculis risus sem vel mi. Nam malesuada sodales neque, a venenatis justo auctor id. Aliquam erat volutpat. Morbi ullamcorper hendrerit metus vel finibus. Praesent nec tortor id tortor cursus lobortis. Aliquam ut condimentum metus. In hac habitasse platea dictumst' },
    { id: 4, createdBy: userId, reference: 'objective', referenceId: 2, content: 'Nullam commodo eros ac turpis finibus vehicula. Curabitur ultricies bibendum magna, bibendum lobortis elit semper sit amet. Nullam consectetur porta felis in feugiat. Etiam in urna vel risus congue consequat. Suspendisse nec consectetur turpis. In sed ultricies odio. Phasellus posuere bibendum augue a rhoncus. In iaculis ullamcorper pretium. Praesent sed facilisis nisi. Curabitur ac massa mollis, euismod turpis vel, eleifend nisl. Duis pretium tellus quis ante laoreet, sed ornare nunc consequat. Ut in mollis orci. Quisque eu pulvinar purus. Praesent eleifend eros ligula, in interdum orci malesuada sed. Sed sed egestas dui' },
  ];

  for (const comment of objectiveComments) {
    await Comment.create(comment);
  }

};
