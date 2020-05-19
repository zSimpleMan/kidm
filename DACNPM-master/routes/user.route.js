const express = require('express');
const userModel = require('../models/user.model');

const router = express.Router();

//sign up
router.post('/', async(req, res) => {

    const check = await userModel.singleByEmail(req.body.email);
    if (check.length > 0) {
        return res.json({
            message: 'this email already exists',
        })
    }

    const result = await userModel.add(req.body);

    const ret = {
        message:"successful",
        email: req.body.email
    }
    res.status(201).json(ret);
});

router.post('/update-profile/:id', async(req, res) => {

    const userid = req.params.id;
    const check = await userModel.singleProfileByID(userid);
    if (check.length > 0) {
        await userModel.updateProfile(req.body, userid)
       return res.json({result: "successful"});
    }
    req.body.id = userid;
    const result = await userModel.addProfile(req.body);

    res.json(result);
});

module.exports = router;