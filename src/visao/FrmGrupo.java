package visao;


import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleGrupo;
import controle.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.ModeloGrupo;

public class FrmGrupo extends javax.swing.JFrame {

    ConexaoBanco connGrupo = new ConexaoBanco();
    ControleGrupo controle = new ControleGrupo();
    ModeloGrupo mod = new ModeloGrupo();
    
    public FrmGrupo() {
       try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
        initComponents();
        connGrupo.conexao();
        
        jfCodigo.setDocument(new LimiteCaracterNumerico(6));
        jfGrupo.setDocument(new LimiteCaracter(100));
        
        preencherTabela();
        
        //jtGrupo.setDefaultRenderer(Object.class, new CellRenderer());
        
        
        
    }

    private void preencherTabela(){
		
	   ArrayList dados = new ArrayList();
       
       String [] Colunas = new String[]{"Código", "Grupo"};
     
       connGrupo.conexao();
       connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");
       
       try{
          connGrupo.rs.first();
          
          do{
             dados.add(new Object[]{connGrupo.rs.getInt("CODIGO_GRUPO"), 
										connGrupo.rs.getString("NOME_GRUPO")});
          } while(connGrupo.rs.next());
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmGrupo\npreencherTabela(){}\n" + e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtGrupo.setModel(modelo);
       jtGrupo.getColumnModel().getColumn(0).setPreferredWidth(50);
       jtGrupo.getColumnModel().getColumn(0).setResizable(true);
       
       jtGrupo.getColumnModel().getColumn(1).setPreferredWidth(80);
       jtGrupo.getColumnModel().getColumn(1).setResizable(true);
       
       jtGrupo.getTableHeader().setReorderingAllowed(!true);
       jtGrupo.setAutoResizeMode(jtGrupo.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtGrupo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
    }
    
    private void habilitar(){
       
       jfGrupo.setEnabled(true);
       jbSalvar.setEnabled(true);
       jbNovo.setEnabled(false);
       
       
        
    }
    
    private void limpar(){
       jfCodigo.setText(null);
       jfGrupo.setText(null);
       jfGrupo.setEnabled(false);
       jbNovo.setEnabled(true);
       jbSalvar.setEnabled(false);
       jbAlterar.setEnabled(false);
       jbExcluir.setEnabled(false);
       
    }
    
    private void habilitarAlteracao(){
       jfGrupo.setEnabled(true);
       jbAlterar.setEnabled(true);
       jbExcluir.setEnabled(true);
       jbNovo.setEnabled(false);
    }
    
    
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jfCodigo = new javax.swing.JTextField();
      jfGrupo = new javax.swing.JTextField();
      jbNovo = new javax.swing.JButton();
      jbSalvar = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();
      jbExcluir = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jbVoltar = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtGrupo = new javax.swing.JTable();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Formulário de Grupos");
      setBackground(new java.awt.Color(229, 229, 233));

      jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel2.setText("Código:");

      jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jLabel3.setText("Grupo:");

      jfCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigo.setEnabled(false);

      jfGrupo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfGrupo.setEnabled(false);

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbSalvar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbSalvar.setText("Salvar");
      jbSalvar.setEnabled(false);
      jbSalvar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSalvarActionPerformed(evt);
         }
      });

      jbAlterar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAlterar.setText("Alterar");
      jbAlterar.setEnabled(false);
      jbAlterar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarActionPerformed(evt);
         }
      });

      jbExcluir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbExcluir.setText("Excluir");
      jbExcluir.setEnabled(false);
      jbExcluir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbExcluirActionPerformed(evt);
         }
      });

      jbPrimeiro.setText("<<");
      jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPrimeiroActionPerformed(evt);
         }
      });

      jbVoltar.setText("<");
      jbVoltar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbVoltarActionPerformed(evt);
         }
      });

      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jPanel2.setBackground(java.awt.SystemColor.textHighlight);
      jPanel2.setToolTipText("Registro");
      jPanel2.setFocusable(false);

      jtGrupo.setBackground(new java.awt.Color(244, 249, 254));
      jtGrupo.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtGrupo.setGridColor(new java.awt.Color(255, 255, 255));
      jtGrupo.getTableHeader().setResizingAllowed(false);
      jtGrupo.getTableHeader().setReorderingAllowed(false);
      jScrollPane1.setViewportView(jtGrupo);

      org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .add(30, 30, 30)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(layout.createSequentialGroup()
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jLabel2)
                     .add(jLabel3))
                  .add(21, 21, 21)
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jfGrupo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 239, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
               .add(layout.createSequentialGroup()
                  .add(jbSalvar)
                  .add(18, 18, 18)
                  .add(jbAlterar)
                  .add(18, 18, 18)
                  .add(jbExcluir))
               .add(layout.createSequentialGroup()
                  .add(jbPrimeiro)
                  .add(18, 18, 18)
                  .add(jbVoltar)
                  .add(18, 18, 18)
                  .add(jbProximo)
                  .add(18, 18, 18)
                  .add(jbUltimo)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(jbSair)
               .add(jbNovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(0, 23, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .add(27, 27, 27)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel2)
               .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jbNovo))
            .add(16, 16, 16)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(layout.createSequentialGroup()
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel3)
                     .add(jfGrupo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(43, 43, 43)
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jbSalvar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jbAlterar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jbExcluir))
                  .add(18, 18, 18)
                  .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                     .add(jbPrimeiro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jbVoltar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jbProximo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
               .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                  .add(jbUltimo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(jbSair, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(66, 66, 66))
      );

      setSize(new java.awt.Dimension(521, 477));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   
   
   
    private void jblimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jblimparActionPerformed
       limpar();
    }//GEN-LAST:event_jblimparActionPerformed

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
       dispose();
       FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
       frmMenu.setVisible(true);
    }//GEN-LAST:event_jbSairActionPerformed

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed

        habilitar();
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
       
       if(jfGrupo.getText().trim().isEmpty()){
          JOptionPane.showMessageDialog(null, "Informe o nome do Grupo!");
          jfGrupo.grabFocus();
       } else{
            
            try{
               mod.setNomeGrupo(jfGrupo.getText());
            
               controle.gravarRegistro(mod);
               preencherTabela();
               limpar();
               
            } catch(Exception ex){
                 //JOptionPane.showMessageDialog(null, "Erro ao salvar registro:\n" + ex);
              }
         }
        
    }//GEN-LAST:event_jbSalvarActionPerformed

    
    
    
    
    
    
    private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
       mod = controle.buscarPrimeiroRegistro();
       
       jfCodigo.setText(""+mod.getCodigo());
       jfGrupo.setText(mod.getNomeGrupo());
       
       habilitarAlteracao();
       
    }//GEN-LAST:event_jbPrimeiroActionPerformed

    private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
       mod = controle.buscarUltimoRegistro();
       
       jfCodigo.setText(""+mod.getCodigo());
       jfGrupo.setText(mod.getNomeGrupo());
       
       habilitarAlteracao();
        
    }//GEN-LAST:event_jbUltimoActionPerformed

    private void jbVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVoltarActionPerformed
       mod = controle.buscarRegistroAnterior();
       
       jfCodigo.setText("" + mod.getCodigo());
       jfGrupo.setText(mod.getNomeGrupo());
       
       habilitarAlteracao();
       
    }//GEN-LAST:event_jbVoltarActionPerformed

    private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
       mod = controle.buscarProximoRegistro();
       
       jfCodigo.setText("" + mod.getCodigo());
       jfGrupo.setText(mod.getNomeGrupo());
       
       habilitarAlteracao();
       
    }//GEN-LAST:event_jbProximoActionPerformed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
       if(jfGrupo.getText().trim().isEmpty()){
          JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
          jfGrupo.grabFocus();
       } else{
            try{
               connGrupo.conexao();
               mod.setCodigo(Integer.parseInt(jfCodigo.getText()));
               mod.setNomeGrupo(jfGrupo.getText());
               controle.alterarRegistro(mod);
               preencherTabela();
               limpar();
            } catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Erro ao setar e executar alteração:\n" + e);
              }
         }//else
        
        
       
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
       if(jfGrupo.getText().trim().isEmpty()){
          JOptionPane.showMessageDialog(null, "Preencha todos os campos");
          jfGrupo.grabFocus();
       } else{
            try{
               connGrupo.conexao();
               
               mod.setCodigo(Integer.parseInt(jfCodigo.getText()));
               //mod.setNomeGrupo(jfGrupo.getText());
               
               controle.excluirRegistro(mod);
               preencherTabela();
               limpar();
            } catch(Exception e){
                 JOptionPane.showMessageDialog(null, "Erro ao setar e executar exclusão:\n" + e);
              }
       
         }
    }//GEN-LAST:event_jbExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGrupo().setVisible(true);
            }
        });
    }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbExcluir;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbSalvar;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JButton jbVoltar;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTextField jfGrupo;
   private javax.swing.JTable jtGrupo;
   // End of variables declaration//GEN-END:variables
}
