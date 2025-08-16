import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("\n✅ Car rented successfully for " + days + " days. Total: $" + car.calculatePrice(days));
        } else {
            System.out.println("\n❌ Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("\n✅ Car returned successfully.");
        } else {
            System.out.println("\n❌ Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("Cars currently rented: " + rentals.size());
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.print("Enter Car ID to rent: ");
                String carId = scanner.nextLine();
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar == null) {
                    System.out.println("\n❌ Invalid Car ID or Car not available.");
                    continue;
                }
                System.out.print("Enter your name: ");
                String name = scanner.nextLine();
                Customer customer = new Customer("CUS" + (customers.size() + 1), name);
                addCustomer(customer);

                System.out.print("Enter number of days: ");
                int days = scanner.nextInt();
                scanner.nextLine();

                rentCar(selectedCar, customer, days);

            } else if (choice == 2) {
                if (rentals.isEmpty()) {
                    System.out.println("\n❌ No cars are currently rented.");
                    continue;
                }
                System.out.println("\nRented Cars:");
                for (Rental rental : rentals) {
                    System.out.println(rental.getCar().getCarId() + " - " + rental.getCar().getBrand() + " " +
                            rental.getCar().getModel() + " (Customer: " + rental.getCustomer().getName() + ")");
                }
                System.out.print("Enter Car ID to return: ");
                String carId = scanner.nextLine();
                Car carToReturn = null;
                for (Rental rental : rentals) {
                    if (rental.getCar().getCarId().equalsIgnoreCase(carId)) {
                        carToReturn = rental.getCar();
                        break;
                    }
                }
                if (carToReturn != null) {
                    returnCar(carToReturn);
                } else {
                    System.out.println("\n❌ Invalid Car ID.");
                }

            } else if (choice == 3) {
                if (rentals.isEmpty()) {
                    break;
                } else {
                    System.out.println("\n⚠ You cannot exit while cars are still rented. Please return all rented cars first.");
                }

            } else {
                System.out.println("\n❌ Invalid choice. Please enter a valid option.");
            }
        }
        scanner.close();
        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Carr {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Car("C001", "Toyota", "Fortuner", 60.0));
        rentalSystem.addCar(new Car("C002", "Hyundai", "Creta", 70.0));
        rentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 150.0));

        rentalSystem.menu();
    }
}
