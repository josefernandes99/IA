import java.util.*;
import java.awt.geom.*;
import java.lang.*;

public class ProjetoIA {

    public static void main(String[] args){
        Outros.clearScreen();
        int input = 0; //input => escolher entre ficheiros teste ou gerador random
        int m=0, n=0; //m => tamanho máximo de cada coordenada, n => número de pontos
        Scanner in = new Scanner(System.in);

        while(true){
            System.out.print("Insira o m:\n   => ");
            m = in.nextInt();
            if(m<1) //m>=1 para poder fazer um gráfico de coordenadas inteiras
                System.out.println("Erro");
            else
                break;
        }

        while(true){
            System.out.print("Insira o n:\n   => ");
            n = in.nextInt();
            if(n<3) //número de pontos tem de ser maior que 2 para fazer um poligono
                System.out.println("Erro");
            else
                break;
        }

        System.out.println();
        int[][] matriz = new int[n][2]; //guarda o x e y de cada ponto
        int[] ordem = new int[n+1]; //guarda a ordem dos pontos

        for(int i=0; i<n; i++){
            int randomX = 0;
            int randomY = 0;

            while(true){
                randomX = (int)(Math.random()*(2*m+1))-m;
                randomY = (int)(Math.random()*(2*m+1))-m;
                boolean unico=true;
                boolean naocolinear=true;

                for(int a=0; a<i; a++){ //verifica se este random já foi gerado anteriormente
                    if(matriz[a][0] == randomX && matriz[a][1] == randomY){
                        unico=false;
                        break;
                    }
                }
                if(i>2){ //verifica colineariedade entre os pontos guardados e este random (caso tenha pontos suficientes)
                    for(int a=0; a<i; a++){
                        for(int b=a+1; b<i; b++){
                            //verifica se o 3º ponto nao é colinear com 2 outros pontos já guardados
                            int area = randomX * (matriz[a][1] - matriz[b][1]) + matriz[a][0] * (matriz[b][1] - randomY) + matriz[b][0] * (randomY - matriz[a][1]);
                            if(area == 0){
                                naocolinear=false;
                                break;
                            }
                        }
                        if(naocolinear == false) break;
                    }
                }
                if(unico==true && naocolinear==true) break; //caso seja unico e nao-colinear, introduz as coordenadas de random na matriz
            }

            matriz[i][0] = randomX;
            matriz[i][1] = randomY;
        }

        for(int i=0; i<n; i++)
            System.out.println("Ponto " + i + " => [" + matriz[i][0] + "," + matriz[i][1] + "]");

        while(true){
            System.out.print("\nSelecione o tipo de solução desejado:\n1) Permutação Random dos Pontos\n2) Nearest-Neighbour First\n   => ");
            int opcao = in.nextInt();
            System.out.println();

            //permutaçao random
            if(opcao == 1){
                boolean[] visitados = new boolean[n];

                for(int i=0; i<n; i++){
                    visitados[i] = false;
                }

                for(int i=0; i<n; i++){
                    while(true){
                        int randomNum = (int) ((Math.random() * n));

                        if(visitados[randomNum] == false){ //caso o randomNum seja um ponto nao visitado, adiciona-o na ordem
                            ordem[i]=randomNum;
                            visitados[randomNum] = true;
                            break;
                        }
                    }
                }

                ordem[n]=ordem[0];
                System.out.print("Ordem: ");
                Outros.lerOrdem(ordem, n);
                break;
            }

            //nearest
            else if(opcao == 2){
                boolean[] visitados = new boolean[n];
                int pontoAtual=0, aux=0;

                for (int i=0; i<n; i++){
                    visitados[i] = false;
                }

                pontoAtual = (int)(Math.random()*(n-1)); //escolhe de forma random um ponto da matriz para ser o inicial
                ordem[aux] = pontoAtual;
                visitados[pontoAtual]=true;
                aux++;

                while(true){
                    int distanciaMin=Integer.MAX_VALUE;
                    int pontoMin = 0;

                    for (int i=0; i<n; i++){
                        if(visitados[i]==false){
                            int distanciaTemp = Outros.distanciaEntre(matriz[pontoAtual][0], matriz[i][0], matriz[pontoAtual][1], matriz[i][1]);

                            if (distanciaTemp < distanciaMin){
                                distanciaMin = distanciaTemp;
                                pontoMin = i;
                            }
                        }
                    }

                    pontoAtual = pontoMin;
                    ordem[aux++]=pontoAtual;
                    visitados[pontoAtual]=true;

                    if((Outros.checkVisitados(visitados, n))==true){  //verifica se todos os pontos foram visitados
                        break;
                    }
                }

                ordem[n]=ordem[0];
                System.out.print("Ordem: ");
                Outros.lerOrdem(ordem,n);
                break;
            }

            else
                System.out.println("Erro no input, tente novamente");

        }

        int resultado = Outros.countIntersecao(ordem,matriz,n);
        int opcao1=0;

        if (resultado == 1){
            System.out.print("\nExiste 1 interseção\nEscolha o melhoramento a aplicar:\n1) Hill-Climbing\n2) Simulated Annealing\n   => ");
            opcao1 = in.nextInt();
        }

        else if (resultado > 1){
            System.out.print("\nExistem " + resultado + " interseções\nEscolha o melhoramento a aplicar:\n1) Hill-Climbing\n2) Simulated Annealing\n   => ");
            opcao1 = in.nextInt();
        }
        if(opcao1 == 1){ //hill-climbing
            while(true){
                System.out.print("\nEscolha o objetivo que pretende aplicar ao hill-climbing:\n1) Escolher sempre o candidato que reduz mais o perímetro do polígono\n2) Escolher sempre o primeiro candidato da vizinhança\n3) Escolher sempre o candidato que produza menos conflitos de arestas\n4) Escolher sempre um candidato random\n   => ");
                int opcao2=in.nextInt();
                System.out.print("\nOrdem Inicial: ");
                Outros.lerOrdem(ordem,n);
                System.out.println();
                if(opcao2 == 1){
                    while(Outros.checkIntersecao(ordem, matriz, n) == true){
                        int[] temp = ordem;
                        int perimetroInicial = 0;
                        for(int i=0; i<n; i++){
                            perimetroInicial += Outros.distanciaEntre(matriz[ordem[i]][0], matriz[ordem[i+1]][0], matriz[ordem[i]][1], matriz[ordem[i+1]][1]);
                        }
                        System.out.println("Perimetro Do Inicio da Iteraçao: " + perimetroInicial);
                        temp = HillClimbing.bestImprovementFirst(ordem, matriz, n, perimetroInicial);
                        if(temp == null){
                            System.out.println("Não há melhor perímetro, solução perfeita não foi alcançada");
                        break;
                        }
                        System.out.print("novaOrdem: ");
                        ordem = temp;
                        Outros.lerOrdem(ordem,n);
                    System.out.println();
                    }
                    break;
                }
                else if(opcao2 == 2){
                    while(Outros.checkIntersecao(ordem, matriz, n) == true){
                        ordem = HillClimbing.firstImprovement(ordem, matriz, n);
                        System.out.print("novaOrdem: ");
                        Outros.lerOrdem(ordem,n);
                        System.out.println();
                    }
                    break;
                }
                else if(opcao2 == 3){
                    //menos conflitos
                    while(Outros.checkIntersecao(ordem, matriz, n) == true){
                        int[] temp = ordem;
                        temp = HillClimbing.leastConflicts(ordem, matriz, n);
                        if(temp == null){
                            System.out.println("Não há candidatos com menos conflitos, solução perfeita não foi alcançada\n");
                            break;
                        }
                        ordem = temp;
                        System.out.print("novaOrdem: ");
                        Outros.lerOrdem(ordem,n);
                        System.out.println();
                    }
                    break;
                }
                else if(opcao2 == 4){
                    while(Outros.checkIntersecao(ordem, matriz, n) == true){
                        ordem = HillClimbing.randomChoice(ordem,matriz,n);
                        System.out.print("novaOrdem: ");
                        Outros.lerOrdem(ordem,n);
                        System.out.println();
                    }
                    break;
                }
            }
            System.out.print("Melhorias aplicadas\nResultado Final: ");
            Outros.lerOrdem(ordem,n);
            System.out.println();
        }

        else if(opcao1 == 2){    //simulated annealing
            int maxIter = Integer.MAX_VALUE;
            double T = (double) (Outros.countIntersecao(ordem, matriz, n));
            double r = 0.95;
            int lastIter = 0;
            System.out.println();
            for(int t=0; t<=maxIter; t++){
                T = T * r;
                if (T==0 || Outros.countIntersecao(ordem,matriz,n) == 0){
                System.out.println("\nTerminado");
                    lastIter = t;
                    break;
                }
                System.out.println("T is " + T);
                int[] novaOrdem = new int[n+1];
                novaOrdem = SimulatedAnnealing.randomChoice(ordem,matriz,n);
                System.out.print("novaOrdem: ");
                Outros.lerOrdem(novaOrdem,n);
                int deltaE = (Outros.countIntersecao(novaOrdem,matriz,n)*(-1)) - (Outros.countIntersecao(ordem,matriz,n));
                if(deltaE<0){
                    ordem = novaOrdem;
                    System.out.println("DeltaE < 0");
                    System.out.println("novaOrdem é aceite");
                }
                else{
                    System.out.println("DeltaE >= 0");
                    if(((Outros.countIntersecao(ordem,matriz,n)-Outros.countIntersecao(novaOrdem,matriz,n))/T)>Math.random()){
                        ordem = novaOrdem;
                        System.out.println("Maior que random, logo novaOrdem é aceite");
                    }
                    else{
                        System.out.println("Menor que random, novaOrdem recusada");
                    }
                }
                System.out.println();
            }
            System.out.println("Nº de Iteraçoes: " + lastIter);
            System.out.println("Melhorias aplicadas");
            System.out.print("Resultado Final: ");
            Outros.lerOrdem(ordem,n);
            System.out.println();
        }

        if (resultado == 0)
            System.out.println("\nNão há interseções");
    }
}
