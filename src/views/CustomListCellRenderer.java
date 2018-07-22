package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xaviamorcastillo on 22/7/18.
 */
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

                    if (etiqueta == 1) {
                        setBackground(Color.green);
                        setForeground(Color.black);
                    }
                    if (etiqueta == 2) {
                        setBackground(Color.red);
                        setForeground(Color.black);
                    }
                    if (etiqueta == 3) {
                        setBackground(Color.yellow);
                        setForeground(Color.black);
                    }
                    if (etiqueta == 4) {
                        setBackground(Color.blue);
                        setForeground(Color.white);
                    }
                    if (etiqueta == 5) {
                        setBackground(Color.pink);
                        setForeground(Color.black);
                    }

                } else {

                    setOpaque(false);
                    setBackground(Color.white);
                    setForeground(Color.black);
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
