package com.hs.inventory;

import java.util.Collection;

class MissingParamsException extends Exception {
    private Collection<String> paramsName;

    MissingParamsException(Collection<String> paramsName) {
        super(String.format("Не указаны обязательные параметры: %s", StringHelpers.join(", ", paramsName)));
        this.paramsName = paramsName;
    }

    Collection<String> getParamsNames() {
        return paramsName;
    }
}
