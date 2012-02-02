package reportingTool;

import java.io.Serializable;

/**
 * The object of this class saves the time of a secrete measurement.
 * 
 * @author Isabell Schmitt
 */
public class Time implements Serializable {

	private static final long serialVersionUID = -8481612087956370317L;
	private long time;
	private long id;

	public Time() {
	}

	public Time(long id, long time) {
		this.id = id;
		this.time = time;
	}

	/**
	 * This is the setter of the time.
	 * 
	 * @param time
	 *            the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * This is the getter of the time.
	 * 
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * This is the getter of the id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * This is the setter of the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Time " + id + "[time=" + time + "]";
	}

	/**
	 * Creates a deep copy
	 * 
	 * @return deep copy
	 */
	public Time copy() {
		Time time = new Time();

		time.setId(this.id);
		time.setTime(this.time);

		return time;
	}

}
