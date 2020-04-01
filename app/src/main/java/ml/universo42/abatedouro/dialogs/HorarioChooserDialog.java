package ml.universo42.abatedouro.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.function.Consumer;

import ml.universo42.abatedouro.R;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.model.Horario;

public class HorarioChooserDialog extends AlertDialog implements View.OnClickListener {

    private Horario inicio;
    private Horario fim;
    private HorarioConsumer listener;

    public HorarioChooserDialog(Context context, Horario inicio, Horario fim, HorarioConsumer listener) {
        super(context);
        this.inicio = inicio;
        this.fim = fim;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horario_dialog);

        initView();
    }

    private void initView() {
        Button btnCancel = findViewById(R.id.horario_dialog_btn_cancel);
        btnCancel.setOnClickListener(this);
        btnCancel.setText(DateUtil.toStringTime(inicio.getTime()));

        Button btnOk = findViewById(R.id.horario_dialog_btn_ok);
        btnOk.setOnClickListener(this);
        btnOk.setText(DateUtil.toStringTime(fim.getTime()));
    }

    @Override
    public void onClick(View view) {
        dismiss();

        if (view.getId() == R.id.horario_dialog_btn_cancel) {
            listener.accept(inicio);
        } else if (view.getId() == R.id.horario_dialog_btn_ok) {
            listener.accept(fim);
        }
    }

    public interface HorarioConsumer {
        void accept(Horario horario);
    }

}
