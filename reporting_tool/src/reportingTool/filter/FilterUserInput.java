package reportingTool.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;

import reportingTool.ReportingTool;
import reportingTool.Secret;
import reportingTool.Time;

public class FilterUserInput extends Filter {

	private double lowerBound = 0;
	private double upperBound = 1;
	Logger logger = ReportingTool.getLogger();

	@Override
	public ArrayList<Secret> getFilteredSecrets(ArrayList<Secret> secrets) {

		if(lowerBound > upperBound) {
			logger.warning("The lowerBound is larger then the upperBound.");
			System.exit(1);
		}

		ArrayList<Secret> result = new ArrayList<Secret>();

		//gets all times of the secrets
		for (int i = 0; i < secrets.size(); i++){
			ArrayList<Long> arrayList = new ArrayList<Long>();

			for(Time time : secrets.get(i).getTimes()) {
				arrayList.add(time.getTime());
			}

			//sorts the list of the times of one secret
			long[] list = new long[arrayList.size()];
			for (int j = 0; j < list.length; j++) {
				list[j] = arrayList.get(j);
			}

			Arrays.sort(list);

			long min = list[(int)(list.length * this.lowerBound)];
			long max = list[(int)(list.length * this.upperBound)];

			// copy the secret
			result.add(secrets.get(i).copy());

			// filter
			for (Iterator<Time> it = result.get(i).getTimes().iterator(); it.hasNext();) {
				Time time = it.next();
				if(time.getTime() < min || time.getTime() > max) {
					it.remove();
				}
			}
		}

		return result;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public void setBound(double lowerBound, double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
}
