package com.conx.logistics.kernel.ui.common.gwt.client.ui;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.VerticalLayout;

public class ConXNavigationAccordion extends VerticalLayout {
	private static final long serialVersionUID = 3370715870286189986L;

	private Accordion accordion;

	public ConXNavigationAccordion() {
		setSizeFull();
		setMargin(false, false, true, false);

		accordion = new Accordion();
		accordion.setStyleName("conx-navigation-accordion");
		accordion.setSizeFull();

		addComponent(accordion);
	}

	public void addCategory(ConXNavigationTree subTree, String title) {
		accordion.addTab(subTree, title);
	}

	public void setContainer(HierarchicalContainer container) {
		removeAllComponents();
		Collection<?> rootIds = container.rootItemIds();
		for (Object rootId : rootIds) {
			Item currentItem = container.getItem(rootId);
			Object nameProperty = currentItem.getItemProperty("name").getValue();
			if (nameProperty instanceof String) {
				ConXNavigationTree tree = new ConXNavigationTree();
				Collection<?> treeRootIds = container.getChildren(rootId);
				for (Object treeRootId : treeRootIds) {
					populateTree(container, tree, null, treeRootId);
				}
			}
		}
	}

	private void populateTree(HierarchicalContainer container, ConXNavigationTree tree, Integer parentId, Object currentId) {
		Item current = container.getItem(currentId);
		if (current != null) {
			Object iconUrl = current.getItemProperty("iconUrl").getValue(), caption = current.getItemProperty("caption").getValue();
			if (!(iconUrl instanceof String) || !(caption instanceof String)) {
				return;
			} else {
				ConXNavigationTreeItem item;
				if (parentId != null) {
					item = tree.addConXNavigationTreeItem((String) caption, (String) iconUrl, parentId);
				} else {
					item = tree.addConXNavigationTreeItem((String) caption, (String) iconUrl);
				}

				Collection<?> childIds = container.getChildren(currentId);
				for (Object childId : childIds) {
					if (container.hasChildren(childId)) {
						populateTree(container, tree, item.getId(), childId);
					} else {
						Item childItem = container.getItem(childId);
						iconUrl = childItem.getItemProperty("iconUrl").getValue();
						caption = childItem.getItemProperty("caption").getValue();
						if ((iconUrl instanceof String) && (caption instanceof String)) {
							tree.addConXNavigationTreeItem((String) caption, (String) iconUrl, item.getId());
						} 
					}
				}
			}
		}
	}
}
