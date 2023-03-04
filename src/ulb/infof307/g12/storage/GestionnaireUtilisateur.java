package ulb.infof307.g12.storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GestionnaireUtilisateur {


    public void writeToFile(String username, String password) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("stockUser.txt"));
        out.write(username + "#" + password);
        out.newLine();
        out.close();
    }
}
