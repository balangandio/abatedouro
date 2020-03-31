package ml.universo42.abatedouro.model;

import org.joda.time.LocalTime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ml.universo42.jornada.Jornada;
import ml.universo42.jornada.JornadaSerial;
import ml.universo42.jornada.model.Dia;
import ml.universo42.jornada.model.Mes;
import ml.universo42.jornada.model.Turno;

public class Model implements Serializable {

    private transient Jornada jornada;
    private int selectedMes = -1;
    private int selectedDia = -1;
    private LocalTime selectedHorario;

    public Model() {
    }

    public Model(Jornada jornada) {
        this.jornada = jornada;
    }

    public Jornada getJornada() {
        return jornada;
    }


    public void setSelectedMes(Mes mes) {
        this.selectedMes = jornada.getMeses().indexOf(mes);
    }

    public void setSelectedDia(int index) {
        this.selectedDia = index;
    }

    public void setSelectedHorario(LocalTime horario) {
        this.selectedHorario = horario;
    }

    public Mes selectedMes() {
        return selectedMes == -1 ? null : jornada.getMeses().get(selectedMes);
    }

    public Dia selectedDia() {
        return selectedDia == -1 ? null : selectedMes().getDias().get(selectedDia);
    }

    public LocalTime selectedHorario() {
        return selectedHorario;
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
