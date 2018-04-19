package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 19/4/18.
 */
public class TransparentListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setOpaque(isSelected);

        if (cellHasFocus){

            setBackground(Color.WHITE);
            setForeground(Color.DARK_GRAY);
        }
        return this;
    }

}
