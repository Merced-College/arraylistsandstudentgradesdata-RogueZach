import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Name: Zachary Amith
 * Date: 02/22/2026
 * Program: Course Grades Analyzer
 */

public class Main {
    public static void main(String[] args) {
        ArrayList<Course> courses = new ArrayList<>();
        File file = new File("courseAndGradesData.csv");

        // STEP 4: Read CSV and Build ArrayList<Course>
        try {
            Scanner fileScanner = new Scanner(file);
            
            // Skip the header lines (the CSV has 2 header lines)
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // Skips: "Count of,Grade,,,,"
            }
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // Skips: "Course,A,B,C,D,F"
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String courseName = parts[0];
                ArrayList<Integer> grades = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    int count = Integer.parseInt(parts[i].trim());
                    grades.add(count); // index 0 = A, 1 = B, 2 = C, 3 = D, 4 = F
                }

                // IMPORTANT: Do NOT combine duplicates here. Check if it already exists.
                boolean foundDuplicate = false;
                for (Course c : courses) {
                    if (c.getCourseName().equalsIgnoreCase(courseName)) {
                        // If it exists, add the counts together index-by-index
                        ArrayList<Integer> existingGrades = c.getCourseGrades();
                        for (int i = 0; i < 5; i++) {
                            existingGrades.set(i, existingGrades.get(i) + grades.get(i));
                        }
                        foundDuplicate = true;
                        break;
                    }
                }

                // Only add a new Course object if it wasn't already in the list
                if (!foundDuplicate) {
                    courses.add(new Course(courseName, grades));
                }
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'courseAndGradesData.csv' was not found.");
            return;
        }

        // STEP 5: Print a Summary Table
        System.out.println("Course     A     B     C     D     F     Total   A%");
        for (Course c : courses) {
            // Alternatively, you could just print `c.toString()` depending on your preference
            System.out.printf("%-10s %-5d %-5d %-5d %-5d %-5d %-7d %.2f%n",
                c.getCourseName(),
                c.getCourseGrades().get(0), c.getCourseGrades().get(1), c.getCourseGrades().get(2),
                c.getCourseGrades().get(3), c.getCourseGrades().get(4),
                c.getTotalGrades(), c.getAPercent());
        }
        System.out.println();

        // STEP 6: Find the Course with the Highest A%
        if (!courses.isEmpty()) {
            Course best = courses.get(0);
            for (Course c : courses) {
                if (c.getAPercent() > best.getAPercent()) {
                    best = c;
                }
            }
            System.out.println("--- Course with Highest A% ---");
            System.out.println("Course: " + best.getCourseName());
            System.out.printf("A%%: %.2f%%%n", best.getAPercent());
            System.out.println("Total Grades: " + best.getTotalGrades());
            System.out.printf("Counts (A-F): %d, %d, %d, %d, %d%n", 
                best.getCourseGrades().get(0), best.getCourseGrades().get(1), best.getCourseGrades().get(2),
                best.getCourseGrades().get(3), best.getCourseGrades().get(4));
            System.out.println();
        }

        // STEP 7: User Search (Linear Search)
        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Enter a course name to search (e.g., ACTG-31): ");
        String searchTarget = inputScanner.nextLine().trim();
        
        boolean isFound = false;
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(searchTarget)) {
                System.out.println("\nCourse found!");
                System.out.println("Course: " + c.getCourseName());
                System.out.println("Total Grades: " + c.getTotalGrades());
                System.out.printf("A%%: %.2f%%%n", c.getAPercent());
                System.out.printf("Counts (A-F): %d, %d, %d, %d, %d%n", 
                    c.getCourseGrades().get(0), c.getCourseGrades().get(1), c.getCourseGrades().get(2),
                    c.getCourseGrades().get(3), c.getCourseGrades().get(4));
                isFound = true;
                break;
            }
        }
        
        if (!isFound) {
            System.out.println("\nSorry, the course '" + searchTarget + "' was not found in the records.");
        }
        
        inputScanner.close();
    }
}