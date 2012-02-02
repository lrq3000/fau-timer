package reportingTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An Object of this class calls secrete and saves a lot of time objects. This
 * class contains methods to searches the min, max, median and arithmetic mean
 * of the secrete times.
 * 
 * @author Isabell Schmitt
 */
public class Secret implements Serializable {
	private static final long serialVersionUID = 1609007593870147622L;
	private String name;
	private ArrayList<Time> times = new ArrayList<Time>();

	public Secret() {
	}

	public Secret(String name) {
		this.name = name;
	}

	/**
	 * This method adds a new time to the secrete.
	 * 
	 * @param id
	 * @param time
	 */
	public void addTime(long id, long time) {
		this.times.add(new Time(id, time));
	}

	/**
	 * This method adds a new time to the secrete.
	 * 
	 * @param time
	 */
	public void addTime(Time time) {
		this.times.add(time);
	}

	/**
	 * This is the setter of the secret.
	 * 
	 * @param name
	 *            the secret to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This is the getter of the secret.
	 * 
	 * @return the secret
	 */
	public String getName() {
		return name;
	}

	/**
	 * This is the setter of the arraylist time.
	 * 
	 * @param times
	 *            the times to set
	 */
	public void setTimes(ArrayList<Time> times) {
		this.times = times;
	}

	/**
	 * This is the getter of the arraylist time.
	 * 
	 * @return the times
	 */
	public ArrayList<Time> getTimes() {
		return times;
	}

	/**
	 * This method returns the number of times for one secret.
	 * 
	 */
	public int getCountTimes() {
		return times.size();
	}

	/**
	 * This method returns the maximum of the measurementTuples.
	 * 
	 * @return long Maximum of the measurement
	 */
	public long getMax() {

		long maximum = times.get(0).getTime();
		for (int i = 1; i < times.size(); i++) {
			if (maximum < times.get(i).getTime()) {
				maximum = times.get(i).getTime();
			}
		}
		return maximum;
	}

	/**
	 * This method returns the minimum of the measurementTuples.
	 * 
	 * @return long Minimum of the measurement
	 */
	public long getMin() {

		long minimum = times.get(0).getTime();
		for (int i = 1; i < times.size(); i++) {
			if (minimum > times.get(i).getTime()) {
				minimum = times.get(i).getTime();
			}
		}
		return minimum;
	}

	/**
	 * This method returns the median of the measurementTuples.
	 * 
	 * @return long Median of the measurement
	 */
	public long getMedian() {

		long median = 0;
		long[] array = new long[times.size()];
		for (int i = 0; i < times.size(); i++) {
			array[i] = times.get(i).getTime();
		}
		Arrays.sort(array);
		int middle = array.length / 2;
		if (array.length % 2 == 1) {
			median = array[middle];
		} else {
			median = (long) ((array[middle - 1] + array[middle]) / 2.0);
		}
		return median;
	}

	/**
	 * This method returns the arithmeticMean of the measurementTuples.
	 * 
	 * @return long Arithmetic Mean of the measurement
	 */
	public long getArithmeticMean() {

		long arithmeticMean = 0;
		int counter = 0;
		while (counter < times.size()) {
			arithmeticMean += times.get(counter).getTime();
			counter++;
		}
		arithmeticMean = arithmeticMean / counter;
		return arithmeticMean;
	}

	/**
	 * This method returns the quantile of the measurementTuples.
	 * 
	 * @return long quantile of the measurement
	 */
	public long getLowerQuantile() {

		long[] array = new long[times.size()];
		for (int i = 0; i < times.size(); i++) {
			array[i] = times.get(i).getTime();
		}
		Arrays.sort(array);

		return array[(int) (Math.ceil(array.length * 0.25) - 1)];
	}

	/**
	 * This method returns the quantile of the measurementTuples.
	 * 
	 * @return long quantile of the measurement
	 */
	public long getUpperQuantile() {

		long[] array = new long[times.size()];
		for (int i = 0; i < times.size(); i++) {
			array[i] = times.get(i).getTime();
		}
		Arrays.sort(array);

		return array[(int) (Math.ceil(array.length * 0.75) - 1)];
	}

	@Override
	public String toString() {
		return "Secret [secret=" + name + ", time=" + times.toString() + "]";
	}

	/**
	 * Creates a deep copy
	 * 
	 * @return deep copy
	 */
	public Secret copy() {
		Secret secret = new Secret();

		secret.setName(this.name);
		for (Time time : this.times) {
			secret.addTime(time.copy());
		}

		return secret;
	}

}
