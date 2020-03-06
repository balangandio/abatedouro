package ml.universo42.jornada;

import com.annimon.stream.Stream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ml.universo42.jornada.model.Mes;

public class Jornada implements Serializable {

    private List<Mes> meses;
    private int jornadaDiaria = 8;

    public Jornada(List<Mes> meses) {
        this.meses = meses;
    }

    public Jornada(List<Mes> meses, int jornadaDiaria) {
        this(meses);
        this.jornadaDiaria = jornadaDiaria;
    }

    public List<Mes> getMeses() {
        return meses;
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


    public static Jornada empty() {
        return new Jornada(new ArrayList<>());
    }

}
