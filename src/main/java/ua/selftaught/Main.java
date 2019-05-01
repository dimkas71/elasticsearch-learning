package ua.selftaught;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

public class Main {

	public static void main(String[] args) {
		
		try (RestClient client = RestClient.builder(new HttpHost("localhost", 9200)).build()) {
			
			Response response = client.performRequest(new Request("GET", "music/songs/1"));
			
			InputStream content = response.getEntity().getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			Stream<String> lines = reader.lines();
			
			lines.forEach(System.out::println);
			
			
			reader.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
