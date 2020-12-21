package com.ftest;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Arrays;


public class Test {
	
	
	public static void main(String[] args) {
		String[] data = { "10000000003", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "end" };
		long sum = Arrays.stream(data).filter(Test::isNumeric).mapToLong(Long::parseLong).sum();
		System.out.println(sum);

		Arrays.stream(data).forEach(d -> {
			if (d.equals("end")) {
				// make a pause before end command
				// hopefully sending of the other requests is completed
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			
			
			new Thread(() -> {
				System.out.println("Expected: " + sum 
						+ " ; returned: " + sendHTTPRequestAndReturnBody(d));

			}).start();
			
		});
	}
	
	private static String sendHTTPRequestAndReturnBody(String withData) {
		HttpClient httpClient = HttpClient.newHttpClient();


		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/"))
				.POST(BodyPublishers.ofString(withData)).build();


		try {
			return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static boolean isNumeric(String str) {
		if (str == null)
			return false;

		try {
			Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}
}
