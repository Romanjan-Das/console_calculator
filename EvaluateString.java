//package com.romanjandas.calculatorwithsteps;

//import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaluateString{
        private static final char PLUS='+';
        private static final char MINUS='-';
        private static final char DIVIDE='/';
        private static final char MULTIPLY='x';
        private static final char LEFTBRACKET='(';
        private static final char RIGHTBRACKET=')';
        private static final String DEC_PAT=".00000";
        private static final String DEC_REP="";
        private static final String ZERO="0";

        private static String process_string="";
        private static String temp_string="initial_random_string";
        private static String answer="";
        private static String left_of_equation="";
        private static String right_of_equation="";
        private static String temp_equation="random_temp_equation";

        public static String steps="";
        public static boolean number_too_large=false;
        private static int number_max_size=15;

        public static int f_num=1;
  
    public static String evaluate_string(String s){
        //Log.d("mytag",f_num+". "+"evaluate_string"+" params--> "+"s="+s); f_num++;
        while(!temp_equation.equals(s)){
            temp_equation=s;
            process_string=look_for_brackets(s);
            process_of_evaluation_of_simple_string();
            s=left_of_equation+answer+right_of_equation;
            left_of_equation="";right_of_equation="";
            if(answer.equals(s)){
                break;
            }
        }    
        return s;
    }

    private static void process_of_evaluation_of_simple_string(){
        //Log.d("mytag",f_num+". "+"process_of_evaluation_of_simple_string"+" : "+"process_string="+process_string); f_num++;
        process_string=simplifyString(process_string);
        while(temp_string!=process_string){
            temp_string=process_string;
            checkForMultiplyOrDivisionSign(process_string);        
        }
        process_string=simplifyString(process_string);
        add_or_subtract(process_string);
    }

    private static void checkForMultiplyOrDivisionSign(String s){
        //Log.d("mytag",f_num+". "+"checkForMultiplyOrDivisionSign"+" params--> "+"s="+s); f_num++;
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
        //Log.d("mytag",f_num+". "+"divide_or_multiply"+" params--> "+"i="+i+",operator="+operator); f_num++;
        Pattern p=Pattern.compile(DEC_PAT);
        int k,l; String leftString="",rightString="",leftOfResult="",rightOfResult=""; double leftNumber,rightNumber,resultNumber;
        int j=i;
        j=j-1;
        while(true){
            leftString=process_string.charAt(j)+leftString;
            j=j-1;
            if((j==-1) || process_string.charAt(j)==MINUS || process_string.charAt(j)==PLUS || process_string.charAt(j)==MULTIPLY || process_string.charAt(j)==DIVIDE){    
                k=j;
                break;
            }
        }
        j=i;
        j=j+1;
        while(true){
            rightString=rightString+process_string.charAt(j);
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
        if(len_of_num(leftString)>number_max_size || len_of_num(rightString)>number_max_size){
            number_too_large=true;
        }
        else{
            leftNumber=Double.parseDouble(leftString);
            rightNumber=Double.parseDouble(rightString);
            if(operator){
                resultNumber=leftNumber/rightNumber;
                process_string=leftOfResult+String.format("%.5f",resultNumber)+rightOfResult;
                Matcher m=p.matcher(simplifyString(left_of_equation+leftOfResult+String.format("%.5f",resultNumber)+rightOfResult+right_of_equation));
                if(!ZERO.equals(m.replaceAll(DEC_REP))){
                    steps=steps+"\n"+m.replaceAll(DEC_REP);
                }

            }
            if(!operator){
                resultNumber=leftNumber*rightNumber;
                process_string=leftOfResult+String.format("%.5f",resultNumber)+rightOfResult;
                Matcher m=p.matcher(simplifyString(left_of_equation+leftOfResult+String.format("%.5f",resultNumber)+rightOfResult+right_of_equation));
                if(!ZERO.equals(m.replaceAll(DEC_REP))){
                    steps=steps+"\n"+m.replaceAll(DEC_REP);
                }
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

    private static String simplifyString(String s){
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

    private static String look_for_brackets(String s){
        //Log.d("mytag",f_num+". "+"look_for_brackets"+" params--> "+"s="+s); f_num++;
        int leftBracketPosition=0,rightBracketPosition=0,i=0;
        String s1="";
        while(true){
            if(i<s.length()){
                if(LEFTBRACKET==s.charAt(i)){
                    leftBracketPosition=i;
                }
                else if(RIGHTBRACKET==s.charAt(i)){
                    rightBracketPosition=i;
                    s1=separate_the_string(leftBracketPosition,rightBracketPosition,s);
                    break;
                }

            }
            else{
                s1=s;
                break;
            }
            i++;
        }
        return s1;
    }

    private static String separate_the_string(int a,int b,String s){
        //Log.d("mytag",f_num+". "+"separate_the_string"+" params--> "+"s="+s+", a,b="+a+","+b); f_num++;
        String midStr="",leftStr="",rightStr="";//,evalStr="",joinedStr="";
        int i=0,j=b+1;
        while(i<a){
            leftStr=leftStr+s.charAt(i);
            i++;
        }
        while(j<s.length()){
            rightStr=rightStr+s.charAt(j);
            j++;
        }
        while(a<b-1){
            a++;
            midStr=midStr+s.charAt(a);
        }
        left_of_equation=leftStr;
        right_of_equation=rightStr;
        return midStr;
    }
    
}