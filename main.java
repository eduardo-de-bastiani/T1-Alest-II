import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main{
    public static void main(String[] args) {
        List<String> caminhosArquivos = new ArrayList<>();
        java.io.File arquivo1 = new java.io.File("casos-cohen-noite/casoG50.txt");
        java.io.File arquivo2 = new java.io.File("casos-cohen-noite/casoG100.txt");
        java.io.File arquivo3 = new java.io.File("casos-cohen-noite/casoG200.txt");
        java.io.File arquivo4 = new java.io.File("casos-cohen-noite/casoG500.txt");
        java.io.File arquivo5 = new java.io.File("casos-cohen-noite/casoG750.txt");
        java.io.File arquivo6 = new java.io.File("casos-cohen-noite/casoG1000.txt");
        java.io.File arquivo7 = new java.io.File("casos-cohen-noite/casoG1500.txt");
        java.io.File arquivo8 = new java.io.File("casos-cohen-noite/casoG2000.txt");
        caminhosArquivos.add(arquivo1.getAbsolutePath());
        caminhosArquivos.add(arquivo2.getAbsolutePath());
        caminhosArquivos.add(arquivo3.getAbsolutePath());
        caminhosArquivos.add(arquivo4.getAbsolutePath());
        caminhosArquivos.add(arquivo5.getAbsolutePath());
        caminhosArquivos.add(arquivo6.getAbsolutePath());
        caminhosArquivos.add(arquivo7.getAbsolutePath());
        caminhosArquivos.add(arquivo8.getAbsolutePath());

        leArquivos(caminhosArquivos);
    }

    public static void leArquivos(List<String> caminhosArquivos){
        for (String caminho : caminhosArquivos) {
            try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
                //teste 
                System.out.println("Arquivo " + caminho + " lido com sucesso");
            }
            catch(IOException e){
                System.out.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}