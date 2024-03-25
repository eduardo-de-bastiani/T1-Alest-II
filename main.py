def calcular_soma_numeros(p_inicial, txt):
    linhas = txt.strip().split('\n')
    linha_at, coluna_at = p_inicial
    direcao = (0, 1)
    total = 0
    
    while True:
        char = linhas[linha_at][coluna_at] #define o caracter atual
        print(char)

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
            
        elif char.isdigit():  #encontrou um número
            num = ''
            while char.isdigit():
                num += char
                linha_at += direcao[0]
                coluna_at += direcao[1]
                char = linhas[linha_at][coluna_at]
            total += int(num)

    return total   

nomes_arq = ['casos-cohen-noite/casoG50.txt', 'casos-cohen-noite/casoG100.txt', 'casos-cohen-noite/casoG200.txt', 'casos-cohen-noite/casoG500.txt', 
             'casos-cohen-noite/casoG750.txt', 'casos-cohen-noite/casoG1000.txt', 'casos-cohen-noite/casoG1500.txt', 'casos-cohen-noite/casoG2000.txt']

for nome in nomes_arq:
    with open(nome, 'r') as arquivo:
        conteudo = arquivo.read()
        if conteudo:
            #encontrar a posição inicial
            linhas = conteudo.strip().split('\n')  #.strip() pode causar problemas     
            p_inicial = None
            for y, linha in enumerate(linhas):
                if linha.startswith(" "):
                    continue
                elif linha[0] == '-':
                    p_inicial = (0,y)
                    break
            
            print("A posicao inicial é: ", p_inicial)
            soma = calcular_soma_numeros(p_inicial, conteudo)
            print("Soma dos números encontrados:", soma)      
