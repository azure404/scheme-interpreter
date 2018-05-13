package com.azure.interpreter.func;

import com.azure.interpreter.Environment;
import com.azure.interpreter.SExpression;
import com.azure.interpreter.var.Variable;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月13日 下午5:47
 */
public interface Function {

    int MINIMUN_PARAMETERS = 2;

    /**
     * 求值, s表达式在作用域内的值
     * @param exps
     * @param scope
     * @return
     * @throws Exception
     */
    Variable apply(SExpression[] exps, Environment scope) throws Exception;
}
