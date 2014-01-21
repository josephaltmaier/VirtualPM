package com.virtualpm.main.listenerinterfaces;

import com.virtualpm.main.localobjects.Kingdom;

import java.util.Collection;

/**
 * Created by Joseph Altmaier on 1/20/14.
 */
public interface KingdomsListener {
    public void setKingdoms(Collection<Kingdom> kingdoms);

    public void kingdomsError();
}
