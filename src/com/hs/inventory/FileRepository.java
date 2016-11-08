package com.hs.inventory;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

class FileRepository implements IRepository {
    private static final String FILENAME = "database.dat";
    private HashMap<Integer, Record> fakeBd = new HashMap<>();

    FileRepository() {
        readDatabase();
    }

    /* stackoverflow says that it is safe */
    @SuppressWarnings("unchecked")
    private void readDatabase() {
        try (FileInputStream stream = new FileInputStream(FILENAME)) {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(stream));
            fakeBd = (HashMap<Integer, Record>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            /* считаем, что у нас пустая ДБ */
        }
    }

    private boolean updateDatabase() {
        boolean positive = false;
        try (FileOutputStream stream = new FileOutputStream(FILENAME)) {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(stream));
            out.writeObject(fakeBd);
            positive = true;
            out.close();
            stream.close();
        } catch (IOException e) {
            /* ух ты, беда */
            System.err.println("Не получилось обновить базу данных");
        }
        return positive;
    }

    @Override
    public boolean addOrUpdateRecord(Record record) {
        fakeBd.put(record.getSku(), record);
        return updateDatabase();
    }

    @Override
    public boolean deleteRecord(int sku) {
        boolean result = false;
        if (hasRecord(sku)) {
            fakeBd.remove(sku);
            result = updateDatabase();
        }
        return result;
    }

    @Override
    public boolean hasRecord(int sku) {
        return fakeBd.containsKey(sku);
    }

    @Override
    public Collection<Record> getAllRecords() {
        return fakeBd.values();
    }

    @Override
    public boolean removeAllRecords() {
        fakeBd.clear();
        return updateDatabase();
    }
}
