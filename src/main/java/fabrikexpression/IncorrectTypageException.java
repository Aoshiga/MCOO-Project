package fabrikexpression;

public class IncorrectTypageException extends Exception {
    private String type;
    private String expected;

    public IncorrectTypageException(String  type , String expected) {
        this.type = type;
        this.expected =expected;
    }
    
    public String toString(){
        return this.type + " Dosen't match expected type : " + this.expected;
    }

}
