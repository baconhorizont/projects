package schoolrecords;

import java.util.List;

public class Tutor {
    private String name;
    private List<Subject> taughtSubjects;

    public Tutor(String name, List<Subject> taughtSubjects) {
        this.name = name;
        this.taughtSubjects = taughtSubjects;
    }
    public boolean isTutorTeachingSubject(String subjectName){
        for (Subject actual: taughtSubjects) {
            if (actual.getName().equals(subjectName)){
                return true;
            }
        }
        return false;
    }

    public void addSubject(Subject subject){
        taughtSubjects.add(subject);
    }
    public String getName() {
        return name;
    }
}
