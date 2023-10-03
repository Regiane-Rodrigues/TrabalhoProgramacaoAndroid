package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class
CadastroItensActivity extends AppCompatActivity {

    private Button btCadastrarItem, btVoltar, btGerarCodigo;
    private EditText edCodigoItem, edDescricaoItem, edValorUnitarioItem;
    private TextView tvListaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_itens);

        btCadastrarItem = findViewById(R.id.btCadastrarItem);
        btVoltar = findViewById(R.id.btVoltarMenu);
        btGerarCodigo = findViewById(R.id.btGerarCodigo);
        edCodigoItem = findViewById(R.id.edCodigoItem);
        edDescricaoItem = findViewById(R.id.edDescricaoItem);
        edValorUnitarioItem = findViewById(R.id.edValorUnitarioItem);
        tvListaItem = findViewById(R.id.tvListaItem);

        atualizarItem();
        btCadastrarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItem(edCodigoItem,edDescricaoItem,edValorUnitarioItem);
                atualizarItem();
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(MainActivity.class);
            }
        });

        btGerarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(100000) + 1;
                edCodigoItem.setText(String.valueOf(numeroAleatorio));
            }
        });

    }
    private void salvarItem(EditText codigoItem, EditText descricao, EditText valorUnitario) {
        if (codigoItem.getText().toString().isEmpty()) {
            codigoItem.setError("Campo obrigatório!");
            return;
        }
        if (valorUnitario.getText().toString().isEmpty()) {
            valorUnitario.setError("Campo obrigatório!");
            return;
        }
        if (descricao.getText().toString().isEmpty()) {
            descricao.setError("Campo obrigatório!");
            return;
        }
        int codigo = Integer.parseInt(codigoItem.getText().toString());
        double valor = Double.parseDouble(valorUnitario.getText().toString());
        Item item = new Item();
        item.setCodigoItem(codigo);
        item.setValorUnitarioItem(valor);
        item.setDescricaoItem(descricao.getText().toString());
        Controller.getInstance().salvarItem(item);
        codigoItem.setText("");
        descricao.setText("");
        valorUnitario.setText("");
        Toast.makeText(this, "Item adicionado com sucesso!", Toast.LENGTH_LONG).show();
    }

    public void atualizarItem(){
        String texto = "";
        for(Item it : Controller.getInstance().retornarItem()){
            texto += "Código: " + it.getCodigoItem() + "\nDescrição: " + it.getDescricaoItem()
                    + "\nValor: " + it.getValorUnitarioItem() + "\n";
        }
        tvListaItem.setText(texto);
    }

    private void abrirActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}