package ml.universo42.abatedouro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;

import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.util.DateUtil;
import ml.universo42.jornada.JornadaFile;

public class MainActivity extends AbstractModelActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 2464;

    TextView txtTotal;
    View layoutSaldoJornada;

    @Override
    protected int getContentViewRsc() {
        return R.layout.activity_main;
    }

    protected void initView() {
        txtTotal = findViewById(R.id.txt_total);
        layoutSaldoJornada = findViewById(R.id.saldo_jornada);
    }

    @Override
    protected void loadModel() {
        if (checkAndRequestPermissions(new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE })) {
            super.loadModel();
        }
    }

    protected void updateView() {
        if (model != null) {
            long saldo = model.getJornada().getSaldoMinutos();

            txtTotal.setText(DateUtil.toStringTime(saldo));

            if (saldo < 0) {
                layoutSaldoJornada.setBackgroundResource(R.drawable.bg_saldo_negativo);
            } else if (saldo > 0) {
                layoutSaldoJornada.setBackgroundResource(R.drawable.bg_saldo_positivo);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE
                && IntStream.of(grantResults)
                    .anyMatch(result -> result == PackageManager.PERMISSION_DENIED)) {
            finish();
        } else if (model == null) {
            super.loadModel();
            updateView();
        }
    }

    public void onClickSaldo(View view) {
        startActivityWithModel(MesesActivity.class);
    }

    public void onClickRegistrar(View view) {
        startActivityWithModel(RegistroActivity.class);
    }

    private boolean checkAndRequestPermissions(String[] permissions) {
        permissions = Stream.of(permissions)
                .filter(p -> !checkPermission(p))
                .toArray(String[]::new);

        if (permissions.length > 0) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
            return false;
        }

        return true;
    }

    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = this.checkSelfPermission(permission);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

}
