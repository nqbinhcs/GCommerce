package com.example.e_commerce.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDB {
    private static FirebaseDB instance = null;

    private FirebaseFirestore firestore;

    private FirebaseDB() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseDB getInstance() {
        if (instance != null)
            return instance;
        synchronized (FirebaseDB.class) {
            if (instance == null) {
                instance = new FirebaseDB();
            }
            return instance;
        }
    }



}
