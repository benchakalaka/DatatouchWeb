/*
 * Copyright 2000-2013 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.datascope.test.components;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;

import com.vaadin.ui.themes.ValoTheme;

public class Trees extends VerticalLayout implements View {
    public Trees() {
        setMargin(true);

        Label h1 = new Label("Trees");
        h1.addStyleName(ValoTheme.LABEL_H1);
        addComponent(h1);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        row.setSpacing(true);
        addComponent(row);
//
//        TreeGrid tree = new TreeGrid();
//        tree.setSelectable(true);
//        tree.setMultiSelect(true);
//        Container generateContainer = ValoThemeUI.generateContainer(10, true);
//        tree.setContainerDataSource(generateContainer);
//        tree.setDragMode(TreeDragMode.NODE);
//        row.addComponent(tree);
//        tree.setItemCaptionPropertyId(ValoThemeUI.CAPTION_PROPERTY);
//        tree.setItemIconPropertyId(ValoThemeUI.ICON_PROPERTY);
//        tree.expandItem(generateContainer.getItemIds().iterator().next());
//
//        tree.setDropHandler(new DropHandler() {
//            @Override
//            public AcceptCriterion getAcceptCriterion() {
//                return AcceptAll.get();
//            }
//
//            @Override
//            public void drop(DragAndDropEvent event) {
//                Notification.show(event.getTransferable().toString());
//            }
//        });
//
//        // Add actions (context menu)
//        tree.addActionHandler(ValoThemeUI.getActionHandler());
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
