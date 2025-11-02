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

public class DeterminanteInversa extends JPanel{
    
    private ArrayList<JTextField> tf_elementos; 

    private JPanel p_matriz, p_botao;
    private JButton bt_novaMatriz, bt_limpar, bt_determinante, bt_inversa, bt_preencher;

    public DeterminanteInversa(){
        setLayout(new BorderLayout());

        tf_elementos = new ArrayList<>();

        bt_novaMatriz = new JButton("Nova Matriz");
        bt_novaMatriz.addActionListener(e -> {novaMatriz();});
        bt_novaMatriz.setMnemonic('n');

        bt_limpar = new JButton("Limpar");
        bt_limpar.addActionListener(e -> {limpar();});
        bt_limpar.setMnemonic('l');

        bt_determinante = new JButton("Determinante");
        bt_determinante.addActionListener(e -> {determinante();});
        bt_determinante.setMnemonic('d');

        bt_inversa = new JButton("Inversa");
        bt_inversa.addActionListener(e -> {inversa();});
        bt_inversa.setMnemonic('i');

        bt_preencher = new JButton("Preencher");
        bt_preencher.addActionListener(e -> {preencher();});
        bt_preencher.setMnemonic('p');

        p_matriz = new JPanel();
        p_matriz.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        p_botao = new JPanel(new GridLayout(1, 4, 5, 5));
        p_botao.setBorder(new EmptyBorder(5, 5, 5, 5));
        p_botao.add(bt_novaMatriz);
        p_botao.add(bt_limpar);
        p_botao.add(bt_determinante);
        p_botao.add(bt_inversa);
        p_botao.add(bt_preencher);

        add(p_matriz, BorderLayout.CENTER);
        add(p_botao, BorderLayout.SOUTH);
    }

    private void novaMatriz(){
        int ordem;
        String num = JOptionPane.showInputDialog(this, "Digite qual será a ordem da matriz", "Nova matriz", JOptionPane.QUESTION_MESSAGE);
        
        if(num == null) return;

        try{
            if(num.isBlank() || Integer.parseInt(num) < 0){
                JOptionPane.showMessageDialog(this, "Selecione um valor maior que 0 para determinar a ordem da matriz", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ordem = Integer.parseInt(num);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número da ordem da matriz", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementos.clear();

        p_matriz.removeAll();
        p_matriz.setLayout(new GridLayout(ordem, ordem, 5, 5));

        for(int i = 0; i < ordem; i++){
            for(int j = 0; j < ordem; j++){
                JTextField tf = new JTextField(10);

                tf_elementos.add(tf);

                JPanel p_elemento = new JPanel(new FlowLayout());
                p_elemento.add(new JLabel(String.format("<html>a<sub>%d%d</sub></html>", i + 1, j + 1)));
                p_elemento.add(tf);
                p_matriz.add(p_elemento);
            }
        }
        p_matriz.revalidate();
        p_matriz.repaint();
    }

    private void determinante(){
        if(tf_elementos.isEmpty() || !Matrizes.elementosValidos(tf_elementos)){
            JOptionPane.showMessageDialog(this, "Preencha a matriz com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[][] matriz = Matrizes.criarMatriz(tf_elementos);

        Fraction determinante = Fraction.getFraction(Matrizes.determinante(matriz));

        String msg = "det" + Matrizes.formatoLaTeX(matriz) + "=" + (determinante.getDenominator() == 1 ? determinante.getNumerator() : String.format("\\frac{%d}{%d}", determinante.getNumerator(), determinante.getDenominator()));

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Resultado do determinante", JOptionPane.PLAIN_MESSAGE);
    }

    private void inversa(){
        if(tf_elementos.isEmpty() || !Matrizes.elementosValidos(tf_elementos)){
            JOptionPane.showMessageDialog(this, "Preencha a matriz com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double[][] matriz = Matrizes.criarMatriz(tf_elementos);

        if(Matrizes.determinante(matriz) == 0){
            JOptionPane.showMessageDialog(this, "A matriz não possui inversa, pois o determiante da matriz fornecida é igual a 0.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double[][] matrizInversa = Matrizes.inversa(matriz);

        String msg = Matrizes.formatoLaTeX(matriz) + "^{-1} =" + Matrizes.formatoLaTeX(matrizInversa);

        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Resultado da inversão", JOptionPane.PLAIN_MESSAGE);
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
