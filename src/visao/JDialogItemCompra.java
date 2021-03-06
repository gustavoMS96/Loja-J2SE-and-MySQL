/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleCompra;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.ModeloCompra;
import modelo.ModeloItemCompra;
import modelo.ModeloProduto;



public class JDialogItemCompra extends javax.swing.JDialog {
   ConexaoBanco connProduto = new ConexaoBanco();
   ModeloCompra modCompra = new ModeloCompra();
   ModeloItemCompra modItem = new ModeloItemCompra();
   ModeloProduto modProduto = new ModeloProduto();
   ConexaoBanco connItem = new ConexaoBanco();
   ControleCompra controle = new ControleCompra();
   
   public static FrmCompra formCompra;
  
   public JDialogItemCompra(FrmCompra parent, boolean modal) {
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      modCompra.setItemCompra(modItem);
      modItem.setCompra(modCompra);
      modItem.setProduto(modProduto);
      
      this.formCompra = parent;
      this.setModal(modal);
     
      setLocationRelativeTo(this);
      initComponents();
      
      
      jcbFrete.removeAllItems();
      
      jcbFrete.addItem("");
      jcbFrete.addItem("FOB");
      jcbFrete.addItem("CIF");
      jcbFrete.addItem("Não possui");
   }
   
   public void pegaStatus(boolean botaoClicado){
      if (botaoClicado == true){
         jbConfirmar.setEnabled(false);
         jbAlterar.setEnabled(true);
         
      } else{
           jbConfirmar.setEnabled(!false);
           jbAlterar.setEnabled(!true);
        }
   }
   
   

   public void pegaCodigoItem(int codigo){
      jfCodigoItemI.setText(String.valueOf(codigo));
   
   }
   
  public void buscaCodigoItem(int codigoCompra){
      try{
         connItem.conexao();
         connItem.executaaSQL("SELECT MAX(CODIGO_ITEM) FROM ITEM_COMPRA where codigo_compra = '" + codigoCompra + "';");
         connItem.rs.last();
         if(!connItem.rs.last()){
            jfCodigoItemI.setText("1");
         
         } else{
              jfCodigoItemI.setText(String.valueOf(connItem.rs.getInt("MAX(CODIGO_ITEM)") + 1 ) );
         
           }
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, e); 
        }
   
  }
   
   
   public void carregaItem(String codigoItem){
      
      
      
      try{
         connItem.conexao();
         connItem.executaaSQL("SELECT I.CODIGO_PRODUTO, P.DESCRICAO, I.QUANTIDADE, I.VALOR_ITEM, I.VALOR_TOTAL_ITEM, I.FRETE FROM ITEM_COMPRA I JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO WHERE I.CODIGO_ITEM = '" + codigoItem + "';");
         connItem.rs.first();
         
         jfCodigoItemI.setText(String.valueOf(codigoItem));
         jfCodigoProduto.setText(String.valueOf(connItem.rs.getInt("CODIGO_PRODUTO")));
         jfDescricaoProduto.setText(connItem.rs.getString("DESCRICAO"));
         jfQuantidade.setText(String.valueOf(connItem.rs.getInt("QUANTIDADE")));
         jfValorUnitario.setText(String.valueOf(connItem.rs.getDouble("VALOR_ITEM")));
         jfValorTotal.setText(String.valueOf(connItem.rs.getDouble("VALOR_TOTAL_ITEM")));
         jcbFrete.setSelectedItem(connItem.rs.getString("FRETE"));
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Erro ao carregar dados do item\n" + e);
        }
     connItem.desconecta();
   }
   
   
   


   
   
   
   public void pegaCodigoCompra(String codigoCompra){
      //JOptionPane.showMessageDialog(null, codigoCompra);
      jfCodigoCompraI.setText(codigoCompra);
   }
   
   public void pegaFornecedor(String forn){
      jfFornecedorI.setText(forn);
   }
   
   public void pegaTipoCompra(String tipo){
      jfTipoCompraI.setText(tipo);
   }
   
   public void preencheProduto(){
      try{
         connProduto.conexao();
         connProduto.executaaSQL("SELECT * FROM PRODUTO WHERE CODIGO_PRODUTO = '" + Integer.parseInt(jfCodigoProduto.getText()) + "';");
         connProduto.rs.first();
         
         jfDescricaoProduto.setText(connProduto.rs.getString("DESCRICAO"));
         
        
      
      } catch(SQLException ex){
           JOptionPane.showMessageDialog(null, "FrmItemCompra\n\nErro ao carregar jcbProduto:\n" + ex);
        }
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
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      jfCodigoCompraI = new javax.swing.JTextField();
      jfTipoCompraI = new javax.swing.JTextField();
      jfFornecedorI = new javax.swing.JTextField();
      jfCodigoItemI = new javax.swing.JTextField();
      jLabel5 = new javax.swing.JLabel();
      jfCodigoProduto = new javax.swing.JTextField();
      jfDescricaoProduto = new javax.swing.JTextField();
      jbEscolherProduto = new javax.swing.JButton();
      jLabel6 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jfQuantidade = new javax.swing.JTextField();
      jfValorUnitario = new javax.swing.JTextField();
      jLabel8 = new javax.swing.JLabel();
      jfValorTotal = new javax.swing.JTextField();
      jLabel9 = new javax.swing.JLabel();
      jcbFrete = new javax.swing.JComboBox<>();
      jbConfirmar = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jbCancelar = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setResizable(false);

      jPanel1.setBackground(new java.awt.Color(153, 204, 255));
      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Código Compra:");

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Fornecedor:");

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Tipo Compra:");

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("Código item:");

      jfCodigoCompraI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoCompraI.setFocusable(false);

      jfTipoCompraI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfTipoCompraI.setFocusable(false);

      jfFornecedorI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfFornecedorI.setFocusable(false);

      jfCodigoItemI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoItemI.setFocusable(false);

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel1)
               .addComponent(jLabel2)
               .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jfFornecedorI)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                  .addComponent(jfTipoCompraI, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(jLabel4)
                  .addGap(18, 18, 18)
                  .addComponent(jfCodigoItemI, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jfCodigoCompraI, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jfCodigoCompraI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(jfFornecedorI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jfCodigoItemI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel4)
               .addComponent(jLabel3)
               .addComponent(jfTipoCompraI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Produto:");

      jfCodigoProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfCodigoProdutoFocusLost(evt);
         }
      });

      jfDescricaoProduto.setEditable(false);
      jfDescricaoProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbEscolherProduto.setText("+");

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel6.setText("Quantidade:");

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("Valor Unitário:");

      jfQuantidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfValorUnitario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfValorUnitario.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfValorUnitarioFocusLost(evt);
         }
      });
      jfValorUnitario.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jfValorUnitarioActionPerformed(evt);
         }
      });

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel8.setText("Valor Total:");

      jfValorTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfValorTotal.setFocusable(false);

      jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel9.setText("Frete:");

      jcbFrete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbConfirmar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbConfirmar.setText("Confirmar");
      jbConfirmar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbConfirmarActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jbCancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbCancelar.setText("Cancelar");
      jbCancelar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarActionPerformed(evt);
         }
      });

      jbAlterar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAlterar.setText("Alterar");
      jbAlterar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                     .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfQuantidade))
                     .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addGroup(layout.createSequentialGroup()
                              .addGap(18, 18, 18)
                              .addComponent(jLabel5)
                              .addGap(12, 12, 12))
                           .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                              .addComponent(jLabel9)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                           .addComponent(jfCodigoProduto)
                           .addComponent(jcbFrete, 0, 80, Short.MAX_VALUE))))
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbEscolherProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                     .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jbConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(46, 46, 46)
                  .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(61, 61, 61)
                  .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(48, 48, 48)
                  .addComponent(jbCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel5)
                  .addComponent(jfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jbEscolherProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jfQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel7)
                  .addComponent(jfValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel8)
                  .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jLabel6))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jcbFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jbConfirmar)
               .addComponent(jbLimpar)
               .addComponent(jbCancelar)
               .addComponent(jbAlterar))
            .addContainerGap())
      );

      setSize(new java.awt.Dimension(709, 356));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jfCodigoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCodigoProdutoFocusLost
      if(jfCodigoProduto.getText().trim().isEmpty()){
         return;
      } else{
      
            
           preencheProduto();
   
      
         }
   }//GEN-LAST:event_jfCodigoProdutoFocusLost

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      jfCodigoProduto.setText(null);
      jfDescricaoProduto.setText(null);
      jfValorTotal.setText(null);
      jfValorUnitario.setText(null);
      jfQuantidade.setText(null);
      jcbFrete.setSelectedItem(null);
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jbConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarActionPerformed
      if(jfCodigoProduto.getText().trim().isEmpty() || jfDescricaoProduto.getText().trim().isEmpty() || jfQuantidade.getText().trim().isEmpty() 
              || jfValorTotal.getText().trim().isEmpty() || jfValorUnitario.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
         jfCodigoProduto.grabFocus();
      
      }
      try{
         connProduto.conexao();
         connProduto.executaaSQL("SELECT CODIGO_PRODUTO FROM PRODUTO WHERE DESCRICAO = '" + jfDescricaoProduto.getText() + "';");
         connProduto.rs.first();
         
         modCompra.setCodigoCompra(Integer.parseInt(jfCodigoCompraI.getText()));
         modCompra.getItemCompra().getProduto().setCodigoProduto(connProduto.rs.getInt("CODIGO_PRODUTO"));
         modCompra.getItemCompra().setQuantidade(Integer.parseInt(jfQuantidade.getText()));
         modCompra.getItemCompra().setValorItem(Double.parseDouble(jfValorUnitario.getText()));
         modCompra.getItemCompra().setValorTotalItem(Double.parseDouble(jfValorTotal.getText()));
         modCompra.getItemCompra().setFrete((String) jcbFrete.getSelectedItem());
         modCompra.getItemCompra().setStatus("INDEFINIDO");
         modCompra.getItemCompra().setCodigoItem(Integer.parseInt(jfCodigoItemI.getText()));
         
         
      
            controle.salvarItem(modCompra);
        
            
      
         
      
      
      
      } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "JDialogItemCompra\nConfirmar item\nErro\n" + e);
        }
      
  
      
      
      dispose();
      
      formCompra.preencherTabela();
      
   }//GEN-LAST:event_jbConfirmarActionPerformed

   private void jfValorUnitarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfValorUnitarioFocusLost
      
      /*
      
      00.000,00
      replace('.','');
      00000,00
      replace(',','.');
      00000.00 decimal format
      */
      
      
      String campo = jfValorUnitario.getText();
      String replace = campo.replace(',','.');
      //JOptionPane.showMessageDialog(null, campo + " passou para " + replace);
      jfValorUnitario.setText(replace);
      
      
      double somaValorTotal = Integer.parseInt(jfQuantidade.getText().trim()) * Double.parseDouble(replace);
      jfValorTotal.setText(String.valueOf(somaValorTotal));
   }//GEN-LAST:event_jfValorUnitarioFocusLost

   
   
   
   private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
       if(jfCodigoProduto.getText().trim().isEmpty() || jfDescricaoProduto.getText().trim().isEmpty() || jfQuantidade.getText().trim().isEmpty() 
              || jfValorTotal.getText().trim().isEmpty() || jfValorUnitario.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
         jfCodigoProduto.grabFocus();
      
      }
      try{
         connProduto.conexao();
         connProduto.executaaSQL("SELECT CODIGO_PRODUTO FROM PRODUTO WHERE DESCRICAO = '" + jfDescricaoProduto.getText() + "';");
         connProduto.rs.first();
         
         modCompra.setCodigoCompra(Integer.parseInt(jfCodigoCompraI.getText()));
         modCompra.getItemCompra().getProduto().setCodigoProduto(connProduto.rs.getInt("CODIGO_PRODUTO"));
         modCompra.getItemCompra().setQuantidade(Integer.parseInt(jfQuantidade.getText()));
         modCompra.getItemCompra().setValorItem(Double.parseDouble(jfValorUnitario.getText()));
         modCompra.getItemCompra().setValorTotalItem(Double.parseDouble(jfValorTotal.getText()));
         modCompra.getItemCompra().setFrete((String) jcbFrete.getSelectedItem());
         modCompra.getItemCompra().setStatus("AGUARDANDO");
         modCompra.getItemCompra().setCodigoItem(Integer.parseInt(jfCodigoItemI.getText()));
         
         
            controle.alteraItem(modCompra);
          
       
             
      
      } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "JDialogItemCompra\nConfirmar item\nErro\n" + e);
        }
      
      
      
      
      dispose();
      
      formCompra.preencherTabela();                

   }//GEN-LAST:event_jbAlterarActionPerformed

   private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
      dispose();
   }//GEN-LAST:event_jbCancelarActionPerformed

   private void jfValorUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfValorUnitarioActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jfValorUnitarioActionPerformed

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
         java.util.logging.Logger.getLogger(JDialogItemCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(JDialogItemCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(JDialogItemCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(JDialogItemCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the dialog */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            JDialogItemCompra dialog = new JDialogItemCompra(formCompra, true);
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
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbCancelar;
   private javax.swing.JButton jbConfirmar;
   private javax.swing.JButton jbEscolherProduto;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JComboBox<String> jcbFrete;
   private javax.swing.JTextField jfCodigoCompraI;
   private javax.swing.JTextField jfCodigoItemI;
   private javax.swing.JTextField jfCodigoProduto;
   private javax.swing.JTextField jfDescricaoProduto;
   private javax.swing.JTextField jfFornecedorI;
   private javax.swing.JTextField jfQuantidade;
   private javax.swing.JTextField jfTipoCompraI;
   private javax.swing.JTextField jfValorTotal;
   private javax.swing.JTextField jfValorUnitario;
   // End of variables declaration//GEN-END:variables
}
