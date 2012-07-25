package com.conx.logistics.kernel.ui.common.ui.ext;

import java.util.ArrayList;

import com.vaadin.ui.TreeTable;

public class ConXNavigationTree extends TreeTable {
	private static final long serialVersionUID = -4744167266095653799L;
	
	private ArrayList<ConXNavigationTreeItem> itemList;
	
	public ConXNavigationTree() {
		itemList = new ArrayList<ConXNavigationTreeItem>();
		
		setSizeFull();
		setSelectable(true);
		addStyleName("conx-tree-nav");
		setColumnHeaderMode(ConXNavigationTree.COLUMN_HEADER_MODE_HIDDEN);
		addContainerProperty("name", ConXNavigationTreeItem.class, "");
	}
	
	public ConXNavigationTreeItem addConXNavigationTreeItem(String caption, String iconUrl) {
		ConXNavigationTreeItem newItem = new ConXNavigationTreeItem(caption, iconUrl, this, itemList.size());
		itemList.add(newItem);
		addItem(new Object[] { newItem }, newItem.getId());
		setChildrenAllowed(newItem.getId(), false);
		return newItem;
	}
	
	public ConXNavigationTreeItem addConXNavigationTreeItem(String caption, String iconUrl, int parentId) {
		ConXNavigationTreeItem newItem = addConXNavigationTreeItem(caption, iconUrl);
		if (itemList.get(parentId) != null) {
			setChildrenAllowed(parentId, true);
			setParent(newItem.getId(), parentId);
			setCollapsed(parentId, false);
		}
		return newItem;
	}
}
