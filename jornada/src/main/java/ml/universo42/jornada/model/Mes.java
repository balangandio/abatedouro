package ml.universo42.jornada.model;

import com.annimon.stream.Stream;

import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.List;

import ml.universo42.jornada.Jornada;

public class Mes implements Comparable<Mes> {

    private static final String MONTH_PATTERN = "MM/yyyy";
    private static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(MONTH_PATTERN);

    public YearMonth mes;
    private List<Dia> dias;

    public Mes(YearMonth mes, List<Dia> dias) {
        setMonth(mes);
        setDias(dias);
    }

    public YearMonth getMes() {
        return mes;
    }

    public String getDescricao() {
        return mes.toString(MONTH_PATTERN);
    }

    public List<Dia> getDias() {
        return dias;
    }

    private void setMonth(YearMonth month) {
        this.mes = month;
    }

    private void setDias(List<Dia> dias) {
        this.dias = dias;
    }

    public long getMinutosSaldo(int jornadaDiaria) {
        return Stream.of(getDias()).mapToLong(d -> d.getMinutosRestantes(jornadaDiaria)).sum();
    }

    public long getMinutosSaldoUntil(Dia dia, Jornada jornada) {
        int index = dias.indexOf(dia);

        if (index != -1) {
            return Stream.of(dias.subList(0, index + 1)).mapToLong(d -> d.getMinutosRestantes(jornada.getJornadaDiaria())).sum();
        }

        return 0;
    }

    @Override
    public int compareTo(Mes o) {
        return mes.compareTo(o.mes);
    }


    public static Mes build(String month, List<Dia> dias) {
        return new Mes(YearMonth.parse(month, FORMATTER), Collections.unmodifiableList(dias));
    }
}
