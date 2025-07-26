package Vista;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author wadad
 */
public class vMensaje {

    public void mensajeConsola(String texto) {

        JOptionPane.showMessageDialog(null, texto);
    }

    public void MensajeInfo(Component C, String texto) {

                JOptionPane.showMessageDialog(C, texto, "Información",
                        JOptionPane.INFORMATION_MESSAGE);

    }
     public int BajaDialog(Component C,String s) {
         int opcion;
         if(s.equals("M")){
         opcion = JOptionPane.showConfirmDialog(C, "Deseas eliminar dicho Monitor ?",
                "Atención", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
         }
        else{
            opcion = JOptionPane.showConfirmDialog(C, s,
                "Atención", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
         }
        return opcion;
    }
}
