package com.dindamaylan.tasku.repo.remote;

import android.app.Activity;
import android.util.Log;

import com.dindamaylan.tasku.data.UserData;
import com.dindamaylan.tasku.repo.local.LocalStore;
import com.dindamaylan.tasku.utils.Collection;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserRepo {
    private final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    public void createNewUser(UserData user, firebaseCallback callback) {
        mDatabase.collection(Collection.DATA_USER).document(user.id).set(user)
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) callback.isSuccess(true, "Berhasil call back");
                    Log.d("Tag", "createNewUser : complete = " + task.toString());
                })
                .addOnFailureListener(e -> {
                    callback.isSuccess(false, e.getMessage());
                    Log.d("Tag", "createNewUser : failed = " + e.getMessage());
                })
        ;
    }

    public String generateId() {
        return mDatabase.collection(Collection.DATA_USER).document().getId();
    }

    public void getUserId(String userId, onGetUserListener listener){
        FirebaseFirestore.getInstance().collection(Collection.DATA_USER)
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserData user = documentSnapshot.toObject(UserData.class);
                    if (user != null) listener.onUserGet(true, user);
                    else listener.onUserGet(false, new UserData());
                })
                .addOnFailureListener(e -> {
                    Log.d("TAG", "getUserId: ");
                    listener.onUserGet(false, new UserData());
                });
    }

    public interface onGetUserListener{
        void onUserGet(Boolean isSuccess, UserData user);
    }

    public void loginUser(Activity context, String username, String password, firebaseCallback callback) {
        mDatabase.collection(Collection.DATA_USER)
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                    if (!snapshots.isEmpty()) {
                        DocumentSnapshot firstSnapshot = snapshots.get(0);
                        UserData userData = firstSnapshot.toObject(UserData.class);
                        if (userData != null) {
                            if (userData.password.equals(password)){
                                LocalStore.getInstance(context).storeUserId(userData.id, ((isSuccess, user_id) -> {
                                    Log.d("TAG", "loginUser: ok");
                                    callback.isSuccess(true, userData.name);
                                }) );
                            }else{
                                callback.isSuccess(false, "Password Salah");
                            }
                        }else {
                            callback.isSuccess(false, "Pengguna tidak ditemukan");
                        }
                    } else {
                        callback.isSuccess(false, "Pengguna tidak ditemukan");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Tag", "Login User : failed = " + e.getMessage());
                    callback.isSuccess(false, "Failed " + e.getMessage());
                });
    }

    private void getUserbyUserName(String username, onGetUserListener listener) {
        FirebaseFirestore.getInstance().collection(Collection.DATA_USER)
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.getDocuments().isEmpty()) {
                        UserData userData = queryDocumentSnapshots.getDocuments().get(0).toObject(UserData.class);
                        if (userData != null) {
                            listener.onUserGet(true, userData);
                        } else listener.onUserGet(false, new UserData());
                    } else listener.onUserGet(false, new UserData());
                })
                .addOnFailureListener(e -> {
                    Log.d("TAG", "getUserbyUserName: failed" + e.getMessage());
                    listener.onUserGet(false, new UserData());
                });
    }

    public void updatePassword(String username, String passwordNew, firebaseCallback callback) {
        getUserbyUserName(username, (isSuccess, userData) -> {
            if (isSuccess) {
                FirebaseFirestore.getInstance().collection(Collection.DATA_USER)
                        .document(userData.id)
                        .update("password", new Helpers().getHashPassword(passwordNew))
                        .addOnSuccessListener(unused -> {
                            callback.isSuccess(true, "Sukses mengubah password");
                        })
                        .addOnFailureListener(e -> {
                            Log.d("TAG", "updatePassword + gagal : " + e.getMessage());
                            callback.isSuccess(false, "Gagal mengubah password");
                        });
            }
        });

    }

    public void logout(Activity context, firebaseCallback callback) {
        LocalStore.getInstance(context).storeUserId("", (isSuccess, user_id) -> {
            callback.isSuccess(true, "berhasil keluar");
            callback.isSuccess(false, "gagal keluar");
        });
    }
}
