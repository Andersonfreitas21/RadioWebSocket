package view;

import clientes.Snowcast_control;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anderson Freitas
 */
public class Snowcast_control_fr extends javax.swing.JFrame {

    private ObjectOutputStream output ;
    private ObjectInputStream input;
    
    private String nomeServidor;

    private Socket client;
    
    
    Snowcast_control clienteTCP;
    Map<Integer, String> estacoes = new HashMap<>();
    private DefaultTableModel modelo;

    public Snowcast_control_fr() {
        initComponents();
        //connectToServer();
    }

//    public void connectToServer() {
//        try {
//            client = new Socket(InetAddress.getByName(nomeServidor),55555);
//            System.out.println("Conextado a " + client.getInetAddress().getHostName());
//        } catch (Exception e) {
//        }
//
//    }

    //Método para exibir na grade 
    public void RetornoDados(Object obj) {
        this.estacoes = (Map<Integer, String>) obj;
        modelo = (DefaultTableModel) jTableEstacoes.getModel();
        modelo.setNumRows(0);
        estacoes.keySet().forEach((key) -> {
            //Capturamos o valor a partir da chave
            String value = estacoes.get(key);
            modelo.addRow(new Object[]{key, value});
        });
    }
    public void enviaEstacao(String estacao) throws ClassNotFoundException {
        if (txtEstacao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Command Invalid - Selecione uma estação.");
            txtEstacao.setText("");
            txtEstacao.requestFocus();
        } else if (Integer.parseInt(txtEstacao.getText()) >= jTableEstacoes.getRowCount()) {
            JOptionPane.showMessageDialog(null, "Fora da faixa de estações! ");
            txtEstacao.setText("");
            txtEstacao.requestFocus();
        } else {

            switch (Integer.parseInt(txtEstacao.getText())) {
                case 0:
                    clienteTCP.conexaoTCP(this, 0);
                    break;
                case 1:
                    clienteTCP.conexaoTCP(this, 1);
                    break;
                case 2:
                    clienteTCP.conexaoTCP(this, 2);
                    break;
                case 3:
                    clienteTCP.conexaoTCP(this, 3);
                    break;
                default:
                    System.out.println("Fora da faixa de estações!");
            }
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstacaoKeyTyped(evt);
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
            boolean[] canEdit = new boolean [] {
                false, false
            };

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
            jTableEstacoes.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTableEstacoes.getColumnModel().getColumn(1).setResizable(false);
            jTableEstacoes.getColumnModel().getColumn(1).setPreferredWidth(150);
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
        try {
            enviaEstacao(txtEstacao.getText());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Snowcast_control_fr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_enviarActionPerformed

    private void txtEstacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstacaoKeyReleased
//        if (txtEstacao.getText().equals("0")) {
//            JOptionPane.showMessageDialog(null, "DEU CERTO");
//        }
    }//GEN-LAST:event_txtEstacaoKeyReleased

    private void txtEstacaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstacaoKeyTyped
//        String caracteres = "0987654321";
//        if (!caracteres.contains(evt.getKeyChar() + "")) {
//            evt.consume();
//        }
    }//GEN-LAST:event_txtEstacaoKeyTyped

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
