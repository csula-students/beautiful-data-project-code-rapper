package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.Stream;

import edu.csula.datascience.model.Climate;

public class climateCollector implements Collector<Climate, Climate> {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public climateCollector() {

		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("mydb");
		collection = database.getCollection("climate");
	}

	@Override
	public Collection<Climate> mungee(Collection<Climate> src) {

		return src.stream()
				.filter(data -> data.getCountry() != null && data.getAverageTemperature() != null
						&& data.getAvgTempUnc() != null && data.getCountry() != null)
				.map(Climate::build).collect(Collectors.toList());

	}

	@Override
	public void save(Collection<Climate> climateList) {

		System.out.println("save function");

		// System.out.println("Data size "+climateList.size());

		List<Document> documents = climateList.stream()
				.map(item -> new Document().append("dt", item.getDt())
						.append("AverageTemperature", item.getAverageTemperature())
						.append("AverageTemperatureUncertainity", item.getAvgTempUnc()))
				.collect(Collectors.toList());

		collection.insertMany(documents);

	}

}
