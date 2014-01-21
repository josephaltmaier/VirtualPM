package com.virtualpm.main.localobjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joseph Altmaier on 1/15/14.
 */
public class Kingdom implements Comparable<Kingdom>, Serializable {
    int kingdomId;
    String kingdomName;
    List<Land> lands;

    public Kingdom(){}

    public Kingdom(int kingdomId, String kingdomName) {
        this.kingdomId = kingdomId;
        this.kingdomName = kingdomName;
    }

    public Kingdom(int kingdomId, String kingdomName, List<Land> lands) {
        this.kingdomId = kingdomId;
        this.kingdomName = kingdomName;
        this.lands.addAll(lands);
    }

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public List<Land> getLands() {
        return lands;
    }

    public void setLands(List<Land> lands) {
        this.lands = lands;
    }

    @Override
    public int compareTo(Kingdom another) {
        return getKingdomName().compareTo(another.getKingdomName());
    }
}
