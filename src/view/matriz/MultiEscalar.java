package view.matriz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.math.Fraction;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import utils.Matrizes;

public class MultiEscalar extends JPanel{

    private ArrayList<JTextField> tf_elementos; 

    int numLinha, numColuna;

    private JPanel p_matriz, p_botao;
    private JButton bt_novaMatriz, bt_limpar, bt_multiEscalar, bt_preencher;

    public MultiEscalar(){
        setLayout(new BorderLayout());

        tf_elementos = new ArrayList<>();

        bt_novaMatriz = new JButton("Nova Matriz");
        bt_novaMatriz.addActionListener(e -> {novaMatriz();});
        bt_novaMatriz.setMnemonic('n');

        bt_limpar = new JButton("Limpar");
        bt_limpar.addActionListener(e -> {limpar();});
        bt_limpar.setMnemonic('l');

        bt_multiEscalar = new JButton("Multiplicar");
        bt_multiEscalar.addActionListener(e -> {multiplicar();});
        bt_multiEscalar.setMnemonic('m');

        bt_preencher = new JButton("Preencher");
        bt_preencher.addActionListener(e -> {preencher();});
        bt_preencher.setMnemonic('p');

        p_matriz = new JPanel();
        p_matriz.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        p_botao = new JPanel(new GridLayout(1, 4, 5, 5));
        p_botao.setBorder(new EmptyBorder(5, 5, 5, 5));
        p_botao.add(bt_novaMatriz);
        p_botao.add(bt_limpar);
        p_botao.add(bt_multiEscalar);
        p_botao.add(bt_preencher);

        add(p_matriz, BorderLayout.CENTER);
        add(p_botao, BorderLayout.SOUTH);
    }

    private void novaMatriz(){
        String linha = JOptionPane.showInputDialog(this, "Digite qual será o número de linhas da matriz", "Nova matriz", JOptionPane.QUESTION_MESSAGE);
        String coluna = JOptionPane.showInputDialog(this, "Digite qual será o número de colunas da matriz", "Nova matriz", JOptionPane.QUESTION_MESSAGE);

        if(linha == null || coluna == null) return;

        try{
            if(linha.isBlank() || Integer.parseInt(linha) < 0 || coluna.isBlank() || Integer.parseInt(coluna) < 0){
                JOptionPane.showMessageDialog(this, "Selecione valores maior que 0 para determinar o número de linhas e colunas da matriz", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            numLinha = Integer.parseInt(linha);
            numColuna = Integer.parseInt(coluna);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número de linhas e colunas da matriz", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementos.clear();

        p_matriz.removeAll();
        p_matriz.setLayout(new GridLayout(numLinha, numColuna, 5, 5));

        for(int i = 0; i < numLinha; i++){
            for(int j = 0; j < numColuna; j++){
                JTextField tf1 = new JTextField(10);

                tf_elementos.add(tf1);

                JPanel p_elemento1 = new JPanel(new FlowLayout());

                p_elemento1.add(new JLabel(String.format("<html>a<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento1.add(tf1);
                p_matriz.add(p_elemento1);
            }
        }

        p_matriz.revalidate();
        p_matriz.repaint(); 
    }

    private void multiplicar(){
        if(tf_elementos.isEmpty() || !Matrizes.elementosValidos(tf_elementos)){
            JOptionPane.showMessageDialog(this, "Preencha a matriz com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double k = 0;

        try{
            k = Double.parseDouble(JOptionPane.showInputDialog(this, "Digite o valor do escalar que multiplicará a matriz", "Multiplicação por escalar", JOptionPane.QUESTION_MESSAGE));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico válido para multiplicar a matriz", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        double[][] matriz = Matrizes.criarMatriz(tf_elementos, numLinha, numColuna);
        double[][] matrizMultiplicada = Matrizes.multiEscalar(matriz, k);

        Fraction escalar = Fraction.getFraction(k);

        String msg = (escalar.getDenominator() == 1 ? escalar.getNumerator() : String.format("\\frac{%d}{%d}", escalar.getNumerator(), escalar.getDenominator())) + Matrizes.formatoLaTeX(matriz) + "=" + Matrizes.formatoLaTeX(matrizMultiplicada);

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Resultado da multiplicação por escalar", JOptionPane.PLAIN_MESSAGE);
    }

    private void limpar(){
        if(!tf_elementos.isEmpty()){
            for(JTextField e: tf_elementos)
            e.setText("");
        }
    }

    private void preencher(){
        if(!tf_elementos.isEmpty()){
            int i = 1;

            for(JTextField e: tf_elementos)
            e.setText(Integer.toString(i++));
        }
    }
}
