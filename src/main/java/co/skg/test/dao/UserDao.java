package co.skg.test.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.InsertAllRequest;
import com.google.cloud.bigquery.InsertAllResponse;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import co.skg.test.model.User;

public class UserDao {

	protected static BigQuery bigquery;
	protected static final String TABLE = "user";
	protected static final String DATASET = "skg";
	protected static final String FILE = "skg.json";
	protected static final String PROJECT = "true-vista-338900";

	public static boolean createUser(User user) {

		try {
			bigQueryConnect();
			InsertAllRequest insertAllRequest = InsertAllRequest.newBuilder(DATASET, TABLE)
					.addRow(getRow(user.getDocument(), user.getName())).build();
			InsertAllResponse response = bigquery.insertAll(insertAllRequest);

			if (response.hasErrors()) {
				System.out.println("Error inserting: " + user.getName());
				return false;
			}

		} catch (BigQueryException e) {
			System.out.println("Insert operation not performed " + e.toString());
		}

		System.out.println("Row successfully inserted into table");
		return true;
	}

	public static User searchUser(Integer document) {
		
		User user = new User();
		final String SELECT = "SELECT * FROM `true-vista-338900.skg.user` WHERE document = " + document;

		try {
			bigQueryConnect();
			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(SELECT).build();
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
			queryJob = queryJob.waitFor();
			if (queryJob == null) {
				throw new Exception("job no longer exists");
			}
			if (queryJob.getStatus().getError() != null) {
				throw new Exception(queryJob.getStatus().getError().toString());
			}
			TableResult result = queryJob.getQueryResults();
			for (FieldValueList row : result.iterateAll()) {
	            user.setDocument(row.get("document").getNumericValue().intValue());
	            user.setName(row.get("name").getStringValue());
	        }
		} catch (Exception e) {
			System.out.println("Insert operation not performed " + e.toString());
		}

		return user;
	}

	private static void bigQueryConnect() {

		try (InputStream serviceAccountStream = UserDao.class.getClassLoader().getResourceAsStream(FILE)) {
			ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
			bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).setProjectId(PROJECT).build()
					.getService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Map<String, Object> getRow(Integer document, String name) {

		Map<String, Object> rowMap = new HashMap<String, Object>();

		rowMap.put("document", document);
		rowMap.put("name", name);

		return rowMap;
	}
}