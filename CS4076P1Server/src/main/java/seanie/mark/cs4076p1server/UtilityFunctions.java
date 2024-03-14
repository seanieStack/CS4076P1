package seanie.mark.cs4076p1server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class UtilityFunctions {
    static int stringTimeToIntTime(String time){
        String temp = time.substring(0, 2);
        temp += time.substring(3);

        return Integer.parseInt(temp);
    }

    public static boolean checkOverlap(String time, String day){
        String[] timeSplit = time.split("-");
        String startTime = timeSplit[0];
        String endTime = timeSplit[1];

        String sql = "SELECT COUNT(*) AS overlap_count FROM timetableentries WHERE day = ? AND ((? < end_time AND ? > start_time) OR (? < end_time AND ? > start_time))";

        try (Connection con = DatabaseCon.getConnection();
                PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, day);
            statement.setString(2, startTime);
            statement.setString(3, endTime);
            statement.setString(4, endTime);
            statement.setString(5, startTime);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("overlap_count") > 0;
            }
        }
        catch (Exception e) {
            System.out.println("Error checking for overlapping timetable entries");
        }
        return false;
    }

    public static boolean isValidModule(String module) {
        // Check if the module string length is exactly 6
        if (module.length() != 6) {
            return false;
        }

        // Check the first two characters for letters
        for (int i = 0; i < 2; i++) {
            char ch = module.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }

        // Check the last four characters for digits
        for (int i = 2; i < 6; i++) {
            char ch = module.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }

        // If all checks pass, return true
        return true;
    }

    static boolean isValidRoom(String room) {

        Set<String> campusBuildings = new HashSet<>(Arrays.asList(
                "SG", "S", "KGB", "KB", "CSG", "CS", "GLG", "GL", "FB", "FG", "F", "ERB", "ER",
                "LCB", "LC", "LB", "LG", "L", "SR", "PG", "PM", "P", "HSG", "HS", "A", "AM", "B",
                "BM", "CG", "C", "CM", "DG", "DM", "D", "EG", "E", "EM", "AD", "IWG", "IW",
                "GEM", "GEMS"
        ));

        if (!room.contains("-")) {
            return false;
        }

        String prefix = room.toUpperCase().split("-")[0];
        return campusBuildings.contains(prefix);
    }

    public static boolean moduleInDB(String moduleCode) {
        String sql = "SELECT module_code FROM modules";
        try(Connection con = DatabaseCon.getConnection();
            PreparedStatement statement = con.prepareStatement(sql)){

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                if(rs.getString("module_code").equals(moduleCode)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception e) {
            System.out.println("Error checking if module is in database");
            return false;
        }
    }

    public static void addTimetableEntryToDB(String moduleCode, String time, String day, String room) {
        //convert time in format 00:00-00:00 to startTime 00:00 and end time 00:00
        String[] times = time.split("-");
        String startTime = times[0];
        String endTime = times[1];


        String sql = "INSERT INTO timetableentries(module_code, start_time, end_time, day, room) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = DatabaseCon.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, moduleCode);
            statement.setString(2, startTime);
            statement.setString(3, endTime);
            statement.setString(4, day);
            statement.setString(5, room);

            statement.executeUpdate();
        } catch (Exception e) {
            //System.out.println("Error adding timetable entry to database");
            e.printStackTrace();
        }
    }

    public static void addModuleToDB(String moduleCode) {
        String sql = "INSERT INTO modules(module_code) VALUES(?)";
        try (Connection con = DatabaseCon.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, moduleCode);

            statement.executeUpdate();
        } catch (Exception e) {
            //System.out.println("Error adding module to database");
            e.printStackTrace();
        }
    }

    public static int getNumberOfModules() {
        String sql = "SELECT COUNT(*) FROM modules";
        try(Connection con = DatabaseCon.getConnection();
            PreparedStatement statement = con.prepareStatement(sql)){

            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch(Exception e) {
            System.out.println("Error getting number of modules");
            return -1;
        }
    }

    public static boolean checkIfModulesHasNoRemainingClasses(String moduleCode){
        String sql = "SELECT COUNT(*) FROM timetableentries WHERE module_code = ?";
        try(Connection con = DatabaseCon.getConnection();
            PreparedStatement statement = con.prepareStatement(sql)){

            statement.setString(1, moduleCode);

            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;
        }
        catch(Exception e) {
            System.out.println("Error checking if module has no remaining classes");
            return false;
        }
    }

    public static String removeTimetableEntryFromDB(String moduleCode, String time, String day, String room) {
        String[] times = time.split("-");
        String startTime = times[0];
        String endTime = times[1];

        String sql = "DELETE FROM timetableentries WHERE module_code = ? AND start_time = ? AND end_time = ? AND day = ? AND room = ?";
        try (Connection con = DatabaseCon.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, moduleCode);
            statement.setString(2, startTime);
            statement.setString(3, endTime);
            statement.setString(4, day);
            statement.setString(5, room);

            if(statement.executeUpdate() > 0){
                boolean noRemainingClasses = checkIfModulesHasNoRemainingClasses(moduleCode);
                if(noRemainingClasses){
                    String sql2 = "DELETE FROM modules WHERE module_code = ?";
                    try (PreparedStatement statement2 = con.prepareStatement(sql2)) {
                        statement2.setString(1, moduleCode);
                        statement2.executeUpdate();
                    }
                }
                return "cr" + " " + startTime + "-" + endTime + " " + day + " " + room;
            }
            else{
                return "nsc";
            }
        } catch (Exception e) {
            System.out.println("Error removing timetable entry from database");
            return "error";
        }
    }

    public static String displayModuleTimetable(String moduleCode) {
        String sql = "SELECT start_time, end_time, day, room FROM timetableentries WHERE module_code = ?";
        try(Connection con = DatabaseCon.getConnection();
            PreparedStatement statement = con.prepareStatement(sql)){

            statement.setString(1, moduleCode);

            ResultSet rs = statement.executeQuery();
            StringBuilder timetable = new StringBuilder();
            while(rs.next()){
                timetable.append(rs.getString("start_time")).append("-").append(rs.getString("end_time")).append(" ").append(rs.getString("day")).append(" ").append(rs.getString("room")).append("\n");
            }
            System.out.println(timetable);
            return "cp";
        }
        catch(Exception e) {
            System.out.println("Error displaying module timetable");
            return "error";
        }
    }
}
