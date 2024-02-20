package seanie.mark.cs4076p1server;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String moduleCode;
    private final List<TimetableEntry> timetable;

    public Module(String moduleCode){
        this.moduleCode = moduleCode;
        this.timetable = new ArrayList<>();
    }

    public void addTimetableEntry(String time,String day, String room){
        timetable.add(new TimetableEntry(time, day, room));
    }

    public String getModuleCode(){
        return moduleCode;
    }

    public List<TimetableEntry> getTimetable(){
        return timetable;
    }
}
