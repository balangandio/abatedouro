package ml.universo42.abatedouro.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ml.universo42.jornada.Jornada;
import ml.universo42.jornada.JornadaSerial;
import ml.universo42.jornada.model.Dia;
import ml.universo42.jornada.model.Mes;

public class Model implements Serializable {

    private transient Jornada jornada;
    private int selectedMes = -1;
    private int selectedDia = -1;

    public Model() {
    }

    public Model(Jornada jornada) {
        this.jornada = jornada;
    }

    public Jornada getJornada() {
        return jornada;
    }


    public void setSelectedMes(int index) {
        this.selectedMes = index;
    }

    public void setSelectedDia(int index) {
        this.selectedDia = index;
    }

    public Mes selectedMes() {
        return selectedMes == -1 ? null : jornada.getMeses().get(selectedMes);
    }

    public Dia selectedDia() {
        return selectedDia == -1 ? null : selectedMes().getDias().get(selectedDia);
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(JornadaSerial.jsonSerializer().serialize(jornada));
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();

        String json = (String) ois.readObject();

        this.jornada = JornadaSerial.jsonDeserializer().deserialize(json);
    }

}
