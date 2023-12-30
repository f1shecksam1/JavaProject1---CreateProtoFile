import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//değişken isimlerinde türkçe harf kontrolü, değişken isimleri (int, float, double olamaz,) değişken isimleri özel karakter ile başlayamaz,
// değişken ismi kontrolünü dosya adı için de çağır

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
    public static boolean messageControl(String inputMessage){
        if (inputMessage.isEmpty() ||  Character.isDigit(inputMessage.charAt(0)) ||  inputMessage.contains(" ")
        || inputMessage.equals("int")
        || inputMessage.equals("double")
        || inputMessage.equals("float")
        || inputMessage.equals("char")
        || inputMessage.equals("boolean")
        || inputMessage.equals("String"))
        {
            System.out.println("Hata: Girişte başında sayı ve aralarda boşluk bulunmamalıdır.");
            return false;
        };
        return true;
    }

    public static String makeFirstCharBig(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Tüm harfleri küçük yap
        String kucukHarfString = str.toLowerCase();

        // İlk harfi büyük yap

        return Character.toUpperCase(kucukHarfString.charAt(0)) + kucukHarfString.substring(1);
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
            programmingLanguage = makeFirstCharBig(programmingLanguage);
            while (!programmingLanguage.equals("C#") && !programmingLanguage.equals("C++") && !programmingLanguage.equals("Java") && !programmingLanguage.equals("Python")) {
                System.out.println("Gecersiz dil girdiniz. Lutfen tekrar giriniz.");
                System.out.print("Please write your programming language (C#, C++, Java, Python): ");
                programmingLanguage = input.nextLine();
            }
            System.out.print("Please write your proto file name: ");
            String protoFileName = input.nextLine();
            String protoFileNameWithProto = protoFileName + ".proto";
            String selectedLanguage="";

            // programmingLanguage control yap

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
                            if(!messageControl(girilenMessage)){
                                continue;
                            }
                            lines.add("message "+girilenMessage+"{ ");
                            messageList.add(girilenMessage);
                            int varCount=1;
                            List<String> varNames = new ArrayList<>();
                            while (true){
                                System.out.printf(" %d. değişken tipini giriniz. Cikmak icin bitti yaziniz: ",varCount);
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

                                if (Objects.equals(protoVarType, "")){
                                    System.out.println("Hatalı bir değişken türü girdiniz. Lütfen tekrar deneyin.");
                                    continue;
                                }


                                while (true) {
                                    System.out.printf("%d. değişken adını giriniz: ",varCount);
                                    String varName = input.nextLine();
                                    if (!messageControl(varName)) {
                                        continue;
                                    }
                                    if (varNames.contains(varName)){
                                        System.out.println("Daha önce kullanılmış bir değişken adı girdiniz. Lütfen tekrar deneyin.");
                                        continue;
                                    }
                                    lines.add(protoVarType+" "+varName+" = "+varCount+";");
                                    varNames.add(varName);
                                    break;
                                }
                                varCount+=1;
                                


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
                            if(!messageControl(rpcName)){
                                continue;
                            }
                            System.out.println("Mesajlar:");

                            for (String mesageN: messageList){
                                System.out.print(mesageN+" ");
                                System.out.println();
                            }

                            System.out.println("Önce client, sonra server mesaj ismini girin.");
                            while (true) {
                                System.out.print("Client mesajı: ");
                                String clientMessage = input.nextLine();
                                if (!messageList.contains(clientMessage)) {
                                    System.out.println("Girdiginiz mesaj bulunamadi. Lutfen tekrar deneyin");
                                    continue;
                                }

                                while (true) {
                                     System.out.print("Server mesajı: ");
                                     String serverMessage = input.nextLine();
                                     if (!messageList.contains(serverMessage)) {
                                    System.out.println("Girdiginiz mesaj bulunamadi. Lutfen tekrar deneyin");
                                    continue;
                                }
                                     lines.add("     rpc "+rpcName+"("+clientMessage+") returns ("+serverMessage+") {}");

                                     break;
                                }
                                break;
                            }


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