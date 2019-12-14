package pt.onept.mei.is1920.assignment.kafka.cli.util;

public class ResponseMessages {
    public static void responseMessages(int status) {
        if(status == 200) {
            System.out.println("Operation completed successfully.");
        } else if(status == 500) {
            System.out.println("Internal server error.");
        } else {
            System.out.println("Bad request.");
        }
    }
}
