package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastrarCliente;
    private Button btCadastrarItens;
    private Button btLancamentoPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCadastrarCliente = findViewById(R.id.btCadastrarCliente);
        btCadastrarItens = findViewById(R.id.btCadastrarItens);
        btLancamentoPedido = findViewById(R.id.btLancamentoPedido);

        btCadastrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroClienteActivity.class);
            }
        });
        btCadastrarItens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroItensActivity.class);
            }
        });
        btLancamentoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(LancamentoPedidoActivity.class);
            }
        });
    }

    public void abrirActivity(Class<?> activity){
        Intent intent = new Intent(MainActivity.this,activity);
        startActivity(intent);
    }
}