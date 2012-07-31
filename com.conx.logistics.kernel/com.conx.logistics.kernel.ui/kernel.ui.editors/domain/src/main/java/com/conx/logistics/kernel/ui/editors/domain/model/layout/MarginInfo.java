package com.conx.logistics.kernel.ui.editors.domain.model.layout;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.conx.logistics.mdm.domain.MultitenantBaseEntity;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name="sysedmargininforepresentation")
public class MarginInfo extends MultitenantBaseEntity {
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 4;
    private static final int LEFT = 8;

    private int bitMask;

    public MarginInfo(int bitMask) {
        this.bitMask = bitMask;
    }

    public MarginInfo(boolean top, boolean right, boolean bottom, boolean left) {
        setMargins(top, right, bottom, left);
    }

    public MarginInfo(boolean enable) {
    	this(enable,enable,enable,enable);
	}

	public void setMargins(boolean top, boolean right, boolean bottom,
            boolean left) {
        bitMask = top ? TOP : 0;
        bitMask += right ? RIGHT : 0;
        bitMask += bottom ? BOTTOM : 0;
        bitMask += left ? LEFT : 0;
    }

    public void setMargins(MarginInfo marginInfo) {
        bitMask = marginInfo.bitMask;
    }

    public boolean hasLeft() {
        return (bitMask & LEFT) == LEFT;
    }

    public boolean hasRight() {
        return (bitMask & RIGHT) == RIGHT;
    }

    public boolean hasTop() {
        return (bitMask & TOP) == TOP;
    }

    public boolean hasBottom() {
        return (bitMask & BOTTOM) == BOTTOM;
    }

    public int getBitMask() {
        return bitMask;
    }

    public void setMargins(boolean enabled) {
        if (enabled) {
            bitMask = TOP + RIGHT + BOTTOM + LEFT;
        } else {
            bitMask = 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MarginInfo)) {
            return false;
        }

        return ((MarginInfo) obj).bitMask == bitMask;
    }

    @Override
    public int hashCode() {
        return bitMask;
    }
}
