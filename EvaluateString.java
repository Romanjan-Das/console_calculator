public class EvaluateString{
        private static final char PLUS='+';
        private static final char MINUS='-';
        private static final char DIVIDE='/';
        private static final char MULTIPLY='x';
        private static final char LEFTBRACKET='(';
        private static final char RIGHTBRACKET=')';

        private static String process_string="";
        private static String temp_string="initial_random_string";
        private static String answer="";
        private static String left_of_equation="";
        private static String right_of_equation="";
        private static String temp_equation="random_temp_equation";

        public static String steps="";
        public static boolean number_too_large=false;
        private static int number_max_size=15;

        private static int[] bracket_position=new int[2];
  
    public static String evaluate_string(String s){
        while(!temp_equation.equals(s)){
            temp_equation=s;
            bracket_position=look_for_brackets(s);
            if(bracket_position[0]==0&&bracket_position[1]==0){
                process_string=s; // no brackets
            }
            else{
                process_string=separate_the_string(bracket_position[0], bracket_position[1], s);
            }
            evaluate_simple_string();
            s=left_of_equation+answer+right_of_equation;
            left_of_equation="";right_of_equation="";
            if(answer.equals(s)){
                break;
            }
        }    
        return s;
    }

    private static void evaluate_simple_string(){
        process_string=simplify_string(process_string);
        while(temp_string!=process_string){
            temp_string=process_string;
            check_for_multiply_or_divide_sign(process_string);        
        }
        process_string=simplify_string(process_string);
        add_or_subtract(process_string);
    }

    private static void check_for_multiply_or_divide_sign(String s){
        int m=0;
        while(m<s.length()){
            if(DIVIDE==s.charAt(m)){
                divide_or_multiply(m,true);
                break;
            }
            if(MULTIPLY==s.charAt(m)){
                divide_or_multiply(m,false);
                break;
            }
            m++;
        }
    }

    private static void divide_or_multiply(int i,boolean operator){
        int k,l; String left_stringing="",right_stringing="",leftOfResult="",rightOfResult=""; double leftNumber,rightNumber,resultNumber;
        int j=i;
        j=j-1;
        while(true){
            left_stringing=process_string.charAt(j)+left_stringing;
            j=j-1;
            if((j==-1) || process_string.charAt(j)==MINUS || process_string.charAt(j)==PLUS || process_string.charAt(j)==MULTIPLY || process_string.charAt(j)==DIVIDE){    
                k=j;
                break;
            }
        }
        j=i;
        j=j+1;
        while(true){
            right_stringing=right_stringing+process_string.charAt(j);
            j=j+1;
            if(j==process_string.length()){ 
                l=j-1;
                break;
            }
            if(process_string.charAt(j)==MINUS || process_string.charAt(j)==PLUS || process_string.charAt(j)==MULTIPLY || process_string.charAt(j)==DIVIDE){
                l=j;
                while(l<process_string.length()){
                    rightOfResult=rightOfResult+process_string.charAt(l);
                    l++;
                }
                break;
            }
        }


        while(k>-1){
            leftOfResult=process_string.charAt(k)+leftOfResult;
            k=k-1;
        }
        if(len_of_num(left_stringing)>number_max_size || len_of_num(right_stringing)>number_max_size){
            number_too_large=true;
        }
        else{
            leftNumber=Double.parseDouble(left_stringing);
            rightNumber=Double.parseDouble(right_stringing);
            if(operator){
                resultNumber=leftNumber/rightNumber;
                process_string=leftOfResult+String.format("%.5f",resultNumber)+rightOfResult;
            }
            if(!operator){
                resultNumber=leftNumber*rightNumber;
                process_string=leftOfResult+String.format("%.5f",resultNumber)+rightOfResult;
            }
        }

    }

    private static int len_of_num(String x){
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
        //Log.d("mytag",f_num+". "+"len_of_num"+" params--> "+"x="+x+" , return--> val="+val); f_num++;
        return val;
    }

    private static String simplify_string(String s){
        //Log.d("mytag",f_num+". "+"simplyfyString"+" params--> "+"s="+s); f_num++;
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

    private static void add_or_subtract(String s){
        //Log.d("mytag",f_num+". "+"add_or_subtract"+" params--> "+"s="+s); f_num++;
        //Pattern p2=Pattern.compile(DEC_PAT);
        int i=s.length()-1; String str=""; double num=0;
        while(i!=-1){
            str=s.charAt(i)+str;
            if(MINUS==s.charAt(i) || PLUS==s.charAt(i) || i==0){
                if(len_of_num(str)>number_max_size){
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

    private static String separate_the_string(int a,int b,String s){
        String middle_string="",left_string="",right_string="";
        int i=0,j=b+1;
        while(i<a){
            left_string=left_string+s.charAt(i);
            i++;
        }
        while(j<s.length()){
            right_string=right_string+s.charAt(j);
            j++;
        }
        while(a<b-1){
            a++;
            middle_string=middle_string+s.charAt(a);
        }
        left_of_equation=left_string;
        right_of_equation=right_string;
        return middle_string;
    }
    
}