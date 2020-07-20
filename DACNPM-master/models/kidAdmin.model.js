const bcrypt = require('bcryptjs');
const db = require('../utils/db');

module.exports = {
    add: entity => {
        const hash = bcrypt.hashSync(entity.password, 8);
        entity.password = hash;
        return db.add(entity, 'kid_admin');
    },
    all: () => db.load(`SELECT * FROM kid_admin`),

    singleByEmail: email => db.load(`SELECT * FROM kid_admin WHERE email = '${email}' `),

    singleByID: id => db.load(`SELECT * FROM kid_admin WHERE id = '${id}'  `),
};