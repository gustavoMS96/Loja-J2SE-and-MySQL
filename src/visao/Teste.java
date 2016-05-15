/*
 * @author Gustavo Mathias de Siqueira 
 * @date 07/05/2016 
 * 
 */

package visao;


public class Teste{
	public static void main(String args[]){
	
		String prazo = "030-060-090";
		String[] quebra = prazo.split("-");
		
		for(int i=0;i<quebra.length;i++){
			System.out.println(quebra[i]);
		}
	
	
	}





}