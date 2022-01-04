package com.dindamaylan.tasku.data;

public class MenuTaskData {
    public String titleTask;
    public Integer totalTask, ic, bgColor;

    public MenuTaskData() {
    }

    public MenuTaskData(String titleTask, Integer totalTask, Integer ic, Integer bgColor) {
        this.titleTask = titleTask;
        this.totalTask = totalTask;
        this.ic = ic;
        this.bgColor = bgColor;
    }
}
