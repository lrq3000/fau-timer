package org.jgnuplot;

/**
 * This class models all possible values for the style option for a graph in a
 * plot command.
 * 
 * @author Pander
 */
public final class Style {
   private Style() {
   }

   public static final int NOT_SPECIFIED = -2;

   public static final int LINES = 1;

   public static final int POINTS = 2;

   public static final int LINESPOINTS = 3;

   public static final int IMPULSES = 4;

   public static final int DOTS = 5;

   public static final int STEPS = 6;

   public static final int FSTEPS = 7;

   public static final int HISTEPS = 8;

   public static final int ERRORBARS = 9;

   public static final int XERRORBARS = 10;

   public static final int YERRORBARS = 11;

   public static final int XYERRORBARS = 12;

   public static final int BOXES = 13;

   public static final int FILLEDCURVES = 14;

   public static final int BOXERRORBARS = 15;

   public static final int BOXXYERRORBARS = 16;

   public static final int FINANCEBARS = 17;

   public static final int CANDLESTICKS = 18;

   public static final int VECTORS = 19;

   public static final int PM3D = 20;
   
   public static final int HISTOGRAM = 21;

   public static String toString(int theStyle) {
      switch (theStyle) {
      case NOT_SPECIFIED:
         return null;
      case LINES:
         return "lines";
      case POINTS:
         return "points";
      case LINESPOINTS:
         return "linespoints";
      case IMPULSES:
         return "impulses";
      case DOTS:
         return "dots";
      case STEPS:
         return "steps";
      case FSTEPS:
         return "fsteps";
      case HISTEPS:
         return "histeps";
      case ERRORBARS:
         return "erorbars";
      case XERRORBARS:
         return "xerrorbars";
      case YERRORBARS:
         return "yerrorbars";
      case XYERRORBARS:
         return "xyerrorbars";
      case BOXES:
         return "boxes";
      case FILLEDCURVES:
         return "filledcurves";
      case BOXERRORBARS:
         return "boxerrorbars";
      case BOXXYERRORBARS:
         return "boxxyerrorbars";
      case FINANCEBARS:
         return "";
      case CANDLESTICKS:
         return "candlesticks";
      case VECTORS:
         return "vectors";
      case PM3D:
         return "pm3d";
      case HISTOGRAM:
          return "histogram";
      default:
         throw new IllegalArgumentException("Unknown Style");
      }
   }
}
