/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.lowagie.text.Font;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleAvaliacaoDeCompra;
import controle.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.JTableHeader;
import modelo.ModeloCompra;

public class FrmAvaliacaoDeCompra extends javax.swing.JFrame {

    ConexaoBanco connAvalia = new ConexaoBanco();
    ConexaoBanco connVencimento = new ConexaoBanco();
    ControleAvaliacaoDeCompra controle = new ControleAvaliacaoDeCompra();
    ModeloCompra modCompra = new ModeloCompra();
    
    
    public FrmAvaliacaoDeCompra() {
       FontUIResource font = new FontUIResource("Arial", Font.NORMAL, 14);
       UIManager.put("Table.font", font);
       //UIManager.put("Table.foreground", Color.RED);
       
       try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
       initComponents();
       jfCodigo.setDocument(new LimiteCaracterNumerico(6));
       
       
       JTableHeader cabecalhoCompra = jtCompra.getTableHeader();
       JTableHeader cabecalhoItens = jtItem.getTableHeader();
       JTableHeader cabecalhoVencimento = jtVencimento.getTableHeader();
       cabecalhoCompra.setFont(font);
       cabecalhoItens.setFont(font);
       cabecalhoVencimento.setFont(font);
      // PreencherTodaTabela();
      
      iniciaTabelaCompra();
      iniciaTabelaItem();
      iniciaTabelaVencimento();
      
    }
    
    private void iniciaTabelaCompra(){
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{"Compra", "Fornecedor", "Valor", "Data", "Tipo de Compra", "Status"};
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       jtCompra.setModel(modelo);
       
    }
    
    private void iniciaTabelaItem(){
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{"Produto", "Quantidade", "Val. Uni.", "Val. Tot.", "Frete", "Status"};
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       jtItem.setModel(modelo);
    }
    
    private void iniciaTabelaVencimento(){
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{"Prazo", "Vencimento"};
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       jtVencimento.setModel(modelo);
    }
    
    public void PreencherTodaTabela(){
           
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{"Compra", "Fornecedor", "Valor", "Data", "Tipo de Compra", "Status"};
       
       
       try{
          connAvalia.conexao();
          
          
          /**********
           SE CAMPO CODIGO ESTIVER PREENCHIDO BUSCARA PELO CODIGO, SENAO PREENCHERA COM TODOS OS REGISTROS
           ***********/
          
          if(jfCodigo.getText().trim().length() > 0){
             //JOptionPane.showMessageDialog(null, Integer.parseInt(jfCodigo.getText()));
             
             connAvalia.executaaSQL("SELECT * FROM COMPRA C "
               + "JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR WHERE C.CODIGO_COMPRA = '" + Integer.parseInt(jfCodigo.getText()) + "'" + " ORDER BY CODIGO_COMPRA;");
             connAvalia.rs.first();
             
             
          } else if(jfCodigo.getText().trim().isEmpty()){
               connAvalia.executaaSQL("SELECT * FROM COMPRA C "
               + "JOIN FORNECEDOR F ON C.CODIGO_FORNECEDOR = F.CODIGO_FORNECEDOR ORDER BY CODIGO_COMPRA;");
               connAvalia.rs.first();
            
            }
            
          
          
          
          do{
             dados.add(new Object[]{
                 
                connAvalia.rs.getInt("CODIGO_COMPRA"), connAvalia.rs.getString("RAZAO_SOCIAL"),
                "R$ " + connAvalia.rs.getDouble("VALOR_COMPRA"),dataPadraoBrasil(connAvalia.rs.getString("DATA")),
                
                connAvalia.rs.getString("TIPO_COMPRA"), connAvalia.rs.getString("SITUACAO")});
          
          } while(connAvalia.rs.next());
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       jtCompra.setModel(modelo);
      
       
       jtCompra.getTableHeader().setReorderingAllowed(false);
       jtCompra.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       connAvalia.desconecta();
    }
    
    
    public void PreencherTabelaVencimento(int codigo){
           
       ArrayList dados = new ArrayList();
       
       String [] Colunas = new String[]{"Prazo", "Vencimento"};
       
       
       try{
          connAvalia.conexao();
          connAvalia.executaaSQL("SELECT * FROM VENCIMENTO_COMPRA V JOIN COMPRA C ON V.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE C.CODIGO_COMPRA = '" + codigo + "' ORDER BY V.PRAZO ASC");
          connAvalia.rs.first();
          
          do{
             dados.add(new Object[]{
                 
                connAvalia.rs.getInt("PRAZO"), dataBR(connAvalia.rs.getString("DATA_VENCIMENTO"))
             });
          
          } while(connAvalia.rs.next());
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtVencimento.setModel(modelo);
       
     
       
             
       
       jtVencimento.getTableHeader().setReorderingAllowed(!true);
       jtVencimento.setAutoResizeMode(jtVencimento.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtVencimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       connAvalia.desconecta();
    }
    
    
    
    
    
    
    
    public String dataPadraoBrasil(String data){
       String dataBanco = data.substring(10,16) + " | " + data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(2,4);
       return dataBanco;
    }

    
    public String dataBR(String data){
       String novaData = data.substring(8) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
       return novaData;
    }
    
    
    
    public void PreencherTabelaItem(int codigo){
           
       ArrayList dados = new ArrayList();
       
       String [] Colunas = new String[]{"Produto", "Quantidade", "Val. Uni.", "Val. Tot.", "Frete", "Status"};
       
       
       try{
          connAvalia.conexao();
          connAvalia.executaaSQL("SELECT * FROM ITEM_COMPRA I "
                  + "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO WHERE I.CODIGO_COMPRA = '" + codigo + "'");
          connAvalia.rs.first();
          
          do{
             dados.add(new Object[]{
                 
                connAvalia.rs.getString("DESCRICAO"), connAvalia.rs.getInt("QUANTIDADE"),
                "R$ " + connAvalia.rs.getDouble("VALOR_ITEM"), "R$ " + connAvalia.rs.getDouble("VALOR_TOTAL_ITEM"), 
                connAvalia.rs.getString("FRETE"), connAvalia.rs.getString("STATUS")
             });
          
          } while(connAvalia.rs.next());
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtItem.setModel(modelo);
       
       
       jtItem.getTableHeader().setReorderingAllowed(!true);
       jtItem.setAutoResizeMode(jtItem.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       connAvalia.desconecta();
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
      jfCodigo = new javax.swing.JTextField();
      jbPesquisar = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtCompra = new javax.swing.JTable();
      jbLimpar = new javax.swing.JButton();
      jPanel3 = new javax.swing.JPanel();
      jScrollPane2 = new javax.swing.JScrollPane();
      jtItem = new javax.swing.JTable();
      jbReprovar = new javax.swing.JButton();
      jbReavaliar = new javax.swing.JButton();
      jbCancelar = new javax.swing.JButton();
      jbAprovar = new javax.swing.JButton();
      jButton1 = new javax.swing.JButton();
      jPanel4 = new javax.swing.JPanel();
      jScrollPane3 = new javax.swing.JScrollPane();
      jtVencimento = new javax.swing.JTable();
      jTextField1 = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jTextField2 = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setBackground(new java.awt.Color(255, 255, 255));
      setPreferredSize(new java.awt.Dimension(1240, 720));
      setResizable(false);

      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Nº da compra:");

      jfCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbPesquisar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbPesquisar.setText("Pesquisar");
      jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPesquisarActionPerformed(evt);
         }
      });

      jPanel2.setBackground(new java.awt.Color(53, 162, 242));
      jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jtCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtCompra.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtCompra.setAlignmentX(1.0F);
      jtCompra.setAlignmentY(1.0F);
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
            .addContainerGap())
      );

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jPanel3.setBackground(new java.awt.Color(53, 162, 242));
      jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jtItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtItem.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtItem.setAlignmentX(1.0F);
      jtItem.setAlignmentY(1.0F);
      jtItem.setGridColor(new java.awt.Color(255, 255, 255));
      jtItem.setRowHeight(25);
      jScrollPane2.setViewportView(jtItem);

      javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2)
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
            .addContainerGap())
      );

      jbReprovar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbReprovar.setText("Reprovar");
      jbReprovar.setEnabled(false);
      jbReprovar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbReprovarActionPerformed(evt);
         }
      });

      jbReavaliar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbReavaliar.setText("Reavaliar");
      jbReavaliar.setEnabled(false);
      jbReavaliar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbReavaliarActionPerformed(evt);
         }
      });

      jbCancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbCancelar.setText("Cancelar");
      jbCancelar.setEnabled(false);
      jbCancelar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarActionPerformed(evt);
         }
      });

      jbAprovar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAprovar.setText("Aprovar");
      jbAprovar.setEnabled(false);
      jbAprovar.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jbAprovarMouseClicked(evt);
         }
      });
      jbAprovar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAprovarActionPerformed(evt);
         }
      });

      jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jButton1.setText("Voltar ao Menu");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      jPanel4.setBackground(new java.awt.Color(53, 162, 242));
      jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jtVencimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtVencimento.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtVencimento.setFocusable(false);
      jtVencimento.setGridColor(new java.awt.Color(255, 255, 255));
      jtVencimento.setRowHeight(25);
      jtVencimento.setRowSelectionAllowed(false);
      jScrollPane3.setViewportView(jtVencimento);

      javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
         jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addContainerGap())
      );
      jPanel4Layout.setVerticalGroup(
         jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addContainerGap())
      );

      jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Fornecedor:");

      jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Data:");

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addComponent(jLabel1)
                     .addComponent(jLabel2))
                  .addGap(18, 18, 18)
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jbPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(jbLimpar))
                     .addComponent(jTextField1))
                  .addGap(42, 42, 42)
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jbAprovar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jbReavaliar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jbReprovar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                  .addComponent(jButton1))
               .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jbAprovar)
                  .addComponent(jbReprovar))
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                  .addComponent(jbLimpar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(jfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jbPesquisar)
                     .addComponent(jLabel1)
                     .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jLabel3))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jbReavaliar)
                  .addComponent(jbCancelar)
                  .addComponent(jButton1))
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel2)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(227, 227, 227))
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      setSize(new java.awt.Dimension(1256, 630));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
        jfCodigo.setText("");
        jbAprovar.setEnabled(false);
        jbReavaliar.setEnabled(false);
        jbReprovar.setEnabled(false);
        jbCancelar.setEnabled(false);
        apagaTabela();
        iniciaTabelaCompra();
        iniciaTabelaItem();
        iniciaTabelaVencimento();
        
    }//GEN-LAST:event_jbLimparActionPerformed

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
       PreencherTodaTabela();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCompraMouseClicked
       String codigo = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(),0));
       int cod = Integer.parseInt(codigo);
       
        PreencherTabelaItem(cod);
        PreencherTabelaVencimento(cod);
       try{
          connAvalia.conexao();
          connAvalia.executaaSQL("SELECT SITUACAO FROM COMPRA WHERE CODIGO_COMPRA = '" + cod + "'");
          connAvalia.rs.first();
          String situacao = connAvalia.rs.getString("SITUACAO");
          if(situacao.equals("AGUARDANDO")){
             jbAprovar.setEnabled(true);
             jbCancelar.setEnabled(true);
             jbReavaliar.setEnabled(true);
             jbReprovar.setEnabled(true);
          } else if(situacao.equals("CANCELADO")){
               jbAprovar.setEnabled(false);
               jbCancelar.setEnabled(false);
               jbReavaliar.setEnabled(false);
               jbReprovar.setEnabled(false);
            } else if(situacao.equals("REAVALIAR")){
                 jbAprovar.setEnabled(false);
                 jbCancelar.setEnabled(true);
                 jbReavaliar.setEnabled(false);
                 jbReprovar.setEnabled(false);
              } else if(situacao.equals("REPROVADO")){
                   jbAprovar.setEnabled(false);
                   jbCancelar.setEnabled(true);
                   jbReavaliar.setEnabled(false);
                   jbReprovar.setEnabled(false);
                } else if(situacao.equals("APROVADA")){
                     jbAprovar.setEnabled(false);
                     jbCancelar.setEnabled(true);
                     jbReavaliar.setEnabled(false);
                     jbReprovar.setEnabled(false);
                  }
       
       
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmAvaliacaoDeCompra\n\nErro ao carregar permissões de ação");
         } finally{
              connAvalia.desconecta();
           } 
        
    }//GEN-LAST:event_jtCompraMouseClicked

    private void jbAprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAprovarActionPerformed
       String cod = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 0));
       int codigo = Integer.parseInt(cod);
       
       //modCompra.setCodigo_compra(codigo);
       
       controle.aprovaCompra(/*modCompra,*/ codigo);
       PreencherTodaTabela();
       
    }//GEN-LAST:event_jbAprovarActionPerformed

    private void jbAprovarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAprovarMouseClicked
       /*String cod = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 0));
       
       int codigo = Integer.parseInt(cod);
       
       modCompra.setCodigo_compra(codigo);
       
       controle.aprovaCompra(modCompra, codigo);*/
    }//GEN-LAST:event_jbAprovarMouseClicked

    private void jbReprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReprovarActionPerformed
       
       String obs = JOptionPane.showInputDialog(null, "Motivo da Reprovação"); 
        
       String cod = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 0));
       int codigo = Integer.parseInt(cod);
       
     //  modCompra.setObservacao(obs);
     //  modCompra.setCodigo_compra(codigo);
       
       controle.reprovaCompra( codigo, obs);
       PreencherTodaTabela();
    }//GEN-LAST:event_jbReprovarActionPerformed

    private void jbReavaliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReavaliarActionPerformed
       String cod = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 0));
       int codigo = Integer.parseInt(cod);
       
       String obs = JOptionPane.showInputDialog(null, "Motivo da Reprovação"); 
        
       
       
       
       modCompra.setCodigoCompra(codigo);
       
       controle.reavaliarCompra(modCompra, codigo, obs);
       PreencherTodaTabela();
    }//GEN-LAST:event_jbReavaliarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
       String cod = String.valueOf(jtCompra.getValueAt(jtCompra.getSelectedRow(), 0));
       int codigo = Integer.parseInt(cod);
       
       modCompra.setCodigoCompra(codigo);
       
       controle.cancelaCompra(modCompra, codigo);
       PreencherTodaTabela();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
        frmMenu.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    public void apagaTabela(){
         
       ArrayList dados = new ArrayList();
       
       
       String [] Colunas = new String[]{};
       
                
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        jtCompra.setModel(modelo);
        jtItem.setModel(modelo);
        jtVencimento.setModel(modelo);
   }
    
    
    
    
    
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
            java.util.logging.Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAvaliacaoDeCompra().setVisible(true);
            }
        });
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JScrollPane jScrollPane3;
   private javax.swing.JTextField jTextField1;
   private javax.swing.JTextField jTextField2;
   private javax.swing.JButton jbAprovar;
   private javax.swing.JButton jbCancelar;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbPesquisar;
   private javax.swing.JButton jbReavaliar;
   private javax.swing.JButton jbReprovar;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTable jtCompra;
   private javax.swing.JTable jtItem;
   private javax.swing.JTable jtVencimento;
   // End of variables declaration//GEN-END:variables
}
