package com.example;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Laptop> laptops = Arrays.asList(
            new Laptop("Lenovo IdeaPad 3", "Lenovo", 8, 500_000_000, 14.0, "Intel Core i5"),
            new Laptop("HP Pavilion x360", "HP", 16, 1_000_000_000, 15.6, "AMD Ryzen 7"),
            new Laptop("Asus ZenBook Pro Duo", "Asus", 32, 2_000_000_000, 15.6, "Intel Core i9"),
            new Laptop("Dell XPS 13", "Dell", 8, 500_000_000, 13.3, "Intel Core i5"),
            new Laptop("Apple MacBook Air", "Apple", 8, 512_000_000, 13.3, "Apple M1"),
            new Laptop("Microsoft Surface Laptop 4", "Microsoft", 16, 1_000_000_000, 13.5, "AMD Ryzen 7"),
            new Laptop("Google Pixelbook Go", "Google", 8, 512_000_000, 13.3, "Intel Core m3"),
            new Laptop("Acer Aspire 5", "Acer", 8, 512_000_000, 15.6, "AMD Ryzen 5"),
            new Laptop("MSI Prestige 14 Evo", "MSI", 16, 1_000_000_000, 14.1, "Intel Core i7"),
            new Laptop("Samsung Galaxy Book Pro", "Samsung", 16, 1_000_000_000, 13.3, "AMD Ryzen 7"),
            new Laptop("HP Envy 13", "HP", 8, 512_000_000, 13.3, "AMD Ryzen 7"),
            new Laptop("Lenovo ThinkPad X1 Carbon Gen 9", "Lenovo", 16, 1_000_000_000, 14.0, "Intel Core i7"),
            new Laptop("Asus VivoBook S15", "Asus", 8, 512_000_000, 15.6, "AMD Ryzen 7"),
            new Laptop("Microsoft Surface Pro 8", "Microsoft", 16, 1_000_000_000, 13.0, "Intel Core i7"),
            new Laptop("Alienware m15 R6", "Alienware", 32, 2_000_000_000, 15.6, "Intel Core i9"),
            new Laptop("Dell XPS 17", "Dell", 32, 2_000_000_000, 15.6, "Intel Core i9"),
            new Laptop("HP Spectre x360 15T", "HP", 32, 2_000_000_000, 15.6, "Intel Core i9"),
            new Laptop("Apple MacBook Pro 16", "Apple", 32, 2_000_000_000, 16.0, "Apple M1 Pro"),
            new Laptop("Google Pixelbook Go", "Google", 8, 512_000_000, 13.3, "Intel Core m3"),
            new Laptop("Microsoft Surface Laptop Studio", "Microsoft", 32, 2_000_000_000, 14.0, "Intel Core i7"),
            new Laptop("Acer Swift 5", "Acer", 8, 512_000_000, 14.0, "AMD Ryzen 7"),
            new Laptop("Samsung Galaxy Book Pro 360", "Samsung", 16, 1_000_000_000, 13.3, "AMD Ryzen 7"),
            new Laptop("MSI Modern 14 B10M", "MSI", 8, 512_000_000, 14.0, "AMD Ryzen 7")
        );  
        

        Scanner scanner = new Scanner(System.in);

        // Пользовательский ввод для выбора параметров фильтрации
        System.out.println("Выберите минимальный размер оперативной памяти (в ГБ): ");
        Optional<Integer> ramSize = askForInteger(scanner);
        if (ramSize.isPresent()) {
            System.out.println("Выберите минимальный объем жесткого диска (в ТБ): ");
            Optional<Long> hddCapacity = askForLong(scanner);
            if (hddCapacity.isPresent()) {
                System.out.println("Выберите минимальный размер экрана (в дюймах): ");
                Optional<Double> screenSize = askForDouble(scanner);
                if (screenSize.isPresent()) {
                    System.out.println("Выберите производителя: ");
                    System.out.println();
                    for (Laptop laptop : laptops) {
                        if (!laptop.getManufacturer().equals("")) {
                            System.out.print("\t" + laptop.getManufacturer() + "\t\t");
                        }
                    }
                    System.out.println();

                    String input = scanner.nextLine();

                    List<Laptop> filteredLaptops = filterByCriteria(laptops, ramSize.get(), hddCapacity.get(), screenSize.get(), input);

                    if (filteredLaptops.isEmpty()) {
                        System.out.println("Нет ноутбуков от выбранного производителя.");
                    } else {
                        System.out.println("Выберите ноутбук по модели: ");
                        for (Laptop laptop : filteredLaptops) {
                            System.out.print("\t" + laptop.getModelName() + "\t\t");
                        }
                        System.out.println();

                        input = scanner.nextLine();

                        Laptop selectedLaptop = getSelectedLaptop(filteredLaptops, input);
                        if (selectedLaptop != null) {
                            System.out.println("Ваш выбор: " + selectedLaptop.toString());
                        } else {
                            System.out.println("Ноутбук с такой моделью не найден.");
                        }
                    }
                } else {
                    System.out.println("Неправильное значение размера экрана. Попробуйте еще раз.");
                }
            } else {
                System.out.println("Неправильное значение объема жесткого диска. Попробуйте еще раз.");
            }
        } else {
            System.out.println("Неправильное значение размера оперативной памяти. Попробуйте еще раз.");
        }
    }

    private static List<Laptop> filterByCriteria(List<Laptop> laptops, int minRAMSize, long minHDDCapacity, double minScreenSize, String manufacturer) {
        Predicate<Laptop> byRAMSize = laptop -> laptop.getRamSize() >= minRAMSize;
        Predicate<Laptop> byHDDCapacity = laptop -> laptop.getHddCapacity() >= minHDDCapacity;
        Predicate<Laptop> byScreenSize = laptop -> laptop.getScreenSize() >= minScreenSize;
        Predicate<Laptop> byManufacturer = laptop -> laptop.getManufacturer().toLowerCase().contains(manufacturer.toLowerCase());
        return laptops.stream().filter(byRAMSize.and(byHDDCapacity).and(byScreenSize).and(byManufacturer)).collect(Collectors.toList());
    }

    private static List<Laptop> filterByManufacturer(List<Laptop> laptops, String manufacturer) {
        Predicate<Laptop> byManufacturer = laptop -> laptop.getManufacturer().toLowerCase().contains(manufacturer.toLowerCase());
        return laptops.stream().filter(byManufacturer).collect(Collectors.toList());
    }

    private static List<Laptop> filterByRAMSize(List<Laptop> laptops, int minRAMSize) {
        Predicate<Laptop> byRAMSize = laptop -> laptop.getRamSize() >= minRAMSize;
        return laptops.stream().filter(byRAMSize).collect(Collectors.toList());
    }

    private static List<Laptop> filterByHDDCapacity(List<Laptop> laptops, long minHDDCapacity) {
        Predicate<Laptop> byHDDCapacity = laptop -> laptop.getHddCapacity() >= minHDDCapacity;
        return laptops.stream().filter(byHDDCapacity).collect(Collectors.toList());
    }

    private static List<Laptop> filterByScreenSize(List<Laptop> laptops, double minScreenSize) {
        Predicate<Laptop> byScreenSize = laptop -> laptop.getScreenSize() >= minScreenSize;
        return laptops.stream().filter(byScreenSize).collect(Collectors.toList());
    }

    private static Laptop getSelectedLaptop(List<Laptop> laptops, String modelName) {
        for (Laptop laptop : laptops) {
            if (laptop.getModelName().equalsIgnoreCase(modelName)) {
                return laptop;
            }
        }
        return null;
    }

    private static Optional<Integer> askForInteger(Scanner scanner) {
        try {
            return Optional.of(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Long> askForLong(Scanner scanner) {
        try {
            return Optional.of(Long.parseLong(scanner.nextLine()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Double> askForDouble(Scanner scanner) {
        try {
            return Optional.of(Double.parseDouble(scanner.nextLine()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

class Laptop {
    private final String modelName;
    private final String manufacturer;
    private final int ramSize;
    private final long hddCapacity;
    private final double screenSize;
    private final String processorType;

    public Laptop(String modelName, String manufacturer, int ramSize, long hddCapacity, double screenSize, String processorType) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.ramSize = ramSize;
        this.hddCapacity = hddCapacity;
        this.screenSize = screenSize;
        this.processorType = processorType;
    }

    public String getModelName() {
        return modelName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getRamSize() {
        return ramSize;
    }

    public long getHddCapacity() {
        return hddCapacity;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public String getProcessorType() {
        return processorType;
    }

    @Override
    public String toString() {
        return "Модель: " + modelName + ", Производитель: " + manufacturer + ", RAM: " + ramSize + "ГБ, HDD: " + hddCapacity / 1000000000 + "ТБ, Экран: " + screenSize + " дюйм, Процессор: " + processorType;
    }
}