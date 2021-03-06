package dy.utils.libhttp.httpservice.model.movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth : dy
 * Time : 2017/1/22 10:48
 * Email: dymh21342@163.com
 * Description:
 */

public class MovieEntity {
    public String alt;
    public String title="";
    public String descript="";
    public List<Cast> casts;
    public List<String> genres;
    public String id;
    public String year;
    public Images images;
    public String getDescript(){
        String des = "时间："+year;
        des+="\n类型：";
        for(int i=0;i<genres.size();i++){
            des+=genres.get(i)+" ";
        }
        des+= "\n主演：";
        for(int i=0;i<casts.size();i++){
            Cast c = casts.get(i);
            des+=c.name+" ";
        }
        return des;
    }


    public static List<MovieEntity> getList(){
        List<MovieEntity> list = new ArrayList<>();
        MovieEntity item = new MovieEntity();
        item.title ="我国环境监测技术规范规划制定现状分析";
        item.descript = "截至2010年2月,我国现行有效的环境监测技术规范共有80个。“十一 五”规划的环境监测技术规范有38个,“十一五”之前遗留的尚未完成的监测技术规范共有27个。现有规划制定过程中,存在缺乏环境监测技术规范标准化定 义,分类交叉、不严密、重复、可操作性差等问题。新一轮规划制定时,应首要建立环境监测技术规范标准化定义,并进行科学分类,充分考虑行业特点,制定针对 性和可操作性的监测技术规范。";
        list.add(item);


        MovieEntity item1 = new MovieEntity();
        item1.title ="环境空气颗粒物（PM2.5）手工监测方法（重量法）技术规范 ";
        item1.descript = "PM2.5即细颗粒物。\n" +
                "细颗粒物又称细粒、细颗粒。PM2.5：指环境空气中空气动力学当量直径小于等于 2.5 微米的颗粒物，也称细颗粒物。能较长时间悬浮于空气中，其在空气中含量（浓度）越高，就代表空气污染越严重。可吸入颗粒物又称为PM10，指空气动力学当量直径在10微米以下的颗粒物。[1]在职业危害相关技术标准中，将能够较长时间悬浮于空气中的固体颗粒统称为“粉尘”。PM2.5是指大气中直径小于或等于2.5微米的颗粒物，也称为可入肺颗粒物。它的直径还不到人的头发丝粗细的1/20。虽然PM2.5只是地球大气成分中含量很少的组分，但它对空气质量和能见度等有重要的影响。与较粗的大气颗粒物相比，PM2.5粒径小，富含大量的有毒、有害物质且在大气中的停留时间长、输送距离远，因而对人体健康和大气环境质量的影响更大。2013年2月，全国科学技术名词审定委员会将PM2.5的中文名称命名为细颗粒物。\n" +
                "虽然细颗粒物只是地球大气成分中含量很少的组分，但它对空气质量和能见度等有重要的影响。细颗粒物粒径小，比表面积大，活性强，易附带有毒、有害物质（例如，重金属、微生物等），且在大气中的停留时间长、输送距离远，因而对人体健康和大气环境质量的影响更大。\n" +
                "细颗粒物的化学成分主要包括有机碳(OC)、元素碳(EC)、硝酸盐(NO3-)、硫酸盐(SO42-)、铵盐(NH4+)、钠盐(Na+)等。\n" +
                "大气中的含碳粒子是由有机碳(OC)和吸光的元素碳(EC)组成，元素碳的化学结构类似于不纯的石墨，有机碳是细颗粒物中含量最高的组分。";
        list.add(item1);

        MovieEntity item2 = new MovieEntity();
        item2.title ="室内环境有关甲醛的国家标准";
        item2.descript = "  目前国家发布的室内环境有关的甲醛的检测标准主要有：\n" +
                "    1.根据中华人民共和国国家标准《居室空气中甲醛的卫生标准》规定：室内空气中甲醛的最高容许浓度为0.08毫克/立方米。\n" +
                "    2.根据中华人民共和国国家标准《实木复合地板》规定：A类实木复合地板甲醛释放量小于和等于9毫克/100克；B类实木复合地板甲醛释放量等于9毫克—40毫克/100克。\n" +
                "    3.根据《国家环境标志产品技术要求——人造木质板材》规定：人造板材中甲醛释放量应该小于0.20毫克/立方米；木地板中甲醛释放量应该小于0.12毫克/立方米。\n" +
                "\\\n" +
                "    4.根据国家家具标准GB5296.2004规定：如果甲醛释放量大于1.5毫克/升的规定标准，有关厂家将被处以销售额50% 至3倍的罚款。还将受到涉嫌欺诈的处罚。\n" +
                "    5.根据《民用建筑工程室内环境污染控制规范》GB50325-2010第6.0.4项规定，民用建筑工程验收时，Ⅰ类民用建筑工程室内环境污染物甲醛浓度限量为0.6毫克/立方米；Ⅱ类民用建筑工程甲醛浓度限量为0.1毫克/立方米。\n" +
                "    另外，很多家具、地板有味道，其实是漆的味道，有时即使家具环保，但是如果选择的漆不好，也会有过多的甲醛。急性甲醛中毒为接触高浓度甲醛蒸气引起的以眼、呼吸系统损害为主的全身性疾病。";
        list.add(item2);


        return list;

    }

}
