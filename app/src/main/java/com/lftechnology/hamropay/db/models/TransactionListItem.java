package com.lftechnology.hamropay.db.models;

import java.io.Serializable;

/**
 */
public class TransactionListItem implements Serializable {
    public String servicePhoto, serviceName, snippet;
    public long date;
    public long serviceId;
    public int serviceType;
    public boolean isComplete = false;
    public String dateString;
}
