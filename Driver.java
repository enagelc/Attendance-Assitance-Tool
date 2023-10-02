import java.util.*;

public class Driver {
    public static void main(String[] args){

        Scanner c = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter student roster file name: ");
        String filename1 = c.nextLine();  // Read user 
        System.out.println("Enter student log file name: ");
        String filename2 = c.nextLine();  // Read user input

        System.out.println("\nLoading files...");

        AttendanceApp obj = new AttendanceApp(filename1, filename2);
        AttendanceLog obj1 = new AttendanceLog();
        StudentRoster obj2 = new StudentRoster();

        String name = "";
        String date = "";
        String time = "";
        int num = 0;

        ArrayList<?> saver = new ArrayList<>();
        String desiredFunc = "";

        while(!desiredFunc.equals("0")){

            System.out.println("Which operation would you like to proceed with? ");

            System.out.println("0 - Exit Program");
            System.out.println("A - load_log()");
            System.out.println("B - print_collection()");
            System.out.println("C - print_count()");

            System.out.println("D - load_roster()");
            System.out.println("E - print_collection()");
            System.out.println("F - print_count()");

            System.out.println("G - list_students_not_in_class()");
            System.out.println("H - list_all_times_checking_in_and_out()");
            System.out.println("I - list_all_times_checked_in()");
            System.out.println("J - list_students_late_to_class()");
            System.out.println("K - get_first_student_to_enter()");
            System.out.println("L - print_attendance_data_for_student()");
            System.out.println("M - is_present()");
            System.out.println("N - list_all_students_checked_in()");
            System.out.println("O - list_all_students_checked_in_before()");
            System.out.println("P - list_students_attendance_count()");
            System.out.println("Q - get_first_student_to_enter()");
            System.out.println("R - print_query_list()");
            System.out.println("S - print_count()");
            

            System.out.println("\nWhich operation would you like to proceed with? - ");

            desiredFunc = c.nextLine();  // Read user input

            if(desiredFunc.equals("A")){
            System.out.println("Logs have been loaded.");
            }

            if(desiredFunc.equals("B")){
                obj1.print_collection();
            }

            if(desiredFunc.equals("C")){
                obj1.print_count();
            }

            if(desiredFunc.equals("D")){
                System.out.println("Roster has been loaded.");
            }

            if(desiredFunc.equals("E")){
                obj2.print_collection();
            }

            if(desiredFunc.equals("F")){
                obj2.print_count();
            }

            if(desiredFunc.equals("G")){
                saver = obj.list_students_not_in_class();
            }

            if(desiredFunc.equals("H")){
                System.out.println("\nEnter Student name (lastname, name) - ");
                name = c.nextLine();
                saver = obj.list_all_times_checking_in_and_out(name);
            }

            if(desiredFunc.equals("I")){
                saver = obj.list_all_times_checked_in();
            }

            if(desiredFunc.equals("J")){
                System.out.println("\nEnter time (hh:mm:ss) - ");
                time = c.nextLine();
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                saver = obj.list_students_late_to_class(time, date);
            }

            if(desiredFunc.equals("K")){
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                saver = obj.get_first_student_to_enter(date);
            }

            if(desiredFunc.equals("L")){
                System.out.println("\nEnter Student name (lastname, name) - ");
                name = c.nextLine();
                saver = obj.print_attendance_data_for_student(name);
            }

            if(desiredFunc.equals("M")){
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                System.out.println("\nEnter Student name (lastname, name) - ");
                name = c.nextLine();
                saver = obj.is_present(name, date);
            }

            if(desiredFunc.equals("N")){
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                saver = obj.list_all_students_checked_in(date);
            }

            if(desiredFunc.equals("O")){
                System.out.println("\nEnter time (hh:mm:ss) - ");
                time = c.nextLine();
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                saver = obj.list_all_students_checked_in_before(time, date);
            }

            if(desiredFunc.equals("P")){
                System.out.println("\nEnter the number of days to check");
                num = c.nextInt();
                saver = obj.list_students_attendance_count(num);
            }

            if(desiredFunc.equals("Q")){
                System.out.println("\nEnter date (MM/dd/yyyy) - ");
                date = c.nextLine();
                saver = obj.get_first_student_to_enter(date);
            }

            if(desiredFunc.equals("R")){
                obj.print_query_list(saver); //Ince, Ryan
            }

            if(desiredFunc.equals("S")){
                obj.print_count(saver);
            }

            date = "";
            name = "";
            time = "";
            num = 0;

        }
        System.out.println("Program exited.");

      
    }
}