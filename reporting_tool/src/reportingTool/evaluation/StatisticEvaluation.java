package reportingTool.evaluation;

import java.util.ArrayList;
import java.util.logging.Logger;

import reportingTool.Measurement;
import reportingTool.ReportingTool;
import reportingTool.Secret;

public class StatisticEvaluation {

	Logger logger = null;

	public StatisticEvaluation() {
		logger = ReportingTool.getLogger();
	}


	public void makeEvaluation(Measurement measurement){
		@SuppressWarnings("unchecked")
		ArrayList<Secret> secrets = (ArrayList<Secret>) measurement.getSecrets().clone();
		if(secrets.size() < 2) {
			logger.warning("There are less than two secrets, which does not make much sense here.");
			return;
		}
		Secret current = secrets.remove(0);

		compareSecrets(current, secrets);
	}

	private void compareSecrets(Secret current, ArrayList<Secret> secrets) {

		if(secrets.size() == 0) {
			return;
		}
		Secret next = secrets.remove(0);

		BoxTest bt = new BoxTest(current.getTimes(), next.getTimes());
		boolean ret = bt.test(5, 5);
		logger.info("1: " + current.getName() + " < 2: " + next.getName() + " -->  " + ret);

		bt = new BoxTest(next.getTimes(), current.getTimes());
		ret = bt.test(5, 5);
		logger.info("1: " + next.getName() + " < 2: " + current.getName() + " -->  " + ret);

		/*
		 * Do work here.
		 */

		compareSecrets(next, secrets);
	}
}
