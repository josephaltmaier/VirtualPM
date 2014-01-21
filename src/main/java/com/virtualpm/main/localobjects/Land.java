package com.virtualpm.main.localobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joseph Altmaier on 1/15/14.
 */
public class Land implements Comparable<Land>, Serializable {
    int landId;
    int kingdomId;
    String landName;
    String kingdomName;
    Date sheetDate;
    String eventName;
    ArrayList<Player> players;

    public Land(){}

    public Land(int landId, int kingdomId, String landName, String kingdomName) {
        this.landId = landId;
        this.kingdomId = kingdomId;
        this.landName = landName;
        this.kingdomName = kingdomName;
    }

    public Land(int landId, int kingdomId, String landName, String kingdomName, List<Player> players) {
        this.landId = landId;
        this.kingdomId = kingdomId;
        this.landName = landName;
        this.kingdomName = kingdomName;
        this.players.addAll(players);
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public int getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(int kingdomId) {
        this.kingdomId = kingdomId;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }

    public Date getSheetDate() {
        return sheetDate;
    }

    public void setSheetDate(Date sheetDate) {
        this.sheetDate = sheetDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public int compareTo(Land another) {
        return getLandName().compareTo(another.getLandName());
    }
}
