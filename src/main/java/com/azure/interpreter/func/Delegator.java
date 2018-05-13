package com.azure.interpreter.func;

import com.azure.interpreter.Environment;
import com.azure.interpreter.SExpression;
import com.azure.interpreter.exeption.IllegalParameterNumbersException;
import com.azure.interpreter.var.Bool;
import com.azure.interpreter.var.Number;
import com.azure.interpreter.var.Variable;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月13日 下午7:04
 */
public class Delegator {

    public static void checkAtLeastLength(SExpression[] exps, int expectLength) throws IllegalParameterNumbersException {
        if(exps.length < expectLength){
            throw new IllegalParameterNumbersException("parameter count should be at least " + expectLength);
        }
    }

    public static void checkEqualLength(SExpression[] exps, int expectLength) throws IllegalParameterNumbersException {
        if(exps.length != expectLength){
            throw new IllegalParameterNumbersException("parameter count should be " + expectLength);
        }
    }

    public static Variable[] evaluate(SExpression[] exps, Environment scope) throws Exception {
        Variable[] vars = new Variable[exps.length];
        for (int i = 0; i < exps.length; i++) {
            vars[i] = exps[i].evaluate(scope);
        }
        return vars;
    }

    public static Number accumulate(Variable[] vars){
        return accumulate(vars, 0, vars.length);
    }

    public static Number accumulate(Variable[] vars, int start, int end){
        start = start >= 0 ? start : 0;
        end = end <= vars.length ? end : vars.length;

        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += Number.valueOf(vars[i]);
        }
        return new Number(sum);
    }

    public static Number aggregate(Variable[] vars){
        return aggregate(vars, 0, vars.length);
    }

    public static Number aggregate(Variable[] vars, int start, int end){
        start = start >= 0 ? start : 0;
        end = end <= vars.length ? end : vars.length;

        long pro = 1;
        for (int i = start; i < end; i++) {
            pro *= Number.valueOf(vars[i]);
        }
        return new Number(pro);
    }

    public static Bool any(SExpression[] exps, Environment scope, Bool except) throws Exception {
        for (int i = 0; i < exps.length; i++) {
            Bool value = (Bool) exps[i].evaluate(scope);
            if(Bool.valueOf(value) == Bool.valueOf(except)){
                return Bool.TRUE;
            }
        }
        return Bool.FALSE;
    }

    public static Bool all(SExpression[] exps, Environment scope, Bool except) throws Exception {
        for (int i = 0; i < exps.length; i++) {
            Bool value = (Bool) exps[i].evaluate(scope);
            if(Bool.valueOf(value) != Bool.valueOf(except)){
                return Bool.FALSE;
            }
        }
        return Bool.TRUE;
    }

    public static Bool chainRelation(SExpression[] exps, Environment scope, ChainRelation relation) throws Exception {
        if(exps.length < Function.MINIMUN_PARAMETERS){
            throw new IllegalParameterNumbersException("parameter count should be at least 2");
        }
        Number left = (Number) exps[0].evaluate(scope);
        for (int i = 1; i < exps.length; i++) {
            Number right = (Number) exps[i].evaluate(scope);
            if(relation.relate(left, right)){
                left = right;
            }else {
                return Bool.FALSE;
            }
        }
        return Bool.TRUE;
    }
}
