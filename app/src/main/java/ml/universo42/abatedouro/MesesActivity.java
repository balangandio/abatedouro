package ml.universo42.abatedouro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ml.universo42.abatedouro.adapters.MesAdapter;
import ml.universo42.jornada.model.Mes;

public class MesesActivity extends AbstractModelActivity implements ListView.OnItemClickListener {

    ListView lista;

    private MesAdapter adapter;

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_meses;
    }

    protected void initView() {
        lista = findViewById(R.id.list_meses);
        lista.setOnItemClickListener(this);
    }

    protected void updateView() {
        if (model != null) {
            lista.setVisibility(View.VISIBLE);
            adapter = new MesAdapter(model.getJornada(), this);
            lista.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setSelectedMes((Mes) adapter.getItem(position));
        startActivityWithModel(MesDetailActivity.class);
    }

}
