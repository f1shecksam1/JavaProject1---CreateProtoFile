import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Please write your proto file name.");
            String protoFileName = input.nextLine();
            String protoFileNameWithProto = protoFileName + ".proto";

            try {
                Path path = Paths.get(protoFileNameWithProto);
                if (Files.exists(path)) {
                    List<String> satirlar = Files.readAllLines(path);
                    Files.write(path, satirlar);
                } else {
                    System.out.println("New proto file created.");
                    Files.createFile(path);
                    List<String> satirlar = Files.readAllLines(path);
                    satirlar.add("syntax = \"proto3\";");
                    satirlar.add("");
                    satirlar.add("package " + protoFileName);
                    Files.write(path, satirlar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (Scanner inputs = new Scanner(System.in)) {
                ScalarValuesCSharp csharp = new ScalarValuesCSharp();
                String name = inputs.nextLine();

                for (String key : csharp.scalarValuesMap.keySet()) {
                    String value = csharp.scalarValuesMap.get(key);
                    if (key.equals(name)) {
                        System.out.println(value);
                    }
                }
            }
        }
    }
}
