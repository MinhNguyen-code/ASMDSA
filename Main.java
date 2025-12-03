import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagement manager = new StudentManagement();

    public static void main(String[] args) {
        int choice = -1;
        
        // Sample data for quick testing
        manager.addStudent("Nguyen Van A", 7.5);
        manager.addStudent("Tran Thi B", 9.0);
        manager.addStudent("Le Van C", 6.0);
        
        while (choice != 0) {
            try {
                displayMenu();
                System.out.print("Choose: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                System.out.println("------------------------------");
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        editStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        searchStudent();
                        break;
                    case 5:
                        sortStudents();
                        break;
                    case 6:
                        manager.displayAllStudents();
                        break;
                    case 0:
                        System.out.println("Program terminated. Goodbye!"); 
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again."); 
                }
                System.out.println("------------------------------");

            } catch (InputMismatchException e) {
                System.out.println("Input error. Please enter a number for the menu choice."); 
                scanner.nextLine(); // Clear incorrect input
                choice = -1;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage()); 
            }
        }
    }
    
    // Menu interface
    private static void displayMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT =====");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Search Student (Binary Search/Linear Search)");
        System.out.println("5. Sort Students by Marks/ID (QuickSort/Selection Sort)");
        System.out.println("6. Display All Students");
        System.out.println("0. Exit");
        System.out.println("==============================");
    }
    
    // Function 1: Add Student (with Marks validation 0.0 - 10.0)
    private static void addStudent() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        double marks = -1;
        boolean isValidInput = false;

        do {
            try {
                System.out.print("Enter Marks (0.0 - 10.0): ");
                if (scanner.hasNextDouble()) {
                    marks = scanner.nextDouble();
                    if (marks >= 0.0 && marks <= 10.0) {
                        isValidInput = true;
                    } else {
                        System.out.println("-> Marks must be between 0.0 and 10.0. Please re-enter.");
                    }
                } else {
                    System.out.println("-> Invalid input for Marks. Please enter a number.");
                }
                scanner.nextLine(); // Consume newline/Clear invalid input
            } catch (Exception e) {
                System.out.println("-> An unexpected error occurred during marks input.");
                scanner.nextLine();
            }
        } while (!isValidInput);

        manager.addStudent(name, marks);
        System.out.println("-> Student added successfully.");
    }
    
    // Function 2: Edit Student (with Marks validation 0.0 - 10.0)
    private static void editStudent() {
        int id = -1;
        boolean isIdValid = false;

        do {
            try {
                System.out.print("Enter Student ID to Edit: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    isIdValid = true;
                } else {
                    System.out.println("-> Invalid ID input. Please enter a number.");
                }
                scanner.nextLine(); 
            } catch (Exception e) {
                scanner.nextLine(); // Clear input
            }
        } while (!isIdValid);
        
        System.out.print("Enter New Name: ");
        String newName = scanner.nextLine();
        
        double newMarks = -1;
        boolean isMarksValid = false;

        do {
            try {
                System.out.print("Enter New Marks (0.0 - 10.0): ");
                if (scanner.hasNextDouble()) {
                    newMarks = scanner.nextDouble();
                    if (newMarks >= 0.0 && newMarks <= 10.0) {
                        isMarksValid = true;
                    } else {
                        System.out.println("-> New Marks must be between 0.0 and 10.0. Please re-enter.");
                    }
                } else {
                    System.out.println("-> Invalid input for New Marks. Please enter a number.");
                }
                scanner.nextLine(); 
            } catch (Exception e) {
                scanner.nextLine(); 
            }
        } while (!isMarksValid);
        
        if (manager.editStudent(id, newName, newMarks)) {
            System.out.println("-> Student ID " + id + " updated successfully.");
        } else {
            System.out.println("-> Student with ID: " + id + " not found.");
        }
    }
    
    // Function 3: Delete Student
    private static void deleteStudent() {
        int id = -1;
        boolean isIdValid = false;

        do {
            try {
                System.out.print("Enter Student ID to Delete: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    isIdValid = true;
                } else {
                    System.out.println("-> Invalid ID input. Please enter a number.");
                }
                scanner.nextLine(); 
            } catch (Exception e) {
                scanner.nextLine(); 
            }
        } while (!isIdValid);
        
        if (manager.deleteStudent(id)) {
             System.out.println("-> Student ID " + id + " deleted successfully.");
        } else {
            System.out.println("-> Student with ID: " + id + " not found.");
        }
    }

    // Function 4: Search Student (Using 2 search algorithms)
    private static void searchStudent() {
        System.out.println("--- Search Options ---");
        System.out.println("1. Search by Name (Linear Search)");
        System.out.println("2. Search by Marks (Binary Search)");
        System.out.print("Choose search method: ");
        
        int searchChoice = -1;
        try {
            if (scanner.hasNextInt()) {
                searchChoice = scanner.nextInt();
            }
            scanner.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("-> Invalid choice. Returning to main menu.");
            scanner.nextLine();
            return;
        }

        if (searchChoice == 1) {
            System.out.print("Enter Name to search: ");
            String name = scanner.nextLine();
            List<Student> results = manager.searchByNameLinear(name);
            if (results.isEmpty()) {
                System.out.println("-> No student found with the name '" + name + "'.");
            } else {
                System.out.println("\n===== SEARCH RESULTS BY NAME =====");
                results.forEach(System.out::println);
                System.out.println("====================================\n");
            }
        } else if (searchChoice == 2) {
            System.out.print("Enter Marks to search (exact match): ");
            double marks = -1;
            
            try {
                if (scanner.hasNextDouble()) {
                    marks = scanner.nextDouble();
                } else {
                    System.out.println("-> Invalid Marks input. Returning to main menu.");
                    scanner.nextLine();
                    return;
                }
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("-> Invalid Marks input. Returning to main menu.");
                scanner.nextLine();
                return;
            }
            
            // Binary Search requires the list to be sorted.
            Student result = manager.searchByMarksBinary(marks);
            if (result == null) {
                System.out.println("-> No student found with marks " + marks + " (after temporary sort).");
            } else {
                System.out.println("\n===== SEARCH RESULTS BY MARKS (Binary Search) =====");
                System.out.println(result);
                System.out.println("===================================================\n");
            }
        } else {
            System.out.println("Invalid search choice."); 
        }
    }
    
    // Function 5: Sort Student (Using 2 sort algorithms)
    private static void sortStudents() {
        System.out.println("--- Sort Options ---");
        System.out.println("1. Sort by Marks (QuickSort)");
        System.out.println("2. Sort by ID (Selection Sort)");
        System.out.print("Choose sort method: ");
        
        int sortChoice = -1;
        try {
            if (scanner.hasNextInt()) {
                sortChoice = scanner.nextInt();
            }
            scanner.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("-> Invalid choice. Returning to main menu.");
            scanner.nextLine();
            return;
        }

        if (sortChoice == 1) {
            manager.sortStudentsByMarksQuickSort();
            System.out.println("-> Students sorted by Marks (QuickSort).");
        } else if (sortChoice == 2) {
            manager.sortStudentsByIdSelectionSort();
             System.out.println("-> Students sorted by ID (Selection Sort).");
        } else {
            System.out.println("Invalid sort choice."); 
        }
    }
}