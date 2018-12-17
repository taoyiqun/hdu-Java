package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Para{
    public static final int   doemstic=0;//国内
    public static final int   internation=1;//国际
    public static final int   all=2;//均可
    public static final int   Ntype=1;//N型
    public static final int   Wtype=2;//W型
    public static final int   ALLtype=3;
    protected int cometype;
    protected int gotype;
    protected int airtype;
    public Para(String  cometype,String gotype){
        this.cometype=panduan(cometype);
        this.gotype=panduan(gotype);
    }
    public Para(){
        cometype=all;
        gotype=all;
        airtype=ALLtype;
    }
    public Para(String  cometype,String gotype,String   airtype){
        this.cometype=panduan(cometype);
        this.gotype=panduan(gotype);
        if(airtype.equals("N")){
            this.airtype=Ntype;
        }else {
            this.airtype=Wtype;
        }
    }
    private int panduan(String  type){
        if(type.equals("I")){
            return internation;
        }else if(type.equals("D")){
            return doemstic;
        }else {
            return all;
        }

    }

    public int getAirtype() {
        return airtype;
    }

    public int getCometype() {
        return cometype;
    }

    public int getGotype() {
        return gotype;
    }
    public Boolean  match(ParaGate paragate){
        if(paragate.getAirtype()==airtype||paragate.getAirtype()==ALLtype){
            if(paragate.getGotype()==2||paragate.getGotype()==gotype){
                if(paragate.getCometype()==2||paragate.getCometype()==cometype){
                    return true;
                }
            }
        }
        return false;
    }
}
class ParaGate  extends Para{
    public static final int   Tfield=0;
    public static final int   Sfield=1;
    private int field;
    public ParaGate(String  field,String  cometype,String gotype,String   airtype){
        super(cometype,gotype,airtype);
        if(field.equals("T")){
            this.field=Tfield;
        }else {
            this.field=Sfield;
        }
    }
    public ParaGate(){
        super();
    }

    public int getField() {
        return field;
    }
}
class   Gate{
    private ParaGate    paraGate;
    private Boolean isOcuppied;
    private Plane   plane;
    private Date    Ocuppying;
    private String  id;
    public Gate(ParaGate   paraGate,String  id){
        this.paraGate= paraGate;
        isOcuppied=false;
        this.id=id;
    }

    public Boolean getOcuppied() {
        return isOcuppied;
    }

    public ParaGate getPara() {
        return paraGate;
    }

    public void setOcuppied() {
        isOcuppied = true;
    }

    public Date getPlaneGotime() {
        return plane.getGotime();
    }
    public void setPlane(Plane  plane){
        this.plane=plane;
    }

    public Plane getPlane() {
        return plane;
    }
    public void addoccupying(Date   start,Date  end){
        if(Ocuppying==null){
            Ocuppying=new Date(end.getTime()-start.getTime()+45*60*1000);
        }else {
            Ocuppying=new Date(end.getTime()-start.getTime()+45*60*1000+Ocuppying.getTime());
        }
    }

    public String getId() {
        return id;
    }

    public Date getOcuppying() {
        return Ocuppying;
    }
}
class Paraplane extends Para{
    public static   final List<String>  Wplanetype=Arrays.asList("332","333","33E","33H","33L","773");
    public static final List<String>    Nplanetype=Arrays.asList("319","320","321","323","325","738","73E","73H","73L");
    public Paraplane(String  cometype,String gotype,String   airtype){
       super(cometype,gotype);
       if(Wplanetype.contains(airtype)){
           super.airtype=Wtype;
       }else {
           super.airtype=Ntype;
       }
    }
}
class   Plane  {
    private Paraplane    para;
    private Date cometime;
    private Date gotime;
    public Plane(Paraplane   para,Date    cometime,Date    gotime){
        this.para=para;
        this.cometime=cometime;
        this.gotime=gotime;;
    }

    public Date getCometime() {
        return cometime;
    }

    public Date getGotime() {
        return gotime;
    }

    public Paraplane getPara() {
        return para;
    }
}
class Comparetor    implements Comparator<Plane>{
    @Override
    public int  compare(Plane   plane1,Plane    plane2){
        return plane1.getCometime().compareTo(plane2.getCometime());
    }
}
public class Main {
    public static void main(String[]    args) throws ParseException {
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<Plane>    planes=new ArrayList<Plane>();
        ArrayList<Gate>    interGate=new ArrayList<Gate>();
        ArrayList<Gate> nogates=new ArrayList<Gate>();
        ArrayList<Gate> isgates=new ArrayList<Gate>();
        SimpleDateFormat    simpleDateFormat=new SimpleDateFormat("dd-MMM-yy'/'HH:mm", Locale.US);
        Date    current=null;
        int count=0;
        int countNtype=0;
        int countWtype=0;
        int countTfield=0;
        int countSfield=0;
        int sucessTfield=0;
        int sucessSfield=0;
        try {
            FileReader fr = new FileReader("C:\\maths\\gates.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList1.add(str);
            }
            fr=new FileReader("C:\\maths\\planes.txt");
            bf=new BufferedReader(fr);
            while ((str=bf.readLine())!=null){
                arrayList2.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int    i=0;i<arrayList1.size();i++){
            String  ags=arrayList1.get(i);
            String  params[]=ags.split("\t");
            ParaGate    paraGate=new ParaGate(params[1],params[3],params[4],params[5]);
            Gate    gate=new Gate(paraGate,params[0]);
            if(paraGate.getField()==ParaGate.Tfield){
                countTfield++;
            }else {
                countSfield++;
            }
            nogates.add(gate);
        }
        for (int i=0;i<arrayList2.size();i++) {
            String ags = arrayList2.get(i);
            String params[] = ags.split("\t");
            Paraplane paraplane = new Paraplane(params[3], params[8], params[4]);
            if(paraplane.getAirtype()==Para.Ntype){
                countNtype++;
            }else {
                countWtype++;
            }
            Date cometime = simpleDateFormat.parse(params[0] + "/" + params[1]);
            Date gotime = simpleDateFormat.parse(params[5] + "/" + params[6]);
            Plane plane = new Plane(paraplane, cometime, gotime);
            planes.add(plane);
        }
        Collections.sort(planes,new Comparetor());
        int plannumber=0;
        current=planes.get(0).getCometime();
        Date    endtime=current;
        Date    start=current;
        int internumber=0;
        int sucessN=0;
        int sucessW=0;
        Plane   nowplane=null;
        Paraplane   paranowplane=null;
        while (current.compareTo(endtime)<=0||plannumber<planes.size()){
            for(int i=0;i<isgates.size();i++){
                Date    isgotime=isgates.get(i).getPlaneGotime();
                Gate    nowisgate=isgates.get(i);
                if(current.compareTo(new Date(isgotime.getTime()+45*60*1000))>=0){
                    isgates.remove(nowisgate);
                    nowisgate.setPlane(null);
                    nogates.add(0,nowisgate);
                    i=i-1;//移出size变化
                }
            }
            for (int    i=0;i<interGate.size();i++){
                Gate    nowintergate=interGate.get(i);
                Plane   nowinterplane=interGate.get(i).getPlane();
                if(nowinterplane!=null){
                    Paraplane   nowinterplanepram=nowinterplane.getPara();
                    if(nowinterplane.getGotime().compareTo(current)<0){
                        internumber++;
                        System.out.println("飞机在临时停机坪起飞"+internumber+"次");
                        nowintergate.setPlane(null);
                    }else {
                        for (int    j=0;j<nogates.size();j++){
                            Gate    nownogate=nogates.get(j);
                            if(nowinterplanepram.match(nownogate.getPara())){
                                nownogate.setOcuppied();
                                nownogate.setPlane(nowinterplane);
                                isgates.add(nownogate);
                                nogates.remove(nownogate);//找到就结束
                                j--;
                                nowintergate.setPlane(null);
                                break;
                            }
                        }
                    }
                }
            }
            //planes遍历结束，current未结束
            if(plannumber<planes.size()){
                do{
                    nowplane=planes.get(plannumber);
                    paranowplane=nowplane.getPara();
                    if(nowplane.getCometime().compareTo(endtime)>0){
                        endtime=nowplane.getCometime();
                    }
                    if(new Date(nowplane.getGotime().getTime()+1000*45*60).compareTo(endtime)>0){
                        endtime=new Date(nowplane.getGotime().getTime()+1000*45*60);
                    }
                    plannumber++;
                    int i=0;
                    int flag=0;
                    for ( i=0;i<nogates.size();i++){
                        Gate    nownogate=nogates.get(i);
                        if(paranowplane.match(nownogate.getPara())){
                            if(paranowplane.getAirtype()==ParaGate.Ntype){
                                sucessN++;
                            }else {
                                sucessW++;
                            }
                            nownogate.addoccupying(nowplane.getCometime(),nowplane.getGotime());
                            nownogate.setOcuppied();
                            nogates.remove(nownogate);
                            i--;
                            nownogate.setPlane(nowplane);
                            isgates.add(nownogate);
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        int k;
                        for (k=0;k<interGate.size();k++){
                            Gate    nowintergate=interGate.get(k);
                            if(nowintergate.getPlane()==null){
                                if(paranowplane.match(nowintergate.getPara())){
                                    nowintergate.setPlane(nowplane);
                                    flag=2;
                                    break;
                                }
                            }
                        }
                        if(flag==0){
                            Gate    newintergate=new Gate(new ParaGate(),"临时");
                            newintergate.setPlane(nowplane);
                            interGate.add(newintergate);
                        }
                    }
                }while (plannumber<planes.size()&&planes.get(plannumber).getCometime().compareTo(current)<=0);
            }
            current=new Date(current.getTime()+5*60*1000);
        }

        for (int    i=0;i<nogates.size();i++){
            if(nogates.get(i).getOcuppied()){
                count++;
                if(nogates.get(i).getPara().getField()==ParaGate.Tfield){
                    sucessTfield++;
                    System.out.println(nogates.get(i).getId()+"号固定登记位占用率"+(double)(nogates.get(i).getOcuppying().getTime())/(endtime.getTime()-start.getTime()));
                }else {
                    sucessSfield++;
                    System.out.println(nogates.get(i).getId()+"号固定登记位占用率"+(double)(nogates.get(i).getOcuppying().getTime())/(endtime.getTime()-start.getTime()));
                }
            }
        }
        System.out.println("最少占用"+count+"个固定停机坪");
        System.out.println("使用了"+interGate.size()+"个临时停机坪");
        System.out.println("共有"+sucessN+"窄型飞机分配固定登机位成功，一共"+countNtype+"窄型飞机");
        System.out.println("共有"+sucessW+"宽型飞机分配固定登机位成功，一共"+countWtype+"宽型飞机");
        System.out.println("共有"+sucessSfield+"个S区域的固定登机位被占用，S区域的固定登记位共"+countSfield+"个");
        System.out.println("共有"+sucessTfield+"个T区域的固定登机位被占用，T区域的固定登记位共"+countTfield+"个");
    }
}
