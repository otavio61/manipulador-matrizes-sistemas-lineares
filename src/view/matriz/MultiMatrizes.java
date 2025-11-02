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

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import utils.Matrizes;

public class MultiMatrizes extends JPanel{

    private ArrayList<JTextField> tf_elementosA, tf_elementosB;

    int numLinhaA, numColunaA, numLinhaB, numColunaB;

    private JPanel p_matrizA, p_matrizB, p_botao;
    private JButton bt_novasMatrizA, bt_novasMatrizB, bt_limpar, bt_multiplicar, bt_preencher;

    public MultiMatrizes(){
        setLayout(new BorderLayout());

        tf_elementosA = new ArrayList<>();
        tf_elementosB = new ArrayList<>();

        bt_novasMatrizA = new JButton("Nova matriz A");
        bt_novasMatrizA.setMnemonic('a');
        bt_novasMatrizA.addActionListener(e -> {novaMatrizA();});

        bt_novasMatrizB = new JButton("Nova matriz B");
        bt_novasMatrizB.setMnemonic('b');
        bt_novasMatrizB.addActionListener(e -> {novaMatrizB();});

        bt_limpar = new JButton("Limpar");
        bt_limpar.setMnemonic('l');
        bt_limpar.addActionListener(e -> {limpar();});

        bt_multiplicar = new JButton("Multiplicar");
        bt_multiplicar.setMnemonic('m');
        bt_multiplicar.addActionListener(e -> {multiplicar();});

        bt_preencher = new JButton("Preencher");
        bt_preencher.addActionListener(e -> {preencher();});
        bt_preencher.setMnemonic('p');

        p_botao = new JPanel(new GridLayout(1, 4, 5, 5));
        p_botao.setBorder(new EmptyBorder(5, 5, 5, 5));
        p_botao.add(bt_novasMatrizA);
        p_botao.add(bt_novasMatrizB);
        p_botao.add(bt_limpar);
        p_botao.add(bt_multiplicar);
        p_botao.add(bt_preencher);

        p_matrizA = new JPanel();
        p_matrizA.setBorder(new EmptyBorder(5, 5, 5, 5));

        p_matrizB = new JPanel();
        p_matrizB.setBorder(new EmptyBorder(5, 5, 5, 5));

        add(p_matrizA, BorderLayout.WEST);
        add(p_matrizB, BorderLayout.EAST);
        add(p_botao, BorderLayout.SOUTH);
    }

    private void novaMatrizA(){
        String linha = JOptionPane.showInputDialog(this, "Digite qual será o número de linhas da matriz", "Novas Matrizes", JOptionPane.QUESTION_MESSAGE);
        String coluna = JOptionPane.showInputDialog(this, "Digite qual será o número de colunas da matriz", "Novas Matrizes", JOptionPane.QUESTION_MESSAGE);
        
        if(linha == null || coluna == null) return;

        try{
            if(linha.isBlank() || Integer.parseInt(linha) < 0 || coluna.isBlank() || Integer.parseInt(coluna) < 0){
                JOptionPane.showMessageDialog(this, "Selecione valores maior que 0 para determinar o número de linhas e colunas da matriz A", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            numLinhaA = Integer.parseInt(linha);
            numColunaA = Integer.parseInt(coluna);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número de linhas e colunas da matriz A", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementosA.clear();

        p_matrizA.removeAll();
        p_matrizA.setLayout(new GridLayout(numLinhaA, numColunaA, 5, 5));

        for(int i = 0; i < numLinhaA; i++){
            for(int j = 0; j < numColunaA; j++){
                JTextField tf1 = new JTextField(10);

                tf_elementosA.add(tf1);

                JPanel p_elemento1 = new JPanel(new FlowLayout());

                p_elemento1.add(new JLabel(String.format("<html>a<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento1.add(tf1);
                p_matrizA.add(p_elemento1);
            }
        }

        p_matrizA.revalidate();
        p_matrizA.repaint();
    }

    private void novaMatrizB(){
        String linha = JOptionPane.showInputDialog(this, "Digite qual será o número de linhas da matriz", "Novas Matrizes", JOptionPane.QUESTION_MESSAGE);
        String coluna = JOptionPane.showInputDialog(this, "Digite qual será o número de colunas da matriz", "Novas Matrizes", JOptionPane.QUESTION_MESSAGE);
        
        if(linha == null || coluna == null) return;

        try{
            if(linha.isBlank() || Integer.parseInt(linha) < 0 || coluna.isBlank() || Integer.parseInt(coluna) < 0){
                JOptionPane.showMessageDialog(this, "Selecione valores maior que 0 para determinar o número de linhas e colunas da matriz B", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            numLinhaB = Integer.parseInt(linha);
            numColunaB = Integer.parseInt(coluna);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número de linhas e colunas da matriz B", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementosB.clear();

        p_matrizB.removeAll();
        p_matrizB.setLayout(new GridLayout(numLinhaB, numColunaB, 5, 5));

        for(int i = 0; i < numLinhaB; i++){
            for(int j = 0; j < numColunaB; j++){
                JTextField tf2 = new JTextField(10);

                tf_elementosB.add(tf2);

                JPanel p_elemento2 = new JPanel(new FlowLayout());

                p_elemento2.add(new JLabel(String.format("<html>b<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento2.add(tf2);
                p_matrizB.add(p_elemento2);
            }
        }

        p_matrizB.revalidate();
        p_matrizB.repaint();
    }

    private void multiplicar(){
        if(tf_elementosA.isEmpty() || tf_elementosB.isEmpty() || !Matrizes.elementosValidos(tf_elementosA) || !Matrizes.elementosValidos(tf_elementosB)){
            JOptionPane.showMessageDialog(this, "Preencha as matrizes com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[][] matrizA = Matrizes.criarMatriz(tf_elementosA, numLinhaA, numColunaA);
        double[][] matrizB = Matrizes.criarMatriz(tf_elementosB, numLinhaB, numColunaB);

        if(matrizA[0].length != matrizB.length){
            JOptionPane.showMessageDialog(this, "O número de colunas da matriz A deve ser igual o número de linhas da matriz B!", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double[][] matrizC = Matrizes.multiMatrizes(matrizA, matrizB);

        String msg = Matrizes.formatoLaTeX(matrizA) + "\\times" + Matrizes.formatoLaTeX(matrizB) + "=" + Matrizes.formatoLaTeX(matrizC);

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, label, "Resultado da multiplicação", JOptionPane.PLAIN_MESSAGE);
    }

    private void limpar(){
        if(!tf_elementosA.isEmpty()){
            for(JTextField e: tf_elementosA)
            e.setText("");    
        }

        if(!tf_elementosB.isEmpty()){
            for(JTextField e: tf_elementosB)
             e.setText("");
        }
    }

    private void preencher(){
        if(!tf_elementosA.isEmpty() && !tf_elementosB.isEmpty()){
            int i = 1;

            for(JTextField e: tf_elementosA)
            e.setText(Integer.toString(i++));

            for(JTextField e: tf_elementosB)
            e.setText(Integer.toString(i++));
        }
    }
}
