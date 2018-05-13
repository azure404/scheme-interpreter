package com.azure.interpreter;

import com.azure.interpreter.exeption.VariableNotExistExeption;
import com.azure.interpreter.var.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:31
 */
public class Environment {

    private Environment parent;
    private Map<String, Variable> vars;

    public Environment(Environment parent) {
        this.parent = parent;
        this.vars = new HashMap<>();
    }

    public Variable find(String varName) throws VariableNotExistExeption {
        Environment env = this;
        Variable var;
        while(env != null){
            var = env.getValue(varName);
            if(var != null){
                return var;
            }
            env = env.getParentEnvironment();
        }
        throw new VariableNotExistExeption(varName);
    }

    public Variable define(String name, Variable value){
        return vars.put(name, value);
    }

    public Variable findDirectly(String name){
        return vars.get(name);
    }

    public Variable getValue(String varName){
        return vars.get(varName);
    }

    public Environment getParentEnvironment(){
        return parent;
    }
}
