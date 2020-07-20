const mysql = require('mysql');
const { promisify } = require('util');

const pool = mysql.createPool({
    connectionLimit: 100,
    host: 'us-cdbr-east-05.cleardb.net',
    port: 3306,
    user: 'b1bd0e2253fb4f',
    password: 'a2f6d7b2',
    database: 'heroku_096d62b2c9d603b'
});

const pool_query = promisify(pool.query).bind(pool);

module.exports = {
    load: sql => pool_query(sql),
    add: (entity, tableName) => pool_query(`insert into ${tableName} set ?`, entity),
    update: (entity, id, tableName) => pool_query(`update ${tableName} set ? where id = ${id}`, entity),
    updateL: (entity, code, tableName) => pool_query(`update ${tableName} set ? where CODE = ${code}`, entity)
};