import java.util.Scanner;;

public class Calculator {
    private static String input = "";
    private static int i = 0;
    private static boolean wrong_input=false;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        input = s.nextLine();
        input=input+"=";
        while (i < input.length()) {
            StringFormation.verify_input(input.charAt(i));
            if (!StringFormation.allow) {
                wrong_input=true;
                break;
            }
            i++;
        }
        if(wrong_input){
            System.out.println("wrong input");
        }
        else{
            System.out.println("answer: " + StringFormation.input_string);
        }
    }
}
