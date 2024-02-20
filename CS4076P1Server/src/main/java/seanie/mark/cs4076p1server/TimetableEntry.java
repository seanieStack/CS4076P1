package seanie.mark.cs4076p1server;

public class TimetableEntry {
    private final String time;
    private final String day;
    private final String room;


    public TimetableEntry(String time, String day ,String room){
        this.time = time;
        this.day = day;
        this.room = room;
    }

    public String getTime(){
        return time;
    }

    public String getDay(){
        return day;
    }

    public String getRoom(){
        return room;
    }
}
