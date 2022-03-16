package xalbrech.exercises.starwars.controller;

/**
 * Error message mapping class.
 */
public class SearchControllerError {

    private String errorMessage;

    public SearchControllerError(String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
