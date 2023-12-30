import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void setdefaultMessage(String userInput, List<String> lines , List<String>  messageVeriables,List<String> messageVeriablesName){
        String messageBase = "message " + userInput + "{";
        lines.add(messageBase);
        for (int i = 0;i<messageVeriables.size();i++){
            String messageVeriables1 =  messageVeriables.get(i) + " " + messageVeriablesName.get(i) + " = " + (i+1);
        }

    }
    public static void main(String[] args) {

        ScalarValuesCSharp csharp;
        ScalarValuesCPP cpp;
        ScalarValuesJAVA java;
        ScalarValuesPython python;
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Please write your programming language (C#, C++, Java, Python): ");
            String programmingLanguage = input.nextLine();
            while (!programmingLanguage.equals("C#") && !programmingLanguage.equals("C++") && !programmingLanguage.equals("Java") && !programmingLanguage.equals("Python")) {
                System.out.println("Gecersiz dil girdiniz. Lutfen tekrar giriniz.");
                System.out.println("Please write your programming language (C#, C++, Java, Python): ");
                programmingLanguage = input.nextLine();
            }
            System.out.println("Please write your proto file name: ");
            String protoFileName = input.nextLine();
            String protoFileNameWithProto = protoFileName + ".proto";
            String selectedLanguage="";

            switch (programmingLanguage){
                case "C#":
                    selectedLanguage="C#";
                    csharp = new ScalarValuesCSharp();
                    System.out.println("C# switch girdi");
                    break;
                case "C++":
                    selectedLanguage="C+";
                    cpp = new ScalarValuesCPP();
                    break;

                case "Java":
                    selectedLanguage="Java";
                    java = new ScalarValuesJAVA();
                    break;
                case  "Python":
                    selectedLanguage="Python";
                    python = new ScalarValuesPython();
                    break;

                default:
                    System.out.println("SwitchCase defalt. Cıkıs yaptı. Noluyo lan");
                    break;
            }

            try {
                Path path = Paths.get(protoFileNameWithProto);
                List<String> lines = new ArrayList<>();

                if (Files.exists(path)) {
                    lines = Files.readAllLines(path);
                    Files.write(path, lines);
                } else {
                    System.out.println("New proto file created.");
                    Files.createFile(path);

                    lines.add("syntax = \"proto3\";");
                    lines.add("");
                    lines.add("package " + protoFileName);
                    //Files.write(path, lines);
                    try {
                        while(true){
                            System.out.println("Mesajınızı giriniz. Cikmak icin bitti yaziniz");
                            String girilenMessage = input.nextLine();
                            if(girilenMessage.equals("bitti")){
                                System.out.println("Cıkıs yapıldı");
                                break;
                            }
                            if (girilenMessage.isEmpty() || Character.isDigit(girilenMessage.charAt(0)) || girilenMessage.contains(" "))
                            {

                                    System.out.println("Hata: Girişte başında sayı ve aralarda boşluk bulunmamalıdır.");
                                    continue;

                            };

                            lines.add(girilenMessage);
                            Files.write(path, lines);
                        }
                       // Files.write(path, lines);
                        //sayı almak yerıne sınırsız gırıs hakkı verelım kac kelime girerse onu sayacta tutalım



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