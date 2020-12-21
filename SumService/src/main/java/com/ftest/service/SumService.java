package com.ftest.service;

import static com.ftest.util.Log.LOG;

/**
 * Service class provides the service methods.
 * 
 * @author Aleksei Nazarov
 *
 */
public class SumService {

	private static SumService instance;
	
	/**
	 * Get the reference to the service object.
	 * @return the service
	 */
	public static SumService getInstance() {
		if (SumService.instance == null) {
			SumService.instance = new SumService();
		}
		return SumService.instance;
	}

	/**
	 * Process input data depending on its type and return 
	 * sum.
	 * 
	 * @param data input data
	 * @return sum
	 */
	public long processInput(String data) {
		if (data == null) return processNumber(0);
		if (data.equals("end")) return processComplete();
		
		
		try {
			return processNumber(Long.parseLong(data));
		} catch (NumberFormatException nfe) {
			LOG.debug(nfe);
		}
		return processNumber(0);
	}

	// processing the "end" command
	private static long processComplete() {
		return Sum.getInstance().complete();
	}

	// processing a number, i.e. add it to sum
	private static long processNumber(long n) {
		Sum.getInstance().add(n);
		return Sum.getInstance().getResult();
	}
}
