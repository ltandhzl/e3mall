//import com.e3.common.pojo.JedisClientCluster;
//import com.e3.common.pojo.JedisClientPool;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class JedisClientTest {
//
//    @Test
//    public void jedisPoolTest(){
//
//        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
//
//        JedisClientPool jedisClientPool = (JedisClientPool) applicationContext.getBean("jedisClientPool");
//
//        jedisClientPool.set("a","123");
//        String a = jedisClientPool.get("a");
//        System.out.println(a);
//    }
//
//    @Test
//    public void jedisClusterTest(){
//        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
//
//        JedisClientCluster jedisClientCluster = (JedisClientCluster) applicationContext.getBean("jedisClientCluster");
//
//        jedisClientCluster.set("b","111");
//
//        String b = jedisClientCluster.get("b");
//        System.out.println(b);
//    }
//}
