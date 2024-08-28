package com.ua.project.android_homework_writingcontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ua.project.android_homework_writingcontacts.databinding.ActivityMainBinding;
import com.ua.project.android_homework_writingcontacts.helpers.ContactHelper;
import com.ua.project.android_homework_writingcontacts.models.Contact;

import java.time.LocalDate;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final ActivityResultLauncher<String> writeContactsPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isPermitted -> {
                if(!isPermitted) {
                    Toast.makeText(MainActivity.this, "Permission is required to write a new contact!", Toast.LENGTH_LONG).show();
                }
            }
    );

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.clearFieldsButton.setOnClickListener(v -> {
            clearAllFields();
        });

        binding.saveContactButton.setOnClickListener(v -> {
            if(checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                Contact currentContact = Contact.builder()
                        .firstName(binding.firstNameEditText.getText().toString())
                        .lastName(binding.lastNameEditText.getText().toString())
                        .mobilePhoneNumber(binding.mobilePhoneNumberEditText.getText().toString())
                        .workPhoneNumber(binding.workPhoneNumberEditText.getText().toString())
                        .emailAddress(binding.emailAddressEditText.getText().toString())
                        .birthDate(binding.birthdateEditText.getText().toString())
                        .build();

                ContactHelper.add(MainActivity.this, currentContact);
                clearAllFields();

                Log.d("TAG", "onCreate: " + currentContact);
            }
            else {
                writeContactsPermissionLauncher.launch(Manifest.permission.WRITE_CONTACTS);
            }
        });
    }

    private void clearAllFields() {
        binding.firstNameEditText.setText("");
        binding.lastNameEditText.setText("");
        binding.mobilePhoneNumberEditText.setText("");
        binding.workPhoneNumberEditText.setText("");
        binding.emailAddressEditText.setText("");
        binding.birthdateEditText.setText("");
    }
}