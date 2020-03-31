package ml.universo42.abatedouro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

public class DateEditActivity extends AppCompatActivity {

    public static final int DATE_EDIT_RESULT_CODE = 7355;
    public static final String DATE_EDIT_PARAM = "date_edit";

    TextView txtDate;
    NumberPicker dayPicker;
    NumberPicker monthPicker;
    NumberPicker yearPicker;

    private LocalDate date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_edit);

        initView();
        loadData();
        updateView();
    }

    private void initView() {
        txtDate = findViewById(R.id.txt_date_edit);

        dayPicker = findViewById(R.id.num_picker_day);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        monthPicker = findViewById(R.id.num_picker_month);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        yearPicker = findViewById(R.id.num_picker_year);
        yearPicker.setMinValue(1970);
        yearPicker.setMaxValue(9999);
    }

    private void loadData() {
        date = (LocalDate) getIntent().getSerializableExtra(DATE_EDIT_PARAM);
    }

    private void updateView() {
        if (date != null) {
            txtDate.setText(date.toString("dd/MM/yyyy"));

            yearPicker.setValue(date.getYear());
            monthPicker.setValue(date.getMonthOfYear());
            dayPicker.setValue(date.getDayOfMonth());
        }
    }

    public void onClickConfirmar(View view) {
        LocalDate date = null;

        try {
            date = LocalDate.now()
                    .withDayOfMonth(1)
                    .withYear(yearPicker.getValue())
                    .withMonthOfYear(monthPicker.getValue())
                    .withDayOfMonth(dayPicker.getValue());
        } catch(IllegalArgumentException e) {
            Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(DATE_EDIT_PARAM, date);
        setResult(DATE_EDIT_RESULT_CODE, intent);
        finish();
    }
}
