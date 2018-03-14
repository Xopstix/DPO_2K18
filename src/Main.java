import Vistas.VistaPrincipal;

import javax.swing.*;

/**
 * Created by xaviamorcastillo on 12/3/18.
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                VistaPrincipal vista = new VistaPrincipal();

                vista.setVisible(true);
            }
        });

    }

}
