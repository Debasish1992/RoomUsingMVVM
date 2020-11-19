package com.e.roomandmvvm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.roomandmvvm.R;

public class AddNote extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.e.roomandmvvm.EXTRA_TITLE";
    public static final String EXTRA_DESC = "com.e.roomandmvvm.EXTRA_DESC";
    public static final String EXTRA_PRIORITY = "com.e.roomandmvvm.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.e.roomandmvvm.EXTRA_ID";

    Toolbar toolbar;
    EditText etTitle, etDesc, etPriority;
    Button btnSaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24px);
        etTitle = findViewById(R.id.etNoteTitle);
        etDesc = findViewById(R.id.etNoteDesc);
        etPriority = findViewById(R.id.etNotePriority);
        btnSaveNote = findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(v -> validateUserInputs());

        Intent intent = getIntent();
        if(intent.hasExtra(AddNote.EXTRA_ID)){
            //setting the title
            toolbar.setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDesc.setText(intent.getStringExtra(EXTRA_DESC));
            etPriority.setText(String.valueOf(intent.getIntExtra(EXTRA_PRIORITY, 1)));
        }else{
            //setting the title
            toolbar.setTitle("Add Note");
        }
    }


    void validateUserInputs() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String getPriority = etPriority.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Title can not be blank", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(desc)) {
            Toast.makeText(this, "Desc can not be blank", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(getPriority)) {
            Toast.makeText(this, "Priority can not be blank", Toast.LENGTH_SHORT).show();
        }else{
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESC, desc);
            data.putExtra(EXTRA_PRIORITY, Integer.parseInt(getPriority));
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if(id != -1){
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }
}