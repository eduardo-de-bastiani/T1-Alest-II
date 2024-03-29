import time

#função principal que faz o caminho e soma na ordem
def calcular_soma_numeros(p_inicial, conteudo):
    coluna_at, linha_at = p_inicial
    direcao = (0, 1)
    total = 0
    linhas = conteudo.strip().split('\n')

    while True:
        if linha_at < 0 or linha_at >= len(linhas) or coluna_at < 0 or coluna_at >= len(linhas[linha_at]):
            break
        char = linhas[linha_at][coluna_at] #define o caracter atual

        if char == '#':  #finaliza quando achar o fim
            break

        elif char == '-': #continua na mesma direção
            linha_at += direcao[0]
            coluna_at += direcao[1]


        elif char == '|': #como esse símbolo vária dependendo da direção temq ter uma verificação pra todas as direções
            if direcao == (0, 1): #direita
                direcao = (0, 1)
            elif direcao == (0, -1): #esquerda
                direcao = (0, -1)
            elif direcao == (-1, 0): #cima
                direcao = (-1, 0)
            elif direcao == (1, 0): #baixo
                direcao = (1, 0)
            linha_at += direcao[0]
            coluna_at += direcao[1]

        elif char == '/': 
            if direcao == (-1, 0):  
                direcao = (0, 1)  
            elif direcao == (1, 0): 
                direcao = (0, -1)  
            elif direcao == (0, 1):  
                direcao = (-1, 0)  
            elif direcao == (0, -1):  
                direcao = (1, 0)  
            linha_at += direcao[0]
            coluna_at += direcao[1]

        elif char == '\\': 
            if direcao == (-1, 0):  
                direcao = (0, -1)  
            elif direcao == (1, 0):  
                direcao = (0, 1) 
            elif direcao == (0, 1):
                direcao = (1, 0)  
            elif direcao == (0, -1):  
                direcao = (-1, 0) 
            linha_at += direcao[0]
            coluna_at += direcao[1]
            
        elif char.isdigit():
            num = ''
            while char.isdigit():
                num += char
                linha_at += direcao[0]
                coluna_at += direcao[1]
                if 0 <= linha_at < len(linhas) and 0 <= coluna_at < len(linhas[linha_at]):
                    char = linhas[linha_at][coluna_at]
                else:
                    break
            total += int(num)

    return total  

#realiza a soma dos números fora de ordem (apenas somando todos os presentes no arquivo)
def soma_fora_ordem(arquivo):
    soma = 0
    primeira_linha = True
    
    for linha in arquivo:
        if primeira_linha:
            primeira_linha = False
            continue
        linha = linha.strip()
        for char in linha:
            if char.isdigit():
                soma += int(char)
    return soma

#método que encontra a posição inicial do arquivo
def encontra_inicial(conteudo):
    linhas = conteudo.strip().split('\n')
    for y, linha in enumerate(linhas):
        if linha.strip() and linha.strip()[0] == '-':
            return (0, y)
    return None

#função para printar o resultado
def printa_resultado(p_inicial, conteudo, nome):
    indice = nome.rfind('/')
    nome_arquivo = nome[indice + 1:]
    if p_inicial:
        print(f"------------ [ ARQUIVO : {nome_arquivo} ] ------------")

        start_time = time.time()

        print("Posição inicial: ", p_inicial)

        soma = calcular_soma_numeros(p_inicial, conteudo)
        print("Soma dos números encontrados: ", soma)

        soma_n_ordenada = soma_fora_ordem(conteudo)
        print("Soma dos números encontrados fora de ordem: ", soma_n_ordenada)

        end_time = time.time()
        tempo = end_time - start_time
        tempo_form = f"{tempo: .5f}"

        print("Tempo decorrido para realizar o caminho e gerar os resultados: ", tempo_form, "segundos")

        print("\n-----------------------------------------------------------\n")
    else:
        print("Não foi possível encontrar a posição inicial no arquivo: ", nome_arquivo)

#função que lê o arquivo e chama outros métodos
def processa_arquivo(nome):
    
    with open(nome, 'r') as arquivo:
        conteudo = arquivo.read()
        if conteudo:
            p_inicial = encontra_inicial(conteudo)
            printa_resultado(p_inicial, conteudo, nome)
    


#definição dos arquivos a serem lidos
nomes_arq = ['casos-cohen-noite/casoG50.txt', 'casos-cohen-noite/casoG100.txt', 'casos-cohen-noite/casoG200.txt', 'casos-cohen-noite/casoG500.txt', 
             'casos-cohen-noite/casoG750.txt', 'casos-cohen-noite/casoG1000.txt', 'casos-cohen-noite/casoG1500.txt', 'casos-cohen-noite/casoG2000.txt']

#main
for nome in nomes_arq:
    processa_arquivo(nome)
            