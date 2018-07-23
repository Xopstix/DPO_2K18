package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 19/4/18.
 */
//Classe per pintar transparent les celes de les llistes
public class TransparentListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setOpaque(isSelected);
        setForeground(Color.WHITE);

        if (cellHasFocus){

            setForeground(Color.WHITE);
        }
        return this;
    }
}
