import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AttendanceApp {

    public static ArrayList<Log> swipeDataLog;
    public static ArrayList<Student> rosterList;
    public String header = "";

    public AttendanceApp(String filename_for_roster, String filename_for_swipedata){
        // TODO - Constructor
        rosterList = StudentRoster.load_roster(filename_for_roster);
        swipeDataLog = AttendanceLog.load_log(filename_for_swipedata);
    }
    

    public ArrayList<String> list_students_not_in_class(){
        // TODO - Compare the swipe log with the given course roster. Place those students that did not show up for class into a list.
        ArrayList<String> missing_students_list = new ArrayList<String>();
        boolean paired = false;

        for(Student i : rosterList){
            for(Log j : swipeDataLog){
                if(i.student.equals(j.student)){
                    paired = true;
                    break;
                }
            }
            if(!paired){
                missing_students_list.add(i.student);
            }
            paired = false;
        }
        header = "****** Students missing in class *************";
        // for(Student i : missing_students_list){
        //     System.out.println(i.student);
        // }
        
        return missing_students_list;
    }

    public ArrayList<String> list_all_times_checking_in_and_out(String name){
        // TODO - Looking at the swiping log, this function will list all in and outs for a particular Student. *MORE IN DOC*
        ArrayList<String> retString = new ArrayList<String>();

        header = "****** List all swipe in and out for a student *******";
        for(Log i : swipeDataLog){
            if(i.student.equals(name)){
                retString.add(name + ", " + i.time + ", " + i.date);
            }
        }
        return retString;

    }

    public ArrayList<Log> changeDates(){ //helper function 
        ArrayList<Log> newDates = new ArrayList<Log>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yy");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
        String new_date = "";

        for(Log i : swipeDataLog){
            try {
                new_date = fmt.format(sdf.parse(i.date));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            i.date = new_date;
            newDates.add(i);
        }
        
        return newDates;
    }

    public ArrayList<Log> changeBack(){ //helper function 
        ArrayList<Log> newDates = new ArrayList<Log>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yy"); //"yyyy-MM-dd"
        String new_date = "";

        for(Log i : swipeDataLog){
            try {
                new_date = fmt.format(sdf.parse(i.date));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            i.date = new_date;
            newDates.add(i);
        }
        
        return newDates;
    }

    public ArrayList<String> list_all_times_checked_in(){
        // TODO - This function returns a list of when all student(s) FIRST swipe in. 
        ArrayList<String> first_check_in = new ArrayList<String>();


        header = "****** Check in times for all students who attended***";
        
        Timestamp t1;
        Timestamp t2;
        int count = 0;
        

        ArrayList<Log> forParsing = changeDates();

        for(Log i : forParsing){
            t1 = Timestamp.valueOf(i.date + " " + i.time);
            for(Log j : forParsing){
                t2 = Timestamp.valueOf(j.date + " " + j.time);
                if(t1.compareTo(t2) > 0 && i.student.equals(j.student)){
                    first_check_in.add(j.student + ", " + j.time + ", " + j.date);
                    count++;
                }
            }
            
        }
        // for(Log i : first_check_in){
        //     System.out.println(i.student + " --- " + i.time + "---" + i.date);
        // }
        //System.out.println(count);
        forParsing = changeBack();
        return first_check_in;
    }

    public ArrayList<Log> list_all_times_checked_in_helper(){
        // TODO - This function returns a list of when all student(s) FIRST swipe in. 
        ArrayList<Log> first_check_in = new ArrayList<Log>();


        header = "****** Check in times for all students who attended***";
        
        Timestamp t1;
        Timestamp t2;
        int count = 0;
        

        ArrayList<Log> forParsing = changeDates();

        for(Log i : forParsing){
            t1 = Timestamp.valueOf(i.date + " " + i.time);
            for(Log j : forParsing){
                t2 = Timestamp.valueOf(j.date + " " + j.time);
                if(t1.compareTo(t2) > 0 && i.student.equals(j.student)){
                    first_check_in.add(j);
                    count++;
                }
            }
            
        }
        // for(Log i : first_check_in){
        //     System.out.println(i.student + " --- " + i.time + "---" + i.date);
        // }
        //System.out.println(count);
        //first_check_in = changeBack();
        return first_check_in;
    }
        
    

    public ArrayList<String> list_students_late_to_class(String time, String date){
        // TODO - When given a timestamp string and the swipe log, a list of those records of students who swiped in late into the class is produced.
        ArrayList<Log> first_check_in = list_all_times_checked_in_helper();
        ArrayList<String> late_students = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yy");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String new_date = "";
        

        try {
            //System.out.println(date);
            new_date = fmt.format(sdf.parse(date));
        } catch (Exception e) {
            System.out.println(date + " Error: " + e.getMessage());
        }
    
        
        
        Timestamp t1 = Timestamp.valueOf(new_date+" "+time);
        Timestamp t2;
        long diff = 3000000;
        int count = 0;
        
        
        header = "****** Students that arrived late ********************";
        for(Log i : first_check_in){
            t2 = Timestamp.valueOf(i.date+" "+i.time);
            if(t2.compareTo(t1) > 0 && (t2.getTime() - t1.getTime() < diff)){
                late_students.add(i.student);
                
                count++;
            }
           
        }
        // for(String i : late_students){
        //     System.out.println(i.student + " --- " + i.date + "---" + i.time);
        // }
        System.out.println(count);


        first_check_in = changeBack();

        return late_students;
    }

    public ArrayList<String> print_attendance_data_for_student(String student) { 
        // TODO - This needs to display the log-in data student for that particular, if present.
        String returnString = "";
        boolean present = false;

        //ArrayList<Log> inTimes = list_all_times_checked_in();
        returnString = student + " [";
        for(Log i : swipeDataLog){
            if(i.student.equals(student)){
                returnString += "'" + i.time + ", " + i.date + "', ";
                present = true;
            }
        }
        returnString += "]";
        if(!present){
            returnString = "No student of this name in the attendance log";
        }
        header = "********* Looking up Student Attendance Data ***********";
        //System.out.println(returnString);
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(returnString);
        return ret;
    }

    public ArrayList<Boolean> is_present(String student, String date) {
        // TODO - Simply return true if a student and a certain date, logged into the class.
        boolean attended = false;
        for(Log i : swipeDataLog){
            if(i.student.equals(student) && i.date.equals(date)){
                attended = true;
            }
        }
        header = "**** Looking to see if Student was present on date ****";
        ArrayList<Boolean> ret = new ArrayList<Boolean>();
        ret.add(attended);
        return ret;
    }

    public ArrayList<String> list_all_students_checked_in(String date){
        // TODO -  Simply return a list of students that attended class on a given date. Please also display the number of students that matched the request
        ArrayList<String> Students_checked_in = new ArrayList<String>();
        int count = 0;
        
        for(Log i : swipeDataLog){
            if(i.date.equals(date)){
                Students_checked_in.add(i.student);
                count++;
            }
        }

        System.out.println("**** Students present on this date ****");
        for(String i : Students_checked_in){
            System.out.println(i);
        }
        System.out.println("Matched requests: "+ count);
        
        return Students_checked_in;
    }

    public ArrayList<String> list_all_students_checked_in_before(String time, String date){
        // TODO - Simply return a list of students that attended class on a given date and BEFORE a given time. Please also display the number of students that matched the request as shown below. 
        ArrayList<String> attended_before_time = new ArrayList<String>();
        ArrayList<Log> fixedData = changeDates();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yy");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String new_date = "";
        

        try {
            new_date = fmt.format(sdf.parse(date));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Timestamp t1 = Timestamp.valueOf(new_date + " " + time);
        Timestamp t2;
        long diff = 3000000;
        int count = 0;

        for(Log i : fixedData){
            t2 = Timestamp.valueOf(i.date + " " + i.time);
            
            if(i.date.equals(new_date) && (t1.compareTo(t2) > 0) && (t1.getTime() - t2.getTime() < diff)){
                attended_before_time.add(i.student);
                count++;
            }
        }

        header = "**** Those present on " + date + " & before " + time + " ****";
        // for(String i : attended_before_time){
        //     System.out.println(i);
        // }
        // System.out.println("Matched requests: "+ count);
        fixedData = changeBack();
        return attended_before_time;
    }

    public ArrayList<String> list_students_attendance_count(int daysAttended){
        // TODO 
        /*
         * This function needs to return a list of students that have attended a (integer) particular amount of days. 
         * Since the test data is only one week the greatest number of days would be 2. BUT… our test data could be several weeks of data. 
         * Your function should adjust to the number of days requested. There is ONE student that has not appeared in class at all! 
         * You might want to test your function for that naughty student. Please note we are not specifying what parameters are needed at all. 
         * Also notice the number of records from the query are also displayed. You are welcome to create helper functions.
         */
        ArrayList<String> ret = new ArrayList<String>();
        int count = 0;

        for(Log i : swipeDataLog){
            for(Student j : rosterList){
                if(i.student.equals(j.student)){
                    j.attendance++;
                }
            }
        }
        for(Student i : rosterList){
            if(i.attendance == daysAttended){
                ret.add(i.student);
                count++;
            }
        }

        header = "**** Those who attended "+ daysAttended + " classes ****";
        for(String i : ret){
            System.out.println(i);
        }
        System.out.println("Matched requests: "+ count);

        return ret;

    }

    public ArrayList<String> get_first_student_to_enter(String date){
        // TODO - this function returns the student that swiped in first. Note, the order of the data entered into the swipe log as 
        // not the earliest student to enter.  You will need a date in order to determine which student is first on that specific day.  
        // Do not take the first student overall. 
        ArrayList<Log> fixedData = changeDates();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/d/yy");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String new_date = "";
        String first_student = fixedData.get(0).student;

        try {
            new_date = fmt.format(sdf.parse(date));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Timestamp smallestTime = Timestamp.valueOf(new_date + " " + swipeDataLog.get(0).time);
        Timestamp currTime;

        for(Log i : swipeDataLog){
            currTime = Timestamp.valueOf(i.date + " " + i.time);
            if(smallestTime.compareTo(currTime) > 0 && new_date.equals(i.date)){
                first_student = i.student;
                smallestTime = currTime;
            }
        }
        header = "**** First student to enter on "+ date + " ****";
        //System.out.println(first_student);
        ArrayList<String> ret = new ArrayList<String>();
        ret.add(first_student);
        fixedData = changeBack();
        return ret;

    }

    public void print_query_list(ArrayList<?> list){
        // TODO - This function simply prints the list created by a query. The function should not be able to change any 
        // values of that list passed in. 
        System.out.println(header);
        for(Object i : list){
            System.out.println(i);
        }

    }

    public void print_count(ArrayList<?> list){
        // TODO - This function simply prints the number of items in a list. That list is likely a result of a query.
        System.out.println("There were " + list.size() + " records for this query");

    }

    // public static void main(String[] args) {
    //     //AttendanceApp test = new AttendanceApp("rosters.txt", "dataAllShow1stClass.txt");
    //     //test.list_students_not_in_class();
    //     //test.list_all_times_checking_in_and_out("Ince, Ryan");
    //     //test.list_all_times_checked_in();
    //     //test.changeDates();
    //     //test.list_students_late_to_class("09:45:00", "11/2/2022");
    //     //test.print_attendance_data_for_student("Nagel, Emilio");
    //     //test.is_present("MacDonald, Stephen", "11/3/2022");
    //     //test.list_all_students_checked_in("11/2/2022");
    //     //test.list_all_students_checked_in_before("12:56:00", "11/2/2022");
    //     //test.list_students_attendance_count(0);
    //     //test.get_first_student_to_enter("11/5/2022");

    //     // ArrayList<String> testerList = test.list_all_students_checked_in_before("09:46:49", "11/2/2022");
    //     // test.print_count(testerList);


    // }

}
