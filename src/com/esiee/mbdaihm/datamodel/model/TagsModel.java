package com.esiee.mbdaihm.datamodel.model;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.common.ModelList;
import com.esiee.mbdaihm.datamodel.common.ModelListListener;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.listeners.SelectedCountryListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 07/10/2016.
 */
public class TagsModel implements ModelList<TagModel>, SelectedCountryListener {

    private static TagsModel INSTANCE;

    private List<ModelListListener<TagModel>> tagsModelListeners = new ArrayList<>();

    private List<TagModel> tags = new ArrayList<>();

    private TagsModel(){
        DataManager.INSTANCE.addSelectedCountryListener(this);
    }

    public static TagsModel getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TagsModel();
        return INSTANCE;
    }

    @Override
    public void onSelectedCountryChange(Country country) {
        add(new TagModel(country.getName()));
    }

    @Override
    public void add(TagModel model) {
        tags.add(model);
        for (ModelListListener<TagModel> l : tagsModelListeners){
            l.onModelAdded(model);
        }
    }

    @Override
    public void remove(TagModel model) {
        tags.remove(model);
        for (ModelListListener<TagModel> l : tagsModelListeners){
            l.onModelRemoved(model);
        }
    }

    @Override
    public void addModelListListener(ModelListListener<TagModel> listener) {
        tagsModelListeners.add(listener);
    }

    @Override
    public void removeModelListListener(ModelListListener<TagModel> listener) {
        tagsModelListeners.remove(listener);
    }
}
