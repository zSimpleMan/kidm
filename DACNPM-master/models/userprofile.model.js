const db = require('../utils/db');

module.exports = {
    all: () => db.load(`SELECT * FROM user_profile`),

    addUser: (entity) => db.add(entity, 'user_profile')
};