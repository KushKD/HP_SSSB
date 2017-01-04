package Enum;

/**
 * Created by kuush on 1/4/2017.
 */

public enum TaskType {

    REGISTRATION(1),
    GET_PDF(2),
    GET_VACANCIES(3);


    int value;

    private TaskType(int value) {
        this.value = value;
    }
}
