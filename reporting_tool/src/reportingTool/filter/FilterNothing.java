package reportingTool.filter;

import java.util.ArrayList;

import reportingTool.Secret;

public class FilterNothing extends Filter {

	@Override
	public ArrayList<Secret> getFilteredSecrets(ArrayList<Secret> secrets) {
		ArrayList<Secret> result = new ArrayList<Secret>();

		for (Secret secret : secrets) {
			result.add(secret.copy());
		}

		return result;
	}

}
