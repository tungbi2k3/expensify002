package com.example.expensify;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.expensify.RoomDB.AppDatabase;
import com.example.expensify.RoomDB.Info;

public class MainActivity extends AppCompatActivity {

    EditText editTextDate;
    EditText editTextExpenseName;
    EditText editTextExpenseAmount, editTextExpenseLocation;
    Spinner spinnerExpenseCategory;
    SwitchCompat switchExpensePaid;
    Button buttonAddExpense;
    ImageButton calendar01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        // Ánh xạ các thành phần trong layout

        editTextDate = findViewById(R.id.editTextDate);
        editTextExpenseName = findViewById(R.id.editTextExpenseName);
        editTextExpenseAmount = findViewById(R.id.editTextExpenseAmount);
        spinnerExpenseCategory = findViewById(R.id.spinnerExpenseCategory);
        switchExpensePaid = findViewById(R.id.switchExpensePaid);
        editTextExpenseLocation=findViewById(R.id.editTextExpenseLocation);
        buttonAddExpense = findViewById(R.id.buttonAddExpense);
        calendar01=findViewById(R.id.calendar01);
        // Đặt sự kiện click cho nút "Thêm"

        calendar01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buttonAddExpense.setOnClickListener(view -> {
            // Lấy thông tin từ các trường nhập liệu
            String date = editTextDate.getText().toString();
            String expenseName = editTextExpenseName.getText().toString();
            String expenseAmount = editTextExpenseAmount.getText().toString();
            String expenseLocation = editTextExpenseLocation.getText().toString();
            String expenseCategory = spinnerExpenseCategory.getSelectedItem().toString();
            boolean expensePaid = switchExpensePaid.isChecked();

            // Xử lý dữ liệu theo nhu cầu của ứng dụng
            // (ví dụ: lưu vào cơ sở dữ liệu, thực hiện các thao tác khác)

            if(date.isEmpty() || expenseName.isEmpty() || expenseAmount.isEmpty() || expenseCategory.isEmpty() || expenseLocation.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // Xử lý dữ liệu theo nhu cầu của ứng dụng
            // (ví dụ: lưu vào cơ sở dữ liệu, thực hiện các thao tác khác)
            new Thread (() -> {
                Info info = new Info();
                info.name = expenseName;
                info.amount = expenseAmount;
                info.category = expenseCategory;
                info.address = expenseLocation;
                info.paid = expensePaid;
                info.date = date;

                db.infoDAO().insert(info);
                Log.d("TAG", "Inserted: " + info);
            }).start();

            // Hiển thị thông báo hoặc thực hiện các hành động khác
            Toast.makeText(MainActivity.this, "Đã thêm khoản chi phí: " + expenseName +"("+expenseLocation+")", Toast.LENGTH_SHORT).show();
        });
    }
    private void openDialog(){
        DatePickerDialog dialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextDate.setText(String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth));
            }
        }, 2024, 5, 17);
        dialog.show();
    }
}