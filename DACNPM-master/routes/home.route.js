const express = require('express');
const userModel = require('../models/user.model');
const adminModel = require('../models/admin.model');
const router = express.Router();

//get all users
router.get('/', async(req, res) => {
    const ret1 = await userModel.all();
    const ret2 = await adminModel.all();
    console.log(ret);
    res.render('home', {
        users: ret1,
        admins: ret2
    });
})

module.exports = router;