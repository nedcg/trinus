var sailsIO = require('sails.io.js')(require('socket.io-client'));
sailsIO.sails.url = 'http://localhost:1337';
export const io = sailsIO;