package com.azure.interpreter.var;

import java.util.AbstractList;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午9:54
 */
public class List extends Variable{

    private final AbstractList<Variable> value;

    public List(AbstractList<Variable> value) {
        this.value = value;
    }

    public Variable get(int index){
        return value.get(index);
    }

    public int size(){
        return value.size();
    }

    public List first(){
        return new List((AbstractList<Variable>) value.subList(0,1));
    }

    public List rest(){
        return new List((AbstractList<Variable>) value.subList(1, value.size()));
    }

    public List append(List list){
        for (int i = 0, size = list.size(); i < size; i++) {
            value.add(list.get(i));
        }
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
