package CaseStudy.OrderInventory.exception;

public class CustomException extends RuntimeException {
	
	private String code;
    private String message;

    // Constructor
    public CustomException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}