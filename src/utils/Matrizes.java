package utils;

import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.math4.legacy.linear.MatrixUtils;
import org.apache.commons.math4.legacy.linear.RealMatrix;

/**
 * Classe que possui métodos para realizar operações envolvendo matrizes.
 */
public class Matrizes {
    
    /**
     * Cria uma matriz quadrada de ordem <i>n</i> apartir dos valores
     * presentes em cada {@code JTextField} presentes no {@code ArrayList}.
     * 
     * @param tf_elementos {@code ArrayList} que contém diversos {@code JTextField}
     * 
     * @return Uma matriz quadrada do tipo {@code double}
     */
    public static double[][] criarMatriz(ArrayList<JTextField> tf_elementos){
        int ordem = (int) Math.sqrt(tf_elementos.size()), k = 0;

        double[][] matriz = new double[ordem][ordem];

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz.length; j++){
                matriz[i][j] = Double.parseDouble(tf_elementos.get(k++).getText());
            }
        }

        return matriz;
    }

    /**
     * Cria uma matriz de ordem <i>m</i> por <i>n</i> apartir dos valores 
     * presentes em cada {@code JTextField} presentes no {@code ArrayList}.
     * 
     * @param tf_elementos {@code ArrayList} que contém diversos {@code JTextField}
     * @param numLinha Número de linhas da matriz
     * @param numColuna Número de colunas da matriz
     * 
     * @return Uma matriz de ordem <i>m</i> por <i>n</i> do tipo {@code double}
     */
    public static double[][] criarMatriz(ArrayList<JTextField> tf_elementos, int numLinha, int numColuna){
        double[][] matriz = new double[numLinha][numColuna];

        int k = 0;

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){
                matriz[i][j] = Double.parseDouble(tf_elementos.get(k++).getText());
            }
        }

        return matriz;
    }

    /**
     * Calcula a determinante de uma matriz. O cálculo pode variar a depender da ordem da matriz. Para matrizes de ordem 1, 
     * o determinante é o próprio valor unitário. Para matrizes de ordem 2, é realizado a subtração do produto da diagonal principal
     * pelo produto da diagonal secundária. Para matrizes de ordem 3 é utilizado a regra de Sarrus para descubrir a determinante.
     * Para matrizes de ordem maior ou igual a 4, é utilizado o teorema de Laplace para descobrir a determinante.
     * 
     * @param matriz A matriz que terá sua determinante calculada
     * 
     * @return A determinante da matriz
     */
    public static double determinante(double[][] matriz){
        if(matriz.length == 1) return matriz[0][0];

        if(matriz.length == 2) return (matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]);

        if(matriz.length == 3){
            int k = 0, l = matriz.length - 1;
            double diagonalPrincipal = 0, diagonalSecundaria = 0, diagonal1 = 1, diagonal2 = 1;

            for(int i = 0; i < matriz.length; i++){
                for(int j = 0; j < matriz.length; j++) {
                    diagonal1 *= matriz[j][k++];                          
                    if(k == matriz.length) k = 0;

                    diagonal2 *= matriz[j][l--]; 
                    if(l == -1) l = matriz.length - 1;
                }
                
                diagonalPrincipal += diagonal1;
                diagonalSecundaria += diagonal2;

                diagonal1 = 1;
                diagonal2 = 1;
                
                k++;
                l--; 
            }

            return diagonalPrincipal - diagonalSecundaria;

        }else{
            double[][] submatriz = new double[matriz.length - 1][matriz.length - 1];

            int c = 0; 
    
            double determinante = 0;
    
            for (int k = 0; k < matriz.length; k++) {
                
                int n = 0;

                for (int i = 1; i < matriz.length; i++) {
                    
                    int p = 0;

                    for (int j = 0; j < matriz.length; j++) {
                        if(j != c){              
                            submatriz[n][p] = matriz[i][j];
                            p++;
                        }
                    }
                    
                    n++;
                }

                // det A = \Sum_{j = 1}^{n} a_{ij}\Delta_{ij}
                determinante += matriz[0][c] * Math.pow(-1, c) * determinante(submatriz);

                c++;
            }

            return determinante;
        }
    }

    /**
     * Realiza a soma entre uma matriz <i>A</i> e <i>B</i> que tem como resultado
     * uma terceira matriz <i>C</i> cuja fórmula é: <i>C = [aij + bij]m x n</i>.
     * 
     * @param matrizA A primeira matriz
     * @param matrizB A segunda matriz
     * 
     * @return Uma terceira matriz cujo os elementos são
     * a soma dos elementos correspondentes de <i>A</i> e <i>B</i>
     */
    public static double[][] soma(double[][] matrizA, double[][] matrizB){
        double[][] matrizC = new double[matrizA.length][matrizA[0].length];

        for(int i = 0; i < matrizA.length; i++){
            for(int j = 0; j < matrizA[0].length; j++){
                matrizC[i][j] = matrizA[i][j] + matrizB[i][j];
            } 
        }

        return matrizC;
    }

    /**
     * Realiza a subtração entre duas matrizes <i>A</i> e <i>B</i> que tem como resultado
     * uma terceira matriz <i>C</i> cuja fórmula é: <i>C = [aij - bij]m x n</i>.
     * 
     * @param matrizA A primeira matriz
     * @param matrizB A segunda matriz
     * 
     * @return Uma terceira matriz cujo os elementos são
     * a subtração dos elementos correspondentes de <i>A</i> e <i>B</i>
     */
    public static double[][] subtracao(double[][] matrizA, double[][] matrizB){
        double[][] matrizC = new double[matrizA.length][matrizA[0].length];

        for(int i = 0; i < matrizA.length; i++){
            for(int j = 0; j < matrizA[0].length; j++){
                matrizC[i][j] = matrizA[i][j] - matrizB[i][j];
            } 
        }

        return matrizC;
    }

    /**
     * Realiza a multiplicação entre duas matrizes <i>A</i> e <i>B</i> que tem
     * como resultado uma terceira matriz <i>C</i> cuja ordem é igual ao número
     * de linhas de <i>A</i> e o número de colunas de <i>B</i>. Para que a 
     * multiplicação seja possível, é necessário que o número de colunas da
     * primeira matriz seja igual ao número de colunas da segunda matriz.
     * 
     * @param matrizA A primeira matriz
     * @param matrizB A segunda matriz
     * 
     * @return uma terceira matriz <i>C</i> cuja ordem é igual ao número
     * de linhas de <i>A</i> e o número de colunas de <i>B</i>
     */
    public static double[][] multiMatrizes(double[][] matrizA, double[][] matrizB){
        double[][] matrizC = new double[matrizA.length][matrizB[0].length];

        for (int i = 0; i < matrizC.length; i++) {
            for (int j = 0; j < matrizC[0].length; j++) {
                matrizC[i][j] = 0;
            }
        }

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizB[0].length; j++) {
                for(int k = 0; k < matrizA[0].length; k++){
                    matrizC[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        return matrizC;
    }

    /**
     * Realiza a multiplicação por um escalar entre uma matriz <i>A</i> e um valor
     * numério real qualquer <i>k</i> cujo resultado é uma segunda matriz
     * em que: <i>k . A = [k . aij]m x n</i>.
     * 
     * @param matrizA A matriz a ser multiplicada
     * @param k Um escalar (número real) que irá multiplicar a matriz
     * 
     * @return Uma matriz cujos elementos são os valores de <i>A</i>
     * multiplicados por <i>k</i> 
     */
    public static double[][] multiEscalar(double[][] matrizA, double k){
        double[][] matrizB = new double[matrizA.length][matrizA[0].length];

        for(int i = 0; i < matrizA.length; i++){
            for(int j = 0; j < matrizA[0].length; j++){
                matrizB[i][j] = matrizA[i][j] * k;
            }
        }

        return matrizB;
    }

    /**
     * Realiza uma transposição de uma matriz <i>A</i> tendo como resultado
     * é uma matriz <i>A'</i> cuja fórmula é: <i>A' = [bij]n x m</i>
     * em que <i>bij = aji</i>.
     * 
     * @param matrizA A matriz a ser transposta
     * 
     * @return A transposta da matriz inicial
     */
    public static double[][] transposicao(double[][] matrizA){
        double[][] matrizB = new double[matrizA[0].length][matrizA.length];

        for(int i = 0; i < matrizB.length; i++){
            for(int j = 0; j < matrizB[0].length; j++){
                matrizB[i][j] = matrizA[j][i];
            }
        }

        return matrizB;
    }

    /**
     * Retorna a inversa de uma dada matriz <i>A</i>. Uma matriz é inversa de outra quando satizfaz 
     * a condição: <i>A^-1 . A = I</i>, onde <i>I</i> é uma matriz identidade de mesma ordem das outras duas matrizes.
     * Para a inversão da matriz, é utilizado o método de eliminação de Gauss-Jordan.
     * 
     * @param matrizA A matriz que será invertida
     * 
     * @return A inversa da matriz dada
     * 
     * @see MatrixUtils#createRealMatrix(double[][])
     * @see MatrixUtils#createRealIdentityMatrix(double[][])
     * @see RealMatrix#getData()
     */
    public static double[][] inversa(double[][] matrizA){
        double[][] copia = MatrixUtils.createRealMatrix(matrizA).getData();
        double[][] inversa = MatrixUtils.createRealIdentityMatrix(matrizA.length).getData();

        for(int j = 0; j < copia.length - 1; j++){ 
            int pivo = j;

            if(copia[j][j] == 0){
               double maiorValor = Math.abs(copia[j][j]);
                
                for(int i = j + 1; i < matrizA.length; i++){
                    if(maiorValor < Math.abs(copia[i][j])){
                        maiorValor = Math.abs(copia[i][j]);
                        pivo = i;
                    }
                }

            }

            if(pivo != j){
                double[] linhaOriginal = copia[j];
                copia[j] = copia[pivo];
                copia[pivo] = linhaOriginal;

                linhaOriginal = inversa[j];
                inversa[j] = inversa[pivo];
                inversa[pivo] = linhaOriginal;
            }

            if(copia[j][j] != 0){
                double d = copia[j][j]; 

                for(int i = 0; i < copia.length; i++) { 
                    copia[j][i] = copia[j][i] / d;
                    inversa[j][i] = inversa[j][i] / d;
                }
                
                for(int i = j + 1; i < copia.length; i++){ 
                    double multiplicador = copia[i][j] / copia[j][j];

                    for(int k = 0; k < copia.length; k++){ 
                        copia[i][k] = copia[i][k] - (multiplicador * copia[j][k]);
                        inversa[i][k] = inversa[i][k] - (multiplicador * inversa[j][k]);
                    }
                }
            }
        }

        for(int j = copia.length - 1; j > 0; j--){
            double d = copia[j][j];

            for(int i = 0; i < inversa.length; i++) {
                copia[j][i] = copia[j][i] / d;
                inversa[j][i] = inversa[j][i] / d;
            }

            for(int i = j - 1; i >= 0; i--) {
                double multiplicador = copia[i][j] / copia[j][j];

                for(int k = copia.length - 1; k >= 0 ; k--) {
                    copia[i][k] = copia[i][k] - (multiplicador * copia[j][k]);
                    inversa[i][k] = inversa[i][k] - (multiplicador * inversa[j][k]);
                }
            }
        }
        
        return inversa;
    }

    /**
     * Retorna a matriz com a coluna selecionada removida.
     * 
     * @param matriz A matriz original
     * @param coluna A coluna a ser eliminada
     * 
     * @return A matriz com a coluna removida
     */
    public static double[][] removerColuna(double[][] matriz, int coluna){
        double[][] novaMatriz = new double[matriz.length][matriz[0].length - 1];

        for(int i = 0; i < novaMatriz.length; i++) {
            for(int j = 0; j < novaMatriz[0].length; j++) {
                if(j == coluna - 1) continue;

                novaMatriz[i][j] = matriz[i][j];
            }
        }

        return novaMatriz;
    }

    /**
     * Retorna a matriz com a coluna selecionada adicionada no final.
     * 
     * @param matriz A matriz original
     * @param coluna A coluna a ser adicionada
     * 
     * @return A matriz com a coluna removida
     */
    public static double[][] adicionarColuna(double[][] matriz, double[][] coluna){
        double[][] novaMatriz = new double[matriz.length][matriz[0].length + 1];

        for(int i = 0; i < novaMatriz.length; i++) {
            for(int j = 0; j < matriz[0].length; j++) {
                novaMatriz[i][j] = matriz[i][j];
            }
            
            novaMatriz[i][novaMatriz[0].length - 1] = coluna[i][0];
        }

        return novaMatriz;
    }

    /**
     * Retorna uma submatriz quadrada a partir da matriz quadrada original
     * 
     * @param matriz A matriz original
     * @param ordem A ordem da submatriz
     * 
     * @return A submatriz quadrada
     */
    private static double[][] submatriz(double[][] matriz, int ordem){
        double[][] novaMatriz = new double[ordem][ordem];

        for(int i = 0; i < ordem; i++){
            for(int j = 0; j < ordem; j++){
                novaMatriz[i][j] = matriz[i][j];
            }            
        }

        return novaMatriz;
    }
    
    /**
     * Retorna {@code true} se a matriz quadrada for simétrica.
     * 
     * @param matriz A matriz quadrada
     * 
     * @return {@code true} se a matriz quadrada for simétrica
     */
    public static boolean matrizSimetrica(double[][] matriz){
        for(int i = 0; i < matriz.length; i++){
            for (int j = 0; j < matriz.length; j++){
                if(matriz[i][j] != matriz[j][j]) return false;
            }
        }

        return true;
    }
    
    /**
     * Retorna {@code true} se a matriz cumpre o <b><a href="https://en.wikipedia.org/wiki/Sylvester%27s_criterion">Critério de Sylvester</a></b>, que afirma que se uma 
     * dada matriz quadrada contém todos os menores complementares líderes positivos (D[n, n] > 0) então esta matriz é definida positiva.
     * 
     * @param matriz A matriz quadrada a ser avaliada
     * 
     * @return {@code true} se a matriz for definida positiva
     */
    public static boolean criterioSylvester(double[][] matriz){
        int c = matriz.length;

        for(int k = 0; k < matriz.length; k++) {
            for(int i = 0; i < c; i++){
                for(int j = 0; j < c; j++){
                    if(Matrizes.determinante(Matrizes.submatriz(matriz, c)) < 0) return false;
                }
            }

            c--;
        }
        
        return true;
    }

    /**
     * Verifica se os valores presentes em cada {@code JTextField} são válidos para criar uma matriz.
     * 
     * @param elementos {@code ArrayList} que contém diversos {@code JTextField}
     * 
     * @return Se os valores são válidos ou não para criar uma matriz
     */
    public static boolean elementosValidos(ArrayList<JTextField> elementos){
        try{
            for(JTextField e: elementos)
            Double.parseDouble(e.getText());

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retorna uma {@code String} com a estrutura da matriz no formato {@code LaTeX}
     * 
     * @param matriz A matriz a ser convertida no formato {@code LaTeX}
     * 
     * @return Uma {@code String} contendo a matriz no formato {@code LaTeX}
     */
    public static String formatoLaTeX(double[][] matriz) {
        String formula = "\\begin{bmatrix}";

        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){

                Fraction fracao = Fraction.getFraction(matriz[i][j]);

                if(fracao.getDenominator() == 1) {
                    formula += NumberFormat.getInstance().format(fracao.getNumerator());
                }else{
                    if(fracao.getNumerator() < 0){
                        formula += "-"; 
                        fracao = fracao.multiplyBy(Fraction.getFraction(-1));
                    } 

                    formula += String.format("\\frac{%d}{%d}", fracao.getNumerator(), fracao.getDenominator());
                } 

                if(j < matriz[0].length - 1) formula += "&";
            }

            if(i < matriz.length - 1) formula += "\\\\";
        }

        formula += "\\end{bmatrix}";

        return formula;
    }
}