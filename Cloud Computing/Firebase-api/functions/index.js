const functions = require("firebase-functions");
const express = require("express");
const admin = require("firebase-admin");


const app = express();

// initialize permissions to the firestore
admin.initializeApp({
    credential: admin.credential.cert("./credentials.json"),
    databaseURL: "https://drone-capstone-386903-default-rtdb.firebaseio.com",
});

const db = admin.firestore();

//test 
app.get("/", (req, res) => {
    return res.status(200).json({ "message": "Welcome to Drone-Capstone" });
});

// get laundry by ID
app.get("/api/laundry/:laundry_id", (req, res) => {
    (async() => {
        try {
            const doc = db.collection("laundry").doc(req.params.laundry_id);
            const item = await doc.get();
            const response = item.data();

            return res.status(200).json(response);
        } catch (error) {
            return res.status(500).send(error);
        }
    })();
});

// get all laundry data
app.get("/api/laundry", async(req, res) => {
    try {
        const query = db.collection("laundry");
        const querySnapshot = await query.get();
        const docs = querySnapshot.docs;

        const response = docs.map((doc) => ({
            id: doc.id,
            address: doc.data().address,
            price: doc.data().price,
            latitude: doc.data().latitude,
            rating: doc.data().rating,
            opening_hours: doc.data().opening_hours,
            icon: doc.data().icon,
            phone_number: doc.data().phone_number,
            laundry_name: doc.data().laundry_name,
            email: doc.data().email,
            longitude: doc.data().longitude,
        }));
        return res.status(200).json(response);
    } catch (error) {
        return res.status(500).json();
    }
});

exports.apps = functions
    .region("asia-southeast2")
    .https.onRequest(app);
