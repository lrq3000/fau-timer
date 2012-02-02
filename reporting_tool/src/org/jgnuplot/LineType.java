package org.jgnuplot;

/**
 * This class models all possible values for the line style option for a graph
 * in a plot command.
 * 
 * @author Pander
 */
public final class LineType {
   private LineType() {
   }

   public static final int NOT_SPECIFIED = -2;
   /**
    * <font face="Courier New, courier" color="#000000">===========</font> is a
    * black solid bold line on screen.
    */
   public static final int SCREEN_BLACK_SOLID_BOLD = -1;
   /**
    * <font face="Courier New, courier" color="#000000">. . . . .</font> is a
    * black dotted line on screen.
    */
   public static final int SCREEN_BLACK_DOTS = 0;
   /**
    * <font face="Courier New, courier" color="#ff0000">___________</font> is a
    * red solid line on screen.
    */
   public static final int SCREEN_RED = 1;
   /**
    * <font face="Courier New, courier" color="#00ff00">___________</font> is a
    * green solid line on screen.
    */
   public static final int SCREEN_GREEN = 2;
   /**
    * <font face="Courier New, courier" color="#0000ff">___________</font> is a
    * blue solid line on screen.
    */
   public static final int SCREEN_BLUE = 3;
   /**
    * <font face="Courier New, courier" color="#00ffff">___________</font> is a
    * magenta solid line on screen.
    */
   public static final int SCREEN_MAGENTA = 4;
   /**
    * <font face="Courier New, courier" color="#ff00ff">___________</font> is a
    * cyan solid line on screen.
    */
   public static final int SCREEN_CYAN = 5;
   /**
    * <font face="Courier New, courier" color="#a0522d">___________</font> is a
    * brown solid line on screen.
    */
   public static final int SCREEN_BROWN = 6;
   /**
    * <font face="Courier New, courier" color="#ffa500">___________</font> is
    * yellow solid line on screen.
    */
   public static final int SCREEN_YELLOW = 7;
   /**
    * <font face="Courier New, courier" color="#ff7f50">___________</font> is
    * salmon solid line on screen.
    */
   public static final int SCREEN_SALMON = 8;
   /**
    * <font face="Courier New, courier">=========</font> is solid bold line on
    * screen.
    */
   public static final int POSTSCRIPT_SOLID_BOLD = -1;
   /**
    * <font face="Courier New, courier">. . . . .</font> is a 'dot space' line
    * in PostScript.
    */
   public static final int POSTSCRIPT_DOT_SPACE = 0;
   /**
    * <font face="Courier New, courier">_________</font> is a solid line in
    * PostScript.
    */
   public static final int POSTSCRIPT_SOLID = 1;
   /**
    * <font face="Courier New, courier">---------</font> is a dashed line in
    * PostScript.
    */
   public static final int POSTSCRIPT_DASHES = 2;
   /**
    * <font face="Courier New, courier">- - - - -</font> is a 'dash space' line
    * in PostScript.
    */
   public static final int POSTSCRIPT_DASH_SPACE = 3;
   /**
    * <font face="Courier New, courier">.........</font> is a dotted line in
    * PostScript.
    */
   public static final int POSTSCRIPT_DOTS = 4;
   /**
    * <font face="Courier New, courier">-.-.-.-.-</font> is a 'dash dot' line
    * in PostScript.
    */
   public static final int POSTSCRIPT_DASH_DOT = 5;
   /**
    * <font face="Courier New, courier">- . - . -</font> is a 'dash space dot
    * space' line in PostScript.
    */
   public static final int POSTSCRIPT_DASH_SPACE_DOT_SPACE = 6;
   /**
    * <font face="Courier New, courier">-- -- -- </font> is a 'dash dash space'
    * line in PostScript.
    */
   public static final int POSTSCRIPT_DASH_DASH_SPACE = 7;
   /**
    * <font face="Courier New, courier">--- --- </font> is a 'dash dash dash
    * space' line in PostScript.
    */
   public static final int POSTSCRIPT_DASH_DASH_DASH_SPACE = 8;
   /**
    * <font face="Courier New, courier">---- ----</font> is a 'dash dash dash
    * dash space' line in PostScript.
    */
   public static final int POSTSCRIPT_DASH_DASH_DASH_DASH_SPACE = 9;

   /**
    * This method converts screen line type to PostScript line type because the
    * underlying integer values are not identical.
    * 
    * @param theLineType
    *           screen line type
    * @return postscript line type.
    */
   public static int convertScreenToPostscript(int theLineType) {
      switch (theLineType) {
      case SCREEN_BLACK_SOLID_BOLD:
         return POSTSCRIPT_SOLID;
      default:
         return POSTSCRIPT_SOLID; // TODO not all are covered
      }
   }

   /**
    * This method converts PostScript line type to screen line type because the
    * underlying integer values are not identical.
    * 
    * @param theLineType
    *           PostScript line type
    * @return screen line type.
    */
   public static int convertPostscriptToScreen(int theLineType) {
      switch (theLineType) {
      case POSTSCRIPT_SOLID:
         return SCREEN_BLACK_SOLID_BOLD;
      default:
         return SCREEN_BLACK_SOLID_BOLD; // TODO not all are covered
      }
   }
}
