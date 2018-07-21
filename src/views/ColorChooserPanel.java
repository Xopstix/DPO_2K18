package views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xaviamorcastillo on 18/5/18.
 */

public class ColorChooserPanel extends AbstractColorChooserPanel implements ActionListener{

    private MainView mainView;

    JToggleButton red;
    JToggleButton yellow;
    JToggleButton green;
    JToggleButton blue;
    JToggleButton purple;

    @Override
    public void updateChooser() {

        Color color = getColorFromModel();
        if (Color.red.equals(color)) {
            red.setSelected(true);
        } else if (Color.yellow.equals(color)) {
            yellow.setSelected(true);
        } else if (Color.black.equals(color)) {
            green.setSelected(true);
        } else if (Color.blue.equals(color)) {
            blue.setSelected(true);
            System.out.println("hola1");
        } else if (Color.pink.equals(color)) {
            purple.setSelected(true);
            System.out.println("Hola");
        }
    }

    protected JToggleButton createColor(String name, Border normalBorder) {
        JToggleButton color = new JToggleButton();
        color.setActionCommand(name);
        color.addActionListener(this);

        //Set the image or, if that's invalid, equivalent text.
        ImageIcon icon = new ImageIcon("icons/" + name + ".png");
        Image auxImage = icon.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH );
        icon = new ImageIcon(auxImage);

        color.setIcon(icon);
        color.setToolTipText(name);
        color.setBorder(normalBorder);

        return color;
    }

    @Override
    protected void buildChooser() {
        setLayout(new GridLayout(0, 1));

        ButtonGroup boxOfCrayons = new ButtonGroup();
        Border border = BorderFactory.createEmptyBorder(4,14,4,4);

        green = createColor("greensticker", border);
        boxOfCrayons.add(green);
        add(green);

        red = createColor("redsticker", border);
        boxOfCrayons.add(red);
        add(red);

        yellow = createColor("yellowsticker", border);
        boxOfCrayons.add(yellow);
        add(yellow);

        blue = createColor("bluesticker", border);
        boxOfCrayons.add(blue);
        add(blue);

        purple = createColor("purplesticker", border);
        boxOfCrayons.add(purple);
        add(purple);
    }

    @Override
    public String getDisplayName() {
        return "Colors";
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }

    @Override
    public void actionPerformed (ActionEvent e){

        Color newColor = null;
        String command = ((JToggleButton)e.getSource()).getActionCommand();
        if ("greensticker".equals(command)) {
            newColor = Color.green;
        } else if ("redsticker".equals(command)) {
            newColor = Color.red;
        } else if ("yellowsticker".equals(command)) {
            newColor = Color.yellow;
        } else if ("bluesticker".equals(command)){
            newColor = Color.blue;
        } else if ("purplesticker".equals(command)) {
            newColor = Color.pink;
        }
        System.out.println(newColor);
    }
}
