package ml.universo42.abatedouro.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ml.universo42.abatedouro.R;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.model.Turno;

public class TurnoAdapter extends BaseAdapter {

    private List<Turno> list;
    private Activity act;

    public TurnoAdapter(List<Turno> turnos, Activity act) {
        this.list = turnos;
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

        View listaLayout = layoutInflater.inflate(R.layout.lista_turno_layout, parent, false);

        Turno turno = list.get(position);

        setText(listaLayout, R.id.txt_horario_inicio, DateUtil.toStringTime(turno.getInicio().getTime()));

        if (turno.getFim() != null) {
            setText(listaLayout, R.id.txt_horario_fim, DateUtil.toStringTime(turno.getFim().getTime()));
            setText(listaLayout, R.id.txt_saldo_turno, DateUtil.toStringTime(turno.getMinutos()));
        } else {
            setText(listaLayout, R.id.txt_horario_fim, "--:--");
            setText(listaLayout, R.id.txt_saldo_turno, " ");
        }

        listaLayout.setBackgroundResource((position + 1) % 2 == 0 ? R.color.list_bg_1 : R.color.list_bg_2);

        return listaLayout;
    }

    private void setText(View view, int txtViewId, String value) {
        TextView txtView = (TextView) view.findViewById(txtViewId);
        txtView.setText(value);
    }
}