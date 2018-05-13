package com.azure.interpreter;

import com.azure.interpreter.var.Variable;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午6:11
 */
public class SourceCode {

    private SourceCode(){}

    /**
     * 生成token流
     * @param code
     * @return
     */
    private static String[] tokenize(String code){
        String[] tokens = code.replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ")
                .trim()
                .split("\\s+");
        return tokens;
    }

    public static Variable execute(String code, Environment scope) throws Exception {
        String[] tokens = tokenize(code);
        SExpression exp = SExpression.parse(tokens);
        return exp.evaluate(scope);
    }

}
