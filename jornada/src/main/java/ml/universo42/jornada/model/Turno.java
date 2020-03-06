package ml.universo42.jornada.model;

import org.joda.time.Minutes;

public class Turno {

    private Horario inicio;
    private Horario fim;

    public Turno(Horario inicio, Horario fim) {
        setInicio(inicio);
        setFim(fim);
    }

    public Horario getInicio() {
        return inicio;
    }

    private void setInicio(Horario inicio) {
        this.inicio = inicio;
    }

    public Horario getFim() {
        return fim;
    }

    private void setFim(Horario fim) {
        this.fim = fim;
    }

    public long getMinutos() {
        if (fim == null) {
            return 0;
        }

        return Minutes.minutesBetween(inicio.getTime(), fim.getTime()).getMinutes();
    }

}
