const express = require('express');
const authModel = require('../models/auth.model');
const userModel = require('../models/user.model');
const usercodeModel = require('../models/user_code.model');

const router = express.Router();

//sign in by code
router.post('/', async(req, res) => {
    // req.body = {
    //     code: '2845254242'
    // }
    const result = await usercodeModel.singleByCode(req.body.code);
    console.log(result);
    console.log(req.body);

    if (result.length === 0) {
        return res.json({
            success: false,
            message: 'Sai mã đăng nhập'
        })
    }

    res.json({
        success: true,
        message: 'Đăng nhập thành công',
        code: req.body.code
    })
})


module.exports = router;