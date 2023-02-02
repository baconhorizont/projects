package schoolrecords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class School {

    private List<Subject> subjects = new ArrayList<>();
    private List<Tutor> tutors = new ArrayList<>();

    public School(Path path) {
            loadFromFile(path);
    }

    private void loadFromFile(Path path) {
        try {
            List<String> lines = Files.readAllLines(path);
            load(lines);
        } catch (
                IOException e) {
            throw new IllegalStateException("Can't load subjects and tutors from file.", e);
        }
    }

    private void load(List<String> lines) {
        for (String actual : lines) {
            String[] temp = actual.split(";");
            addSubject(temp[0]);
            addTutor(temp[0],temp[1]);

        }
    }

    private void addSubject(String subjectName){
        boolean subjectExist = false;
        for (Subject actual : subjects) {
            if(actual.getName().equals(subjectName)){
                subjectExist = true;
            }
        }
        if (!subjectExist){
            subjects.add(new Subject(subjectName));
        }

    }

    private void addTutor(String subjectName,String tutorName){
        boolean tutorExist = false;
        Tutor tutor = new Tutor(tutorName,new ArrayList<>());
        for (Tutor actual : tutors) {
            if (actual.getName().equals(tutorName)){
                tutorExist = true;
                tutor = actual;
            }
        }
        if (!tutorExist){
            tutors.add(tutor);
        }
        if (!tutor.isTutorTeachingSubject(subjectName)){
            Subject subject = findSubjectByName(subjectName);
            tutor.addSubject(subject);
        }
    }

        public Tutor findTutorByName (String name){
            for (Tutor actual : tutors) {
                if (actual.getName().equals(name)) {
                    return actual;
                }
            }
            throw new IllegalArgumentException("Can't find tutor with this name!");
        }

        public Subject findSubjectByName (String name){
            for (Subject actual : subjects) {
                if (actual.getName().equals(name)) {
                    return actual;
                }
            }
            throw new IllegalArgumentException("Can't find subject with this name!");
        }
}




