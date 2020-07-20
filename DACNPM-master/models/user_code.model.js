const db = require('../utils/db');

module.exports = {
    all: () => db.load(`SELECT * FROM user_code`),

    add: (entity) => db.add(entity, 'user_code'),

    singleByCode: code => db.load(`SELECT * FROM user_code WHERE code = '${code}' `)
};