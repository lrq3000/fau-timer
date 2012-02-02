package output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This is the interface class of the writing classes.
 * 
 * @author Isabell Schmitt
 */
public abstract class Write {
	abstract public void writeFile() throws IOException, Exception;

	protected void replace(File input, File output,
			HashMap<String, String> replacer) throws FileNotFoundException,
			IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(input)));
		StringBuilder contentBuilder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			contentBuilder.append(line + "\n");
		}
		String content = contentBuilder.toString();

		String key;
		String value;
		for (Entry<String, String> replace : replacer.entrySet()) {
			key = replace.getKey().replaceAll("\\\\", "\\\\\\\\");
			value = replace.getValue().replaceAll("\\\\", "\\\\\\\\");
			content = content.replaceAll("::" + key + ":::", value);
		}

		OutputStreamWriter outputWriter = new OutputStreamWriter(
				new FileOutputStream(output));
		outputWriter.write(content);
		outputWriter.close();
	}
}
