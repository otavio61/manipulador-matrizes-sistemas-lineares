package view.sistemaLinear;

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
import utils.SistemasLineares;

public class SistemaQuadrado extends JPanel{

    private ArrayList<JTextField> tf_elementos; 

    int numLinha, numColuna;

    private JPanel p_sistema, p_botao;
    private JButton bt_novoSistema, bt_limpar, bt_resolucao;

    public SistemaQuadrado(){
        setLayout(new BorderLayout());

        tf_elementos = new ArrayList<>();

        bt_novoSistema = new JButton("Novo sistema");
        bt_novoSistema.addActionListener(e -> {novoSistema();});

        bt_limpar = new JButton("Limpar");
        bt_limpar.addActionListener(e -> {limpar();});
        bt_limpar.setMnemonic('l');

        bt_resolucao = new JButton("Resolver sistema");
        bt_resolucao.addActionListener(e -> {resolver();});

        p_sistema = new JPanel();
        p_sistema.setBorder(new EmptyBorder(5, 5, 5, 5));

        p_botao = new JPanel(new GridLayout(1, 3, 5, 5));
        p_botao.setBorder(new EmptyBorder(5, 5, 5, 5));
        p_botao.add(bt_novoSistema);
        p_botao.add(bt_limpar);
        p_botao.add(bt_resolucao);

        add(p_sistema, BorderLayout.CENTER);
        add(p_botao, BorderLayout.SOUTH);
    }

    private void novoSistema(){
        int ordem;
        String num = JOptionPane.showInputDialog(this, "Digite qual será a ordem do sistema", "Novo sistema", JOptionPane.QUESTION_MESSAGE);
        
        if(num == null) return;

        try{
            if(num.isBlank() || Integer.parseInt(num) < 0){
                JOptionPane.showMessageDialog(this, "Selecione um valor maior que 0 para determinar a ordem do sistema", "Atenção!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ordem = Integer.parseInt(num);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Selecione um valor numérico inteiro positivo para o número da ordem do sistema", "ERRO!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        tf_elementos.clear();

        p_sistema.removeAll();
        p_sistema.setLayout(new GridLayout(ordem, ordem + 1, 5, 5));

        for(int i = 0; i < ordem; i++){
            int c = 1;

            for(int j = 0; j < ordem; j++){
                JTextField tf = new JTextField(10);

                tf_elementos.add(tf);

                JPanel p_elemento = new JPanel(new FlowLayout());
                p_elemento.add(tf);
                p_elemento.add(new JLabel(String.format("<html>x<sub>%d</sub></html>", c++)));
                p_elemento.add(new JLabel((j != ordem - 1) ? "          +" : "          ="));
                p_sistema.add(p_elemento);
            }

            JTextField tf = new JTextField(10);

            tf_elementos.add(tf);

            JPanel p_elemento = new JPanel(new FlowLayout());
            p_elemento.add(new JLabel(String.format("<html>b<sub>%d</sub></html>", i + 1)));
            p_elemento.add(tf);
            p_sistema.add(p_elemento);
        }

        p_sistema.revalidate();
        p_sistema.repaint();
    }

    private void resolver(){
        if(tf_elementos.isEmpty() || !Matrizes.elementosValidos(tf_elementos)){
            JOptionPane.showMessageDialog(this, "Preencha o sistema linear com valores válidos!", "ERRO!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int linha = (int) Math.sqrt(tf_elementos.size());

        double[][] sistema = Matrizes.criarMatriz(tf_elementos, linha, linha + 1);

        if(Matrizes.determinante(Matrizes.removerColuna(sistema, sistema[0].length)) == 0){
            JOptionPane.showMessageDialog(this, "O sistema linear fornecido é possível e indeterminado ou impossível", "Classificação do sistema", JOptionPane.INFORMATION_MESSAGE);
            return;
        } 

        double[][] resultado = SistemasLineares.substituicaoRetroativa(SistemasLineares.eliminacaoGaussPivoteado(sistema));

        String msg = "\\begin{bmatrix}";

        for(int i = 1; i <= linha; i++) {
            msg += String.format("x_{%d}", i);
            if(i != linha) msg += "\\\\";
        }

        msg += "\\end{bmatrix} = " + Matrizes.formatoLaTeX(resultado);
        
        TeXFormula formula = new TeXFormula(msg);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        BufferedImage b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        JLabel label = new JLabel();
        label.setIcon(icon);

        JPanel panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Vetor resposta do sistema", JOptionPane.PLAIN_MESSAGE);

        msg = "b - Ax = " + Matrizes.formatoLaTeX(SistemasLineares.residuo(sistema, resultado));

        formula = new TeXFormula(msg);
        icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

        b = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

        icon.paintIcon(new JLabel(), b.getGraphics(), 0, 0);

        label = new JLabel();
        label.setIcon(icon);

        panel = new JPanel();
        panel.add(label);

        JOptionPane.showMessageDialog(this, panel, "Vetor resíduo do sistema", JOptionPane.PLAIN_MESSAGE);
    }

    private void limpar(){
        if(!tf_elementos.isEmpty()){
            for(JTextField e: tf_elementos)
            e.setText("");
        }
    }
}