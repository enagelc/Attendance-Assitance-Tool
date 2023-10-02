import java.util.*;
import java.io.File;
public class StudentRoster {

    public static ArrayList<Student> studentList = new ArrayList<Student>();

    public static ArrayList<Student> load_roster(String filename){
        // TODO - loads a single file of roster data into a collection of your choice. Uses a filename as a parameter to determine which file.
        try{
            // System.out.println("parsing through "+ filename);
            File file = new File(filename); // scanner will parse through the file
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){ //parses until the end of the file and then closes scanner

                String row = scanner.nextLine(); //row takes in each line, which is then divided in "blocks" to put into 
                String[] blocks = row.split(", "); 
                Student studentLine = new Student();

                studentLine.student = blocks[0] + ", " + blocks[1]; //each block is a name, lastname, ...

                studentList.add(studentLine); //adds to arrayList
            }
            scanner.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return studentList;
    }

    public static void print_collection(){
        // TODO - prints students read into the collection of your choice. This is more of a debugging feature. It should display the entire Collection stored as viewed below.
        System.out.println("**** Those students on roster ****");
        for(Student i : studentList){
            System.out.println(i.student);
        }
    }

    public static void print_count(){
        // TODO - prints the number of students read into the collection of your choice. This is more of a debugging feature. 
        System.out.println("there are " + studentList.size() + " entries");
    }
    public static void main(String[] args) {
        load_roster("rosters.txt");
        print_collection();
        print_count();
    }

}
