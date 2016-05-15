/*
 * @author Gustavo Mathias de Siqueira 
 * @date 01/05/2016 
 * 
 */
package visao;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FrmRealizarVenda extends javax.swing.JFrame {

   /**
    * Creates new form FrmRealizarVenda
    */
   public FrmRealizarVenda() {
      try {
         UIManager.setLookAndFeel(new WindowsLookAndFeel());
      } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(FrmAvaliacaoDeCompra.class.getName()).log(Level.SEVERE, null, ex);
      }
      initComponents();
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
      jLabel5 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      jTextField1 = new javax.swing.JTextField();
      jTextField2 = new javax.swing.JTextField();
      jTextField3 = new javax.swing.JTextField();
      jTextField4 = new javax.swing.JTextField();
      jTextField5 = new javax.swing.JTextField();
      jTextField6 = new javax.swing.JTextField();
      jButton1 = new javax.swing.JButton();
      jButton2 = new javax.swing.JButton();
      jButton3 = new javax.swing.JButton();
      jPanel2 = new javax.swing.JPanel();
      jButton4 = new javax.swing.JButton();
      jButton5 = new javax.swing.JButton();
      jButton6 = new javax.swing.JButton();
      jLabel7 = new javax.swing.JLabel();
      jTextField7 = new javax.swing.JTextField();
      jLabel8 = new javax.swing.JLabel();
      jTextField8 = new javax.swing.JTextField();
      jLabel9 = new javax.swing.JLabel();
      jTextField9 = new javax.swing.JTextField();
      jScrollPane1 = new javax.swing.JScrollPane();
      jTable1 = new javax.swing.JTable();
      jPanel3 = new javax.swing.JPanel();
      jButton7 = new javax.swing.JButton();
      jButton9 = new javax.swing.JButton();
      jButton10 = new javax.swing.JButton();
      jButton11 = new javax.swing.JButton();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
      setTitle("Venda");

      jPanel1.setBackground(new java.awt.Color(0, 153, 255));
      jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

      jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel1.setForeground(new java.awt.Color(255, 255, 255));
      jLabel1.setText("Venda Nº:");

      jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel2.setForeground(new java.awt.Color(255, 255, 255));
      jLabel2.setText("Cliente:");

      jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel3.setForeground(new java.awt.Color(255, 255, 255));
      jLabel3.setText("RG:");

      jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel4.setForeground(new java.awt.Color(255, 255, 255));
      jLabel4.setText("Data:");

      jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel5.setForeground(new java.awt.Color(255, 255, 255));
      jLabel5.setText("Telefone:");

      jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jLabel6.setForeground(new java.awt.Color(255, 255, 255));
      jLabel6.setText("Operador do Caixa:");

      jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField1.setText("jTextField1");

      jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField2.setText("jTextField2");

      jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField3.setText("jTextField3");

      jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField4.setText("jTextField4");
      jTextField4.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField4ActionPerformed(evt);
         }
      });

      jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField5.setText("jTextField5");

      jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTextField6.setText("jTextField6");

      jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jButton1.setText("Nova Venda");
      jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jButton2.setText("Limpar");
      jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jButton3.setText("Procurar");
      jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel1)
               .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addComponent(jLabel4)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jTextField2))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jButton3)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(jLabel3))
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addGap(0, 11, Short.MAX_VALUE)
                  .addComponent(jLabel6)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, 18)
                  .addComponent(jLabel5)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jTextField5))
            .addGap(26, 26, 26)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(21, 21, 21))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jLabel4)
               .addComponent(jLabel6)
               .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jButton1))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel2)
                  .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel3)
                  .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel5)
                  .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jButton3))
               .addComponent(jButton2))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jPanel2.setBackground(new java.awt.Color(51, 102, 255));
      jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jButton4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jButton4.setText("Adicionar Item");
      jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jButton5.setText("Alterar Item");
      jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jButton6.setText("Remover Item");
      jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
      jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel7.setText("Valor Bruto");
      jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

      jTextField7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jTextField7.setForeground(new java.awt.Color(51, 51, 255));
      jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
      jTextField7.setText("0,0");

      jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
      jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel8.setText("Desconto");

      jTextField8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jTextField8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
      jTextField8.setText("0,0");

      jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
      jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      jLabel9.setText("Valor Final");

      jTextField9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
      jTextField9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
      jTextField9.setText("0,0");

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
               .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jTextField7)
               .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jTextField8)
               .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jTextField9))
            .addContainerGap())
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(44, 44, 44)
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabel8)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabel9)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(22, Short.MAX_VALUE))
      );

      jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
      jTable1.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jTable1.setGridColor(new java.awt.Color(255, 255, 255));
      jTable1.setRowHeight(25);
      jTable1.setRowMargin(0);
      jTable1.getTableHeader().setResizingAllowed(false);
      jTable1.getTableHeader().setReorderingAllowed(false);
      jScrollPane1.setViewportView(jTable1);

      jPanel3.setBackground(new java.awt.Color(51, 102, 255));
      jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

      jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jButton7.setText("Adicionar Vendedores");
      jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jButton9.setText("Condições de Pagamento");
      jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
      jButton10.setText("Finalizar Venda");
      jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      jButton11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
      jButton11.setText("Voltar ao Menu");
      jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

      javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
      jPanel3.setLayout(jPanel3Layout);
      jPanel3Layout.setHorizontalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(150, 150, 150))
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton11)
            .addContainerGap())
      );
      jPanel3Layout.setVerticalGroup(
         jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton11)
            .addContainerGap())
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jScrollPane1)
                     .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addGroup(layout.createSequentialGroup()
                  .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap())
      );

      setSize(new java.awt.Dimension(1129, 588));
      setLocationRelativeTo(null);
   }// </editor-fold>//GEN-END:initComponents

   private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_jTextField4ActionPerformed

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
         java.util.logging.Logger.getLogger(FrmRealizarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(FrmRealizarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(FrmRealizarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(FrmRealizarVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new FrmRealizarVenda().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JButton jButton10;
   private javax.swing.JButton jButton11;
   private javax.swing.JButton jButton2;
   private javax.swing.JButton jButton3;
   private javax.swing.JButton jButton4;
   private javax.swing.JButton jButton5;
   private javax.swing.JButton jButton6;
   private javax.swing.JButton jButton7;
   private javax.swing.JButton jButton9;
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
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTable jTable1;
   private javax.swing.JTextField jTextField1;
   private javax.swing.JTextField jTextField2;
   private javax.swing.JTextField jTextField3;
   private javax.swing.JTextField jTextField4;
   private javax.swing.JTextField jTextField5;
   private javax.swing.JTextField jTextField6;
   private javax.swing.JTextField jTextField7;
   private javax.swing.JTextField jTextField8;
   private javax.swing.JTextField jTextField9;
   // End of variables declaration//GEN-END:variables

}
