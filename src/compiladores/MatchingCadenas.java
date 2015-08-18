/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compiladores;


import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import java.io.*;

public class MatchingCadenas extends JFrame implements ActionListener{

    static final int MAX_CARACTERES = 15000;
    String nuevaLinea = "\n";
    Algoritmos IR;
    String patron=" ";
    char [] pat;
    Archivo a;
    
    JTextPane panelTexto;
    AbstractDocument doc;    
    JTextArea areaTexto;    
    HashMap<Object, Action> actions;
    JPanel panel2;
    protected JTextField textField;
    JButton button;
    
             
    public MatchingCadenas() 
    {
        super("Compiladores    Jorge Luis Guevara Diaz");
        panel2=new JPanel();
	crearPanel2();
        
        panelTexto = new JTextPane();
        panelTexto.setCaretPosition(0);
        panelTexto.setMargin(new Insets(5,5,5,5));
        
        StyledDocument styledDoc = panelTexto.getStyledDocument();
        
        if (styledDoc instanceof AbstractDocument) 
        {
            doc = (AbstractDocument)styledDoc;
            doc.setDocumentFilter(new Filtro(MAX_CARACTERES));
        } 
        else 
        {
            System.err.println("");
            System.exit(-1);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelTexto);
        scrollPane.setPreferredSize(new Dimension(200, 350));

        
        areaTexto = new JTextArea(5, 75);
        areaTexto.setEditable(true);        
        JScrollPane scrollPaneForLog = new JScrollPane(areaTexto);        
        JSplitPane splitPane = new JSplitPane(
                                       JSplitPane.VERTICAL_SPLIT,
                                       scrollPane, scrollPaneForLog);
        splitPane.setOneTouchExpandable(true);

        
        JPanel statusPane = new JPanel(new GridLayout(1, 1));
        statusPane.add(panel2);

        
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusPane, BorderLayout.PAGE_END);

        
        createActionTable(panelTexto);
        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);

        //poner texto inicial en el panel
        initDocument();

        
//        panelTexto.addCaretListener(caretListenerLabel);
        doc.addDocumentListener(new MyDocumentListener());
    }
    
    	public void crearPanel2(){	
    	
    	button = new JButton("compilar");
    	button.addActionListener(this);
    
	String scaner ="Scanner";
	String parser ="Parser";
	String semantico ="parser";

	JRadioButton scanerButton = new JRadioButton(scaner);
    	scanerButton.setMnemonic(KeyEvent.VK_D);
    	scanerButton.setActionCommand(scaner);

    	JRadioButton parserButton = new JRadioButton(parser);
    	parserButton.setMnemonic(KeyEvent.VK_C);
    	parserButton.setActionCommand(parser);

    	JRadioButton semanticoButton = new JRadioButton(semantico);
    	semanticoButton.setMnemonic(KeyEvent.VK_D);
    	semanticoButton.setActionCommand(semantico);
    
    	ButtonGroup group = new ButtonGroup();
    	group.add(scanerButton);
    	group.add(parserButton);
    	group.add(semanticoButton);
    	
    	scanerButton.addActionListener(this);
    	parserButton.addActionListener(this);
    	semanticoButton.addActionListener(this);
    	
	panel2 = new JPanel(new GridLayout(1, 0));	
        panel2.add(button);
        panel2.add(scanerButton);
        panel2.add(parserButton);
        panel2.add(semanticoButton);
       
        
    }
    
     public void actionPerformed(ActionEvent e) 
     {
         int opcion=1;    
         
         if(e.getActionCommand()=="Scanner")
         {
             opcion=1;
         }
	 if(e.getActionCommand()=="Parser")
         {
             opcion=2;
	 }
         if(e.getActionCommand()=="Semantico")
         {
             opcion=3;
         }
			 	
	if(e.getActionCommand()=="Compilar")
        {
            //escribir codigo para cuando se ejecute el boton compilar
        }
			 	
	patron = textField.getText();
	if(patron.length()>0) {
			 		
		areaTexto.append("patron a buscar   "+patron + nuevaLinea);
        	 	textField.selectAll();
        	 	areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
        	 	
        	 	pat=new char[patron.length()];
		
				for(int i=0;i<patron.length();i++)
					{
					   pat[i]=patron.charAt(i);
						}     	
        	 	IR=new Algoritmos(a.texto,pat);
        	 		
			 	if(opcion==1){
			 			areaTexto.append("Algoritmo Ingenuo "+nuevaLinea);
//			 		IR.scaner();
			 			areaTexto.append("posiciones "+nuevaLinea);
			 		for(int i=0;i<IR.posiciones().length;i++)
			 			if(IR.posiciones[i]!=0)//para que o me imprima ceros
			 			areaTexto.append(""+IR.posiciones[i]+" ");
			 			
//			 			doc.
			 			
			 		}
			 	if(opcion==2){
			 		areaTexto.append("Algoritmo Knuth-Morris-Pratt "+nuevaLinea);
//			 		IR.parser();
			 		areaTexto.append("posiciones "+nuevaLinea);
			 		for(int i=0;i<IR.posiciones().length;i++)
			 			if(IR.posiciones[i]!=0)//para que o me imprima ceros
			 			areaTexto.append(""+IR.posiciones[i]+" ");
//			 			doc.
			 		}
			 	if(opcion==3){
			 		areaTexto.append("Algoritmo Boyer-Moore "+nuevaLinea);
//			 		IR.semantico();
			 		areaTexto.append("posiciones "+nuevaLinea);
			 		for(int i=0;i<IR.posiciones().length;i++)
			 			if(IR.posiciones[i]!=0)//para que o me imprima ceros
			 			areaTexto.append(""+IR.posiciones[i]+" ");
			 		}
				
			 		
			 		}
        	 	
				
	//		 	}		
			 
			 	
			 
			 	                                  	
	}


    
  /*  protected class CaretListenerLabel extends JLabel
                                       implements CaretListener {
        public CaretListenerLabel(String label) {
            super(label);
        }

    
        public void caretUpdate(CaretEvent e) {
            displaySelectionInfo(e.getDot(), e.getMark());
        }

        protected void displaySelectionInfo(final int dot,
                                            final int mark) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (dot == mark) {  // no seleccionado
                        try {
                            Rectangle caretCoords = panelTexto.modelToView(dot);
                            //Convierte a coordenadas de vista
                            setText("posiciond el texto: " + dot
                                    + ", posicion de vista = ["
                                    + caretCoords.x + ", "
                                    + caretCoords.y + "]"
                                    + nuevaLinea);
                        } catch (BadLocationException ble) {
                            setText("posiciond el texto: " + dot + nuevaLinea);
                        }
                    } else if (dot < mark) {
                        setText("selection from: " + dot
                                + " to " + mark + nuevaLinea);
                    } else {
                        setText("selection from: " + mark
                                + " to " + dot + nuevaLinea);
                    }
                }
            });
        }
    }
*/
    

    //And this one listens for any changes to the document.
    protected class MyDocumentListener
                    implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            displayEditInfo(e);
        }
        public void removeUpdate(DocumentEvent e) {
            displayEditInfo(e);
        }
        public void changedUpdate(DocumentEvent e) {
            displayEditInfo(e);
        }
        private void displayEditInfo(DocumentEvent e) {
            Document document = (Document)e.getDocument();
            int changeLength = e.getLength();
            areaTexto.append(" Longitud del Texto = " + document.getLength() +"." + nuevaLinea);
            
        }
    }

    

    

    protected void initDocument() {
    	
   /* 	char [] patron={'E'};
		char [] patron1={'a','b','r','a','c','a','d','a','b','r','a'};
		char [] patron2={'a','b','a','b','a','b','a','b','a','b'};
		char [] patron3={'e','x','a','m','p','l','e'};
		char [] patron4={'r','e','m','i','n','i','s','c','e','n','c','e'};
		char [] patron5={'a','b','c','x','x','x','a','b','c'};
		
		char [] patron6={'a','b','y','x','c','d','e','y','x'};
		char [] patron7={'a','t','-','t','h','a','t'};
		char [] patron8={'l','i'};
		System.out.println("PATRON A BUSCAR");
	*/	a= new Archivo();
		
		try
		{a.abrirArchivo();}
		
		catch(IOException e){
			
			System.out.println("no se pudo abrir el archivo");
			}
    	
    	String cadena =new String(a.texto());
        String initString[] ={"Texto.","", cadena};
                		
	  	//IR=new Algoritmos(a.texto,pat);
		
	//	doc.
//		StyleConstants.setForeground(attrs[5], Color.red);
	
     SimpleAttributeSet[] attrs = initAttributes(initString.length);

        try {
            for (int i = 0; i < initString.length; i ++) {
                doc.insertString(doc.getLength(), initString[i] + nuevaLinea,
                        attrs[i]);
            }
        } catch (BadLocationException ble) {
            System.err.println("Couldn't insert initial text.");
        }
    }

    protected SimpleAttributeSet[] initAttributes(int length) {
        //Hard-code some attributes.
        SimpleAttributeSet[] attrs = new SimpleAttributeSet[length];
  	    return attrs;
    }

    //The following two methods allow us to find an
    //action provided by the editor kit by its name.
    private void createActionTable(JTextComponent textComponent) {
        actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
    }

    private Action getActionByName(String name) {
        return actions.get(name);
    }

   
 	  
    private static void createAndShowGUI() {
 
        final MatchingCadenas frame = new MatchingCadenas();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

   
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
