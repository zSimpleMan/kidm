const express = require('express');
const authModel = require('../models/auth.model');
const userModel = require('../models/user.model');

const router = express.Router();

//sign in
router.post('/', async(req, res) => {

    // req.body = {
    //     "email": "pvhau",
    //     "password": "pvhau"
    // }

    const ret = await authModel.login(req.body);

    if (ret === null) {
        return res.json({
            login: "fail"
        });
    }

    const rows = await userModel.singleByEmail(req.body.email);

    res.json({
        login:"successful",
        email: rows[0].email,
        id: rows[0].id,
        is_parent: rows[0].is_parent
    });

})

module.exports = router;