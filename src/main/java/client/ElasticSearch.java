package client;


import core.DbBaseClient;
import dto.Car;
import org.apache.http.HttpHost;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ElasticSearch extends DbBaseClient {
    RestHighLevelClient client;
    static Logger log = Logger.getLogger(ElasticSearch.class.getName());
    static int id=1;

    public ElasticSearch(){
        getConnection();
    }

    public void getConnection(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")));
    }


    public void createObject(Car car) {

        XContentBuilder builder = null;
        int floor=(car.getSlot()/20)+1;
        int slot=(car.getSlot()%20)+1;
        try {
            builder = XContentFactory.jsonBuilder().startObject().field("Registration Number", car.getRegistrationNo()).field("Color", car.getColor()).field("Floor",floor).field("Slot",slot).endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexRequest indexRequest = new IndexRequest("parkedcars");
        indexRequest.source(builder);

        try {
            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getRegNoByColor(String color) {

        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(color);
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String first = rNo[0];
                log.info(first.substring(1, first.length()));
            }
        } catch (IOException ex) {
            log.info("Exception occured");
        }
    }

    public void getSlot(int registrationNo) {
        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(String.valueOf(registrationNo));
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            log.info(String.valueOf(catalogItems));
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String[] first = rNo[2].split(":");
                String[] second=rNo[3].split(":");
                log.info("Floor:"+first[1]);
                log.info("Slot:"+second[1].substring(0,second[1].length()-1));
            }
        } catch (IOException ex) {
            log.info("Exception occured");
        }

    }

    public void getSlot(String color) {
        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(color);
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String[] first = rNo[2].split(":");
                String[] second=rNo[3].split(":");
                log.info("Floor:"+first[1]);
                log.info("Slot:"+second[1].substring(0,second[1].length()-1));
            }
        } catch (IOException ex) {
            log.info("Exception occured");
        }
    }

    public void dePark(int number) throws IOException {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder scb = new SearchSourceBuilder();
        SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(String.valueOf(number));
        scb.query(mcb);
        request.source(scb);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        String id=searchHits[0].getId();
        DeleteRequest delRequest=new DeleteRequest("parkedcars",id);
        DeleteRequest deleteRequest = new DeleteRequest(String.valueOf(searchHits[0]));
        deleteRequest.id(id);
        log.info("Car deparked!");

    }

    public void deleteCollection() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest("parkedcars");
        request.setQuery(QueryBuilders.matchAllQuery());
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
        closeConnection();

    }
    private synchronized void closeConnection() throws IOException {
        client.close();
        client = null;
    }



}


