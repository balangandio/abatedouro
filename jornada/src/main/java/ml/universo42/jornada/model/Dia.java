package ml.universo42.jornada.model;

import com.annimon.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Dia implements Comparable<Dia> {

    private int diaMes;
    private List<Horario> horarios;

    public Dia(int diaMes, List<Horario> horarios) {
        this.diaMes = diaMes;
        this.horarios = horarios;
    }

    public int getDiaMes() {
        return diaMes;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public List<Turno> getTurnos() {
        int max = horarios.size() % 2 == 0 ? horarios.size() : horarios.size() - 1;
        List<Turno> turnos = new ArrayList<>();

        for (int i = 1; i < max; i += 2) {
            turnos.add(new Turno(horarios.get(i - 1), horarios.get(i)));
        }

        if (horarios.size() > 0 && horarios.size() % 2 != 0) {
            turnos.add(new Turno(horarios.get(horarios.size() - 1), null));
        }

        return turnos;
    }

    public String getDescricao(Mes mes) {
        return new DateTime(mes.getMes().getYear(), mes.getMes().getMonthOfYear(), diaMes, 0, 0)
                .toString("dd/MM");
    }

    public long getMinutosDia() {
        return Stream.of(getTurnos()).mapToLong(t -> t.getMinutos()).sum();
    }

    public long getMinutosRestantes(int jornadaDiaria) {
        long minutosDiaTrabalho = 60 * jornadaDiaria;

        return getMinutosDia() - minutosDiaTrabalho;
    }

    @Override
    public int compareTo(Dia dia) {
        return diaMes - dia.diaMes;
    }
}
