package com.esiee.mbdaihm.datamodel.common;

/**
 * Created by antonin on 07/10/2016.
 */
public interface ModelListListener<M extends Model> {

    void onModelAdded(M model);

    void onModelRemoved(M model);

}
