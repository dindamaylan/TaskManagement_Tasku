package com.dindamaylan.tasku.utils;

import com.dindamaylan.tasku.R;
import com.dindamaylan.tasku.data.MenuTaskData;
import com.dindamaylan.tasku.data.StatusTask;
import com.dindamaylan.tasku.data.TaskData;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;

public class DataHelpers {
    public ArrayList<MenuTaskData> getMenuTask(Integer totalTodo, Integer totalDoing, Integer totalDone, Integer totalMissing){
        return new ArrayList<>(Arrays.asList(
                new MenuTaskData("Todo", totalTodo, R.drawable.ic_todo, R.color.todo),
                new MenuTaskData("Doing", totalDoing, R.drawable.ic_doing, R.color.doing),
                new MenuTaskData("Done", totalDone, R.drawable.ic_done, R.color.done),
                new MenuTaskData("Missing", totalMissing, R.drawable.ic_missing, R.color.missing)
        ));
    }

    public ArrayList<TaskData> dummyTaskData = new ArrayList<>(Arrays.asList(
            new TaskData(StatusTask.todo, Timestamp.now(), "Model dan Metode Rekayasa Perangkat Lunak", "Membuat Activity Diagram Petani Teh di Desa Sana" ),
            new TaskData(StatusTask.doing,  Timestamp.now(), "Pemrograman 2", "Membuat GUI Pelanggan dan Connect ke Database" ),
            new TaskData(StatusTask.done,  Timestamp.now(), "Pemrograman 2", "TUBES" ),
            new TaskData(StatusTask.missing, Timestamp.now(), "Pemrograman 2", "Membuat Program Sederhana tanpa GUI" )
    ));
}
