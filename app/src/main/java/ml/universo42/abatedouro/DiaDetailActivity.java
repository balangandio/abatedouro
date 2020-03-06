package ml.universo42.abatedouro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ml.universo42.abatedouro.adapters.TurnoAdapter;
import ml.universo42.abatedouro.model.Model;
import ml.universo42.jornada.model.Dia;

public class DiaDetailActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    TextView txtDia;
    ListView listHorarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_detail);

        initView();

        loadModel();
    }

    private void initView() {
        txtDia = findViewById(R.id.txt_detail_dia);

        listHorarios = findViewById(R.id.list_horarios);
        listHorarios.setOnItemClickListener(this);
    }

    private void loadModel() {
        Model model = (Model) getIntent().getSerializableExtra(Constants.MODEL_PARAM);

        Dia diaSelecionado = model.selectedDia();

        if (diaSelecionado != null) {
            txtDia.setText(diaSelecionado.getDescricao(model.selectedMes()));
            listHorarios.setAdapter(new TurnoAdapter(diaSelecionado.getTurnos(), this));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
