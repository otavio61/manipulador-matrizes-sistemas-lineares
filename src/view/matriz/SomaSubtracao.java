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

public class SomaSubtracao extends JPanel{

    private ArrayList<JTextField> tf_elementosA, tf_elementosB;

    int numLinha, numColuna;

    private JPanel p_matrizA, p_matrizB, p_botao;
    private JButton bt_novasMatrizes, bt_limpar, bt_somar, bt_subtrair, bt_preencher;

    public SomaSubtracao(){
        setLayout(new BorderLayout());

        tf_elementosA = new ArrayList<>();
        tf_elementosB = new ArrayList<>();

        bt_novasMatrizes = new JButton("Novas matrizes");
        bt_novasMatrizes.setMnemonic('n');
        bt_novasMatrizes.addActionListener(e -> {novasMatrizes();});

        bt_limpar = new JButton("Limpar");
        bt_limpar.setMnemonic('l');
        bt_limpar.addActionListener(e -> {limpar();});

        bt_somar = new JButton("Somar");
        bt_somar.setMnemonic('s');
        bt_somar.addActionListener(e -> {soma();});

        bt_subtrair = new JButton("Subtrair");
        bt_subtrair.setMnemonic('u');
        bt_subtrair.addActionListener(e -> {subtracao();});

        bt_preencher = new JButton("Preencher");
        bt_preencher.addActionListener(e -> {preencher();});
        bt_preencher.setMnemonic('p');

        p_botao = new JPanel(new GridLayout(1, 4, 5, 5));
        p_botao.setBorder(new EmptyBorder(5, 5, 5, 5));
        p_botao.add(bt_novasMatrizes);
        p_botao.add(bt_limpar);
        p_botao.add(bt_somar);
        p_botao.add(bt_subtrair);
        p_botao.add(bt_preencher);

        p_matrizA = new JPanel();
        p_matrizA.setBorder(new EmptyBorder(5, 5, 5, 5));

        p_matrizB = new JPanel();
        p_matrizB.setBorder(new EmptyBorder(5, 5, 5, 5));

        add(p_matrizA, BorderLayout.WEST);
        add(p_matrizB, BorderLayout.EAST);
        add(p_botao, BorderLayout.SOUTH);
    }

    private void novasMatrizes(){
        String linha = JOptionPane.showInputDialog(this, "Digite qual será o número de linhas das matrizes", "Novas matrizes", JOptionPane.QUESTION_MESSAGE);
        String coluna = JOptionPane.showInputDialog(this, "Digite qual será o número de colunas das matrizes", "Novas matrizes", JOptionPane.QUESTION_MESSAGE);

        if(linha == null || coluna == null) return;

        try{
            if(linha.isBlank() || Integer.parseInt(linha) < 0 || coluna.isBlank() || Integer.parseInt(coluna) < 0){
                JOptionPane.showMessageDialog(this, "Selecione um valor maior que 0 para determinar a ordem das matrizes", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            numLinha = Integer.parseInt(linha);
            numColuna = Integer.parseInt(coluna);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número da ordem das matrizes", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementosA.clear();
        tf_elementosB.clear();

        p_matrizA.removeAll();
        p_matrizA.setLayout(new GridLayout(numLinha, numColuna, 5, 5));

        p_matrizB.removeAll();
        p_matrizB.setLayout(new GridLayout(numLinha, numColuna, 5, 5));

        for(int i = 0; i < numLinha; i++){
            for(int j = 0; j < numColuna; j++){
                JTextField tf1 = new JTextField(10);
                JTextField tf2 = new JTextField(10);

                tf_elementosA.add(tf1);
                tf_elementosB.add(tf2);

                JPanel p_elemento1 = new JPanel(new FlowLayout());
                JPanel p_elemento2 = new JPanel(new FlowLayout());

                p_elemento1.add(new JLabel(String.format("<html>a<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento1.add(tf1);
                p_matrizA.add(p_elemento1);

                p_elemento2.add(new JLabel(String.format("<html>b<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento2.add(tf2);
                p_matrizB.add(p_elemento2);
            }
        }
        p_matrizA.revalidate();
        p_matrizA.repaint();

        p_matrizB.revalidate();
        p_matrizB.repaint();
    }

    private void soma(){
        if(tf_elementosA.isEmpty() || tf_elementosB.isEmpty() || !Matrizes.elementosValidos(tf_elementosA) || !Matrizes.elementosValidos(tf_elementosB)){
            JOptionPane.showMessageDialog(this, "Preencha as matrizes com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[][] matrizA = Matrizes.criarMatriz(tf_elementosA, numLinha, numColuna);
        double[][] matrizB = Matrizes.criarMatriz(tf_elementosB, numLinha, numColuna);
        double[][] soma = Matrizes.soma(matrizA, matrizB);

        String msg = Matrizes.formatoLaTeX(matrizA) + "+" + Matrizes.formatoLaTeX(matrizB) + "=" + Matrizes.formatoLaTeX(soma);

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Resultado da soma", JOptionPane.PLAIN_MESSAGE);
    }

    private void subtracao(){
        if(tf_elementosA.isEmpty() || tf_elementosB.isEmpty() || !Matrizes.elementosValidos(tf_elementosA) || !Matrizes.elementosValidos(tf_elementosB)){
            JOptionPane.showMessageDialog(this, "Preencha as matrizes com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[][] matrizA = Matrizes.criarMatriz(tf_elementosA, numLinha, numColuna);
        double[][] matrizB = Matrizes.criarMatriz(tf_elementosB, numLinha, numColuna);
        double[][] subtracao = Matrizes.subtracao(matrizA, matrizB);

        String msg = Matrizes.formatoLaTeX(matrizA) + "-" + Matrizes.formatoLaTeX(matrizB) + "=" + Matrizes.formatoLaTeX(subtracao);

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Resultado da subtração", JOptionPane.PLAIN_MESSAGE);
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
