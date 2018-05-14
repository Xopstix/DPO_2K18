package controlador;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by xaviamorcastillo on 14/5/18.
 */
public class CustomTransferHandler extends TransferHandler {

        private DefaultListModel<String> dataUser;

        private int index;
        private boolean beforeIndex = false;
        private JList<String> userColumn;
        private JScrollPane jScrollPane;

        public CustomTransferHandler(JList<String> userColumn, DefaultListModel<String> dataUser, JScrollPane jScrollPane){
            this.userColumn = userColumn;
            this.jScrollPane = jScrollPane;
            this.dataUser = dataUser;
        }

        @Override
        public int getSourceActions(JComponent comp) {
            return COPY_OR_MOVE;
        }

        @Override
        public Transferable createTransferable(JComponent comp) {
            index = userColumn.getSelectedIndex();
            return new StringSelection((String) userColumn.getSelectedValue());
        }

        @Override
        public void exportDone(JComponent comp, Transferable trans, int action) {
            if (action == MOVE) {
                if (beforeIndex) {
                    dataUser.remove(index + 1);
                } else {
                    dataUser.remove(index);
                }
                jScrollPane.updateUI();
            }
        }

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            // Data =? String
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                // Data to String
                String s = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                dataUser.add(dl.getIndex(), s);
                beforeIndex = dl.getIndex() < index ? true : false;
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
            }
            return false;
        }
}
