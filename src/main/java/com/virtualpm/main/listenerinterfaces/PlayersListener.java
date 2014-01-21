package com.virtualpm.main.listenerinterfaces;

import com.virtualpm.main.localobjects.Player;

import java.util.Collection;

/**
 * Created by Joseph Altmaier on 1/20/14.
 */
public interface PlayersListener {
    public void setPlayers(Collection<Player> players);

    public void playersError();
}
