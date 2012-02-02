package org.jgnuplot;

/**
 * This class models a triplet value for certain settings, basicly it is boolean
 * that can also be unspecified.
 * 
 * @author Pander
 */
public final class State {
   private State() {
   }

   public static final int ON = 1;
   public static final int OFF = 2;
   public static final int NOT_SPECIFIED = -2;

}
