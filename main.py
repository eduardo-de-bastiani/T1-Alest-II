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



        
        

