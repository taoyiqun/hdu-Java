package com.toytrain.wenjian;

import java.io.*;

public class bufferzifu {
    public  static void du(File src){
        BufferedReader reader=null;
        try {
            reader = new BufferedReader(new FileReader(src));
            char[]  flush=new char[1024];
            int len=0;
            while (-1!=(len=reader.read(flush))){
                String  str=new String(flush,0,len);
                System.out.println(str);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("源文件不存在");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("文件读取失败");
        }finally {
            if(null!=reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("关闭流失败");
                }
            }
        }
    }
    public  static void xie(File    src,String  sc){
        BufferedWriter  wr=null;
        try {
            wr=new BufferedWriter(new FileWriter(src));
                wr.write(sc);
            wr.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件不存在");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("文件写出失败");
        }finally {
            try {
                if (null != wr) {
                    wr.close();
                }
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("关闭流失败");
            }
        }
    }

}
