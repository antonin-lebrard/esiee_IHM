package com.esiee.mbdaihm.datamodel.model;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.common.Model;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;

import java.util.Optional;

/**
 * Created by student on 07/10/2016.
 */
public class TagModel implements Model {

    private Country associatedCountry;
    private boolean isCountry = false;

    private Indicator associatedIndicator;
    private boolean isIndicator = false;

    public TagModel(String tag){
        Optional<Country> counOpt = DataManager.INSTANCE.getCountries().stream()
                .filter((p)->p.getName().equals(tag))
                .findFirst();
        counOpt.ifPresent(country -> {
            associatedCountry = country;
            isCountry = true;
        });
        if (isCountry) return;

        Indicator indic = DataManager.INSTANCE.getFlatIndicatorMap().get(tag);
        if (indic != null){
            associatedIndicator = indic;
            isIndicator = true;
        }
    }

    public TagModel(Country country){
        associatedCountry = country;
        isCountry = true;
    }

    public TagModel(Indicator indicator){
        associatedIndicator = indicator;
        isIndicator = true;
    }

    public boolean isIndicator() {
        return isIndicator;
    }

    public Indicator getAssociatedIndicator() {
        return associatedIndicator;
    }

    public boolean isCountry() {
        return isCountry;
    }

    public Country getAssociatedCountry() {
        return associatedCountry;
    }
}
