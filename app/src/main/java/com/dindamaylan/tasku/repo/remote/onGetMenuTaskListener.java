package com.dindamaylan.tasku.repo.remote;

import com.dindamaylan.tasku.data.TaskData;

import java.util.ArrayList;

public interface onGetMenuTaskListener {
    void isSuccess(Boolean isSuccess, ArrayList<TaskData> listOfTask);
}
