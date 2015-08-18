/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compiladores;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;

public class Filtro extends DocumentFilter {
    int maxCharacters;
    boolean DEBUG = false;

    public Filtro(int maxChars) {
        maxCharacters = maxChars;
    }

    public void insertString(FilterBypass fb, int offs,
                             String str, AttributeSet a)
        throws BadLocationException {
        if (DEBUG) {

        }

        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters)
            super.insertString(fb, offs, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }
    
    public void replace(FilterBypass fb, int offs,
                        int length, 
                        String str, AttributeSet a)
        throws BadLocationException {
        if (DEBUG) {
         
        }

        if ((fb.getDocument().getLength() + str.length()
             - length) <= maxCharacters)
            super.replace(fb, offs, length, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }

}
