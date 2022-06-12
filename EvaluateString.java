public class EvaluateString{
        private static final char PLUS='+',MINUS='-',DIVIDE='/',MULTIPLY='x',LEFTBRACKET='(',RIGHTBRACKET=')';
        private static String process_equation="",temp="",answer="",left_of_equation="",right_of_equation="",temporary_equation="";
        public static String steps="";
        public static boolean number_too_large=false;
        private static int number_max_size=15,m=0;
        private static int[] bracket_position=new int[2];
  
    public static String evaluate_string(String input_equation){
        steps=input_equation;
        System.out.println(input_equation);
        while(!temporary_equation.equals(input_equation)){
            temporary_equation=input_equation;
            bracket_position=look_for_brackets(input_equation);
            if(bracket_position[0]==0 && bracket_position[1]==0){
                process_equation=input_equation; // no brackets
            }
            else{
                process_equation=input_equation.substring(bracket_position[0]+1,bracket_position[1]);
                left_of_equation=input_equation.substring(0,bracket_position[0]);
                right_of_equation=input_equation.substring(bracket_position[1], input_equation.length()-1);
            }
            process_equation=remove_redundant_plus_and_minus_sign(process_equation);
            while(temp!=process_equation){
                temp=process_equation;
                m=0;
                while(m<process_equation.length()){
                    if(DIVIDE==process_equation.charAt(m)){
                        divide_or_multiply(m,true);
                        break;
                    }
                    if(MULTIPLY==process_equation.charAt(m)){
                        divide_or_multiply(m,false);
                        break;
                    }
                    m++;
                }        
            }
            process_equation=remove_redundant_plus_and_minus_sign(process_equation);
            add_or_subtract(process_equation);
            input_equation=left_of_equation+answer+right_of_equation;
            left_of_equation="";right_of_equation="";
            if(answer.equals(input_equation)){
                break;
            }
        }    
        //System.out.println(steps);
        return input_equation;
    }

    private static int[] look_for_brackets(String s){
        int i=0;
        int[] position_of_brackets=new int[2];
        while(true){
            if(i<s.length()){
                if(LEFTBRACKET==s.charAt(i)){
                    position_of_brackets[0]=i;
                }
                else if(RIGHTBRACKET==s.charAt(i)){
                    position_of_brackets[1]=i;
                    break;
                }

            }
            else{
                position_of_brackets[0]=0;
                position_of_brackets[1]=0;
                break;
            }
            i++;
        }
        return position_of_brackets;
    }

    private static String remove_redundant_plus_and_minus_sign(String s){
        int i=0; String tempString="";
        int k=0; boolean l=false;
        while(i<s.length()){
            if(s.charAt(i)!=PLUS && s.charAt(i)!=MINUS){
                tempString=tempString+s.charAt(i);
                i++;
            }
            else{
                while(s.charAt(i)==PLUS || s.charAt(i)==MINUS){
                    if(s.charAt(i)==MINUS){
                        k++;
                    }
                    i++;
                    l=true;
                }
                if(k%2!=0 && l){
                    tempString=tempString+"-";
                    k=0; l=false;
                }
                else if(k%2==0 && l){
                    tempString=tempString+"+";
                    k=0; l=false;
                }
            }
        }
        return tempString;
    }

    private static void divide_or_multiply(int i,boolean operator){
        String number1="",number2="",left_of_result="",right_of_result=""; 
        double result;
        int j; 

        j=i-1;
        while(true){
            j=j-1;
            if(j==-1){
                break;
            }
            if(process_equation.charAt(j)==MINUS || process_equation.charAt(j)==PLUS || process_equation.charAt(j)==MULTIPLY || process_equation.charAt(j)==DIVIDE){    
                left_of_result=process_equation.substring(0,j+1);
                break;
            }
        }
        number1=process_equation.substring(j+1, i);

        j=i+1;
        while(true){
            j=j+1;
            if(j==process_equation.length()){ 
                break;
            }
            if(process_equation.charAt(j)==MINUS || process_equation.charAt(j)==PLUS || process_equation.charAt(j)==MULTIPLY || process_equation.charAt(j)==DIVIDE){
                right_of_result=process_equation.substring(j, process_equation.length());
                break;
            }
        }
        number2=process_equation.substring(i+1, j);
        
        if(length_of_the_number(number1)>number_max_size || length_of_the_number(number2)>number_max_size){
            number_too_large=true;
        }
        else{
            if(operator){
                result=Double.parseDouble(number1)/Double.parseDouble(number2);
                process_equation=left_of_result+String.format("%.5f",result)+right_of_result;
            }
            if(!operator){
                result=Double.parseDouble(number1)*Double.parseDouble(number2);
                process_equation=left_of_result+String.format("%.5f",result)+right_of_result;
            }
        }

    }


    private static void add_or_subtract(String s){
        int i=s.length()-1; String str=""; double num=0;
        while(i!=-1){
            str=s.charAt(i)+str;
            if(MINUS==s.charAt(i) || PLUS==s.charAt(i) || i==0){
                if(length_of_the_number(str)>number_max_size){
                    number_too_large=true;
                }
                else{
                    num=Double.parseDouble(str)+num;
                    str="";
                }
            }
            i=i-1;
        }
        answer=String.format("%.5f",num);
        int f=answer.length()-1; String temp="";
        while(answer.charAt(f)=='0'){
            f=f-1;
        }
        if(answer.charAt(f)=='.'){
            f=f-1;
        }
        while(f>-1){
            temp=answer.charAt(f)+temp;
            f=f-1;
        }
        answer=temp;
    }



    private static int length_of_the_number(String x){
        int y=x.length(); int j=0; int val=0;
        for(int i=0;i<y;i++){
            if(x.charAt(i)=='.'){
                j=i;
            }
        }

        if(j==0){
            if(x.charAt(0)=='+'||x.charAt(0)=='-'){
                val= y-1;
            }
            else{
                val= y;
            }

        }
        else{
            if(x.charAt(0)=='+'||x.charAt(0)=='-'){
                val= j-1;
            }
            else{
                val= j;
            }

        }
        return val;
    }

    
}