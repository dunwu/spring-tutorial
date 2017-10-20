package io.github.dunwu.spring.ioc.annotation.inject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.dunwu.spring.ioc.annotation.inject.instrument.Instrument;

public class Band implements Performer {
    private Collection<Instrument> collections;
    private List<Instrument> list;
    private Set<Instrument> set;
    private Map<String, Instrument> map;

    public Band() {}

    @Override
    public void perform() throws Exception {
        System.out.println("乐队演奏");

        System.out.println("=============== collections ===============");
        for (Instrument instrument : collections) {
            instrument.play();
        }

        System.out.println("=============== list ===============");
        for (Instrument instrument : list) {
            instrument.play();
        }

        System.out.println("=============== set ===============");
        for (Instrument instrument : set) {
            instrument.play();
        }

        System.out.println("=============== map ===============");
        for (String key : map.keySet()) {
            Instrument instrument = map.get(key);
            instrument.play();
        }
    }

    public void setCollections(Collection<Instrument> collections) {
        this.collections = collections;
    }

    public void setList(List<Instrument> list) {
        this.list = list;
    }

    public void setSet(Set<Instrument> set) {
        this.set = set;
    }

    public void setMap(Map<String, Instrument> map) {
        this.map = map;
    }
}
