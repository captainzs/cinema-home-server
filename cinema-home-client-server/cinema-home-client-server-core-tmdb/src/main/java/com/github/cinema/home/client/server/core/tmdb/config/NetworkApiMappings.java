package com.github.cinema.home.client.server.core.tmdb.config;

import com.github.cinema.home.client.server.common.types.media.NetworkGroup;
import com.github.cinema.home.client.server.common.utils.Sets;
import com.github.cinema.home.client.server.core.tmdb.types.TmdbCompany;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NetworkApiMappings {
    private static final ImmutableMap<NetworkGroup, Set<TmdbCompany>> MAPPING = ImmutableMap.<NetworkGroup, Set<TmdbCompany>>builder()
            .put(NetworkGroup.ABC, asSet(
                    TmdbCompany.of(2, "ABC"),
                    TmdbCompany.of(18, "ABC"),
                    TmdbCompany.of(75, "ABC Family"),
                    TmdbCompany.of(279, "ABC Me"),
                    TmdbCompany.of(321, "ABC Comedy"),
                    TmdbCompany.of(601, "ABC News"),
                    TmdbCompany.of(1327, "ABC iview"),
                    TmdbCompany.of(1345, "ABC Spark"),
                    TmdbCompany.of(2488, "ABC Nyheter"),
                    TmdbCompany.of(2854, "ABC KIDS"),
                    TmdbCompany.of(3322, "ABC.com")))
            .put(NetworkGroup.APPLE, asSet(
                    TmdbCompany.of(1932, "Apple Music"),
                    TmdbCompany.of(2552, "Apple TV+")))
            .put(NetworkGroup.AMAZON, Collections.singleton(TmdbCompany.of(1024, "Amazon")))
            .put(NetworkGroup.BBC, asSet(
                    TmdbCompany.of(3, "BBC Three"),
                    TmdbCompany.of(4, "BBC One"),
                    TmdbCompany.of(100, "BBC Four"),
                    TmdbCompany.of(126, "BBC Choice"),
                    TmdbCompany.of(317, "BBC World News"),
                    TmdbCompany.of(332, "BBC Two"),
                    TmdbCompany.of(375, "BBC News"),
                    TmdbCompany.of(414, "BBC Kids"),
                    TmdbCompany.of(428, "BBC Red Button"),
                    TmdbCompany.of(493, "BBC America"),
                    TmdbCompany.of(1001, "BBC UKTV"),
                    TmdbCompany.of(1051, "BBC First"),
                    TmdbCompany.of(1155, "BBC iPlayer"),
                    TmdbCompany.of(3278, "BBC Scotland"),
                    TmdbCompany.of(3546, "BBC Television"),
                    TmdbCompany.of(3552, "BBC Home Service"),
                    TmdbCompany.of(3590, "BBC Knowledge"),
                    TmdbCompany.of(3655, "BBC Television Centre"),
                    TmdbCompany.of(3660, "BBC South Africa"),
                    TmdbCompany.of(3859, "BBC Earth")))
            .put(NetworkGroup.CBS, asSet(
                    TmdbCompany.of(16, "CBS"),
                    TmdbCompany.of(1709, "CBS All Access"),
                    TmdbCompany.of(2528, "CBS Reality"),
                    TmdbCompany.of(2621, "CBS.com"),
                    TmdbCompany.of(3190, "CBS Drama"),
                    TmdbCompany.of(3457, "CBS (JP)"),
                    TmdbCompany.of(3922, "CBS")))
            .put(NetworkGroup.CRITERION, Collections.emptySet())
            .put(NetworkGroup.CW, asSet(
                    TmdbCompany.of(71, "The CW"),
                    TmdbCompany.of(1049, "CW seed")))
            .put(NetworkGroup.DCU, Collections.singleton(TmdbCompany.of(2243, "DC Universe")))
            .put(NetworkGroup.DISNEY, asSet(
                    TmdbCompany.of(44, "Disney XD"),
                    TmdbCompany.of(54, "Disney Channel"),
                    TmdbCompany.of(142, "Toon Disney"),
                    TmdbCompany.of(281, "Disney Junior"),
                    TmdbCompany.of(515, "Disney Channel"),
                    TmdbCompany.of(539, "Disney Channel"),
                    TmdbCompany.of(730, "Disney Channel"),
                    TmdbCompany.of(835, "Disney Channel Latin America"),
                    TmdbCompany.of(1385, "Disney Channel"),
                    TmdbCompany.of(1996, "Disney Channel"),
                    TmdbCompany.of(2324, "Disney XD"),
                    TmdbCompany.of(2325, "Disney Junior"),
                    TmdbCompany.of(2326, "Disney XD"),
                    TmdbCompany.of(2327, "Disney Channel"),
                    TmdbCompany.of(2534, "Disney Channel"),
                    TmdbCompany.of(2739, "Disney+"),
                    TmdbCompany.of(2771, "Disney Junior"),
                    TmdbCompany.of(2897, "Disney Channel"),
                    TmdbCompany.of(2991, "Playhouse Disney"),
                    TmdbCompany.of(3408, "Disney Channel"),
                    TmdbCompany.of(3874, "Disney Channel "),
                    TmdbCompany.of(3919, "Disney+ Hotstar"),
                    TmdbCompany.of(4006, "Disney Channel"),
                    TmdbCompany.of(4011, "Disney Junior (PT)")))
            .put(NetworkGroup.HBO, asSet(
                    TmdbCompany.of(49, "HBO"),
                    TmdbCompany.of(1062, "HBO Nordic"),
                    TmdbCompany.of(1089, "HBO Latin America"),
                    TmdbCompany.of(1129, "HBO Europe"),
                    TmdbCompany.of(1303, "HBO Asia"),
                    TmdbCompany.of(1590, "HBO Canada"),
                    TmdbCompany.of(2593, "HBO Family"),
                    TmdbCompany.of(3186, "HBO Max"),
                    TmdbCompany.of(3308, "HBO"),
                    TmdbCompany.of(3618, "HBO Brasil"),
                    TmdbCompany.of(3877, "HBO España")))
            .put(NetworkGroup.HULU, asSet(
                    TmdbCompany.of(453, "Hulu"),
                    TmdbCompany.of(1772, "Hulu Japan")))
            .put(NetworkGroup.ITUNES, Collections.singleton(TmdbCompany.of(1377, "iTunes Store")))
            .put(NetworkGroup.MTV, asSet(
                    TmdbCompany.of(17, "MTV2"),
                    TmdbCompany.of(33, "MTV"),
                    TmdbCompany.of(295, "MTV3"),
                    TmdbCompany.of(304, "MTV Australia"),
                    TmdbCompany.of(335, "MTV Canada"),
                    TmdbCompany.of(381, "MTV Base"),
                    TmdbCompany.of(410, "MTV Italy"),
                    TmdbCompany.of(454, "MTV Europe"),
                    TmdbCompany.of(488, "MTV"),
                    TmdbCompany.of(568, "MTV Pakistan"),
                    TmdbCompany.of(788, "MTV Philippines"),
                    TmdbCompany.of(867, "MTV Brasil"),
                    TmdbCompany.of(924, "MTV Germany"),
                    TmdbCompany.of(931, "MTV Asia"),
                    TmdbCompany.of(959, "MTV Poland"),
                    TmdbCompany.of(976, "MTV Denmark"),
                    TmdbCompany.of(1008, "MTV Russia"),
                    TmdbCompany.of(1173, "MTV Lebanon"),
                    TmdbCompany.of(2116, "MTV Latin America"),
                    TmdbCompany.of(2234, "MTV Japan"),
                    TmdbCompany.of(2294, "MTV Nederland"),
                    TmdbCompany.of(2414, "MTV 1"),
                    TmdbCompany.of(2972, "MTV (ES)"),
                    TmdbCompany.of(3402, "MTV-Hindi")))
            .put(NetworkGroup.MTVA, Collections.emptySet())
            .put(NetworkGroup.NBC, asSet(
                    TmdbCompany.of(6, "NBC"),
                    TmdbCompany.of(37, "MSNBC"),
                    TmdbCompany.of(175, "CNBC"),
                    TmdbCompany.of(186, "CNBC Europe"),
                    TmdbCompany.of(287, "CNBC Asia"),
                    TmdbCompany.of(355, "CNBC World"),
                    TmdbCompany.of(413, "NBCSN"),
                    TmdbCompany.of(581, "WNBC"),
                    TmdbCompany.of(624, "KNBC"),
                    TmdbCompany.of(629, "Nikkei CNBC"),
                    TmdbCompany.of(790, "NBC Weather Plus"),
                    TmdbCompany.of(900, "CNBC TV18"),
                    TmdbCompany.of(2462, "NBC")))
            .put(NetworkGroup.NICKELODEON, asSet(
                    TmdbCompany.of(13, "Nickelodeon"),
                    TmdbCompany.of(35, "Nick Jr."),
                    TmdbCompany.of(224, "Nicktoons"),
                    TmdbCompany.of(234, "TeenNick"),
                    TmdbCompany.of(259, "Nick at Nite"),
                    TmdbCompany.of(286, "Nickelodeon"),
                    TmdbCompany.of(416, "Nickelodeon"),
                    TmdbCompany.of(474, "Nickelodeon"),
                    TmdbCompany.of(594, "Nick Jr."),
                    TmdbCompany.of(775, "Nickelodeon"),
                    TmdbCompany.of(794, "Nickelodeon"),
                    TmdbCompany.of(1703, "Nick Pakistan"),
                    TmdbCompany.of(2313, "Nickelodeon India"),
                    TmdbCompany.of(2314, "Nickelodeon"),
                    TmdbCompany.of(2315, "Nick Jr."),
                    TmdbCompany.of(3620, "Kids Nick Polska"),
                    TmdbCompany.of(4015, "Nickelodeon"),
                    TmdbCompany.of(4016, "Nickelodeon Latin America")))
            .put(NetworkGroup.NETFLIX, Collections.singleton(TmdbCompany.of(213, "Netflix")))
            .put(NetworkGroup.STARZ, asSet(
                    TmdbCompany.of(318, "Starz"),
                    TmdbCompany.of(758, "Starz Encore")))
            .build();

    private static Set<TmdbCompany> asSet(TmdbCompany... companies) {
        return new HashSet<>(Arrays.asList(companies));
    }

    public static Set<TmdbCompany> of(NetworkGroup network) {
        if (network == null) {
            return Collections.emptySet();
        }
        return MAPPING.getOrDefault(network, Collections.emptySet());
    }

    public static LinkedHashSet<NetworkGroup> toNetworks(Set<TmdbCompany> networks) {
        if (networks == null) {
            return Sets.emptyLinkedSet();
        }
        return MAPPING.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(networks::contains))
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
