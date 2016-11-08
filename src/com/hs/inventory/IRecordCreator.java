package com.hs.inventory;

import java.util.Map;

interface IRecordCreator {
    Record create(Map<String, String> params) throws InventoryParamsException;
    String name();
}
