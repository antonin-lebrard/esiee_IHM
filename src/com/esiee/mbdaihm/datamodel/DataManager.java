package com.esiee.mbdaihm.datamodel;

import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.WorldRegions;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import com.esiee.mbdaihm.datamodel.listeners.SelectedCountryListener;

import java.nio.channels.SelectableChannel;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Singleton, following enum pattern, used to easily store and retrieve data.
 *
 * @author Nicolas M.
 */
public enum DataManager
{
    INSTANCE;

    private Country countrySelected = null;

    private List<SelectedCountryListener> selectedCountryListeners = new ArrayList<>();

    private List<Country> countries = new ArrayList<>();

    private Map<String, Country> countriesMap = new HashMap<>();

    private WorldRegions regions;

    // Topic, subtopic and indicators.
    private final Map<String, Map<String, List<Indicator>>> indicatorsMap = new TreeMap<>();

    private final Map<String, Indicator> flatIndicatorMap = new HashMap<>();

    private Indicator currentIndicator;

    public List<Country> getCountries()
    {
        return countries;
    }

    public WorldRegions getWorldRegions()
    {
        return regions;
    }

    public void setCountries(List<Country> countries)
    {
        countriesMap = countries.stream().
                filter(c -> !c.getIsoCode().equals("-99")).
                collect(Collectors.toMap(c -> c.getIsoCode(), Function.identity()));

        long validCountryCodes = countries.stream().filter(c -> !(c.getIsoCode().equals("-99"))).count();

        System.err.println("Stored " + validCountryCodes + " countries with valid code");

        this.countries = countries;
        regions = new WorldRegions(countries);
    }

    public Map<String, Map<String, List<Indicator>>> getIndicatorsMap()
    {
        return indicatorsMap;
    }

    public Country getCountryByCode(String code)
    {
        return countriesMap.get(code);
    }

    public Indicator getCurrentIndicator()
    {
        return currentIndicator;
    }

    public void setCurrentIndicator(Indicator currentIndicator)
    {
        this.currentIndicator = currentIndicator;
    }

    public Country getCountrySelected() {
        return countrySelected;
    }

    public void setCountrySelected(Country countrySelected) {
        this.countrySelected = countrySelected;
        for (SelectedCountryListener l : selectedCountryListeners){
            l.onSelectedCountryChange(countrySelected);
        }
    }

    public void addSelectedCountryListener(SelectedCountryListener listener) {
        selectedCountryListeners.add(listener);
    }

    public void removeSelectedCountryListener(SelectedCountryListener listener) {
        selectedCountryListeners.remove(listener);
    }

    public Map<String, Indicator> getFlatIndicatorMap() {
        return flatIndicatorMap;
    }
}
