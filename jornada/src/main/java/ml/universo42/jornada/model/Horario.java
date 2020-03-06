package ml.universo42.jornada.model;

import org.joda.time.LocalTime;

public class Horario implements Comparable<Horario> {

    private LocalTime time;

    public Horario(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Horario horario) {
        return time.compareTo(horario.time);
    }
}
