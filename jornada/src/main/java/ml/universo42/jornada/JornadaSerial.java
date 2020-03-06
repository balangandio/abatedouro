package ml.universo42.jornada;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.LocalTime;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ml.universo42.jornada.interfaces.Deserializer;
import ml.universo42.jornada.interfaces.Serializer;
import ml.universo42.jornada.model.Dia;
import ml.universo42.jornada.model.Horario;
import ml.universo42.jornada.model.Mes;

public class JornadaSerial {

    public static Serializer<Jornada> jsonSerializer() {
        return new JsonSerializerDeserializer();
    }

    public static Deserializer<Jornada> jsonDeserializer() {
        return new JsonSerializerDeserializer();
    }

}

class JsonSerializerDeserializer implements Serializer<Jornada>, Deserializer<Jornada> {

    private static DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");
    private static final String MONTH_FORMATTER_STR = "MM/yyyy";
    private static DateTimeFormatter MONTH_FORMATTER = DateTimeFormat.forPattern(MONTH_FORMATTER_STR);

    @Override
    public Jornada deserialize(String json) {
        return fromJson(json);
    }

    public Jornada fromJson(String json) {
        try {
            JSONObject modelObj = new JSONObject(json);

            JSONArray mesesArray = (JSONArray) modelObj.get("meses");

            List<JSONObject> list = new ArrayList<>();
            for (int i = 0; i < mesesArray.length(); i++) {
                list.add(mesesArray.getJSONObject(i));
            }

            List<Mes> meses = Stream.of(list).map(this::parseMes).sorted().collect(Collectors.toList());

            return new Jornada(meses, (Integer) modelObj.get("jornadaDiaria"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Mes parseMes(JSONObject obj) {
        try {
            YearMonth yearMonth = YearMonth.parse(obj.getString("mes"), MONTH_FORMATTER);

            JSONArray diasArray = obj.getJSONArray("dias");

            List<JSONObject> list = new ArrayList<>();
            for (int i = 0; i < diasArray.length(); i++) {
                list.add(diasArray.getJSONObject(i));
            }

            List<Dia> dias = Stream.of(list).map(this::parseDia).sorted().collect(Collectors.toList());

            return new Mes(yearMonth, dias);
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Dia parseDia(JSONObject diaObj) {
        JSONArray array = diaObj.getJSONArray("horarios");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }

        List<Horario> horarios = Stream.of(list)
                .map(str -> new Horario(LocalTime.parse(str, TIME_FORMATTER)))
                .sorted()
                .collect(Collectors.toList());

        return new Dia(diaObj.getInt("diaMes"), horarios);
    }


    @Override
    public String serialize(Jornada obj) {
        return toJson(obj);
    }

    public String toJson(Jornada model) {
        JSONObject modelObj = new JSONObject();

        JSONArray array = new JSONArray();
        Stream.of(model.getMeses()).map(this::toJson).forEach(array::put);

        try {
            modelObj.putOpt("meses", array);
            modelObj.put("jornadaDiaria", model.getJornadaDiaria());
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
        return modelObj.toString();
    }

    private JSONObject toJson(Mes mes) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("mes", mes.getMes().toString(MONTH_FORMATTER_STR));

            JSONArray dias = new JSONArray();

            Stream.of(mes.getDias()).map(this::toJson).forEach(dias::put);

            obj.put("dias", dias);

        } catch(JSONException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    private JSONObject toJson(Dia dia) {
        JSONObject diaObj = new JSONObject();
        JSONArray arrayHorarios = new JSONArray();

        Stream.of(dia.getHorarios())
                .map(horario -> horario.getTime().toString(TIME_FORMATTER))
                .forEach(arrayHorarios::put);

        diaObj.put("diaMes", dia.getDiaMes());
        diaObj.put("horarios", arrayHorarios);

        return diaObj;
    }

}
