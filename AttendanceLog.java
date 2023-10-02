//import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class AttendanceLog {

    public static ArrayList<Log> logList = new ArrayList<Log>();

    public static ArrayList<Log> load_log(String filename){
        
        // TODO - loads a single file of attendance data into a collection of your choice. 
        // Uses a filename as a parameter to determine which file.

        try{
            //System.out.println("parsing through "+ filename);
            File file = new File(filename); // scanner will parse through the file
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){ //parses until the end of the file and then closes scanner

                String row = scanner.nextLine(); //row takes in each line, which is then divided in "blocks" to put into 
                String[] blocks = row.split(", "); 
                Log log = new Log();

                log.student = blocks[0] + ", " + blocks[1]; //each block is a name, lastname, ...
                log.time = blocks[2];
                log.date = blocks[3];

                logList.add(log); //adds to arrayList
            }
            scanner.close();
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return logList;

    }

    public static void print_collection(){
        // TODO - prints logs read into the collection of your choice. This is more of a debugging feature.//
        if(logList.size() == 1400){
            for(int i = 0; i < logList.size()/2; i++){
                System.out.println(logList.get(i).student + " ['" + logList.get(i).time + ", " + logList.get(i).date + "', '" + logList.get(i+700).time + ", "+ logList.get(i+700).date + "']");
            }
        }
        else{
            for(int i = 0; i < logList.size(); i++){
                System.out.println(logList.get(i).student + " ['" + logList.get(i).time + ", " + logList.get(i).date + "']");
            }
        }

    }

    public static void print_count(){
        // TODO - prints the number of logs read into the collection of your choice. This is more of a debugging feature. //
        System.out.println("There are " + logList.size()+ " entries");
    }


    public static void main(String[] args) {
        load_log("dataAllShow1stAnd2ndClass.txt");
        print_collection();
    }

}