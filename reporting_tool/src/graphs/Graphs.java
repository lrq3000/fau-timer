package graphs;

import reportingTool.filter.Filter;


/**
 * This class is the abstract class of the plotting classes.
 * 
 * @author Isabell Schmitt
 */

public abstract class Graphs {
	abstract public void plotAllFormats(String path);
	abstract public void plotAllFormats(Filter filter, String path);
}