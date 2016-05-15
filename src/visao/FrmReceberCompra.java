/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleRecebimento;
import controle.ModeloTabela;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.ModeloCompra;
import modelo.ModeloRecebimento;

/**
 *
 * @author Gustavo M S
 */
public class FrmReceberCompra extends javax.swing.JFrame {

   ConexaoBanco connReceb = new ConexaoBanco();
   ModeloRecebimento modReceb = new ModeloRecebimento();
   ModeloCompra modCompra = new ModeloCompra();
   public static JDialogRecebEscolherCompra jdCompra;
   public static JDialogRecebimentosAbertos jdRecbAbertos;
   ControleRecebimento controle = new ControleRecebimento();
   
   public FrmReceberCompra() {
      super();
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      initComponents();
      
      modReceb.setCompra(modCompra);
      
      iniciaTabelaCompra();
      iniciaTabelaVencimento();
     
      
   }

   private void mostraJDialog(){
      jdCompra = new JDialogRecebEscolherCompra(this,true);
      jdCompra.setVisible(true);
   }
   
   
   public void defineCompra(String codigo){
      jfCompra.setText(codigo);
      
   }
   
   public void defineDadosRecebimento(String codigo, String data, String compra, String nf, String serie, String emissao){
      jfRecebimento.setText(codigo);
      jfData.setText(data);
      jfCompra.setText(compra);
      jfEmissao.setText(emissao);
      jfNF.setText(nf);
      jfSerie.setText(serie);
      
      jfNF.setEnabled(true);
      jfSerie.setEnabled(true);
      jbSalvar.setEnabled(true);
      jbLimpar.setEnabled(true);
      
      
   }
   
   private void habilitaCampos(){
      jfCompra.setEnabled(true);
      jfNF.setEnabled(true);
      jfSerie.setEnabled(true);
      jfEmissao.setEnabled(true);
      jbSalvar.setEnabled(true);
      jbNovo.setEnabled(false);
      jbProcurarCompra.setEnabled(true);
   }
   
   private void limparCampos(){
      jfCompra.setEnabled(!true);
      jfNF.setEnabled(!true);
      jfSerie.setEnabled(!true);
      jfEmissao.setEnabled(false);
      jbSalvar.setEnabled(!true);
      jbNovo.setEnabled(!false);
      jbAlterarRecebimento.setEnabled(false);
      
      apagaTabela();
      apagaVencimento();
      
      jfData.setText(null);
      jfCompra.setText(null);
      jfNF.setText(null);
      jfSerie.setText(null);
      jfEmissao.setText(null);
      jfRecebimento.setText(null);
      jfRecebimento.grabFocus();
      
      
   }
   
   
   
   private String dataBR(String d){
      String dataNormal = d.substring(8) + "/" + d.substring(5,7) + "/" + d.substring(0,4);
      return dataNormal;
   }
   
   private void iniciaTabelaCompra(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{"Prazo", "Vencimento"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtVencimento.setModel(modelo);
   
   }
   
   private void iniciaTabelaVencimento(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{"Produto", "Quant.", "Val. Uni.", "Val Tot.", "Frete"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtCompra.setModel(modelo);
   
   }
   
   
   private void preencherTabela(){
       
       ArrayList dados = new ArrayList();
       String [] Colunas = new String[]{"Produto", "Quant.", "Val. Uni.", "Val Tot.", "Frete"};
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT P.DESCRICAO, I.VALOR_ITEM, I.QUANTIDADE, I.VALOR_TOTAL_ITEM, I.FRETE " 
                  + "FROM ITEM_COMPRA I "
                  + "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO " 
                  + "WHERE I.CODIGO_COMPRA = '" + Integer.parseInt(jfCompra.getText()) + "'");
         
          connReceb.rs.first();
         
          if(connReceb.rs.first() == true){
         
              do{  
             
                 dados.add(new Object[]{
                    connReceb.rs.getString("DESCRICAO"), connReceb.rs.getDouble("VALOR_ITEM"), connReceb.rs.getInt("QUANTIDADE"),
                    connReceb.rs.getDouble("VALOR_TOTAL_ITEM"), connReceb.rs.getString("FRETE")
                 });
            
               
                 
                } while(connReceb.rs.next());
           } else{
                apagaTabela();
                return;
             }//if
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmReceberCompra\n\nErro ao carregar tabela com detalhes da compra recebida\n\n" + e);
         } 
       connReceb.desconecta();
       
       
       
       
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtCompra.setModel(modelo);
       jtCompra.getColumnModel().getColumn(0).setPreferredWidth(220);
       jtCompra.getColumnModel().getColumn(0).setResizable(!true);
       
     //  jtCompra.getColumnModel().getColumn(1).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(1).setResizable(!true);
       
    //   jtCompra.getColumnModel().getColumn(2).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(2).setResizable(!true);
       
    //   jtCompra.getColumnModel().getColumn(3).setPreferredWidth(50);
       jtCompra.getColumnModel().getColumn(3).setResizable(!true);
       
      // jtCompra.getColumnModel().getColumn(4).setPreferredWidth(80);
       jtCompra.getColumnModel().getColumn(4).setResizable(!true);
       
       
       
       jtCompra.getTableHeader().setReorderingAllowed(!true);
       jtCompra.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       
       
   }
   
   
   
   private void PreencherTabelaVencimento(int codigo){
           
       ArrayList dados = new ArrayList();
       
       String [] Colunas = new String[]{"Prazo", "Vencimento"};
       
       
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT DATA_VENCIMENTO, PRAZO FROM VENCIMENTO V JOIN COMPRA C ON V.CODIGO_COMPRA = C.CODIGO_COMPRA WHERE C.CODIGO_COMPRA = '" + codigo + "' ORDER BY V.PRAZO ASC");
         if( connReceb.rs.first() == true){
          
          do{
             dados.add(new Object[]{
                 
                connReceb.rs.getString("PRAZO"), dataBR(connReceb.rs.getString("DATA_VENCIMENTO"))
             });
          
          } while(connReceb.rs.next());
         } else{
              apagaVencimento();
           }
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
         }
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtVencimento.setModel(modelo);
      // jtVencimento.getColumnModel().getColumn(0).setPreferredWidth(80);
       jtVencimento.getColumnModel().getColumn(0).setResizable(!true);
       //jtVencimento.getColumnModel().getColumn(0).setPreferredWidth(80);
       jtVencimento.getColumnModel().getColumn(0).setResizable(!true);
       
     
       
             
       
       jtVencimento.getTableHeader().setReorderingAllowed(!true);
       jtVencimento.setAutoResizeMode(jtVencimento.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtVencimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       connReceb.desconecta();
    }
   
   
   
   
   
   
   private void apagaTabela(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtCompra.setModel(modelo);
      
   }
   
   private void apagaVencimento(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtVencimento.setModel(modelo);
   
   }
   
   
   
   
   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel4 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      jfRecebimento = new javax.swing.JTextField();
      jfCompra = new javax.swing.JTextField();
      jbProcurarCompra = new javax.swing.JButton();
      jbNovo = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jPanel1 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtCompra = new javax.swing.JTable();
      jbSalvar = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jLabel7 = new javax.swing.JLabel();
      jfNF = new javax.swing.JTextField();
      jLabel8 = new javax.swing.JLabel();
      jfSerie = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jfEmissao = new javax.swing.JTextField();
      jScrollPane3 = new javax.swing.JScrollPane();
      jtVencimento = new javax.swing.JTable();
      jfData = new javax.swing.JTextField();
      chamarRecebAbertos = new javax.swing.JButton();
      jbAlterarRecebimento = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setResizable(false);

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jLabel4.setText("Nº Receb:");

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jLabel5.setText("Compra:");

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jLabel6.setText("Data:");

      jfRecebimento.setEditable(false);
      jfRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfRecebimento.setFocusable(false);

      jfCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCompra.setEnabled(false);
      jfCompra.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfCompraFocusLost(evt);
         }
      });

      jbProcurarCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbProcurarCompra.setText("Procurar");
      jbProcurarCompra.setEnabled(false);
      jbProcurarCompra.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProcurarCompraActionPerformed(evt);
         }
      });

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jPanel1.setBackground(new java.awt.Color(204, 204, 204));
      jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

      jtCompra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
      jtCompra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
      jScrollPane1.setViewportView(jtCompra);

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
            .addContainerGap())
      );

      jbSalvar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbSalvar.setText("Confirmar Recebimento");
      jbSalvar.setEnabled(false);
      jbSalvar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSalvarActionPerformed(evt);
         }
      });

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Sair");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jPanel2.setBackground(new java.awt.Color(204, 204, 204));
      jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jLabel7.setText("Nota Fiscal:");

      jfNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfNF.setEnabled(false);

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jLabel8.setText("Série:");

      jfSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfSerie.setEnabled(false);

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Data Emissão:");

      jfEmissao.setEditable(false);
      jfEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEmissao.setEnabled(false);
      jfEmissao.setFocusable(false);

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addComponent(jLabel7)
            .addGap(18, 18, 18)
            .addComponent(jfNF, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(53, 53, 53)
            .addComponent(jLabel8)
            .addGap(18, 18, 18)
            .addComponent(jfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(83, 83, 83)
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addComponent(jfEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(217, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel7)
               .addComponent(jfNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel8)
               .addComponent(jfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel1)
               .addComponent(jfEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jtVencimento.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
      jtVencimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
      jtVencimento.setGridColor(new java.awt.Color(255, 255, 255));
      jScrollPane3.setViewportView(jtVencimento);

      jfData.setEditable(false);
      jfData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfData.setFocusable(false);

      chamarRecebAbertos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      chamarRecebAbertos.setText("Recebimentos Abertos");
      chamarRecebAbertos.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            chamarRecebAbertosActionPerformed(evt);
         }
      });

      jbAlterarRecebimento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbAlterarRecebimento.setText("Alterar Recebimento Aberto");
      jbAlterarRecebimento.setEnabled(false);
      jbAlterarRecebimento.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarRecebimentoActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addGroup(layout.createSequentialGroup()
                  .addGap(10, 10, 10)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 317, Short.MAX_VALUE))
                     .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addGroup(layout.createSequentialGroup()
                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                 .addComponent(jLabel4)
                                 .addComponent(jLabel5))
                              .addGap(18, 18, 18)
                              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                 .addGroup(layout.createSequentialGroup()
                                    .addComponent(jfCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jbProcurarCompra))
                                 .addComponent(jfRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                           .addGroup(layout.createSequentialGroup()
                              .addComponent(jLabel6)
                              .addGap(52, 52, 52)
                              .addComponent(jfData, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                           .addGroup(layout.createSequentialGroup()
                              .addComponent(jbSalvar)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                              .addComponent(jbAlterarRecebimento))
                           .addGroup(layout.createSequentialGroup()
                              .addComponent(jbNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                              .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addGap(16, 16, 16)
                              .addComponent(chamarRecebAbertos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                        .addComponent(jbSair))))
               .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(5, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGap(15, 15, 15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel4)
               .addComponent(jfRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbNovo)
               .addComponent(jbLimpar)
               .addComponent(chamarRecebAbertos)
               .addComponent(jbSair))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(layout.createSequentialGroup()
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                           .addComponent(jLabel5)
                           .addComponent(jfCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(jbProcurarCompra))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addComponent(jfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(jLabel6)))
                     .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addGap(35, 35, 35)
                  .addComponent(jbAlterarRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(31, 31, 31)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      setSize(new java.awt.Dimension(1145, 505));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jbProcurarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcurarCompraActionPerformed
      
      mostraJDialog();
      jbProcurarCompra.setEnabled(false);
      jfNF.grabFocus();
      jfEmissao.setText(controle.carregaDataEmissao(Integer.parseInt(jfCompra.getText())));
      preencherTabela();
      PreencherTabelaVencimento(Integer.parseInt(jfCompra.getText()));
   }//GEN-LAST:event_jbProcurarCompraActionPerformed

   
   
   
   
   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      dispose();
      FrmMenuPrincipal frm = new FrmMenuPrincipal();
      frm.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed
 
   Date date = new Date();
   SimpleDateFormat dBanco = new SimpleDateFormat("yyyy-MM-dd");
   SimpleDateFormat dCampo = new SimpleDateFormat("dd/MM/yyyy");
   
   String dataBanco = dBanco.format(date);
   String dataCampo = dCampo.format(date);
   
   private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
      
      jfData.setText(dataCampo);
      
      
      
      habilitaCampos();
      
      try{
         connReceb.conexao();
         connReceb.executaaSQL("SELECT MAX(CODIGO_RECEBIMENTO) FROM RECEBER_COMPRA");
         if(connReceb.rs.first()){
            jfRecebimento.setText(String.valueOf(connReceb.rs.getInt("MAX(CODIGO_RECEBIMENTO)") + 1));
         
         } else{
              jfRecebimento.setText("1");
           }
      
      } catch(SQLException e){
           JOptionPane.showMessageDialog(null, "FrmRecebimento\nErro ao carregar ultimo codigo de recebimento...\n" + e);
        }
      
      
      
      jfCompra.grabFocus();
      
      
      
      
   }//GEN-LAST:event_jbNovoActionPerformed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      limparCampos();
   }//GEN-LAST:event_jbLimparActionPerformed

   
   private String transformaData(String data){
      String dataBanco = data.substring(6) + "-" + data.substring(3,5) + "-" + data.substring(0,2);
      //JOptionPane.showMessageDialog(null, dataBanco);
      return dataBanco;
   }
   
   
   
   
   
   
   
   private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
      if(jfCompra.getText().trim().isEmpty() || jfNF.getText().trim().isEmpty() || jfSerie.getText().trim().isEmpty()){
         
         JOptionPane.showMessageDialog(null, "Preencha todos os campos....");
         jfCompra.grabFocus();
         
      } else{
           try{
              
              modReceb.setDataRecebimento(dataBanco);
             // //System.out.println(modReceb.getDataRecebimento());
              
              modReceb.getCompra().setCodigoCompra(Integer.parseInt(jfCompra.getText()));
        //      //System.out.println(modReceb.getCompra().getCodigoCompra());
              
              modReceb.setNotaFiscal(jfNF.getText());
           //   //System.out.println(modReceb.getNotaFiscal());
              
              modReceb.setSerie(jfSerie.getText());
           //   //System.out.println(modReceb.getSerie());
              
              modReceb.setDataEmissao(jfEmissao.getText());
          //    //System.out.println(modReceb.getDataEmissao());
              
          //    //System.out.println(modReceb);
              
              controle.salvaRecebimento(modReceb);
              
              String quantidade = String.valueOf(jtCompra.getRowCount());//numero de linhas
          //    //System.out.println("var quantidade = " + quantidade);
              
              int quantidadeLinhas = Integer.parseInt(quantidade);
           //   //System.out.println("var quantidadeLinhas = " + quantidadeLinhas); 
//JOptionPane.showMessageDialog(null, quantidadeLinhas);
              int posicaoLinha = 0;
              
              do{
              
              
              String produto = String.valueOf(jtCompra.getValueAt(posicaoLinha, 0));
           //      //System.out.println("col pro = " + produto);
              
              String quantidadeProduto = String.valueOf(jtCompra.getValueAt(posicaoLinha, 2));
           //      //System.out.println("col quantidade = " + quantidadeProduto); 
              
              int quantProduto = Integer.parseInt(quantidadeProduto);
              
              controle.lancarEstoque(quantProduto, produto);
              
              posicaoLinha += 1;
              }while(posicaoLinha < quantidadeLinhas);
              
              
              
           } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "FrmReceberCompra\nErro ao definir setters de recebimento\n" + ex.getMessage());
                ex.printStackTrace();
                ex.getCause();
             }
      
        }
      limparCampos();
      apagaTabela();
   }//GEN-LAST:event_jbSalvarActionPerformed

   private void jfCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfCompraFocusLost
      if(!jfCompra.getText().trim().isEmpty())
         preencherTabela();
   }//GEN-LAST:event_jfCompraFocusLost

   private void chamarRecebAbertosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chamarRecebAbertosActionPerformed
      jdRecbAbertos = new JDialogRecebimentosAbertos(this,true);
      jdRecbAbertos.preencheTabela();
      jdRecbAbertos.setVisible(true);
   }//GEN-LAST:event_chamarRecebAbertosActionPerformed

   public void liberaBotao(boolean s){
      if(s == true){
         jbAlterarRecebimento.setEnabled(true);
      } else{
            jbAlterarRecebimento.setEnabled(!true);
      }
   }
   
   
   private void jbAlterarRecebimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarRecebimentoActionPerformed
      try{
         modReceb.setCodigoRecebimento(Integer.parseInt(jfRecebimento.getText()));
         modReceb.setNotaFiscal(jfNF.getText());
         modReceb.setSerie(jfSerie.getText());
         controle.alterarRecebimentoAberto(modReceb);
      } 
      catch(Exception e){
         JOptionPane.showMessageDialog(null, "Classe: FrmReceberCompra\nAlterar Recebimento Aberto\nErro: " + e);
      }
      finally{
         limparCampos();
         jbAlterarRecebimento.setEnabled(false);
      }
   }//GEN-LAST:event_jbAlterarRecebimentoActionPerformed

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
         java.util.logging.Logger.getLogger(FrmReceberCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmReceberCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmReceberCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmReceberCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmReceberCompra().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton chamarRecebAbertos;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane3;
   private javax.swing.JButton jbAlterarRecebimento;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbProcurarCompra;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbSalvar;
   private javax.swing.JTextField jfCompra;
   private javax.swing.JTextField jfData;
   private javax.swing.JTextField jfEmissao;
   private javax.swing.JTextField jfNF;
   private javax.swing.JTextField jfRecebimento;
   private javax.swing.JTextField jfSerie;
   private javax.swing.JTable jtCompra;
   private javax.swing.JTable jtVencimento;
   // End of variables declaration//GEN-END:variables
}
