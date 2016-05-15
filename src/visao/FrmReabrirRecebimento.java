/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleReabrirRecebimento;
import controle.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class FrmReabrirRecebimento extends javax.swing.JFrame {
   ModeloRecebimento modReceb = new ModeloRecebimento();
   ModeloCompra modCompra = new ModeloCompra();
   ControleReabrirRecebimento controle = new ControleReabrirRecebimento();
   ConexaoBanco connCompra = new ConexaoBanco();
   ConexaoBanco connReceb = new ConexaoBanco();
   
   
   public FrmReabrirRecebimento() {
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      initComponents();
   }

   
   
   
   
   
   
   
   public void preencherTabela(){
       double valor_total = 0 ;    
       ArrayList dados = new ArrayList();
       
       try{
          connCompra.conexao();
          connCompra.executaaSQL("SELECT * FROM ITEM_COMPRA I "
                 + "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO "
                 + "JOIN COMPRA C ON I.CODIGO_COMPRA = C.CODIGO_COMPRA "
                 
                 + "WHERE I.CODIGO_COMPRA = '" + Integer.parseInt(jfCodigoCompra.getText()) + "'");// AND SITUACAO != 'CANCELADO'
         
          connCompra.rs.first();
         
          if(connCompra.rs.first() == true){
         
              do{  
             
                 dados.add(new Object[]{
                    
                    connCompra.rs.getString("DESCRICAO"),  
                    connCompra.rs.getInt("QUANTIDADE"), "R$ " + connCompra.rs.getDouble("VALOR_ITEM"), "R$ " +  
                    connCompra.rs.getDouble("VALOR_TOTAL_ITEM"), connCompra.rs.getString("FRETE"), 
                    connCompra.rs.getString("STATUS")
                 });//dados
            
                 if(!connCompra.rs.getString("STATUS").equals("CANCELADO")){
                     valor_total = valor_total + connCompra.rs.getDouble("VALOR_TOTAL_ITEM");
                 }
                 //if(jtCompra.getValueAt(, NORMAL))
                 
                } while(connCompra.rs.next());
           } else{
                apagaTabela();
                return;
             }//if
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmCompra\n\nErro ao carregar tabela com itens da compra\n\n" + e);
         } finally{
              connCompra.desconecta();
           }
       
       
       String [] Colunas = new String[]{"Produto", "Quant.", "Val. Uni.", "Val Tot.", "Frete", "Status"};
       
       
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtCompra.setModel(modelo);
       jtCompra.getColumnModel().getColumn(0).setPreferredWidth(200);
       jtCompra.getColumnModel().getColumn(0).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(1).setPreferredWidth(20);
       jtCompra.getColumnModel().getColumn(1).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(2).setPreferredWidth(30);
       jtCompra.getColumnModel().getColumn(2).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(3).setPreferredWidth(40);
       jtCompra.getColumnModel().getColumn(3).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(4).setPreferredWidth(60);
       jtCompra.getColumnModel().getColumn(4).setResizable(true);
       
       jtCompra.getColumnModel().getColumn(5).setPreferredWidth(80);
       jtCompra.getColumnModel().getColumn(5).setResizable(true);
       
       jtCompra.getTableHeader().setReorderingAllowed(!true);
       jtCompra.setAutoResizeMode(jtCompra.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       jfValorTotal.setText(String.valueOf(valor_total));
       
   }
   
   
   
   
   
   public void apagaTabela(){
         
       ArrayList dados = new ArrayList();
       
       
       String [] Colunas = new String[]{};
       
                
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
        jtCompra.setModel(modelo);
} 
   
    public void apagaVencimento(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtVencimento.setModel(modelo);
   
   }
   
   
   
   public void preencherTabelaVencimento(int codigo){
           
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
   
   
   
   public String dataBR(String d){
      String dataNormal = d.substring(8) + "/" + d.substring(5,7) + "/" + d.substring(0,4);
      return dataNormal;
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
      jfCodigoRecebimento = new javax.swing.JTextField();
      jbProcurar = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jLabel4 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      jfCodigoCompra = new javax.swing.JTextField();
      jfDataCompra = new javax.swing.JTextField();
      jScrollPane2 = new javax.swing.JScrollPane();
      jtCompra = new javax.swing.JTable();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtVencimento = new javax.swing.JTable();
      jLabel8 = new javax.swing.JLabel();
      jfValorTotal = new javax.swing.JTextField();
      jbPrimeiro = new javax.swing.JButton();
      jbAnterior = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbReabrirRecebimento = new javax.swing.JButton();
      jbCancelarRecebimento = new javax.swing.JButton();
      jbSair = new javax.swing.JButton();
      jPanel3 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jfDataRecebimento = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jfDataEmissao = new javax.swing.JTextField();
      jfNotaFiscal = new javax.swing.JTextField();
      jfSerie = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setResizable(false);

      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Numero Receb.:");

      jfCodigoRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbProcurar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbProcurar.setText("Procurar");
      jbProcurar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProcurarActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalhes da compra"));

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("Número Compra:");

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Data Compra:");

      jfCodigoCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfDataCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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
      jScrollPane2.setViewportView(jtCompra);

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
      jtVencimento.setRowHeight(25);
      jScrollPane1.setViewportView(jtVencimento);

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel8.setText("Valor Final:");

      jfValorTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel2Layout.createSequentialGroup()
                  .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGroup(jPanel2Layout.createSequentialGroup()
                  .addComponent(jLabel4)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jfCodigoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addComponent(jLabel5)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jfDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addComponent(jLabel8)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel4)
               .addComponent(jLabel5)
               .addComponent(jfCodigoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jfDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel8)
               .addComponent(jfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
               .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jbPrimeiro.setText("<<");
      jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPrimeiroActionPerformed(evt);
         }
      });

      jbAnterior.setText("<");
      jbAnterior.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAnteriorActionPerformed(evt);
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

      jbReabrirRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbReabrirRecebimento.setText("Reabrir Recebimento");
      jbReabrirRecebimento.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbReabrirRecebimentoActionPerformed(evt);
         }
      });

      jbCancelarRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbCancelarRecebimento.setText("Cancelar Recebimento");
      jbCancelarRecebimento.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarRecebimentoActionPerformed(evt);
         }
      });

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalhes Recebimento"));

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Data Receb.:");

      jfDataRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Nota Fiscal:");

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel6.setText("Série:");

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("Data Emissão NF:");

      jfDataEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfNotaFiscal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel2)
               .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jfDataRecebimento, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
               .addComponent(jfNotaFiscal))
            .addGap(18, 18, 18)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel7)
               .addComponent(jLabel6))
            .addGap(18, 18, 18)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jfDataEmissao, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
               .addComponent(jfSerie))
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(jfDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel7)
               .addComponent(jfDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel3)
               .addComponent(jfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel6)
               .addComponent(jfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addComponent(jbSair)
               .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addGap(18, 18, 18)
                  .addComponent(jfCodigoRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(46, 46, 46)
                  .addComponent(jbProcurar)
                  .addGap(44, 44, 44)
                  .addComponent(jbLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(66, 66, 66)
                  .addComponent(jbPrimeiro)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jbAnterior)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jbProximo)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jbUltimo))
               .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                  .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jbCancelarRecebimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(jbReabrirRecebimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
               .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jbPrimeiro)
                     .addComponent(jbAnterior)
                     .addComponent(jbProximo)
                     .addComponent(jbUltimo)))
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addGap(22, 22, 22)
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(jLabel1)
                     .addComponent(jfCodigoRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jbProcurar)
                     .addComponent(jbLimpar))))
            .addGap(23, 23, 23)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jbReabrirRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(16, 16, 16)
                  .addComponent(jbCancelarRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jbSair)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );

      setSize(new java.awt.Dimension(941, 603));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   
   
   private void recebeAtributos(){
      try{
         
        // modReceb.setCompra(modCompra);
         //JOptionPane.showMessageDialog(null,modReceb.getDataRecebimento() );
         jfDataRecebimento.setText(modReceb.getDataRecebimento());
         
         jfDataEmissao.setText(modReceb.getDataEmissao());
         jfNotaFiscal.setText(modReceb.getNotaFiscal());
         jfSerie.setText(modReceb.getSerie());
         jfCodigoCompra.setText("" + modReceb.getCompra().getCodigoCompra());
         jfDataCompra.setText(modReceb.getCompra().getDataCompra());
         jfValorTotal.setText("" + modReceb.getCompra().getValorTotal());
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "ReabrirRecebimento:\nrecebeAtributos()\n" + e.getMessage());
        }
   }
   
   
   
   private void jbProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcurarActionPerformed
      if(jfCodigoRecebimento.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Informe o número do recebimento!");
         jfCodigoRecebimento.grabFocus();
      } else{
           
               modReceb = controle.procuraRecebimento(Integer.parseInt(jfCodigoRecebimento.getText()));
               if(ControleReabrirRecebimento.c == 1){
               } else{
                   recebeAtributos();
                 }
      
        }
   }//GEN-LAST:event_jbProcurarActionPerformed

   private void jbReabrirRecebimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReabrirRecebimentoActionPerformed
      if(jfCodigoRecebimento.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Informe o numero do recebimento!");
         jfCodigoRecebimento.grabFocus();
      } else{
           
         if(ControleReabrirRecebimento.c == 1){
            limpar();
         } else{
            controle.reabrirRecebimento(Integer.parseInt(jfCodigoRecebimento.getText()), Integer.parseInt(jfCodigoCompra.getText()));
            limpar();
           }
      
      
        }
   }//GEN-LAST:event_jbReabrirRecebimentoActionPerformed

   
   public void limpar(){
      jfCodigoCompra.setText(null);
      jfCodigoRecebimento.setText(null);
      jfDataCompra.setText(null);
      jfDataEmissao.setText(null);
      jfDataRecebimento.setText(null);
      jfNotaFiscal.setText(null);
      jfSerie.setText(null);
      jfValorTotal.setText(null);
      
      apagaTabela();
      apagaVencimento();
   }
   
   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      limpar();
   }//GEN-LAST:event_jbLimparActionPerformed

   private void jbCancelarRecebimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarRecebimentoActionPerformed
      if(jfCodigoRecebimento.getText().isEmpty()){
         JOptionPane.showMessageDialog(null, "Informe o numero do recebimento!");
         jfCodigoRecebimento.grabFocus();
      } else{
           
         if(ControleReabrirRecebimento.c == 1){
            limpar();
         } else{
            controle.cancelarRecebimento(Integer.parseInt(jfCodigoRecebimento.getText()), Integer.parseInt(jfCodigoCompra.getText()));
            limpar();
           }
      
      
        }
   }//GEN-LAST:event_jbCancelarRecebimentoActionPerformed

   private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
      try{
         
         modReceb = controle.primeiro();
         carregaPesquisa();
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmReabrirRecebimento:\nprimeiro registro\n" + e);
        }
   }//GEN-LAST:event_jbPrimeiroActionPerformed

   private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed
      try{
         modReceb = controle.anterior();
         carregaPesquisa();
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmReabrirRecebimento:\nvoltar registro\n" + e);
        }
   }//GEN-LAST:event_jbAnteriorActionPerformed

   private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
       try{
         modReceb = controle.proximo();
         carregaPesquisa();
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmReabrirRecebimento:\nproximo registro\n" + e);
        }
   }//GEN-LAST:event_jbProximoActionPerformed

   private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
       try{
         modReceb = controle.ultimo();
         carregaPesquisa();
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmReabrirRecebimento:\nultimo registro\n" + e);
        }
   }//GEN-LAST:event_jbUltimoActionPerformed

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      dispose();
      FrmMenuPrincipal f = new FrmMenuPrincipal();
      f.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   public void carregaPesquisa(){
      
      //modReceb.setCompra(modCompra);
      
      jfCodigoRecebimento.setText("" + modReceb.getCodigoRecebimento());
      jfDataRecebimento.setText(modReceb.getDataRecebimento());
      jfDataEmissao.setText(modReceb.getDataEmissao());
      jfNotaFiscal.setText(modReceb.getNotaFiscal());
      jfSerie.setText(modReceb.getSerie());
      jfCodigoCompra.setText("" + modReceb.getCompra().getCodigoCompra());
      jfDataCompra.setText(modReceb.getCompra().getDataCompra());
      jfValorTotal.setText("" + modReceb.getCompra().getValorTotal());
      preencherTabela();
      preencherTabelaVencimento(Integer.parseInt(jfCodigoCompra.getText()));
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
         java.util.logging.Logger.getLogger(FrmReabrirRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmReabrirRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmReabrirRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmReabrirRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>
      //</editor-fold>
      //</editor-fold>
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmReabrirRecebimento().setVisible(true);
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
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JButton jbAnterior;
   private javax.swing.JButton jbCancelarRecebimento;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProcurar;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbReabrirRecebimento;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JTextField jfCodigoCompra;
   private javax.swing.JTextField jfCodigoRecebimento;
   private javax.swing.JTextField jfDataCompra;
   private javax.swing.JTextField jfDataEmissao;
   private javax.swing.JTextField jfDataRecebimento;
   private javax.swing.JTextField jfNotaFiscal;
   private javax.swing.JTextField jfSerie;
   private javax.swing.JTextField jfValorTotal;
   private javax.swing.JTable jtCompra;
   private javax.swing.JTable jtVencimento;
   // End of variables declaration//GEN-END:variables
}
