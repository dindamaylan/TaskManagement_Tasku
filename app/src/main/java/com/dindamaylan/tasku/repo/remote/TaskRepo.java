package com.dindamaylan.tasku.repo.remote;

import android.util.Log;

import com.dindamaylan.tasku.data.TaskData;
import com.dindamaylan.tasku.utils.Collection;
import com.dindamaylan.tasku.utils.Helpers;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TaskRepo extends UserRepo {
    private final FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    public void addNewTask(TaskData task, firebaseCallback callback) {
        mDatabase.collection(Collection.TASK)
                .document()
                .set(task)
                .addOnSuccessListener(unused -> {
                    callback.isSuccess(true, "berhasil membuat task baru");
                })
                .addOnFailureListener(e -> {
                    callback.isSuccess(false, "gagal membuat task baru");
                    Log.d("Tag", "addNewTask : failed = " + e.getMessage());
                });
    }

    public  void getTaskByUserId(String userId, onGetMenuTaskListener listener){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .whereEqualTo("userId", userId)
                .addSnapshotListener(((value, error) -> {
                    if (error != null)
                        return;
                    if (value != null && !value.isEmpty()){
                        ArrayList<TaskData> listOfTask = new ArrayList<>();
                        for (DocumentSnapshot doc:value.getDocuments()) {
                            listOfTask.add(doc.toObject(TaskData.class));
                        }
                        listener.isSuccess(true, listOfTask);
                    }
                    else{
                        Log.d("TAG", "getTaskByUserId: error = "+error.getMessage());
                        listener.isSuccess(false, new ArrayList<>());
                    }

                }));
    }

    public void getDeadlineTask(String userId, onGetMenuTaskListener listener){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .whereEqualTo("userId", userId)
                .orderBy("deadline", Query.Direction.ASCENDING)
                .whereLessThan("deadline", new Helpers().getAWeenToGo())
                .addSnapshotListener(((value, error) -> {
                    if (error != null)
                        return;
                    if (value != null && !value.isEmpty()){
                        ArrayList<TaskData> listOfTask = new ArrayList<>();
                        for (DocumentSnapshot doc:value.getDocuments()) {
                            listOfTask.add(doc.toObject(TaskData.class));
                        }
                        listener.isSuccess(true, listOfTask);
                    }
                    else{
                        Log.d("TAG", "getTaskByUserId: error = "+error.getMessage());
                        listener.isSuccess(false, new ArrayList<>());
                    }

                }));
    }

    public void getTaskByStatusAndUserId(String userId, String status, onGetMenuTaskListener listener){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .whereEqualTo("userId", userId)
                .whereEqualTo("status", status)
                .orderBy("deadline", Query.Direction.ASCENDING)
                .addSnapshotListener(((value, error) -> {
                    if (error != null) {
                        Log.d("TAG", "getTaskByUserId: error = " + error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()){
                        ArrayList<TaskData> listOfTask = new ArrayList<>();
                        for (DocumentSnapshot doc:value.getDocuments()) {
                            listOfTask.add(doc.toObject(TaskData.class));
                        }
                        listener.isSuccess(true, listOfTask);
                    }
                    else{
                        listener.isSuccess(false, new ArrayList<>());
                    }
                }));
    }

    public void deleteTask(String taskId, firebaseCallback callback){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .document(taskId)
                .delete()
                .addOnSuccessListener(unused -> {
                    callback.isSuccess(true,"berhasil menghapus task");
                })
                .addOnFailureListener(e -> {
                    callback.isSuccess(false,"gagal menghapus task");
                    Log.d("TAG", "deleteTask, failed: "+e.getMessage());
                });
    }

    public void updateStatusTask (String taskId, String newStatus, firebaseCallback callback){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .document(taskId)
                .update("status", newStatus)
                .addOnSuccessListener(unused -> {
                    callback.isSuccess(true,"berhasil mengupdate status task");
                })
                .addOnFailureListener(e -> {
                    callback.isSuccess(false,"gagal mengupdate task");
                    Log.d("TAG", "updateTask, failed: "+e.getMessage());
                });
    }

    public void updateTask (TaskData task, firebaseCallback callback){
        FirebaseFirestore.getInstance().collection(Collection.TASK)
                .document(task.id)
                .update(
                        "title", task.title,
                        "desc", task.desc,
                        "status", task.status,
                        "deadline", task.deadline
                )
                .addOnSuccessListener(unused -> {
                    callback.isSuccess(true,"task berhasil diubah");
                })
                .addOnFailureListener(e -> {
                    callback.isSuccess(false,"task gagal diubah");
                    Log.d("TAG", "updateTask, failed: "+e.getMessage());
                });
    }

}
