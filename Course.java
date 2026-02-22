import java.util.ArrayList;

/*
 * Name: Zachary Amith
 * Date: 02/22/2026
 * Program: Course Grades Analysis Tool - Analyzes a course and its grade counts
*/


public class Course {
    private String courseName;
    private ArrayList<Integer> courseGrades;

    // Constructor
    public Course(String courseName, ArrayList<Integer> courseGrades) {
        this.courseName = courseName;
        this.courseGrades = courseGrades;
    }

    // Getters
    public String getCourseName() {
        return courseName;
    }

    public ArrayList<Integer> getCourseGrades() {
        return courseGrades;
    }

    // Setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseGrades(ArrayList<Integer> courseGrades) {
        this.courseGrades = courseGrades;
    }

    // Returns the total sum of A+B+C+D+F
    public int getTotalGrades() {
        int total = 0;
        for (int value : courseGrades) {
            total += value;
        }
        return total;
    }

    // Returns the percentage of A's as a double
    public double getAPercent() {
        int total = getTotalGrades();
        if (total == 0) {
            return 0.0;
        }
        return (double) courseGrades.get(0) / total * 100.0;
    }

    // toString method
    @Override
    public String toString() {
        return String.format("%-10s %-5d %-5d %-5d %-5d %-5d %-7d %.2f%%", 
            courseName, 
            courseGrades.get(0), courseGrades.get(1), courseGrades.get(2), 
            courseGrades.get(3), courseGrades.get(4), 
            getTotalGrades(), getAPercent());
    }
}