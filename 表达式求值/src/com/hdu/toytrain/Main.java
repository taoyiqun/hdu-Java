package com.hdu.toytrain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 表达式求值重写栈版
 */
class SqStack<T>{

    private T[] stackElem;
    private int top;
    public static final int maxSize=30;//初始分配变量
    public static final int addSize=20;//栈递增增量

    public SqStack(Class<T> type)
    {
        stackElem=(T[]) Array.newInstance(type,maxSize);
        top=0;
    }

    //清空
    public void clear()
    {
        top=0;
    }
    //是否为空
    public boolean isEmpty()
    {
        return top==0;
    }
    //元素个数
    public int length()
    {
        return top;
    }
    //栈顶
    public Object peek()
    {
        if(!isEmpty())
            return stackElem[top-1];
        else
            return null;
    }

    //入栈
    public void push(T x)
    {
        if(top==stackElem.length)
        {
            stackElem= Arrays.copyOf(stackElem,addSize+stackElem.length);
            stackElem[top++]=x;
        }
        else
        {
            stackElem[top++]=x;
        }
    }
    //出栈
    public Object pop()
    {

        return stackElem[--top];  //删除然后返回现在的栈顶
    }

    //打印（从栈顶到栈底）
    public void display(int count)
    {
        System.out.print(count+" ");
        for(int i=length()-1; i>=0; i--)
        {
            System.out.print(stackElem[i]+" ");
        }
        System.out.println();
    }

}
class   cal{                                /*'+'  '-'  '*'  '/'  '('  ')'  '#'  '^' */
    public static final char[][]rule=/*'+'*/{{'>', '>', '<', '<', '<', '>', '>', '<'},
                                      /*'-'*/{'>', '>', '<', '<', '<', '>', '>', '<'},
                                      /*'*'*/{'>', '>', '>', '>', '<', '>', '>', '<'},
                                      /*'/'*/{'>', '>', '>', '>', '<', '>', '>', '<'},
                                      /*'('*/{'<', '<', '<', '<', '<', '=', ' ', '<'},
                                      /*')'*/{'>', '>', '>', '>', ' ', '>', '>', '>'},
                                      /*'#'*/{'<', '<', '<', '<', '<', ' ', '=', '<'},
                                      /*'^'*/{'>', '>', '>', '>', '<', '>', '>', '>'}};     //运算先后矩阵
    public static final BigDecimal  zero=new BigDecimal("0");
    public static int   add(char    t){//定位符号位置
        switch(t){
            case '+':{
                return 0;
            }
            case '-':{
                return 1;
            }
            case '*':{
                return 2;
            }
            case '/':{
                return 3;
            }
            case '(':{
                return 4;
            }
            case ')':{
                return 5;
            }
            case '#':{
                return 6;
            }
            case  '^':{
                return 7;
            }
            default:{
                return -1;
            }
        }

    }
    //单个运算
    public static BigDecimal    calculation(BigDecimal  a,BigDecimal    b,char  operation){
        BigDecimal  res=null;
        switch (operation){
            case '+':{
                res=a.add(b);
                break;
            }
            case '-':{
                res=a.subtract(b);
                break;


            }
            case '*':{
                res= a.multiply(b);
                break;
            }
            case '/':{
                if(b.compareTo(zero)!=0){//0的特判
                    res=a.divide(b);
                    break;
                }else {
                    break;
                }
            }
            case '^':{
                if(b.compareTo(cal.zero)==0){
                    res=new BigDecimal("1");
                }else {
                    if (b.compareTo(cal.zero)<0){//负指数特判
                        b=cal.zero.subtract(b);
                        res=new BigDecimal("1").divide(a.pow(b.intValue()));
                    }else {
                        res=a.pow(b.intValue());
                    }
                }
                break;
            }
        }
        return res;
    }
    public static String    calculator(String    str){
        StringBuffer    strb=new StringBuffer(str);
        SqStack num=new SqStack(BigDecimal.class);      //数字栈
        SqStack mark=new SqStack(Character.class);      //符号栈
        mark.push('#');
        int j;
        for(j = 0;j < strb.length(); j++){//负数特判
            if(strb.charAt(j) == '-'){
                if(j == 0){
                    strb.insert(0,'0');
                }else if(strb.charAt(j-1) == '('){
                    strb.insert(j,'0');
                }
            }
        }
        j=0;
        int count=0;
        while (j<strb.length()&&strb.charAt(j)!='#'||(Character)mark.peek()!='#'){//j不能越界
            count++;
            num.display(count);     //当前数字栈
            mark.display(count);    //当前运算符栈
            if(strb.charAt(j)>='0'&&strb.charAt(j)<='9'){
                String  number=new String("");
                int pointflag=0;
                while (strb.charAt(j)>='0'&&strb.charAt(j)<='9'||strb.charAt(j)=='.'){
                    if(strb.charAt(j)=='.'){
                        pointflag++;
                    }
                    number=number+strb.charAt(j);
                    j++;
                }//处理小数，并将其转为数字入栈
                if(pointflag>1){
                    return "小数点错误";
                }
                num.push(new BigDecimal(number));
            }else {
                int op1=add((char)mark.peek()); //符号在运算先后矩阵中的下标
                int op2=add(strb.charAt(j));
                if (op1==-1||op2==-1){
                    return "输入含有非法字符";
                }
                char sequence=rule[op1][op2];
                switch (sequence){
                    case '>':{
                        char operation=(char)mark.pop();
                        BigDecimal  b=(BigDecimal)num.pop();//弹出后一个数
                        BigDecimal  a=(BigDecimal)num.pop();
                        BigDecimal  res=cal.calculation(a,b,operation);
                        if(res==null){
                            return "0不能做除数";
                        }else {
                            num.push(res);
                        }
                        break;

                    }
                    case '=':{
                        mark.pop();
                        j++;
                        break;
                    }
                    case '<':{
                        mark.push(strb.charAt(j));
                        j++;
                        break;
                    }
                }
            }
        }
        BigDecimal  res=(BigDecimal)num.peek();
        return res.toString();
    }
}

/**
 * ui
 */
class ui  extends JFrame {
    public static int flag=0;
    public ui(){
        Container c=getContentPane();//顶层容器
        setLayout(new GridLayout(2,1));//2行1类
        JTextField  jtf=new JTextField("0",40);//文本框
        jtf.setHorizontalAlignment(JTextField.RIGHT);//从右边显示
        JButton data0 = new JButton("0");
        JButton data1 = new JButton("1");
        JButton data2 = new JButton("2");
        JButton data3 = new JButton("3");
        JButton data4 = new JButton("4");
        JButton data5 = new JButton("5");
        JButton data6 = new JButton("6");
        JButton data7 = new JButton("7");
        JButton data8 = new JButton("8");
        JButton data9 = new JButton("9");
        JButton point = new JButton(".");
        JButton lbracket=new JButton("(");
        JButton rbracket=new JButton(")");
        JButton plus=new JButton("+");
        JButton minus=new JButton("-");
        JButton minuti=new JButton("*");
        JButton div=new JButton("/");
        JButton cf=new JButton("^");
        JButton ql=new JButton("清零");
        JButton equ=new JButton("=");
        JButton tg=new JButton("退格");
        JPanel  jp=new JPanel();//面板来安置按钮
        jp.setLayout(new GridLayout(5,5,5,5));
        jp.add(data7);
        jp.add(data8);
        jp.add(data9);
        jp.add(point);
        jp.add(plus);
        jp.add(data4);
        jp.add(data5);
        jp.add(data6);
        jp.add(cf);
        jp.add(minus);
        jp.add(data1);
        jp.add(data2);
        jp.add(data3);
        jp.add(ql);
        jp.add(minuti);
        jp.add(lbracket);
        jp.add(data0);
        jp.add(rbracket);
        jp.add(tg);
        jp.add(div);
        jp.add(equ);
        c.add(jtf);
        c.add(jp);
        setSize(400,300);
        setTitle("表达式求值");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        data0.addActionListener(new ActionListener() {//文本框加入0
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"0");
                }
            }
        });
        data1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("1");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"1");
                }
            }
        });
        data2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("2");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"2");
                }
            }
        });
        data3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("3");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"3");
                }
            }
        });
        data4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("4");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"4");
                }
            }
        });
        data5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("5");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"5");
                }
            }
        });
        data6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("6");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"6");
                }
            }
        });
        data7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("7");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"7");
                }
            }
        });
        data8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("8");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"8");
                }
            }
        });
        data9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("9");
                    jtf.requestFocus();
                }else {
                    String  str=jtf.getText();
                    jtf.setText(str+"9");
                }
            }
        });
        point.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jtf.getText().equals("0")){
                    jtf.setText("0.");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+".");
                }
            }
        });
        ql.addActionListener(new ActionListener(){//清零
            public void actionPerformed(ActionEvent e) {
                jtf.setText("0");
            }

        });
        lbracket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("(");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"(");
                }
            }
        });
        rbracket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText(")");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+")");
                }
            }
        });
        plus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jtf.getText().equals("0")){
                    jtf.setText("0+");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"+");
                }
            }
        });
        minus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jtf.getText().equals("0")){
                    jtf.setText("0-");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"-");
                }
            }
        });
        minuti.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jtf.getText().equals("0")){
                    jtf.setText("0*");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"*");
                }
            }
        });
        div.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("0/");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"/");
                }
            }
        });
        cf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("0^");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"^");
                }
            }
        });
        tg.addActionListener(new ActionListener(){//退格
            public void actionPerformed(ActionEvent e){
                String text = jtf.getText();
                int i = text.length();
                if(i>0){
                    text = text.substring(0,i-1);
                    if (text.length() == 0) {
                        jtf.setText("0");
                    } else {
                        jtf.setText(text);
                    }
                }
            }
        });
        equ.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String  str=jtf.getText();
                jtf.setText("");
                str=str+"#";
                str=cal.calculator(str);
                jtf.setText(str);

            }
        });
    }
}
public class Main {
    public static void main(String[]  args){
        new ui();
    }
}