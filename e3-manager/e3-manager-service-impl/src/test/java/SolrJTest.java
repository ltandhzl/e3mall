//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.params.SolrParams;
//import org.junit.Test;
//
//import javax.swing.text.Document;
//import java.util.List;
//import java.util.Map;
//
//
//public class SolrJTest {
//
//    @Test
//    public void testAddSolrJ(){
//        try {
//            SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
//            SolrInputDocument document=new SolrInputDocument();
//            document.addField("id","doc1");
//            document.addField("title","测试商品");
//            document.addField("price",1000);
//            solrServer.add(document);
//            solrServer.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testDeleteSolrJ(){
//        try {
//            SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
//            solrServer.deleteById("doc1");
//            solrServer.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testQueryIndex() throws Exception{
//        SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
//        SolrQuery query=new SolrQuery();
//        query.set("q","item_title:手机");
//        QueryResponse response = solrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        System.out.println(results.getNumFound());
//        for (SolrDocument document:results){
//            System.out.println(document.get("id"));
//            System.out.println(document.get("item_title"));
//            System.out.println(document.get("item_price"));
//            System.out.println(document.get("item_image"));
//            System.out.println(document.get("item_sell_point"));
//            System.out.println(document.get("item_category_name"));
//
//        }
//
//    }
//
//    @Test
//    public void testQueryCopmplex() throws Exception{
//        SolrServer solrServer=new HttpSolrServer("http://192.168.25.128:8080/solr");
//        SolrQuery query=new SolrQuery();
//        query.set("q","手机");
//        query.set("df","item_title");
//        query.setStart(0);
//        query.setRows(20);
//        query.setHighlight(true);
//        query.addHighlightField("item_title");
//        query.setHighlightSimplePre("<span style='color:red'>");
//        query.setHighlightSimplePost("</span>");
//        QueryResponse response = solrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
//        System.out.println(results.getNumFound());
//        for (SolrDocument document:results){
//            System.out.println(document.get("id"));
//            Map<String, List<String>> listMap = highlighting.get(document.get("id"));
//            if (listMap!=null){
//                List<String> itemTitle = listMap.get("item_title");
//                if (itemTitle!=null && itemTitle.size()>0){
//                    System.out.println(itemTitle.get(0));
//                }
//            }
//            System.out.println(document.get("item_price"));
//            System.out.println(document.get("item_image"));
//            System.out.println(document.get("item_sell_point"));
//            System.out.println(document.get("item_category_name"));
//
//        }
//
//    }
//}
