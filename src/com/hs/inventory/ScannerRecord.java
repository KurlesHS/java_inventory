package com.hs.inventory;

class ScannerRecord extends Record {
    static final int WIFI_FLAG = 0x01;
    static final int ETHERNET_FLAG = 0x02;

    private int networkInterfaces;
    private boolean isColor;

    @Override
    public String getType() {
        return "Сканнер";
    }

    void setColor(boolean color) {
        isColor = color;
    }

    void setNetworkInterfaces(int interfaceFlag) {
        this.networkInterfaces |= interfaceFlag;
    }

    @Override
    public String toString() {
        String color = isColor ? "цветной" : "ч/б";
        String interfaces = "";
        if ((networkInterfaces & ETHERNET_FLAG) != 0) {
            interfaces = ", с Ethernet";
        }
        if ((networkInterfaces & WIFI_FLAG) != 0) {
            interfaces += ", с WiFi";
        }
        String hasNetwork = interfaces.length() > 0 ?
                "сетевой" : "локальный";

        return String.format("%s - %s, %s%s", super.toString(), color, hasNetwork, interfaces);
    }
}
