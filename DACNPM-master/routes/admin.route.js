const express = require('express');
const adminModel = require('../models/admin.model');

const router = express.Router();

router.post('/add-admin', (req, res) => {
    const result = await adminModel.add(req.body);

    const ret = {
        message: "successful",
        email: req.body.email,
    };
    res.status(201).json(ret);
})


module.exports = router;