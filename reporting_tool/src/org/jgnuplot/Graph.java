package org.jgnuplot;

import java.io.File;

/**
 * This class models graphs, defined in terms of functions or data files, that
 * are used by the plot command.
 * 
 * @author Pander
 */
public class Graph {
   private String itsDataFileName;

   private String itsUsing;

   private String itsDataModifiers;

   private String itsFunction;

   private String itsAxes;

   private String itsName;

   private int itsStyle;

   private int itsLineType;

   private int itsPointType;

   // function
   public Graph(String theFunction, int theAxes, String theName, int theStyle,
         int theLineType, int thePointType) {
      itsFunction = theFunction;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = theStyle;
      itsLineType = theLineType;
      itsPointType = thePointType;
   }

   // function
   public Graph(String theFunction, int theAxes, String theName, int theStyle) {
      itsFunction = theFunction;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = theStyle;
      itsLineType = LineType.NOT_SPECIFIED;
      itsPointType = PointType.NOT_SPECIFIED;
   }

   // function
   public Graph(String theFunction, int theAxes, String theName) {
      itsFunction = theFunction;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = Style.NOT_SPECIFIED;
      itsLineType = LineType.NOT_SPECIFIED;
      itsPointType = PointType.NOT_SPECIFIED;
   }

   // function
   public Graph(String theFunction, int theAxes) {
      itsFunction = theFunction;
      itsAxes = Axes.toString(theAxes);
      itsName = null;
      itsStyle = Style.NOT_SPECIFIED;
      itsLineType = LineType.NOT_SPECIFIED;
      itsPointType = PointType.NOT_SPECIFIED;
   }

   // function
   public Graph(String theFunction) {
      itsFunction = theFunction;
      itsAxes = Axes.toString(Axes.NOT_SPECIFIED);
      itsName = null;
      itsStyle = Style.NOT_SPECIFIED;
      itsLineType = LineType.NOT_SPECIFIED;
      itsPointType = PointType.NOT_SPECIFIED;
   }

   // datafile
   public Graph(String theDataFileName, String theUsing,
         String theDataModifiers, int theAxes, String theName, int theStyle,
         int theLineType, int thePointType) {
      if (theDataFileName == null) {
         throw new IllegalArgumentException("DataFileName cannot be null");
      }
      // make path absolute for .plt file which is in temp directory
      if (!theDataFileName.startsWith(File.separator)
            && theDataFileName.charAt(1) != ':') {
         itsDataFileName = (new File("")).getAbsolutePath() + File.separator
               + theDataFileName;
      }
      else {
         itsDataFileName = theDataFileName;
      }
      // escape backslashes
      itsDataFileName = itsDataFileName.replaceAll("\\\\", "\\\\\\\\");
      itsUsing = theUsing;
      itsDataModifiers = theDataModifiers;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = theStyle;
      itsLineType = theLineType;
      itsPointType = thePointType;
   }

   // datafile
   public Graph(String theDataFileName, String theUsing, int theAxes,
         String theName, int theStyle, int theLineType, int thePointType) {
      if (theDataFileName == null) {
         throw new IllegalArgumentException("DataFileName cannot be null");
      }
      // make path absolute for .plt file which is in temp directory
      if (!theDataFileName.startsWith(File.separator)
            && theDataFileName.charAt(1) != ':') {
         itsDataFileName = (new File("")).getAbsolutePath() + File.separator
               + theDataFileName;
      }
      else {
         itsDataFileName = theDataFileName;
      }
      // escape backslashes
      itsDataFileName = itsDataFileName.replaceAll("\\\\", "\\\\\\\\");
      itsUsing = theUsing;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = theStyle;
      itsLineType = theLineType;
      itsPointType = thePointType;
   }

   // datafile
   public Graph(String theDataFileName, String theUsing, int theAxes,
         int theStyle, int theLineType, int thePointType) {
      if (theDataFileName == null) {
         throw new IllegalArgumentException("DataFileName cannot be null");
      }
      // make path absolute for .plt file which is in temp directory
      if (!theDataFileName.startsWith(File.separator)
            && theDataFileName.charAt(1) != ':') {
         itsDataFileName = (new File("")).getAbsolutePath() + File.separator
               + theDataFileName;
      }
      else {
         itsDataFileName = theDataFileName;
      }
      // escape backslashes
      itsDataFileName = itsDataFileName.replaceAll("\\\\", "\\\\\\\\");
      itsUsing = theUsing;
      itsAxes = Axes.toString(theAxes);
      itsName = "";
      itsStyle = theStyle;
      itsLineType = theLineType;
      itsPointType = thePointType;
   }

   // datafile
   public Graph(String theDataFileName, String theUsing, int theAxes,
         String theName, int theStyle) {
      if (theDataFileName == null) {
         throw new IllegalArgumentException("DataFileName cannot be null");
      }
      // make path absolute for .plt file which is in temp directory
      if (!theDataFileName.startsWith(File.separator)
            && theDataFileName.charAt(1) != ':') {
         itsDataFileName = (new File("")).getAbsolutePath() + File.separator
               + theDataFileName;
      }
      else {
         itsDataFileName = theDataFileName;
      }
      // escape backslashes
      itsDataFileName = itsDataFileName.replaceAll("\\\\", "\\\\\\\\");
      itsUsing = theUsing;
      itsAxes = Axes.toString(theAxes);
      itsName = theName;
      itsStyle = theStyle;
      itsLineType = LineType.NOT_SPECIFIED;
      itsPointType = PointType.NOT_SPECIFIED;
   }

   public Graph(Graph theGraph) {
      itsFunction = theGraph.getFunction();
      itsDataFileName = theGraph.getDataFileName();
      itsDataModifiers = theGraph.getDataModifiers();
      itsUsing = theGraph.getUsing();
      itsAxes = theGraph.getAxes();
      itsName = theGraph.getName();
      itsStyle = theGraph.getStyle();
      itsLineType = theGraph.getLineType();
      itsPointType = theGraph.getPointType();
   }

   public final void setFunction(String theFunction) {
      itsFunction = theFunction;
      itsDataFileName = null;
      itsDataModifiers = null;
   }

   public final void setName(String theName) {
      itsName = theName;
   }

   public final String getFunction() {
      return itsFunction;
   }

   public final String getDataFileName() {
      return itsDataFileName;
   }

   public final String getUsing() {
      return itsUsing;
   }

   public final String getDataModifiers() {
      return itsDataModifiers;
   }

   public final String getAxes() {
      return itsAxes;
   }

   public final String getName() {
      return itsName;
   }

   public final int getStyle() {
      return itsStyle;
   }

   public final int getLineType() {
      return itsLineType;
   }

   public final int getPointType() {
      return itsPointType;
   }

   public final boolean isFunction() {
      if (itsFunction == null) {
         return false;
      }
      return true;
   }
}
