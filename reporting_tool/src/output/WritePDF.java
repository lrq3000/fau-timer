package output;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import reportingTool.Conf;
import reportingTool.Measurement;
import reportingTool.ReportingTool;

/**
 * This class creates the PDF file of the measurement. It uses the lib jlr.jar.
 * 
 * @author Isabell Schmitt
 */
public class WritePDF extends Write {
	Logger logger = null;

	public WritePDF(){
		logger = ReportingTool.getLogger();
	}

	@Override
	public void writeFile() throws Exception {
		String sep = File.separator;
		File input = new File("templates" + sep + "latex" + sep + "Report.tex");
		File tmpDir = null;
		while (tmpDir == null || tmpDir.exists() == true) {
			tmpDir = new File("reporting_tool_tmp" + sep + "latex"
					+ Long.toString(System.nanoTime()));
		}
		tmpDir.mkdir();
		logger.info("Writing tex files to " + tmpDir.getPath());

		File output = new File(Measurement.getInstance().getOutputFile());
		if (output.exists()) {
			output.delete();
		}

		File makeIndex = new File(Conf.get("makeindexPath"));
		File pdfLatex = new File(Conf.get("pdflatexPath"));

		Measurement measurement = Measurement.getInstance();

		HashMap<String, String> replacer = new HashMap<String, String>();
		replacer.put("name", measurement.getMeasurementName());
		replacer.put("Spalte1", "Secret");
		replacer.put("Spalte2", "Amount Measurement");
		replacer.put("Spalte3", "MIN");
		replacer.put("Spalte4", "MAX");
		replacer.put("Spalte5", "Median");
		replacer.put("Spalte6", "AVG");
		replacer.put("out", "../" + measurement.getOutputFile());

		StringBuilder table = new StringBuilder();
		for (int i = 0; i < measurement.getSecrets().size(); i++) {
			table.append(measurement.getSecrets().get(i).getName().replace("_", "\\_") + "&"
					+ measurement.getSecrets().get(i).getCountTimes() + "&"
					+ measurement.getSecrets().get(i).getMin() + "&"
					+ measurement.getSecrets().get(i).getMax() + "&"
					+ measurement.getSecrets().get(i).getMedian() + "&"
					+ measurement.getSecrets().get(i).getArithmeticMean()
					+ "\\\\\n");

			table.append("\\hline\n");
		}
		replacer.put("tableContent", table.toString());

		File tmpOutput = new File(tmpDir, "Report.tex");
		replace(input, tmpOutput, replacer);

		// Prepare building pdf
		String cmdarray[] = null;
		Process p = null;
		BufferedReader localBufferedReader = null;
		StringBuilder localStringBuilder = null;
		String str = null;


		// makeindex
		cmdarray = new String[2];
		cmdarray[0] = makeIndex.getAbsolutePath();
		cmdarray[1] = tmpOutput.getName();

		logger.info("Calling makeindex: " + cmdarray[0] + " " + cmdarray[1]);

		p = Runtime.getRuntime().exec(cmdarray, null, tmpDir);
		localBufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		localStringBuilder = new StringBuilder();
		try
		{
			while ((str = localBufferedReader.readLine()) != null)
				localStringBuilder.append(str + "\n");
		}
		finally {
			localBufferedReader.close();
		}

		try {
			p.waitFor();				
			if(p.exitValue() != 0) {
				logger.warning("ERROR while calling makeindex: " + p.exitValue());
				throw new Exception("ERROR while calling makeindex: " + p.exitValue());
			}
		} catch (InterruptedException e) {
			p.destroy();
		}


		// makepdf
		cmdarray = new String[3];
		cmdarray[0] = pdfLatex.getAbsolutePath();
		cmdarray[1] = "-interaction=nonstopmode";
		cmdarray[2] = tmpOutput.getName();
		for (int i = 0; i < 2; ++i) {
			// We have to run pdflatex twice

			logger.info("Calling pdflatex (" + i + "/2): " + cmdarray[0] + " " + cmdarray[1] + " " + cmdarray[2]);

			p = Runtime.getRuntime().exec(cmdarray, null, tmpDir);

			// It's important to read the input stream. If input stream is full pdflatex hangs
			localBufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			localStringBuilder = new StringBuilder();
			str = null;
			try
			{
				while ((str = localBufferedReader.readLine()) != null)
					localStringBuilder.append(str + "\n");
			}
			finally {
				localBufferedReader.close();
			}
			try {
				p.waitFor();
				if(p.exitValue() != 0) {
					logger.warning("ERROR while calling pdflatex: " + p.exitValue());
					throw new Exception("ERROR while calling pdflatex: " + p.exitValue());
				}
			} catch (InterruptedException e) {
				p.destroy();
			}
		}

		// move generated pdf
		File src = new File(tmpDir, "Report.pdf");
		File dst = new File(output, measurement.getMeasurementName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pdf");
		src.renameTo(dst);
		logger.info("Moved report to " + dst.getAbsolutePath());
	}



}
