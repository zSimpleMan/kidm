const express = require('express');
const kidAdminModel = require('../models/kidAdmin.model');


const router = express.Router();

router.get('/', (req, res) => {
    res.render('login');
})


router.post('/', async(req, res) => {
    const ret = await kidAdminModel.singleByEmail(req.body.email);

    if (ret !== 0) {
        if (ret[0].password === req.body.password) {

            return res.redirect('/admin/home');
        } else {
            return res.render('login', {
                failed: true
            });
        }
    }
    res.render('login', {
        failed: true
    })
})

module.exports = router;