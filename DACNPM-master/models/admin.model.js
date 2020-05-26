const db = require('../utils/db');
const bcrypt = require('bcryptjs');

module.exports = {
    all: () => db.load(`SELECT * FROM admins`),
    add: (entity) => {
        // entity = {
        //     "username":""
        //      "name":""
        //     "password": "pvhau",
        //     "email": "pvhaun@gmail.com"
        //      "phone":"015655648564"
        // }
        const hash = bcrypt.hashSync(entity.password, 8);
        entity.password = hash;
        return db.add(entity, 'admins');
    }
};