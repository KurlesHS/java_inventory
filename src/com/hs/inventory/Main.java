package com.hs.inventory;

public class Main {

    public static void main(String[] args) {
        IRepository repository = new FileRepository();
        MainHandler handler = new MainHandler(repository);
        handler.addCreator(new PrinterRecordCreator());
        handler.addCreator(new MonitorRecordCreator());
        handler.addCreator(new ScannerRecordCreator());
        try {
            handler.process(args);
        } catch (InventoryParamsException e) {
            System.err.println(e.getMessage());
        }
    }
}
