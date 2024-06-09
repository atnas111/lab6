package server.commands;

import common.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Long.parseLong;

public class UpdateId {
    private static Set<StudyGroup> studyGroups;

    public UpdateId(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public static void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command syntax. Usage: update {element}");
            return;
        }

        long id;
        try {
            id = parseLong(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        StudyGroup newGroup = inputCollection(args[1]);
        if (newGroup == null) {
            System.out.println("Failed to parse the input element of StudyGroup.");
            return;
        }

        boolean updated = false;
        for (StudyGroup group : studyGroups) {
            if (group.getId() == id) {
                studyGroups.remove(group);
                studyGroups.add(newGroup);
                updated = true;
                System.out.println("Study group with ID " + id + " updated successfully.");
                break;
            }
        }

        if (!updated) {
            System.out.println("Study group with ID " + id + " not found.");
        }
    }

    public static StudyGroup inputCollection(String f) {
        Scanner scanner = new Scanner(System.in);
        String groupName = null;
        Double x = null;
        Float y = null;
        LocalDateTime time = null;
        Integer studentsCount = null;
        Double averageMark = null;
        FormOfEducation formOfEducation = null;
        Semester semester = null;
        String nameAdmin = null;
        LocalDateTime adminBirthday = null;
        int adminWeight = 0;
        Double locationX = null;
        Double locationY = null;
        Long locationZ = null;
        String locationName = null;

        Long id = parseLong(f);
        do{
            try {
                System.out.print("Enter name of group: ");
                groupName = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter coordinates (x): ");
                x = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinate format: " + e.getMessage());
            }
        } while (true);

        do{


            try {
                System.out.print("Enter coordinates (y): ");
                y = Float.parseFloat(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinate format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter Date (YYYY-MM-DDTHH:MM:SS): ");
                time = LocalDateTime.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date/time format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter количество студентов: ");
                studentsCount = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid students count format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter average mark: ");
                averageMark = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid average mark format: " + e.getMessage());
            }
        } while (true);
        do {
            try {
                System.out.print("Enter form of education (DISTANCE_EDUCATION,\n" +
                        "    FULL_TIME_EDUCATION,\n" +
                        "    EVENING_CLASSES): ");
                formOfEducation = FormOfEducation.valueOf(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid form of education: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter semester: FIRST,\n" +
                        "    FOURTH,\n" +
                        "    SIXTH,\n" +
                        "    EIGHTH");
                semester = Semester.valueOf(scanner.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid semester: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter name admin: ");
                nameAdmin = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin birthday (YYYY-MM-DDTHH:MM:SS): ");
                adminBirthday = LocalDateTime.parse(scanner.nextLine());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid admin birthday format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin weight: ");
                adminWeight = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid admin weight format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin location (x): ");
                locationX = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid admin location format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin location (y): ");
                locationY = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid admin location format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin location (z): ");
                locationZ = parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid admin location format: " + e.getMessage());
            }
        } while (true);
        do{
            try {
                System.out.print("Enter admin location name: ");
                locationName = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        } while (true);

        // Create and return the StudyGroup object
        return new StudyGroup(id, groupName, new Coordinates(x, y), time,
                studentsCount, averageMark, formOfEducation, semester,
                new Person(nameAdmin, adminBirthday, adminWeight, new Location(locationX, locationY, locationZ, locationName)));
    }

}
