//import com.e3.common.pojo.FastDFSClient;
//import org.csource.fastdfs.*;
//import org.junit.Test;
//
//
//public class FastDfsTest {
//    @Test
//    public void testUpload() throws Exception {
////        //创建一个配置文件，内容就是tracker服务器的地址
////        //使用全局对象加载配置文件
////        ClientGlobal.init("D:\\java-idea\\e3\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
////        //创建一个trackerClient对象
////        TrackerClient trackerClient=new TrackerClient();
////        //通过TrackClient获得trackerClient对象
////        TrackerServer trackerServer=trackerClient.getConnection();
////        //创建一个StorageServer的引用，可以是null
////        StorageServer storageServer=null;
////        //创建一个StorageClient，参数需要trackerServer和StorageServer
////        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
////        //使用StorageClient上传文件
////        String[] jpgs = storageClient.upload_file("C:\\Users\\蔡阳阳\\Desktop\\images\\1.jpg", "jpg", null);
////        for (String jpg:jpgs){
////            System.out.println(jpg);
////        }
//
//    }
//    @Test
//    public void testFasDfsClient() throws Exception{
//
////        FastDFSClient fastDFSClient=new FastDFSClient("D:\\java-idea\\e3\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
////        String file = fastDFSClient.uploadFile("C:\\Users\\蔡阳阳\\Desktop\\images\\2.jpg");
////        System.out.println(file);
//
//    }
//}
