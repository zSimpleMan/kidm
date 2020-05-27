const express = require('express');
const userModel = require('../models/user.model');
const adminModel = require('../models/admin.model');
const router = express.Router();

//get all users
router.get('/', async(req, res) => {
    const ret1 = await userModel.all();
    console.log(ret1);
    res.render('home', {
        users: ret1
    });
})

module.exports = router;