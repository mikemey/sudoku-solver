package sudoku.core.internal.test;

import sudoku.core.internal.ASolutionFinder;
import sudoku.core.internal.DataCont;
import sudoku.core.searchsolution.Complex;
import sudoku.core.searchsolution.FirstTry;

public class DataContPerformanceTest {

	public static void main(String[] args) {
		ASolutionFinder first = new FirstTry();
		ASolutionFinder second = new Complex();

		DataCont dc1 = new DataCont(first);
		DataCont dc2 = new DataCont(second);

		//		long[] t1 = testDataCont(dc1);
		//		reportTimes(t1);

		long[] t2 = testDataCont(dc2);
		reportTimes(t2);
	}

	private static final int NUMBER_TEST_COUNT = 1000;

	private static long[] testDataCont(DataCont dc) {
		long[] times = new long[NUMBER_TEST_COUNT];
		long tmpTime;
		for (int i = 0; i < NUMBER_TEST_COUNT; i++) {
			tmpTime = System.currentTimeMillis();
			dc.shuffleData();
			times[i] = System.currentTimeMillis() - tmpTime;
		}
		return times;
	}

	private static void reportTimes(long[] times) {
		// Max - Min - values
		long max = Long.MIN_VALUE;
		long min = Long.MAX_VALUE;
		// Sum for average
		long sum = 0;

		for (int i = 0; i < times.length; i++) {
			if (times[i] > max) {
				max = times[i];
			}
			if (times[i] < min) {
				min = times[i];
			}
			sum += times[i];
		}
		long avg = sum / times.length;
		long avgSum = 0;
		for (int i = 0; i < times.length; i++) {
			avgSum += Math.abs(times[i] - avg);
		}
		long stdDev = avgSum / times.length;

		System.out.println("Anzahl: \t" + times.length);
		System.out.println("Maximum: \t" + max + " ms");
		System.out.println("Minimum: \t" + min + " ms");
		System.out.println("Summe: \t\t" + sum + " ms");
		System.out.println("Durchschn.: \t" + avg + " ms");
		System.out.println("Std.Dev.: \t" + stdDev + " ms");

	}
}
