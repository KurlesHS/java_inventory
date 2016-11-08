package com.hs.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

class PrinterRecordCreator implements IRecordCreator {
    private static final String COLOR_PARAM_NAME = "color";
    private static final String NETWORK_INTERFACES_PARAM_NAME = "network";



    @Override
    public Record create(Map<String, String> params) throws InventoryParamsException {
        Collection<String> missingParams = new ArrayList<>();
        PrinterRecord record = new PrinterRecord();
        try {
            ParametersParser.fillCommonParams(record, params);
        } catch (MissingParamsException e) {
            missingParams = e.getParamsNames();
        }

        String isColorStr = params.get(COLOR_PARAM_NAME);
        String isNetworkStr = params.get(NETWORK_INTERFACES_PARAM_NAME);

        if (isColorStr == null) {
            missingParams.add(COLOR_PARAM_NAME);
        } else {
            record.setColor(getBooleanHelper(COLOR_PARAM_NAME, isColorStr));
        }

        if (isNetworkStr == null) {
            missingParams.add(NETWORK_INTERFACES_PARAM_NAME);
        } else {
            record.setNetwork(getBooleanHelper(NETWORK_INTERFACES_PARAM_NAME, isNetworkStr));
        }

        if (missingParams.size() > 0) {
            throw new InventoryParamsException(String.format("add printer: не указаны обязательные параметры: %s",
                    StringHelpers.join(", ", missingParams)));
        }
        return record;
    }

    private boolean getBooleanHelper(String paramName, String value) throws InventoryParamsException {
        try {
            return StringHelpers.toBool(value);
        } catch (ClassCastException e) {
            throw new InventoryParamsException(String.format("add printer: неверный параметр '--%s': '%s'. " +
                    "Допустимые значения: true|false", paramName, value));
        }
    }

    @Override
    public String name() {
        return "PRINTER";
    }
}
