package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 22/7/18.
 */

//Classe usada per pintar les celes de les llistes transparents o de colors segons l'etiqueta
public class CustomListCellRenderer extends DefaultListCellRenderer {

        private ProjectView projectView;
        private JList<String> list;

        public CustomListCellRenderer(ProjectView projectView, JList<String> list) {
            this.projectView = projectView;

            this.list = list;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            //setOpaque(isSelected);

            if (!isSelected) {

                int etiqueta = projectView.getProject().getColumnes().get(Integer.parseInt(list.getName())).
                        getTasques().get(index).getId_etiqueta();

                if (etiqueta > 0) {

                    setOpaque(true);

                    if (etiqueta == projectView.getProject().getEtiquetes().get(0).getId_etiqueta()) {
                        setBackground(Color.green);
                        setForeground(Color.black);
                    }
                    if (etiqueta == projectView.getProject().getEtiquetes().get(1).getId_etiqueta()) {
                        setBackground(Color.red);
                        setForeground(Color.black);
                    }
                    if (etiqueta == projectView.getProject().getEtiquetes().get(2).getId_etiqueta()) {
                        setBackground(Color.yellow);
                        setForeground(Color.black);
                    }
                    if (etiqueta == projectView.getProject().getEtiquetes().get(3).getId_etiqueta()) {
                        setBackground(Color.blue);
                        setForeground(Color.white);
                    }
                    if (etiqueta == projectView.getProject().getEtiquetes().get(4).getId_etiqueta()) {
                        setBackground(Color.pink);
                        setForeground(Color.black);
                    }

                } else {

                    setOpaque(false);
                    setBackground(Color.darkGray);
                    setForeground(Color.white);
                }
            }else{

                setOpaque(false);
                setForeground(Color.white);
            }

            return this;
        }

        public JList<String> getList() {
            return list;
        }

        public void setList(JList<String> list) {
            this.list = list;
        }

        public ProjectView getProjectView() {
            return projectView;
        }

        public void setProject(ProjectView projecView) {
            this.projectView = projectView;
        }
}
