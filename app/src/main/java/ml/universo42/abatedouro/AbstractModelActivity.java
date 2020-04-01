package ml.universo42.abatedouro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import ml.universo42.abatedouro.model.Model;
import ml.universo42.jornada.JornadaFile;
import ml.universo42.jornada.exceptions.LoadException;

public abstract class AbstractModelActivity extends AppCompatActivity {

    protected static final int ACTIVITY_REQUEST_WITH_MODEL = 6368;
    protected static final int ACTIVITY_RESULT_WITH_MODEL = 8636;

    protected Model model;
    protected JornadaFile modelFile = new JornadaFile(Constants.MODEL_FILE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewRsc());

        initView();

        loadModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    protected void loadModel() {
        Intent intent = getIntent();

        try {
            if (intent.hasExtra(Constants.MODEL_PARAM)) {
                model = (Model) intent.getSerializableExtra(Constants.MODEL_PARAM);
            } else {
                model = new Model(modelFile.load());
            }

            afterModelLoad(model);
        } catch(LoadException e) {
            Toast.makeText(
                    this,
                    "# Erro ao carregar arquivo: " + Constants.MODEL_FILE.getAbsolutePath(),
                    Toast.LENGTH_SHORT
            );
        }
    }

    protected void afterModelLoad(Model model) {}

    protected void startActivityWithModel(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(Constants.MODEL_PARAM, model);
        startActivityForResult(intent, ACTIVITY_REQUEST_WITH_MODEL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_REQUEST_WITH_MODEL && resultCode == ACTIVITY_RESULT_WITH_MODEL) {
            model = (Model) data.getSerializableExtra(Constants.MODEL_PARAM);

            afterModelLoad(model);

            Intent intent = new Intent();
            intent.putExtra(Constants.MODEL_PARAM, model);
            setResult(ACTIVITY_RESULT_WITH_MODEL, intent);
        }
    }

    protected void commitModel() {
        modelFile.write(model.getJornada());

        Intent intent = new Intent();
        intent.putExtra(Constants.MODEL_PARAM, model);
        setResult(ACTIVITY_RESULT_WITH_MODEL, intent);
    }

    protected abstract void initView();
    protected abstract void updateView();
    protected abstract int getContentViewRsc();

}
