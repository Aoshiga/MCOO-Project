package fabrikexpression;


public class NonExistentTypeException extends Exception {
    private String type;
    NonExistentTypeException(String type) {
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
