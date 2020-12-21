package com.ftest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.ftest.service.Sum;
import com.ftest.service.SumService;

public class ServiceTests {

	@Test
	public void testSum() {
		String[] data = { "10000000003", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "3", "5", "15", "33", "end" };
		long sum = Arrays.stream(data).filter(TestUtils::isNumeric).mapToLong(Long::parseLong).sum();

		Arrays.stream(data).forEach(d -> {
			new Thread(() -> {
				assertEquals(sum, SumService.getInstance().processInput(d));
				assertEquals(true, Sum.getInstance().isReset());
			}).start();
		});

		// test again to make sure the service is reset
		String[] newData = { "1", "5", "2", "end" };
		long newSum = Arrays.stream(newData).filter(TestUtils::isNumeric).mapToLong(Long::parseLong).sum();

		Arrays.stream(newData).forEach(d -> {
			new Thread(() -> {
				assertEquals(newSum, SumService.getInstance().processInput(d));
				assertEquals(true, Sum.getInstance().isReset());

			}).start();
		});
	}


}
