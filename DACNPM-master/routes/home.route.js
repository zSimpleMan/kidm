const express = require('express');
const userModel = require('../models/user.model');
const userprofileModel = require('../models/userprofile.model');
const moment = require('moment');
const router = express.Router();

//get all users
router.get('/', async(req, res) => {
    const user = await userprofileModel.all();

    for (let index = 0; index < user.length; index++) {
        user[index].birthday = moment(user[index].birthday).format('DD-MM-YYYY');
    }

    res.render('home', {
        parents: user
    });
})

module.exports = router;