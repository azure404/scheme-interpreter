package com.azure.interpreter.exeption;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:41
 */
public class VariableNotExistExeption extends Exception{

    public VariableNotExistExeption(String varName) {
        super("variable: " + varName + " not exist");
    }

}
