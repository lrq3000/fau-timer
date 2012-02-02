package org.jgnuplot;

/**
 * This class models all possible values for the point style option for a graph
 * in a plot command.
 * 
 * @author Pander
 */
public final class PointType {
   private PointType() {
   }

   public static final int NOT_SPECIFIED = -2;

   public static final int SCREEN_NONE = -1;

   public static final int SCREEN_DOT = 0;

   public static final int SCREEN_PLUS = 1;

   public static final int SCREEN_X = 2;

   public static final int SCREEN_STAR = 3;

   public static final int SCREEN_SQUARE_DOT = 4;

   public static final int SCREEN_SQUARE_FILLED = 5;

   public static final int SCREEN_CIRCLE_DOT = 6;

   public static final int SCREEN_CIRCLE_FILLED = 7;

   public static final int SCREEN_TRIANGLE_UP_DOT = 8;

   public static final int SCREEN_TRIANGLE_UP_FILLED = 9;

   public static final int SCREEN_TRIANGLE_DOWN_DOT = 10;

   public static final int SCREEN_TRIANGLE_DOWN_FILLED = 11;

   public static final int SCREEN_DIAMOND_DOT = 12;

   public static final int SCREEN_DIAMOND_FILLED = 13;

   public static final int POSTSCRIPT_DOT = -1;

   public static final int POSTSCRIPT_DOT_SMALL = 0;

   public static final int POSTSCRIPT_PLUS = 1;

   public static final int POSTSCRIPT_X = 2;

   public static final int POSTSCRIPT_STAR = 3;

   public static final int POSTSCRIPT_SQUARE_DOT = 4;

   public static final int POSTSCRIPT_SQUARE_FILLED = 5;

   public static final int POSTSCRIPT_CIRCLE_DOT = 6;

   public static final int POSTSCRIPT_CIRCLE_FILLED = 7;

   public static final int POSTSCRIPT_TRIANGLE_UP_DOT = 8;

   public static final int POSTSCRIPT_TRIANGLE_UP_FILLED = 9;

   public static final int POSTSCRIPT_TRIANGLE_DOWN_DOT = 10;

   public static final int POSTSCRIPT_TRIANGLE_DOWN_FILLED = 11;

   public static final int POSTSCRIPT_DIAMOND_DOT = 12;

   public static final int POSTSCRIPT_DIAMOND_FILLED = 13;

   public static final int POSTSCRIPT_PENTAGON_DOT = 14;

   public static final int POSTSCRIPT_PENTAGON_FILLED = 15;

   // TODO POSTSCRIPT 16 - 74

   /**
    * This method converts screen point type to PostScript point type because
    * the underlying integer values are not identical.
    * 
    * @param thePointType
    *           screen point type
    * @return postscript point type.
    */
   public static int convertScreenToPostscript(int thePointType) {
      switch (thePointType) {
      case SCREEN_DOT:
         return POSTSCRIPT_DOT;
      case SCREEN_PLUS:
         return POSTSCRIPT_PLUS;
      case SCREEN_X:
         return POSTSCRIPT_X;
      case SCREEN_STAR:
         return POSTSCRIPT_STAR;
      case SCREEN_SQUARE_DOT:
         return POSTSCRIPT_SQUARE_DOT;
      case SCREEN_SQUARE_FILLED:
         return POSTSCRIPT_SQUARE_FILLED;
      case SCREEN_CIRCLE_DOT:
         return POSTSCRIPT_CIRCLE_DOT;
      case SCREEN_CIRCLE_FILLED:
         return POSTSCRIPT_CIRCLE_FILLED;
      case SCREEN_TRIANGLE_DOWN_DOT:
         return POSTSCRIPT_TRIANGLE_DOWN_DOT;
      case SCREEN_TRIANGLE_DOWN_FILLED:
         return POSTSCRIPT_TRIANGLE_DOWN_FILLED;
      case SCREEN_TRIANGLE_UP_DOT:
         return POSTSCRIPT_TRIANGLE_UP_DOT;
      case SCREEN_TRIANGLE_UP_FILLED:
         return POSTSCRIPT_TRIANGLE_UP_FILLED;
      case SCREEN_DIAMOND_DOT:
         return POSTSCRIPT_DIAMOND_DOT;
      case SCREEN_DIAMOND_FILLED:
         return POSTSCRIPT_DIAMOND_FILLED;
      default:
         return POSTSCRIPT_DOT; // TODO not all are covered
      }
   }

   /**
    * This method converts PostScript point type to screen point type because
    * the underlying integer values are not identical.
    * 
    * @param thePointType
    *           PostScript point type
    * 
    * @return screen point type.
    */
   public static int convertPostscriptToScreen(int thePointType) {
      switch (thePointType) {
      case POSTSCRIPT_DOT:
         return SCREEN_DOT;
      case POSTSCRIPT_PLUS:
         return SCREEN_PLUS;
      case POSTSCRIPT_X:
         return SCREEN_X;
      case POSTSCRIPT_STAR:
         return SCREEN_STAR;
      case POSTSCRIPT_SQUARE_DOT:
         return SCREEN_SQUARE_DOT;
      case POSTSCRIPT_SQUARE_FILLED:
         return SCREEN_SQUARE_FILLED;
      case POSTSCRIPT_CIRCLE_DOT:
         return SCREEN_CIRCLE_DOT;
      case POSTSCRIPT_CIRCLE_FILLED:
         return SCREEN_CIRCLE_FILLED;
      case POSTSCRIPT_TRIANGLE_DOWN_DOT:
         return SCREEN_TRIANGLE_DOWN_DOT;
      case POSTSCRIPT_TRIANGLE_DOWN_FILLED:
         return SCREEN_TRIANGLE_DOWN_FILLED;
      case POSTSCRIPT_TRIANGLE_UP_DOT:
         return SCREEN_TRIANGLE_UP_DOT;
      case POSTSCRIPT_TRIANGLE_UP_FILLED:
         return SCREEN_TRIANGLE_UP_FILLED;
      case POSTSCRIPT_DIAMOND_DOT:
         return SCREEN_DIAMOND_DOT;
      case POSTSCRIPT_DIAMOND_FILLED:
         return SCREEN_DIAMOND_FILLED;
      default:
         return SCREEN_DOT; // TODO not all are covered
      }
   }
}
