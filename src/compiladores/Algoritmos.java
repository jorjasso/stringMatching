/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compiladores;

// Algoritmos para busqueda de cadenas
//Jorge Luis Guevara Diaz

class Algoritmos{
	
	char [] texto;
	char [] patron;
	
	int [] posiciones;
	
	public Algoritmos(char [] texto,char[] patron){	
		
		this.texto=texto;
		this.patron=patron;
		
		posiciones=new int[texto.length];
		
		}
	
	
	//algoritmo naivesearch
	/***************************************************************************/
	public void  busquedaIngenua(){
		int n=texto.length; //longitud del texto
		int m=patron.length; //longitud del patron
		int i, j, k, lim;
		int ocurrencia=0;
		
		int pos=0;
		
		System.out.println("ALGORITMO INGENUO"+n);
		System.out.println("LONGITUD DEL TEXTO"+n);
		System.out.println("LONGITUD DEL PATRON"+m);
		System.out.println("OCURRENCIAS EN ");
		
		mostrarPatron();	
		
		lim = n-m+1;
			/*busqueda*/
		for( i = 0; i<lim; i++ ) 
		{
				k = i;
			for( j=0; j<m && texto[k] == patron[j]; j++ ) {
				
				k++;
				}
			if( j == m ) {
				ocurrencia=k-(j-1);//osea posocion en el texto-tamaño del patron+1 i-j+2
				System.out.print(" - "+ocurrencia);
				
				posiciones[pos++]=ocurrencia;
				}
				
			}


		System.out.println("");
		
		
		}
	
	//algoritmo de Knuth Morris Pratt
	/***************************************************************************/
	public void knuthMorrisPratt(){
		int n=texto.length; //longitud del texto
		int m=patron.length; //longitud del patron
		int [] prefijo = new int [patron.length];
		int q=-1;     //numero de caracteres  emparejados
		
		int i, j, k, lim;
		int ocurrencia=0;
		int pos=0;
		
		System.out.println("ALGORITMO DE KNUTH-MORRIS-PRATT"+n);
		System.out.println("LONGITUD DEL TEXTO"+n);
		System.out.println("LONGITUD DEL PATRON"+m);
		System.out.println("OCURRENCIAS EN ");
		
		mostrarPatron();
		
		
		computarFuncionPrefijo(prefijo);	
		lim = n-m+1;
		
			/*busqueda*/
		for( i = 0; i<lim; i++ ) 
		{
			while (q>=0 && patron[q+1]!=texto[i])
				{
					q=prefijo[q];	//siguiente caracter no hace matching
					}
			if(patron[q+1]==texto[i])
				{
					q=q+1;			//siguientes caracteres hacen matching 
					}
			if(q==(m-1))
				{
					ocurrencia=i-m+1;
				    System.out.print(" - "+ocurrencia);
				    q=prefijo[q];    //busca el siguiente matching
				    posiciones[pos++]=ocurrencia+1; //para fines de visualizacion
					}		
					
				}
				
			
		System.out.println("");
		
	
				
				}
	
		public  int [] computarFuncionPrefijo(int [] next){			
		
			int m=patron.length;
			
			next[0]=-1;
			
			int k=-1;
			int q=0;
		
//			System.out.println(" q "+q+" k " +k+" inicio");
			
			for(q=1;q<m;q++){
				while (k>=0 && patron[k+1]!=patron[q]){
					
					k=next[k];
					}
				if(patron[k+1]==patron[q]){
						
					 k=k+1;
						
					}
					
				next[q]=k;
					
				}
			
		System.out.println("NEXT");
		
		for(int i=0;i<next.length;i++){
			
//			next[i]=next[i]+1;
			System.out.print(" "+next[i]);
			
			
			}
			System.out.println("");
		return next;
  }
		
	// Algortimo de Boyer-Moore
	/***************************************************************************/
	//computar d tabla  con occurrence heuristic.
	
	public void boyerMoore(){
		int n=texto.length; //longitud del texto
		int m=patron.length; //longitud del patron
		
		
		char[] texto1=new char[texto.length+1];
		char[] patron1 =new char[patron.length+1];
		
		int i;
		for(i=0;i<texto.length;i++)
		
		texto1[i+1]=texto[i];
		
		for( i=0;i<patron.length;i++)
		
		patron1[i+1]=patron[i];
		
		int s=0;
		int  j, k, lim;
		int ocurrencia=0;
		int pos=0;
		
		int [] d = new int[256]; //tabla delta 1 con 256 caracteres del alfabeto
		iniciarD(d);
		
		System.out.println("ALGORITMO DE BOYER-MOORE"+n);
		System.out.println("LONGITUD DEL TEXTO"+n);
		System.out.println("LONGITUD DEL PATRON"+m);
		System.out.println("OCURRENCIAS EN ");
		
		mostrarPatron();
		
			System.out.println("texto[23] "+texto[23]);
		while (s<=n-m){
			
				j=m;
				
		
	//		System.out.print(" pos = "+(s+j));
			while (j>0&&patron1[j]==texto1[s+j])
			{
				j=j-1;
			
				
	//			System.out.println("j "+j);
				}
			
			if(j==0){
	//			System.out.println("ocurrencia en "+s);
				
				posiciones[pos++]=s+1; //para fines de visualizacion
			//	s=s+delta2[0];
			
				s=s+m;	
	//			System.out.print(" S = "+s);
				}
			else{
		
	//			System.out.print(" d[texto[s+j] = "+d[texto[s+j]]+" texto[s+j] "+texto[s+j]);
				
			//	else{
					s=s+j-d[texto1[s+j]];
	//				System.out.print(" s = "+s+" j "+j+" | ");
			//		}	
				
		
				}	
			
			}
		
		
	}
	
	public void iniciarD(int []d){
		int m=patron.length;
		int k;
		int MAX_ALPHABET_SIZE=256;//????

		for( k=0; k < MAX_ALPHABET_SIZE; k++ ) d[k] = 0;
		for( k=0; k<m; k++ ) 
		{	
		//	System.out.println(" patron[k] "+patron[k]+" (int)patron[k]"+(int)patron[k]);
			d[patron[k]] = k;
		//	d[patron[k]] = k;
		
		//  System.out.println("d[patron[k]]  = "+d[patron[k]]+"   patron[k]"+patron[k]+" m-k "+m+" - "+k);
		
		}
		
		System.out.println(" TABLA Delta 1 ");
		for( k=0; k<m; k++ ) 
		{	
		  System.out.println(" "+patron[k]+" "+d[patron[k]]);
		
		}
		
		}
	
	
	
/*	public void boyerMoore(){
		int n=texto.length; //longitud del texto
		int m=patron.length; //longitud del patron
		
		int s=0;
		int i, j, k, lim;
		int ocurrencia=0;
		int pos=0;
		
		int [] d = new int[256]; //tabla delta 1 con 256 caracteres del alfabeto
		iniciarD(d);
		
		System.out.println("ALGORITMO DE BOYER-MOORE"+n);
		System.out.println("LONGITUD DEL TEXTO"+n);
		System.out.println("LONGITUD DEL PATRON"+m);
		System.out.println("OCURRENCIAS EN ");
		
		mostrarPatron();
		
			System.out.println("texto[23] "+texto[23]);
		while (s<=n-m){
			
				j=m-1;
				
		
	//		System.out.print(" pos = "+(s+j));
			while (j>=0&&patron[j]==texto[s+j])
			{
				j=j-1;
			
				
	//			System.out.println("j "+j);
				}
			
			if(j<0){
	//			System.out.println("ocurrencia en "+s);
				
				posiciones[pos++]=s+1; //para fines de visualizacion
			//	s=s+delta2[0];
			
				s=s+m;	
	//			System.out.print(" S = "+s);
				}
			else{
		
	//			System.out.print(" d[texto[s+j] = "+d[texto[s+j]]+" texto[s+j] "+texto[s+j]);
				if(d[texto[s+j]]==m-1)
					{s=s+1;}
				else{
					s=s+j-d[texto[s+j]];
	//				System.out.print(" s = "+s+" j "+j+" | ");
					}	
				
		
				}	
			
			}
		
		
	}
	
	public void iniciarD(int []d){
		int m=patron.length;
		int k;
		int MAX_ALPHABET_SIZE=256;//????

		for( k=0; k < MAX_ALPHABET_SIZE; k++ ) d[k] = 0;
		for( k=0; k<m; k++ ) 
		{	
		//	System.out.println(" patron[k] "+patron[k]+" (int)patron[k]"+(int)patron[k]);
			d[patron[k]] = k;
		//	d[patron[k]] = k;
		
		//  System.out.println("d[patron[k]]  = "+d[patron[k]]+"   patron[k]"+patron[k]+" m-k "+m+" - "+k);
		
		}
		
		System.out.println(" TABLA Delta 1 ");
		for( k=0; k<m; k++ ) 
		{	
		  System.out.println(" "+patron[k]+" "+d[patron[k]]);
		
		}
		
		}
		
*/	//computar dd tabla con match heuristic	version dd hat 
	public void iniciarDD(int [] d2 ){ //tamaño de delta = m
		int m=patron.length;
		char [] pat = new char[m];
		int  [] prefijo = new int[m];
		int  [] prefijoReverso = new int[m];
		int i,j;
		
		
		pat=reverso(patron);
		prefijo=computarFuncionPrefijo(prefijo);// computa el sufijo que es prefijo de si mismo
		
		for( i=0;i<m;i++){
			prefijoReverso[i]=prefijo[m-i-1];
			}
		
		
		for(i=1;i<=m;i++){
			d2[i]=-1;
			}
			
		//listo para construir delta2
		//buscar k
		int k;
		for(i=0;i<m;i++){
			
			
			}
			
					
		
		}	
	


	 	
	 public char [] reverso(char[] patron)
	 {
	 	char[] pat= new char[patron.length];
	 	
	 	for(int i=0;i<patron.length;i++){
	 		
	 		pat[i]=patron[patron.length-i-1];
	 	}
	 	return pat;
	 	}
	 	
	 public  int [] computarFuncionPrefijo(int [] next, char [] p){			
		
			int m=p.length;
			
			next[0]=-1;
			
			int k=-1;
			int q=0;
		
//			System.out.println(" q "+q+" k " +k+" inicio");
			
			for(q=1;q<m;q++){
				while (k>=0 && p[k+1]!=p[q]){
					
					k=next[k];
					}
				if(p[k+1]==p[q]){
						
					 k=k+1;
						
					}
					
				next[q]=k;
					
				}
			
		System.out.println("NEXT");
		
		for(int i=0;i<next.length;i++){
			
//			next[i]=next[i]+1;
			System.out.println(next[i]);
			
			
			}
		return next;
  }
  
  void mostrarPatron(){
  	//PATRON
  	System.out.println("PATRON A BUSCAR");
  	for(int i=0;i<patron.length;i++){
  		
  		System.out.print(""+patron[i]);
  		}
  		System.out.println("");
  	
  	}
  	
  public int[] posiciones(){
  	
  
  		return posiciones;
  	}
  	
  
  	
  	}		
	 
	