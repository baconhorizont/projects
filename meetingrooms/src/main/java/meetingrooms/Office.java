package meetingrooms;

import java.util.ArrayList;
import java.util.List;

public class Office {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    public void addMeetingRoom(String name, int width, int length) {
        meetingRooms.add(new MeetingRoom(name, width, length));
    }

    public List<MeetingRoom> getMeetingRooms() {
        return new ArrayList<>(meetingRooms);
    }

    public List<MeetingRoom> getMeetingRoomsInReverse() {
        List<MeetingRoom> meetingRoomsReverse = new ArrayList<>();
        for (int i = meetingRooms.size() - 1; i >= 0; i--) {
            meetingRoomsReverse.add(meetingRooms.get(i));
        }
        return meetingRoomsReverse;
    }

    public List<MeetingRoom> getEverySecondMeetingRoom(int number) {
        List<MeetingRoom> meetingRoomsEverySecond = new ArrayList<>();
        for (int i = number-1; i < meetingRooms.size(); i += 2) {
            meetingRoomsEverySecond.add(meetingRooms.get(i));
        }
        return meetingRoomsEverySecond;
    }

    public MeetingRoom getMeetingRoomWithGivenName(String name) {
        for (MeetingRoom actual : meetingRooms) {
            if (actual.getName().equals(name)) {
                return actual;
            }
        }
            throw new IllegalArgumentException("There is no room with this name: " + name);
    }


        public List<MeetingRoom> getMeetingRoomsWithGivenNamePart (String name){
            List<MeetingRoom> meetingRoomsWithNames = new ArrayList<>();
            for (MeetingRoom actual : meetingRooms) {
                if (actual.getName().toLowerCase().contains(name.toLowerCase())) {
                    meetingRoomsWithNames.add(actual);
                }
            }
            return meetingRoomsWithNames;
        }

        public List<MeetingRoom> getMeetingRoomsWithAreaLargerThan ( int area){
            List<MeetingRoom> meetingRoomsArea = new ArrayList<>();
            for (MeetingRoom actual:meetingRooms) {
                if ( actual.getArea() > area){
                    meetingRoomsArea.add(actual);
                }
            }
            return meetingRoomsArea;
        }

}

