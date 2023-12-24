import java.util.List;
import java.util.Scanner;
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
        }
    }
}
