package com.azure.interpreter;

import com.azure.interpreter.var.Variable;

import java.util.Scanner;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午6:58
 */
public class Interpreter {

    public static final String TERMINAL_SYMBOL = "q";
    public static final String PROMPT_SIGN = ">> ";

    private Environment scope;

    public Interpreter() {
        scope = new Environment(null);
    }

    public void keepInterpreting() {
        Scanner scanner = new Scanner(System.in);
        String code;
        while(true) {
            System.out.print(PROMPT_SIGN);
            code = scanner.nextLine();
            if(TERMINAL_SYMBOL.equalsIgnoreCase(code)){
                System.exit(0);
            }
            try {
                Variable result = SourceCode.execute(code, scope);
                System.out.println(PROMPT_SIGN + result);
            }catch (Exception e){
                System.out.println(PROMPT_SIGN + e.getMessage());
            }
        }
    }
}
