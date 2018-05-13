package com.azure.interpreter.var;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:47
 */
public class Number extends Variable{

    private final long value;

    public Number(long value) {
        this.value = value;
    }

    public static long valueOf(Variable var){
        if(var instanceof Number) {
            return ((Number) var).value;
        }
        throw new IllegalArgumentException("not a number");
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
