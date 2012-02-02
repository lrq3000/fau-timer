package org.jgnuplot;

/**
 * This class models all possibe values for axes used by the plot command.
 * 
 * @author Pander
 */
public final class Axes {
   private Axes() {
   }

   public static final int NOT_SPECIFIED = -2;

   public static final int X1Y1 = 1;

   public static final int X1Y2 = 2;

   public static final int X2Y1 = 3;

   public static final int X2Y2 = 4;

   public static String toString(int theAxes) {
      switch (theAxes) {
      case NOT_SPECIFIED:
         return null;
      case X1Y1:
         return "x1y1";
      case X1Y2:
         return "x1y2";
      case X2Y1:
         return "x2y1";
      case X2Y2:
         return "x2y2";
      default:
         throw new IllegalArgumentException("Unknown Axes");
      }
   }
}
