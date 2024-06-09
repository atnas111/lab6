//import common.model.StudyGroup;
//import server.commands.*;
//import server.readwrite.Reader;
//
//import java.io.*;
//import java.net.*;
//import java.nio.channels.*;
//import java.nio.*;
//import java.util.*;
//
//public class Server {
//    private static final int PORT = 6666;
//    public static Reader reader = new Reader();
//
//    public static String filename = "std.csv";
//    private static Set<StudyGroup> studyGroups = reader.readFromCSV(filename);
//    static ComManager commandManager = new ComManager(studyGroups, filename);
//
//    public static void main(String[] args) {
//
//        try {
//            DatagramChannel serverChannel = DatagramChannel.open();
//            serverChannel.socket().bind(new InetSocketAddress(PORT));
//            serverChannel.configureBlocking(false);
//            ByteBuffer buffer = ByteBuffer.allocate(4096);
//            while (true) {
//                buffer.clear();
//                SocketAddress clientAddress = serverChannel.receive(buffer);
//                if (clientAddress != null) {
//                    buffer.flip();
//                    ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
//                    ObjectInputStream ois = new ObjectInputStream(bais);
//                    String command = (String) ois.readObject();
//                    Object data = null;
//                    if (command.startsWith("add") || command.startsWith("update 1") || command.startsWith("update 2") || command.startsWith("update 3") || command.startsWith("remove_lower")) {
//                        data = ois.readObject();
//                    }
//                    String response = handleCommand(command, data);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ObjectOutputStream oos = new ObjectOutputStream(baos);
//                    oos.writeObject(response);
//                    oos.flush();
//                    buffer.clear();
//                    buffer.put(baos.toByteArray());
//                    buffer.flip();
//                    serverChannel.send(buffer, clientAddress);
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String handleCommand(String command, Object data) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//
//        if (command.startsWith("add")) {
//            studyGroups.add((StudyGroup) data);
//            return "Added";
//        } else if (command.equals("clear")) {
//            commandManager.executeCommand("clear");
//            return "Cleared";
//        } else if (command.equals("info")) {
//            return Info.info(studyGroups);
//        } else if (command.equals("show")) {
//            return Show.execute(studyGroups);
//        } else if (command.startsWith("update 1") || command.startsWith("update 2") || command.startsWith("update 3") || command.startsWith("update 4")) {
//            studyGroups.add((StudyGroup) data);
//            commandManager.executeCommand("remove_by_id 3");
//            return "Updated";
//        } else if (command.startsWith("remove_by_id")) {
//            commandManager.executeCommand(command);
//            return "Removed by ID";
//        } else if (command.equals("add_if_min")) {
//            StudyGroup minStudyGroup = studyGroups.stream()
//                    .min(Comparator.comparingDouble(StudyGroup::getAverageMark))
//                    .orElse(null);
//
//            if (minStudyGroup == null || ((StudyGroup) data).getAverageMark() < minStudyGroup.getAverageMark()) {
//                studyGroups.add((StudyGroup) data);
//                return "Added";
//            }
//        } else if (command.equals("add_if_max")) {
//            StudyGroup maxStudyGroup = studyGroups.stream()
//                    .max(Comparator.comparingDouble(StudyGroup::getAverageMark))
//                    .orElse(null);
//
//            if (maxStudyGroup == null || ((StudyGroup) data).getAverageMark() > maxStudyGroup.getAverageMark()) {
//                studyGroups.add((StudyGroup) data);
//                return "Added ";
//            }
//        } else if (command.equals("remove_lower")) {
//            commandManager.executeCommand("remove_lower");
//            return "Removed ";
//        } else if (command.equals("max_by_creation_date")) {
//            commandManager.executeCommand("max_by_creation_date");
//            return MaxByCreationDate.max(studyGroups);
//        } else if (command.startsWith("count_by_group_admin")) {
//            commandManager.executeCommand(command);
//            return CountBy.countby(command.split(" "));
//
//        } else if (command.startsWith("filter_less_than_form_of_education")) {
//            commandManager.executeCommand(command);
//            return "Filtered";
//        } else if (command.equals("help")) {
//            return Help.help();
//        } else if (command.equals("")) {
//            return "";
//        }
//        return "unknown command";
//    }
//}
import common.model.StudyGroup;
        import server.commands.*;
        import server.readwrite.Reader;

        import java.io.*;
        import java.net.*;
        import java.nio.channels.*;
        import java.nio.*;
        import java.util.*;

public class Server {
    private static final int PORT = 6667;
    public static Reader reader = new Reader();

    public static String filename = "std.csv";
    private static Set<StudyGroup> studyGroups = reader.readFromCSV(filename);
    static ComManager commandManager = new ComManager(studyGroups, filename);

    public static void main(String[] args) {

        try {
            DatagramChannel serverChannel = DatagramChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(4096);

            Thread commandListener = new Thread(() -> {
                try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (true) {
                        String serverCommand = consoleReader.readLine();
                        if ("save".equals(serverCommand)) {
                            saveAndExit();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            commandListener.setDaemon(true);
            commandListener.start();

            while (true) {
                buffer.clear();
                SocketAddress clientAddress = serverChannel.receive(buffer);
                if (clientAddress != null) {
                    buffer.flip();
                    ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    String command = (String) ois.readObject();
                    Object data = null;
                    if (command.startsWith("add") || command.startsWith("update 1") || command.startsWith("update 2") || command.startsWith("update 3") || command.startsWith("remove_lower")) {
                        data = ois.readObject();
                    }
                    String response = handleCommand(command, data);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(response);
                    oos.flush();
                    buffer.clear();
                    buffer.put(baos.toByteArray());
                    buffer.flip();
                    serverChannel.send(buffer, clientAddress);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String handleCommand(String command, Object data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        if (command.startsWith("add")) {
            studyGroups.add((StudyGroup) data);
            return "Added";
        } else if (command.equals("clear")) {
            commandManager.executeCommand("clear");
            return "Cleared";
        } else if (command.equals("info")) {
            return Info.info(studyGroups);
        } else if (command.equals("show")) {
            return Show.execute(studyGroups);
        } else if (command.startsWith("update 1") || command.startsWith("update 2") || command.startsWith("update 3") || command.startsWith("update 4")) {
            studyGroups.add((StudyGroup) data);
            commandManager.executeCommand("remove_by_id 3");
            return "Updated";
        } else if (command.startsWith("remove_by_id")) {
            commandManager.executeCommand(command);
            return "Removed by ID";
        } else if (command.equals("add_if_min")) {
            StudyGroup minStudyGroup = studyGroups.stream()
                    .min(Comparator.comparingDouble(StudyGroup::getAverageMark))
                    .orElse(null);

            if (minStudyGroup == null || ((StudyGroup) data).getAverageMark() < minStudyGroup.getAverageMark()) {
                studyGroups.add((StudyGroup) data);
                return "Added";
            }
        } else if (command.equals("add_if_max")) {
            StudyGroup maxStudyGroup = studyGroups.stream()
                    .max(Comparator.comparingDouble(StudyGroup::getAverageMark))
                    .orElse(null);

            if (maxStudyGroup == null || ((StudyGroup) data).getAverageMark() > maxStudyGroup.getAverageMark()) {
                studyGroups.add((StudyGroup) data);
                return "Added ";
            }
        } else if (command.equals("remove_lower")) {
            commandManager.executeCommand("remove_lower");
            return "Removed ";
        } else if (command.equals("max_by_creation_date")) {
            commandManager.executeCommand("max_by_creation_date");
            return MaxByCreationDate.max(studyGroups);
        } else if (command.startsWith("count_by_group_admin")) {
            commandManager.executeCommand(command);
            return CountBy.countby(command.split(" "));

        } else if (command.startsWith("filter_less_than_form_of_education")) {
            commandManager.executeCommand(command);
            return "Filtered";
        } else if (command.equals("help")) {
            return Help.help();
        } else if (command.equals("")) {
            return "";
        }
        return "unknown command";
    }

    private static void saveAndExit() {
        commandManager.executeCommand("save");
        System.out.println("Сервер закрывается...");
        System.exit(0);
    }
}
