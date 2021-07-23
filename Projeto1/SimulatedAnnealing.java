import java.util.*;
import java.awt.geom.*;
import java.lang.*;

public class SimulatedAnnealing{

    public static int[] randomChoice(int[] ordem, int[][] matriz, int n){
        int numInter=Outros.countIntersecao(ordem, matriz, n);
        int contagem=0;

        //decidir numero random
        int numRandom = (int)((Math.random()*(numInter))+1);
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
}
