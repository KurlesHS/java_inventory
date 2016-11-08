package com.hs.inventory;

import java.util.Collection;

interface IRepository {
    boolean addOrUpdateRecord(Record record);
    boolean deleteRecord(int sku);
    boolean hasRecord(int sku);
    Collection<Record> getAllRecords();
    boolean removeAllRecords();

}
