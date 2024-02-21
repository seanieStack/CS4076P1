package seanie.mark.cs4076p1server;

public class TimetableEntry {
    private final String startTime;
    private final String endTime;
    private final String day;
    private final String room;


    public TimetableEntry(String time, String day ,String room){
        String[] times = time.split("-");

        this.startTime = times[0];
        this.endTime = times[1];

        this.day = day;
        this.room = room;
    }


    public String getDay(){
        return day;
    }

    public String getRoom(){
        return room;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
