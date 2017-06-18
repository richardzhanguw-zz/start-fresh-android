package com.example.richard.startfresh.AdaptersAndOtherClasses;


/**
 * Created by Richard on 2016-02-27.
 */
public class ToDoItemToday {

    String timeOfTask;
    String taskName;
    String taskDetails;
    String taskExpanded;
    public ToDoItemToday (String taskName, String taskDetails, String timeOfTask, String taskExpanded) {
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.timeOfTask = timeOfTask;
        this.taskExpanded = taskExpanded;
    }
}
