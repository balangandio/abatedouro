package ml.universo42.abatedouro.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ml.universo42.abatedouro.R;

public class ConfirmationDialog extends AlertDialog implements View.OnClickListener {

    private ConfirmationListener listener;
    private String message;

    public ConfirmationDialog(Context context, String message, ConfirmationListener listener) {
        super(context);
        this.message = message;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_dialog);

        initView();
    }

    private void initView() {
        TextView txtMsg = findViewById(R.id.conf_dialog_msg);
        txtMsg.setText(message);

        Button btnCancel = findViewById(R.id.conf_dialog_btn_cancel);
        btnCancel.setOnClickListener(this);

        Button btnOk = findViewById(R.id.conf_dialog_btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();

        if (view.getId() == R.id.conf_dialog_btn_ok) {
            listener.accept(true);
        } else if (view.getId() == R.id.conf_dialog_btn_cancel) {
            listener.accept(false);
        }
    }

    public interface ConfirmationListener {
        void accept(boolean confirmed);
    }

}
