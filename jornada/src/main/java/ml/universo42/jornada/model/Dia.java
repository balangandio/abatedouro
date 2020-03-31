package ml.universo42.jornada.model;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class Dia implements Comparable<Dia> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("HH:mm");

    private int diaMes;
    private List<Horario> horarios;

    public Dia(int diaMes) {
        this(diaMes, new ArrayList<>());
    }

    public Dia(int diaMes, List<Horario> horarios) {
        this.diaMes = diaMes;
        this.horarios = new ArrayList(horarios);
    }

    public int getDiaMes() {
        return diaMes;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public List<Turno> getTurnos() {
        List<Horario> horarios = Stream.of(this.horarios).sorted().toList();

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

    public boolean contains(LocalTime time) {
        return Stream.of(horarios)
                .map(h -> h.getTime().toString(FORMATTER))
                .toList().contains(time.toString(FORMATTER));
    }

    public Optional<Horario> add(LocalTime time) {
        Horario newOne = null;

        if (!contains(time)) {
            newOne = new Horario(time);
            horarios.add(newOne);
        }

        return Optional.ofNullable(newOne);
    }

    public Optional<Horario> remove(LocalTime time) {
        return Stream.of(horarios)
                .filter(h -> h.getTime().toString(FORMATTER).equals(time.toString(FORMATTER)))
                .findFirst()
                .map(h -> {
                    horarios.remove(h);
                    return h;
                });
    }

    public Optional<Horario> update(LocalTime oldTime, LocalTime newTime) {
        if (!contains(newTime)) {
            return remove(oldTime).flatMap(h -> add(newTime));
        }

        return Optional.empty();
    }

    @Override
    public int compareTo(Dia dia) {
        return diaMes - dia.diaMes;
    }
}
