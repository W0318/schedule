package com.schedule.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Calculate {
    /**
     * 处理公式
     *
     * @param expression 公式字符串
     * @param var        带入变量值
     * @return 值
     */
    public double dealExp(String expression, double var) {
        System.out.println("表达式什么神马Aa                                                                                              "+expression);
        Stack<Double> operandStack = new Stack<>();   //操作数
        Stack<Character> operatorStack = new Stack<>();   //操作符

        expression = insertBlanks(expression);
        List<String> list = Arrays.asList(expression.split(" "));
        ArrayList<String> tokens = new ArrayList<>(list);
        tokens = dealVariable(tokens, var);

        for (String token : tokens) {
            if (token.length() == 0)   //处理不规则输入表达式，即一会儿操作符和操作数之间有空格，一会儿没有
                continue;
            else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
                //同优先级或高优先级都可以进行计算了，即 + - * / 都可以进行运算
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek() == '+' || operatorStack.peek() == '-' ||
                                operatorStack.peek() == '*' || operatorStack.peek() == '/')) {
                    calculate(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
                //同优先级或高优先级都可以进行计算了，即 * / 都可以进行运算
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek() == '*' ||
                                operatorStack.peek() == '/')) {
                    calculate(operandStack, operatorStack);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.trim().charAt(0) == '(') {
                operatorStack.push('(');
            } else if (token.trim().charAt(0) == ')') {
                while (operatorStack.peek() != '(') {   //计算括号内的值
                    calculate(operandStack, operatorStack);
                }
                operatorStack.pop();
            } else {   //操作数入栈
                operandStack.push(new Double(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            calculate(operandStack, operatorStack);
        }

        return operandStack.pop();
    }

    /**
     * 运算后入operandStack栈
     *
     * @param operandStack  操作数栈
     * @param operatorStack 操作符栈
     */
    public void calculate(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char op = operatorStack.pop();
        double op1 = operandStack.pop();
        double op2 = operandStack.pop();

        if (op == '+')
            operandStack.push(op2 + op1);
        else if (op == '-')
            operandStack.push(op2 - op1);
        else if (op == '*')
            operandStack.push(op2 * op1);
        else if (op == '/')
            operandStack.push(op2 / op1);
    }

    /**
     * 操作符前后加空格
     *
     * @param s 输入表达式
     * @return 表达式字符串
     */
    public String insertBlanks(String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')' ||
                    s.charAt(i) == '+' || s.charAt(i) == '-' ||
                    s.charAt(i) == '*' || s.charAt(i) == '/')
                result += " " + s.charAt(i) + " ";
            else
                result += s.charAt(i);
        }

        return result;
    }

    /**
     * 将公式中的变量带入数值，不支持多个变量参数
     *
     * @param tokens 操作数、操作符
     * @param var    变量值
     * @return
     */
    public ArrayList<String> dealVariable(ArrayList<String> tokens, double var) {
        ArrayList<String> result = tokens;

        for (int i = 0; i < result.size(); i++) {
            if ("size".equals(result.get(i).trim()) || "flow".equals(result.get(i).trim())) {
                result.set(i, String.valueOf(var));
            }
        }

        return result;
    }
}
