/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;

class SessionInterface extends JFileChooser implements ISessionInterface {
    private JFrame parent;

    SessionInterface (JFrame frame) {
        parent = frame;
    }

    public String getStorageFileName() {
        File selectedFile;
        int ret = showSaveDialog(parent);

        if (ret == JFileChooser.APPROVE_OPTION) {
            selectedFile = getSelectedFile();

            try {
                selectedFile.createNewFile();
            } catch (IOException e) { }

            return selectedFile.getAbsolutePath();
        }

        return null;
    }
}
