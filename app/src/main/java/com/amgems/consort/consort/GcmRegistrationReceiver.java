package com.amgems.consort.consort;

/**
 * @author Sherman Pay.
 * @version 0.1, 11/10/14.
 */
public interface GcmRegistrationReceiver {
    public void onReceivedRegistrationId(String id);
    public void onError(String errorMsg);
}
