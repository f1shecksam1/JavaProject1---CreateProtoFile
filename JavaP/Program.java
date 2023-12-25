import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Please write your programming language (C#, C++, Java, Python): ");
            String programmingLanguage = input.nextLine();
            System.out.print("Please write your proto file name: ");
            String protoFileName = input.nextLine();
            String protoFileNameWithProto = protoFileName + ".proto";

            try {
                Path path = Paths.get(protoFileNameWithProto);
                if (Files.exists(path)) {
                    List<String> lines = Files.readAllLines(path);
                    Files.write(path, lines);
                } else {
                    System.out.println("New proto file created.");
                    Files.createFile(path);
                    List<String> lines = new ArrayList<>();
                    lines.add("syntax = \"proto3\";");
                    lines.add("");
                    lines.add("package " + protoFileName);
                    Files.write(path, lines);
                    try {
                        System.out.print("Please write your message count: ");
                        int messageCount = input.nextInt();
                        for (int messageIndex = 0; messageIndex < messageCount; messageIndex++) {

                                System.out.printf("Please write your message %d name: ", messageIndex+1);
                                String messageName = input.next();

                                try  {
                                    System.out.printf("Please write your message %d variable count: ", messageIndex+1);
                                    int messageVariableCount = input.nextInt();
                                    for (int variableIndex = 0; variableIndex < messageVariableCount; variableIndex++) {


                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
