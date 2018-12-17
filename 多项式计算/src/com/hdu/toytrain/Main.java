package com.toytrain.Main;
/**
 * @author  170421277 陶逸群
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 节点类用来表示多项式
 * */
class PolyNode{
    public BigDecimal coef;//系数
    public BigInteger expon;//指数
    public PolyNode link;//指向下一个节点
    public static BigDecimal   czero=new BigDecimal("0");//系数0项
    public static BigInteger    ezero=new BigInteger("0");//指数0项
    public static BigInteger    eone=new BigInteger("1");//指数1项
    public static BigDecimal    cone=new BigDecimal("1");//系数1项
    public static BigDecimal    coneno=new BigDecimal("-1");//系数-1项
    /**
     * 构造一个以coef为系数,expon为指数,下个节点为null的项
     */
    public PolyNode(BigDecimal  coef,BigInteger   expon){
        this.coef=coef;
        this.expon=expon;
        this.link=null;
    }
    /**
     * 不提供系数，指数,构造下个节点为null的项
     */
    public PolyNode(){
        this.coef=new BigDecimal("0");
        this.expon=new BigInteger("0");
        this.link=null;
    }
    /**
     * 以c为系数e为指数构造一个新节点连接在rear节点后
     */
    public static PolyNode Attach(BigDecimal    c,BigInteger  e,PolyNode  rear){
        PolyNode    node=new PolyNode(c,e);
        rear.link=node;
        rear=node;
        return  rear;
    }
    /**
     * 从字符串中读取多项式，按指数从大到小顺序排列
     */
    public static PolyNode ReadPloy(String  s){
        String  ps[]=s.split("[ ]");//将字符串以“ ”分割返回分隔后子串
        int N=ps.length;
        PolyNode    front=new PolyNode();//头结点
        for (int i=0;i<N;i=i+2){//每两个字符串为一组构成节点
            BigDecimal  c=new BigDecimal(ps[i]);
            BigInteger  e=new BigInteger(ps[i+1]);
            if(c.compareTo(PolyNode.czero)==0){//系数等于0不添加该节点
                continue;
            }
            PolyNode    node=new PolyNode(c,e);
            PolyNode    q;//q节点用来搜素要插入位置,每次都在q节点之后插入
            for(q=front; (q.link)!=null&&(q.link.expon.compareTo(e)>1); q=q.link);//找到插入位置
            if(q.link==null){//要插入位置后为空，新加节点
                q.link=node;
            }else {
                if(q.link.expon.compareTo(e)==0){//如果q节点的下一个节点指数等于e
                    q.link.coef=q.link.coef.add(c);//系数相加
                    if(q.link.coef.compareTo(PolyNode.czero)==0){//和为0删除该节点
                        q=q.link.link;
                    }
                } else {//在q节点后插入新节点
                    node.link=q.link;
                    q.link=node;
                }
            }
        }


        front=front.link;//释放头结点
        return front;
    }
    /**
     * 打印实际操作,flag代表不同打印模式
     * return    String
     */
    public static String Printres(int flag,BigDecimal coef,BigInteger    expon){
        String  ans="";
        switch (flag){
            case 1:{
                ans=ans+coef;
                break;
            }
            case 2:{
                ans=ans+"x";
                break;
            }
            case 3:{
                ans=ans+"-x";
                break;
            }
            case 4:{
                ans=ans+coef+"x";
                break;
            }
            case 5:{
                ans=ans+"x^"+expon;
                break;
            }
            case 6:{
                ans=ans+"-x^"+expon;
                break;
            }
            case 7:{
                ans=ans+coef+"x^"+expon;
                break;
            }
        }
        return ans;
    }
    public  static String Printnode(int key,BigDecimal coef,BigInteger expon){
        String  ans="";
        int flag=0;
        if(expon.compareTo(PolyNode.ezero)==0){
            flag=1;
        }else {
            if(expon.compareTo(PolyNode.eone)==0){
                if(coef.compareTo(PolyNode.cone)==0){
                    flag=2;
                }
                if(coef.compareTo(PolyNode.coneno)==0){
                    flag=3;
                }
                if(coef.compareTo(PolyNode.cone)!=0&&coef.compareTo(PolyNode.coneno)!=0){
                    flag=4;
                }
            }else {
                if(coef.compareTo(PolyNode.cone)==0){
                    flag=5;
                }
                if(coef.compareTo(PolyNode.coneno)==0){
                    flag=6;
                }
                if(coef.compareTo(cone)!=0&&coef.compareTo(PolyNode.coneno)!=0){
                    flag=7;
                }
            }
        }
        if(key==0){
            ans=ans+Printres(flag,coef,expon);
        }else {
            if(coef.compareTo(new BigDecimal(0))>0){
                ans=ans+"+";
            }
            ans=ans+Printres(flag,coef,expon);
        }
        return ans;
    }
    public static String PrintPoly(PolyNode   p){
        String  answer="";
        if(p==null){
            return  "0";
        }
        answer=answer+Printnode(0,p.coef,p.expon);
        p=p.link;
        while (p!=null){
            answer=answer+Printnode(1,p.coef,p.expon);
            p=p.link;
        }
        return answer;

    }
    public static PolyNode AddPoly(PolyNode p1,PolyNode p2){
        PolyNode    front,rear;
        front=new PolyNode();
        front.link=null;
        rear=front;
        while((p1!=null)&&(p2!=null)){
            if(p1.expon.compareTo(p2.expon)>0){
                rear=Attach(p1.coef,p1.expon,rear);
                p1=p1.link;
            } else{
                if(p1.expon.compareTo(p2.expon)<0){
                    rear=Attach(p2.coef,p2.expon,rear);
                    p2=p2.link;
                } else{
                    BigDecimal  sum=p1.coef.add(p2.coef);
                    if(sum.compareTo(PolyNode.czero)!=0){
                        rear=Attach(sum,p1.expon,rear);
                    }
                    p1=p1.link;
                    p2=p2.link;
                }
            }
        }
        while (p1!=null){
            rear=Attach(p1.coef,p1.expon,rear);
            p1=p1.link;
        }
        while (p2!=null){
            rear=Attach(p2.coef,p2.expon,rear);
            p2=p2.link;
        }
        front=front.link;
        return front;
    }
    public static PolyNode  Subtraction(PolyNode    p1,PolyNode p2){
        PolyNode    t;
        t=p2;
        PolyNode    pfront,prear;
        pfront=new PolyNode(PolyNode.czero.subtract(t.coef),t.expon);
        prear=pfront;
        t=t.link;
        while (t!=null){
            prear=Attach(PolyNode.czero.subtract(t.coef),t.expon,prear);
            t=t.link;
        }
        t=AddPoly(p1,pfront);
        return t;

    }
    public static PolyNode  MutiPoly(PolyNode  p1,PolyNode  p2){
        PolyNode    front,rear,t1,t2;
        front=new PolyNode();
        rear=front;
        t1=p1;
        t2=p2;
        while (t2!=null){
            BigDecimal  c=t1.coef.multiply(t2.coef);
            BigInteger e=t1.expon.add(t2.expon);
            rear=Attach(c,e,rear);
            t2=t2.link;
        }
        t1=t1.link;
        while (t1!=null){
            t2=p2;
            while (t2!=null){
                BigDecimal  c=t1.coef.multiply(t2.coef);
                BigInteger e=t1.expon.add(t2.expon);
                rear=front;
                while (rear.link!=null&&rear.link.expon.compareTo(e)>0){
                    rear=rear.link;
                }
                if(rear.link!=null&&rear.link.expon.equals(e)){
                    BigDecimal  sum=rear.link.coef.add(c);
                    if(sum.compareTo(PolyNode.czero)!=0){
                        rear.link.coef=sum;
                    }else {
                        rear.link=rear.link.link;
                    }
                    t2=t2.link;
                }else {
                    PolyNode    node=new PolyNode(c,e);
                    node.link=rear.link;
                    rear.link=node;
                    t2=t2.link;
                }
            }
            t1=t1.link;
        }
        front=front.link;
        return front;
    }
    public static PolyNode  Differentiate(PolyNode  p){
        PolyNode    t;
        t=p;
        PolyNode    pr,front;
        front=new PolyNode();
        pr=front;
        while (t!=null){
            PolyNode    res=new PolyNode(t.coef.multiply(new BigDecimal(t.expon.toString())),t.expon.subtract(PolyNode.eone));
            if (res.coef.compareTo(PolyNode.czero)==0){
                res=null;
                t=t.link;
            }else {
                pr.link=res;
                pr=res;
                t=t.link;
            }
        }
        front=front.link;
        return front;
    }
    public static String    Jisuan(PolyNode p,BigDecimal    va){
        PolyNode    t=p;
        BigDecimal   cans=new BigDecimal("0");
        Double  ans=null;
        while (t!=null){
            cans=cans.add(va.pow(t.expon.intValue()));
            t=t.link;
        }
        return cans.toString();
    }

}
class Caculator  extends JFrame{
    public Caculator(){
        Container   c=getContentPane();
        setLayout(new GridLayout(2,1));
        JTextField  jtf=new JTextField("0",40);
        jtf.setHorizontalAlignment(JTextField.RIGHT);
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
        JButton space=new JButton("Space");
        JButton plus=new JButton("+");
        JButton minus=new JButton("-");
        JButton minuti=new JButton("*");
        JButton qd=new JButton("求导");
        JPanel  jp=new JPanel();
        JButton ql=new JButton("清零");
        JButton qz=new JButton("求值");
        JButton equ=new JButton("=");
        JButton tg=new JButton("退格");
        jp.setLayout(new GridLayout(5,4,5,5));
        jp.add(data7);
        jp.add(data8);
        jp.add(data9);
        jp.add(plus);
        jp.add(data4);
        jp.add(data5);
        jp.add(data6);
        jp.add(minus);
        jp.add(data1);
        jp.add(data2);
        jp.add(data3);
        jp.add(minuti);
        jp.add(data0);
        jp.add(space);
        jp.add(point);
        jp.add(qd);
        jp.add(tg);
        jp.add(ql);
        jp.add(qz);
        jp.add(equ);
        c.add(jtf);
        c.add(jp);
        setSize(400,300);
        setTitle("多项式计算");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        data0.addActionListener(new ActionListener() {
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
                    jtf.setText("");
                    jtf.setText(".");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+".");
                }
            }
        });
        ql.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jtf.setText("0");
            }

        });
        space.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText(" ");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+" ");
                }
            }
        });
        plus.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("+");
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
                    jtf.setText("");
                    jtf.setText("-");
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
                    jtf.setText("");
                    jtf.setText("*");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"*");
                }
            }
        });
        qd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PolyNode    p1,p2;
                String  answer;
                if(jtf.getText().equals("0")){
                    jtf.setText("0");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText("");
                    p1=PolyNode.ReadPloy(str);
                    p2=PolyNode.Differentiate(p1);
                    answer=PolyNode.PrintPoly(p2);
                    jtf.setText(answer);


                }
            }
        });
        qz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jtf.getText().equals("0")){
                    jtf.setText("");
                    jtf.setText("&");
                    jtf.requestFocus();
                }
                else{
                    String str = jtf.getText();
                    jtf.setText(str+"&");
                }
            }
        });
        equ.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                PolyNode    p1,p2,p3;
                BigDecimal va;
                String  answer;
                if(jtf.getText().indexOf(" + ")!= -1){
                    String[] s = jtf.getText().split(" [+] ");
                    jtf.setText("");
                    p1=PolyNode.ReadPloy(s[0]);
                    p2=PolyNode.ReadPloy(s[1]);
                    p3=PolyNode.AddPoly(p1,p2);
                    answer=PolyNode.PrintPoly(p3);
                    jtf.setText(answer);
                }
                else if(jtf.getText().indexOf(" - ")!= -1){
                    String[] s = jtf.getText().split(" - ");
                    jtf.setText("");
                    p1=PolyNode.ReadPloy(s[0]);
                    p2=PolyNode.ReadPloy(s[1]);
                    p3=PolyNode.Subtraction(p1,p2);
                    answer=PolyNode.PrintPoly(p3);
                    jtf.setText(answer);
                }

                else if(jtf.getText().indexOf("*")!= -1){
                    String[] s = jtf.getText().split(" [*] ");
                    jtf.setText("");
                    p1=PolyNode.ReadPloy(s[0]);
                    p2=PolyNode.ReadPloy(s[1]);
                    p3=PolyNode.MutiPoly(p1,p2);
                    answer=PolyNode.PrintPoly(p3);
                    jtf.setText(answer);
                }
                else if(jtf.getText().indexOf("&")!=-1){
                    String[]    s=jtf.getText().split(" [&] ");
                    jtf.setText("");
                    p1=PolyNode.ReadPloy(s[0]);
                    va=new BigDecimal(s[1]);
                    answer=PolyNode.Jisuan(p1,va);
                    jtf.setText(answer);
                }
                else{
                    jtf.setText("请选择要进行的运算");
                }
            }
        });
        tg.addActionListener(new ActionListener(){
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
    }
}
public class Main {
    public static void main(String[]  args){
        new Caculator();
    }
}
