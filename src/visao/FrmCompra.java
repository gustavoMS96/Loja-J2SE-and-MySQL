/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import com.lowagie.text.Font;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleCompra;
import controle.ModeloTabela;
import java.awt.Color;
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
import javax.swing.plaf.FontUIResource;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.ModeloCompra;
import modelo.ModeloFornecedor;
import modelo.ModeloFuncionario;
import modelo.ModeloItemCompra;

/**
 *
 * @author Gustavo
 */
public class FrmCompra extends javax.swing.JFrame {

   ConexaoBanco connItem = new ConexaoBanco();
   ConexaoBanco connCompra = new ConexaoBanco();
   ConexaoBanco connFornecedor = new ConexaoBanco();
   ModeloItemCompra modItem = new ModeloItemCompra();
   ModeloCompra modCompra = new ModeloCompra();
   ModeloFuncionario modFunc = new ModeloFuncionario();
   ModeloFornecedor modForn = new ModeloFornecedor();

   ControleCompra controle = new ControleCompra();

   public static JDialogItemCompra jdItem;

   public FrmCompra() {
      super();

      FontUIResource font = new FontUIResource("Arial", Font.NORMAL, 14);
      UIManager.put("Table.font", font);

      try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
      }
      //super();
      initComponents();
      getContentPane().setBackground(Color.decode("#EEE9E9"));//modificação da cor de fundo do formulario
      preencheTipoCompra();
      preencheFornecedor();
      //carregaSituacao();
      modCompra.setFornecedor(modForn);
      modCompra.setFuncionario(modFunc);

      iniciaTabela();

      try {
         MaskFormatter data = new MaskFormatter("##/##/####");
         jfDataEmissao.setFormatterFactory(new DefaultFormatterFactory(data));

      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "FrmCompra\n\nErro ao definir mascara data de Emissao:\n" + e);
      }

      JTableHeader cabecalhoCompra = jtItens.getTableHeader();
      cabecalhoCompra.setFont(font);

   }

   private void iniciaTabela() {
      ArrayList dados = new ArrayList();
      String[] Colunas = new String[]{"Código", "Produto", "Quant.", "Val. Uni.", "Val Tot.", "Frete", "Status"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtItens.setModel(modelo);
   }

   /*
   implementar funcionalidade de saber qual o funcionario logado e atribuir as ações dele no banco
   
   public void pegaFuncionario(String nome) {
      jfFuncionario.setText(nome);
   }

   
    */
   public void mostraJDialog() {
      jdItem = new JDialogItemCompra(this, true);
      //jdItem.mudaFlag(1);
      jdItem.pegaCodigoCompra(jfCodigo.getText());
      jdItem.pegaFornecedor((String) jcbFornecedor.getSelectedItem());
      jdItem.pegaTipoCompra((String) jcbTipoCompra.getSelectedItem());
      jdItem.buscaCodigoItem(Integer.parseInt(jfCodigo.getText()));
      jdItem.pegaStatus(false);
      jdItem.setVisible(true);

   }

   public void mostraJDialogAlterar() {
      jdItem = new JDialogItemCompra(this, true);
      jdItem.pegaStatus(true);
      //jdItem.mudaFlag(2);
      //jdItem.carregaItem(Integer.parseInt(String.valueOf(jtItens.getValueAt(jtItens.getSelectedRow(), jtItens.getSelectedColumn()))));
      jdItem.pegaCodigoCompra(jfCodigo.getText());
      jdItem.pegaFornecedor((String) jcbFornecedor.getSelectedItem());
      jdItem.pegaTipoCompra((String) jcbTipoCompra.getSelectedItem());
      jdItem.pegaCodigoItem(Integer.parseInt(String.valueOf(jtItens.getValueAt(jtItens.getSelectedRow(), 0))));
      jdItem.carregaItem(String.valueOf(jtItens.getValueAt(jtItens.getSelectedRow(), 0)));
      //jdItem.buscaCodigoItem();
      jdItem.setVisible(true);

   }

   /**
    * *********
    * carregaSituacao() ao clicar em 'confirmar compra' ou 'cancelar compra',
    * carrego seu status para o canto da tela, *********
    */
   public void carregaSituacao() {

      if (jfCodigo.getText().trim().isEmpty()) {
         return;

      } else {

         try {
            connCompra.conexao();
            connCompra.executaaSQL("SELECT SITUACAO FROM COMPRA WHERE CODIGO_COMPRA = '" + Integer.parseInt(jfCodigo.getText()) + "';");

            if (!connCompra.rs.first()) {
               connCompra.desconecta();
            } else {
               jfStatus.setText(connCompra.rs.getString("SITUACAO"));
               if (String.valueOf(connCompra.rs.getString("SITUACAO")).equals("REAVALIAR") || String.valueOf(connCompra.rs.getString("SITUACAO")).equals("REPROVADO")) {
                  jbObservacao.setEnabled(true);
               } else {
                  jbObservacao.setEnabled(false);
               }

               if (String.valueOf(connCompra.rs.getString("SITUACAO")).equals("REAVALIAR")) {
                  jbConfirmarCompra.setEnabled(true);
                  jbCancelarCompra.setEnabled(true);
                  jbNovoItem.setEnabled(true);
                  jbAlterarItem.setEnabled(!true);
                  jbCancelarItem.setEnabled(!true);
               } else if (String.valueOf(connCompra.rs.getString("SITUACAO")).equals("ENTREGUE")) {
                  jbConfirmarCompra.setEnabled(!true);
                  jbCancelarCompra.setEnabled(!true);
                  jbNovoItem.setEnabled(!true);
                  jbAlterarItem.setEnabled(!true);
                  jbCancelarItem.setEnabled(!true);

               } else if (String.valueOf(connCompra.rs.getString("SITUACAO")).equals("CANCELADO")) {
                  jbConfirmarCompra.setEnabled(!true);
                  jbCancelarCompra.setEnabled(!true);
                  jbNovoItem.setEnabled(!true);
                  jbAlterarItem.setEnabled(!true);
                  jbCancelarItem.setEnabled(!true);
               } else if (String.valueOf(connCompra.rs.getString("SITUACAO")).equals("REPROVADO")) {
                  jbConfirmarCompra.setEnabled(!true);
                  jbCancelarCompra.setEnabled(!true);
                  jbNovoItem.setEnabled(!true);
                  jbAlterarItem.setEnabled(!true);
                  jbCancelarItem.setEnabled(!true);
               }
            }

         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "FrmCompra\n\nErro ao carregar situação da compra\n\n" + ex);
         } finally {
            connCompra.desconecta();
         }
      }
   }

   public void preencheTipoCompra() {//acrescento itens para a comboBox 'Tipo de Compra'
      jcbTipoCompra.addItem("");
      jcbTipoCompra.addItem("REPOSICAO");
      jcbTipoCompra.addItem("PRODUTO NOVO");
   }

   public void preencheFornecedor() {//acrescento dados para comboBox 'Fornecedor'

      try {
         connFornecedor.conexao();

         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR ORDER BY RAZAO_SOCIAL");
         connFornecedor.rs.first();
         jcbFornecedor.addItem("");

         do {
            jcbFornecedor.addItem(connFornecedor.rs.getString("RAZAO_SOCIAL"));
         } while (connFornecedor.rs.next());
      } catch (SQLException erro) {
         JOptionPane.showMessageDialog(null, "FrmCompra\n\nErro ao carregar jcbFornecedor:\n" + erro);
      } finally {
         connFornecedor.desconecta();
      }

   }

   /**
    * ***************
    * preencherTabela() MODIFICAR ITENS EXIBIDOS NELA
    *
    *****************
    */
   public void preencherTabela() {
      double valor_total = 0;
      ArrayList dados = new ArrayList();

      try {
         connCompra.conexao();
         connCompra.executaaSQL("SELECT * FROM ITEM_COMPRA I "
                 + "JOIN PRODUTO P ON I.CODIGO_PRODUTO = P.CODIGO_PRODUTO "
                 + "JOIN COMPRA C ON I.CODIGO_COMPRA = C.CODIGO_COMPRA "
                 + "WHERE I.CODIGO_COMPRA = '" + Integer.parseInt(jfCodigo.getText()) + "'");// AND SITUACAO != 'CANCELADO'

         connCompra.rs.first();

         if (connCompra.rs.first() == true) {

            do {

               dados.add(new Object[]{
                  connCompra.rs.getInt("CODIGO_ITEM"),
                  connCompra.rs.getString("DESCRICAO"),
                  connCompra.rs.getInt("QUANTIDADE"), "R$ " + connCompra.rs.getDouble("VALOR_ITEM"), "R$ "
                  + connCompra.rs.getDouble("VALOR_TOTAL_ITEM"), connCompra.rs.getString("FRETE"),
                  connCompra.rs.getString("STATUS")
               });//dados

               if (!connCompra.rs.getString("STATUS").equals("CANCELADO")) {
                  valor_total = valor_total + connCompra.rs.getDouble("VALOR_TOTAL_ITEM");
               }
               //if(jtItens.getValueAt(, NORMAL))

            } while (connCompra.rs.next());
         } else {
            apagaTabela();
            return;
         }//if

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "FrmCompra\n\nErro ao carregar tabela com itens da compra\n\n" + e);
      } finally {
         connCompra.desconecta();
      }

      String[] Colunas = new String[]{"Código", "Produto", "Quant.", "Val. Uni.", "Val Tot.", "Frete", "Status"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtItens.setModel(modelo);

      jtItens.getTableHeader().setReorderingAllowed(true);
      jtItens.setAutoResizeMode(jtItens.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout

      jtItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      jfValorTotal.setText(String.valueOf(valor_total));

   }

   //busca o fornecedor do item
   public void carregaFornecedor(String f) {
      jcbFornecedor.setSelectedItem(f);

      try {
         connFornecedor.conexao();
         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR WHERE RAZAO_SOCIAL = '" + jcbFornecedor.getSelectedItem() + "'");
         connFornecedor.rs.first();

         jfCodigoFornecedor.setText("" + connFornecedor.rs.getInt("CODIGO_FORNECEDOR"));
         jfCNPJ.setText(connFornecedor.rs.getString("CNPJ"));
         jfCidadeUF.setText(connFornecedor.rs.getString("EMAIL"));

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e);
      }
   }

   public void carregaTipoCompra(String tipo) {
      jcbTipoCompra.setSelectedItem(tipo);
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
      jLabel3 = new javax.swing.JLabel();
      jfFuncionario = new javax.swing.JTextField();
      jfStatus = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      jcbTipoCompra = new javax.swing.JComboBox();
      jbPimeiro = new javax.swing.JButton();
      jbVoltar = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jbNovo = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jLabel7 = new javax.swing.JLabel();
      jfValorTotal = new javax.swing.JTextField();
      jbConfirmarCompra = new javax.swing.JButton();
      jLabel8 = new javax.swing.JLabel();
      jfData = new javax.swing.JTextField();
      jbSair = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jScrollPane2 = new javax.swing.JScrollPane();
      jtItens = new javax.swing.JTable();
      jbNovoItem = new javax.swing.JButton();
      jbAlterarItem = new javax.swing.JButton();
      jbCancelarItem = new javax.swing.JButton();
      jButton1 = new javax.swing.JButton();
      jPanel3 = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jcbFornecedor = new javax.swing.JComboBox();
      jLabel4 = new javax.swing.JLabel();
      jfCNPJ = new javax.swing.JTextField();
      jfCodigoFornecedor = new javax.swing.JTextField();
      jfCidadeUF = new javax.swing.JTextField();
      jLabel5 = new javax.swing.JLabel();
      jfEndereco = new javax.swing.JTextField();
      jLabel12 = new javax.swing.JLabel();
      jbCancelarCompra = new javax.swing.JButton();
      jbSalvaCompra = new javax.swing.JButton();
      jbProcurarCompra = new javax.swing.JButton();
      jbObservacao = new javax.swing.JButton();
      jLabel10 = new javax.swing.JLabel();
      jfDataEmissao = new javax.swing.JFormattedTextField();
      jLabel11 = new javax.swing.JLabel();
      jfPrazoPagamento = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Compra");
      setBackground(new java.awt.Color(51, 153, 255));
      setForeground(new java.awt.Color(51, 153, 255));
      setName("FrmCompra"); // NOI18N
      setResizable(false);

      jPanel1.setBackground(new java.awt.Color(229, 229, 233));
      jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Compra");

      jfCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Funcionario");

      jfFuncionario.setEditable(false);
      jfFuncionario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfFuncionario.setFocusable(false);

      jfStatus.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
      jfStatus.setForeground(new java.awt.Color(255, 0, 0));
      jfStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jfStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

      jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel6.setText("Tipo Compra");

      jcbTipoCompra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jbPimeiro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbPimeiro.setText("<<");
      jbPimeiro.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbPimeiroActionPerformed(evt);
         }
      });

      jbVoltar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbVoltar.setText("<");
      jbVoltar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbVoltarActionPerformed(evt);
         }
      });

      jbProximo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbProximo.setText(">");
      jbProximo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProximoActionPerformed(evt);
         }
      });

      jbUltimo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbUltimo.setText(">>");
      jbUltimo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbUltimoActionPerformed(evt);
         }
      });

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("Valor Total");

      jfValorTotal.setEditable(false);
      jfValorTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jfValorTotal.setForeground(new java.awt.Color(0, 51, 255));
      jfValorTotal.setFocusable(false);

      jbConfirmarCompra.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbConfirmarCompra.setText("Confirmar Compra");
      jbConfirmarCompra.setEnabled(false);
      jbConfirmarCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
      jbConfirmarCompra.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
      jbConfirmarCompra.setIconTextGap(10);
      jbConfirmarCompra.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbConfirmarCompraActionPerformed(evt);
         }
      });

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel8.setText("Data");

      jfData.setEditable(false);
      jfData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfData.setFocusable(false);

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jPanel2.setBackground(new java.awt.Color(92, 139, 247));
      jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jtItens.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
      jtItens.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jtItensMouseClicked(evt);
         }
      });
      jScrollPane2.setViewportView(jtItens);

      jbNovoItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbNovoItem.setText("Novo Item");
      jbNovoItem.setEnabled(false);
      jbNovoItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
      jbNovoItem.setIconTextGap(10);
      jbNovoItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoItemActionPerformed(evt);
         }
      });

      jbAlterarItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbAlterarItem.setText("Alterar Item");
      jbAlterarItem.setEnabled(false);
      jbAlterarItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
      jbAlterarItem.setIconTextGap(10);
      jbAlterarItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbAlterarItemActionPerformed(evt);
         }
      });

      jbCancelarItem.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbCancelarItem.setText("Cancelar Item");
      jbCancelarItem.setEnabled(false);
      jbCancelarItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
      jbCancelarItem.setIconTextGap(10);
      jbCancelarItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarItemActionPerformed(evt);
         }
      });

      jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jButton1.setText("Imprimir Compra");

      org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jScrollPane2)
               .add(jPanel2Layout.createSequentialGroup()
                  .add(jbNovoItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jbAlterarItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jbCancelarItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 173, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jbNovoItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jbCancelarItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jbAlterarItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 197, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
      );

      jPanel3.setBackground(new java.awt.Color(219, 219, 239));
      jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Fornecedor:");

      jcbFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jcbFornecedorFocusLost(evt);
         }
      });

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("CNPJ:");

      jfCNPJ.setEditable(false);
      jfCNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCNPJ.setFocusable(false);

      jfCodigoFornecedor.setEditable(false);
      jfCodigoFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCodigoFornecedor.setFocusable(false);

      jfCidadeUF.setEditable(false);
      jfCidadeUF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfCidadeUF.setFocusable(false);

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Endereço:");

      jfEndereco.setEditable(false);
      jfEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEndereco.setFocusable(false);

      jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel12.setText("Cidade/UF:");

      org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
               .add(jLabel5)
               .add(jLabel2)
               .add(jLabel4))
            .add(18, 18, 18)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jfCodigoFornecedor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jcbFornecedor, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jfCNPJ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(30, 30, 30)
                  .add(jLabel12)
                  .add(18, 18, 18)
                  .add(jfCidadeUF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
               .add(jfEndereco))
            .add(51, 51, 51))
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel2)
               .add(jcbFornecedor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfCodigoFornecedor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jLabel4)
               .add(jfCNPJ, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfCidadeUF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel12))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfEndereco, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel5))
            .addContainerGap(24, Short.MAX_VALUE))
      );

      jbCancelarCompra.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbCancelarCompra.setText("Cancelar Compra");
      jbCancelarCompra.setEnabled(false);
      jbCancelarCompra.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
      jbCancelarCompra.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
      jbCancelarCompra.setIconTextGap(10);
      jbCancelarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jbCancelarCompraMouseClicked(evt);
         }
      });
      jbCancelarCompra.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbCancelarCompraActionPerformed(evt);
         }
      });

      jbSalvaCompra.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbSalvaCompra.setText("Salvar");
      jbSalvaCompra.setEnabled(false);
      jbSalvaCompra.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSalvaCompraActionPerformed(evt);
         }
      });

      jbProcurarCompra.setText("+");
      jbProcurarCompra.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbProcurarCompraActionPerformed(evt);
         }
      });

      jbObservacao.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jbObservacao.setText("Observação");
      jbObservacao.setEnabled(false);
      jbObservacao.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbObservacaoActionPerformed(evt);
         }
      });

      jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel10.setText("Data Emissão:");

      jfDataEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

      jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel11.setText("Prazo Pagamento:");

      jfPrazoPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfPrazoPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfPrazoPagamentoFocusLost(evt);
         }
      });

      org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addContainerGap())
               .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                           .add(org.jdesktop.layout.GroupLayout.LEADING, jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
                        .add(jbProcurarCompra)
                        .add(12, 12, 12)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jcbTipoCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 223, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(jLabel6)))
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel10)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jfDataEmissao, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 132, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(39, 39, 39)
                        .add(jLabel11)))
                  .add(18, 18, 18)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jfPrazoPagamento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jLabel8)
                     .add(jfData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 134, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(18, 18, 18)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jbSalvaCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jbNovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(jbLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                  .add(33, 33, 33))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(18, 18, 18)
                  .add(jfStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel3)
                        .add(18, 18, 18)
                        .add(jfFuncionario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 411, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                     .add(jPanel1Layout.createSequentialGroup()
                        .add(jbPimeiro)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbVoltar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jbProximo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbUltimo)
                        .add(52, 52, 52)
                        .add(jLabel7)
                        .add(18, 18, 18)
                        .add(jfValorTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                  .add(136, 136, 136)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jbObservacao, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .add(jbConfirmarCompra, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .add(45, 45, 45)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                     .add(jbSair, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                     .add(jbCancelarCompra, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .add(0, 0, Short.MAX_VALUE))))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jbNovo)
                     .add(jbSalvaCompra))
                  .add(18, 18, 18)
                  .add(jbLimpar))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel1)
                     .add(jLabel6)
                     .add(jLabel8))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jfData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jcbTipoCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jbProcurarCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(19, 19, 19)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel10)
                        .add(jfDataEmissao, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jfPrazoPagamento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel11)))))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jfStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(18, 18, 18)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel1Layout.createSequentialGroup()
                  .add(20, 20, 20)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jbUltimo)
                     .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbPimeiro)
                        .add(jbVoltar)
                        .add(jbProximo))
                     .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel7)
                        .add(jfValorTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                  .add(23, 23, 23)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel3)
                     .add(jfFuncionario, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
               .add(jPanel1Layout.createSequentialGroup()
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jbConfirmarCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jbCancelarCompra, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(18, 18, 18)
                  .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jbSair, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jbObservacao, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
            .add(26, 26, 26))
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layout.createSequentialGroup()
            .addContainerGap()
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 653, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      setSize(new java.awt.Dimension(1082, 714));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
      dispose();
      FrmMenuPrincipal frm = new FrmMenuPrincipal();
      frm.setVisible(true);
   }//GEN-LAST:event_jbSairActionPerformed

   /**
    * *****************************
    * //ao abrir a tela Item da compra o codigo, fornecedor e tipo da compra é
    * carregado para o formulario do item ******************************
    */
   private void jbNovoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoItemActionPerformed

      mostraJDialog();

      int quantidadeLinha = jtItens.getRowCount();
      if (quantidadeLinha >= 1) {
         jbConfirmarCompra.setEnabled(true);
      } else {
         jbConfirmarCompra.setEnabled(!true);
      }

   }//GEN-LAST:event_jbNovoItemActionPerformed

   /**
    * ************************
    * ao clicar em 'alterar item', carrego as informações daquele item em
    * FrmItemCompra, onde posso alterar os dados, e ao clicar novamente em
    * confirmar, mudo a flag do botão para ele alterar ao inves de inserir como
    * atualmente está setado
    *
    * em fase de desenvolvimento **********************
    */

   private void jbAlterarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarItemActionPerformed

      mostraJDialogAlterar();

   }//GEN-LAST:event_jbAlterarItemActionPerformed


   private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
      //carrego a data atual no momento que clico em novo

      limpar();
      Habilitar();
      apagaTabela();

      jbPimeiro.setEnabled(false);
      jbVoltar.setEnabled(false);
      jbProximo.setEnabled(false);
      jbUltimo.setEnabled(false);

      Date agora = new Date();
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      jfData.setText(df.format(agora));


   }//GEN-LAST:event_jbNovoActionPerformed


   private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
      apagaTabela();
      limpar();
   }//GEN-LAST:event_jbLimparActionPerformed


   private void jcbFornecedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbFornecedorFocusLost
      //carrego os dados atrelados a fornecedor ao sair da comboBox 'Fornecedor' 
      carregaFornecedor();
   }//GEN-LAST:event_jcbFornecedorFocusLost

   public void carregaFornecedor() {
      try {
         connFornecedor.conexao();
         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO WHERE RAZAO_SOCIAL = '" + jcbFornecedor.getSelectedItem() + "'");
         connFornecedor.rs.first();

         jfCodigoFornecedor.setText("" + connFornecedor.rs.getInt("CODIGO_FORNECEDOR"));
         jfCNPJ.setText(connFornecedor.rs.getString("CNPJ"));
         jfCidadeUF.setText(connFornecedor.rs.getString("NOME_CIDADE") + " / " + connFornecedor.rs.getString("UF"));
         jfEndereco.setText(connFornecedor.rs.getString("ENDERECO") + ", " + connFornecedor.rs.getInt("N_ENDERECO") + ", " + connFornecedor.rs.getString("BAIRRO"));

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e);
      } finally {
         connFornecedor.desconecta();
      }

   }


   private void jbSalvaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvaCompraActionPerformed

      if (jcbTipoCompra.getSelectedItem().equals("") || jcbFornecedor.getSelectedItem().equals("")) {
         JOptionPane.showMessageDialog(null, "Os campos TIPO DE COMPRA e FORNECEDOR precisam ser preenchidos!");
         jcbTipoCompra.grabFocus();
      } else {

         try {

            connFornecedor.conexao();
            connFornecedor.executaaSQL("SELECT CODIGO_FORNECEDOR FROM FORNECEDOR WHERE RAZAO_SOCIAL = '" + jcbFornecedor.getSelectedItem() + "'");
            connFornecedor.rs.first();

            modCompra.getFornecedor().setCodigo(connFornecedor.rs.getInt("CODIGO_FORNECEDOR"));
            modCompra.setTipoCompra((String) jcbTipoCompra.getSelectedItem());

            connCompra.conexao();
            connCompra.executaaSQL("SELECT MAX(CODIGO_COMPRA) FROM COMPRA");
            connCompra.rs.last();

            if (!connCompra.rs.last()) {
               jfCodigo.setText(String.valueOf(1));
            } else {
               jfCodigo.setText(String.valueOf(connCompra.rs.getInt("MAX(CODIGO_COMPRA)") + 1));
            }

            modCompra.setDataEmissao(jfDataEmissao.getText());
            modCompra.setCodigoCompra(Integer.parseInt(jfCodigo.getText()));
            controle.salvarCompra(modCompra);

            String prazos = jfPrazoPagamento.getText();
            String[] quebraPrazo = prazos.split("-");

            for (String quebraPrazo1 : quebraPrazo) {
               modCompra.setPrazo(quebraPrazo1);
               controle.salvarVencimento(modCompra);
            }

            jbCancelarCompra.setEnabled(true);
            jbNovoItem.setEnabled(true);

         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "FrmCompra\\Erro ao setar parametros de salvamento\n\n" + ex);
         } finally {
            connCompra.desconecta();
            connFornecedor.desconecta();
         }

      }//else

   }//GEN-LAST:event_jbSalvaCompraActionPerformed


    private void jbConfirmarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarCompraActionPerformed
       modCompra.setCodigoCompra(Integer.parseInt(jfCodigo.getText()));
       modCompra.setValorTotal(Double.parseDouble(jfValorTotal.getText()));

       controle.confirmaCompra(modCompra);
       carregaSituacao();
       jbLimpar.setEnabled(true);
       jbNovoItem.setEnabled(false);
       jbAlterarItem.setEnabled(false);
       jbCancelarItem.setEnabled(false);
       jbConfirmarCompra.setEnabled(false);

    }//GEN-LAST:event_jbConfirmarCompraActionPerformed

    private void jbCancelarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarCompraActionPerformed
       //modCompra.setCodigoCompra(Integer.parseInt(jfCodigo.getText()));
       controle.excluiCompra(Integer.parseInt(jfCodigo.getText()));
       jbLimpar.setEnabled(true);
       carregaSituacao();
       verificaStatus(jfStatus.getText());


    }//GEN-LAST:event_jbCancelarCompraActionPerformed


    private void jbPimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPimeiroActionPerformed

       modCompra = controle.primeiro();
       if (ControleCompra.existe == true) {
          carregaPesquisa();
          modCompra = controle.buscaPrazos(Integer.parseInt(jfCodigo.getText()));
          carregaPrazo();
       }
    }//GEN-LAST:event_jbPimeiroActionPerformed


    private void jbVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVoltarActionPerformed

       modCompra = controle.anterior();
       if (ControleCompra.existe == true) {
          carregaPesquisa();
          modCompra = controle.buscaPrazos(Integer.parseInt(jfCodigo.getText()));
          carregaPrazo();
       }
    }//GEN-LAST:event_jbVoltarActionPerformed


    private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
       modCompra = controle.proximo();
       if (ControleCompra.existe == true) {
          carregaPesquisa();
          modCompra = controle.buscaPrazos(Integer.parseInt(jfCodigo.getText()));
          carregaPrazo();
       }
    }//GEN-LAST:event_jbProximoActionPerformed


    private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed

       modCompra = controle.ultimo();

       if (ControleCompra.existe == true) {
          carregaPesquisa();
          modCompra = controle.buscaPrazos(Integer.parseInt(jfCodigo.getText()));
          carregaPrazo();
       }
    }//GEN-LAST:event_jbUltimoActionPerformed

   public void carregaPrazo() {  
      jfPrazoPagamento.setText(modCompra.getPrazo());
   }//pega o prazo no formato ddd-ddd-ddd para apresentar na tela

   public void carregaPesquisa() {//busca os dados dos atributos para a determinada pesquisa no banco
      if (ControleCompra.existe == true) {

         jfCodigo.setText("" + modCompra.getCodigoCompra());

         jcbFornecedor.setSelectedItem(modCompra.getFornecedor().getRazaoSocial());
         achaFornecedor();
         jfData.setText(modCompra.getDataCompra());
         jfValorTotal.setText("" + modCompra.getValorTotal());
         jfDataEmissao.setText(modCompra.getDataEmissao());
         jfStatus.setText(modCompra.getSituacao());

         verificaStatus(modCompra.getSituacao());

         jcbTipoCompra.setSelectedItem(modCompra.getTipoCompra());
         jcbTipoCompra.setEnabled(false);
         jcbFornecedor.setEnabled(false);

         preencherTabela();
         jbLimpar.setEnabled(true);
         carregaSituacao();
      }
   }

   public void verificaStatus(String status) {

      if (status.equals("AGUARDANDO")) {
         jbCancelarCompra.setEnabled(true);
         jbConfirmarCompra.setEnabled(false);
      } else if (status.equals("CANCELADO")) {
         jbCancelarCompra.setEnabled(false);
         jbConfirmarCompra.setEnabled(false);
      }

      if (status.equals("INDEFINIDO")) {
         jbNovoItem.setEnabled(true);
         jbAlterarItem.setEnabled(!true);
         jbCancelarItem.setEnabled(!true);
         jbCancelarCompra.setEnabled(true);
         jbConfirmarCompra.setEnabled(true);
      }

      if (status.equals("APROVADA")) {
         jbCancelarCompra.setEnabled(true);
         jbConfirmarCompra.setEnabled(false);
      }

   }


    private void jbCancelarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarItemActionPerformed
       String codigo = String.valueOf(jtItens.getValueAt(jtItens.getSelectedRow(), 0));
       //JOptionPane.showMessageDialog(null, codigo);
       int cod = Integer.parseInt(codigo);
       modCompra.getItemCompra().setCodigoItem(cod);

       controle.excluirItem(cod);
       apagaTabela();
       preencherTabela();
       carregaSituacao();

    }//GEN-LAST:event_jbCancelarItemActionPerformed


    private void jbProcurarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcurarCompraActionPerformed
       apagaTabela();

       jbLimpar.setEnabled(true);

       if (jfCodigo.getText().trim().isEmpty()) {
          JOptionPane.showMessageDialog(null, "Primeiro informe o numero da compra.");
          jfCodigo.grabFocus();
       } else {

          modCompra = controle.procuraCompra(Integer.parseInt(jfCodigo.getText()));

          if (controle.control == true) {
             JOptionPane.showMessageDialog(null, "Numero de compra não existe");
             jfCodigo.setText(null);
             jfCodigo.grabFocus();

             jcbTipoCompra.setSelectedItem("");
             jfData.setText(null);
             jfCodigoFornecedor.setText(null);
             jcbFornecedor.setSelectedItem("");
             jfCidadeUF.setText(null);
             jfCNPJ.setText(null);

          } else {

             jcbFornecedor.setSelectedItem(modCompra.getFornecedor().getRazaoSocial());
             achaFornecedor();//testanto
             /*if (modCompra.getFornecedor().getRazaoSocial() == null) {
                return;
             } else {
                achaFornecedor();
             }*/

 /*if (modCompra.getDataCompra() == null) {
                return;
             } else {
                modCompra.getDataCompra();
             }*/
             modCompra.getDataCompra();//testando

             jfValorTotal.setText("" + modCompra.getValorTotal());

             /* if (modCompra.getSituacao().equals("")) {
                jfStatus.setText("");
             } else {
                jfStatus.setText(modCompra.getSituacao());
             }*/
             jfStatus.setText(modCompra.getSituacao());

             // if (modCompra.getSituacao() == null) {
             //   return;
             //} else {
             verificaStatus(modCompra.getSituacao());
             // }

             jcbTipoCompra.setSelectedItem(modCompra.getTipoCompra());

             preencherTabela();

             jcbTipoCompra.setEnabled(false);
             jcbFornecedor.setEnabled(false);
          }
       }
       controle.control = false;

    }//GEN-LAST:event_jbProcurarCompraActionPerformed

    private void jbObservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbObservacaoActionPerformed

       try {
          connCompra.conexao();
          connCompra.executaaSQL("SELECT OBSERVACAO FROM COMPRA WHERE CODIGO_COMPRA = '" + jfCodigo.getText() + "';");
          connCompra.rs.first();

          JOptionPane.showMessageDialog(null, connCompra.rs.getString("OBSERVACAO"));
       } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "FrmCompra\nErro ao carregar Observação da compra\n" + ex);
       } finally {
          connCompra.desconecta();
       }


    }//GEN-LAST:event_jbObservacaoActionPerformed

   private void jfPrazoPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfPrazoPagamentoFocusLost

   }//GEN-LAST:event_jfPrazoPagamentoFocusLost

   private void jbCancelarCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbCancelarCompraMouseClicked
      controle.excluiCompra(Integer.parseInt(jfCodigo.getText()));
      jbLimpar.setEnabled(true);
      carregaSituacao();
      verificaStatus(jfStatus.getText());

   }//GEN-LAST:event_jbCancelarCompraMouseClicked

   private void jtItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtItensMouseClicked

      try {
         connCompra.conexao();
         connCompra.executaaSQL("SELECT STATUS FROM ITEM_COMPRA WHERE CODIGO_ITEM = '" + jtItens.getValueAt(jtItens.getSelectedRow(), 0) + "';");
         connCompra.rs.first();

         if (connCompra.rs.getString("STATUS").equals("INDEFINIDO") || connCompra.rs.getString("STATUS").equals("REAVALIAR")) {
            jbAlterarItem.setEnabled(true);
         } else {
            jbAlterarItem.setEnabled(!true);
         }

      } catch (SQLException ex) {
         JOptionPane.showMessageDialog(null, "jtItens.mouseClicked\n" + ex);
      }

      /*
      
      
      
      fazer select para verificar a situação do item, se estiver aguardando ou reavaliar, habilitar cancelar e alterar, senão não habilitar
      
      
      
      
      
      
      
      
      
      
      
       */

   }//GEN-LAST:event_jtItensMouseClicked

   public void achaFornecedor() {
      try {
         connFornecedor.conexao();
         connFornecedor.executaaSQL("SELECT * FROM FORNECEDOR F JOIN CIDADE C ON F.CODIGO_CIDADE = C.CODIGO_CIDADE JOIN ESTADO E ON F.CODIGO_ESTADO = E.CODIGO_ESTADO WHERE RAZAO_SOCIAL = '" + jcbFornecedor.getSelectedItem() + "'");
         connFornecedor.rs.first();

         jfCodigoFornecedor.setText("" + connFornecedor.rs.getInt("CODIGO_FORNECEDOR"));
         jfCNPJ.setText(connFornecedor.rs.getString("CNPJ"));
         jfCidadeUF.setText(connFornecedor.rs.getString("NOME_CIDADE") + " / " + connFornecedor.rs.getString("UF"));
         jfEndereco.setText(connFornecedor.rs.getString("ENDERECO") + ", Nº " + connFornecedor.rs.getInt("N_ENDERECO"));

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, e);
      } finally {
         connFornecedor.desconecta();
      }

   }

   public void preparaForm() {
      jbNovoItem.setEnabled(true);
      jbAlterarItem.setEnabled(true);
      jbCancelarItem.setEnabled(true);
      jbConfirmarCompra.setEnabled(true);
      jbCancelarCompra.setEnabled(true);

   }

   public void Habilitar() {
      jcbTipoCompra.grabFocus();
      jbProcurarCompra.setEnabled(false);
      jfCodigo.setEnabled(false);
      jcbTipoCompra.setEnabled(true);

      jcbFornecedor.setEnabled(true);

      jbNovoItem.setEnabled(false);
      jbAlterarItem.setEnabled(false);
      jbCancelarItem.setEnabled(false);
      jbSalvaCompra.setEnabled(true);
      jbConfirmarCompra.setEnabled(false);
      jbCancelarCompra.setEnabled(false);

      jbLimpar.setEnabled(true);
   }

   public void apagaTabela() {

      ArrayList dados = new ArrayList();

      String[] Colunas = new String[]{};

      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtItens.setModel(modelo);
   }

   public void limpar() {
      jfCodigo.grabFocus();
      jbObservacao.setEnabled(false);
      jbPimeiro.setEnabled(true);
      jbVoltar.setEnabled(true);
      jbProximo.setEnabled(true);
      jbUltimo.setEnabled(true);
      jfValorTotal.setText(null);
      jfEndereco.setText(null);
      jfDataEmissao.setText(null);
      jfPrazoPagamento.setText(null);
      jbProcurarCompra.setEnabled(true);
      jfCodigo.setEnabled(true);
      jcbTipoCompra.setEnabled(false);
      jcbTipoCompra.setSelectedItem("");

      jcbFornecedor.setEnabled(false);

      jfStatus.setText("");
      modCompra.setCodigoCompra(0);

      jfCodigo.setText(null);
      jcbTipoCompra.setSelectedItem("");
      jcbTipoCompra.setSelectedItem("");
      jfData.setText(null);
      jfCodigoFornecedor.setText(null);
      jcbFornecedor.setSelectedItem("");
      jfCNPJ.setText(null);
      jfCidadeUF.setText(null);

      jbSalvaCompra.setEnabled(false);
      jbNovo.setEnabled(true);
      jbNovoItem.setEnabled(false);
      jbAlterarItem.setEnabled(false);
      jbCancelarItem.setEnabled(false);

      jbConfirmarCompra.setEnabled(false);
      jbCancelarCompra.setEnabled(false);
      jfFuncionario.setText(null);

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
         java.util.logging.Logger.getLogger(FrmCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmCompra().setVisible(true);
         }
      });
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
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
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JButton jbAlterarItem;
   private javax.swing.JButton jbCancelarCompra;
   private javax.swing.JButton jbCancelarItem;
   private javax.swing.JButton jbConfirmarCompra;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbNovoItem;
   private javax.swing.JButton jbObservacao;
   private javax.swing.JButton jbPimeiro;
   private javax.swing.JButton jbProcurarCompra;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbSalvaCompra;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JButton jbVoltar;
   private javax.swing.JComboBox jcbFornecedor;
   private javax.swing.JComboBox jcbTipoCompra;
   private javax.swing.JTextField jfCNPJ;
   private javax.swing.JTextField jfCidadeUF;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTextField jfCodigoFornecedor;
   private javax.swing.JTextField jfData;
   private javax.swing.JFormattedTextField jfDataEmissao;
   private javax.swing.JTextField jfEndereco;
   private javax.swing.JTextField jfFuncionario;
   private javax.swing.JTextField jfPrazoPagamento;
   private javax.swing.JLabel jfStatus;
   private javax.swing.JTextField jfValorTotal;
   private javax.swing.JTable jtItens;
   // End of variables declaration//GEN-END:variables
}
