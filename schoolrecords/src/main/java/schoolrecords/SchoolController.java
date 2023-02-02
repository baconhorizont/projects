package schoolrecords;

import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class SchoolController {

    private ClassRecords classRecords;
    private School school;
    private Scanner scanner = new Scanner(System.in);
    private final static int MENU_EXIT = 9;

    public static void main(String[] args) {
        SchoolController schoolController = new SchoolController();
        schoolController.school = new School(Paths.get("src/test/resources/school.csv"));
        schoolController.classRecords = new ClassRecords("12.A", new Random());
        schoolController.initClass();
        schoolController.runMenu();
    }

    private void runMenu() {
        int menuNumber = 0;
        while (menuNumber != MENU_EXIT) {
            printMenu();
            System.out.println("Menüpont száma: ");
            try {
                menuNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Egész számot adjon meg!");
            }
            controlMenu(menuNumber);
        }
    }

    private void controlMenu(int menuNumber) {
        switch (menuNumber) {
            case 1: {
                System.out.println(classRecords.listStudentNames());;
            }
            return;
            case 2: {
                System.out.println("Keresett diák neve:");
                String name = scanner.nextLine();
                System.out.println(classRecords.findStudentByName(name));
            }
            return;
            case 3: {
                System.out.println("Új diák neve:");
                String name = scanner.nextLine();
                classRecords.addStudent(name);
            }
            return;
            case 4: {
                System.out.println("Törölni kívánt diák neve:");
                String name = scanner.nextLine();
                classRecords.removeStudent(name);
            }
            return;
            case 5: {
                Student student = classRecords.repetition();
                System.out.println("Felelő: " + student.getName());
                System.out.println("Tantárgy: ");
                Subject subject = school.findSubjectByName(scanner.nextLine());
                System.out.println("Oktató: ");
                Tutor tutor = school.findTutorByName(scanner.nextLine());
                System.out.println("Érdemjegy: ");
                int actualMark = Integer.parseInt(scanner.nextLine());
                student.addGrading(new Mark(getMarkTypeByValue(actualMark),subject,tutor));
            }
            return;
            case 6: {
                System.out.println("Tantárgy: ");
                String subject = scanner.nextLine();
                System.out.println("Tantárgyi osztályátlag: "+ classRecords.calculateClassAverageBySubject(subject));
            }
            return;
            case 7: {
                System.out.println("Tantárgy: ");
                String subject = scanner.nextLine();
                classRecords.listSubjectResults(subject);
            }
            return;
            case 8: {
                System.out.println("Diák neve: ");
                String name = scanner.nextLine();
                System.out.println("Tantárgy: ");
                String subject = scanner.nextLine();
                Student student = classRecords.findStudentByName(name);
                System.out.println(student.calculateSubjectAverage(subject));

            }
            return;
            case 9: {
                System.out.println("Program vége.");
            }
            return;
            default: {
                System.out.println("Nem létező menüpont!");
            }
        }
    }

    private MarkType getMarkTypeByValue(int mark){
        for (MarkType markType : MarkType.values()) {
            if(markType.getValue() == mark){
                return markType;
            }
        }
        throw new IllegalArgumentException("Can not find mark!");
    }
        private void printMenu () {
            System.out.println();
            System.out.println(
                    "1. Diákok nevének listázása\n" +
                    "2. Diák név alapján keresése\n" +
                    "3. Diák létrehozása\n" +
                    "4. Diák név alapján törlése\n" +
                    "5. Diák feleltetése\n" +
                    "6. Tantárgyi osztályátlag kiszámolása\n" +
                    "7. Diákok átlagának listázása egy tantárgyból\n" +
                    "8. Egy diák egy tantárgyhoz tartozó átlagának kiszámolása\n" +
                    "9. Kilépés");
            System.out.println();
        }
        private void initClass () {
            classRecords.addStudent("Kovács Rita");
            classRecords.addStudent("Nagy Béla");
            classRecords.addStudent("Varga Márton");
            Student firstStudent = classRecords.findStudentByName("Kovács Rita");
            firstStudent.addGrading(new Mark(MarkType.A, school.findSubjectByName("földrajz"), school.findTutorByName("Dienes Irén")));
            firstStudent.addGrading(new Mark(MarkType.C, school.findSubjectByName("matematika"), school.findTutorByName("Szabó László")));
            firstStudent.addGrading(new Mark(MarkType.D, school.findSubjectByName("földrajz"), school.findTutorByName("Dienes Irén")));
            Student secondStudent = classRecords.findStudentByName("Nagy Béla");
            secondStudent.addGrading(new Mark(MarkType.A, school.findSubjectByName("biológia"), school.findTutorByName("Dienes Irén")));
            secondStudent.addGrading(new Mark(MarkType.C, school.findSubjectByName("matematika"), school.findTutorByName("Tóth Ilona")));
            secondStudent.addGrading(new Mark(MarkType.D, school.findSubjectByName("ének-zene"), school.findTutorByName("Németh Lili")));
            Student thirdStudent = classRecords.findStudentByName("Varga Márton");
            thirdStudent.addGrading(new Mark(MarkType.A, school.findSubjectByName("fizika"), school.findTutorByName("Kiss József")));
            thirdStudent.addGrading(new Mark(MarkType.C, school.findSubjectByName("kémia"), school.findTutorByName("Kiss József")));
            thirdStudent.addGrading(new Mark(MarkType.D, school.findSubjectByName("földrajz"), school.findTutorByName("Tóth Ilona")));
        }

}


