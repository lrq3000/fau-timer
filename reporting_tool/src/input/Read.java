package input;

import java.util.ArrayList;
import java.util.logging.Logger;

import reportingTool.ReportingTool;
import reportingTool.Secret;
/**
 * This class is the abstract class of the reading classes.
 * 
 * @author Isabell Schmitt
 */
public abstract class Read {
	Logger logger = null;

	Read(){
		logger = ReportingTool.getLogger();
	}
	abstract public ArrayList<Secret> readFile(String file);

}
