package com.virtualpm.main.listenerinterfaces;

import com.virtualpm.main.localobjects.Land;

import java.util.Collection;

/**
 * Created by Joseph Altmaier on 1/20/14.
 */
public interface LandsListener {
    public void setLands(Collection<Land> lands);

    public void landsError();
}
