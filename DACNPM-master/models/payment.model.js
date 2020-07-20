const db = require('../utils/db');

module.exports = {
    all: () => db.load(`SELECT * FROM payment_info`),

    add: (entity) => db.add(entity, 'payment_info')
};