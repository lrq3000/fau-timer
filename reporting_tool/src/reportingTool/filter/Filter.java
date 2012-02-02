package reportingTool.filter;

import java.util.ArrayList;

import reportingTool.Secret;

public abstract class Filter {

	abstract public ArrayList<Secret> getFilteredSecrets(ArrayList<Secret> secrets);

}