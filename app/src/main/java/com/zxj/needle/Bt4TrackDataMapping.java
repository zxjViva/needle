package com.zxj.needle;

import com.zxj.needle_annotation.map.AbsTrackDataMapping;

import java.util.HashMap;

public class Bt4TrackDataMapping extends AbsTrackDataMapping {

    private final HashMap<Integer, String> map;

    public Bt4TrackDataMapping() {
        map = new HashMap<>();
        map.put(1,"im not 1");
        map.put(2,"im not 2");
        map.put(3,"im not 3");
    }

    @Override
    public String getRealData(Object key) {
        return map.get(key);
    }

    @Override
    public String getName() {
        return "key2";
    }
}
