package ml.universo42.abatedouro.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ml.universo42.abatedouro.R;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.Jornada;
import ml.universo42.jornada.model.Dia;
import ml.universo42.jornada.model.Mes;

public class DiaAdapter extends BaseAdapter {

    private List<Dia> list;
    private Mes mes;
    private Jornada jornada;
    private Activity act;

    public DiaAdapter(Jornada jornada, Mes mes, Activity act) {
        this.jornada = jornada;
        this.mes = mes;
        this.list = mes.getDias();
        this.act = act;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.act.getLayoutInflater();

        View listaLayout = layoutInflater.inflate(R.layout.lista_dia_layout, parent, false);

        Dia dia = list.get(position);

        setText(listaLayout, R.id.txt_dia_lista_dia, dia.getDescricao(mes));
        setText(listaLayout, R.id.txt_saldo_diario_lista_dia, DateUtil.toStringTime(dia.getMinutosRestantes(jornada.getJornadaDiaria())));
        setText(listaLayout, R.id.txt_saldo_geral_lista_dia, DateUtil.toStringTime(mes.getMinutosSaldoUntil(dia, jornada)));

        if (position == list.size() - 1) {
            LinearLayout linearLayout = listaLayout.findViewById(R.id.linear_layout_lista_dia);
            linearLayout.setPadding(0, 0, 0, 15);
        }

        return listaLayout;
    }

    private void setText(View view, int txtViewId, String value) {
        TextView txtView = (TextView) view.findViewById(txtViewId);
        txtView.setText(value);
    }
}
