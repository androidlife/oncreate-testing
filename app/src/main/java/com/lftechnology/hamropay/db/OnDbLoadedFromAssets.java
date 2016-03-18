package com.lftechnology.hamropay.db;

/**
 */
public class OnDbLoadedFromAssets {

    private static final int WAIT_FOR_REPLY = 0x1, DO_NOT_WAIT_FOR_REPLY = 0x2;
    private int waitForReply = WAIT_FOR_REPLY;

    public void nullify() {
        waitForReply = DO_NOT_WAIT_FOR_REPLY;
    }

    public boolean isObjectNull() {
        return waitForReply == DO_NOT_WAIT_FOR_REPLY;
    }

    public void notifyDbLoadedFromAssets(boolean success) {

    }
}
