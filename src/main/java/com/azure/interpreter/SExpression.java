package com.azure.interpreter;

import com.azure.interpreter.func.BuiltInProcess;
import com.azure.interpreter.var.Number;
import com.azure.interpreter.var.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：TODO
 *
 * @author guyi
 * @date 2018年05月12日 下午6:11
 */
public class SExpression {

    public static final String OPEN_PARENTHESIS = "(";
    public static final String CLOSE_PARENTHESIS = ")";

    private String value;
    private SExpression parent;
    private List<SExpression> children;

    private SExpression(String value, SExpression parent) {
        this.value = value;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    private SExpression addChild(String token){
        SExpression child = new SExpression(token, this);
        this.children.add(child);
        return child;
    }

    /**
     * 解析token流
     * @param tokens
     * @return
     */
    public static SExpression parse(String[] tokens){
        SExpression root = new SExpression("", null);
        SExpression curNode = root;
        for (String token : tokens){
            if(OPEN_PARENTHESIS.equals(token)){
                curNode = curNode.addChild(token);
            } else if(CLOSE_PARENTHESIS.equals(token)){
                curNode = curNode.parent;
            }else{
                curNode.addChild(token);
            }
        }
        return root.children.get(0);
    }

    public Variable evaluate(Environment scope) throws Exception {
        if(children.isEmpty()){
            if(tryParse(value)) {
                return new Number(Long.valueOf(value));
            } else {
                return scope.find(value);
            }
        }else{
            SExpression symbol = children.get(0);
            BuiltInProcess func = BuiltInProcess.of(symbol.value);
            int size = children.size();
            SExpression[] arguments = children.subList(1, size).toArray(new SExpression[size - 1]);
            return func.apply(arguments, scope);
        }
    }


    private boolean tryParse(String value){
        try{
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }


}
