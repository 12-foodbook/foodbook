package th.ac.kmitl.it.foodbook.utils;

public class Alert {
    
    public static enum AlertTypes {
        SUCCESS, INFO, WARNING, DANGER
    };
    
    private AlertTypes type;
    private String message;
    private boolean isShowed;
    
    public Alert(AlertTypes type, String message) {
        this.type = type;
        this.message = message;
        isShowed = false;
    }
    
    public String getType() {
        switch (type) {
        case SUCCESS:
            return "success";
        case INFO:
            return "info";
        case WARNING:
            return "warning";
        case DANGER:
            return "danger";
        default:
            return "default";
        }
    }
    
    public String getMessage() {
        isShowed = true;
        return message;
    }
    
    public boolean isShowed() {
        return isShowed;
    }
    
}
