//GET Method
const express = require("express");
const fs = require("fs");
const path = require("path");

const router = express.Router();

const statsFilePath = path.join(__dirname, "./data.json");

//GET ALL
const getAllData = async (req, res, next) => {
    try {
        const data = fs.readFileSync(path.join(__dirname, './data.json'));
        const stats = JSON.parse(data);
        res.json(stats);
        } 
        catch (err) {
            res.status(500).json({
                error: "Error Getting Data"
            });
        }
    
    };
            
                    
router.route("/api/v1/laundry").get(getAllData);


//GET BY ID
const getDatabyId = async (req, res, next) => {
    try {
        const data = fs.readFileSync(path.join(__dirname, './data.json'));
        const stats = JSON.parse(data);
        const laundryStats = stats.find(laundry => laundry.id === Number(req.params.id));
        if (!laundryStats) {
            const err = new Error('Laundry not found');
            err.status = 404;
            throw err;
        }
        res.json(laundryStats);
    } catch (e) {
        next(e);
    }
};

router.route("/api/v1/laundry/:id").get(getDatabyId);

//POST Method
const createData = async (req, res, next) => {
    try {
        const data = fs.readFileSync(statsFilePath);
        const stats = JSON.parse(data);
        const newData= {
            id: req.body.id,
            name: req.body.name,
            location: req.body.location,
            phone: req.body.phone,
        };
        stats.push(newData);
        fs.writeFileSync(statsFilePath, JSON.stringify(stats));
        res.status(201).json(newData);
    } catch (e) {
        next(e);
    }
};

router.route("/api/v1/laundry").post(createData);

//PUT Method
const updateData = async (req, res, next) => {
    try {
        const data = fs.readFileSync(statsFilePath);
        const stats = JSON.parse(data);
        const laundryStats = stats.find(
            (laundry) => laundry.id === Number(req.params.id)
        );
        if (!laundryStats) {
            const err = new Error("Laundry not found");
            err.status = 404;
            throw err;
        }
        const newLaundryData = {
            id: req.body.id,
            name: req.body.name,
            location: req.body.location,
            phone: req.body.phone,
        };
        const newStats = stats.map((laundry) => {
            if (laundry.id === Number(req.params.id)) {
                return newLaundryData;
            } else {
                return laundry;
            }
        });
        fs.writeFileSync(statsFilePath, JSON.stringify(newStats));
        res.status(200).json(newLaundryData);
    } catch (e) {
        next(e);
    }
};

router.route("/api/v1/laundry/:id").get(getDatabyId).put(updateData);

//DELETE Method
const deleteData = async (req, res, next) => {
    try {
        const data = fs.readFileSync(statsFilePath);
        const stats = JSON.parse(data);
        const statsIndex = stats.findIndex(
            (laundry) => laundry.id === Number(req.params.id)
            );
            if (statsIndex === -1) {
                const err = new Error("Laundry not found");
                err.status = 404;
                throw err;
            }
            stats.splice(statsIndex, 1);
            fs.writeFileSync(statsFilePath, JSON.stringify(stats));
            res.status(200).json(stats);
        } catch (e) {
            next(e);
        }
};
                 

router.route("/api/v1/laundry/:id").delete(deleteData);


module.exports = router;

router
    .route("/api/v1/laundry")
    .get(getAllData)
    .get(getDatabyId)
    .post(createData)
    .put(updateData)
    .delete(deleteData);

