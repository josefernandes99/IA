--COMPILAR--
Na consola (com localizaçao na pasta com o ficheiro ProjetoIA.java), correr o seguinte comando:
    javac ProjetoIA.java

--EXECUTAR--
Depois de compilar corretamente o programa, correr na consola o seguinte comando:
    java ProjetoIA

--USAR--
Depois de executar, o resto deverá ser intuitivo. Serão apresentadas várias opções (inserir o m, inserir o n, escolher entre uma permutaçao qualquer ou o uso do nearest-neighbour first,
escolher entre as 4 opções de melhoramento iterativo,...) e o utilizador apenas terá de selecionar o pretendido para o seu teste.

--QUESTÕES QUE RESOLVE--
Posso estar errado, mas penso que este mesmo programa qualquer problema desde o exercício 1 até ao exercicio 4.

--BUGS CONHECIDOS--
    -Programei de forma a o gerador de pontos não produzir mais de 2 pontos colineares. Acho que não é um "bug", mas diminui a amostra de possibilidades.
    -Notei que no caso de m ser 1 e n>4 o gerador de pontos poderá posicionar um dos primeiros pontos numa posiçao que impossibilita mais tarde de finalizar a mesma geraçao de pontos
(pois como o gerador nao aceita mais de 3 pontos colineares, nao irá ter posiçao possivel para o seu proximo ponto, então o programa bloqueia). Exemplo:

            xxx
            ..x
            ..x

    Sendo m=1, n=5, '.' os pontos gerados ate ao momento e 'x' as coordenadas livres para gerar pontos, podemos verificar que nenhuma delas é possivel (segundo o meu programa) pois
qualquer uma das opções irá gerar mais de 2 pontos colineares.
    Apenas reparei neste caso, mas acredito que existam certamente outros relacionados a este erro, sendo portanto este o principal bug.

--EM CASO DE BLOQUEIO--
    Ctrl+C, compilar, executar, e recomeçar o teste
