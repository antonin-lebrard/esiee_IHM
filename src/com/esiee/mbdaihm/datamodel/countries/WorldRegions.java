package com.esiee.mbdaihm.datamodel.countries;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class used
 */
public class WorldRegions
{
    private final Set<String> regionValues;

    private final Map<String, List<String>> subRegionsMap;
    public WorldRegions(List<Country> content)
    {
        regionValues = content.stream()
                .map(c -> c.getRegion())
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(TreeSet<String>::new));

        subRegionsMap = regionValues.stream()
                .collect(
                        Collectors.toMap(
                                // Key Mapper : region
                                Function.identity(),
                                // Value Mapper : list of sub regions for region
                                r -> content.stream().filter(c -> c.getRegion().equals(r))
                                .map(c -> c.getSubRegion())
                                .distinct()
                                .sorted()
                                .collect(Collectors.toList()),
                                // Merge function : No duplicate so no merge...
                                (t, u) -> t,
                                // Supplier
                                TreeMap::new));
    }

    public Set<String> getRegionValues()
    {
        return regionValues;
    }

    public Map<String, List<String>> getSubRegionsMap()
    {
        return subRegionsMap;
    }

}
