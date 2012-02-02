package org.jgnuplot;

/**
 * This class models all possible values for the terminal command.
 * 
 * @author Pander
 */
public final class Terminal {
   private Terminal() {
   }

   public static final String NOT_SPECIFIED = null;

   public static final String PNG = "png";

   public static final String GIF = "gif";

   public static final String SVG = "svg";

   public static final String POSTSCRIPT = "postscript";

   public static final String PDF = "pdf";

   // public static final String DUMB = "windows";

   // public static final String WINDOWS = "windows";

   // TODO support more terminals
}
