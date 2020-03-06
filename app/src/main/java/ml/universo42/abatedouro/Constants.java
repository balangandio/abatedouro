package ml.universo42.abatedouro;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String PARENT_DIR = Environment.getExternalStorageDirectory() + "/Documents";
    public static final File PARENT_FILE = new File(PARENT_DIR);
    public static final File MODEL_FILE = new File(PARENT_FILE, "abatedouro.json");

    public static final String MODEL_PARAM = "jornada";

}
