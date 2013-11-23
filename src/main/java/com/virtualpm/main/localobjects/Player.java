package com.virtualpm.main.localobjects;

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Altmaier
 * Date: 11/19/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player implements Comparable<Player> {
    int playerId;
    int landId;
    int kingdomId;
    String name;
    String persona;
    String landName;
    String kingdomName;
    boolean duesPaid;
    String amtClass;

    public Player(){
        super();
    }

    public Player(int playerId, int landId, int kingdomId, String name, String persona, String landName, String kingdomName, boolean duesPaid, String amtClass) {
        this.playerId = playerId;
        this.landId = landId;
        this.kingdomId = kingdomId;
        this.name = name;
        this.persona = persona;
        this.landName = landName;
        this.kingdomName = kingdomName;
        this.duesPaid = duesPaid;
        this.amtClass = amtClass;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
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

    public boolean isDuesPaid() {
        return duesPaid;
    }

    public void setDuesPaid(boolean duesPaid) {
        this.duesPaid = duesPaid;
    }

    public String getAmtClass() {
        return amtClass;
    }

    public void setAmtClass(String amtClass) {
        this.amtClass = amtClass;
    }


    @Override
    public int compareTo(Player another) {
        int compare = getName().compareTo(another.getName());
        if(compare == 0)
            compare = getPersona().compareTo(another.getPersona());
        return compare;
    }
}
