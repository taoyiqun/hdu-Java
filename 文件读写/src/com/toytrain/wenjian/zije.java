package com.toytrain.wenjian;

import java.io.*;

public class zije {
    public  static void du(File src){
        InputStream reader=null;
        try {
            reader = new FileInputStream(src);
            byte[]  flush=new byte[1024];
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
                    System.out.println("关闭读取流失败");
                }
            }
        }
    }
    public  static void xie(File    src,String  sc){
        OutputStream  wr=null;
        try {
                wr=new FileOutputStream(src,false) ;
            byte[]  data=sc.getBytes();
            wr.write(data,0,data.length);
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
                System.out.println("关闭输出流失败");
            }
        }
    }
}
