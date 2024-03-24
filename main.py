nomes_arq = ['casos-cohen-noite/casoG50.txt', 'casos-cohen-noite/casoG100.txt', 'casos-cohen-noite/casoG200.txt', 'casos-cohen-noite/casoG500.txt', 
             'casos-cohen-noite/casoG750.txt', 'casos-cohen-noite/casoG1000.txt', 'casos-cohen-noite/casoG1500.txt', 'casos-cohen-noite/casoG2000.txt']

for nome in nomes_arq:
    with open(nome, 'r') as arquivo:
        conteudo = arquivo.read()
        if conteudo:
            print(f"Lendo o arquivo: {nome}")
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


def criterios(mapa, p_atual, direcao):
    x,y = p_atual; 
    s_atual = mapa[x][y]

    if s_atual == ['-']:
        if direcao == "direita":
            proxima_p = (x + 1, y)

        elif direcao == "esquerda":
            proxima_p = (x - 1, y)
        
        elif direcao == "cima":
            proxima_p = (x, y + 1)
        
        elif direcao == "baixo":
            proxima_p = (x, y - 1)
        
        if mapa[proxima_p[1]][proxima_p[0]] == '-':     #verifica se a proxima_p é rua
            return proxima_p, direcao

    return p_atual, direcao               

