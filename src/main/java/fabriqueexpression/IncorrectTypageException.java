package fabriqueexpression;


import java.util.ArrayList;

public class IncorrectTypageException extends Exception {
    private String type;
    private String excpected;
    public IncorrectTypageException(String  type , String expected) {
        this.type = type;
        this.excpected =expected;

    }

    public string toString(){
        return this.type + " Dosen't match expected type : " + this.excpected;
    }

}
