package edu.csula.datascience.acquisition;
import com.google.gson.Gson;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class ClimateElasticSearch {

	private final static String indexName = "climate-data";
    private final static String typeName = "country-temperatures";

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
            ClassLoader.getSystemResource("GlobalLandTemperaturesByCity.csv")
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
                    !record.get("dt").isEmpty() &&
                    !record.get("AverageTemperature").isEmpty() &&
                    !record.get("AverageTemperatureUncertainty").isEmpty()
                ) {
                	Temperature temp = new Temperature (
                			record.get("dt"),
                			Double.valueOf(record.get("AverageTemperature")),
                			Double.valueOf(record.get("AverageTemperatureUncertainty")),
                			record.get("Country")
                			);
                	
                	

                    bulkProcessor.add(new IndexRequest(indexName, typeName)
                        .source(gson.toJson(temp))
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

    static class Temperature {
    	final String dt;
    	final double AverageTemperature;
    	final double AverageTemperatureUncertainty;
    	final String Country;
    	
        

        public Temperature(String date, double averageTemperature, Double avgTempUnc, String country) {
            this.dt = date;
            this.AverageTemperature = averageTemperature;
            this.AverageTemperatureUncertainty = avgTempUnc;
            this.Country = country;
        }
    }

}
