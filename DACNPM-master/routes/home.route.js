const express = require('express');
const userModel = require('../models/user.model');
const router = express.Router();

//get all users
router.get('/', async(req, res) => {
    const ret1 = await userModel.all();
    console.log(ret1);
    res.render('home', {
        parents: ret1
    });
})

module.exports = router;