package ml.universo42.abatedouro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ml.universo42.abatedouro.adapters.MesAdapter;
import ml.universo42.abatedouro.model.Model;

public class MesesActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView lista;

    private Model model;
    private MesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meses);

        initView();

        loadJornada();
    }

    private void initView() {
        lista = findViewById(R.id.list_meses);
        lista.setOnItemClickListener(this);
    }

    private void loadJornada() {
        model = (Model) getIntent().getSerializableExtra(Constants.MODEL_PARAM);

        lista.setVisibility(View.VISIBLE);
        adapter = new MesAdapter(model.getJornada(), this);
        lista.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setSelectedMes(position);

        Intent intent = new Intent(this, MesDetailActivity.class);
        intent.putExtra(Constants.MODEL_PARAM, model);

        startActivity(intent);
    }

}
