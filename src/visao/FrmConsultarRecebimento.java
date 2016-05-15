/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.lowagie.text.Font;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleConsultarRecebimento;
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
import modelo.ModeloFornecedor;
import modelo.ModeloFuncionario;
import modelo.ModeloItemCompra;
import modelo.ModeloRecebimento;

/**
 *
 * @author Gustavo M S
 */
public class FrmConsultarRecebimento extends javax.swing.JFrame {

    ConexaoBanco connReceb = new ConexaoBanco();
    ModeloRecebimento modReceb = new ModeloRecebimento();
    ModeloFornecedor modForn = new ModeloFornecedor();
    ModeloItemCompra modItemCompra = new ModeloItemCompra();
    ModeloCompra modCompra = new ModeloCompra();
    ModeloFuncionario modFunc = new ModeloFuncionario();
    ControleConsultarRecebimento controle = new ControleConsultarRecebimento();
    
    
   public FrmConsultarRecebimento() {
      FontUIResource font = new FontUIResource("Arial", Font.NORMAL, 14);
      UIManager.put("Table.font", font);
     //JTableHeader cabecalhoItens = jtItens.getTableHeader();
      //cabecalhoItens.setFont(font);
      
      
      
      try {
          UIManager.setLookAndFeel(new WindowsLookAndFeel());
       } catch (UnsupportedLookAndFeelException ex) {
          Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
       }
      initComponents();
      modReceb.setCompra(modCompra);
      modCompra.setFornecedor(modForn);
      modReceb.setFuncionario(modFunc);
      
      
      JTableHeader cabecalhoItens = jtItens.getTableHeader();
      cabecalhoItens.setFont(font);
      JTableHeader cabelcalhoVencimento = jtVencimento.getTableHeader();
      cabelcalhoVencimento.setFont(font);
   }

   public void defineAssociacao(){
     /* modReceb.setCompra(modCompra);
      modReceb.setFuncionario(modFunc);
      modCompra.setItemCompra(modItemCompra);
      modCompra.setFornecedor(modForn);
      
      ou
      
      modReceb.setCompra(modCompra);
      modCompra.setFornecedor(modForn);
      modReceb.setFuncionario(modFunc);
      */
   } 
   
   
   public void preencherTabela(){
       
       ArrayList dados = new ArrayList();
       
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT P.DESCRICAO, I.VALOR_ITEM, I.QUANTIDADE, I.VALOR_TOTAL_ITEM, I.FRETE "
                  + "FROM COMPRA C JOIN ITEM_COMPRA I ON C.CODIGO_COMPRA = I.CODIGO_COMPRA "
                  + "JOIN PRODUTO P ON P.CODIGO_PRODUTO = I.CODIGO_PRODUTO WHERE C.CODIGO_COMPRA = '" + Integer.parseInt(jfNumeroCompra.getText()) + "';");
         
          connReceb.rs.first();
         
          if(connReceb.rs.first() == true){
         
              do{  
             
                 dados.add(new Object[]{
                    
                    connReceb.rs.getString("DESCRICAO"), 
                    connReceb.rs.getDouble("VALOR_ITEM"),
                    connReceb.rs.getInt("QUANTIDADE"),
                    connReceb.rs.getDouble("VALOR_TOTAL_ITEM"),
                    connReceb.rs.getString("FRETE"),
                   
                                           
                    
                 });
            
                } while(connReceb.rs.next());
              
           } else{
                apagaTabelaItens();
                return;
             }//if
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\n\nErro ao carregar tabela com detalhes da compra recebida\n\n" + e);
         } finally{
              connReceb.desconecta();
           }
       
       
       String [] Colunas = new String[]{"Produto", "Val. Item", "Qtde", "Val. Total", "Frete"};
       
       
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtItens.setModel(modelo);
       jtItens.getColumnModel().getColumn(0).setPreferredWidth(250);
       jtItens.getColumnModel().getColumn(0).setResizable(true);
       
      // jtItens.getColumnModel().getColumn(1).setPreferredWidth(50);
       jtItens.getColumnModel().getColumn(1).setResizable(!true);
       
     //  jtItens.getColumnModel().getColumn(2).setPreferredWidth(30);
       jtItens.getColumnModel().getColumn(2).setResizable(!true);
       
     //  jtItens.getColumnModel().getColumn(3).setPreferredWidth(50);
       jtItens.getColumnModel().getColumn(3).setResizable(!true);
       
     //  jtItens.getColumnModel().getColumn(4).setPreferredWidth(20);
       jtItens.getColumnModel().getColumn(4).setResizable(!true);
      
      
      
     
       
       jtItens.getTableHeader().setReorderingAllowed(!true);
       jtItens.setAutoResizeMode(jtItens.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       
       preencherTabelaVencimento();
   }
   
   
   
   public void apagaTabelaVencimento(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      //jtItens.setModel(modelo);
      jtVencimento.setModel(modelo);
   }
   
    public void apagaTabelaItens(){
      ArrayList dados = new ArrayList();
      String [] Colunas = new String[]{};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtItens.setModel(modelo);
   }

   
   public void preencherTabelaVencimento(){
       
       ArrayList dados = new ArrayList();
       
       try{
          connReceb.conexao();
          connReceb.executaaSQL("SELECT V.PRAZO, V.DATA_VENCIMENTO " +
               "FROM VENCIMENTO V WHERE V.CODIGO_COMPRA = '" + Integer.parseInt(jfNumeroCompra.getText()) + "' ORDER BY PRAZO ASC;");
         
          connReceb.rs.first();
         
          if(connReceb.rs.first() == true){
         
              do{  
             
                 dados.add(new Object[]{
                    
                    connReceb.rs.getInt("PRAZO"),
                    dataBR(connReceb.rs.getString("DATA_VENCIMENTO"))
                   
                    
                    
                 });
            
               
                 
                } while(connReceb.rs.next());
           } else{
                apagaTabelaVencimento();
                return;
             }//if
       
       } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\n\nErro ao carregar tabela com detalhes da compra recebida\n\n" + e);
         } finally{
              connReceb.desconecta();
           }
       
       
       String [] Colunas = new String[]{"Prazo", "Data Vencimento"};
       
       
       
       ModeloTabela modelo = new ModeloTabela(dados, Colunas);
       
       jtVencimento.setModel(modelo);
       jtVencimento.getColumnModel().getColumn(0).setPreferredWidth(40);
       jtVencimento.getColumnModel().getColumn(0).setResizable(!true);
       
      ;
       jtVencimento.getColumnModel().getColumn(1).setResizable(!true);
       
      
       
     
       
       jtVencimento.getTableHeader().setReorderingAllowed(!true);
       jtVencimento.setAutoResizeMode(jtVencimento.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout
      
       jtVencimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
       
       
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
      jPanel2 = new javax.swing.JPanel();
      jLabel1 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      jLabel5 = new javax.swing.JLabel();
      jfNumeroRecebimento = new javax.swing.JTextField();
      jfFuncionarioRecebimento = new javax.swing.JTextField();
      jfDataRecebimento = new javax.swing.JFormattedTextField();
      jbProcurar = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbVoltar = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jPanel3 = new javax.swing.JPanel();
      jLabel6 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jLabel8 = new javax.swing.JLabel();
      jLabel9 = new javax.swing.JLabel();
      jLabel10 = new javax.swing.JLabel();
      jfRazaoSocial = new javax.swing.JTextField();
      jfCnpj = new javax.swing.JTextField();
      jfEndereco = new javax.swing.JTextField();
      jfCidade = new javax.swing.JTextField();
      jfEstado = new javax.swing.JTextField();
      jPanel4 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jLabel11 = new javax.swing.JLabel();
      jLabel12 = new javax.swing.JLabel();
      jLabel13 = new javax.swing.JLabel();
      jfNumeroCompra = new javax.swing.JTextField();
      jfNotaFiscal = new javax.swing.JTextField();
      jfSerie = new javax.swing.JTextField();
      jfDataCompra = new javax.swing.JTextField();
      jfDataEmissaoRecebimento = new javax.swing.JTextField();
      jPanel5 = new javax.swing.JPanel();
      jLabel16 = new javax.swing.JLabel();
      jLabel17 = new javax.swing.JLabel();
      jfValorCompra = new javax.swing.JTextField();
      jfTipoCompra = new javax.swing.JTextField();
      jScrollPane2 = new javax.swing.JScrollPane();
      jtVencimento = new javax.swing.JTable();
      jButton1 = new javax.swing.JButton();
      jScrollPane3 = new javax.swing.JScrollPane();
      jtItens = new javax.swing.JTable();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Consulta de Recebimento");
      setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      setResizable(false);

      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Recebimento"));

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Número Recebimento:");

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("Data Recebimento:");

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Funcionário:");

      jfNumeroRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfFuncionarioRecebimento.setEditable(false);
      jfFuncionarioRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfDataRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jbVoltar.setText("<");
      jbVoltar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbVoltarActionPerformed(evt);
         }
      });

      jbPrimeiro.setText("<<");
      jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPrimeiroActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel2Layout.createSequentialGroup()
                  .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jLabel1)
                     .addComponent(jLabel4)
                     .addComponent(jLabel5))
                  .addGap(18, 18, 18)
                  .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                           .addComponent(jfDataRecebimento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                           .addComponent(jfNumeroRecebimento, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                           .addComponent(jbProcurar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                           .addComponent(jbLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                     .addComponent(jfFuncionarioRecebimento)))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                  .addGap(0, 0, Short.MAX_VALUE)
                  .addComponent(jbPrimeiro)
                  .addGap(18, 18, 18)
                  .addComponent(jbVoltar)
                  .addGap(18, 18, 18)
                  .addComponent(jbProximo)
                  .addGap(18, 18, 18)
                  .addComponent(jbUltimo)))
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jfNumeroRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jbProcurar))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jbLimpar)
               .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jfDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel4)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel5)
               .addComponent(jfFuncionarioRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jbUltimo)
               .addComponent(jbProximo)
               .addComponent(jbVoltar)
               .addComponent(jbPrimeiro))
            .addContainerGap())
      );

      jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Fornecedor"));

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel6.setText("Razão Social:");

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("CNPJ:");

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel8.setText("Endereço:");

      jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel9.setText("Cidade:");

      jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel10.setText("Estado:");

      jfRazaoSocial.setEditable(false);
      jfRazaoSocial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfCnpj.setEditable(false);
      jfCnpj.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfEndereco.setEditable(false);
      jfEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfCidade.setEditable(false);
      jfCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfEstado.setEditable(false);
      jfEstado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel3Layout.createSequentialGroup()
                  .addComponent(jLabel6)
                  .addGap(18, 18, 18)
                  .addComponent(jfRazaoSocial))
               .addGroup(jPanel3Layout.createSequentialGroup()
                  .addGap(17, 17, 17)
                  .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addComponent(jLabel9)
                     .addComponent(jLabel8)
                     .addComponent(jLabel7))
                  .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                           .addGroup(jPanel3Layout.createSequentialGroup()
                              .addComponent(jfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addGap(18, 18, 18)
                              .addComponent(jLabel10)
                              .addGap(18, 18, 18)
                              .addComponent(jfEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                           .addComponent(jfEndereco)))
                     .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))))
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel6)
               .addComponent(jfRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel7)
               .addComponent(jfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel8)
               .addComponent(jfEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(7, 7, 7)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jfEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel10))
               .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jfCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel9)))
            .addContainerGap(36, Short.MAX_VALUE))
      );

      jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalhes do Recebimento"));

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Compra:");

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Data Compra:");

      jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel11.setText("Nota Fiscal:");

      jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel12.setText("Serie:");

      jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel13.setText("Emissao:");

      jfNumeroCompra.setEditable(false);
      jfNumeroCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfNotaFiscal.setEditable(false);
      jfNotaFiscal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfSerie.setEditable(false);
      jfSerie.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfDataCompra.setEditable(false);
      jfDataCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfDataEmissaoRecebimento.setEditable(false);
      jfDataEmissaoRecebimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
         jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel4Layout.createSequentialGroup()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel4Layout.createSequentialGroup()
                  .addGap(25, 25, 25)
                  .addComponent(jLabel2))
               .addGroup(jPanel4Layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(jLabel11))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(jLabel12)))
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                  .addComponent(jfNumeroCompra)
                  .addComponent(jfSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
               .addComponent(jfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(46, 46, 46)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addComponent(jLabel3)
               .addComponent(jLabel13))
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jfDataCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
               .addComponent(jfDataEmissaoRecebimento))
            .addContainerGap(31, Short.MAX_VALUE))
      );
      jPanel4Layout.setVerticalGroup(
         jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2)
               .addComponent(jLabel3)
               .addComponent(jfNumeroCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jfDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(4, 4, 4)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jfNotaFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel13)
               .addComponent(jfDataEmissaoRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel11))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel12)
               .addComponent(jfSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalhes da Compra"));

      jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel16.setText("Valor da Compra:");

      jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel17.setText("Tipo de Compra:");

      jfValorCompra.setEditable(false);
      jfValorCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jfTipoCompra.setEditable(false);
      jfTipoCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jtVencimento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
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
      jScrollPane2.setViewportView(jtVencimento);

      jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jButton1.setText("Voltar ao Menu");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
      jPanel5.setLayout(jPanel5Layout);
      jPanel5Layout.setHorizontalGroup(
         jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel5Layout.createSequentialGroup()
                  .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                     .addComponent(jLabel16)
                     .addComponent(jLabel17))
                  .addGap(18, 18, 18)
                  .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addComponent(jfValorCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                     .addComponent(jfTipoCompra)))
               .addComponent(jButton1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );
      jPanel5Layout.setVerticalGroup(
         jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel5Layout.createSequentialGroup()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
               .addGroup(jPanel5Layout.createSequentialGroup()
                  .addContainerGap()
                  .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(jLabel16)
                     .addComponent(jfValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jfTipoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(jLabel17))
                  .addGap(52, 52, 52)
                  .addComponent(jButton1))
               .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 4, Short.MAX_VALUE))
      );

      jtItens.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
      jtItens.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtItens.setGridColor(new java.awt.Color(255, 255, 255));
      jtItens.setRowHeight(25);
      jScrollPane3.setViewportView(jtItens);

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(8, 8, 8)
                  .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addGap(10, 10, 10)
                  .addComponent(jScrollPane3)))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap(16, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
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

      setSize(new java.awt.Dimension(1256, 585));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jbProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcurarActionPerformed
      
      if(jfNumeroRecebimento.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(null, "Você deve escolher um recebimento para pesquisar");
         jfNumeroRecebimento.grabFocus();
         
      } else{
           try{
      
              modReceb = controle.consultarRecebimento(Integer.parseInt(jfNumeroRecebimento.getText()));
              buscaAtributos();
              preencherTabela();
            } 
            catch(Exception e){
               JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nProcurar recebimento:\n" + e);
            }
      
      }
      
  
      
   }//GEN-LAST:event_jbProcurarActionPerformed

   private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
      
      try{
         modReceb = controle.primeiroRecebimento();
         buscaAtributos();
           preencherTabela();
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nErro ao buscar getters\n" + e);
         }
      
   }//GEN-LAST:event_jbPrimeiroActionPerformed

   private void jbVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVoltarActionPerformed
      try{
         modReceb = controle.voltarRecebimento();
         buscaAtributos();
           preencherTabela();
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nErro ao buscar getters\n" + e);
         }
      
   }//GEN-LAST:event_jbVoltarActionPerformed

   private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
      try{
         modReceb = controle.proximoRecebimento();
         buscaAtributos();
           preencherTabela();
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nErro ao buscar getters\n" + e);
         }
      
   }//GEN-LAST:event_jbProximoActionPerformed

   private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
      try{
         modReceb = controle.ultimoRecebimento();
         buscaAtributos();
         preencherTabela();
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nErro ao buscar getters\n" + e);
         }
      
   }//GEN-LAST:event_jbUltimoActionPerformed

   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      dispose();
      FrmMenuPrincipal voltaMenu = new FrmMenuPrincipal();
      voltaMenu.setVisible(true);
   }//GEN-LAST:event_jButton1ActionPerformed

   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      jfNumeroRecebimento.setText("");
         jfDataRecebimento.setText("");
         jfRazaoSocial.setText("");
         jfEndereco.setText("");
         jfCnpj.setText("");
         jfCidade.setText("");
         jfEstado.setText("");
         jfNumeroCompra.setText("");
         jfDataCompra.setText("");
         jfNotaFiscal.setText("");
         jfSerie.setText("");
         jfDataEmissaoRecebimento.setText("");
         jfValorCompra.setText("");
         jfTipoCompra.setText("");
         apagaTabelaItens();
         apagaTabelaVencimento();
   }//GEN-LAST:event_jbLimparActionPerformed

   
   
   public void buscaAtributos(){
     
      
      try{
     
         jfNumeroRecebimento.setText("" + modReceb.getCodigoRecebimento());
         jfDataRecebimento.setText(modReceb.getDataRecebimento());
         jfRazaoSocial.setText(modReceb.getCompra().getFornecedor().getRazaoSocial());
         jfEndereco.setText(modReceb.getCompra().getFornecedor().getEndereco() + " Nº " + modReceb.getCompra().getFornecedor().getNumeroEndereco());
         jfCnpj.setText(modReceb.getCompra().getFornecedor().getCnpj());
         jfCidade.setText(modReceb.getCompra().getFornecedor().getCidade());
         jfEstado.setText(modReceb.getCompra().getFornecedor().getEstado());
         jfNumeroCompra.setText(""+ modReceb.getCompra().getCodigoCompra());
         jfDataCompra.setText(modReceb.getCompra().getDataCompra());
         jfNotaFiscal.setText(modReceb.getNotaFiscal());
         jfSerie.setText(modReceb.getSerie());
         jfDataEmissaoRecebimento.setText(modReceb.getDataEmissao());
         jfValorCompra.setText("R$ " + modReceb.getCompra().getValorTotal());
         jfTipoCompra.setText(modReceb.getCompra().getTipoCompra());
      
      } catch(Exception e){
           JOptionPane.showMessageDialog(null, "FrmConsultarRecebimento\nErro ao buscar getters\n" + e.getMessage());
         }
   
   }
   
   
   
   
   
   public String dataBR(String d){//pego data do banco e transformo padrao brasileiro
      String dataNormal = d.substring(8,10) + "/" + d.substring(5,7) + "/" + d.substring(0,4);
      
      return dataNormal;
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
         java.util.logging.Logger.getLogger(FrmConsultarRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmConsultarRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmConsultarRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmConsultarRecebimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmConsultarRecebimento().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel16;
   private javax.swing.JLabel jLabel17;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JScrollPane jScrollPane3;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProcurar;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JButton jbVoltar;
   private javax.swing.JTextField jfCidade;
   private javax.swing.JTextField jfCnpj;
   private javax.swing.JTextField jfDataCompra;
   private javax.swing.JTextField jfDataEmissaoRecebimento;
   private javax.swing.JFormattedTextField jfDataRecebimento;
   private javax.swing.JTextField jfEndereco;
   private javax.swing.JTextField jfEstado;
   private javax.swing.JTextField jfFuncionarioRecebimento;
   private javax.swing.JTextField jfNotaFiscal;
   private javax.swing.JTextField jfNumeroCompra;
   private javax.swing.JTextField jfNumeroRecebimento;
   private javax.swing.JTextField jfRazaoSocial;
   private javax.swing.JTextField jfSerie;
   private javax.swing.JTextField jfTipoCompra;
   private javax.swing.JTextField jfValorCompra;
   private javax.swing.JTable jtItens;
   private javax.swing.JTable jtVencimento;
   // End of variables declaration//GEN-END:variables
}
