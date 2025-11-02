package view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.matriz.DeterminanteInversa;
import view.matriz.MatrizTransposta;
import view.matriz.MultiEscalar;
import view.matriz.MultiMatrizes;
import view.matriz.SomaSubtracao;
import view.sistemaLinear.SistemaInferior;
import view.sistemaLinear.SistemaQuadrado;
import view.sistemaLinear.SistemaSimetrico;
import view.sistemaLinear.SistemaSuperior;

public class MatrizSistemaFrame extends JFrame{
    
    private JPanel p_principal, p_inicial;
    private JLabel lb_titulo, lb_descricao;
    private JMenuBar menuBar;
    private JMenu m_matriz, m_sistema, m_aparencia, m_guia, m_ajuda;
    private JMenuItem mi_determinante, mi_somaSubtracao, mi_multiMatrizes, mi_multiEscalar, mi_transposicao, mi_sistemaQuadrado, mi_sistemaInferior, mi_sistemaSuperior, mi_sistemaSimetrico, mi_sobre, mi_determinanteInversaAjuda, mi_somaSubAjuda, mi_multiMatrizesAjuda, mi_multiEscalarAjuda, mi_transposicaoAjuda, mi_sisQuadrado, mi_sisInferior, mi_sisSuperior, mi_sisSimetrico, mi_limparPreencherAjuda;
    private JRadioButtonMenuItem rbmi_nativeLF, rbmi_classicLF, rbmi_metalLF, rbmi_motifLF, rbmi_nimbusLF; 
    private ButtonGroup bg;
    private CardLayout cardLayout;

    public MatrizSistemaFrame() {
        super("Manipulação de matrizes e sistemas lineares");

        cardLayout = new CardLayout();

        setLayout(cardLayout);

        p_principal = new JPanel(cardLayout);
        p_inicial = new JPanel(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        lb_titulo = new JLabel("Bem vindo ao manipulador de matrizes e sistemas lineares");
        lb_titulo.setFont(new Font("Arial", Font.BOLD, 30));

        lb_descricao = new JLabel("<html><p>Explore as funcionalidade da aplicação acessando o menu \"Operações\".</p> <p>Caso tenha dúvidas, acesse o meu \"Ajuda\", para ver o guia de ajuda. (Projeto ainda em desenvolvimento)</p></html>");
        lb_descricao.setFont(new Font("Arial", Font.PLAIN, 15));

        mi_determinante = new JMenuItem("Determinante e inversa");
        mi_determinante.setMnemonic('d');
        mi_determinante.addActionListener(e -> {determinante();});

        mi_somaSubtracao = new JMenuItem("Soma e subtração entre matrizes");
        mi_somaSubtracao.setMnemonic('s');
        mi_somaSubtracao.addActionListener(e -> {somaSubtracao();});

        mi_multiMatrizes = new JMenuItem("Multiplicação entre matrizes");
        mi_multiMatrizes.setMnemonic('m');
        mi_multiMatrizes.addActionListener(e -> {multiMatrizes();});

        mi_multiEscalar = new JMenuItem("Multiplicação por escalar");
        mi_multiEscalar.setMnemonic('e');
        mi_multiEscalar.addActionListener(e -> {multiEscalar();});

        mi_transposicao = new JMenuItem("Transposição de matrizes");
        mi_transposicao.setMnemonic('t');
        mi_transposicao.addActionListener(e -> {transposicao();});

        mi_sistemaQuadrado = new JMenuItem("Sistema quadrado");
        mi_sistemaQuadrado.setMnemonic('q');
        mi_sistemaQuadrado.addActionListener(e -> {sistemaQuadrado();});

        mi_sistemaInferior = new JMenuItem("Sistema inferior");
        mi_sistemaInferior.setMnemonic('i');
        mi_sistemaInferior.addActionListener(e -> {sistemaInferior();});

        mi_sistemaSuperior = new JMenuItem("Sistema superior");
        mi_sistemaSuperior.setMnemonic('s');
        mi_sistemaSuperior.addActionListener(e -> {sistemaSuperior();});

        mi_sistemaSimetrico = new JMenuItem("Sistema simétrico");
        mi_sistemaSimetrico.setMnemonic('e');
        mi_sistemaSimetrico.addActionListener(e -> {sistemaSimetrico();});

        mi_sobre = new JMenuItem("Sobre");
        mi_sobre.setMnemonic('s');
        mi_sobre.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Projeto \"Manipulador de matrizes e sistemas lineares\"\n\nAutor: Otávio de Moraes\n\nDesenvolvido através do projeto \"Explorando Matrizes e Sistemas Lineares com o Software MAXIMA: Teoria, Aplicações e Implementação Computacional\"" + 
        "\n\nSobre o projeto: Visto que os conteúdos sobre matrizes são ensinados de forma rasa no Ensino Médio, o projeto tem como objetivo\nser uma ferramenta que permita uma maior compreensão sobre os conhecimentos relacionados a matrizes e sistemas lineares, oferencendo uma\ninterface simples e direta que utiliza formataçao TeX para a presentação dos resultados das operações." + 
        "\n\nO projeto também pode ser utilizado como uma ferramenta multidisciplinar, permitindo que os alunos tenham conhecimento\nnão apenas em matrizes, mas também conhecimentos de programação como lógica, operadores matemáticos, lógicos e relacionais\ne desenvolvimento gráfico.", "Sobre o projeto", JOptionPane.INFORMATION_MESSAGE);});

        mi_determinanteInversaAjuda = new JMenuItem("Determinante e Inversa");
        mi_determinanteInversaAjuda.setMnemonic('d');
        mi_determinanteInversaAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "O determinante é uma função matricial que associa a cada matriz quadrada um escalar (número real).\n"
        + "Esta função permite saber se a matriz possui ou não inversa, já que as matrizes que não possuem\ninversa possuem o determinante 0.\n\nA inversa de uma matriz é outra matriz que, quando multiplicada pela matriz original, resulta na\nmatriz identidade (uma matriz onde todos os elementos da diagonal principal são 1 e os restantes são 0)." 
        + "\n\nPara descobrir o determinante de uma matriz quadrada, selecione, no menu de operação, a opção\n\"Determinante e inversa\". Após acessar essa opção, selecione o botão \"Nova Matriz\" e defina quais\nserão os números de linhas e colunas da matriz. " 
        + "Após preencher a matriz, selecione a opção\n\"Determinante\", para verificar qual é o determinante da matriz. Caso o determinante da matriz for\ndiferente de 0, será possível utilizar a opção \"Inversa\" para verificar qual é a inversa da matriz.", "Determinante e Inversa", JOptionPane.INFORMATION_MESSAGE);});

        mi_somaSubAjuda = new JMenuItem("Soma e subtração");
        mi_somaSubAjuda.setMnemonic('s');
        mi_somaSubAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "A soma e subtração de matrizes são operações que se realizam entre matrizes de mesma ordem,\nsomando ou subtraindo os elementos correspondentes em cada posição. Para que a soma ou\nsubtração de matrizes seja possível, é necessário que as matrizes envolvidas tenham o mesmo\nnúmero de linhas e o mesmo número de colunas."
        + "\n\nPara realizar uma soma ou subtração entre duas matrizes, selecione, no menu de operações,\na opção\"Soma e subtração entre matrizes\". Após acessar essa opção, selecione o botão\n\"Novas Matrizes\" e defina quais serão os números de linhas e colunas das matrizes."
        + "\n\nApós preencher as matrizes, selecione a opção \"Somar\" para a soma e \"Subtrair\" para subtração.", "Soma e subtração", JOptionPane.INFORMATION_MESSAGE);}); 

        mi_multiMatrizesAjuda = new JMenuItem("Multiplicação entre matrizes");
        mi_multiMatrizesAjuda.setMnemonic('m');
        mi_multiMatrizesAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "A multiplicação entre matrizes é uma operação que resulta em uma nova matriz, onde cada elemento\né calculado pela soma dos produtos dos elementos correspondentes da linha da primeira matriz e da\ncoluna da segunda matriz. A condição para que a multiplicação seja possível é que o número de\ncolunas da primeira matriz seja igual ao número de linhas da segunda matriz."
        + "\n\nPara multiplicar duas matrizes, selecione, no menu de operações, a opção\n\"Multiplicação entre matrizes\". Após acessar essa opção, selecione os botões \"Nova Matriz A\" e\n\"Nova Matriz B\" para definir o número de linhas e colunas das respectivas matrizes.\nApós preencher as matrizes, selecione a opção \"Multiplicar\" para realizar a multiplicação.", "Multiplicação entre matrizes", JOptionPane.INFORMATION_MESSAGE);});

        mi_multiEscalarAjuda = new JMenuItem("Multiplicação por escalar");
        mi_multiEscalarAjuda.setMnemonic('e');
        mi_multiEscalarAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "A multiplicação por escalar de uma matriz envolve multiplicar cada elemento damatriz por um\núnico número real (escalar). O resultado é uma nova matriz, com os elementos modificados\npela multiplicação."
        + "\n\nPara realizar uma multiplicação por escalar, selecione, no menu de operações, a opção\n\"Multiplicação por escalar\". Após acessar essa opção, selecione o botão \"Nova Matriz\"\ne defina quais serão os números de linhas e colunas da matriz. "
        + "Após preencher a matriz,\nselecione a opção \"Multiplicar\". Um campo irá aparecer perguntando qual será o escalar\nque irá realizar a multiplicação, após selecionar um valor, será mostrado o resultado da\nmultiplicação.", "Multiplicação por escalar", JOptionPane.INFORMATION_MESSAGE);});

        mi_transposicaoAjuda = new JMenuItem("Transposição");
        mi_transposicaoAjuda.setMnemonic('t');
        mi_transposicaoAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "A transposição de uma matriz envolve a inversão de suas linhas e colunas, criando uma\nnova matriz onde as linhas da matriz original se tornam as colunas da transposta, e as\ncolunas da matriz original se tornam as linhas da transposta."
        + "\n\nPara realizar uma operação de transposição, selecione, no menu de operações, a opção\n\"Transposição de matrizes\". Após acessar essa opção, selecione o botão \"Nova Matriz\"\ne defina quais serão os números de linhas e colunas da matriz. "
        + "Após preencher a matriz,\nselecione a opção \"Transpor\" para obter o resultado da transposição da matriz.", "Transposição", JOptionPane.INFORMATION_MESSAGE);});

        mi_sisQuadrado = new JMenuItem("Sistema quadrado");
        mi_sisQuadrado.setMnemonic('q');
        mi_sisQuadrado.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Um sistema quadrado, é aquele que possui a mesma quantidade de linhas e colunas.\nPara resolver este sistema, é utilizado a eliminação de Gauss com pivoteamento parcial\nJuntamente com a substituição retroativa."
        + "\n\nPara desenvolver o sistema, selecione, no menu de operações, a opção\n\"Sistema quadrado\". Após acessar essa opção, selecione o botão \"Novo sistema\"\ne defina os valores do sistema. Após preencher o sistema, selecione a opção\n\"Resolver sistema\" para obter o vetor resposta.", "Sistema quadrado", JOptionPane.INFORMATION_MESSAGE);});

        mi_sisInferior = new JMenuItem("Sistema inferior");
        mi_sisInferior.setMnemonic('i');
        mi_sisInferior.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Um sistema inferior, é aquele que a[i, j] = 0 quando i < j. Para resolver este\nsistema, é utilizado a substituição sucessiva."
        + "\n\nPara desenvolver o sistema, selecione, no menu de operações, a opção\n\"Sistema inferior\". Após acessar essa opção, selecione o botão \"Novo sistema\"\ne defina os valores do sistema. Após preencher o sistema, selecione a opção\n\"Resolver sistema\" para obter o vetor resposta.", "Sistema inferior", JOptionPane.INFORMATION_MESSAGE);});

        mi_sisSuperior = new JMenuItem("Sistema superior");
        mi_sisSuperior.setMnemonic('u');
        mi_sisSuperior.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Um sistema superior, é aquele que a[i, j] = 0 quando i > j. Para resolver este\nsistema, é utilizado a substituição retroativa."
        + "\n\nPara desenvolver o sistema, selecione, no menu de operações, a opção\n\"Sistema superior\". Após acessar essa opção, selecione o botão \"Novo sistema\"\ne defina os valores do sistema. Após preencher o sistema, selecione a opção\n\"Resolver sistema\" para obter o vetor resposta.", "Sistema superior", JOptionPane.INFORMATION_MESSAGE);});

        mi_sisSimetrico = new JMenuItem("Sistema simetrico");
        mi_sisSimetrico.setMnemonic('o');
        mi_sisSimetrico.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Um sistema simétrico, é aquele que a[i, j] = a[j, i]. Para resolver este sistema,\né utilizado a fatoração de Cholesky, juntamente com a substituição\nsucessiva e retroativa."
        + "\n\nPara desenvolver o sistema, selecione, no menu de operações, a opção\n\"Sistema simétrico\". Após acessar essa opção, selecione o botão \"Novo sistema\"\ne defina os valores do sistema. Após preencher o sistema, selecione a opção\n\"Resolver sistema\" para obter o vetor resposta.", "Sistema simétrico", JOptionPane.INFORMATION_MESSAGE);});


        mi_limparPreencherAjuda = new JMenuItem("Limpar e preencher matrizes");
        mi_limparPreencherAjuda.setMnemonic('l');
        mi_limparPreencherAjuda.addActionListener(e -> {JOptionPane.showMessageDialog(this, "Cada painel de operação possui os botões \"Limpar\" e \"Preencher\".\n\nCaso queira limpar todos os campos das matrizes, utilize a opção \"Limpar\"." 
        + "\nCaso queira preencher a matriz de forma rápida, selecione a opção \"Preencher\"\npara ser adicionado um valor para cada campo da matriz.\n\nObs: o preenchimento automático da matriz se inicia com o valor 1 e\nserá aumentado em + 1 para cada campo preenchido.", "Limpar e preencher matrizes", JOptionPane.INFORMATION_MESSAGE);});

        rbmi_nativeLF = new JRadioButtonMenuItem("Nativa");
        rbmi_nativeLF.setSelected(true);
        rbmi_nativeLF.setMnemonic('n');
        rbmi_nativeLF.addActionListener(e -> {setLookAndFeel(UIManager.getSystemLookAndFeelClassName());});

        rbmi_classicLF = new JRadioButtonMenuItem("Windows Clássico");
        rbmi_classicLF.setMnemonic('w');
        rbmi_classicLF.addActionListener(e -> {setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");});  

        rbmi_metalLF = new JRadioButtonMenuItem("Metal");
        rbmi_metalLF.setMnemonic('m');
        rbmi_metalLF.addActionListener(e -> {setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");});

        rbmi_motifLF = new JRadioButtonMenuItem("Motif");
        rbmi_motifLF.setMnemonic('o');
        rbmi_motifLF.addActionListener(e -> {setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");});
        
        rbmi_nimbusLF = new JRadioButtonMenuItem("Nimbus");
        rbmi_nimbusLF.setMnemonic('i');
        rbmi_nimbusLF.addActionListener(e -> {setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");});

        bg = new ButtonGroup();
        bg.add(rbmi_nativeLF);
        bg.add(rbmi_classicLF);
        bg.add(rbmi_metalLF);
        bg.add(rbmi_motifLF);
        bg.add(rbmi_nimbusLF);

        m_matriz = new JMenu("Matrizes");
        m_matriz.setMnemonic('m');
        m_matriz.add(mi_determinante);
        m_matriz.add(mi_somaSubtracao);
        m_matriz.add(mi_multiMatrizes);
        m_matriz.add(mi_multiEscalar);
        m_matriz.add(mi_transposicao);

        m_sistema = new JMenu("Sistemas lineares");
        m_sistema.setMnemonic('s');
        m_sistema.add(mi_sistemaQuadrado);
        m_sistema.add(mi_sistemaInferior);
        m_sistema.add(mi_sistemaSuperior);
        m_sistema.add(mi_sistemaSimetrico);

        m_aparencia = new JMenu("Aparência");
        m_aparencia.setMnemonic('a');
        m_aparencia.add(rbmi_nativeLF);
        m_aparencia.add(rbmi_classicLF);
        m_aparencia.add(rbmi_metalLF);
        m_aparencia.add(rbmi_motifLF);
        m_aparencia.add(rbmi_nimbusLF);

        m_guia = new JMenu("Guia de Ajuda");
        m_guia.setMnemonic('g');
        m_guia.add(mi_determinanteInversaAjuda);
        m_guia.add(mi_somaSubAjuda);
        m_guia.add(mi_multiMatrizesAjuda);
        m_guia.add(mi_multiEscalarAjuda);
        m_guia.add(mi_transposicaoAjuda);
        m_guia.addSeparator();
        m_guia.add(mi_sisQuadrado);
        m_guia.add(mi_sisInferior);
        m_guia.add(mi_sisSuperior);
        m_guia.add(mi_sisSimetrico);
        m_guia.addSeparator();
        m_guia.add(mi_limparPreencherAjuda);

        m_ajuda = new JMenu("Ajuda");
        m_ajuda.setMnemonic('j');
        m_ajuda.add(m_guia);
        m_ajuda.addSeparator();
        m_ajuda.add(mi_sobre);

        menuBar = new JMenuBar();
        menuBar.add(m_matriz);
        menuBar.add(m_sistema);
        menuBar.add(m_aparencia);
        menuBar.add(m_ajuda);

        setJMenuBar(menuBar);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        p_inicial.add(lb_titulo, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        p_inicial.add(lb_descricao, gridBagConstraints);

        p_principal.add(p_inicial, "Inicial");
        p_principal.add(new DeterminanteInversa(), "Determinante");
        p_principal.add(new SomaSubtracao(), "SomaSubtracao");
        p_principal.add(new MultiMatrizes(), "MultiMatrizes");
        p_principal.add(new MultiEscalar(), "MultiEscalar");
        p_principal.add(new MatrizTransposta(), "Transposicao");
        p_principal.add(new SistemaQuadrado(), "SistemaQuadrado");
        p_principal.add(new SistemaInferior(), "SistemaInferior");
        p_principal.add(new SistemaSuperior(), "SistemaSuperior");
        p_principal.add(new SistemaSimetrico(), "SistemaSimetrico");

        add(p_principal);
    }

    private void determinante(){
        cardLayout.show(p_principal, "Determinante");
        this.setSize(600, 500);
    }

    private void somaSubtracao(){
        cardLayout.show(p_principal, "SomaSubtracao");
        this.setSize(1000, 500);
    }

    private void multiMatrizes(){
        cardLayout.show(p_principal, "MultiMatrizes");
        this.setSize(1000, 500);
    }

    private void multiEscalar(){
        cardLayout.show(p_principal, "MultiEscalar");
        this.setSize(500, 500);
    }

    private void transposicao(){
        cardLayout.show(p_principal, "Transposicao");
        this.setSize(500, 500);
    }

    private void sistemaQuadrado(){
        cardLayout.show(p_principal, "SistemaQuadrado");
        this.setSize(900, 500);
    }

    private void sistemaInferior(){
        cardLayout.show(p_principal, "SistemaInferior");
        this.setSize(900, 500);
    }

    private void sistemaSuperior(){
        cardLayout.show(p_principal, "SistemaSuperior");
        this.setSize(900, 500);
    }

    private void sistemaSimetrico(){
        cardLayout.show(p_principal, "SistemaSimetrico");
        this.setSize(900, 500);
    }

    public void setLookAndFeel(String lookAndFeel){
        try{
            UIManager.setLookAndFeel(lookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao alterar a aparência da aplicação \nErro: " + e.getClass(), "ERRO!", JOptionPane.ERROR_MESSAGE);
        }
    }
}