package graphs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.jgnuplot.Axes;
import org.jgnuplot.Graph;
import org.jgnuplot.LineType;
import org.jgnuplot.Plot;
import org.jgnuplot.PointType;
import org.jgnuplot.Style;
import org.jgnuplot.Terminal;

import reportingTool.Measurement;
import reportingTool.ReportingTool;
import reportingTool.Secret;
import reportingTool.filter.Filter;
import reportingTool.filter.FilterNothing;

/**
 * This class creates with help of gnuplot the cdf.
 * 
 * @author Isabell Schmitt
 */

public class CDF extends Graphs {

	Logger logger = ReportingTool.getLogger();
	ArrayList<Secret> secrets = null;

	@Override
	public void plotAllFormats(String path) {
		plotAllFormats(new FilterNothing(), path);
	}

	@Override
	public void plotAllFormats(Filter filter, String path) {
		this.secrets = filter.getFilteredSecrets(Measurement.getInstance().getSecrets());

		writeGraphData();
		plotToPDF(path);
		plotToPNG(path);
	}

	/**
	 * This method writes the cdf graphData file of all secrets with all
	 * times.
	 * 
	 * @throws IOException
	 */
	private void writeGraphData() {
		String sep = File.separator;
		long[] list;

		for (Secret secret : secrets) {

			list = new long[secret.getCountTimes()];

			for (int j = 0; j < secret.getCountTimes(); j++) {
				list[j] = secret.getTimes().get(j).getTime();
			}

			Arrays.sort(list);

			double prob = 0;
			long alteZeit = list[0];
			try {
				File file = new File("reporting_tool_tmp" + sep + "graphData" + sep + "cdfGraphData" + sep + secret.getName() + ".txt");
				FileWriter writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);

				for (int k = 0; k < list.length; k++) {
					if (alteZeit != list[k]) {
						bw.write((k + "\t" + alteZeit + "\t" + prob + "\n"));
						alteZeit = list[k];
					}
					prob = prob + 1.0 / list.length;
				}
				bw.write(list.length + "\t" + alteZeit + "\t" + prob + "\n");
				bw.close();
			} catch (IOException e){
				logger.warning("Error writing file for cdf.");
				System.exit(1);
			} 
		}
	}

	/**
	 * This method starts plotting the pdf graphic.
	 */
	private void plotToPDF(String path) {
		Plot aPlot = new Plot();

		preparePlot(aPlot);

		aPlot.setOutput(Terminal.PDF, path + "CDF.pdf");

		doPlot(aPlot);
	}

	/**
	 * This method starts plotting the png graphic.
	 */
	private void plotToPNG(String path) {
		Plot aPlot = new Plot();

		preparePlot(aPlot);

		aPlot.setOutput(Terminal.PNG, path + "CDF.png", "1200,600");

		doPlot(aPlot);
	}

	/**
	 * This method prepares the plot. Therefore, the method
	 * sets a lot of necessary data.
	 * 
	 * @param aPlot
	 */
	private void preparePlot(Plot aPlot) {
		String sep = File.separator;
		Plot.setGnuplotExecutable(ReportingTool.getGnuplotExecutable());
		Plot.setPlotDirectory(System.getProperty("java.io.tmpdir"));

		aPlot.setTitle("CDF");
		aPlot.setYLabel("probability accumulated");
		aPlot.setXLabel("Time");
		aPlot.setKey("outside box");
		aPlot.setAutoscale();
		aPlot.setDataFileSeparator("\t");
		aPlot.setYRange(0, 1);

		// search biggest time of all secrets
		long biggestTime = 0;
		for (Secret secret : secrets) {
			long max = secret.getMax();
			if (max > biggestTime) {
				biggestTime = max;
			}
		}

		// push graph per secret
		for (Secret secret : secrets) {
			aPlot.pushGraph(new Graph("reporting_tool_tmp" + sep + "graphData" + sep + "cdfGraphData"
					+ sep + secret.getName() + ".txt", "2:3", null,
					Axes.NOT_SPECIFIED, "Secret " + secret.getName(),
					Style.LINESPOINTS, LineType.NOT_SPECIFIED,
					PointType.NOT_SPECIFIED));
		}
	}

	/**
	 * This method does only the plot.
	 * 
	 * @param aPlot
	 */
	private void doPlot(Plot aPlot) {
		try {
			aPlot.plot();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
