/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compiladores;
import java.io.*;
class Archivo
{
    char [] texto;

    public Archivo(){}
	
    public void abrirArchivo()throws IOException
    {
        int i=0;
		
	FileReader entrada=null;
        
        StringBuffer str=new StringBuffer();
        
        try  
        {
            entrada=new FileReader("hola.txt");
            int c;
            while((c=entrada.read())!=-1)
            {
                 str.append((char)c);            
                i++;                
            }
            
            texto= new char[str.length()];
            
            for( i=0;i<texto.length;i++)
            {
                texto[i]=str.charAt(i);
            }
            
            
            System.out.println("--------------------------------------");
            System.out.println("TEXTO");
            
            for(i=0;i<texto.length;i++)
            {
                System.out.print(texto[i]);
            }
            	
            System.out.println("");	
            
       }
       catch (IOException ex) 
       {
           System.out.println(ex);
       }
       finally
       {
//cerrar los flujos de datos
           if(entrada!=null)
           {
               try
               {
                   entrada.close();
                }
               catch(IOException ex){}
            }
            
       }
       
    }
    
    public char[] texto()
    {
        return texto;
    }

}
	
