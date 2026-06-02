package com.climatempo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ClimaGUI extends JFrame {
    private JTextField campoCidade;
    private JTextArea areaResultado;
    private ClimaService service = new ClimaService();

    public ClimaGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Consulta de Clima Tempo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelTopo = new JPanel(new BorderLayout(5, 5));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        painelTopo.add(new JLabel("Digite a Cidade:"), BorderLayout.WEST);
        
        campoCidade = new JTextField();
        campoCidade.setFont(new Font("Arial", Font.PLAIN, 16));
        painelTopo.add(campoCidade, BorderLayout.CENTER);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.addActionListener(e -> acaoBuscar());
        painelTopo.add(btnBuscar, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        areaResultado = new JTextArea("Aguardando consulta...\n");
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaResultado.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(areaResultado);
        add(scroll, BorderLayout.CENTER);
    }

    private void acaoBuscar() {
        String cidade = campoCidade.getText();
        areaResultado.setText("Buscando informações para " + cidade + "...\n");
        
        try {
            ClimaService.DadosClima dados = service.buscarClima(cidade);
            
            ClimaService.CurrentConditions atual = dados.currentConditions;
            ClimaService.Day hoje = dados.days.get(0);


            StringBuilder sb = new StringBuilder();
            sb.append("=== Clima atual em ").append(cidade).append(" ===\n\n");
            sb.append("Condição: ").append(atual.conditions).append("\n");
            sb.append("Temperatura Atual: ").append(atual.temp).append("°C\n");
            sb.append("Máx do Dia: ").append(hoje.tempmax).append("°C | Mín: ").append(hoje.tempmin).append("°C\n");
            sb.append("Umidade: ").append(atual.humidity).append("%\n");
            sb.append("Precipitação: ").append(atual.precip).append(" mm\n");
            sb.append("Vento: ").append(atual.windspeed).append(" km/h\n");
            
            areaResultado.setText(sb.toString());
            
        } catch (ClimaException ex) {
            areaResultado.setText("Falha na consulta.");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClimaGUI().setVisible(true));
    }
}