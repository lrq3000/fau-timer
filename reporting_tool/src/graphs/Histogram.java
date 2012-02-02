package graphs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
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

import reportingTool.Conf;
import reportingTool.Measurement;
import reportingTool.ReportingTool;
import reportingTool.Secret;
import reportingTool.filter.Filter;
import reportingTool.filter.FilterNothing;

/**
 * This class creates with help of gnuplot the histogram.
 * 
 * @author Isabell Schmitt
 */

public class Histogram extends Graphs {

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
	 * This method writes the histogram graphData file of all secrets with all
	 * times.
	 * 
	 * @throws IOException
	 */
	private void writeGraphData() {
		String sep = File.separator;
		final int numBin = Integer.parseInt(Conf.get("numBin"));
		int numIntervals = 0;
		if(Conf.get("scale").equals("q")) {
			numIntervals = ((int)Math.pow(2, numBin) - 1);
		} else if (Conf.get("scale").equals("l")) {
			numIntervals = ((int)((1/9.0)*(Math.pow(10, numBin)-1)));
		} else if (Conf.get("scale").equals("n")) {
			numIntervals = numBin;
		} else {
			logger.warning("No valide input for the value of scale.");	
			System.exit(1);
		}


		// search biggest time of all secrets
		long biggestTime = 0;
		for (Secret secret : secrets) {
			long max = secret.getMax();
			if(max > biggestTime) {
				biggestTime = max;
			}
		}

		// search smallest time of all secrets
		long smallestTime = 0;
		for (Secret secret : secrets) {
			long min = secret.getMin();
			if(min > smallestTime) {
				smallestTime = min;
			}
		}

		final double interval = (biggestTime-smallestTime)/numIntervals; // (maximum - minimum)/(20+19+18+...+2+1)

		long[] list;
		for (int i = 0; i < secrets.size(); i++) {

			list = new long[secrets.get(i).getCountTimes()];


			for (int j = 0; j < secrets.get(i).getCountTimes(); j++) {
				list[j] = secrets.get(i).getTimes().get(j).getTime();
			}

			Arrays.sort(list);
			try {
				File file = new File("reporting_tool_tmp" + sep + "graphData" + sep + "histogramGraphData" + sep + secrets.get(i).getName() + ".txt");
				FileWriter writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);


				BigDecimal probability = new BigDecimal(1).divide(new BigDecimal(list.length), 5, BigDecimal.ROUND_HALF_UP); // 1/list.length

				int intervalPos = 0;
				int elementCounter = 0;
				for (int k = 1; k <= numBin; k++) {
					if(Conf.get("scale").equals("q")) {
						//count elements in interval k
						while(intervalPos < list.length && list[intervalPos] < (smallestTime+((int)Math.pow(2, k) - 1)*interval)) {
							elementCounter++;
							intervalPos++;
						}

						bw.write((smallestTime+((int)Math.pow(2, (k-1))-1)*interval) + "-" + (smallestTime+((int)Math.pow(2, k)-1)*interval-1) + "\t" + probability.multiply(new BigDecimal(elementCounter)) + "\n");				

						elementCounter = 0;
					} else if (Conf.get("scale").equals("l")) {
						//count elements in interval k
						while(intervalPos < list.length && list[intervalPos] < (smallestTime+((int)((1/9.0)*(Math.pow(10, k)-1)))*interval)) {
							elementCounter++;
							intervalPos++;
						}

						bw.write((smallestTime+((int)((1/9.0)*(Math.pow(10, (k-1))-1)))*interval) + "-" + (smallestTime+((int)((1/9.0)*(Math.pow(10, k)-1)))*interval-1) + "\t" + probability.multiply(new BigDecimal(elementCounter)) + "\n");

						elementCounter = 0;
					} else if (Conf.get("scale").equals("n")) {
						//count elements in interval k
						while(intervalPos < list.length && list[intervalPos] < (smallestTime+k*interval)) {
							elementCounter++;
							intervalPos++;
						}

						bw.write((smallestTime+(k-1)*interval) + "-" + (smallestTime+k*interval-1) + "\t" + probability.multiply(new BigDecimal(elementCounter)) + "\n");

						elementCounter = 0;
					} else {
						logger.warning("No valide input for the value of scale.");	
						System.exit(1);
					}


				}

				bw.close();
			} catch(IOException e){
				logger.warning("Error writing file for boxplot.");
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
		aPlot.addExtra("set size 1.0,0.8");
		aPlot.addExtra("set origin 0.0,0.2");
		aPlot.addExtra("set xlabel \"Time\" offset -20,0");
		aPlot.setOutput(Terminal.PDF, path + "histogram.pdf");

		doPlot(aPlot);
	}

	/**
	 * This method starts plotting the png graphic.
	 */
	private void plotToPNG(String path) {
		Plot aPlot = new Plot();

		preparePlot(aPlot);
		aPlot.addExtra("set size 1.0,0.6");
		aPlot.addExtra("set origin 0.0,0.4");
		aPlot.addExtra("set xlabel \"Time\" offset -40,0");
		aPlot.addExtra("set bmargin 0.5");
		aPlot.setOutput(Terminal.PNG, path + "histogram.png", "1200,600");

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

		aPlot.setTitle("Histogram");
		aPlot.setYLabel("Frequency");
		aPlot.setKey("outside box");
		aPlot.setAutoscale();
		aPlot.setDataFileSeparator("\t");

		aPlot.addExtra("set boxwidth 0.9 relative");
		aPlot.addExtra("set style histogram clustered gap 1");
		aPlot.addExtra("set style fill solid border -1");
		aPlot.addExtra("set xtics border in scale 3,1 nomirror rotate by -45 offset character 0, 0, 0 ");

		// push graph per secret
		for (Secret secret : secrets) {
			aPlot.pushGraph(new Graph("reporting_tool_tmp" + sep + "graphData" + sep + "histogramGraphData"
					+ sep + secret.getName() + ".txt", "2:xtic(1)", null, Axes.NOT_SPECIFIED, "Secrets " + 
							secret.getName(), Style.HISTOGRAM, LineType.NOT_SPECIFIED,
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
