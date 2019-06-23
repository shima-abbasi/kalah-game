package shima.backbase.kalah.dto;

public class KalahApiDTO {
    private Boolean hasError;
    private String message;
    private Object data;

    public KalahApiDTO(Boolean hasError, String message, Object data) {
        this.hasError = hasError;
        this.message = message;
        this.data = data;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

