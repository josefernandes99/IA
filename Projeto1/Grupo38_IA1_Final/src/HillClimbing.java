import java.util.*;
import java.awt.geom.*;
import java.lang.*;

public class HillClimbing{

    public static int[] randomChoice(int[] ordem, int[][] matriz, int n){
        int numInter=0, contagem=0;
        System.out.println("Possibilidades:");
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                //System.out.println("Testing " + ordem[i] + "-" + ordem[i+1] + " with " + ordem[j] + "-" + ordem[j+1]);
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true){
                    ++numInter;
                    System.out.println(numInter +" => [" + ordem[i] + "-" + ordem[i+1] + "]-[" + ordem[j] + "-" + ordem[j+1] + "]");
                }
            }
        }
        //decidir numero random
        int numRandom = (int)((Math.random()*(numInter)));
        System.out.println("Número escolhido: " + numRandom);
        if(numRandom == 0){
            return null;
        }
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                //System.out.println("Testing " + ordem[i] + "-" + ordem[i+1] + " with " + ordem[j] + "-" + ordem[j+1]);
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true){
                    ++contagem;
                    if(contagem==numRandom){
                        System.out.println("A aplicar o melhoramento");
                        int a = ordem[i+1];
                        ordem[i+1] = ordem[j];
                        ordem[j] = a;
                        return ordem;
                    }
                }
            }
        }
        return ordem;
    }

    public static int[] leastConflicts(int[] ordem, int[][] matriz, int n){
        int contagem = Outros.countIntersecao(ordem, matriz, n);

        System.out.println("Contagem de conflitos: " + contagem);

        //antes das varias modificaçoes
        int[][] solucoes = new int[contagem][n+1];
        for(int i=0; i<contagem; i++){
            for(int j=0; j<n+1; j++){
                solucoes[i][j]=ordem[j];
            }
        }

        //caso intersete, modificar na sua devida versao
        int contagem2=0;
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true){
                    int a=solucoes[contagem2][i+1];
                    solucoes[contagem2][i+1] = solucoes[contagem2][j];
                    solucoes[contagem2][j] = a;
                    contagem2++;
                }
            }
        }

        int[] contagem3 = new int[contagem];
        for(int i=0; i<contagem; i++){
            contagem3[i] = 0;
        }

        for(int i=0; i<contagem; i++){
            for(int j=0; j<n-1; j++){
                int aux=0;
                if(j==0) aux=n-1;
                else aux = n;
                for(int k=j+2; k<aux; k++){
                    boolean temp = Line2D.linesIntersect(matriz[solucoes[i][j]][0], matriz[solucoes[i][j]][1],matriz[solucoes[i][j+1]][0], matriz[solucoes[i][j+1]][1],matriz[solucoes[i][k]][0], matriz[solucoes[i][k]][1],matriz[solucoes[i][k+1]][0], matriz[solucoes[i][k+1]][1]);
                    if(temp == true){
                        contagem3[i]++;
                    }
                }
            }
        }
        int minConflitos = contagem;
        int minSolucao=-1;
        for(int i=0; i<contagem; i++){
            //System.out.println("conflitos em " + i + ": " + contagem3[i]);
            if(contagem3[i]<minConflitos){
                minConflitos = contagem3[i];
                minSolucao=i;
            }
        }
        if(minSolucao==-1){
            System.out.println("Nenhum candidato melhor do que o atual");
            return null;
        }
        else{
            System.out.println("A aplicar o melhoramento");
            for(int i=0; i<n+1; i++){
                ordem[i] = solucoes[minSolucao][i];
            }
        }
        System.out.println("Número minimo de conflitos encontrado foi " + minConflitos);
        return ordem;
    }

    public static int[] firstImprovement(int[] ordem, int[][] matriz, int n){
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                //System.out.println("Testing " + ordem[i] + "-" + ordem[i+1] + " with " + ordem[j] + "-" + ordem[j+1]);
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true){
                    System.out.println("A aplicar o melhoramento");
                    int a = ordem[i+1];
                    ordem[i+1] = ordem[j];
                    ordem[j] = a;
                    return ordem;
                }
            }
        }
        return ordem;
    }

    public static int[] bestImprovementFirst(int[] ordem,int[][] matriz, int n, int perimetro){
        int contagem = Outros.countIntersecao(ordem, matriz, n);
        //contar interseçoes

        System.out.println("Contagem: " + contagem);

        //antes das varias modificaçoes
        int[][] solucoes = new int[contagem][n+1];
        for(int i=0; i<contagem; i++){
            for(int j=0; j<n+1; j++){
                solucoes[i][j]=ordem[j];
            }
        }

        //caso intersete, modificar na sua devida versao
        int contagem2=0;
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true){
                    int a=solucoes[contagem2][i+1];
                    solucoes[contagem2][i+1] = solucoes[contagem2][j];
                    solucoes[contagem2][j] = a;
                    contagem2++;
                }
            }
        }
        int minPerimetro = perimetro;
        int minSolucao=-1;
        for(int i=0; i<contagem; i++){
            int tempPerimetro=0;
            for(int j=0; j<n; j++){
                int aux = Outros.distanciaEntre(matriz[solucoes[i][j]][0],matriz[solucoes[i][j+1]][0],matriz[solucoes[i][j]][1], matriz[solucoes[i][j+1]][1]);
                tempPerimetro += aux;
            }
            if(tempPerimetro<minPerimetro){
                System.out.println("Perímetro Aceite: " + tempPerimetro);
                minPerimetro=tempPerimetro;
                minSolucao=i;
            }
            else{
                System.out.println("Perímetro Rejeitado: " + tempPerimetro);
            }
        }
        if(minSolucao == -1){
            return null;
        }
        else {
            System.out.println("A aplicar o melhoramento");
            System.out.println("Perimetro mínimo encontrado foi " + minPerimetro);
            for(int i=0; i<n+1; i++){
                ordem[i] = solucoes[minSolucao][i];
            }
        }
        return ordem;
    }
}
