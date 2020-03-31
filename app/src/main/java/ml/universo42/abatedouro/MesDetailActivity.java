package ml.universo42.abatedouro;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ml.universo42.abatedouro.adapters.DiaAdapter;
import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.model.Mes;

public class MesDetailActivity extends AbstractModelActivity implements ListView.OnItemClickListener {

    TextView txtMes;
    ListView listDias;
    Button btnRemover;

    private Mes mesSelecionado;

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_mes_detail;
    }

    protected void initView() {
        this.txtMes = findViewById(R.id.txt_detail_mes);
        this.listDias = findViewById(R.id.list_dias);
        this.listDias.setOnItemClickListener(this);
        this.btnRemover = findViewById(R.id.btn_remover_mes);
    }

    @Override
    protected void afterModelLoad(Model model) {
        mesSelecionado = model.selectedMes();
    }

    protected void updateView() {
        if (mesSelecionado != null) {
            txtMes.setText(mesSelecionado.getDescricao() + " - " + DateUtil.shortMonth(mesSelecionado.getMes().getMonthOfYear() - 1));
            listDias.setAdapter(new DiaAdapter(model.getJornada(), mesSelecionado, this));

            btnRemover.setVisibility(mesSelecionado.getDias().isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        model.setSelectedDia(position);
        startActivityWithModel(DiaDetailActivity.class);
    }

    public void onClickRemover(View view) {
        model.getJornada().remove(mesSelecionado)
            .ifPresentOrElse(mes -> {
                Toast.makeText(this, "Removido!", Toast.LENGTH_SHORT).show();
                commitModel();
                finish();
            }, () -> {
                Toast.makeText(this, "Não foi possível remover", Toast.LENGTH_SHORT).show();
            });
    }

}
