package com.azure.interpreter.var;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:48
 */
public class Bool extends Variable{

    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);

    private final boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    public static boolean valueOf(Variable var){
        if(var instanceof Bool) {
            return ((Bool) var).value;
        }
        throw new IllegalArgumentException("not a bool");
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
