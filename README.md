# AMaze

#### Técnicas de Inteligência Artificial


Esse projeto é um jogo onde o cenário é: um labirinto onde um robô, que inicia na posição inicial (0,0), precisa chegar à posição final (10,10). 

Conforme cada movimento que o robô faz, ele perde uma energia. Ao iniciar o jogo, o robô tem 50 energias e com o seu primeiro movimento é descontado uma energia. 
Ao longo do labirinto, o robô pode encontrar energias, onde ele absorve e é somado com sua energia atual. Temos dois tipos de energias, uma com valor 10 energias (onde pode ser encontrada em 3 casas aleatórias ao decorrer do labirinto) e outra com o valor de 5 energias (onde pode ser encontrada em 5 casas aleatórias ao decorrer do labirinto). Quando acaba sua energia, o robô morre e o jogo acaba. 

Quando o robô entra em um “buraco” ou “vala” ele não pode fazer o movimento para retornar ao caminho anterior, pois ele não pode voltar às casas anteriores, assim o robô morre e o jogo acaba.

O jogo foi criado na linguagem Java, e implementado com os Algoritmos de Largura (ou de Amplitude) e o Algoritmo A*

Identificamos as paredes do labirinto como “[X]” e os caminhos já percorridos pelo robô como “[o]”. As casas que contém as energias podem ser identificadas por “[5]” ou “[10]”.

## Algoritmo de Largura
O algoritmo de largura faz a pesquisa dos próximos caminhos possíveis sendo um mecanismo de controle de busca que utiliza uma técnica de Busca Cega, por ser uma busca sem informação. 
A busca por Amplitude, primeiro verifica todos os próximos passos antes de reproduzir. Todos esses próximos passos serão inseridos em ordem, ou seja, no final de uma lista, onde o próximo passo a ser feito será o primeiro da lista. Assim acumulando os novos resultados.

## Algoritmo A*
Já o Algoritmo A* é um mecanismo de controle de Busca com Informação, utilizando uma função h que pode ser específica para cada tipo de problema.
Nesse cenário de labirinto, utilizei a função h como sendo a distância atual do robô até a posição final (10,10). Então conforme o robô caminha, ele deve escolher a próxima posição onde essa função h terá o menor valor, que será a menor distância do robô até a posição final. Seguindo o menor valor, o robô deverá saber se está chegando perto da solução do labirinto ou não. Assim, ele sempre escolherá a próxima posição que contém a menor distancia da solução final do labirinto.
