package com.virtualpm.main.listenerinterfaces;

/**
 * Created by Joseph Altmaier on 1/20/14.
 */
public interface SubmitListener {
    public void submitSuccess();

    public void submitFailure(int code, String reason);
}
