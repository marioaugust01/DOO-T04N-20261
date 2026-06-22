import javax.swing.*;
import java.util.List;
import java.awt.BorderLayout;

public class TelaPrincipal extends JFrame {

    private JTextField campoBusca;
    private JButton botaoBuscar;
    private JTextArea areaResultado;

    public TelaPrincipal() {
        setTitle("Minhas series");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelTopo = new JPanel(new BorderLayout());
        campoBusca = new JTextField();
        botaoBuscar = new JButton("Buscar");
        botaoBuscar.addActionListener(e -> buscar());
        painelTopo.add(campoBusca, BorderLayout.CENTER);
        painelTopo.add(botaoBuscar, BorderLayout.EAST);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        JScrollPane rolagem = new JScrollPane(areaResultado);

        add(painelTopo, BorderLayout.NORTH);
        add(rolagem, BorderLayout.CENTER);
    }

    private void buscar() {
        String nome = campoBusca.getText();
        areaResultado.setText("");

        try { 
            TVMazeService servico = new TVMazeService();
            List<Serie> series = servico.buscarSeries(nome);

            if(series.isEmpty()) {
                areaResultado.setText("Nenhuma serie foi encontrada!");
                return;
            }

            for (Serie s : series) {
                areaResultado.append("Nome:     " + s.getNome() + "\n");
                areaResultado.append("Idioma:   " + s.getIdioma() + "\n");
                areaResultado.append("Generos:  " + s.getGeneros() + "\n");
                areaResultado.append("Nota:     " + s.getNota() + "\n");
                areaResultado.append("Status:   " + s.getStatus() + "\n");
                areaResultado.append("Estreia:  " + s.getDataEstreia() + "\n");
                areaResultado.append("Termino:  " + s.getDataTermino() + "\n");
                areaResultado.append("Emissora: " + s.getEmissora() + "\n");
                areaResultado.append("--------------------------\n");                
            }
        } catch (Exception ex) {
            // mostra o erro na tela pro usuario
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

}
