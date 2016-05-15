/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.lowagie.text.Font;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ModeloTabela;
import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Gustavo M S
 */
public class JDialogRecebEscolherCompra extends javax.swing.JDialog {
   ConexaoBanco connProcura = new ConexaoBanco();
   
   public static FrmReceberCompra frmReceberCompra;
   
   
   public JDialogRecebEscolherCompra(FrmReceberCompra parent, boolean modal) {
      FontUIResource font = new FontUIResource("Arial", Font.NORMAL, 14);
      UIManager.put("Table.font", font);
      //cabecalhoCompra.setFont(font);
      
      this.setBackground(Color.white);
      
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      //super(parent, modal);
      this.frmReceberCompra = parent;
      this.setModal(modal);
      initComponents();
      setLocationRelativeTo(this);
      
      
      
      JTableHeader cabecalhoCompra = jtEscolherCompra.getTableHeader();
      cabecalhoCompra.setFont(font);
      
   }

   
   public void PreencherTodaTabela(){
           
       ArrayList dados = new ArrayList();
       
       String [] Colunas = new String[]{"Compra", "Fornecedor", "Valor", "Data", "Tipo de Compra"};
       
       
       try{
         
           connProcura.conexao();
           
           if(jfFornecedor.getText().trim().isEmpty() && jfData.getText().trim().isEmpty()){
              
              connProcura.executaaSQL("SELECT C.CODIGO_COMPRA, F.RAZAO_SOCIAL, C.VALOR_COMPRA, C.DATA_COMPRA, C.TIPO_COMPRA FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR  AND SITUACAO = 'APROVADA';");           
           
           } else if(!jfFornecedor.getText().trim().isEmpty() && jfData.getText().trim().isEmpty()){
              
              connProcura.executaaSQL("SELECT C.CODIGO_COMPRA, F.RAZAO_SOCIAL, C.VALOR_COMPRA, C.DATA_COMPRA, C.TIPO_COMPRA FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR WHERE F.RAZAO_SOCIAL LIKE '%" + jfFornecedor.getText() + "%' AND SITUACAO = 'APROVADA';");           
           
           } else if(jfFornecedor.getText().trim().isEmpty() && !jfData.getText().trim().isEmpty()){
                
                connProcura.executaaSQL("SELECT C.CODIGO_COMPRA, F.RAZAO_SOCIAL, C.VALOR_COMPRA, C.DATA_COMPRA, C.TIPO_COMPRA FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR WHERE C.DATA_COMPRA >= '" + dataPadraoBanco(jfData.getText()) + " 00:00:00' AND C.DATA_COMPRA <= '" + dataPadraoBanco(jfData.getText()) + " 23:59:59' AND SITUACAO = 'APROVADA';");           
           
             } else if(!jfFornecedor.getText().trim().isEmpty() && !jfData.getText().trim().isEmpty()){
                  
                  connProcura.executaaSQL("SELECT C.CODIGO_COMPRA, F.RAZAO_SOCIAL, C.VALOR_COMPRA, C.DATA_COMPRA, C.TIPO_COMPRA FROM COMPRA C JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR WHERE C.DATA_COMPRA >= '" + dataPadraoBanco(jfData.getText()) + " 00:00:00' AND C.DATA_COMPRA <= '" + dataPadraoBanco(jfData.getText()) + " 23:59:59' AND F.RAZAO_SOCIAL LIKE '%" + jfFornecedor.getText() + "%' AND SITUACAO = 'APROVADA';"); 
               }
           
           
           
          if(!connProcura.rs.first()){
             JOptionPane.showMessageDialog(null, "Nenhum item corresponde a pesquisa");
          } else{  
           
               do{
                  dados.add(new Object[]{
                  connProcura.rs.getInt("CODIGO_COMPRA"), connProcura.rs.getString("RAZAO_SOCIAL"), connProcura.rs.getDouble("VALOR_COMPRA"),
                  dataPadraoBrasil(connProcura.rs.getString("DATA_COMPRA")), connProcura.rs.getString("TIPO_COMPRA")
             
               });   
                
          
               } while(connProcura.rs.next());
            
            }
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtEscolherCompra.setModel(modelo);
       jtEscolherCompra.getColumnModel().getColumn(0).setPreferredWidth(80);
       jtEscolherCompra.getColumnModel().getColumn(0).setResizable(true);
       
       jtEscolherCompra.getColumnModel().getColumn(1).setPreferredWidth(80);
       jtEscolherCompra.getColumnModel().getColumn(1).setResizable(true);
       
       jtEscolherCompra.getColumnModel().getColumn(2).setPreferredWidth(80);
       jtEscolherCompra.getColumnModel().getColumn(2).setResizable(true);
       
       jtEscolherCompra.getColumnModel().getColumn(3).setPreferredWidth(80);
       jtEscolherCompra.getColumnModel().getColumn(3).setResizable(true);
       
       jtEscolherCompra.getColumnModel().getColumn(4).setPreferredWidth(80);
       jtEscolherCompra.getColumnModel().getColumn(4).setResizable(true);
       
       jtEscolherCompra.getTableHeader().setReorderingAllowed(true);
       jtEscolherCompra.setAutoResizeMode(jtEscolherCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtEscolherCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       
    }
    
    public void apagarTabela(){
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{};
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       jtEscolherCompra.setModel(modelo);
    }
    
    
    public String dataPadraoBanco(String data){
       String dataBanco = data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
       //JOptionPane.showMessageDialog(null, dataBanco);
       return dataBanco;
    }
    
    
    
    public String dataPadraoBrasil(String data){
       String dataBanco = data.substring(10,16) + " | " + data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(2,4);
       return dataBanco;
    }
    
    
   
    
   
   
   
   
   
   
   
   
   
   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      jfFornecedor = new javax.swing.JTextField();
      jfData = new javax.swing.JFormattedTextField();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtEscolherCompra = new javax.swing.JTable();
      jbFiltrar = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jbConfirmar = new javax.swing.JButton();
      jbCancelar = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setResizable(false);

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Fornecedor:");

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Data:");

      jfFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfData.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfDataFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfDataFocusLost(evt);
         }
      });
      jfData.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jfDataActionPerformed(evt);
         }
      });

      jtEscolherCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtEscolherCompra.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtEscolherCompra.setGridColor(new java.awt.Color(255, 255, 255));
      jtEscolherCompra.setRowHeight(25);
      jtEscolherCompra.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jtEscolherCompraFocusLost(evt);
         }
      });
      jtEscolherCompra.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jtEscolherCompraMouseClicked(evt);
         }
      });
      jScrollPane1.setViewportView(jtEscolherCompra);

      jbFiltrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbFiltrar.setText("Procurar");
      jbFiltrar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbFiltrarActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jbConfirmar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbConfirmar.setText("Confirmar");
      jbConfirmar.setEnabled(false);
      jbConfirmar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbConfirmarActionPerformed(evt);
         }
      });

      jbCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbCancelar.setText("Cancelar");
      jbCancelar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPane1)
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addComponent(jLabel1)
                     .addComponent(jLabel2))
                  .addGap(18, 18, 18)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jfData, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(40, 40, 40)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addComponent(jbLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(jbFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addGap(66, 66, 66)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jbConfirmar)
                     .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(0, 195, Short.MAX_VALUE)))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbFiltrar)
               .addComponent(jbConfirmar))
            .addGap(17, 17, 17)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(jfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbLimpar)
               .addComponent(jbCancelar))
            .addGap(18, 18, 18)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      setSize(new java.awt.Dimension(1240, 506));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarActionPerformed
      PreencherTodaTabela();
      jbConfirmar.setEnabled(!true);
   }//GEN-LAST:event_jbFiltrarActionPerformed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      apagarTabela();
      
      jfFornecedor.setText(null);
      jfData.setText(null);
      jbConfirmar.setEnabled(false);
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jbConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarActionPerformed
      String numero = String.valueOf(jtEscolherCompra.getValueAt(jtEscolherCompra.getSelectedRow(), 0));
      //int n = Integer.parseInt(numero);
      
      frmReceberCompra.defineCompra(numero);
      this.dispose();
      
   }//GEN-LAST:event_jbConfirmarActionPerformed

   private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
      this.dispose();
   }//GEN-LAST:event_jbCancelarActionPerformed

   private void jtEscolherCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtEscolherCompraMouseClicked
      jbConfirmar.setEnabled(true);
   }//GEN-LAST:event_jtEscolherCompraMouseClicked

   private void jtEscolherCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtEscolherCompraFocusLost
      //jbConfirmar.setEnabled(false);
   }//GEN-LAST:event_jtEscolherCompraFocusLost

   private void jfDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfDataActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jfDataActionPerformed

   private void jfDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfDataFocusLost
      int controle = 0;
      String testeData = jfData.getText();
      
      if(testeData.isEmpty()){
         jbFiltrar.setEnabled(true);
         return;
      } else{
            try{
               Calendar data = Calendar.getInstance();
               SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
               formato.setLenient(false);
               data.setTime(formato.parse(testeData));
      
            } catch(ParseException e){
                  JOptionPane.showMessageDialog(null, "Data invalida!");
                  //jfData.grabFocus();
                  controle = 1;
                  return;
               }
      
            if(controle == 0){
               jbFiltrar.setEnabled(true);
            }
            
      }
      
   }//GEN-LAST:event_jfDataFocusLost

   private void jfDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfDataFocusGained
      jbFiltrar.setEnabled(false);
   }//GEN-LAST:event_jfDataFocusGained

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
         java.util.logging.Logger.getLogger(JDialogRecebEscolherCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(JDialogRecebEscolherCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(JDialogRecebEscolherCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(JDialogRecebEscolherCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>
      //</editor-fold>

      /* Create and display the dialog */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            JDialogRecebEscolherCompra dialog = new JDialogRecebEscolherCompra(frmReceberCompra, true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
               @Override
               public void windowClosing(java.awt.event.WindowEvent e) {
                  System.exit(0);
               }
            });
            dialog.setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JButton jbCancelar;
   private javax.swing.JButton jbConfirmar;
   private javax.swing.JButton jbFiltrar;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JFormattedTextField jfData;
   private javax.swing.JTextField jfFornecedor;
   private javax.swing.JTable jtEscolherCompra;
   // End of variables declaration//GEN-END:variables
}
