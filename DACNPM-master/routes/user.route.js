const express = require("express");
const userModel = require("../models/user.model");

const router = express.Router();

//sign up
router.post("/", async(req, res) => {
    const check = await userModel.singleByEmail(req.body.email);
    if (check.length > 0) {
        return res.json({
            message: "this email already exists",
        });
    }
    const result = await userModel.add(req.body);

    const ret = {
        message: "successful",
        email: req.body.email,
    };
    res.status(201).json(ret);
});

router.post("/update-profile/:id", async(req, res) => {
    const userid = req.params.id;
    const check = await userModel.singleProfileByID(userid);
    if (check.length > 0) {
        await userModel.updateProfile(req.body, userid);
        const rows = await userModel.singleProfileByID(userid);
        return res.json({
            result: "successful",
            name: rows[0].fullname,
            address: rows[0].address,
            birthday: rows[0].birthday,
            phone: rows[0].phone,
        });
    }
    req.body.id = userid;
    await userModel.addProfile(req.body);
    const rows = await userModel.singleProfileByID(userid);
    res.json({
        result: "successful",
        name: rows[0].fullname,
        address: rows[0].address,
        birthday: rows[0].birthday,
        phone: rows[0].phone,
    });
});

router.get("/profile/:id", async(req, res) => {
    const check = await userModel.singleProfileByID(req.params.id);
    if (check.length > 0) {
        return res.json({
            result: "successful",
            name: check[0].fullname,
            address: check[0].address,
            birthday: check[0].birthday,
            phone: check[0].phone
        });
    }
    res.json({ result: "failed" });

});

module.exports = router;