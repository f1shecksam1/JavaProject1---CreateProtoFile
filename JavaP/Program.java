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

    public static String getVarType(ScalarValuesBase base, String input){
        try {
            for (String key : base.scalarValuesMap.keySet()) {
                String value = base.scalarValuesMap.get(key);
                if (key.equals(input)) {
                    System.out.println(value);
                    return value;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public static void main(String[] args) {
        ScalarValuesCSharp csharp;
        ScalarValuesCPP cpp;
        ScalarValuesJAVA java;
        ScalarValuesPython python;

        csharp = new ScalarValuesCSharp();
        cpp = new ScalarValuesCPP();
        java = new ScalarValuesJAVA();
        python = new ScalarValuesPython();

        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Please write your programming language (C#, C++, Java, Python): ");
            String programmingLanguage = input.nextLine();
            while (!programmingLanguage.equals("C#") && !programmingLanguage.equals("C++") && !programmingLanguage.equals("Java") && !programmingLanguage.equals("Python")) {
                System.out.println("Gecersiz dil girdiniz. Lutfen tekrar giriniz.");
                System.out.print("Please write your programming language (C#, C++, Java, Python): ");
                programmingLanguage = input.nextLine();
            }
            System.out.print("Please write your proto file name: ");
            String protoFileName = input.nextLine();
            String protoFileNameWithProto = protoFileName + ".proto";
            String selectedLanguage="";

            // programminLanguga kontrolü yap

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
                    lines.add("package " + protoFileName+";");
                    lines.add("\n");
                    //Files.write(path, lines);
                    List<String> messageList = new ArrayList<>();
                    try {
                        while(true){
                            System.out.print("Mesajınızı giriniz. Cikmak icin bitti yaziniz: ");
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
                            lines.add("message "+girilenMessage+"{ ");
                            messageList.add(girilenMessage);
                            int messageCount=0;
                            while (true){
                                messageCount+=1;
                                System.out.printf(" %d. değişken tipini giriniz. Cikmak icin bitti yaziniz: ",messageCount);
                                String varType = input.nextLine();

                                if(varType.equals("bitti")){
                                    System.out.println("Cıkıs yapıldı");
                                    break;
                                }
                                String protoVarType="";
                                switch (programmingLanguage){
                                    case "C#":
                                        protoVarType=getVarType(csharp,varType);
                                        break;
                                    case "C++":
                                        protoVarType=getVarType(cpp,varType);
                                        break;
                                    case "Java":
                                        protoVarType=getVarType(java,varType);
                                        break;
                                    case  "Python":
                                        protoVarType=getVarType(python,varType);
                                        break;
                                    default:
                                        System.out.println("SwitchCase defalt. Cıkıs yaptı. Noluyo lan");
                                        break;
                                }
                                System.out.println(protoVarType);


                                System.out.printf("%d. değişken adını giriniz. \n",messageCount);
                                String varName = input.nextLine();

                                lines.add(varType+" "+varName+" = "+messageCount+";");


                            }
                            lines.add("}\n\n");
                            Files.write(path, lines);
                        }
                        lines.add("service "+protoFileName+" {");

                        while (true){
                            System.out.print("RPC adını giriniz. Cikmak icin bitti yaziniz: ");
                            String rpcName = input.nextLine();
                            if(rpcName.equals("bitti")){
                                System.out.println("Cıkıs yapıldı");
                                break;
                            }
                            System.out.println("Mesajlar:");

                            for (String mesageN: messageList){
                                System.out.print(mesageN+" ");
                                System.out.println();
                            }

                            System.out.println("Önce client, sonra server mesaj ismini girin.");
                            System.out.print("Client mesajı: ");
                            String clientMessage = input.nextLine();

                            System.out.print("Server mesajı: ");
                            String serverMessage = input.nextLine();

                            lines.add("     rpc "+rpcName+"("+clientMessage+") returns ("+serverMessage+") {}");



                        }
                        lines.add("}\n");
                        Files.write(path, lines);
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