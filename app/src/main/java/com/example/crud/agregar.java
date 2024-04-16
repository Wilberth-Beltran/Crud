package com.example.crud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class agregar extends AppCompatActivity {

    EditText t_nombre, t_correo, t_direccion;
    Button b_insertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        t_nombre=findViewById(R.id.txtnombre);
        t_correo=findViewById(R.id.txtcorreo);
        t_direccion=findViewById(R.id.txtdireccion);
        b_insertar=findViewById(R.id.btnagregar);

        b_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        }
         );
    }
    public void insertarDatos() {
        final String nombre = t_nombre.getText().toString().trim();
        final String correo = t_correo.getText().toString().trim();
        final String direccion = t_direccion.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando");

        if (nombre.isEmpty()) {
            Toast.makeText(this, "ingrese nombre", Toast.LENGTH_SHORT).show();
            return;
        } else if (correo.isEmpty()) {
            Toast.makeText(this, "ingrese correo", Toast.LENGTH_SHORT).show();
            return;
        } else if (direccion.isEmpty()) {
            Toast.makeText(this, "ingrese direccion", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://devapps.lol/insertar_.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(agregar.this, "datos insertados", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        finish();
                    } else {
                        Toast.makeText(agregar.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(agregar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("nombre", nombre);
                    params.put("correo", correo);
                    params.put("direccion", direccion);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(agregar.this);
            requestQueue.add(request);
        }
    }
        @Override
        public void onBackPressed(){
            super.onBackPressed();
            finish();

        }
}