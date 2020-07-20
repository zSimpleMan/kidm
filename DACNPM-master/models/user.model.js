const bcrypt = require('bcryptjs');
const db = require('../utils/db');

module.exports = {
    add: entity => {
        const hash = bcrypt.hashSync(entity.password, 8);
        entity.password = hash;
        return db.add(entity, 'users');
    },
    all: () => db.load(`SELECT * FROM users`),

    singleByEmail: email => db.load(`select * from users where email = '${email}' `),

    singleByID: id => db.load(`select * from users where id = '${id}'  `),

    singleProfileByID: id => db.load(`select *,DATE_FORMAT(birthday, "%Y-%m-%d") as birthday from user_profile where id = '${id}' `),


    updateProfile: (entity, id) => { return db.update(entity, id, 'user_profile'); },

    addProfile: entity => db.add(entity, 'user_profile'),

    updateProfile: (entity, id) => {
        return db.update(entity, id, 'user_profile');
    },
    addProfile: entity => db.add(entity, 'user_profile'),
    getKidByID: id_parent => db.load(`select * from parents where id_parent='${id_parent}'`),

    addLocation: entity => db.add(entity, 'location'),
    updateLocation: (entity, code) => { return db.updateL(entity, code, 'location'); },
    getLocation: code => db.load(`select * from location where CODE = ${code}`),
};