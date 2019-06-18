//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.Writer;
//import java.util.*;
//
//public class FtlTest {
//
//    @Test
//    public void test()throws Exception{
//       //1、创建一个模板文件
//        //2、创建一个Configuration对象
//        Configuration configuration = new Configuration(Configuration.getVersion());
//        //3、设置模板文件保存的目录
//        configuration.setDirectoryForTemplateLoading(new File("D:\\java-idea\\e3\\e3-item-web\\src\\main\\webapp\\ftl"));
//        //4、模板文件的编码格式，一般就是utf-8
//        configuration.setDefaultEncoding("utf-8");
//        //5、加载一个模板文件，创建一个模板对象。
////		Template template = configuration.getTemplate("hello.ftl");
//        Template template = configuration.getTemplate("student.ftl");
//        //6、创建一个数据集。可以是pojo也可以是map。推荐使用map
//        Map data = new HashMap<>();
//        Student student=new Student(1,"tom",20,"北京");
//        data.put("name","张三");
//        data.put("student",student);
//        List<Student> list=new ArrayList<Student>();
//        list.add(new Student(1,"tom",20,"北京"));
//        list.add(new Student(2,"tom1",21,"北京"));
//        list.add(new Student(3,"tom2",22,"北京"));
//        list.add(new Student(4,"tom3",23,"北京"));
//        list.add(new Student(5,"tom4",24,"北京"));
//        data.put("list",list);
//        data.put("date",new Date());
//        data.put("empty",null);
//        Writer out = new FileWriter(new File("D:\\freemarker\\student.html"));
//        //8、生成静态页面
//        template.process(data, out);
//        //9、关闭流
//        out.close();
//    }
//}
