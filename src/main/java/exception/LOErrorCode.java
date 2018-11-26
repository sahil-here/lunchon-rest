package exception;

public enum LOErrorCode {

    INTERNAL_SERVER_ERROR("Internal server error"),
    INVALID_USER_CREDENTIALS("Email or password is incorrect"),
    PASSWORD_ERROR("User password error"),
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_PRESENT("This user is already present"),
    EVENT_NOT_FOUND("Event not found"),
    ENCRYPTION_ERROR("Error occured during token generation"),
    INVALID_TOKEN("Invalid token"),
    TOKEN_MISMATCH("Auth token does not match the userId"),
    DUPLICATE_REQUEST("Duplicate Request");

    private final String name;

    private LOErrorCode(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
