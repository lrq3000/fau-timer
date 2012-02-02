package reportingTool.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import reportingTool.Time;

public class BoxTest {

	ArrayList<Time> times1 = null;
	ArrayList<Time> times2 = null;

	@SuppressWarnings("unused")
	private BoxTest() {
	}

	public BoxTest(ArrayList<Time> times1, ArrayList<Time> times2) {
		this.times1 = sortArraylist(times1);
		this.times2 = sortArraylist(times2);
	}

	/**
	 * 
	 * @param times1
	 * @param times2
	 * @param boxStart
	 * @param boxEnd
	 * @return true if and only if the boxEnd of times1 is below the boxStart of times2
	 */
	public boolean test(int boxStart, int boxEnd) {
		times1 = sortArraylist(times1);
		times2 = sortArraylist(times2);

		long times1End   = times1.get(boxEnd   * times1.size() / 100).getTime();
		long times2Start = times2.get(boxStart * times2.size() / 100).getTime();

		return times1End < times2Start;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Time> sortArraylist(ArrayList<Time> times) {
		Collections.sort(times, new Comparator() {
			@Override
			public int compare(final Object o1, final Object o2) {
				final Time t1 = (Time) o1;
				final Time t2 = (Time) o2;
				return (int) (t1.getTime() - t2.getTime());
			}
		});
		return times;
	}
}
