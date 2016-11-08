package com.hs.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


class MonitorRecordCreator implements IRecordCreator {
    private static final String COLOR_PARAM_NAME = "color";
    private static final String KIND_PARAM_NAME = "kind";
    private static final String SIZE_PARAM_NAME = "size";

    @Override
    public Record create(Map<String, String> params) throws InventoryParamsException {
        Collection<String> missingParams = new ArrayList<>();
        MonitorRecord record = new MonitorRecord();
        try {
            ParametersParser.fillCommonParams(record, params);
        } catch (MissingParamsException e) {
            missingParams = e.getParamsNames();
        }

        String colorStr = params.get(COLOR_PARAM_NAME);
        String kindStr = params.get(KIND_PARAM_NAME);
        String sizeStr = params.get(SIZE_PARAM_NAME);

        if (colorStr == null) {
            missingParams.add(COLOR_PARAM_NAME);
        } else {
            try {
                record.setColor(StringHelpers.toBool(colorStr));
            } catch (ClassCastException e) {
                throw new InventoryParamsException(String.format("add monitor: неверный параметр '--%s': '%s'. " +
                        "Допустимые значения: true|false", COLOR_PARAM_NAME, colorStr));
            }
        }

        if (kindStr == null) {
            missingParams.add(KIND_PARAM_NAME);
        } else {
            switch (kindStr) {
                case "TUBE":
                    record.setKind(MonitorRecord.Kind.Tube);
                    break;
                case "LCD":
                    record.setKind(MonitorRecord.Kind.Lcd);
                    break;
                case "PROJECTOR":
                    record.setKind(MonitorRecord.Kind.Projector);
                    break;
                default:
                    throw new InventoryParamsException(String.format("add monitor: неверный параметр '--%s': '%s'",
                            KIND_PARAM_NAME, kindStr));
            }
        }

        if (sizeStr == null) {
            missingParams.add(SIZE_PARAM_NAME);
        } else {
            try {
                int size = Integer.parseInt(sizeStr);
                if (size <= 0) {
                    throw new InventoryParamsException(String.format("add monitor: параметр '--%s' должен быть больше нуля",
                            SIZE_PARAM_NAME));
                }
                record.setSize(size);
            } catch (NumberFormatException e) {
                throw new InventoryParamsException(String.format("add monitor: неверный параметр '--%s': '%s'. ",
                        SIZE_PARAM_NAME, sizeStr));
            }
        }

        if (missingParams.size() > 0) {
            throw new InventoryParamsException(String.format("add monitor: не указаны обязательные параметры: %s",
                    StringHelpers.join(", ", missingParams)));
        }
        return record;
    }

    @Override
    public String name() {
        return "MONITOR";
    }
}
