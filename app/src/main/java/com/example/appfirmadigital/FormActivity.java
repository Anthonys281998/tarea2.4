package com.example.appfirmadigital;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class FormActivity extends AppCompatActivity {

    private EditText editTextDescription;
    private SignatureView signatureView;
    private Button buttonSave;
    private SignaturesDatabaseHelper dbHelper;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);





        editTextDescription = findViewById(R.id.editTextDescription);
        signatureView = findViewById(R.id.signatureView);
        buttonSave = findViewById(R.id.buttonSave);
        dbHelper = new SignaturesDatabaseHelper(this);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveSignature();

            }
        });
    }

    private void saveSignature() {
        String description = editTextDescription.getText().toString();

        Bitmap signatureBitmap = signatureView.getSignatureBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Guardar la firma en la base de datos
        dbHelper.addSignature(description, byteArray);

        // Limpiar el formulario después de guardar
        editTextDescription.setText("");

    }

    @Override
    protected void onDestroy() {
        // Cerrar la base de datos cuando la actividad se destruya
        dbHelper.close();
        super.onDestroy();
    }
}
