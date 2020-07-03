package alexgit95.batch.googleLocationsExtractor.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import alexgit95.batch.googleLocationsExtractor.model.LocationsOutput;

@Component
public class DaoServicesImpl implements DaoServices {

	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	

	public DaoServicesImpl() {
		super();
		client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_3).build();
		mapper = new DynamoDBMapper(client);
	}
	@Override
	public void save(LocationsOutput toCreate) {
		mapper.save(toCreate);
	}
	@Override
	public List<LocationsOutput> getAll() {
		return mapper.scan(LocationsOutput.class, new DynamoDBScanExpression());
		
	}

}
