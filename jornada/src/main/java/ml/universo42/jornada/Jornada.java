package ml.universo42.jornada;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ml.universo42.jornada.model.Mes;

public class Jornada implements Serializable {

    private List<Mes> meses;
    private int jornadaDiaria = 8;

    public Jornada(List<Mes> meses) {
        this.meses = new ArrayList<>(meses);
    }

    public Jornada(List<Mes> meses, int jornadaDiaria) {
        this(meses);
        this.jornadaDiaria = jornadaDiaria;
    }

    public List<Mes> getMeses() {
        return Collections.unmodifiableList(meses);
    }

    public int getJornadaDiaria() {
        return jornadaDiaria;
    }

    public void setJornadaDiaria(int jornadaDiaria) {
        this.jornadaDiaria = jornadaDiaria;
    }


    public long getSaldoMinutos() {
        return Stream.of(getMeses())
                .mapToLong(mes -> mes.getMinutosSaldo(getJornadaDiaria())).sum();
    }

    public long getSaldoMinutos(Mes mes) {
        return mes.getMinutosSaldo(getJornadaDiaria());
    }

    public long getSaldoMinutosUntil(Mes mes) {
        int index = meses.indexOf(mes);

        if (index != -1) {
            return Stream.of(meses.subList(0, index + 1))
                    .mapToLong(m -> m.getMinutosSaldo(getJornadaDiaria())).sum();
        }

        return 0;
    }

    public Optional<Mes> getMes(YearMonth yearMonth) {
        return Stream.of(meses).filter(mes -> mes.getMes().equals(yearMonth)).findFirst();
    }


    public void add(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(time.toString(formatter), formatter);

        Optional<Mes> mesOp = getMes(yearMonth);

        Mes mes = mesOp.orElse(new Mes(yearMonth));

        mes.addDia(time.getDayOfMonth(), time.toLocalTime());

        if (mesOp.isEmpty()) {
            meses.add(mes);
        }
    }

    public Optional<Mes> remove(Mes mes) {
        return getMes(mes.getMes()).map(m -> {
            meses.remove(mes);
            return m;
        });
    }


    public static Jornada empty() {
        return new Jornada(new ArrayList<>());
    }

}
