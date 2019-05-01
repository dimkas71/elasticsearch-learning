package ua.selftaught;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	private static final HttpHost HOST = new HttpHost("localhost", 9200);
	
	public static void main(String[] args) {
		ResponseListener listener = new RespListener();
		
		try (RestClient client = RestClient.builder(HOST).build()) {
			client.performRequestAsync(new Request("GET", "hockey/_doc/1"), listener);
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<String> names = Arrays.asList("Salah", "Jordan", "Messi", "Suares", "Piquet", "Van Deik", "Mane");
			IntStream.range(0, names.size())
				.forEach(id -> {
					Request rqst = new Request("PUT", String.format("hockey/_doc/%d",id));
					try {
						rqst.setJsonEntity(mapper.writeValueAsString(new Named(names.get(id))));
						client.performRequest(rqst);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

class RespListener implements ResponseListener {

	@Override
	public void onSuccess(Response response) {
		InputStream content;
		try {
			content = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			Stream<String> lines = reader.lines();
			
			lines.forEach(System.out::println);
			reader.close();
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onFailure(Exception exception) {
		System.out.println(exception.getMessage());
		
	}
	
}


class Named {
	private String name;
	
	public Named() {}
	
	public Named(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Named other = (Named) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Named [name=").append(name).append("]");
		return builder.toString();
	}
	
	
}
