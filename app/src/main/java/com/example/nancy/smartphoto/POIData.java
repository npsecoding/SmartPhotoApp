package com.example.nancy.smartphoto;

import java.util.ArrayList;
import java.util.List;

public class POIData {
    public List<POI> poidata;

    public POIData(){
        poidata = new ArrayList<POI>();
    }

    public void add(POI poi){
        poidata.add(poi);
    }

    public void remove(POI poi)
    {
        poidata.remove(poi);
    }
}