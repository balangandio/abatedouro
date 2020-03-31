package ml.universo42.abatedouro;

import android.content.Intent;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.Jornada;

public class RegistroActivity extends AbstractModelActivity {

    private static final int DATE_EDIT_REQUEST_CODE = 1264;

    TextView txtDia;
    NumberPicker hourPicker;
    NumberPicker minutePicker;

    LocalDateTime time = LocalDateTime.now();

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_registro;
    }

    protected void initView() {
        txtDia = findViewById(R.id.txt_dia_registro);
        hourPicker = findViewById(R.id.num_picker_hour);
        minutePicker = findViewById(R.id.num_picker_minute);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
    }

    protected void updateView() {
        updateDateText();
        hourPicker.setValue(time.getHourOfDay());
        minutePicker.setValue(time.getMinuteOfHour());
    }

    private void updateDateText() {
        txtDia.setText(time.toString("dd/MM/yyyy"));
    }

    public void onClickConfirmar(View view) {
        time = time.withHourOfDay(hourPicker.getValue()).withMinuteOfHour(minutePicker.getValue());

        Jornada jornada = model.getJornada();

        long saldoAnterior = jornada.getSaldoMinutos();

        jornada.add(time);

        commitModel();

        long diff = jornada.getSaldoMinutos() - saldoAnterior;

        if (diff > 0) {
            Toast.makeText(this, "Adicionado! - " + DateUtil.toStringTime(diff), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Adicionado!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void onClickData(View view) {
        Intent intent = new Intent(this, DateEditActivity.class);
        intent.putExtra(DateEditActivity.DATE_EDIT_PARAM, time.toLocalDate());
        startActivityForResult(intent, DATE_EDIT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DATE_EDIT_REQUEST_CODE && resultCode == DateEditActivity.DATE_EDIT_RESULT_CODE) {
            LocalDate date = (LocalDate) data.getSerializableExtra(DateEditActivity.DATE_EDIT_PARAM);

            time = time.withDate(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
            updateDateText();
        }
    }
}
