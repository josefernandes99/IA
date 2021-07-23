import java.util.*;
import java.awt.geom.*;
import java.lang.*;

public class Outros{

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static boolean checkVisitados(boolean[] visitados, int n){
        for(int i=0; i<n; i++){
            if(visitados[i] == false){
                return false;
            }
        }
        return true;
    }

    public static int distanciaEntre(int xA, int xB, int yA, int yB){
        int cateto1 = Math.abs(xA-xB);
        int cateto2 = Math.abs(yA-yB);
        int hipotenusa = cateto1*cateto1 + cateto2*cateto2;
        return hipotenusa;
    }

    public static boolean checkIntersecao(int[] ordem, int[][] matriz, int n){
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                //System.out.println("Testing " + ordem[i] + "-" + ordem[i+1] + " with " + ordem[j] + "-" + ordem[j+1]);
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true) return true;
            }
        }
        return false;
    }

    public static int countIntersecao(int[] ordem, int[][] matriz, int n){
        int contagem=0;
        for(int i=0; i<n-1; i++){
            int aux=0;
            if(i==0) aux = n-1;
            else aux = n;
            for(int j=i+2; j<aux; j++){
                //System.out.println("Testing " + ordem[i] + "-" + ordem[i+1] + " with " + ordem[j] + "-" + ordem[j+1]);
                boolean temp = Line2D.linesIntersect(matriz[ordem[i]][0], matriz[ordem[i]][1],matriz[ordem[i+1]][0], matriz[ordem[i+1]][1],matriz[ordem[j]][0], matriz[ordem[j]][1],matriz[ordem[j+1]][0], matriz[ordem[j+1]][1]);
                if(temp == true) contagem++;
            }
        }
        return contagem;
    }

    public static void lerOrdem(int[] ordem, int n){
        for(int i=0; i<n; i++){
            System.out.print(ordem[i] + ",");
        }
        System.out.println(ordem[n]);
    }
}
