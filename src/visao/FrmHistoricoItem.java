/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ModeloTabela;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.ModeloCompra;

/**
 *
 * @author Gustavo M S
 */
public class FrmHistoricoItem extends javax.swing.JFrame {
   ConexaoBanco connC = new ConexaoBanco();
   ConexaoBanco connH = new ConexaoBanco();
   ModeloCompra modCompra = new ModeloCompra();
   ConexaoBanco connV = new ConexaoBanco();
   public FrmHistoricoItem() {
       try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
      }
       this.setTitle("Histórico de Compras do Item");
      initComponents();
      iniciarTabela();
      iniciarTabelaReceb();
   }

   private void iniciarTabela(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{"Data Compra", "Compra", "Fornecedor", "Qtde.", "Val. Item", "Val. Total"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtCompra.setModel(modelo);
    
   
   }
   
   private void iniciarTabelaReceb(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{"Data Receb.","Recebimento", "NF", "Emissão", "Qtde.", "Val. Item", "Val. Total", "Status"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtRecebimento.setModel(modelo);
      
   }
   
   
   public void preencherDados(){
       
      
      ArrayList dados = new ArrayList();
       
      try{
          
         connH.conexao();
         
         connH.executaaSQL("SELECT  P.DESCRICAO, C.DATA_COMPRA, C.CODIGO_COMPRA, F.RAZAO_SOCIAL, I.QUANTIDADE, I.VALOR_ITEM, I.VALOR_TOTAL_ITEM " +
               "FROM COMPRA C " +
               "JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR " +
               "JOIN ITEM_COMPRA I ON C.CODIGO_COMPRA = I.CODIGO_COMPRA " +
               "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO " +
               "WHERE P.CODIGO_PRODUTO = '" + jfCodigoProduto.getText() + "';");
         
         if(connH.rs.first()){
            jfDescricaoProduto.setText(connH.rs.getString("DESCRICAO"));
            //jfFrete.setText(connH.rs.getString("FRETE"));
            //jfDataEmissaoCompra.setText(dataBR(connH.rs.getString("DATA_EMISSAO")));
            do{
               dados.add(new Object[]{
                  dataBR(connH.rs.getString("DATA_COMPRA")), connH.rs.getInt("CODIGO_COMPRA"),
                  connH.rs.getString("RAZAO_SOCIAL"), connH.rs.getInt("QUANTIDADE"),
                  connH.rs.getDouble("VALOR_ITEM"), connH.rs.getDouble("VALOR_TOTAL_ITEM")
               });
            } while(connH.rs.next());
         } else{
              //JOptionPane.showMessageDialog(null, "Não existe compras para este produto.");
              return;
           }
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\n\nErro ao carregar tabela com detalhes da compra recebida\n\n" + e);
        } 
       
       
      String [] Colunas = new String[]{"Data Compra", "Compra", "Fornecedor", "Qtde.", "Val. Item", "Val. Total"};
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
       
       jtCompra.getTableHeader().setReorderingAllowed(!true);
       jtCompra.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       
   }
   
   
   
   private String dataBR(String data){
      String dataNova = data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
      return dataNova;
   }
  
   
   
   public String tiraHifenPrazo(String prazo){
      String novoPrazo = prazo;
      int t = novoPrazo.length();
      System.out.println(t);
      String prazoCerto;
      prazoCerto = novoPrazo.substring(0,t-1);
      System.out.println(prazoCerto);
      
      
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

      jPanel3 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      jPanel1 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtCompra = new javax.swing.JTable();
      jfFrete = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jfDataEmissaoCompra = new javax.swing.JTextField();
      jfCodigoProduto = new javax.swing.JTextField();
      jfPrazo = new javax.swing.JTextField();
      jfDescricaoProduto = new javax.swing.JTextField();
      jPanel2 = new javax.swing.JPanel();
      jScrollPane2 = new javax.swing.JScrollPane();
      jtRecebimento = new javax.swing.JTable();
      jbLimpar = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setResizable(false);

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Frete:");

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Data Emissão:");

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("Prazo Pagamento:");

      jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalhes Compra"));

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

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1)
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(268, 268, 268))
      );

      jfFrete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfFrete.setFocusable(false);

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Produto:");

      jfDataEmissaoCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfDataEmissaoCompra.setFocusable(false);

      jfCodigoProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfCodigoProdutoFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfCodigoProdutoFocusLost(evt);
         }
      });

      jfPrazo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfPrazo.setFocusable(false);

      jfDescricaoProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfDescricaoProduto.setFocusable(false);

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalhes Recebimento"));

      jtRecebimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
      jtRecebimento.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtRecebimento.setGridColor(new java.awt.Color(255, 255, 255));
      jtRecebimento.setRowHeight(25);
      jScrollPane2.setViewportView(jtRecebimento);

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2)
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
            .addContainerGap())
      );

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .addGroup(jPanel3Layout.createSequentialGroup()
                  .addGap(22, 22, 22)
                  .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jfDataEmissaoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                     .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbLimpar)
                        .addGap(23, 23, 23)
                        .addComponent(jbSair)))))
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbLimpar)
               .addComponent(jbSair))
            .addGap(29, 29, 29)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel4)
               .addComponent(jfPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel2)
               .addComponent(jfFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel3)
               .addComponent(jfDataEmissaoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(23, 23, 23)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      setSize(new java.awt.Dimension(999, 533));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jfCodigoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCodigoProdutoFocusLost
      if(jfCodigoProduto.getText().isEmpty()){
         
      } else{
          preencherDados();
        }
   }//GEN-LAST:event_jfCodigoProdutoFocusLost

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      jfCodigoProduto.setText(null);
      jfDescricaoProduto.setText(null);
      jfPrazo.setText(null);
      jfDataEmissaoCompra.setText(null);
      jfFrete.setText(null);
      
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtRecebimento.setModel(modelo);
      jtCompra.setModel(modelo);
      
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jtCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCompraMouseClicked
      
      String c = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 1));
      int codigoCompra = Integer.parseInt(c);
      ArrayList dados = new ArrayList();
      
      String prazo = "";
      
      try{
         
         connC.conexao();
         connC.executaaSQL("SELECT I.FRETE, V.DATA_EMISSAO FROM VENCIMENTO V JOIN ITEM_COMPRA I ON V.CODIGO_COMPRA = I.CODIGO_COMPRA WHERE V.CODIGO_COMPRA = '" + codigoCompra + "';");
         if(connC.rs.first()){
            jfDataEmissaoCompra.setText(dataBR(connC.rs.getString("DATA_EMISSAO")));
            jfFrete.setText(connC.rs.getString("FRETE"));
         } else{
              jfDataEmissaoCompra.setText(null);
              jfFrete.setText(null);
              jfPrazo.setText(null);
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
         
         
    
         connH.conexao();
         
         connH.executaaSQL("SELECT R.CODIGO_RECEBIMENTO, R.NOTA_FISCAL, R.DATA_EMISSAO, I.QUANTIDADE, I.VALOR_ITEM, I.VALOR_TOTAL_ITEM, R.DATA_RECEBIMENTO, R.STATUS\n" +
            "FROM RECEBER_COMPRA R " +
            "JOIN COMPRA C ON R.CODIGO_COMPRA = C.CODIGO_COMPRA " +
            "JOIN ITEM_COMPRA I ON C.CODIGO_COMPRA = I.CODIGO_COMPRA " +
            "WHERE C.CODIGO_COMPRA = '" + codigoCompra + "' AND I.CODIGO_PRODUTO = '" + jfCodigoProduto.getText() + "';");
         
         if(connH.rs.first()){
            
            
               dados.add(new Object[]{
                  dataBR(connH.rs.getString("DATA_RECEBIMENTO")), connH.rs.getInt("CODIGO_RECEBIMENTO"),
                  connH.rs.getString("NOTA_FISCAL"), dataBR(connH.rs.getString("DATA_EMISSAO")),
                  connH.rs.getInt("QUANTIDADE"), connH.rs.getDouble("VALOR_TOTAL_ITEM"),
                  connH.rs.getDouble("VALOR_TOTAL_ITEM"), connH.rs.getString("STATUS")
               });
            } else{
                  JOptionPane.showMessageDialog(null, "Não existe recebimento para esta compra.");
                  //ArrayList dados = new ArrayList();
                  String [] Colunas = new String[]{};
                  ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                  jtRecebimento.setModel(modelo);
                  return;
              }
         
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\n\nErro ao carregar tabela com detalhes do recebimento\n\n" + e);
        } 
       
       
      String [] Colunas = new String[]{"Data Receb.","Recebimento", "NF", "Emissão", "Qtde.", "Val. Item", "Val. Total", "Status"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtRecebimento.setModel(modelo);
      
      jtRecebimento.getColumnModel().getColumn(0).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(0).setResizable(true);
       
      jtRecebimento.getColumnModel().getColumn(1).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(1).setResizable(!true);
       
      jtRecebimento.getColumnModel().getColumn(2).setPreferredWidth(180);
      jtRecebimento.getColumnModel().getColumn(2).setResizable(!true);
       
      jtRecebimento.getColumnModel().getColumn(3).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(3).setResizable(!true);
       
      jtRecebimento.getColumnModel().getColumn(4).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(4).setResizable(!true);
      
      jtRecebimento.getColumnModel().getColumn(5).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(5).setResizable(!true);
       
      jtRecebimento.getColumnModel().getColumn(6).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(6).setResizable(!true);
       
      jtRecebimento.getColumnModel().getColumn(7).setPreferredWidth(50);
      jtRecebimento.getColumnModel().getColumn(7).setResizable(!true);
       
      jtRecebimento.getTableHeader().setReorderingAllowed(!true);
      jtRecebimento.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
      jtRecebimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
      
      
      
      connV.desconecta();
      connH.desconecta();
      connC.desconecta();
      
      
   }//GEN-LAST:event_jtCompraMouseClicked

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      dispose();
      FrmMenuPrincipal f = new FrmMenuPrincipal();
      f.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   private void jfCodigoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCodigoProdutoFocusGained
      // TODO add your handling code here:
   }//GEN-LAST:event_jfCodigoProdutoFocusGained

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
         java.util.logging.Logger.getLogger(FrmHistoricoItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmHistoricoItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmHistoricoItem().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbSair;
   private javax.swing.JTextField jfCodigoProduto;
   private javax.swing.JTextField jfDataEmissaoCompra;
   private javax.swing.JTextField jfDescricaoProduto;
   private javax.swing.JTextField jfFrete;
   private javax.swing.JTextField jfPrazo;
   private javax.swing.JTable jtCompra;
   private javax.swing.JTable jtRecebimento;
   // End of variables declaration//GEN-END:variables
}
