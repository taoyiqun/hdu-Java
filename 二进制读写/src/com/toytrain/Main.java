package com.toytrain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) throws IOException/*throws IOException */{

        File a = new File("test.txt");
        Scanner input = new Scanner(System.in);

        String c;

        System.out.println("请选择输入模式：");
        System.out.println("输入1，则以字节流输入输出\r\n输入2，则以字符流输入输出\r\n输入3，则以缓冲字节流输入输出\r\n输入4，则以缓冲字符流输入输出" );
        System.out.println("输入-1，则结束程序");
        int i = input.nextInt();
        input.nextLine();
        switch(i)
        {	case 1:
            FileOutputStream write1 = new FileOutputStream(a);
            FileInputStream read1 = new FileInputStream(a);
            c = input.nextLine();
            c=c+("\n");

            while(c.indexOf("-1")==-1) {
                c=c+input.nextLine();
                c=c+("\n");
            }

            c=c.substring(0, c.indexOf("-1"));

            write1.write(c.getBytes());
            System.out.println(new String(read1.readAllBytes()));
            read1.close();
            write1.close();
            break;

            case 2:
                FileWriter write2 = new FileWriter(a);
                FileReader read2 = new FileReader(a);

                c = input.nextLine();
                c += ("\n");

                while(c.indexOf("-1")==-1) {
                    c=c+input.nextLine();
                    c=c+("\n");
                }

                c=c.substring(0, c.indexOf("-1"));


                write2.write(c);
                write2.flush();

                int length = 0;
                char [] list = new char [1024];
                while((length=read2.read(list))!=-1){
                    System.out.println(new String(list,0,length));
                }
                read2.close();
                write2.close();
                break;

            case 3:
                FileInputStream read3 = new FileInputStream(a);
                FileOutputStream write3 = new FileOutputStream(a);
                BufferedInputStream bufread1 = new BufferedInputStream(read3);
                BufferedOutputStream bufwrite1 = new BufferedOutputStream(write3);

                c = input.nextLine();
                c += ("\n");

                while(c.indexOf("-1")==-1) {
                    c=c+input.nextLine();
                    c=c+("\n");
                }

                c=c.substring(0, c.indexOf("-1"));
                bufwrite1.write(c.getBytes());
                bufwrite1.flush();
                System.out.println(new String(bufread1.readAllBytes()));
                read3.close();
                write3.close();
                bufread1.close();
                bufwrite1.close();
                break;

            case 4:
                FileWriter write4 = new FileWriter(a);
                FileReader read4= new FileReader(a);
                BufferedWriter bufwrite2 = new BufferedWriter(write4);
                BufferedReader bufread2 = new BufferedReader(read4);

                c = input.nextLine();
                c += ("\n");

                while(c.indexOf("-1")==-1) {
                    c=c+input.nextLine();
                    c=c+("\n");
                }

                bufwrite2.write(c);
                bufwrite2.flush();
                String temp = new String();
                while((temp=bufread2.readLine())!=null) {
                    System.out.println(temp);
                }

                read4.close();
                write4.close();
                bufread2.close();
                bufwrite2.close();
                break;
            case -1:return;
            default: System.out.println("输入错误,请重新输入");
        }
    }
}