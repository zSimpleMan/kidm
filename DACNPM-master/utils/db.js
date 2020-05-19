const mysql = require('mysql');
const { promisify } = require('util');

const pool = mysql.createPool({
    connectionLimit: 100,
    host: 'localhost',
    port: 3306,
    user: 'root',
    password: '',
    database: 'kidmanagerment'
});

const pool_query = promisify(pool.query).bind(pool);

module.exports = {
    load: sql => pool_query(sql),
    add: (entity, tableName) => pool_query(`insert into ${tableName} set ?`, entity),
    update: (entity, id, tableName) => pool_query(`update ${tableName} set ? where id = ${id}`, entity)
};