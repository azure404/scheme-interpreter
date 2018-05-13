package com.azure.interpreter.func;

import com.azure.interpreter.Environment;
import com.azure.interpreter.SExpression;
import com.azure.interpreter.var.Bool;
import com.azure.interpreter.var.List;
import com.azure.interpreter.var.Number;
import com.azure.interpreter.var.Variable;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月13日 下午5:44
 */
public enum BuiltInProcess {

    DEFINE("define", (exps, scope) -> {
        Delegator.checkEqualLength(exps, Function.MINIMUN_PARAMETERS);
        Variable var = exps[1].evaluate(scope);
        scope.define(exps[0].getValue(), var);
        return var;
    }),

    PLUS("+", (exps, scope) -> {
        Variable[] vars = Delegator.evaluate(exps, scope);
        return Delegator.accumulate(vars);
    }),

    MINUS("-", (exps, scope) -> {
        Variable[] vars = Delegator.evaluate(exps, scope);
        long diff = Number.valueOf(vars[0]);
        if(vars.length == 1){
            return new Number(-diff);
        }
        Number sumSkipOne = Delegator.accumulate(vars, 1, vars.length);
        return new Number(diff - sumSkipOne.getValue());
    }),

    MULTI("*", (exps, scope) -> {
        Variable[] vars = Delegator.evaluate(exps, scope);
        return Delegator.aggregate(vars);
    }),

    DIV("/", (exps, scope) -> {
        Delegator.checkAtLeastLength(exps, Function.MINIMUN_PARAMETERS);
        Variable[] vars = Delegator.evaluate(exps, scope);
        long quot = Number.valueOf(vars[0]);
        Number sumSkipOne = Delegator.accumulate(vars, 1, vars.length);
        return new Number(quot / sumSkipOne.getValue());
    }),

    MOD("%", (exps, scope) -> {
        Delegator.checkAtLeastLength(exps, Function.MINIMUN_PARAMETERS);
        Variable[] vars = Delegator.evaluate(exps, scope);
        long remain = Number.valueOf(vars[0]);
        for (int i = 1; i < vars.length; i++) {
            remain %= Number.valueOf(vars[i]);
        }
        return new Number(remain);
    }),

    AND("and", (exps, scope) -> {
        return Delegator.all(exps, scope, Bool.TRUE);
    }),

    OR("or", (exps, scope) -> {
        return Delegator.any(exps, scope, Bool.TRUE);
    }),

    NOT("not", (exps, scope) -> {
        Delegator.checkEqualLength(exps, 1);
        return Delegator.evaluate(exps, scope)[0];
    }),

    EQUAL("=", (exps, scope) -> {
        return Delegator.chainRelation(exps, scope,
                (n1, n2) -> n1.getValue() == n2.getValue());
    }),

    LESS("<", (exps, scope) -> {
        return Delegator.chainRelation(exps, scope,
                (n1, n2) -> n1.getValue() < n2.getValue());
    }),

    GREAT(">", (exps, scope) -> {
        return Delegator.chainRelation(exps, scope,
                (n1, n2) -> n1.getValue() > n2.getValue());
    }),

    LE("<=", (exps, scope) -> {
        return Delegator.chainRelation(exps, scope,
                (n1, n2) -> n1.getValue() <= n2.getValue());
    }),

    GE(">=", (exps, scope) -> {
        return Delegator.chainRelation(exps, scope,
                (n1, n2) -> n1.getValue() >= n2.getValue());
    }),

    FIRST("first", (exps, scope) -> {
        Delegator.checkEqualLength(exps, 1);
        List list = (List) exps[0].evaluate(scope);
        return list.first();
    }),

    REST("rest", (exps, scope) -> {
        Delegator.checkEqualLength(exps, 1);
        List list = (List) exps[0].evaluate(scope);
        return list.rest();
    }),

    APPEND("append", (exps, scope) -> {
        Delegator.checkAtLeastLength(exps, Function.MINIMUN_PARAMETERS);
        List left = (List) exps[0].evaluate(scope);
        List right = (List) exps[1].evaluate(scope);
        return left.append(right);
    }),

    EMPTY("empty?", (exps, scope) -> {
        Variable[] vars = Delegator.evaluate(exps, scope);
        for (int i = 0; i < vars.length; i++) {
            boolean empty = ((List)vars[i]).size() == 0;
            if(!empty){
                return Bool.FALSE;
            }
        }
        return Bool.TRUE;
    });


    private String name;
    private Function func;

    BuiltInProcess(String name, Function func) {
        this.name = name;
        this.func = func;
    }

    public Variable apply(SExpression[] exps, Environment scope) throws Exception {
        return func.apply(exps, scope);
    }

    public static BuiltInProcess of(String name){
        for(BuiltInProcess func : values()){
            if(func.name.equals(name)){
                return func;
            }
        }
        throw new UnsupportedOperationException("unsupported operation: " + name);
    }

}
