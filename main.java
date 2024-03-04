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

                String primeiraLinha = br.readLine();
                String[] tamanhoMapa = primeiraLinha.split(" ");
                int linhas = Integer.parseInt(tamanhoMapa[0]);
                int colunas = Integer.parseInt(tamanhoMapa[1]);


                boolean encontrouInicio = false;
                int linhaAtual = 1;

                while(!encontrouInicio && linhaAtual <= linhas){
                    String linha = br.readLine();
                    if (linha.startsWith("-")) {
                        encontrouInicio = true;
                    }
                    linhaAtual++;
                }

                if (encontrouInicio) {
                    processarMapa(br, linhas, colunas);
                } else {
                    System.out.println("Não foi possível encontrar o início do caminho no mapa: " + caminho);
                }

            }
            catch(IOException e){
                System.out.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void processarMapa(BufferedReader br, int linhas, int colunas) throws IOException {
        int somaValores = 0;
        char[][] mapa = new char[linhas][colunas];

        // Preenche o mapa
        for (int i = 0; i < linhas; i++) {
            String linha = br.readLine();
            for (int j = 0; j < colunas; j++) {
                mapa[i][j] = linha.charAt(j);
            }
        }

        int posX = 0;
        int posY = 0;
        int direcaoX = 1; // Inicia da esquerda para a direita
        int direcaoY = 0;

        while (posX >= 0 && posX < colunas && posY >= 0 && posY < linhas) {
            char simbolo = mapa[posY][posX];
            processarSimbolo(simbolo, mapa, linhas, colunas, posX, posY, direcaoX, direcaoY, somaValores);
            posX += direcaoX;
            posY += direcaoY;
        }

        System.out.println("Soma dos valores encontrados: " + somaValores);
    }

    public static void processarSimbolo(char simbolo, char[][] mapa, int linhas, int colunas, int posX, int posY, int direcaoX, int direcaoY, int somaValores) {
        if (simbolo == '-' || simbolo == '|') {
            // Verifica a próxima posição na direção que estamos indo
            int proximoX = posX + direcaoX;
            int proximoY = posY + direcaoY;
    
            // Certifica-se de que estamos dentro dos limites do mapa
            if (proximoX >= 0 && proximoX < colunas && proximoY >= 0 && proximoY < linhas) {
                char proximoSimbolo = mapa[proximoY][proximoX];
    
                // Verifica se o próximo símbolo é uma rua
                if (proximoSimbolo == '-' || proximoSimbolo == '|') {
                    // Continua na mesma direção
                    posX = proximoX;
                    posY = proximoY;
                }
            }
        } else if (Character.isDigit(simbolo)) {
            // Lógica para processar números inteiros e decimais
            StringBuilder numero = new StringBuilder();
            numero.append(simbolo);
    
            int proximoX = posX + direcaoX;
            int proximoY = posY + direcaoY;
    
            while (proximoX >= 0 && proximoX < colunas && proximoY >= 0 && proximoY < linhas) {
                char proximoSimbolo = mapa[proximoY][proximoX];
    
                if (Character.isDigit(proximoSimbolo) || proximoSimbolo == '.') {
                    // Continua adicionando ao número
                    numero.append(proximoSimbolo);
                    proximoX += direcaoX;
                    proximoY += direcaoY;
                } else {
                    // Encontrou o final do número
                    break;
                }
            }
    
            // Converte o número para double e adiciona à soma
            double valor = Double.parseDouble(numero.toString());
            somaValores += valor;
    
            // Atualiza a posição para a próxima leitura
            posX = proximoX;
            posY = proximoY;
        } else if (simbolo == '/' || simbolo == '\\') {
            // Lógica para processar curvas
            if (simbolo == '/' && (direcaoX != 0 || direcaoY != 0)) {
                // Curva para cima
                int temp = direcaoX;
                direcaoX = -direcaoY;
                direcaoY = -temp;
            } else if (simbolo == '\\' && (direcaoX != 0 || direcaoY != 0)) {
                // Curva para baixo
                int temp = direcaoX;
                direcaoX = direcaoY;
                direcaoY = temp;
            } else if (simbolo == '/' && direcaoY != 0) {
                // Curva para a esquerda
                direcaoX = 0;
                direcaoY = -1;
            } else if (simbolo == '\\' && direcaoY != 0) {
                // Curva para a direita
                direcaoX = 0;
                direcaoY = 1;
            }
    
            // Atualiza a posição para a próxima leitura
            posX += direcaoX;
            posY += direcaoY;
        }
    }

}