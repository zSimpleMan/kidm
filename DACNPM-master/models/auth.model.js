const bcrypt = require('bcryptjs');
const userModel = require('./user.model');
const db = require('../utils/db');

module.exports = {
    login: async entity => {
        // entity = {
        //   "username": "admin",
        //   "password": "admin"
        // }

        const rows = await userModel.singleByEmail(entity.email);
        if (rows.length === 0)
            return null;

        const hashPwd = rows[0].password;
        if (bcrypt.compareSync(entity.password, hashPwd)) {
            return rows[0];
        }

        return null;
    }
};