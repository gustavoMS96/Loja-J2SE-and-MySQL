
package visao;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class LimiteValor extends PlainDocument {
   private int quantidadeMax;
   
   public LimiteValor(int maxLength){
      super();
      if(maxLength <= 0)
         throw new IllegalArgumentException("Informe a quantidade"); 
         quantidadeMax = maxLength;
   }
   
   @Override
   public void insertString(int offset, String str, AttributeSet attr)
      throws BadLocationException{
         if(str==null || getLength()==quantidadeMax)
            return;
         int totalQuantia = (getLength()+str.length());
         
         if(totalQuantia <=quantidadeMax){
            super.insertString(offset, str.replaceAll("[^0-9 |^.]",""), attr);
            //super.insertString(offset, str.replaceAll("[^a-z|^0-9]",""), attr);
            return;
         }
         
         String nova = str.substring(0,getLength()-quantidadeMax);
         super.insertString(offset, nova, attr);
      }
   
  
   
   
   }
