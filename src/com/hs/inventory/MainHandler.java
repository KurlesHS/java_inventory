package com.hs.inventory;

import java.util.*;


class MainHandler {
    private IRepository repository;
    private Map<String, IRecordCreator> recordCreators = new HashMap<>();

    MainHandler(IRepository repository) {
        this.repository = repository;
    }

    public void setCreator(IRecordCreator creator)
    {
        addCreator(creator);
    }

    public void addCreator(IRecordCreator creator) {
        recordCreators.put(creator.name(), creator);
    }

    void process(String[] args) throws InventoryParamsException {
        if (args.length < 1) {
            throw new InventoryParamsException("Слишком мало параметров");
        }
        String command = args[0];
        Map<String, String> params = ParametersParser.parseArguments(args);

        switch (command) {
            case "add":
                handleAddCommand(params);
                break;
            case "delete":
                handleDeleteCommand(params);
                break;
            case "list":
                handleListCommand();
                break;
            default:
                throw new InventoryParamsException(String.format("Неизвестная команда '%s'", command));
        }
    }

    private void handleAddCommand(Map<String, String> params) throws InventoryParamsException {

        String type = params.get("type");
        if (type == null) {
            throw new InventoryParamsException("Не указан тип инвертаря");
        }
        IRecordCreator creator = recordCreators.get(type);
        if (creator != null) {
            Record record = creator.create(params);
            if (repository.hasRecord(record.getSku())) {
                System.out.println("Запись с таким SKU уже существует, перезаписываю");
            }
            repository.addOrUpdateRecord(record);
            System.out.println(String.format("Добавлен инвентарь: %s", record.toString()));
        } else {
            throw new InventoryParamsException(String.format("add: неизвестный тип инвертаря: '%s'", type));
        }
    }

    private void handleDeleteCommand(Map<String, String> params) throws InventoryParamsException {
        try {
            int sku = ParametersParser.getSku(params);
            if (repository.deleteRecord(sku)) {
                System.out.println(String.format("Запись с SKU %d удалена", sku));
            } else {
                System.out.println(String.format("Указнный SKU (%d) не найден или ошибка обновления БД", sku));
            }
        } catch (MissingParamException e) {
            /* отлавливаем только исключение, сигнализирующее об отсутствующем параметре */
            System.out.print("Удалить все записи? y/N? ");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine().toLowerCase();
            List<String> positiveAnswers = Arrays.asList("y", "yes", "да", "д");
            if (positiveAnswers.contains(line)) {
                if (repository.removeAllRecords()) {
                    System.out.println("БД очищена");
                } else {
                    System.out.println("Ошибка очистики БД");
                }
            } else {
                System.out.println("Отмена операции очистки БД");
            }
        }
    }

    private void handleListCommand() {
        Collection<Record> allRecords = repository.getAllRecords();
        if (allRecords.size() > 0) {
            System.out.println("Список оборудования:");
            for (Record record : allRecords) {
                System.out.println(String.format("   %d - %s", record.getSku(), record.toString()));
            }
        } else {
            System.out.println("БД не содержит записей");
        }
    }
}
