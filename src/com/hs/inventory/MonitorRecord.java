package com.hs.inventory;

public class MonitorRecord extends Record {
    enum Kind {
        Tube,
        Lcd,
        Projector
    }

    private int size;
    private boolean isColor;
    private Kind kind = Kind.Tube;

    void setSize(int size) {
        this.size = size;
    }

    void setColor(boolean color) {
        isColor = color;
    }

    void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public String getType() {
        return "Монитор";
    }

    @Override
    public String toString() {
        String colorStr = isColor ? "цветной" : "ч/б";
        String kindStr;
        String sizeSuffix = StringHelpers.getSuffixForNumber(size, "дюйм", "дюйма", "дюймов");
        switch (kind) {
            case Tube:
                kindStr = "ЭЛТ";
                break;
            case Lcd:
                kindStr = "ЖК";
                break;
            case Projector:
                kindStr = "проектор";
                break;
            default:
                kindStr = "-";
                break;
        }
        return String.format("%s - %s %s, %d %s", super.toString(), colorStr, kindStr, size, sizeSuffix);
    }
}
