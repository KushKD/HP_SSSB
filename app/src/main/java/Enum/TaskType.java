package Enum;

/**
 * Created by kuush on 1/4/2017.
 */

public enum TaskType {

    REGISTRATION(1),
    GET_PDF(2),
    GET_VACANCIES(3),
    GET_FORMS_DASHBOARD(4),
    GET_POSTWISE_DASHBOARD(5);


    int value;

    private TaskType(int value) {
        this.value = value;
    }
}
