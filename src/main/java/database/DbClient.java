package database;

public class DbClient extends DbConnect{
	
	public String collection;

	public DbClient(String collection) {

		super("localhost", "27017", "bgdata", collection);
		
		

	}

	

	

}