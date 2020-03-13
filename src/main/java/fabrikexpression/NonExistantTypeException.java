package fabrikexpression;


public class NonExistantTypeException extends Exception {
    private String type;
    public NonExistantTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Class '" + type + "' does not exist";
    }
}
