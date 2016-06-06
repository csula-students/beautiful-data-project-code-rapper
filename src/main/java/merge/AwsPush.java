package merge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;
import database.DbClient;

import edu.csula.datascience.model.Agriculture;
import edu.csula.datascience.model.Product;
import edu.csula.datascience.model.ProductImpact;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import com.google.common.collect.Lists;
import io.searchbox.action.BulkableAction;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import java.util.Collection;

public class AwsPush {

	/* AWS */

	static JestClientFactory factory = new JestClientFactory();
	static JestClient client;
	private final static String indexName = "product1";
	private final static String typeName = "country-production";
	static String awsAddress = "http://search-cs594-bigdata-code-rapper-d757tezagbsgf57d4qrpz3isem.us-west-2.es.amazonaws.com/";

	private static int batch = 0;

	private static int total_count = 0;

	public static HashMap<String, HashMap<Integer, Double>> temp_map = new HashMap<String, HashMap<Integer, Double>>();


	public static void ES(ArrayList<Product> list) {

		batch++;

		// Gson library for sending json to elastic search
		Gson gson = new Gson();
	
		
		Collection<BulkableAction> actions = Lists.newArrayList();
		list.stream()
            .forEach(tmp -> {
                actions.add(new Index.Builder(tmp).build());
            });
        Bulk.Builder bulk = new Bulk.Builder()
            .defaultIndex(indexName)
            .defaultType(typeName)
            .addAction(actions);
        try {
			client.execute(bulk.build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     //   System.out.println("We are done! Yay!");

		/**
		 * AGGREGATION
//		 */
//		SearchResponse sr = node.client().prepareSearch(indexName).setTypes(typeName)
//				.setQuery(QueryBuilders.matchAllQuery())
//				.addAggregation(AggregationBuilders.terms("countryAgg").field("Country").size(Integer.MAX_VALUE))
//				.execute().actionGet();
		//
		// // Get your facet results
		// Terms agg1 = sr.getAggregations().get("countryAgg");
		//
		// for (Terms.Bucket bucket: agg1.getBuckets()) {
		// System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
		// }

		System.out.println("Batch : " + batch + " Done.. ");
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		factory.setHttpClientConfig(new HttpClientConfig.Builder(awsAddress).multiThreaded(true).build());
		client = factory.getObject();

		ArrayList<Product> product_list = new ArrayList<Product>();
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();
		int count = 0 ;
		int item = 0;
		
		try {
			
//			FileReader fileReader = new FileReader(CsvFile);			
//			CSVParser parser = new CSVParser(fileReader, csvFileFormat);
			
			Reader in = new FileReader("products.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
			
			System.out.println("Products Start ...");
				
			for (CSVRecord record : records) {
				item++;
				
				try 
				{
					
				
//				Product p = new Product(record.get("Country"),record.get("Item"),record.get("Element"), record.get("Duration"), Integer.parseInt((String)record.get("Year")), record.get("Unit"), record.get("W_XValue").isEmpty() ? null : Double.parseDouble((String) record.get("W_XValue")), Double.parseDouble((String)record.get("W_XValue_p_1")),Double.parseDouble((String)record.get("W_XValue_P_5")), Double.parseDouble((String)record.get("W_XValue_P_10")),Double.parseDouble((String)record.get("W_XValue_f_1")), Double.parseDouble((String)record.get("W_XValue_f_5")),Double.parseDouble((String)record.get("W_XValue_f_10")),  Double.parseDouble((String)record.get("O_XValue")), Double.parseDouble((String)record.get("O_XValue_p_1")), Double.parseDouble((String)record.get("O_XValue_p_5")), Double.parseDouble((String)record.get("O_XValue_p_10")), Double.parseDouble((String)record.get("O_XValue_f_1")),Double.parseDouble((String)record.get("O_XValue_f_5")), Double.parseDouble((String)record.get("O_XValue_f_10")), Double.parseDouble((String)record.get("W_per_inc")), Double.parseDouble((String)record.get("O_per_inc")));
				Product p = new Product(record.get("Country"),record.get("Item"),record.get("Element"), record.get("Duration"),record.get("Year").equals("null") ? null : Integer.parseInt((String)record.get("Year")), record.get("Unit"), record.get("W_XValue").equals("null") ? null : Double.parseDouble((String) record.get("W_XValue")), record.get("W_XValue_p_1").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_p_1")), record.get("W_XValue_P_5").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_P_5")),record.get("W_XValue_P_10").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_P_10")),record.get("W_XValue_f_1").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_f_1")), record.get("W_XValue_f_5").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_f_5")),record.get("W_XValue_f_10").equals("null") ? null : Double.parseDouble((String)record.get("W_XValue_f_10")),record.get("O_XValue").equals("null") ? null :  Double.parseDouble((String)record.get("O_XValue")),record.get("O_XValue_p_1").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_p_1")),record.get("O_XValue_p_5").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_p_5")), record.get("O_XValue_p_10").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_p_10")),record.get("O_XValue_f_1").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_f_1")),record.get("O_XValue_f_5").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_f_5")),record.get("O_XValue_f_10").equals("null") ? null : Double.parseDouble((String)record.get("O_XValue_f_10")), record.get("W_per_inc").equals("null") ? null : Double.parseDouble((String)record.get("W_per_inc")),record.get("O_per_inc").equals("null") ? null : Double.parseDouble((String)record.get("O_per_inc")));
				product_list.add(p);
//				item =;
				}catch(NumberFormatException e)
				{
					System.out.println(e +" record : "+record.getRecordNumber());
				}
				
				total_count++;
				
				if(product_list.size() == 1000 )
				{
					count++;
					
					ES(product_list);
					System.out.println("Product Set : "+count+" total_count : "+total_count);
					product_list.clear();
//					list = new ArrayList<Agriculture>();
					
				}
				
					
				
			}
			ES(product_list);
//			agriculture_list.add(list);
			
			System.out.println("Product end ...");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done Totally " +item);
		



	}

}
