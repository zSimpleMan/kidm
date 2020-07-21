const express = require('express');
const userModel = require('../models/user.model');
const userprofileModel = require('../models/userprofile.model');
const paymentModel = require('../models/payment.model');
const usercodeModel = require('../models/user_code.model');
const moment = require('moment');
const router = express.Router();

//get all users
router.get('/', async(req, res) => {
    const user = await userprofileModel.all();
    const info = await paymentModel.all();
    const code = await usercodeModel.all();

    res.render('home', {
        parents: user,
        payments: info,
        usercode: code
    });
})

module.exports = router;