package ml.universo42.abatedouro.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.util.List;

import ml.universo42.abatedouro.R;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.Jornada;
import ml.universo42.jornada.model.Mes;

public class MesAdapter extends BaseAdapter {

    private List<Mes> list;
    private Jornada jornada;
    private Activity act;

    public MesAdapter(Jornada jornada, Activity act) {
        this.jornada = jornada;
        this.list = Stream.of(jornada.getMeses()).sorted(Mes.REVERSE_COMPARATOR).toList();
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

        View listaLayout = layoutInflater.inflate(R.layout.lista_mes_layout, parent, false);

        Mes mes = list.get(position);

        setText(listaLayout, R.id.txt_mes, mes.getDescricao());
        setText(listaLayout, R.id.txt_saldo_mensal, DateUtil.toStringTime(jornada.getSaldoMinutos(mes)));
        setText(listaLayout, R.id.txt_saldo_geral, DateUtil.toStringTime(jornada.getSaldoMinutosUntil(mes)));

        if (position == list.size() - 1) {
            LinearLayout linearLayout = listaLayout.findViewById(R.id.linear_layout_lista_mes);
            linearLayout.setPadding(0, 0, 0, 10);
        }

        listaLayout.setBackgroundResource((position + 1) % 2 == 0 ? R.color.list_bg_1 : R.color.list_bg_2);

        return listaLayout;
    }

    private void setText(View view, int txtViewId, String value) {
        TextView txtView = view.findViewById(txtViewId);
        txtView.setText(value);
    }
}
