//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.impl.CloudSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.junit.Test;
//
//public class TestSolrCloud {
//
//    @Test
//    public void testAddDocument()throws Exception{
//
//        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.129:2181,192.168.25.129:2182,192.168.25.129:2183");
//        cloudSolrServer.setDefaultCollection("collection2");
//        SolrInputDocument document=new SolrInputDocument();
//        document.addField("id","solrCloud01");
//        document.addField("item_title","测试商品");
//        document.addField("item_price",1000);
//        cloudSolrServer.add(document);
//
//        cloudSolrServer.commit();
//    }
//
//    @Test
//    public void testQueryDocument() throws Exception{
//        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.129:2181,192.168.25.129:2182,192.168.25.129:2183");
//        cloudSolrServer.setDefaultCollection("collection2");
//        SolrQuery query=new SolrQuery();
//        query.set("q","*:*");
//        QueryResponse response = cloudSolrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        for (SolrDocument document:results){
//            System.out.println(document.get("id"));
//            System.out.println(document.get("item_title"));
//            System.out.println(document.get("item_price"));
//        }
//
//    }
//}
