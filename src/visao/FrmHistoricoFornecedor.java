/*
 * Autor do projeto: Gustavo Mathias de Siqueira 
 * Sistema para controle de estoque, compra e venda de produtos eletronicos...
 * Data: 21/04/2016 - 22:16:40
 */
package visao;

import controle.ConexaoBanco;
import controle.ModeloTabela;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Gustavo M S
 */
public class FrmHistoricoFornecedor extends javax.swing.JFrame {

   ConexaoBanco connForn = new ConexaoBanco();
   ConexaoBanco connV = new ConexaoBanco();
   
   public FrmHistoricoFornecedor() {
      /*try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
      }*/
      initComponents();
      iniciarTabela();
   }

   
   private void iniciarTabela(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{"Data Compra", "Compra", "Produto", "Qtde.", "Val. Item", "Val. Total", "Situação"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtCompra.setModel(modelo);
   }
   
   
   public void preencherDados(){
       
      
      ArrayList dados = new ArrayList();
       
      try{
          
         connForn.conexao();
         
         connForn.executaaSQL("SELECT  F.RAZAO_SOCIAL, P.DESCRICAO, C.DATA, C.CODIGO_COMPRA, I.QUANTIDADE, I.VALOR_ITEM, I.VALOR_TOTAL_ITEM, C.SITUACAO " +
               "FROM COMPRA C " +
               "JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR " +
               "JOIN ITEM_COMPRA I ON C.CODIGO_COMPRA = I.CODIGO_COMPRA " +
               "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO " +
               "WHERE F.CODIGO_FORNECEDOR = '" + jfCodigoFornecedor.getText() + "';");
         
         if(connForn.rs.first()){
            jfRazao.setText(connForn.rs.getString("RAZAO_SOCIAL"));
            
            do{
               dados.add(new Object[]{
                  dataBR(connForn.rs.getString("DATA_COMPRA")), connForn.rs.getInt("CODIGO_COMPRA"),
                  connForn.rs.getString("DESCRICAO"), connForn.rs.getInt("QUANTIDADE"),
                  connForn.rs.getDouble("VALOR_ITEM"), connForn.rs.getDouble("VALOR_TOTAL_ITEM"),
                  connForn.rs.getString("SITUACAO")
               });
            } while(connForn.rs.next());
         } else{
              //JOptionPane.showMessageDialog(null, "Não existe compras para este fornecedor.");
              return;
           }
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmHistoricoFornecedor\n\nErro ao carregar tabela com detalhes da compra\n" + e);
        } 
       
       
      String [] Colunas = new String[]{"Data Compra", "Compra", "Produto", "Qtde.", "Val. Item", "Val. Total", "Situação"};
       
       
       
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
      jtCompra.setModel(modelo);
      jtCompra.getColumnModel().getColumn(0).setPreferredWidth(50);
      jtCompra.getColumnModel().getColumn(0).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(1).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(1).setResizable(!true);
       
       jtCompra.getColumnModel().getColumn(2).setPreferredWidth(180);
       jtCompra.getColumnModel().getColumn(2).setResizable(!true);
       
       jtCompra.getColumnModel().getColumn(3).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(3).setResizable(!true);
       
       jtCompra.getColumnModel().getColumn(4).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(4).setResizable(!true);
      
       jtCompra.getColumnModel().getColumn(5).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(5).setResizable(!true);
       
       jtCompra.getColumnModel().getColumn(6).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(6).setResizable(!true);
       
       jtCompra.getTableHeader().setReorderingAllowed(!true);
       jtCompra.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       connForn.desconecta();
   }
   
   
   
   private String dataBR(String data){
      String dataNova = data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
      return dataNova;
   }
  
   
   
   public String tiraHifenPrazo(String prazo){
      String novoPrazo = prazo;
      int t = novoPrazo.length();
      //System.out.println(t);
      String prazoCerto;
      prazoCerto = novoPrazo.substring(0,t-1);
      //System.out.println(prazoCerto);
      
      
      return prazoCerto;
   }
   
   
   
   
   
   
   
   
   
   
   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanel1 = new javax.swing.JPanel();
      jLabel1 = new javax.swing.JLabel();
      jfCodigoFornecedor = new javax.swing.JTextField();
      jfRazao = new javax.swing.JTextField();
      jbLimpar = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtCompra = new javax.swing.JTable();
      jLabel2 = new javax.swing.JLabel();
      jfPrazo = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      jfCodigoRecebimento = new javax.swing.JTextField();
      jLabel4 = new javax.swing.JLabel();
      jfNF = new javax.swing.JTextField();
      jLabel5 = new javax.swing.JLabel();
      jfDataEmissao = new javax.swing.JTextField();
      jbSair = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setResizable(false);

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Fornecedor:");

      jfCodigoFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfCodigoFornecedorFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfCodigoFornecedorFocusLost(evt);
         }
      });

      jfRazao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfRazao.setFocusable(false);

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Compras "));

      jtCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtCompra.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtCompra.setGridColor(new java.awt.Color(255, 255, 255));
      jtCompra.setRowHeight(25);
      jtCompra.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jtCompraMouseClicked(evt);
         }
      });
      jScrollPane1.setViewportView(jtCompra);

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
            .addContainerGap())
      );

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Prazo:");

      jfPrazo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfPrazo.setFocusable(false);

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Nº Receb: ");

      jfCodigoRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoRecebimento.setFocusable(false);

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("NF:");

      jfNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfNF.setFocusable(false);

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Data Emissão:");

      jfDataEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfDataEmissao.setFocusable(false);

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(37, 37, 37)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jfCodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jfRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addComponent(jbLimpar)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(jbSair))
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                     .addComponent(jLabel2)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                     .addComponent(jfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addGap(18, 18, 18)
                     .addComponent(jLabel3)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                     .addComponent(jfCodigoRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addGap(18, 18, 18)
                     .addComponent(jLabel4)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                     .addComponent(jfNF, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addGap(18, 18, 18)
                     .addComponent(jLabel5)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                     .addComponent(jfDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(33, 33, 33)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jfCodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jfRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbLimpar)
               .addComponent(jbSair))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(jfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel3)
               .addComponent(jfCodigoRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel4)
               .addComponent(jfNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel5)
               .addComponent(jfDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

      setSize(new java.awt.Dimension(1127, 382));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jfCodigoFornecedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCodigoFornecedorFocusLost
      preencherDados();
   }//GEN-LAST:event_jfCodigoFornecedorFocusLost

   private void jtCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCompraMouseClicked
      String c = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 1));
      int codigoCompra = Integer.parseInt(c);
      
      
      String prazo = "";
      
      try{
         
         connForn.conexao();
         connForn.executaaSQL("SELECT R.DATA_EMISSAO, R.CODIGO_RECEBIMENTO, R.NOTA_FISCAL FROM RECEBER_COMPRA R WHERE R.CODIGO_COMPRA = '" + codigoCompra + "';");
         if(connForn.rs.first()){
            
            jfDataEmissao.setText(dataBR(connForn.rs.getString("DATA_EMISSAO")));
            jfCodigoRecebimento.setText(String.valueOf(connForn.rs.getInt("CODIGO_RECEBIMENTO")));
            jfNF.setText(connForn.rs.getString("NOTA_FISCAL"));
            
         } else{
            
              JOptionPane.showMessageDialog(null, "Compra sem recebimento."); 
              jfDataEmissao.setText(null);
              jfCodigoRecebimento.setText(null);
              jfPrazo.setText(null);
              jfNF.setText(null);
           }
         
         
         
         connV.conexao();
         
         connV.executaaSQL("SELECT PRAZO FROM VENCIMENTO WHERE CODIGO_COMPRA = '" + codigoCompra + "';");
         if(connV.rs.first()){
            
            do{
               prazo = prazo + connV.rs.getInt("PRAZO") + "-";
               jfPrazo.setText(prazo);
            }while(connV.rs.next());
         
            jfPrazo.setText(tiraHifenPrazo(jfPrazo.getText()));
         } else{
              JOptionPane.showMessageDialog(null, "Compra sem prazos de vencimento.\nEsta compra deve ser excluida!");
           }
         
         
    
         
         
      } catch(SQLException | HeadlessException e){
           JOptionPane.showMessageDialog(null, "FrmHistoricoFornecedor\n\nErro ao carregar detalhes do recebimento\n" + e);
        } 
       
       
      
      
      connV.desconecta();
     
      connForn.desconecta();
   }//GEN-LAST:event_jtCompraMouseClicked

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      // TODO add your handling code here:
      dispose();
      FrmMenuPrincipal f = new FrmMenuPrincipal();
      f.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      iniciarTabela();
      jfCodigoFornecedor.setText(null);
      jfCodigoRecebimento.setText(null);
      jfDataEmissao.setText(null);
      jfNF.setText(null);
      jfPrazo.setText(null);
      jfRazao.setText(null);
      
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jfCodigoFornecedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCodigoFornecedorFocusGained
      // TODO add your handling code here:
   }//GEN-LAST:event_jfCodigoFornecedorFocusGained

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
            if ("Windows".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmHistoricoFornecedor().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbSair;
   private javax.swing.JTextField jfCodigoFornecedor;
   private javax.swing.JTextField jfCodigoRecebimento;
   private javax.swing.JTextField jfDataEmissao;
   private javax.swing.JTextField jfNF;
   private javax.swing.JTextField jfPrazo;
   private javax.swing.JTextField jfRazao;
   private javax.swing.JTable jtCompra;
   // End of variables declaration//GEN-END:variables
}
