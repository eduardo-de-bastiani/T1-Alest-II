import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        carregaArquivo();
    }

    public static void carregaArquivo() {
        List<String> caminhosArquivos = new ArrayList<>();
        String[] nomesArquivos = { "casoG50.txt" }; // ", casoG100.txt", "casoG200.txt", "casoG500.txt", "casoG750.txt","casoG1000.txt", "casoG1500.txt", "casoG2000.txt"

        for (String nome : nomesArquivos) {
            java.io.File arquivo = new java.io.File("casos-cohen-noite/" + nome);
            caminhosArquivos.add(arquivo.getAbsolutePath());
        }
 
        leArquivos(caminhosArquivos);
    }

    public static void leArquivos(List<String> caminhosArquivos) {
        for (String caminho : caminhosArquivos) {
            System.out.println("Lendo o arquivo: " + caminho);
            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                System.out.println("Arquivo " + caminho + " lido com sucesso");

                String primeiraLinha = br.readLine();
                String[] tamanhoMapa = primeiraLinha.split(" ");
                int linhas = Integer.parseInt(tamanhoMapa[0]);
                int colunas = Integer.parseInt(tamanhoMapa[1]);

                int inicioY = encontrarCaminhoInicial(br);

                if (inicioY != -1) {
                    processarMapa(br, linhas, colunas, inicioY, 0);
                } else {
                    System.out.println("Não foi possível encontrar o início do caminho: " + caminho);
                }

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static int encontrarCaminhoInicial(BufferedReader br) throws IOException {
        int linhaAtual = 0;
        String linha = br.readLine();
        while (linha != null) {
            if (linha.startsWith("-")) {
                return linhaAtual;
            }
            linhaAtual++;
            linha = br.readLine();
        }
        return -1;
    }

    public static void processarMapa(BufferedReader br, int linhas, int colunas, int inicioY, int inicioX)
            throws IOException {
        int somaValores = 0;
        char[][] mapa = new char[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            String linha = br.readLine();
            if (linha == null) {
                break;
            }

            for (int j = 0; j < colunas; j++) {
                mapa[i][j] = linha.charAt(j);
            }
        }

        int posX = inicioX;
        int posY = inicioY;
        int direcaoX = 1;
        int direcaoY = 0;
        int passos = 0;

        while (posX >= 0 && posX < colunas && posY >= 0 && posY < linhas) {
                char simbolo = mapa[posY][posX];
                // teste
                System.out.println("Posição: (" + posY + ", " + posX + ") - simbolo: " + simbolo);
                processarSimbolo(simbolo, mapa, linhas, colunas, posX, posY, direcaoX, direcaoY, somaValores);
            

            posX += direcaoX;
            posY += direcaoY;
            passos++;

            if (passos > linhas * colunas) {
                System.out.println("Loop infinito!");
                break;
            }
        }

        System.out.println("Soma dos valores encontrados: " + somaValores);
        System.out.println("Numero total de passos: " + passos);
    }

    public static void processarSimbolo(char simbolo, char[][] mapa, int linhas, int colunas, int posX, int posY,
            int direcaoX, int direcaoY, int somaValores) {
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