package meetingrooms;

public class MeetingRoom {

    private String name;
    private int width;
    private int length;

    public MeetingRoom(String name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;
    }

    @Override
    public String toString() {
        return "név: " + name + " szélesség: " + width + " hosszúság: " + length + " terület: " +
                getArea();
    }


    public int getArea(){
        return width*length;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

}
