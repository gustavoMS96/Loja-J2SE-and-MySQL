package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.ConexaoBanco;
import controle.ControleProduto;
import controle.ModeloTabela;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.ModeloGrupo;
import modelo.ModeloProduto;
import modelo.ModeloSubGrupo;

public class FrmProduto extends javax.swing.JFrame {

   ConexaoBanco connSubGrupo = new ConexaoBanco();
   ConexaoBanco connGrupo = new ConexaoBanco();
   ConexaoBanco connProduto = new ConexaoBanco();
   ModeloProduto modProduto = new ModeloProduto();
   ModeloSubGrupo modSubgrupo = new ModeloSubGrupo();
   ModeloGrupo modGrupo = new ModeloGrupo();

   ControleProduto controle = new ControleProduto();

   public FrmProduto() {
      try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
      }
      initComponents();
      getContentPane().setBackground(Color.decode("#EEE9E9"));

      modProduto.setGrupo(modGrupo);
      modProduto.setSubGrupo(modSubgrupo);

      connProduto.conexao();

      connGrupo.conexao();
      connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");

      connSubGrupo.conexao();
      connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO");

      PreencherTabela();

      jcbGrupo.removeAllItems();
      jcbSubGrupo.removeAllItems();
      jfNome.setDocument(new LimiteCampoLivre(150));

      jfEstoque_min.setDocument(new LimiteCaracterNumerico(6));

      try {
         connGrupo.rs.first();
         do {
            jcbGrupo.addItem("" + connGrupo.rs.getString("NOME_GRUPO"));
         } while (connGrupo.rs.next());
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao carregar comboBox GRUPO:\n" + e);
      }

      try {
         connSubGrupo.rs.first();
         do {
            jcbSubGrupo.addItem("" + connSubGrupo.rs.getString("NOME_SUBGRUPO"));
         } while (connSubGrupo.rs.next());
      } catch (SQLException e) {
         JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao carregar comboBox SubGrupo:\n" + e);
      }

   }

   private void iniciaTabela() {
      ArrayList dados = new ArrayList();
      String[] Colunas = new String[]{"Código", "Produto", "Preço", "Estq Mín.", "Estq Max.", "Estq Atual", "Grupo", "Subgrupo"};
      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtProduto.setModel(modelo);

   }

   public void PreencherTabela() {

      ArrayList dados = new ArrayList();
      String[] Colunas = new String[]{"Código", "Produto", "Estq Mín.", "Estq Max.", "Estq Atual", "Garantia", "Grupo", "Subgrupo"};

      connProduto.conexao();
      connProduto.executaaSQL("SELECT * FROM PRODUTO P JOIN GRUPO_PRODUTO G ON P.CODIGO_GRUPO = G.CODIGO_GRUPO JOIN SUBGRUPO_PRODUTO S ON P.CODIGO_SUBGRUPO = S.CODIGO_SUBGRUPO order by codigo_produto;");

      try {
         connProduto.rs.first();

         do {
            dados.add(new Object[]{connProduto.rs.getInt("CODIGO_PRODUTO"), connProduto.rs.getString("DESCRICAO"),
               connProduto.rs.getString("ESTOQUE_MINIMO"), connProduto.rs.getString("ESTOQUE_MAXIMO"),
               connProduto.rs.getString("ESTOQUE"), connProduto.rs.getInt("GARANTIA"),
               connProduto.rs.getString("NOME_GRUPO"), connProduto.rs.getString("NOME_SUBGRUPO")});
         } while (connProduto.rs.next());

      } catch (SQLException e) {
         JOptionPane.showMessageDialog(rootPane, e);
      }

      ModeloTabela modelo = new ModeloTabela(dados, Colunas);
      jtProduto.setModel(modelo);

      jtProduto.getTableHeader().setReorderingAllowed(!true);
      jtProduto.setAutoResizeMode(jtProduto.AUTO_RESIZE_ALL_COLUMNS);//tamanho automatico conforme como é o layout

      jtProduto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   public void Habilitar() {
      jfNome.setEnabled(true);
      jfEstoque_min.setEnabled(true);
      jfEstoqueMax.setEnabled(true);
      jfGarantia.setEnabled(true);
      jcbGrupo.setEnabled(true);
      jcbSubGrupo.setEnabled(true);
      jbInserir.setEnabled(true);
      jbNovo.setEnabled(false);
      jbLimpar.setEnabled(true);

   }

   public void HabilitarAlteracao() {
      jfNome.setEnabled(true);
      jfEstoque_min.setEnabled(true);
      jfEstoqueMax.setEnabled(true);
      jfGarantia.setEnabled(true);
      jcbGrupo.setEnabled(true);
      jcbSubGrupo.setEnabled(true);
      jbInserir.setEnabled(true);
      jbNovo.setEnabled(false);
      jbLimpar.setEnabled(true);
      jbExcluir.setEnabled(true);
      jbAlterar.setEnabled(true);

   }

   public void LimparCampos() {
      jfNome.setText(null);
      jfNome.setEnabled(false);
      jfCodigo.setText(null);
      jfEstoqueMax.setText(null);
      jfEstoque_min.setText(null);
      jfEstoque_min.setEnabled(false);
      jfEstoqueMax.setEnabled(false);
      jfGarantia.setText(null);
      jbNovo.setEnabled(true);
      jbInserir.setEnabled(false);
      jbAlterar.setEnabled(false);
      jbExcluir.setEnabled(false);
      jbLimpar.setEnabled(false);
      jcbGrupo.setSelectedItem(null);
      jcbGrupo.setEnabled(false);
      jcbSubGrupo.setSelectedItem(null);
      jcbSubGrupo.setEnabled(false);
      jfGarantia.setEnabled(false);

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
      jLabel5 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jLabel8 = new javax.swing.JLabel();
      jfCodigo = new javax.swing.JTextField();
      jfNome = new javax.swing.JTextField();
      jfEstoque_min = new javax.swing.JTextField();
      jcbGrupo = new javax.swing.JComboBox();
      jcbSubGrupo = new javax.swing.JComboBox();
      jbNovo = new javax.swing.JButton();
      jbLimpar = new javax.swing.JButton();
      jbChamaGrupo = new javax.swing.JButton();
      jbChamaSubGrupo = new javax.swing.JButton();
      jbInserir = new javax.swing.JButton();
      jbAlterar = new javax.swing.JButton();
      jbExcluir = new javax.swing.JButton();
      jbPrimeiro = new javax.swing.JButton();
      jbAnterior = new javax.swing.JButton();
      jbProximo = new javax.swing.JButton();
      jbUltimo = new javax.swing.JButton();
      jPanel4 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jtProduto = new javax.swing.JTable();
      jbSair = new javax.swing.JButton();
      jLabel1 = new javax.swing.JLabel();
      jfEstoqueMax = new javax.swing.JTextField();
      jLabel4 = new javax.swing.JLabel();
      jfGarantia = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Formulário de Produto");
      setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
      setResizable(false);

      jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastro de Produtos"));

      jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel2.setText("Código:");

      jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel3.setText("Produto:");

      jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel5.setText("Estoque Mín.:");

      jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel7.setText("Grupo:");

      jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel8.setText("SubGrupo:");

      jfCodigo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfCodigo.setEnabled(false);

      jfNome.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfNome.setEnabled(false);

      jfEstoque_min.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
      jfEstoque_min.setEnabled(false);
      jfEstoque_min.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jfEstoque_minFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jfEstoque_minFocusLost(evt);
         }
      });

      jcbGrupo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbGrupo.setEnabled(false);
      jcbGrupo.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            jcbGrupoFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            jcbGrupoFocusLost(evt);
         }
      });

      jcbSubGrupo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jcbSubGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
      jcbSubGrupo.setEnabled(false);

      jbNovo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbNovo.setText("Novo");
      jbNovo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbNovoActionPerformed(evt);
         }
      });

      jbLimpar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbLimpar.setText("Limpar");
      jbLimpar.setEnabled(false);
      jbLimpar.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbLimparActionPerformed(evt);
         }
      });

      jbChamaGrupo.setText("+");
      jbChamaGrupo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbChamaGrupoActionPerformed(evt);
         }
      });

      jbChamaSubGrupo.setText("+");
      jbChamaSubGrupo.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbChamaSubGrupoActionPerformed(evt);
         }
      });

      jbInserir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jbInserir.setText("Salvar");
      jbInserir.setEnabled(false);
      jbInserir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbInserirActionPerformed(evt);
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

      jbAnterior.setText("<");
      jbAnterior.setToolTipText("");
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

      jPanel4.setBackground(java.awt.SystemColor.textHighlight);

      jtProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jtProduto.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jtProduto.setGridColor(new java.awt.Color(255, 255, 255));
      jtProduto.setRowHeight(25);
      jtProduto.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            jtProdutoMouseClicked(evt);
         }
         public void mouseEntered(java.awt.event.MouseEvent evt) {
            jtProdutoMouseEntered(evt);
         }
      });
      jScrollPane1.setViewportView(jtProduto);

      org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(jScrollPane1)
            .addContainerGap())
      );
      jPanel4Layout.setVerticalGroup(
         jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addContainerGap())
      );

      jbSair.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jbSair.setText("Voltar ao Menu");
      jbSair.setToolTipText("Sair");
      jbSair.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jbSairActionPerformed(evt);
         }
      });

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel1.setText("Estoque Max:");

      jfEstoqueMax.setEditable(false);
      jfEstoqueMax.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfEstoqueMax.setEnabled(false);

      jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jLabel4.setText("Garantia:");

      jfGarantia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jfGarantia.setToolTipText("Informe a quantidade de dias para garantia após venda do produto. ");
      jfGarantia.setEnabled(false);

      org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(34, 34, 34)
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel3Layout.createSequentialGroup()
                        .add(jbInserir)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbAlterar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbExcluir)
                        .add(749, 749, 749)
                        .add(jbSair)
                        .add(0, 4, Short.MAX_VALUE))
                     .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jLabel3)
                           .add(jLabel2)
                           .add(jLabel7)
                           .add(jLabel8))
                        .add(41, 41, 41)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                           .add(jPanel3Layout.createSequentialGroup()
                              .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jPanel3Layout.createSequentialGroup()
                                    .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(0, 0, Short.MAX_VALUE))
                                 .add(jcbGrupo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                 .add(jcbSubGrupo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                              .add(10, 10, 10)
                              .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                 .add(jbChamaSubGrupo)
                                 .add(jbChamaGrupo)))
                           .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 425, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(34, 34, 34)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jPanel3Layout.createSequentialGroup()
                              .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                 .add(jLabel5)
                                 .add(jLabel1)
                                 .add(jLabel4))
                              .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                 .add(jPanel3Layout.createSequentialGroup()
                                    .add(19, 19, 19)
                                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                       .add(jfEstoqueMax)
                                       .add(jfEstoque_min, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                 .add(jPanel3Layout.createSequentialGroup()
                                    .add(18, 18, 18)
                                    .add(jfGarantia))))
                           .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                              .add(jbNovo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                              .add(18, 18, 18)
                              .add(jbLimpar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jbPrimeiro)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbAnterior)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbProximo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jbUltimo))))
               .add(jPanel3Layout.createSequentialGroup()
                  .addContainerGap()
                  .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3Layout.createSequentialGroup()
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(28, 28, 28)
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel2)
                     .add(jfCodigo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
               .add(jbUltimo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jbProximo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                  .add(jbAnterior, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(jbPrimeiro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                  .add(jbNovo)
                  .add(jbLimpar)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
               .add(jfNome, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(jLabel3)
               .add(jLabel4)
               .add(jfGarantia, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jcbGrupo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel7))
                     .add(jbChamaGrupo))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jbChamaSubGrupo)
                     .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jcbSubGrupo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel8)))
                  .add(18, 18, 18)
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                     .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jbInserir)
                        .add(jbAlterar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jbExcluir, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                     .add(org.jdesktop.layout.GroupLayout.TRAILING, jbSair, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 11, Short.MAX_VALUE)
                  .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .addContainerGap(25, Short.MAX_VALUE))
               .add(jPanel3Layout.createSequentialGroup()
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jLabel5)
                     .add(jfEstoque_min, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                     .add(jfEstoqueMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                     .add(jLabel1))
                  .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
      );

      org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      setSize(new java.awt.Dimension(1213, 573));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
       dispose();
       FrmMenuPrincipal frmMenu = new FrmMenuPrincipal();
       frmMenu.setVisible(true);
    }//GEN-LAST:event_jbSairActionPerformed

    private void jbChamaSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChamaSubGrupoActionPerformed
       FrmGrupo fGrupo = new FrmGrupo();
       fGrupo.setVisible(true);
    }//GEN-LAST:event_jbChamaSubGrupoActionPerformed

    private void jbChamaGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChamaGrupoActionPerformed
       FrmSubGrupo fSub = new FrmSubGrupo();
       fSub.setVisible(true);
    }//GEN-LAST:event_jbChamaGrupoActionPerformed

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
       Habilitar();
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jbLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparActionPerformed
       LimparCampos();
    }//GEN-LAST:event_jbLimparActionPerformed

    private void jcbGrupoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbGrupoFocusLost
       connSubGrupo.conexao();
       connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO WHERE NOME_GRUPO = '" + jcbGrupo.getSelectedItem() + "'");

       try {
          connSubGrupo.rs.first();
          do {
             jcbSubGrupo.addItem("" + connSubGrupo.rs.getString("NOME_SUBGRUPO"));
          } while (connSubGrupo.rs.next());

       } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao carregar comboBox SubGrupo:\n" + e);
       }


    }//GEN-LAST:event_jcbGrupoFocusLost

    private void jcbGrupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbGrupoFocusGained
       jcbSubGrupo.removeAllItems();


    }//GEN-LAST:event_jcbGrupoFocusGained

    private void jbInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbInserirActionPerformed
       if (jfNome.getText().trim().isEmpty() || jcbGrupo.getSelectedItem().equals("")
               || jcbSubGrupo.getSelectedItem().equals("") || jfEstoque_min.getText().trim().isEmpty()
               || jfGarantia.getText().isEmpty()) {
          JOptionPane.showMessageDialog(null, "Preencha todos os campos");
          jfNome.grabFocus();
       } else {
          try {
             //connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");
             connGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO WHERE NOME_GRUPO = '" + jcbGrupo.getSelectedItem() + "'");
             connGrupo.rs.first();

             connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO WHERE NOME_SUBGRUPO = '" + jcbSubGrupo.getSelectedItem() + "'");
             connSubGrupo.rs.first();

             modProduto.setProduto(jfNome.getText());
             modProduto.getGrupo().setCodigo(connGrupo.rs.getInt("CODIGO_GRUPO"));
             modProduto.getSubGrupo().setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
             modProduto.setEstoqueAtual(0);
             modProduto.setEstoqueMinimo(Integer.parseInt(jfEstoque_min.getText()));
             modProduto.setEstoqueMaximo(Integer.parseInt(jfEstoqueMax.getText()));
             modProduto.setGarantia(Integer.parseInt(jfGarantia.getText()));
             
             
             controle.Inserir(modProduto);

          } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao setar parametros para salvar registro:\n" + ex);
          }

          LimparCampos();
          PreencherTabela();
       }
    }//GEN-LAST:event_jbInserirActionPerformed


   private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
      try {
         connProduto.conexao();

         modProduto.setCodigoProduto(Integer.parseInt(jfCodigo.getText()));

         controle.Excluir(modProduto);
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao setar codigo para exclusão");
      }

      PreencherTabela();
      LimparCampos();
   }//GEN-LAST:event_jbExcluirActionPerformed

   private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed

      if (jfNome.getText().trim().isEmpty() || jcbGrupo.getSelectedItem().equals("")
              || jcbSubGrupo.getSelectedItem().equals("") || jfEstoque_min.getText().trim().isEmpty()
              || jfGarantia.getText().isEmpty()) {
         JOptionPane.showMessageDialog(null, "Preencha todos os campos");
         jfNome.grabFocus();
      } else {
         try {
            //connGrupo.executaaSQL("SELECT * FROM GRUPO_PRODUTO");
            connGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO S JOIN GRUPO_PRODUTO G ON S.CODIGO_GRUPO = G.CODIGO_GRUPO WHERE NOME_GRUPO = '" + jcbGrupo.getSelectedItem() + "'");
            connGrupo.rs.first();

            connSubGrupo.executaaSQL("SELECT * FROM SUBGRUPO_PRODUTO WHERE NOME_SUBGRUPO = '" + jcbSubGrupo.getSelectedItem() + "'");
            connSubGrupo.rs.first();

            modProduto.setProduto(jfNome.getText());
            modProduto.getGrupo().setCodigo(connGrupo.rs.getInt("CODIGO_GRUPO"));
            modProduto.getSubGrupo().setCodigo(connSubGrupo.rs.getInt("CODIGO_SUBGRUPO"));
            modProduto.setGarantia(Integer.parseInt(jfGarantia.getText()));
            modProduto.setEstoqueMinimo(Integer.parseInt(jfEstoque_min.getText()));
            modProduto.setEstoqueMaximo(Integer.parseInt(jfEstoqueMax.getText()));

            controle.Alterar(modProduto);

         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao setar parametros para alterar registro:\n" + ex);
         }

      }//else
      HabilitarAlteracao();
      LimparCampos();
      PreencherTabela();
   }//GEN-LAST:event_jbAlterarActionPerformed

   private void setaAtributosPesquisa() {

      jfCodigo.setText("" + modProduto.getCodigoProduto());
      jfNome.setText(modProduto.getProduto());
      jcbGrupo.setSelectedItem((String) modProduto.getGrupo().getNomeGrupo());
      jcbSubGrupo.setSelectedItem((String) modProduto.getSubGrupo().getNomeSubGrupo());
      jfEstoque_min.setText("" + modProduto.getEstoqueMinimo());
      jfEstoqueMax.setText("" + modProduto.getEstoqueMaximo());
      jfGarantia.setText("" + modProduto.getGarantia());
   }


   private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed

      modProduto = controle.Primeiro();
      setaAtributosPesquisa();
      HabilitarAlteracao();


   }//GEN-LAST:event_jbPrimeiroActionPerformed

   private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed

      modProduto = controle.Anterior();
      setaAtributosPesquisa();
      HabilitarAlteracao();
   }//GEN-LAST:event_jbAnteriorActionPerformed

   private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed

      modProduto = controle.Proximo();
      setaAtributosPesquisa();
      HabilitarAlteracao();
   }//GEN-LAST:event_jbProximoActionPerformed

   private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed

      modProduto = controle.Ultimo();
      setaAtributosPesquisa();
      HabilitarAlteracao();
   }//GEN-LAST:event_jbUltimoActionPerformed

   private void jfEstoque_minFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEstoque_minFocusLost

      if (jfEstoque_min.getText().trim().isEmpty()) {
         jfEstoque_min.grabFocus();

      } else {
         int num = 0;
         int val = Integer.parseInt(jfEstoque_min.getText());

         if (val < 200) {
            num = 400;
         } else if (val >= 200 && val <= 400) {
            num = 550;
         } else if (val > 400 && val <= 500) {
            num = 750;
         } else if (val > 500) {
            JOptionPane.showMessageDialog(null, "Não é possivel ter Estoque Minimo de produto acima de 500 unidades");
            jfEstoque_min.setText(null);
            jfEstoqueMax.setText(null);
            jfEstoque_min.grabFocus();
         }

         jfEstoqueMax.setText(String.valueOf(num));
      }
   }//GEN-LAST:event_jfEstoque_minFocusLost

   private void jfEstoque_minFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfEstoque_minFocusGained
      jfEstoqueMax.setText(null);
   }//GEN-LAST:event_jfEstoque_minFocusGained


    private void jtProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutoMouseClicked
       String codigo_produto = "" + jtProduto.getValueAt(jtProduto.getSelectedRow(), 0);
       //JOptionPane.showMessageDialog(null, descricao); 

       try {
          connProduto.conexao();
          connProduto.executaaSQL("SELECT * FROM PRODUTO P "
                  + "JOIN GRUPO_PRODUTO G ON P.CODIGO_GRUPO = G.CODIGO_GRUPO "
                  + "JOIN SUBGRUPO_PRODUTO S ON P.CODIGO_SUBGRUPO = S.CODIGO_SUBGRUPO WHERE CODIGO_PRODUTO = '" + Integer.parseInt(codigo_produto) + "'");
          connProduto.rs.first();

          jfCodigo.setText(String.valueOf(connProduto.rs.getInt("CODIGO_PRODUTO")));
          jfNome.setText(connProduto.rs.getString("DESCRICAO"));
          jcbGrupo.setSelectedItem((String) connProduto.rs.getString("NOME_GRUPO"));
          jcbSubGrupo.setSelectedItem((String) connProduto.rs.getString("NOME_SUBGRUPO"));
          jfEstoque_min.setText(String.valueOf(connProduto.rs.getInt("ESTOQUE_MINIMO")));
          jfEstoqueMax.setText(String.valueOf(connProduto.rs.getInt("ESTOQUE_MAXIMO")));
          jfGarantia.setText("" + connProduto.rs.getInt("GARANTIA"));
       } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "FrmProduto\n\nErro ao carregar campos ao clicar na tabela\n" + ex);
       } finally {
          connProduto.desconecta();
       }
       HabilitarAlteracao();
    }//GEN-LAST:event_jtProdutoMouseClicked

    private void jtProdutoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProdutoMouseEntered
       // TODO add your handling code here:
    }//GEN-LAST:event_jtProdutoMouseEntered

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
         java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmProduto().setVisible(true);
         }
      });
   }
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JButton jbAlterar;
   private javax.swing.JButton jbAnterior;
   private javax.swing.JButton jbChamaGrupo;
   private javax.swing.JButton jbChamaSubGrupo;
   private javax.swing.JButton jbExcluir;
   private javax.swing.JButton jbInserir;
   private javax.swing.JButton jbLimpar;
   private javax.swing.JButton jbNovo;
   private javax.swing.JButton jbPrimeiro;
   private javax.swing.JButton jbProximo;
   private javax.swing.JButton jbSair;
   private javax.swing.JButton jbUltimo;
   private javax.swing.JComboBox jcbGrupo;
   private javax.swing.JComboBox jcbSubGrupo;
   private javax.swing.JTextField jfCodigo;
   private javax.swing.JTextField jfEstoqueMax;
   private javax.swing.JTextField jfEstoque_min;
   private javax.swing.JTextField jfGarantia;
   private javax.swing.JTextField jfNome;
   private javax.swing.JTable jtProduto;
   // End of variables declaration//GEN-END:variables
}
