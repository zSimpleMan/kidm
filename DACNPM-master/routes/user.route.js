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

router.post("/kidlocation/:id", async(req, res) =>{
    const check = await userModel.singleByID(req.params.id);
    if (check.length < 1){
        return res.json({ result: "failed" });
    }
    if(check[0].is_parent == 1){
        return res.json({ result: "failed" });
    }

    const kidlocation = await userModel.singleLocationByID(req.params.id);
    if (kidlocation.length < 1){
        req.body.id = req.params.id;
        await userModel.addLocation(req.body);
        const rows = await userModel.singleLocationByID(req.params.id);
        return res.json({
            result: 'successful',
            id: rows[0].id,
            latitude: rows[0].latitude,
            longitude: rows[0].longitude
        });
    }
    await userModel.updateLocation(req.body, req.params.id);
    const rows = await userModel.singleLocationByID(req.params.id);
    return res.json({
        result: 'successful',
        id: rows[0].id,
        latitude: rows[0].latitude,
        longitude: rows[0].longitude
    });

});

router.get("/get-mykid/:id", async(req, res) => {
    const check = await userModel.singleByID(req.params.id);
    if (check.length < 1){
        return res.json({ result: "failed" });
    }
    const kid = await userModel.getKidByID(req.params.id);
    if(kid.length<1){
        return res.json({ result: "failed" });
    }
    res.json({
        result: 'successful',
        id_kid: kid[0].id_kid,
        id_parent: kid[0].id_parent
    });
});

module.exports = router;