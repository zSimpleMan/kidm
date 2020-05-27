const bcrypt = require('bcryptjs');
const db = require('../utils/db');

module.exports = {
    add: entity => {
        const hash = bcrypt.hashSync(entity.password, 8);
        entity.password = hash;
        return db.add(entity, 'parents');
    },
    all: () => db.load(`SELECT * FROM parents`),

    singleByEmail: email => db.load(`select * from parents where email = '${email}' `),

    singleByID: id => db.load(`select * from parents where id = '${id}'  `),

    singleProfileByID: id => db.load(`select *,DATE_FORMAT(birthday, "%Y-%m-%d") as birthday from user_profile where id = '${id}' `),

    updateProfile: (entity, id) => { return db.update(entity, id, 'user_profile'); },

    addProfile: entity => db.add(entity, 'user_profile')
};