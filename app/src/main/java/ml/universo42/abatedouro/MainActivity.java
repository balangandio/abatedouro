package ml.universo42.abatedouro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.JornadaFile;
import ml.universo42.jornada.model.Turno;

public class MainActivity extends AppCompatActivity {

    TextView txtTotal;

    JornadaFile modelFile = new JornadaFile(Constants.MODEL_FILE);
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        loadModel();
    }

    public void onClickSaldo(View view) {
        Intent intent = new Intent(this, MesesActivity.class);

        intent.putExtra(Constants.MODEL_PARAM, model);

        startActivity(intent);
    }

    private void initView() {
        txtTotal = (TextView) findViewById(R.id.txt_total);
    }

    private void loadModel() {
        this.model = new Model(this.modelFile.load());

        updateView();
    }

    private void updateView() {
        txtTotal.setText(DateUtil.toStringTime(model.getJornada().getSaldoMinutos()));
    }

}
