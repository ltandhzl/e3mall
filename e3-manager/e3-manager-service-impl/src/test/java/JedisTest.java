//import org.junit.Test;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class JedisTest {
//
//      @Test
//      public void testJedis() throws Exception{
//
//          Jedis jedis=new Jedis("192.168.25.128",6379);
//
//          jedis.set("test","123");
//
//          String test = jedis.get("test");
//          System.out.println(test);
//          jedis.close();
//      }
//
//      @Test
//    public void testJedisPool() throws Exception{
//          JedisPool jedisPool=new JedisPool("192.168.25.128",6379);
//          Jedis jedis = jedisPool.getResource();
//          String test = jedis.get("test");
//          System.out.println(test);
//          jedis.close();
//          jedisPool.close();
//      }
//      @Test
//    public void testJedisCluster() throws Exception{
//
//          Set<HostAndPort> nodes=new HashSet<>();
//          nodes.add(new HostAndPort("192.168.25.128",7001));
//          nodes.add(new HostAndPort("192.168.25.128",7002));
//          nodes.add(new HostAndPort("192.168.25.128",7003));
//          nodes.add(new HostAndPort("192.168.25.128",7004));
//          nodes.add(new HostAndPort("192.168.25.128",7005));
//          nodes.add(new HostAndPort("192.168.25.128",7006));
//
//          JedisCluster jedisCluster=new JedisCluster(nodes);
//          jedisCluster.set("test123","jedis");
//          String test123 = jedisCluster.get("test123");
//          System.out.println(test123);
//          jedisCluster.close();
//      }
//}
