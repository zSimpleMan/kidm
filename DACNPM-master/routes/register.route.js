const express = require('express');
const userprofileModel = require('../models/userprofile.model');
const paymentinfoModel = require('../models/payment.model');
const usercodeModel = require('../models/user_code.model');
const router = express.Router();

var userid;
//register
router.get('/info', (req, res) => {
    res.render('register_info', {
        title: 'Thông tin khách hàng'
    });
})

router.post('/info', async(req, res) => {
    // req.body = {
    //      fullname
    //      address
    //      birthday
    //      phone
    // }

    const result = await userprofileModel.addUser(req.body);
    console.log(`userid: ${result.insertId}`);
    userid = result.insertId;
    return res.redirect('/register/payment');
})

//payment
router.get('/payment', (req, res) => {
    res.render('register_payment', {
        title: 'Thông tin thanh toán'
    });
})

router.post('/payment', async(req, res) => {
    // req.body = {
    //      userid
    //      number
    //      validity_date
    //      name
    // }

    const entity = {
        userid,
        number: req.body.number,
        validity_date: req.body.validity_date,
        name: req.body.name
    };

    const result = await paymentinfoModel.add(entity);
    console.log(result);
    return res.redirect('/register/code');

})

//generate mã để đăng nhập vào app trên mobile
router.get('/code', async(req, res) => {
    var code = '';
    for (let index = 0; index < 10; index++) {
        code += Math.floor(Math.random() * 10);
    }
    const entity = {
        userid,
        code
    }
    const result = await usercodeModel.add(entity);
    return res.render('codeActiveApp', {
        code: code
    })
})

module.exports = router;