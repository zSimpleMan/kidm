const express = require('express');
const adminModel = require('../models/admin.model');

const router = express.Router();

// router.post('/add-admin', async(req, res) => {
//     const result = await adminModel.add(req.body);
//     res.status(201).json(result);
// })


module.exports = router;