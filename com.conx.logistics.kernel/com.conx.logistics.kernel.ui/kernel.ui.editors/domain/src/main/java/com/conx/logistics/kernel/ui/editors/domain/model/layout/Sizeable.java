package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import java.io.Serializable;

public interface Sizeable extends Serializable {

	    /**
	     * Unit code representing pixels.
	     */
	    public static final int UNITS_PIXELS = 0;

	    /**
	     * Unit code representing points (1/72nd of an inch).
	     */
	    public static final int UNITS_POINTS = 1;

	    /**
	     * Unit code representing picas (12 points).
	     */
	    public static final int UNITS_PICAS = 2;

	    /**
	     * Unit code representing the font-size of the relevant font.
	     */
	    public static final int UNITS_EM = 3;

	    /**
	     * Unit code representing the x-height of the relevant font.
	     */
	    public static final int UNITS_EX = 4;

	    /**
	     * Unit code representing millimeters.
	     */
	    public static final int UNITS_MM = 5;

	    /**
	     * Unit code representing centimeters.
	     */
	    public static final int UNITS_CM = 6;

	    /**
	     * Unit code representing inches.
	     */
	    public static final int UNITS_INCH = 7;

	    /**
	     * Unit code representing in percentage of the containing element defined by
	     * terminal.
	     */
	    public static final int UNITS_PERCENTAGE = 8;

	    public static final float SIZE_UNDEFINED = -1;

	    /**
	     * Textual representations of units symbols. Supported units and their
	     * symbols are:
	     * <ul>
	     * <li>{@link #UNITS_PIXELS}: "px"</li>
	     * <li>{@link #UNITS_POINTS}: "pt"</li>
	     * <li>{@link #UNITS_PICAS}: "pc"</li>
	     * <li>{@link #UNITS_EM}: "em"</li>
	     * <li>{@link #UNITS_EX}: "ex"</li>
	     * <li>{@link #UNITS_MM}: "mm"</li>
	     * <li>{@link #UNITS_CM}. "cm"</li>
	     * <li>{@link #UNITS_INCH}: "in"</li>
	     * <li>{@link #UNITS_PERCENTAGE}: "%"</li>
	     * </ul>
	     * These can be used like <code>Sizeable.UNIT_SYMBOLS[UNITS_PIXELS]</code>.
	     */
	    public static final String[] UNIT_SYMBOLS = { "px", "pt", "pc", "em", "ex",
	            "mm", "cm", "in", "%" };
}
