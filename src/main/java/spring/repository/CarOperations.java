package spring.repository;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.mapper.ObjectMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CarOperations {
    private RestHighLevelClient restHighLevelClient;
    private ObjectMapper objectMapper;
    public static List<Boolean> slots;

    public CarOperations(  RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
        slots=new ArrayList<Boolean>();
        for(int i=0;i<100;i++){
            slots.add(true);				//initially all slots are vacant
        }
    }

    public void addCar(Car car){
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
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRegNoByColor(String color) {
        String res="";
        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(color);
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String first = rNo[0];
                res=res+first.substring(1, first.length())+" ";
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return res;
    }

    public int assignSlot() {
        int vac=slots.indexOf(true);	// find first vacant slot
        slots.set(vac,false);
        return vac;
    }

    public String getSlot(int regNo) {
        String res="";
        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(String.valueOf(regNo));
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String[] first = rNo[2].split(":");
                String[] second=rNo[3].split(":");
                res=res+" Floor:"+first[1];
                res=res+" Slot:"+second[1].substring(0,second[1].length()-1);
            }
        } catch (IOException ex) {
            System.out.println("Exception occured");
        }
        return res;
    }

    public String getSlotByColor(String color) {
        String res="";
        try {
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder scb = new SearchSourceBuilder();
            SimpleQueryStringBuilder mcb = QueryBuilders.simpleQueryStringQuery(color);
            scb.query(mcb);
            request.source(scb);
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            List<String> catalogItems = Arrays.stream(searchHits).filter(Objects::nonNull).map(e -> e.getSourceAsString()).collect(Collectors.toList());
            for (String obj : catalogItems) {
                if(obj.isEmpty())
                    continue;
                String[] rNo = obj.split(",");
                String[] first = rNo[2].split(":");
                String[] second=rNo[3].split(":");
                res+=" Floor:"+first[1];
                res+=" Slot:"+second[1].substring(0,second[1].length()-1);
            }
        } catch (IOException ex) {
            System.out.println("Exception occured");
        }
        return res;
    }
}
