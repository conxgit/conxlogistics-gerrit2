package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class VConxMenuOverlay extends VOverlay {
	public VConxMenuOverlay() {
		super();
		setShadowEnabled(false);
	}
	
	public VConxMenuOverlay(boolean autoHide) {
        super(autoHide);
        setShadowEnabled(false);
    }

    public VConxMenuOverlay(boolean autoHide, boolean modal) {
        super(autoHide, modal);
        setShadowEnabled(false);
    }

    public VConxMenuOverlay(boolean autoHide, boolean modal, boolean showShadow) {
        super(autoHide, modal, false);
    }
    
    protected void updateMenuOverlayShadowSizeAndPosition() {
    	updateShadowSizeAndPosition();
    }
}
