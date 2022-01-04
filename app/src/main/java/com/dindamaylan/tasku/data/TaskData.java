package com.dindamaylan.tasku.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class TaskData implements Parcelable {
    public String status;
    public Timestamp deadline;
    public String title, desc;
    public String userId;
    @DocumentId
    public String id;

    public TaskData() {
    }

    public TaskData(StatusTask status, Timestamp deadline, String title, String desc) {
        this.status = status.toString();
        this.deadline = deadline;
        this.title = title;
        this.desc = desc;
    }

    public TaskData(StatusTask status, Timestamp deadline, String title, String desc, String userId, String id) {
        this.status = status.toString();
        this.deadline = deadline;
        this.title = title;
        this.desc = desc;
        this.userId = userId;
        this.id = id;
    }

    public TaskData(StatusTask status, Timestamp deadline, String title, String desc, String userId) {
        this.status = status.toString();
        this.deadline = deadline;
        this.title = title;
        this.desc = desc;
        this.userId = userId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeParcelable(this.deadline, flags);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.userId);
        dest.writeString(this.id);
    }

    public void readFromParcel(Parcel source) {
        this.status = source.readString();
        this.deadline = source.readParcelable(Timestamp.class.getClassLoader());
        this.title = source.readString();
        this.desc = source.readString();
        this.userId = source.readString();
        this.id = source.readString();
    }

    protected TaskData(Parcel in) {
        this.status = in.readString();
        this.deadline = in.readParcelable(Timestamp.class.getClassLoader());
        this.title = in.readString();
        this.desc = in.readString();
        this.userId = in.readString();
        this.id = in.readString();
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel source) {
            return new TaskData(source);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };



}
