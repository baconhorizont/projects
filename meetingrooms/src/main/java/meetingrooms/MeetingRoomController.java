package meetingrooms;

import java.util.List;
import java.util.Scanner;

public class MeetingRoomController {

    private static final int EXIT_NUMBER = 8;

    private Office office = new Office();
    private Scanner runMenuScanner = new Scanner(System.in);

    public static void main(String[] args) {
        MeetingRoomController meetingRoomController = new MeetingRoomController();
        meetingRoomController.runMenu();
    }

    private void runMenu(){
        System.out.println("Adja meg a létrehozni kívánt tárgyalók számát:");
        int requiredRooms = runMenuScanner.nextInt();
        runMenuScanner.nextLine();
        readMeetingRooms(requiredRooms);

        int menuNumber = 0;
        while (menuNumber != EXIT_NUMBER){
            printMenu();
            menuNumber = runMenuScanner.nextInt();
            runMenuScanner.nextLine();
            menuRunner(menuNumber);
        }
    }

    private void readMeetingRooms(int requiredRooms) {
        for (int i = 0; i < requiredRooms; i++) {
            System.out.printf("Adja meg a(z) %d. tárgyaló nevét:",i+1);
            String actualRoomName = runMenuScanner.nextLine();
            System.out.printf("Adja meg a(z) %d. tárgyaló szélességét:",i+1);
            int actualRoomWidth = runMenuScanner.nextInt();
            runMenuScanner.nextLine();
            System.out.printf("Adja meg a(z) %d. tárgyaló hosszúságát:",i+1);
            int actualRoomLength = runMenuScanner.nextInt();
            runMenuScanner.nextLine();
            office.addMeetingRoom(actualRoomName,actualRoomWidth,actualRoomLength);
        }
    }

    private void printMenu(){
        System.out.println();
        System.out.println("1. Tárgyalók sorrendben");
        System.out.println("2. Tárgyalók visszafelé sorrendben");
        System.out.println("3. Minden második tárgyaló");
        System.out.println("4. Területek");
        System.out.println("5. Keresés pontos név alapján");
        System.out.println("6. Keresés névtöredék alapján");
        System.out.println("7. Keresés terület alapján");
        System.out.println("8. Kilépés");
        System.out.println();
    }

    private void menuRunner(int number){
        switch (number) {
            case 1 -> printNames(office.getMeetingRooms());
            case 2 -> printNames(office.getMeetingRoomsInReverse());
            case 3 -> {
                System.out.println("páratlan(1.), páros(2.)");
                int i = runMenuScanner.nextInt();
                runMenuScanner.nextLine();
                printNames(office.getEverySecondMeetingRoom(i));
            }
            case 4 -> System.out.println(office.getMeetingRooms());
            case 5 -> {
                System.out.println("Keresett név:");
                String name = runMenuScanner.nextLine();
                System.out.println(office.getMeetingRoomWithGivenName(name));
            }
            case 6 -> {
                System.out.println("Keresett név töredéke:");
                String namePart = runMenuScanner.nextLine();
                System.out.println(office.getMeetingRoomsWithGivenNamePart(namePart));
            }
            case 7 -> {
                System.out.println("Keresett terület:");
                int area = runMenuScanner.nextInt();
                System.out.println(office.getMeetingRoomsWithAreaLargerThan(area));
            }
            case 8 -> System.out.println("A program kilép!");
            default -> System.out.println("Ismeretlen menü!");
        }
    }

    private void printNames(List<MeetingRoom> meetingRooms){
        for (MeetingRoom actual: meetingRooms) {
            System.out.println(actual.getName());
        }
    }
}
