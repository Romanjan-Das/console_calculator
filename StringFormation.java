//package com.romanjandas.calculatorwithsteps;

//import android.util.Log;

public class StringFormation{
    public static String input_string="";
    public static String result="";
    private static final char PL='+';
    private static final char MI='-';
    private static final char DI='/';
    private static final char MU='x';
    private static final char LB='(';
    private static final char RB=')';
    private static final char EQ='=';
    private static final char DE='.';
    public static char p;
    private static char c;
    public static boolean no_key_pressed=true;
    public static boolean allow=false;
    public static int rbn=0,lbn=0;
    public static boolean equal_is_pressed=false;



    public static void verify_input(char x){
        //Log.d("mytag",EvaluateString.f_num+". "+"verify_input"+" : "+x); EvaluateString.f_num++;
        if(equal_is_pressed && cn(x)){
            no_key_pressed=true; allow=false; input_string=""; EvaluateString.steps=""; equal_is_pressed=false;
        }
        else {
            equal_is_pressed=false;
        }
        c=x;
                if(no_key_pressed){
                    if(c==LB||c==MI||cn(c)){
                        p=c;
                        no_key_pressed=false;
                        allow=true;
                        rbn=0;lbn=0;
                        if(c==LB){lbn=1;}
                    }
                }
                else{
                    if(c==LB){allow=alb();}
                    if(c==RB){allow=arb();}
                    if(c==DI){allow=adi();}
                    if(c==MU){allow=amu();}
                    if(c==PL){allow=apl();}
                    if(c==MI){allow=ami();}
                    if(c==DE){allow=ade();}
                    if(cn(c)){allow=anu();}
                    if(c==EQ){allow=aeq();}
                } 
                if(c!=EQ && allow){
                    p=c;
                    input_string=input_string+c;
                }
                else if(c==EQ && rbn==lbn && allow){
                    try{
                        EvaluateString.steps=input_string;
                        result=EvaluateString.evaluate_string(input_string);
                    }
                    catch(Exception e){
                        input_string="Some error occured";
                    }
                    input_string=result;
                    p=input_string.charAt(input_string.length()-1);
                }
    }

    private static boolean cn(char x){
        if(x=='0'||x=='1'||x=='2'||x=='3'||x=='4'||x=='5'||x=='6'||x=='7'||x=='8'||x=='9'){
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean alb(){
        if(p==RB||p==DE||cn(p)){
            return false;
        }
        else{
            lbn++;
            return true;
        }
    }

    private static boolean arb(){
        if(p==LB||p==DI||p==MU||p==PL||p==MI||p==DE|| rbn==lbn){
            return false;
        }
        else{
            rbn++;
            return true;
        }
    }

    private static boolean adi(){
        if(p==LB||p==DI||p==MU||p==PL||p==MI||p==DE){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean amu(){
        if(p==LB||p==DI||p==PL||p==MI||p==DE||p==MU){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean apl(){
        if(p==LB||p==DI||p==MU||p==MI||p==PL||p==DE){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean ami(){
        if(p==PL||p==MI||p==DE){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean ade(){
        if(p==LB||p==RB||p==MU||p==DI||p==PL||p==MI||p==DE){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean anu(){
        if(p==RB){
            return false;
        }
        else{
            return true;
        }
    }

    private static boolean aeq(){
        if(p==LB||p==MU||p==DI||p==PL||p==MI||p==DE || rbn!=lbn){
            c='0';
            return false;
        }
        else{
            return true;
        }
    }
}