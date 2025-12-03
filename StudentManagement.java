import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StudentManagement {
    // Data Structure 1: ArrayList (used for display and sorting)
    private List<Student> studentList;
    // Data Structure 2: HashMap (used for quick search, edit, delete by ID)
    private HashMap<Integer, Student> studentMap; 
    private int nextId = 1; // Auto-increment ID

    public StudentManagement() {
        this.studentList = new ArrayList<>();
        this.studentMap = new HashMap<>();
    }

    // === 1. Add Student ===
    public void addStudent(String name, double marks) {
        Student newStudent = new Student(nextId, name, marks);
        studentList.add(newStudent);
        studentMap.put(nextId, newStudent);
        nextId++;
        System.out.println("-> Student added successfully. ID: " + newStudent.getId());
    }

    // === 2. Edit Student ===
    // Uses HashMap for O(1) average lookup
    public boolean editStudent(int id, String newName, double newMarks) {
        Student studentToEdit = studentMap.get(id); // O(1) average
        if (studentToEdit != null) {
            studentToEdit.setName(newName);
            studentToEdit.setMarks(newMarks);
            System.out.println("-> Student information for ID " + id + " updated successfully.");
            return true;
        }
        return false;
    }

    // === 3. Delete Student ===
    // Uses HashMap for O(1) average lookup and deletion
    public boolean deleteStudent(int id) {
        Student studentToRemove = studentMap.remove(id); // Remove from HashMap O(1) average
        if (studentToRemove != null) {
            studentList.remove(studentToRemove); // Remove from ArrayList O(n)
            System.out.println("-> Student ID " + id + " deleted successfully.");
            return true;
        }
        return false;
    }

    // === 4. Search Student ===
    // Algorithm 1: Binary Search (used for searching by Marks)
    public Student searchByMarksBinary(double targetMarks) {
        // Requirement: The list must be sorted by Marks before searching
        List<Student> sortedList = new ArrayList<>(studentList);
        // Temporarily sort using QuickSort to ensure Binary Search works
        quickSort(sortedList, 0, sortedList.size() - 1, Comparator.comparingDouble(Student::getMarks));
        
        int low = 0;
        int high = sortedList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedList.get(mid).getMarks() == targetMarks) {
                return sortedList.get(mid);
            } else if (sortedList.get(mid).getMarks() < targetMarks) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
    
    // Algorithm 2: Sequential/Linear Search (used for searching by Name)
    public List<Student> searchByNameLinear(String name) {
        List<Student> foundStudents = new ArrayList<>();
        // Iterate through the entire student list O(n)
        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(name)) {
                foundStudents.add(student);
            }
        }
        return foundStudents;
    }
    
    // === 5. Sort Students ===

    // Algorithm 1: QuickSort (Sort by Marks)
    public void sortStudentsByMarksQuickSort() {
        quickSort(studentList, 0, studentList.size() - 1, Comparator.comparingDouble(Student::getMarks));
        System.out.println("-> List has been sorted by Marks (QuickSort).");
    }

    // QuickSort Implementation (Recursive)
    private void quickSort(List<Student> list, int low, int high, Comparator<Student> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private int partition(List<Student> list, int low, int high, Comparator<Student> comparator) {
        Student pivot = list.get(high);
        int i = (low - 1); 

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
    
    // Algorithm 2: Selection Sort (Sort by ID)
    public void sortStudentsByIdSelectionSort() {
        int n = studentList.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (studentList.get(j).getId() < studentList.get(minIdx).getId()) {
                    minIdx = j;
                }
            }
            Collections.swap(studentList, minIdx, i);
        }
        System.out.println("-> List has been sorted by ID (Selection Sort).");
    }

    // === 6. Display All Students ===
    public void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("-> Student list is empty.");
            return;
        }
        System.out.println("\n===== STUDENT LIST =====");
        for (Student student : studentList) {
            System.out.println(student);
        }
        System.out.println("========================\n");
    }
}