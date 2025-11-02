package utils;

import org.apache.commons.math4.legacy.linear.MatrixUtils;

/**
 * Classe que possui métodos para realizar operações envolvendo sistemas lineares.
 */
public class SistemasLineares {
    
    /**
     * Retorna a matriz expandida superior de um sistema linear, após o escalonamento (eliminação de Gauss) com o pivoteamento parcial.
     * 
     * @param sistema A matriz expandida que contém a matriz dos coeficientes e a matriz dos termos independentes {@code (A|b)}
     * 
     * @return A matriz expandida escalonada
     */
    public static double[][] eliminacaoGaussPivoteado(double[][] sistema){

        for(int i = 0; i < sistema.length - 1; i++) {
            double maiorValor = Math.abs(sistema[i][i]);
            int pivo = i;
            
            for(int j = i + 1; j < sistema.length; j++) {
                if(maiorValor < Math.abs(sistema[j][i])){
                    maiorValor = Math.abs(sistema[j][i]);
                    pivo = j;
                }
            }

            if(pivo != i){
                double[] linhaOriginal = sistema[i];
                sistema[i] = sistema[pivo];
                sistema[pivo] = linhaOriginal;
            }

            if(sistema[i][i] != 0){
                for(int j = i + 1; j < sistema.length; j++) {
                    double m = sistema[j][i] / sistema[i][i];
                    
                    for(int k = i; k < sistema[0].length; k++) {
                        sistema[j][k] = sistema[j][k] - (m * sistema[i][k]);
                    }
                }
            }
        }
        return sistema;
    }
    
    /**
     * Retorna uma matriz expandida com os valores da matriz <i>A</i> dos coeficientes simétrica definida positiva
     * fatorados na forma <i>A=LL^T</i>, onde L é uma matriz triangular inferior
     * 
     * @param sistema O sistema linear simétrico
     * 
     * @return A matriz expandida do sistema na forma {@code (L|b)}
     */
    public static double[][] fatoracaoCholesky(double[][] sistema){
        double[][] matrizL = MatrixUtils.createRealMatrix(sistema).getData();
        double d, soma;

        for(int j = 0; j < matrizL.length; j++){
            soma = 0;

            for(int k = 0; k < j; k++) {
                soma += Math.pow(matrizL[j][k], 2);
            }

            d = matrizL[j][j] - soma;

            matrizL[j][j] = Math.sqrt(d);

            for(int i = j + 1; i < matrizL.length; i++){
                soma = 0;

                for(int k = 0; k < j; k++){
                    soma += matrizL[i][k] * matrizL[j][k];
                }

                matrizL[i][j] = (matrizL[i][j] - soma) / matrizL[j][j];
            }

            for(int i = j + 1; i < matrizL.length; i++){
                matrizL[j][i] = 0;
            }
        }

        return matrizL;
    }

    /**
     * Retorna a matriz coluna da incógnicas, com os respectivos valores através das
     * substituições sucessivas nos sistemas lineares triangulares inferiores.
     *  
     * @param sistema A matriz expandida que contém a matriz dos coeficientes e a matriz dos termos independentes {@code (A|b)}
     * 
     * @return a matriz coluna da incógnicas, com os respectivos valores
     */
    public static double[][] substituicaoSucessiva(double[][] sistema){
        double[][] incognitas = new double[sistema.length][1];

        incognitas[0][0] = sistema[0][sistema[0].length - 1] / sistema[0][0];

        for(int i = 1; i < sistema.length; i++){
            double somatorio = 0;

            for (int j = 0; j <= i - 1; j++) {
                somatorio += (sistema[i][j] * incognitas[j][0]);
            }

            incognitas[i][0] = (sistema[i][sistema[0].length - 1] - somatorio) / sistema[i][i];
        }

        return incognitas;
    }

    /**
     * Retorna a matriz coluna da incógnicas, com os respectivos valores através das
     * substituições retroativas nos sistemas lineares triangulares superiores.
     *  
     * @param sistema A matriz expandida que contém a matriz dos coeficientes e a matriz dos termos independentes {@code (A|b)}
     * 
     * @return a matriz coluna da incógnicas, com os respectivos valores
     */
    public static double[][] substituicaoRetroativa(double[][] sistema){
        double[][] incognitas = new double[sistema.length][1];

        incognitas[sistema.length - 1][0] = sistema[sistema.length - 1][sistema[0].length - 1] / sistema[sistema.length - 1][sistema[0].length - 2];

        for(int i = sistema.length - 2; i >= 0; i--){
            double somatorio = 0;

            for (int j = i + 1; j < incognitas.length; j++)
            somatorio += (sistema[i][j] * incognitas[j][0]);

            incognitas[i][0] = (sistema[i][sistema[0].length - 1] - somatorio) / sistema[i][i];
        }
        
        return incognitas;
    }

    /**
     * Retorna o vetor residuo que é obtido entre o valor dos termos independentes do sistema linear
     * e os valores das incógnitas encontradas ao resolver o sistema na forma <i>r = b - Ax</i>.
     * Se o vetor resíduo conter apenas valores 0, então o vetor resposta que satisfaz o sistema é exato.
     * 
     * @param sistema A matriz expandida que contém a matriz dos coeficientes e a matriz dos termos independentes {@code (A|b)}
     * @param incognitas O vetor das incógnitas obtido na resolução do sistema 
     * 
     * @return O vetor resíduo a partir da operação <i>r = b - Ax</i>
     */
    public static double[][] residuo(double[][] sistema, double[][] incognitas){
        double[][] residuo = new double[sistema.length][1];
        double[][] ax = Matrizes.multiMatrizes(Matrizes.removerColuna(sistema, sistema[0].length), incognitas);
        
        for(int i = 0; i < ax.length; i++) {
            residuo[i][0] = sistema[i][sistema[0].length - 1] - ax[i][0];
        }

        return residuo;
    }
}
