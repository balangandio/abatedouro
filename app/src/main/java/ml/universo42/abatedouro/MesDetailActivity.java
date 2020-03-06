package ml.universo42.abatedouro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ml.universo42.abatedouro.adapters.DiaAdapter;
import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.model.Mes;

public class MesDetailActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    TextView txtMes;
    ListView listDias;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_detail);

        initView();

        loadModel();
    }

    private void initView() {
        this.txtMes = findViewById(R.id.txt_detail_mes);
        this.listDias = findViewById(R.id.list_dias);
        this.listDias.setOnItemClickListener(this);
    }

    private void loadModel() {
        model = (Model) getIntent().getSerializableExtra(Constants.MODEL_PARAM);

        Mes mesSelecionado = model.selectedMes();

        if (mesSelecionado != null) {
            txtMes.setText(mesSelecionado.getDescricao() + " - " + DateUtil.shortMonth(mesSelecionado.getMes().getMonthOfYear() - 1));

            listDias.setAdapter(new DiaAdapter(model.getJornada(), mesSelecionado, this));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setSelectedDia(position);

        Intent intent = new Intent(this, DiaDetailActivity.class);
        intent.putExtra(Constants.MODEL_PARAM, model);

        startActivity(intent);
    }

}
