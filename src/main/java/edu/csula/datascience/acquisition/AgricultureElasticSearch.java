package edu.csula.datascience.acquisition;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import com.google.gson.Gson;


public class AgricultureElasticSearch {

	private final static String indexName = "agriculture-data";
    private final static String typeName = "country-production";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Node node = nodeBuilder().settings(Settings.builder()
            .put("cluster.name", "anuradha")
            .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();

        /**
         *
         *
         * INSERT data to elastic search
         */

        // as usual process to connect to data source, we will need to set up
        // node and client// to read CSV file from the resource folder
        File csv = new File(
            ClassLoader.getSystemResource("Production_Crops_E_All_Data_(Norm).csv")
                .toURI()
        );

        // create bulk processor
        BulkProcessor bulkProcessor = BulkProcessor.builder(
            client,
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                    System.out.println("Facing error while importing data to elastic search");
                    failure.printStackTrace();
                }
            })
            .setBulkActions(10000)
            .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setConcurrentRequests(1)
            .setBackoffPolicy(
                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();

        // Gson library for sending json to elastic search
        Gson gson = new Gson();

        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                csv,
                Charset.defaultCharset(),
                CSVFormat.EXCEL.withHeader()
            );

            // for each record, we will insert data into Elastic Search
            parser.forEach(record -> {
                // cleaning up dirty data which doesn't have time or temperature
                if (
                    !record.get("Value").isEmpty() &&
                    !record.get("Year").isEmpty() &&
                    !record.get("Year Code").isEmpty()
                ) {
                	Agriculture agri = new Agriculture(                			
                			Integer.valueOf(record.get("Country Code")),
                			record.get("Country"),
                			Integer.valueOf(record.get("Item Code")),
                			record.get("Item"),
                			Integer.valueOf(record.get("Element Code")),
                			record.get("Element"),
                			Integer.valueOf(record.get("Year Code")),
                			Integer.valueOf(record.get("Year")),
                			record.get("Unit"),
                			Float.valueOf(record.get("Value")),
                			record.get("Flag")
                			);
                

                    bulkProcessor.add(new IndexRequest(indexName, typeName)
                        .source(gson.toJson(agri))
                    );
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * AGGREGATION
         */
        SearchResponse sr = node.client().prepareSearch(indexName)
            .setTypes(typeName)
            .setQuery(QueryBuilders.matchAllQuery())
            .addAggregation(
                AggregationBuilders.terms("countryAgg").field("Country")
                    .size(Integer.MAX_VALUE)
            )
            .execute().actionGet();

        // Get your facet results
        Terms agg1 = sr.getAggregations().get("countryAgg");

        for (Terms.Bucket bucket: agg1.getBuckets()) {
            System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
        }
    }

    static class Agriculture {
    	
    	
    	final Integer Country_Code;	
    	final String Country;	
    	final Integer Item_Code;	
    	final String Item;	
    	final Integer Element_Code;
    	final String Element;
    	final Integer Year_Code;	
    	final Integer Year;	
    	final String Unit;	
    	final Float Value;	
    	final String Flag;
    	
    	
		public Agriculture(Integer country_Code, String country, Integer item_Code, String item, Integer element_Code,
				String element, Integer year_Code, Integer year, String unit, Float value, String flag) {
			super();
			
			Country_Code = country_Code;
			Country = country;
			Item_Code = item_Code;
			Item = item;
			Element_Code = element_Code;
			Element = element;
			Year_Code = year_Code;
			Year = year;
			Unit = unit;
			Value = value;
			Flag = flag;
		}
    	
    	
        
        
    }



}
