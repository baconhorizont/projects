package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassRecords {

private List<Student> students = new ArrayList<>();
private String className;
private Random random;

    public ClassRecords(String className, Random random) {
        validateName(className);

        this.className = className;
        this.random = random;
    }

    private void validateName(String className) {
        if (className.length() == 0){
            throw new IllegalArgumentException("Name must not be empty!");
        }
    }

    public boolean addStudent(String name){
      if (isStudentExist(name)){
          return false;
      }
      students.add(new Student(name));
       return true;
    }
    public boolean removeStudent(String name){
        if (isStudentExist(name)) {
        students.remove(findStudentByName(name));
            return true;
        }
        return false;
    }

    private boolean isStudentExist(String name) {
        validateName(name);
        for (Student actual : students) {
            if (actual.getName().equals(name)){
                return true;
            }
        }
        return false;
    }


    public double calculateClassAverageBySubject(String subjectName){
        validateName(subjectName);
        double result = 0.0;
        int counter = 0;
        for (Student actual : students) {
            double temp = actual.calculateSubjectAverage(subjectName);
           if (temp > 0.0){
               result += temp;
               counter++;
           }
        }
        return result / counter;
    }

    public Student findStudentByName(String name){
        validateName(name);
        if (students.isEmpty()){
            throw new IllegalArgumentException("No students to search!");
        }
        for (Student actual : students) {
            if (actual.getName().equals(name)){
                return actual;
            }
        }
        throw new IllegalArgumentException("No student found with name: " + name);
    }

    public Student repetition(){
        if (students.isEmpty()){
            throw new IllegalArgumentException("No students to select for repetition!");
        }
        return students.get(random.nextInt(students.size()));
    }

    public List<SubjectResult> listSubjectResults(String subjectName) {
        validateName(subjectName);
        List<SubjectResult> subjectResults = new ArrayList<>();

        for (Student actual : students) {
            double temp = actual.calculateSubjectAverage(subjectName);
            if (temp > 0.0) {
                subjectResults.add(new SubjectResult(actual.getName(), temp));
            }
        }
            return subjectResults;
    }

    public String listStudentNames(){
        StringBuilder sb = new StringBuilder();
        for (Student actual : students) {
            sb.append(actual.getName());
            sb.append(", ");
        }

        return sb.substring(0,sb.length()-2);
    }

    public String getClassName() {
        return className;
    }
}
