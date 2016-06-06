package merge;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.bson.Document;
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
import com.mongodb.client.FindIterable;

import database.DbClient;
import edu.csula.datascience.model.Agriculture;
import edu.csula.datascience.model.Climate;
import edu.csula.datascience.model.ProductImpact;

public class Intergrate {
	
	private static final DbClient connect = new DbClient("agriculture");
	private static final DbClient connect1 = new DbClient("climate");
	
	private final static String indexName = "product2";
    private final static String typeName = "country-production";
    private static final String CSV_SEPARATOR = ",";
    
    private static int batch=0 ;
    
    private static int total_count=0;
    
    public static HashMap<String,HashMap<Integer,Double>> temp_map = new HashMap<String,HashMap<Integer,Double>>();
    
    public static HashMap<String,HashMap<Integer,HashMap<Integer,Double>>> prod_map = new HashMap<String,HashMap<Integer,HashMap<Integer,Double>>>();
	
	private static Node node = nodeBuilder().settings(Settings.builder()
            .put("cluster.name", "genus-antony-es")
            .put("path.home", "elasticsearch-data")).node();
	
	private static Client client = node.client();
        
     // create bulk processor
    private static BulkProcessor bulkProcessor = BulkProcessor.builder(
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
        

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		connect.Retrive("Sno","1");
		        
		
		ArrayList<ProductImpact> product_list = new ArrayList<ProductImpact>();
		
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("products.csv", true));
			
			StringBuffer oneLine = new StringBuffer();
			
			oneLine.append("Sno,Country_Code,Country,Item_Code,Item,Element,Element_Code,Duration,Year,Outcome_Code,Unit,W_XValue,W_XValue_p_1,W_XValue_P_5,W_XValue_P_10,W_XValue_f_1,W_XValue_f_5,W_XValue_f_10,O_XValue,O_XValue_p_1,O_XValue_p_5,O_XValue_p_10,O_XValue_f_1,O_XValue_f_5,O_XValue_f_10,W_per_inc,O_per_inc,Flag"
);
			
			bw.write(oneLine.toString());
            bw.newLine();
        
	        bw.flush();
	        bw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		merge();
		
//		System.out.println(connect1.RetriveTemp("China", 1995));
		
//		System.out.println(connect.Retrive_Out("181","5525","1817",2010));
		
		

	}
	
	
	public static void merge()
	{
		
		
//		System.out.println(iterable.first());    1840001
		
		ArrayList<Agriculture> list = new ArrayList<Agriculture>();
		
		ArrayList<ProductImpact> product_list = new ArrayList<ProductImpact>();
		
		list  = connect.RetriveAgri();
		
		System.out.println("list : "+ list.size());
		
		int count = 0;
		
		for(Agriculture a : list)
		{
			count++;
			
			ProductImpact p = new ProductImpact();
//			Agriculture a;
			String country = a.getCountry();
			int year = Integer.parseInt(a.getYear());
			int item = Integer.parseInt(a.getItem_Code());
			Double value = Double.parseDouble(a.getValue());
			
			
			try 
			{
				
				
				if (prod_map.containsKey(country))
				{
					if(prod_map.get(country).containsKey(year))
					{
						if (prod_map.get(country).get(year).containsKey(item))
						{
//							prod_map.get(country).get(year).get(item);
						}
						else
						{
							prod_map.get(country).get(year).put(item, value);
						}				
					
					}
					else 
					{
						HashMap<Integer,Double> itemMap = new HashMap<Integer,Double>();
						itemMap.put(item, value);
						
						prod_map.get(country).put(year, itemMap);
						
					}
							
				}
				else 
				{
					HashMap<Integer,HashMap<Integer,Double>> yearMap = new HashMap<Integer,HashMap<Integer,Double>>();
					
					HashMap<Integer,Double> itemMap = new HashMap<Integer,Double>();
					
					itemMap.put(item, value);
					
					yearMap.put(year,itemMap);					
					
					prod_map.put(country, yearMap);
				}
			
			p.setSno(a.getSno());
			p.setCountry(a.getCountry());
			p.setCountry_Code(Integer.parseInt(a.getCountry_Code()));
			p.setDuration(a.getYear_Code()+"-01-01");
			p.setElement(a.getElement());
			p.setElement_Code(Integer.parseInt(a.getElement_Code()));
			p.setFlag(a.getFlag());
			p.setItem(a.getItem());
			p.setItem_Code(Integer.parseInt(a.getItem_Code()));			
			p.setYear(Integer.parseInt(a.getYear()));
			p.setOutcome_Code(Integer.parseInt(a.getElement_Code()));
			p.setUnit(a.getUnit());
			
			p.setO_XValue(Double.parseDouble(a.getValue()));
//			p.setO_XValue_f_1(getO_f_1(p));
//			p.setO_XValue_f_5(getO_f_5(p));
//			p.setO_XValue_f_10(getO_f_10(p));
//			p.setO_XValue_p_1(getO_p_1(p));
//			p.setO_XValue_p_5(getO_p_5(p));
//			p.setO_XValue_p_10(getO_p_10(p));
			
//			p.setW_XValue(getTemp(p.getCountry(),p.getYear()));
//			p.setW_XValue_p_1(getTemp(p.getCountry(),p.getYear()-1));
//			p.setW_XValue_P_5(getTemp(p.getCountry(),p.getYear()-5));
//			p.setW_XValue_P_10(getTemp(p.getCountry(),p.getYear()-10));
//			p.setW_XValue_f_1(getTemp(p.getCountry(),p.getYear()+1));
//			p.setW_XValue_f_5(getTemp(p.getCountry(),p.getYear()+5));
//			p.setW_XValue_f_10(getTemp(p.getCountry(),p.getYear()+10));
			
			
			
			product_list.add(p);
			
			}catch(NumberFormatException e)
			{
				System.out.println("Error data : "+p.getSno()+" message : "+e );
				
//				print(p);
//				e.printStackTrace();
			}
			
			
		}
		System.out.println("Iterated : "+product_list.size());
		
		ArrayList<ProductImpact> products = new ArrayList<ProductImpact>();
		
		for (ProductImpact p : product_list)
		{
			
			
			
			p.setO_XValue_f_1(getO_Value(p,1));
			p.setO_XValue_f_5(getO_Value(p,5));
			p.setO_XValue_f_10(getO_Value(p,10));
			p.setO_XValue_p_1(getO_Value(p,-1));
			p.setO_XValue_p_5(getO_Value(p,-5));
			p.setO_XValue_p_10(getO_Value(p,-10));
			
			if (p.getO_XValue()!=null && p.getO_XValue_p_10()!=null)
			{
				p.setO_per_inc((double) Math.round((((p.getO_XValue()-p.getO_XValue_p_10())/p.getO_XValue_p_10())*100)));
			}
			
			
			
			p.setW_XValue(getTemp(p.getCountry(),p.getYear()));
			p.setW_XValue_p_1(getTemp(p.getCountry(),p.getYear()-1));
			p.setW_XValue_P_5(getTemp(p.getCountry(),p.getYear()-5));
			p.setW_XValue_P_10(getTemp(p.getCountry(),p.getYear()-10));
			p.setW_XValue_f_1(getTemp(p.getCountry(),p.getYear()+1));
			p.setW_XValue_f_5(getTemp(p.getCountry(),p.getYear()+5));
			p.setW_XValue_f_10(getTemp(p.getCountry(),p.getYear()+10));
			
			if (p.getW_XValue()!=null && p.getW_XValue_P_10()!=null)
			{
				p.setW_per_inc((double) Math.round((((p.getW_XValue()-p.getW_XValue_P_10())/p.getW_XValue_P_10())*100)));
			}
			total_count++;
			products.add(p);
			
			if(products.size()== 500)
			{
				System.out.println("product_list : "+products.size()+" total_count : "+total_count);
//				ES(products);
				writeCSV(products);
				products.clear();
				count =0;
			}
			
					
		}	
//		ES(products);
		writeCSV(products);
		
		
	}
	
	public static void  writeCSV(ArrayList<ProductImpact> list)
	{
		try
        {
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("products.csv", true), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("products.csv", true));
			
            for (ProductImpact p : list)
            {
                StringBuffer oneLine = new StringBuffer();
//                oneLine.append(p.getSno() <=0 ? "" : p.getSno());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getName().trim().length() == 0? "" : product.getName());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.getCostPrice() < 0 ? "" : product.getCostPrice());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(product.isVatApplicable() ? "Yes" : "No");
                
                oneLine.append(p.getSno());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getCountry_Code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getCountry().replace(',', '-'));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getItem_Code());           
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getItem().replace(',', '-'));    
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getElement().replace(',', '-'));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getElement_Code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getDuration());   
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getYear());     
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getOutcome_Code());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getUnit().replace(',', '-'));    
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_p_1());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_P_5());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_P_10());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_f_1());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_f_5());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_XValue_f_10());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_p_1());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_p_5());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_p_10());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_f_1());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_f_5());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_XValue_f_10());  
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getW_per_inc());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getO_per_inc());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(p.getFlag().replace(',', '-'));
                oneLine.append(CSV_SEPARATOR);

                
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
//		for(ProductImpact p : list)
//		 {	           
//			
//
//		      
//		    }
		
	}
	
	
	public static void  ES(ArrayList<ProductImpact> list){
		
		batch++;
		
		
//		if(batch==2)
		{
//			for (ProductImpact p : list)
//			{
//				print(p);
//			}
		
		
			// Gson library for sending json to elastic search
	        Gson gson = new Gson();

	        for(ProductImpact p : list)
			 {	                

			        bulkProcessor.add(new IndexRequest(indexName, typeName)
			            .source(gson.toJson(p))
			        );
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
//
//	        // Get your facet results
//	        Terms agg1 = sr.getAggregations().get("countryAgg");
//
//	        for (Terms.Bucket bucket: agg1.getBuckets()) {
//	            System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
//	        }
	        
		}
	        System.out.println("Batch : "+batch+ " Done.. " );
	}
	
	
	public static Double getTemp(String Country, int year)
	{
		if(temp_map.containsKey(Country))
		{
			
			if(temp_map.get(Country).containsKey(year))
			{
//				System.out.println("map climate for "+Country+" , "+year );
				return temp_map.get(Country).get(year);
			}
			else
			{
				Double value = connect1.RetriveTemp(Country,year);
//				System.out.println("connect db climate for "+Country+" , "+year );
				temp_map.get(Country).put(year, value);
				return value;
			}
				
		}
		else
		{
			HashMap<Integer,Double> temp = new HashMap<Integer,Double>();
			Double value = connect1.RetriveTemp(Country,year);
//			System.out.println("connect db climate for "+Country+" , "+year );
			temp.put(year, value);
			temp_map.put(Country, temp);
			return value;
			
		}
		
	}
	
	public static Double getO_Value(ProductImpact p,int year)
	{
	int curr_year = p.getYear()+year;
		if (prod_map.containsKey(p.getCountry()))
		{
			if(prod_map.get(p.getCountry()).containsKey(curr_year))
			{
				if (prod_map.get(p.getCountry()).get(curr_year).containsKey(p.getItem_Code()))
				{
//					System.out.println("map agri for "+p.getCountry_Code()+" , "+curr_year+" , "+p.getItem_Code()+" , "+p.getElement_Code() );
					return prod_map.get(p.getCountry()).get(curr_year).get(p.getItem_Code());
				}
				else 
				{
					Double value = connect.Retrive_Val(p.getCountry_Code(),curr_year,p.getItem_Code(),p.getElement_Code());
//					System.out.println("connect db agri for "+p.getCountry_Code()+" , "+curr_year+" , "+p.getItem_Code()+" , "+p.getElement_Code() );
					prod_map.get(p.getCountry()).get(curr_year).put(p.getItem_Code(), value);
					return value;
				}
			}
			else 
			{
				Double value = connect.Retrive_Val(p.getCountry_Code(),curr_year,p.getItem_Code(),p.getElement_Code());
//				System.out.println("connect db agri for "+p.getCountry_Code()+" , "+curr_year+" , "+p.getItem_Code()+" , "+p.getElement_Code() );
				HashMap<Integer,Double> temp = new HashMap<Integer,Double>();
				temp.put(p.getItem_Code(), value);
				prod_map.get(p.getCountry()).put(curr_year, temp);
				return value;
			}
		}
		else
		{
			Double value = connect.Retrive_Val(p.getCountry_Code(),curr_year,p.getItem_Code(),p.getElement_Code());
//			System.out.println("connect db agri for "+p.getCountry_Code()+" , "+curr_year+" , "+p.getItem_Code()+" , "+p.getElement_Code() );
			
			HashMap<Integer,HashMap<Integer,Double>> yearMap = new HashMap<Integer,HashMap<Integer,Double>>();
			
			HashMap<Integer,Double> itemMap = new HashMap<Integer,Double>();
			
			itemMap.put(p.getItem_Code(), value);
			
			yearMap.put(p.getYear()+year,itemMap);					
			
			prod_map.put(p.getCountry(), yearMap);
			
		}
		
		if (year==1)
			return getO_f_1(p);
		else if (year==5)
			return getO_f_5(p);
		else if (year==10)
			return getO_f_10(p);
		else if (year==-1)
			return getO_p_1(p);
		else if (year==-5)
			return getO_p_5(p);
		else
			return getO_p_10(p);
		
	}
	
	public static Double getO_f_1(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()+1);
	}
	
	public static Double getO_f_5(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()+5);
	}
	
	public static Double getO_f_10(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()+10);
	}
	
	public static Double getO_p_1(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()-1);
	}
	
	public static Double getO_p_5(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()-5);
	}
	
	public static Double getO_p_10(ProductImpact p)
	{
		
		return connect.Retrive_Out(p.getCountry_Code(),p.getElement_Code(),p.getItem_Code(),p.getYear()-10);
	}
	
	public static void print(ProductImpact p) 
	{
		System.out.println("");
		System.out.println("************************************************************");
		System.out.println("Country : "+p.getCountry());
		System.out.println("Country_Code : "+p.getCountry_Code());
		System.out.println("Duration_Code : "+ p.getDuration());
		System.out.println("Element : "+p.getElement());
		System.out.println("Element_Code : "+p.getElement_Code());
		System.out.println("Flag : "+ p.getFlag());
		System.out.println("Item : "+p.getItem());
		System.out.println("Item_Code : "+p.getItem_Code());
		System.out.println("Outcome_Code : "+p.getOutcome_Code());
		System.out.println("Unit : "+p.getUnit());
		System.out.println("Year : "+p.getYear());
		System.out.println("O_XValue_f_1 : "+p.getO_XValue_f_1());
		System.out.println("O_XValue_f_5 : "+p.getO_XValue_f_5());
		System.out.println("O_XValue_f_10 : "+p.getO_XValue_f_10());
		System.out.println("O_XValue : "+p.getO_XValue());
		System.out.println("O_XValue_p_1 : "+p.getO_XValue_p_1());
		System.out.println("O_XValue_p_5 : "+p.getO_XValue_p_5());
		System.out.println("O_XValue_p_10 : "+p.getO_XValue_p_10());
		System.out.println("W_XValue_p_1 : "+p.getW_XValue_p_1());
		System.out.println("W_XValue_P_5 : "+p.getW_XValue_P_5());
		System.out.println("W_XValue_P_10 : "+p.getW_XValue_P_10());
		System.out.println("W_XValue : "+p.getW_XValue());		
		System.out.println("W_XValue_f_1 : "+p.getW_XValue_f_1());
		System.out.println("W_XValue_f_5 : "+p.getW_XValue_f_5());
		System.out.println("W_XValue_f_10 : "+p.getW_XValue_f_10());
		System.out.println("getO_per_inc : "+p.getO_per_inc());
		System.out.println("getW_per_inc : "+p.getW_per_inc());
		System.out.println("================================================================");
		System.out.println("");
		
		
		
	}

}
