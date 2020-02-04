package fabrikexpression;


import java.util.ArrayList;

public class NonExistantTypeException extends Exception {
    private String type;
    public NonExistantTypeException(String  type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
