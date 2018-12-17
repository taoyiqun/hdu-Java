import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.toytrain.wenjian.bufferzifu;
import com.toytrain.wenjian.bufferzije;
import com.toytrain.wenjian.zifu;
import com.toytrain.wenjian.zije;

public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
            while (true) {
            System.out.println("请选择io方式：");
            System.out.println("1.字节流");
            System.out.println("2.字符流");
            System.out.println("3.缓冲字节流");
            System.out.println("4.缓冲字符流");
            System.out.println("5.退出");
            int caozuo = in.nextInt();
            File src = new File("C:"+File.separator+"input.txt");
            if (!src.exists()) {
                try {
                    src.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("新建文件失败");
                }
            }
                switch (caozuo) {
                    case 1: {
                        System.out.println("现有文件");
                        zije.du(src);
                        System.out.println("请输入重写的资源,-1表示结束");
                        StringBuilder sc =new StringBuilder();
                        while(true) {
                            String  sac=in.nextLine();
                            if(sac.indexOf("-1")==-1) {
                                sc.append(sac);
                            }
                            else {
                                sac=sac.substring(0, sac.indexOf("-1"));
                                sc.append(sac);
                                break;
                            }
                        }
                        zije.xie(src, sc.toString());
                        break;

                    }
                    case 2: {
                        System.out.println("现有文件");
                        zifu.du(src);
                        System.out.println("请输入重写的资源,-1表示结束");
                        StringBuilder sc =new StringBuilder();
                        while(true) {
                            String  sac=in.nextLine();
                            if(sac.indexOf("-1")==-1) {
                                sc.append(sac);
                            }
                            else {
                                sac=sac.substring(0, sac.indexOf("-1"));
                                sc.append(sac);
                                break;
                            }
                        }
                        zifu.xie(src, sc.toString());
                        break;
                    }
                    case 3: {
                        System.out.println("现有文件");
                        bufferzije.du(src);
                        System.out.println("请输入重写的资源,-1表示结束");
                        StringBuilder sc =new StringBuilder();
                        while(true) {
                            String  sac=in.nextLine();
                            if(sac.indexOf("-1")==-1) {
                                sc.append(sac);
                            }
                            else {
                                sac=sac.substring(0, sac.indexOf("-1"));
                                sc.append(sac);
                                break;
                            }
                        }
                        bufferzije.xie(src, sc.toString());
                        break;
                    }
                    case 4:{
                        System.out.println("现有文件");
                        bufferzifu.du(src);
                        System.out.println("请输入重写的资源,-1表示结束");
                        StringBuilder sc =new StringBuilder();
                        while(true) {
                            String  sac=in.nextLine();
                            if(sac.indexOf("-1")==-1) {
                                sc.append(sac);
                            }
                            else {
                                sac=sac.substring(0, sac.indexOf("-1"));
                                sc.append(sac);
                                break;
                            }
                        }
                        bufferzifu.xie(src, sc.toString());
                        break;

                    }
                    case 5:{
                        System.exit(0);
                    }
                }
            }
        }
    }