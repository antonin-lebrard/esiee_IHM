/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.common.ModelListListener;
import com.esiee.mbdaihm.datamodel.model.TagModel;
import com.esiee.mbdaihm.datamodel.model.TagsModel;

public class TagsPanel extends javax.swing.JPanel implements ModelListListener<TagModel> {


    public TagsPanel() {
        initComponents();
        TagsModel.getInstance().addModelListListener(this);
    }


    private void initComponents() {}

    @Override
    public void onModelAdded(TagModel model) {
        add(new TagPanel(model));
    }

    @Override
    public void onModelRemoved(TagModel model) {
        for (int i = 0; i < getComponents().length; i++){
            if (((TagPanel)getComponents()[i]).getTagModel().equals(model)){
                remove(i);
            }
        }
    }
}
