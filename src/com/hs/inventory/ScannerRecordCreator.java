package com.hs.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


class ScannerRecordCreator implements IRecordCreator {
    private static final String COLOR_PARAM_NAME = "color";
    private static final String NETWORK_INTERFACES_PARAM_NAME = "network";
    private static final String ETHERNET_STR = "ETHERNET";
    private static final String WIFI_STR = "WIFI";

    @Override
    public Record create(Map<String, String> params) throws InventoryParamsException {
        Collection<String> missingParams = new ArrayList<>();
        ScannerRecord record = new ScannerRecord();
        try {
            ParametersParser.fillCommonParams(record, params);
        } catch (MissingParamsException e) {
            missingParams = e.getParamsNames();
        }

        String networkInterfaces = params.get(NETWORK_INTERFACES_PARAM_NAME);
        String colorStr = params.get(COLOR_PARAM_NAME);
        /* параметр 'network' не обязательный */
        if (networkInterfaces != null) {
            String[] interfaces = networkInterfaces.split("[\\|,\\-\\s]");
            for (String iFace : interfaces) {
                switch (iFace) {
                    case ETHERNET_STR:
                        record.setNetworkInterfaces(ScannerRecord.ETHERNET_FLAG);
                        break;
                    case WIFI_STR:
                        record.setNetworkInterfaces(ScannerRecord.WIFI_FLAG);
                        break;
                    default:
                        break;
                }
            }
        }

        if (colorStr == null) {
            missingParams.add(COLOR_PARAM_NAME);
        } else {
            try {
                record.setColor(StringHelpers.toBool(colorStr));
            } catch (ClassCastException e) {
                throw new InventoryParamsException(String.format("add scanner: неверный параметр '--%s': '%s'. " +
                        "Допустимые значения: true|false", COLOR_PARAM_NAME, colorStr));
            }
        }

        if (missingParams.size() > 0) {
            throw new InventoryParamsException(String.format("add scanner: не указаны обязательные параметры: %s",
                    StringHelpers.join(", ", missingParams)));
        }
        return record;
    }

    @Override
    public String name() {
        return "SCANNER";
    }
}
