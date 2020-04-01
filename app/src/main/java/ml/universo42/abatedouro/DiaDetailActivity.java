package ml.universo42.abatedouro;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ml.universo42.abatedouro.adapters.TurnoAdapter;
import ml.universo42.abatedouro.dialogs.HorarioChooserDialog;
import ml.universo42.abatedouro.model.Model;
import ml.universo42.jornada.model.Dia;
import ml.universo42.jornada.model.Horario;
import ml.universo42.jornada.model.Turno;

public class DiaDetailActivity extends AbstractModelActivity implements ListView.OnItemClickListener {

    TextView txtDia;
    ListView listHorarios;
    TurnoAdapter turnoAdapter;
    Button btnRemover;

    private Turno selectedTurno;
    private Dia selectedDia;

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_dia_detail;
    }

    @Override
    protected void initView() {
        txtDia = findViewById(R.id.txt_detail_dia);
        listHorarios = findViewById(R.id.list_horarios);
        listHorarios.setOnItemClickListener(this);
        btnRemover = findViewById(R.id.btn_remover_dia);
    }

    @Override
    protected void afterModelLoad(Model model) {
        selectedDia = model.selectedDia();
    }

    @Override
    protected void updateView() {
        if (selectedDia != null) {
            txtDia.setText(selectedDia.getDescricao(model.selectedMes()));
            List<Turno> turnos = selectedDia.getTurnos();
            turnoAdapter = new TurnoAdapter(turnos, this);
            listHorarios.setAdapter(turnoAdapter);

            btnRemover.setVisibility(turnos.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    public void onClickRemover(View view) {
        model.selectedMes().removeDia(selectedDia)
            .ifPresentOrElse(dia -> {
                Toast.makeText(this, "Removido!", Toast.LENGTH_SHORT).show();
                commitModel();
                finish();
            }, () -> {
                Toast.makeText(this, "Não foi possível remover", Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedTurno = (Turno) turnoAdapter.getItem(position);

        if (selectedTurno.getFim() == null) {
            startEditActivity(selectedTurno.getInicio());
            return;
        }

        AlertDialog dialog = new HorarioChooserDialog(
                this,
                selectedTurno.getInicio(),
                selectedTurno.getFim(),
                this::startEditActivity
        );

        dialog.show();
    }

    private void startEditActivity(Horario horario) {
        model.setSelectedHorario(horario.getTime());
        startActivityWithModel(HorarioActivity.class);
    }

}
