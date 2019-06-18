import com.e3.mapper.TbItemMapper;
import com.e3.pojo.TbItem;
import com.e3.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelperTest {

    @Test
    public void test() {

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
//
//        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
//
//        PageHelper.startPage(1,10);//在执行sql语句之前使用pageHelper进行分页
//        TbItemExample example=new TbItemExample();
//        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
//        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(tbItems);
//        long total = pageInfo.getTotal();
//        System.out.println(total);
//        System.out.println(pageInfo.getPages());
//        System.out.println(pageInfo.getSize());
    }
 }
