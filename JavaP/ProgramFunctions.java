
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProgramFunctions {
    private static final String[] RESERVED_KEYWORDS = { "int", "double", "float", "char", "boolean", "String" };

    public String getVarType(ScalarValuesCSharp base, String input) {
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

    public String getVarType(ScalarValuesCPP base, String input) {
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

    public String getVarType(ScalarValuesJAVA base, String input) {
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

    public String getVarType(ScalarValuesPython base, String input) {
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

    public class ControlFunctions {
        static boolean messageControl(String inputMessage) {
            if (inputMessage.isEmpty()
                    || !Character.isLetter(inputMessage.charAt(0))
                    || inputMessage.contains(" ")
                    || Arrays.asList(RESERVED_KEYWORDS).contains(inputMessage)
                    || inputMessage.matches(".*[üşöçİğÜŞÖÇıĞ].*"))

            {
                System.out.println(
                        "Hata: Giriste basinda sayi , Turkce harf , aralarda bosluk ve ozel karakter bulunmamalidir.");
                return false;
            }
            ;
            return true;
        }
    }


    public String makeFirstCharBig(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String kucukHarfString = str.toLowerCase();
        return Character.toUpperCase(kucukHarfString.charAt(0)) + kucukHarfString.substring(1);
    }

    public boolean ControlProgramingLanguage(String programmingLanguage) {
        return !programmingLanguage.equals("C#") && !programmingLanguage.equals("C++")
                && !programmingLanguage.equals("Java") && !programmingLanguage.equals("Python");
    }

    public String ChooseProgramingLanguage(Scanner input) {
        System.out.print("Lutfen programlama dilini secin (C#, C++, Java, Python): ");
        String programmingLanguage = input.nextLine();
        programmingLanguage = makeFirstCharBig(programmingLanguage);
        while (ControlProgramingLanguage(programmingLanguage)) {
            System.out.println("Gecersiz dil girdiniz. Lutfen tekrar giriniz.");
            System.out.print("Lutfen programlama dilini secin (C#, C++, Java, Python): ");
            programmingLanguage = input.nextLine();
        }
        return programmingLanguage;
    }

    public String SetProtoFileName(Scanner input) {
        while (true) {
            String protoFileName;
            System.out.print("Proto dosyasi ismini girin: ");
            protoFileName = input.nextLine();
            if (!ControlFunctions.messageControl(protoFileName)) {
                continue;
            }
            return protoFileName;
        }
    }

    public boolean ControlEndLoop(String bitti) {
        if (bitti.equals("bitti")) {
            System.out.println("Cikis yapildi");
            return true;
        }
        return false;
    }

    public boolean ControlListContain(List<String> controlList, String WarningMessage, String controlStr) {
        if (controlList.contains(controlStr)) {
            System.out.println(WarningMessage);
            return true;
        }
        return false;
    }
    public boolean ControlListNotContain(List<String> controlList, String WarningMessage, String controlStr) {
        if (controlList.contains(controlStr)) {
            return false;
        }
        System.out.println(WarningMessage);
        return true;
    }

    public String AddRpc(String rpcName, String clientMessage, String serverMessage) {
        return "     rpc " + rpcName + "(" + clientMessage + ") returns ("
                + serverMessage + ") {}";
    }
    public String AddService(String protoFileName) {
        return "service " + protoFileName + " {";
    }

    public String AddVarToMessage(String protoVarType, String varName, Integer varCount) {
        return protoVarType + " " + varName + " = " + varCount + ";";
    }

    public void AddBaseStringToProto(List<String> lines, String protoFileName) {
        lines.add("syntax = \"proto3\";");
        lines.add("");
        lines.add("package " + protoFileName + ";");
        lines.add("\n");
    }

    public String OpenRpcMessage(String girilenMessage) {
        return "message " + girilenMessage + "{ ";
    }

    public void AddVarToMessageLoop(Scanner input, Integer varCount, List<String> varNames, List<String> lines,
            String protoVarType) {
        while (true) {
            System.out.printf("%d. degisken adini giriniz: ", varCount);
            String varName = input.nextLine();
            if (!ControlFunctions.messageControl(varName)) {
                continue;
            }

            if (ControlListContain(varNames,
                    "Daha once kullanilmis bir degisken adi girdiniz. Lutfen tekrar deneyin.",
                    varName)) {
                continue;
            }

            lines.add(AddVarToMessage(protoVarType, varName, varCount));
            varNames.add(varName);
            break;
        }
    }

    public void AddRpcLoop(Scanner input, List<String> messageList, List<String> lines,
            String rpcName) {
        while (true) {
            System.out.print("Client mesaji: ");
            String clientMessage = input.nextLine();

            if (ControlListNotContain(messageList,
                    "Girdiginiz mesaj bulunamadi. Lutfen tekrar deneyin", clientMessage)) {
                continue;
            }

            while (true) {
                System.out.print("Server mesaji: ");
                String serverMessage = input.nextLine();
                if (ControlListNotContain(messageList,
                        "Girdiginiz mesaj bulunamadi. Lutfen tekrar deneyin", serverMessage)) {
                    continue;
                }
                lines.add(AddRpc(rpcName, clientMessage, serverMessage));

                break;
            }
            break;
        }
    }
}
