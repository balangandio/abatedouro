package ml.universo42.jornada;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JornadaFile {

    public File file;

    public JornadaFile(File file) {
        this.file = file;
    }

    public Jornada load() {
        if (!file.exists()) {
            return Jornada.empty();
        }

        return JornadaSerial.jsonDeserializer().deserialize(readFile(file));
    }

    public void write(Jornada model) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        writeToFile(JornadaSerial.jsonSerializer().serialize(model), file);
    }

    private void writeToFile(String str, File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            try {
                writer.write(str);
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);

            try {
                return scanner.useDelimiter("\\A").next();
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
