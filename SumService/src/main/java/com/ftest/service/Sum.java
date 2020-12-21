package com.ftest.service;

import static com.ftest.util.Log.LOG;

/**
 * The class represent a sum of integers. A global instance is shared for all 
 * threads that provided input for calculation of the sum.
 * 
 * @author Aleksei Nazarov
 *
 */
public class Sum {
	// sum
	private long value = 0l;
	// summing is complete, release the result
	private boolean completed = false;
	
	// number of threads waiting for the result
	private static int numberOfWaitingThreads = 0;
	private static Sum instance;
	
	/**
	 * Singleton implementation. Returns global reference to the Sum object 
	 * @return the singleton sum object
	 */
	public static Sum getInstance() {
		if (Sum.instance == null) {
			Sum.instance = new Sum();
		}
		return Sum.instance;
	}
	
	/**
	 * Add a number to the current value
	 * @param i the number to add
	 */
	public synchronized void add(long number) {
		LOG.info("Waiting. Adding number: " + number);
		
		while(this.completed) { // if completed -> wait, no numbers can be added.
			try {
				wait();
			} catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
    			LOG.debug(e);
			}
		}
		LOG.info("Released. Adding number: " + number);
		
		this.value += number; 
		
	}
	
	/**
	 * Called when summing is completed and results must be returned to the all waiting threads
	 * 
	 * @return
	 */
	public synchronized long complete() {
		// set to completed and wait when result is requested
		// after result is requested the data is reset
		this.completed = true; // release results
		
		// release threads waiting for the result
		notifyAll();
		
		
		return this.value;
	}
	
	/**
	 * The sum object is reset and ready for summing again.
	 * 
	 */
	public synchronized void reset() {
		
		LOG.info("RESET. Number of waiting threads: " + numberOfWaitingThreads);
		this.value = 0l;
		this.completed = false;
		
		// summing can be started again
		notifyAll();
	}
	
	/**
	 * Return the sum
	 * 
	 * @return sum
	 */
	public synchronized long getResult() {
		numberOfWaitingThreads++;
		LOG.info("Waiting. Number of waiting threads: " + numberOfWaitingThreads);
		
		while (!completed) { // not completed yet, do not return the result
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt(); 
    			LOG.debug(e);
            }
        }
		
		numberOfWaitingThreads--;
		LOG.info("Released. Number of waiting threads: " + numberOfWaitingThreads);
		
		// store for return, it can be reset
		long retValue = this.value;
		
		if (numberOfWaitingThreads == 0) { // last waiting thread received it's result, data can be reset
			this.reset();
		}
		
		return retValue;
	}

	public boolean isReset() {
		return !this.completed && this.value == 0;
	}
	
	
	
	
}
