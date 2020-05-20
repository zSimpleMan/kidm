const bcrypt = require('bcryptjs');
const db = require('../utils/db');

module.exports = {
    add: entity => {
        // entity = {
        //     "password": "pvhau",
        //     "email": "pvhaun@gmail.com"
        // }
        const hash = bcrypt.hashSync(entity.password, 8);
        entity.password = hash;
        return db.add(entity, 'users');
    },

    singleByEmail: email => db.load(`select * from users where email = '${email}'  `),
    singleByID: id => db.load(`select * from users where id = '${id}'  `),
    singleProfileByID: id => db.load(`select * from user_profile where id = '${id}' `),
    updateProfile: (entity, id) => {
        return db.update(entity, id, 'user_profile');
    },
    addProfile: entity => db.add(entity,'user_profile')
};