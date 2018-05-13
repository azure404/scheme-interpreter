package com.azure.interpreter.var;

import com.azure.interpreter.Environment;
import com.azure.interpreter.SExpression;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:56
 */
public class Function extends Variable{

    /**
     * 函数体
     */
    private SExpression body;
    /**
     * 形式参数
     */
    private String[] parameters;
    /**
     * 作用域，求值环境
     */
    private Environment scope;

    public Function(SExpression body, String[] parameters, Environment scope) {
        this.body = body;
        this.parameters = parameters;
        this.scope = scope;
    }

    public Variable eval() throws Exception {
        String[] filled = filledParameters();
        if(filled.length < parameters.length){
            return this;
        }
        return body.evaluate(scope);
    }

    private String[] filledParameters(){
        ArrayList<String> filled = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            if(scope.findDirectly(parameters[i]) != null){
                filled.add(parameters[i]);
            }
        }
        return filled.toArray(new String[filled.size()]);
    }

    @Override
    public String toString() {
        return String.format("(lambda (%s) %s)", formatParameters(), body);
    }

    private String formatParameters(){
        StringBuilder sb = new StringBuilder();
        Variable value;
        String varName;
        for (int i = 0; i < parameters.length; i++) {
            varName = parameters[i];
            sb.append(varName);
            if((value = scope.findDirectly(varName))
                    != null){
                sb.append(":" + value);
            }
            if(i < parameters.length -1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }


}
