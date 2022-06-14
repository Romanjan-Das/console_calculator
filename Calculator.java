import java.util.Scanner;;

public class Calculator {
    private static String input = "";
    private static int i = 0;
    private static boolean wrong_input=false;

    public static void main(String[] args) {

        try (Scanner s = new Scanner(System.in)) {
            input = s.nextLine();
        }
        if(input.charAt(input.length()-1)=='='){
            calculation();
        }
        else{
            System.out.println("Equal sign is required !");
        }
    }

    private static void calculation(){
        while (i < input.length()) {
            StringFormation.verify_input(input.charAt(i));
            if (!StringFormation.allow) {
                wrong_input=true;
                break;
            }
            i++;
        }
        if(wrong_input){
            System.out.println("Check the input equation !");
        }
        else{
            System.out.println("answer: " + StringFormation.input_string);
        }
    }
}
