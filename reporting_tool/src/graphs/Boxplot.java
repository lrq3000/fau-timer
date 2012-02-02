package graphs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
 * This class creates with help of gnuplot the whisker diagram (boxplot).
 * 
 * @author Isabell Schmitt
 */
public class Boxplot extends Graphs {

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
	 * This method writes the whisker diagram graph data file of the current secret with
	 * all times.
	 * 
	 * @throws IOException
	 */
	private void writeGraphData() {
		String sep = File.separator;
		for (int i = 0; i < secrets.size(); i++) {
			try {
				File file = new File("reporting_tool_tmp" + sep + "graphData" + sep + "whiskerDiagramGraphData" + sep + secrets.get(i).getName() + ".txt");
				FileWriter writer = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(writer);

				bw.write((i + 1) + "\t" + secrets.get(i).getLowerQuantile() + "\t"
						+ secrets.get(i).getMin() + "\t"
						+ secrets.get(i).getUpperQuantile() + "\t"
						+ secrets.get(i).getMax() + "\t"
						+ secrets.get(i).getMedian() + "\n");
				bw.close();
			} catch (IOException e){
				logger.warning("Error writing file for whisker diagram.");
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

		aPlot.setOutput(Terminal.PDF, path + "whiskerDiagram.pdf");

		doPlot(aPlot);
	}

	/**
	 * This method starts plotting the png graphic.
	 */
	private void plotToPNG(String path) {
		Plot aPlot = new Plot();

		preparePlot(aPlot);

		aPlot.setOutput(Terminal.PNG, path + "whiskerDiagram.png", "1200,600");

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

		aPlot.setTitle("Whisker Diagram");
		aPlot.setYLabel("Time");
		aPlot.setXLabel("Secrets");
		aPlot.setKey("outside box");
		aPlot.setAutoscale();
		aPlot.setDataFileSeparator("\t");
		aPlot.setXRange(0, 3);
		aPlot.setXTics("0");
		aPlot.addExtra("set boxwidth 0.8 absolute");
		aPlot.addExtra("set style fill solid 0.6 border lt -1");


		// push graph per secret
		for (Secret secret : secrets) {
			aPlot.pushGraph(new Graph("reporting_tool_tmp" + sep + "graphData" + sep + "whiskerDiagramGraphData"
					+ sep + secret.getName() + ".txt", "1:2:2:4:4", Axes.NOT_SPECIFIED, "Secrets " + 
							secret.getName(), Style.CANDLESTICKS, LineType.NOT_SPECIFIED,
							PointType.NOT_SPECIFIED));

			aPlot.pushGraph(new Graph("reporting_tool_tmp" + sep + "graphData" + sep + "whiskerDiagramGraphData"
					+ sep + secret.getName() + ".txt", "1:6:6:6:6", Axes.NOT_SPECIFIED,
					"", Style.CANDLESTICKS, LineType.SCREEN_BLACK_SOLID_BOLD,
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
