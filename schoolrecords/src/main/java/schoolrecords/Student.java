package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String name;
    private List<Mark> marks = new ArrayList<>();

    public Student(String name) {
        if (name.length() == 0){
        throw new IllegalArgumentException("Student name must not be empty!");
        }
        this.name = name;
    }
    public void addGrading(Mark mark){
        if (mark == null){
            throw new IllegalArgumentException("Mark must not be null!");
        }
        marks.add(mark);
    }

    public double calculateSubjectAverage(String subjectName){
        double result = 0.0;
        List<Integer> marksToCalculate = getMarksAccordingToSubject(subjectName);
        if (marksToCalculate.isEmpty()){
            return result;
        }
        for (Integer i: marksToCalculate) {
            result +=i;
        }
        result = result / marksToCalculate.size()*100;
        int round = (int) result;
        return round / 100.0;
    }

    private List<Integer> getMarksAccordingToSubject(String subjectName) {
        List<Integer> marksToCalculate = new ArrayList<>();
        for (Mark actual: marks) {
            if(actual.getSubjectName().equals(subjectName)){
                marksToCalculate.add(actual.getMarkValue());
            }
        }
        return marksToCalculate;
    }


    @Override
    public String toString() {
        return name + " marks: " + printMarks(marks);
    }

    public String getName() {
        return name;
    }

    private String printMarks(List<Mark> marks){
        StringBuilder sb = new StringBuilder();
        for (Mark actual: marks) {
            sb.append(actual.toString());
            sb.append(", ");
        }
        return sb.substring(0, sb.length()-2);
    }

}
