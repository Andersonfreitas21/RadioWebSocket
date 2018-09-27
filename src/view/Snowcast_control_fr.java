package view;

import clientes.Snowcast_control;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.Mensagem;

/**
 *
 * @author Anderson Freitas
 */
public class Snowcast_control_fr extends javax.swing.JFrame {

    Map<String, String> estacoes = new HashMap<>();
    private DefaultTableModel modelo;
    Mensagem protocoloSetStation = new Mensagem();
    Snowcast_control clienteTCP;

    public Snowcast_control_fr() {
        initComponents();
    }

//    public void enviaSetSTation() throws IOException {
//        Socket socketClienteTCP;
//        socketClienteTCP = new Socket("127.0.0.1",55555);
//        ObjectOutputStream output;
//        output = new ObjectOutputStream(socketClienteTCP.getOutputStream());
//        //tocar música
//        //escolhe o id ou nome da estação que o cliente selecionou  
//        //envia via protocolo SetStation o número da estação
//        //2. SetStation: uint8_t commandType = 1; uint16_t stationNumber;
//        protocoloSetStation.setCommandType('1');
//        protocoloSetStation.setStationNumber(jTableEstacoes.getSelectedRow());
//        //System.out.println("Número da estação : " + protocoloSetStation.getStationNumber());
//        output.writeObject(protocoloSetStation);
//    }
    
    //Método para exibir na grade 
    public void RetornoDados(Object obj) {
        this.estacoes = (Map<String, String>) obj;
        modelo = (DefaultTableModel) jTableEstacoes.getModel();
        modelo.setNumRows(0);

        estacoes.keySet().forEach((key) -> {
            //Capturamos o valor a partir da chave
            String value = estacoes.get(key);
            modelo.addRow(new String[]{key, value});
        });
    }

//    public void setCampo() {
//        if ((jTableEstacoes.getSelectedRow() != -1) || (jTableEstacoes.getSelectedColumn() == 0)) {
//            if (jTableEstacoes.getSelectedColumn() == 0) {
//                txtEstacao.setText(jTableEstacoes.getValueAt(jTableEstacoes.getSelectedRow(), 0).toString());
//            } else {
//                txtEstacao.setText(jTableEstacoes.getValueAt(jTableEstacoes.getSelectedRow(), 1).toString());
//            }
//        }
//    }

    public void enviaEstacao(String estacao) {
        if (txtEstacao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Command Invalid - Selecione uma estação.");
        } else if(Integer.parseInt(txtEstacao.getText()) > jTableEstacoes.getRowCount()) {
            JOptionPane.showMessageDialog(null, "Invalido");
        } 
        else {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (modelo.getValueAt(i, 0).toString().contains(estacao)) {
                    JOptionPane.showMessageDialog(null, jTableEstacoes.getValueAt(i, 0).toString());
                }
            }
            protocoloSetStation.setStationNumber(jTableEstacoes.getSelectedRow());
            clienteTCP.getStationNumber(protocoloSetStation.getStationNumber());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEstacao = new javax.swing.JTextField();
        btn_enviar = new javax.swing.JButton();
        btn_listar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEstacoes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setText("Rádio Web Socket");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Radio_Tower_36px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setText("Estação: ");

        txtEstacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstacaoKeyReleased(evt);
            }
        });

        btn_enviar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btn_enviar.setText("Tocar");
        btn_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enviarActionPerformed(evt);
            }
        });

        btn_listar.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btn_listar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Playlist_24px.png"))); // NOI18N
        btn_listar.setText("Listar Estações");
        btn_listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_listarActionPerformed(evt);
            }
        });

        btn_cancelar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de Estações", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Century Gothic", 0, 10))); // NOI18N

        jTableEstacoes.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jTableEstacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Estação", "Música"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEstacoes.setToolTipText("");
        jTableEstacoes.getTableHeader().setReorderingAllowed(false);
        jTableEstacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEstacoesMouseClicked(evt);
            }
        });
        jTableEstacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableEstacoesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableEstacoes);
        if (jTableEstacoes.getColumnModel().getColumnCount() > 0) {
            jTableEstacoes.getColumnModel().getColumn(0).setResizable(false);
            jTableEstacoes.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableEstacoes.getColumnModel().getColumn(1).setResizable(false);
            jTableEstacoes.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btn_listar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_listar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEstacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_enviar)
                    .addComponent(btn_cancelar))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_listarActionPerformed
        clienteTCP = new Snowcast_control();
        try {
            clienteTCP.conexaoTCP(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Snowcast_control_fr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_listarActionPerformed

    private void jTableEstacoesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableEstacoesKeyReleased
        //setCampo();
    }//GEN-LAST:event_jTableEstacoesKeyReleased

    private void jTableEstacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEstacoesMouseClicked
        //setCampo();
    }//GEN-LAST:event_jTableEstacoesMouseClicked

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviarActionPerformed
        enviaEstacao(txtEstacao.getText());
    }//GEN-LAST:event_btn_enviarActionPerformed

    private void txtEstacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstacaoKeyReleased
        if (txtEstacao.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "DEU CERTO");
        }
    }//GEN-LAST:event_txtEstacaoKeyReleased

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Snowcast_control_fr.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Snowcast_control_fr().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_enviar;
    private javax.swing.JButton btn_listar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableEstacoes;
    private javax.swing.JTextField txtEstacao;
    // End of variables declaration//GEN-END:variables
}
