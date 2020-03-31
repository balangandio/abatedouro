package ml.universo42.abatedouro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalTime;

import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.model.Dia;

public class HorarioActivity extends AbstractModelActivity implements DialogInterface.OnClickListener {

    TextView txtHorario;
    NumberPicker hourPicker;
    NumberPicker minutePicker;

    private LocalTime horario;
    private Dia diaSelecionado;

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_horario;
    }

    protected void initView() {
        txtHorario = findViewById(R.id.txt_edit_horario);
        hourPicker = findViewById(R.id.num_picker_hour_edit);
        minutePicker = findViewById(R.id.num_picker_minute_edit);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);

        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
    }

    @Override
    protected void afterModelLoad(Model model) {
        diaSelecionado = model.selectedDia();
        horario = model.selectedHorario();
    }

    @Override
    protected void updateView() {
        txtHorario.setText(DateUtil.toStringTime(horario));
        hourPicker.setValue(horario.getHourOfDay());
        minutePicker.setValue(horario.getMinuteOfHour());
    }

    public void onClickConfirmarUpdate(View view) {
        LocalTime newHorario = horario
                .withHourOfDay(hourPicker.getValue())
                .withMinuteOfHour(minutePicker.getValue());

        if (diaSelecionado.contains(newHorario)) {
            Toast.makeText(this, "Horário já presente", Toast.LENGTH_SHORT).show();
            return;
        }

        diaSelecionado.update(horario, newHorario)
        .ifPresentOrElse(h -> {
            Toast.makeText(this, "Atualizado!", Toast.LENGTH_SHORT).show();
            finishAndReturnModel();
        }, () -> Toast.makeText(this, "Não foi possível alterar", Toast.LENGTH_SHORT)
                .show());
    }

    public void onClickRemover(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("confirmar remoção do horário?")
                .setNegativeButton("Cancel", this)
                .setPositiveButton("Ok", this)
                .create();

        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
        if (selectedIndex == -1) {
            diaSelecionado.remove(horario).ifPresentOrElse(h -> {
                Toast.makeText(this, "Removido!", Toast.LENGTH_SHORT).show();
                finishAndReturnModel();
            }, () -> Toast.makeText(this, "Não foi possível remover", Toast.LENGTH_SHORT)
                    .show());
        }
    }

    private void finishAndReturnModel() {
        commitModel();
        finish();
    }

}
