package com.esiee.mbdaihm.datamodel.common;

/**
 * Created by antonin on 07/10/2016.
 */
public interface ModelList<M extends Model> {

    void add(M model);

    void remove(M model);

    void addModelListListener(ModelListListener<M> listener);

    void removeModelListListener(ModelListListener<M> listener);

}
