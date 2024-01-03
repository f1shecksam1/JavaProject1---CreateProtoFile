import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Program {

    public static void main(String[] args) {
        ScalarValuesCSharp csharp;
        ScalarValuesCPP cpp;
        ScalarValuesJAVA java;
        ScalarValuesPython python;
        ProgramFunctions programFunctions = new ProgramFunctions();


        csharp = new ScalarValuesCSharp();
        cpp = new ScalarValuesCPP();
        java = new ScalarValuesJAVA();
        python = new ScalarValuesPython();

        try (Scanner input = new Scanner(System.in)) {
            String programmingLanguage = programFunctions.ChooseProgramingLanguage(input);
            String protoFileName = programFunctions.SetProtoFileName(input);
            String protoFileNameWithProto = protoFileName + ".proto";

            try {
                Path path = Paths.get(protoFileNameWithProto);
                List<String> lines = new ArrayList<>();

                if (Files.exists(path)) {
                    lines = Files.readAllLines(path);
                    Files.write(path, lines);
                } else {
                    System.out.println("Proto dosyasi olusturuldu.");
                    Files.createFile(path);

                    programFunctions.AddBaseStringToProto(lines, protoFileName);
                    List<String> messageNames = new ArrayList<>();
                    try {
                        while (true) {
                            System.out.print("Mesajinizi giriniz. Cikmak icin bitti yaziniz: ");
                            String inputMessage = input.nextLine();
                            if (programFunctions.ControlEndLoop(inputMessage)) {
                                break;
                            }
                            if (!ProgramFunctions.ControlFunctions.messageControl(inputMessage) ||
                                    (programFunctions.ControlListContain(messageNames,
                                            "Daha once kullanilmis bir mesaj adi girdiniz. Lutfen tekrar deneyin.",
                                            inputMessage)) ) {
                                continue;
                            }
                            lines.add(programFunctions.OpenRpcMessage(inputMessage));
                            messageNames.add(inputMessage);
                            int varCount = 1;
                            List<String> varNames = new ArrayList<>();
                            while (true) {
                                System.out.printf(" %d. degisken tipini giriniz. Cikmak icin bitti yaziniz: ",
                                        varCount);
                                String varType = input.nextLine();

                                if (programFunctions.ControlEndLoop(varType)) {
                                    break;
                                }

                                String protoVarType = switch (programmingLanguage) {
                                    case "C#" -> programFunctions.getVarType(csharp, varType);
                                    case "C++" -> programFunctions.getVarType(cpp, varType);
                                    case "Java" -> programFunctions.getVarType(java, varType);
                                    case "Python" -> programFunctions.getVarType(python, varType);
                                    default -> "";
                                };

                                if (Objects.equals(protoVarType, "")) {
                                    System.out.println("Hatali bir degisken turu girdiniz. Lutfen tekrar deneyin.");
                                    continue;
                                }
                                programFunctions.AddVarToMessageLoop(input, varCount, varNames, lines, protoVarType);
                                varCount += 1;

                            }
                            lines.add("}\n\n");
                            Files.write(path, lines);
                        }
                        lines.add(programFunctions.AddService(protoFileName));
                        List<String> rpcNames = new ArrayList<>();
                        while (!messageNames.isEmpty()) {
                            System.out.print("RPC adini giriniz. Cikmak icin bitti yaziniz: ");
                            String rpcName = input.nextLine();

                            if (programFunctions.ControlEndLoop(rpcName)) {
                                break;
                            }

                            if (!ProgramFunctions.ControlFunctions.messageControl(rpcName) ||
                                    (programFunctions.ControlListContain(rpcNames,
                                            "Daha once kullanilmis bir RPC adi girdiniz. Lutfen tekrar deneyin.",
                                            rpcName)) ) {
                                continue;
                            }
                            System.out.println("Mesajlar:");

                            for (String mesageN : messageNames) {
                                System.out.print(mesageN + " ");
                                System.out.println();
                            }

                            System.out.println("Once client, sonra server mesaj ismini girin.");
                            programFunctions.AddRpcLoop(input, messageNames, lines, rpcName);
                            rpcNames.add(rpcName);
                            
                        }
                        lines.add("}\n");
                        Files.write(path, lines);

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