package org.jgnuplot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class models the plot command.
 * 
 * @author Pander
 */
public class Plot {
   private boolean itsPlotable;

   private String itsTerminal;

   private String itsOutputFileName;

   private String itsTitle;

   private String itsGrid;

   private String itsDataFileSeparator;

   private String itsKey;

   private int itsParametric;

   private int itsPolar;

   private String itsSamples;

   private String itsDummy;

   private String itsClip;

   private String itsAutoscale;

   private String itsXLabel;

   private String itsX2Label;

   private String itsYLabel;

   private String itsY2Label;

   private String itsXTics;

   private String itsX2Tics;

   private String itsYTics;

   private String itsY2Tics;

   private String itsRanges;

   private String itsXRange;

   private String itsX2Range;

   private String itsYRange;

   private String itsY2Range;

   private String itsTRange;

   private String itsMXTics;

   private String itsMYTics;

   private String itsXData;

   private String itsYData;

   private String itsTimeFormat;

   private String itsDataFileName;

   private String itsSize;

   private Stack<Graph> itsGraphs;

   private List<String> itsExtra;

   private String itsData;

   private String itsLogscale;

   private boolean itsCommandsOnOneLine;

   private BufferedWriter itsOutputPlotFile;

   private static String itsGnuplotExecutable;

   private static String itsPlotDirectory;

   private boolean itsAutoscaleAfterRanges;

   /*
    * private static int getInt(Vector theVector, int theElement) { return
    * ((Integer)theVector.elementAt(theElement)).intValue(); }
    */

   public final void setAutoscaleAfterRanges() {
      itsAutoscaleAfterRanges = true;
   }

   public final void setTitle(String theTitle) {
      itsTitle = theTitle;
   }

   public final void setDataFileSeparator(String theDataFileSeparator) {
      itsDataFileSeparator = theDataFileSeparator;
   }

   public final void setGrid(String theGrid) {
      itsGrid = theGrid;
   }

   public final void setGrid() {
      itsGrid = "";
   }

   public final void unsetGrid() {
      itsGrid = null;
   }

   public final void setLogscale(String theLogscale) {
      itsLogscale = theLogscale;
   }

   public final void setLogscale() {
      itsLogscale = "";
   }

   public final void unsetLogscale() {
      itsLogscale = null;
   }

   public final void setKey(String theKey) {
      itsKey = theKey;
   }

   public final void unsetKey() {
      itsKey = null;
   }

   public final void setParametric() {
      itsParametric = State.ON;
   }

   public final void unsetParametric() {
      itsParametric = State.OFF;
   }

   public final void setPolar() {
      itsPolar = State.ON;
   }

   public final void unsetPolar() {
      itsPolar = State.OFF;
   }

   public final void setSamples(String theSamples) {
      itsSamples = theSamples;
   }

   public final void setDummy(String theDummy) {
      itsDummy = theDummy;
   }

   public final void setClip(String theClip) {
      itsClip = theClip;
   }

   public final void setClip() {
      itsClip = "";
   }

   // comma separated
   public final void setAutoscale(String theAutoscale) {
      itsAutoscale = theAutoscale;
   }

   public final void setAutoscale() {
      itsAutoscale = "";
   }

   public final void setOutput(String theTerminal, String theOutputFileName) {
      itsTerminal = theTerminal;
      // make path absolute for .plt file which is in temp directory
      if (!theOutputFileName.startsWith(File.separator)
            && theOutputFileName.charAt(1) != ':') {
         itsOutputFileName = (new File("")).getAbsolutePath() + File.separator
               + theOutputFileName;
      }
      itsOutputFileName = duplicateDoubleBackSlashes(theOutputFileName);
   }

   public final void setOutput(String theTerminal, String theOutputFileName,
         String theSize) {
      setOutput(theTerminal, theOutputFileName);
      itsSize = theSize;
   }

   public Plot() {
      clear();
      itsExtra = new Vector<String>();
      itsParametric = State.OFF;
      itsPolar = State.OFF;
   }

   public final void clear() {
      // TODO don't if all needs to be cleared or not, e.g.
      // itsTerminal
      itsPlotable = false;
      itsGraphs = new Stack<Graph>();
      itsTerminal = Terminal.NOT_SPECIFIED;
      itsOutputFileName = null;
      itsCommandsOnOneLine = false;
      itsTitle = null;
   }

   public final void clearExtra() {
      itsExtra = new Vector<String>();
   }

   public final void setCommandsOnOneLine(boolean theValue) {
      itsCommandsOnOneLine = theValue;
   }

   public final void pushGraph(Graph theGraph) {
      itsGraphs.push(new Graph(theGraph)); // TODO precaution for too
      // enthousiastic Graph recycling
      itsDataFileName = null;
      evaluate();
   }

   public final Graph popGraph() {
      Graph aGraph = null;
      if (itsGraphs.size() != 0) {
         aGraph = (Graph) itsGraphs.pop();
         evaluate();
      }
      return aGraph;
   }

   private static String duplicateDoubleBackSlashes(String theString) {
      return theString.replaceAll("\\\\", "\\\\\\\\");
   }

   public final void setDataFileName(String theDataFileName) { // TODO check when
      // to
      // use this
      itsDataFileName = duplicateDoubleBackSlashes(theDataFileName);
      itsGraphs.clear();
      evaluate();
   }

   private void evaluate() {
      if (itsGraphs.size() != 0 || itsDataFileName != null) {
         itsPlotable = true;
      }
      else {
         itsPlotable = false;
      }
   }

   public final void setXLabel(String theXLabel) {
      itsXLabel = theXLabel;
   }

   public final void setData(String theData) {
      itsData = theData;
   }

   public final void setYLabel(String theYLabel) {
      itsYLabel = theYLabel;
   }

   public final void setX2Label(String theX2Label) {
      itsX2Label = theX2Label;
   }

   public final void setY2Label(String theY2Label) {
      itsY2Label = theY2Label;
   }

   public final void setXTics(String theXTics) {
      itsXTics = theXTics;
   }

   public final void setXTics() {
      itsXTics = "";
   }

   public final void unsetXTics() {
      itsXTics = null;
   }

   public final void setYTics(String theYTics) {
      itsYTics = theYTics;
   }

   public final void setYTics() {
      itsYTics = "";
   }

   public final void unsetYTics() {
      itsYTics = null;
   }

   public final void setX2Tics(String theX2Tics) {
      itsX2Tics = theX2Tics;
   }

   public final void setX2Tics() {
      itsX2Tics = "";
   }

   public final void unsetX2Tics() {
      itsX2Tics = null;
   }

   public final void setY2Tics(String theY2Tics) {
      itsY2Tics = theY2Tics;
   }

   public final void setY2Tics() {
      itsY2Tics = "";
   }

   public final void unsetY2Tics() {
      itsY2Tics = null;
   }

   public final void setMXTics(String theMXTics) {
      itsMXTics = theMXTics;
   }

   public final void setMYTics(String theMYTics) {
      itsMYTics = theMYTics;
   }

   public final void setXData(String theXData) {
      itsXData = theXData;
   }

   public final void setYData(String theYData) {
      itsYData = theYData;
   }

   public final void setTimeFormat(String theTimeFormat) {
      itsTimeFormat = theTimeFormat;
   }

   public static void setGnuplotExecutable(String theGnuplotExecutable) {
      itsGnuplotExecutable = theGnuplotExecutable;
   }

   public static void setPlotDirectory(String thePlotDirectory) {
      itsPlotDirectory = thePlotDirectory;
   }

   public final void setRanges(String theRanges) {
      itsRanges = theRanges;
   }

   public final void setXRange(double theBegin, double theEnd) {
      itsXRange = toRange(Double.toString(theBegin), Double.toString(theEnd));
   }

   public final void setXRange(int theBegin, int theEnd) {
      itsXRange = toRange(Integer.toString(theBegin), Integer.toString(theEnd));
   }

   public final void setXRange(String theBegin, String theEnd) {
      itsXRange = toRange(theBegin, theEnd);
   }

   public final void setX2Range(double theBegin, double theEnd) {
      itsX2Range = toRange(Double.toString(theBegin), Double.toString(theEnd));
   }

   public final void setX2Range(int theBegin, int theEnd) {
      itsX2Range = toRange(Integer.toString(theBegin), Integer.toString(theEnd));
   }

   public final void setX2Range(String theBegin, String theEnd) {
      itsX2Range = toRange(theBegin, theEnd);
   }

   public final void setYRange(double theBegin, double theEnd) {
      itsYRange = toRange(Double.toString(theBegin), Double.toString(theEnd));
   }

   public final void setYRange(int theBegin, int theEnd) {
      itsYRange = toRange(Integer.toString(theBegin), Integer.toString(theEnd));
   }

   public final void setYRange(String theBegin, String theEnd) {
      itsYRange = toRange(theBegin, theEnd);
   }

   public final void setY2Range(double theBegin, double theEnd) {
      itsY2Range = toRange(Double.toString(theBegin), Double.toString(theEnd));
   }

   public final void setY2Range(int theBegin, int theEnd) {
      itsY2Range = toRange(Integer.toString(theBegin), Integer.toString(theEnd));
   }

   public final void setY2Range(String theBegin, String theEnd) {
      itsY2Range = toRange(theBegin, theEnd);
   }

   public final void setTRange(String theBegin, String theEnd) {
      itsTRange = toRange(theBegin, theEnd);
   }

   private final String toRange(String theBegin, String theEnd) {
      return "[" + String.valueOf(theBegin) + ":" + String.valueOf(theEnd)
            + "]";
   }

   public final void addExtra(String theExtra) {
      if (!theExtra.trim().startsWith("#") && theExtra.indexOf("=") != -1) {
         String theKey = theExtra.substring(0, theExtra.indexOf("=")).trim();
         Enumeration<String> aEnum = ((Vector) itsExtra).elements();
         while (aEnum.hasMoreElements()) {
            Object aExtra = aEnum.nextElement();
            String aKey = (String) aExtra;
            if (!aKey.trim().startsWith("#") && aKey.indexOf("=") != -1) {
               aKey = aKey.substring(0, aKey.indexOf("=")).trim();
               if (theKey.equals(aKey)) {
                  itsExtra.remove(aExtra);
                  break;
               }
            }
         }
      }
      itsExtra.add(theExtra);
   }

   public final void plot() throws IOException, InterruptedException {
      if (!itsPlotable) {
         throw new IllegalArgumentException(
               "Set data before calling plot method");
      }
      String aFileName = itsPlotDirectory + File.separator
            + String.valueOf(System.currentTimeMillis()) + "-plt.txt";
      /*
       * BufferedWriter aOutputDataFile = new BufferedWriter(new
       * FileWriter(aFileBaseName + ".dat")); Enumeration aDataEnum =
       * itsData.elements(); while (aDataEnum.hasMoreElements()) {
       * aOutputDataFile.write((String)aDataEnum.nextElement() + "\n"); }
       * aOutputDataFile.close();
       */
      itsOutputPlotFile = new BufferedWriter(new FileWriter(aFileName));
      if (itsTitle != null) {
         itsOutputPlotFile.write("set title \"" + itsTitle + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsDataFileSeparator != null) {
         itsOutputPlotFile.write("set datafile separator \""
               + itsDataFileSeparator + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsGrid == null) {
         itsOutputPlotFile.write("unset grid");
      }
      else {
         itsOutputPlotFile.write("set grid");
         if (!itsGrid.equals("")) {
            itsOutputPlotFile.write(" " + itsGrid);
         }
      }
      if (itsCommandsOnOneLine) {
         itsOutputPlotFile.write("; ");
      }
      else {
         itsOutputPlotFile.write("\n");
      }
      if (itsKey == null) {
         itsOutputPlotFile.write("unset key");
      }
      else {
         itsOutputPlotFile.write("set key " + itsKey);
      }
      if (itsCommandsOnOneLine) {
         itsOutputPlotFile.write("; ");
      }
      else {
         itsOutputPlotFile.write("\n");
      }
      switch (itsParametric) {
      case State.ON:
         itsOutputPlotFile.write("set parametric");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
         break;
      case State.OFF:
         itsOutputPlotFile.write("unset parametric");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid parametric state");
      }
      switch (itsPolar) {
      case State.ON:
         itsOutputPlotFile.write("set polar");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
         break;
      case State.OFF:
         itsOutputPlotFile.write("unset polar");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid polar state");
      }
      if (itsSamples != null) {
         itsOutputPlotFile.write("set samples " + itsSamples);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsDummy != null) {
         itsOutputPlotFile.write("set dummy " + itsDummy);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsClip != null) {
         itsOutputPlotFile.write("set clip");
         if (!itsClip.equals("")) {
            itsOutputPlotFile.write(" " + itsClip);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsXLabel != null) {
         itsOutputPlotFile.write("set xlabel \"" + itsXLabel + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsYLabel != null) {
         itsOutputPlotFile.write("set ylabel \"" + itsYLabel + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsX2Label != null) {
         itsOutputPlotFile.write("set x2label \"" + itsX2Label + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsY2Label != null) {
         itsOutputPlotFile.write("set y2label \"" + itsY2Label + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsXTics != null) {
         itsOutputPlotFile.write("set xtics");
         if (!itsXTics.equals("")) {
            itsOutputPlotFile.write(" " + itsXTics);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsYTics != null) {
         itsOutputPlotFile.write("set ytics");
         if (!itsYTics.equals("")) {
            itsOutputPlotFile.write(" " + itsYTics);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsX2Tics != null) {
         itsOutputPlotFile.write("set x2tics");
         if (!itsX2Tics.equals("")) {
            itsOutputPlotFile.write(" " + itsX2Tics);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsY2Tics != null) {
         itsOutputPlotFile.write("set y2tics");
         if (!itsY2Tics.equals("")) {
            itsOutputPlotFile.write(" " + itsY2Tics);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      else {
         if (itsYTics == null && itsY2Label != null) {
            // TODO should be according to all used axes
            itsOutputPlotFile.write("set ytics nomirror");
            if (itsCommandsOnOneLine) {
               itsOutputPlotFile.write("; ");
            }
            else {
               itsOutputPlotFile.write("\n");
            }
            itsOutputPlotFile.write("set y2tics nomirror");
            if (itsCommandsOnOneLine) {
               itsOutputPlotFile.write("; ");
            }
            else {
               itsOutputPlotFile.write("\n");
            }
         }
      }
      if (itsMXTics != null) {
         itsOutputPlotFile.write("set mxtics " + itsMXTics);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsMYTics != null) {
         itsOutputPlotFile.write("set mytics " + itsMYTics);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsTimeFormat != null) {
         itsOutputPlotFile.write("set timefmt '" + itsTimeFormat + "'");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsXData != null) {
         itsOutputPlotFile.write("set xdata " + itsXData);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsYData != null) {
         itsOutputPlotFile.write("set ydata " + itsYData);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsTerminal != Terminal.NOT_SPECIFIED && itsOutputFileName != null) {
         itsOutputPlotFile.write("set terminal " + itsTerminal);
         if (itsSize != null) {
            itsOutputPlotFile.write(" size " + itsSize);
         }
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
         itsOutputPlotFile.write("set output \"" + itsOutputFileName + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");

         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      Enumeration<String> aEnum = ((Vector) itsExtra).elements();
      while (aEnum.hasMoreElements()) {
         itsOutputPlotFile.write((String) aEnum.nextElement());
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsLogscale == null) {
         itsOutputPlotFile.write("unset logscale");
      }
      else {
         itsOutputPlotFile.write("set logscale");
         if (!itsLogscale.equals("")) {
            itsOutputPlotFile.write(" " + itsLogscale);
         }
      }
      if (itsCommandsOnOneLine) {
         itsOutputPlotFile.write("; ");
      }
      else {
         itsOutputPlotFile.write("\n");
      }
      if (!itsAutoscaleAfterRanges) {
         if (itsAutoscale != null) {
            itsOutputPlotFile.write("set autoscale");
            if (!itsAutoscale.equals("")) {
               StringTokenizer aST = new StringTokenizer(itsAutoscale, ",");
               boolean aFirst = true;
               while (aST.hasMoreElements()) {
                  if (aFirst) {
                     aFirst = false;
                  }
                  else {
                     itsOutputPlotFile.write(";set autoscale");
                  }
                  itsOutputPlotFile.write(" " + (String) aST.nextElement());
               }
            }
            if (itsCommandsOnOneLine) {
               itsOutputPlotFile.write("; ");
            }
            else {
               itsOutputPlotFile.write("\n");
            }
         }
      }
      if (itsXRange != null) {
         itsOutputPlotFile.write("set xrange " + itsXRange);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsX2Range != null) {
         itsOutputPlotFile.write("set x2range " + itsX2Range);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsYRange != null) {
         itsOutputPlotFile.write("set yrange " + itsYRange);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");

         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsY2Range != null) {
         itsOutputPlotFile.write("set y2range " + itsY2Range);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsTRange != null) {
         itsOutputPlotFile.write("set trange " + itsTRange);
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      if (itsAutoscaleAfterRanges) {
         if (itsAutoscale != null) {
            itsOutputPlotFile.write("set autoscale");
            if (!itsAutoscale.equals("")) {
               StringTokenizer aST = new StringTokenizer(itsAutoscale, ",");
               boolean aFirst = true;
               while (aST.hasMoreElements()) {
                  if (aFirst) {
                     aFirst = false;
                  }
                  else {
                     itsOutputPlotFile.write(";set autoscale");
                  }
                  itsOutputPlotFile.write(" " + (String) aST.nextElement());
               }
            }
            if (itsCommandsOnOneLine) {
               itsOutputPlotFile.write("; ");
            }
            else {
               itsOutputPlotFile.write("\n");
            }
         }
      }
      itsOutputPlotFile.write("plot");
      if (itsRanges != null) {
         itsOutputPlotFile.write(" " + itsRanges);
      }
      if (itsGraphs.size() == 0) {
         itsOutputPlotFile.write("\t\"" + itsDataFileName + "\"");
         if (itsCommandsOnOneLine) {
            itsOutputPlotFile.write("; ");
         }
         else {
            itsOutputPlotFile.write("\n");
         }
      }
      else {
         for (int i = 0; i < itsGraphs.size(); i++) {
            Graph aGraph = (Graph) itsGraphs.elementAt(i);
            if (itsCommandsOnOneLine) {
               itsOutputPlotFile.write(" ");
            }
            else {
               itsOutputPlotFile.write("\t");
            }
            if (aGraph.isFunction()) {
               // function
               itsOutputPlotFile.write(aGraph.getFunction());
            }
            else {
               // data file
               itsOutputPlotFile.write("\""
                     + duplicateDoubleBackSlashes(aGraph.getDataFileName())
                     + "\"");
               // columns to use
               if (aGraph.getUsing() != null) {
                  itsOutputPlotFile.write(" using " + aGraph.getUsing());
               }
               // data modifiers
               if (aGraph.getDataModifiers() != null) {
                  itsOutputPlotFile.write(" " + aGraph.getDataModifiers());
               }
            }
            // axes to use
            if (aGraph.getAxes() != null) {
               itsOutputPlotFile.write(" axes " + aGraph.getAxes());
            }
            // title / name
            if (aGraph.getName() != null) {
               if (aGraph.getName().equals("")) {
                  itsOutputPlotFile.write(" notitle");
               }
               else {
                  itsOutputPlotFile.write(" title \"" + aGraph.getName() + "\"");
               }
            }
            // style
            if (aGraph.getStyle() != Style.NOT_SPECIFIED) {
               itsOutputPlotFile.write(" with "
                     + Style.toString(aGraph.getStyle()));
               if (aGraph.getLineType() != LineType.NOT_SPECIFIED) {
                  itsOutputPlotFile.write(" lt " + aGraph.getLineType());
               }
               if (aGraph.getPointType() != PointType.NOT_SPECIFIED
                     && (aGraph.getStyle() == Style.POINTS || aGraph.getStyle() == Style.LINESPOINTS)) {
                  itsOutputPlotFile.write(" pt " + aGraph.getPointType());
               }
            }
            if (i != itsGraphs.size() - 1) {
               itsOutputPlotFile.write(",");
            }
            if (!itsCommandsOnOneLine) {
               if (i != itsGraphs.size() - 1) {
                  itsOutputPlotFile.write("\\");
               }
               itsOutputPlotFile.write("\n");
            }
         }
      }
      if (itsCommandsOnOneLine) {
         itsOutputPlotFile.write("; ");
      }
      if (itsTerminal == Terminal.NOT_SPECIFIED && itsOutputFileName == null) {
         itsOutputPlotFile.write("pause -1 \"Hit return to continue\"\n");
      }
      else {
         itsOutputPlotFile.write("\n");
      }
      if (itsData != null) {
         if (!itsCommandsOnOneLine) {
            throw new IllegalArgumentException(
                  "Commands should be on one line when data has been set");
         }
         itsOutputPlotFile.write(itsData);
      }
      itsOutputPlotFile.close();
      if (itsOutputFileName != null) {
         File aFile = new File(itsOutputFileName);
         aFile = new File(aFile.getParent());
         if (!aFile.canWrite()) {
            if (!aFile.mkdirs()) {
               throw new IOException("Could not create directory "
                     + aFile.getParent());
            }
         }
      }
      Process aProcess = Runtime.getRuntime().exec(
            itsGnuplotExecutable + " " + aFileName);
      Thread.currentThread().sleep(1000);
      aProcess.waitFor();
      // TODO Remove temporary -plt.txt file and make it configurable for
      // debug purposes via system properties. Better and preferred is to
      // directly stream it into gnuplot.
   }
}
