import common.model.StudyGroup;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6667;

    public static void main(String[] args) {
        try {
            DatagramChannel clientChannel = DatagramChannel.open();
            clientChannel.configureBlocking(false);
            InetSocketAddress serverAddress = new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT);
            ByteBuffer buffer = ByteBuffer.allocate(4096); // увеличьте размер буфера
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter command: ");
                try {
                    String command = scanner.nextLine();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);

                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Bye");
                        break;
                    }

                    if (command.startsWith("add")|| command.startsWith("remove_lower")) {
                        // Создаем объект StudyGroup
                        StudyGroup studyGroup = client.commands.Add.inputCollection();

                        // Отправка команды и объекта на сервер
                        oos.writeObject(command);
                        oos.writeObject(studyGroup);
                    } else if (command.startsWith("update 1") || command.startsWith("update 2") || command.startsWith("update 3")) {
                        // Создаем объект StudyGroup
                        StudyGroup studyGroup = client.commands.UpdateId.inputCollection();

                        // Отправка команды и объекта на сервер
                        oos.writeObject(command);
                        oos.writeObject(studyGroup);
                    }else if (command.startsWith("clear")) {
                        oos.writeObject(command);
                    } else if (command.equals("")) {
                        continue;
                    } else {
                        // Отправка только команды на сервер
                        oos.writeObject(command);
                    }

                    oos.flush();
                    byte[] data = baos.toByteArray();
                    buffer.clear();
                    buffer.put(data);
                    buffer.flip();
                    clientChannel.send(buffer, serverAddress);

                    // Получение ответа от сервера
                    buffer.clear();
                    while (true) {
                        buffer.clear();
                        clientChannel.receive(buffer);
                        buffer.flip();
                        if (buffer.hasRemaining()) {
                            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                            ObjectInputStream ois = new ObjectInputStream(bais);
                            String response = (String) ois.readObject();
                            System.out.println("Response from server: " + response);
                            break;
                        }
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Выход из программы.");
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            clientChannel.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
