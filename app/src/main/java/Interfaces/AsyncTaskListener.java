package Interfaces;

import Enum.TaskType;

/**
 * Created by kuush on 1/4/2017.
 */

public interface AsyncTaskListener {

    public void onTaskCompleted(String result, TaskType taskType);
}
