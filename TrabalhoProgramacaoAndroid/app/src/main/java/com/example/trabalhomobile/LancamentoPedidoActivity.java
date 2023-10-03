package com.example.trabalhomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LancamentoPedidoActivity extends AppCompatActivity {

    private TextView tvItensAdicionados, tvValorTotal, tvQuantidadeTotal, tvParcelas, tvCalcularTotal;
    private AutoCompleteTextView tvACCliente;
    private Spinner spItem, spItemPagar;
    private Button btAdicionarItem,btPagar,btVoltar, btValorTotal;
    private RadioButton rbAVista,rbAPrazo;
    private EditText edQuantidadeItem, edValorUnitarioItem, edParcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_pedido);

        tvACCliente = findViewById(R.id.tvACCliente);

        tvItensAdicionados = findViewById(R.id.tvItensAdicionados);

        tvQuantidadeTotal = findViewById(R.id.tvQuantidadeTotal);

        tvValorTotal = findViewById(R.id.tvValorTotal);

        tvParcelas = findViewById(R.id.tvListaParcelas);

        tvCalcularTotal = findViewById(R.id.tvValorTotalP);

        spItem = findViewById(R.id.spItem);

        spItemPagar = findViewById(R.id.spItemPagar);

        btAdicionarItem = findViewById(R.id.btAdicionarItem);

        btPagar = findViewById(R.id.btPagar);

        btVoltar =  findViewById(R.id.btVoltarMenuPedido);

        btValorTotal = findViewById(R.id.btValorTotal);

        rbAVista = findViewById(R.id.rbAVista);

        rbAPrazo = findViewById(R.id.rbAPrazo);

        edQuantidadeItem = findViewById(R.id.edQuantidadeItem);

        edValorUnitarioItem = findViewById(R.id.edValorUnitarioItemPedido);

        edParcelas = findViewById(R.id.edParcela);

        carregarItem();
        carregarListaClientes();

        btAdicionarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarItemPedido();
            }
        });

        btPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Controller.getInstance().retornarPedido().isEmpty()){
                    Toast.makeText(LancamentoPedidoActivity.this, "Erro!\n Nenhum pedido adicionado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LancamentoPedidoActivity.this, "Pedido cadastrado", Toast.LENGTH_LONG).show();
                }
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(MainActivity.class);
            }
        });

        btValorTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbAVista.isChecked()){
                    tvValorTotal.setText(String.valueOf(calcularValorTotal("vista")));
                }
                if(rbAPrazo.isChecked()){
                    tvValorTotal.setText(String.valueOf(calcularValorTotal("prazo")));
                }
            }
        });
    }

    private void carregarListaClientes(){
        ArrayList<Cliente> listaClientes = Controller.getInstance().retornarCliente();
        String[] vetorClientes = new String[listaClientes.size() + 1];
        vetorClientes[0] = "";

        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            vetorClientes[i + 1] = cliente.getNome();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, vetorClientes);
        tvACCliente.setAdapter(adapter);
    }
    private void carregarItem(){
        ArrayList<Item> itens = Controller.getInstance().retornarItem();
        String[] vetorItem = new String[itens.size()];

        for(int i=0;i<itens.size();i++){
            Item item = itens.get(i);
            vetorItem[i] = item.getDescricaoItem();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,vetorItem);
        spItem.setAdapter(adapter);
    }

    public void adicionarItemPedido(){
        Pedido pedido = new Pedido();
        String itemSelecionado = spItem.getSelectedItem().toString();
        if(edQuantidadeItem.getText().toString().isEmpty()){
            edQuantidadeItem.setError("Preencha o campo!");
            return;
        }
        if(edValorUnitarioItem.getText().toString().isEmpty()){
            edValorUnitarioItem.setError("Preencha o campo!");
            return;
        }
        int quantidade = Integer.parseInt(edQuantidadeItem.getText().toString());
        double valorUnitario = Double.parseDouble(edValorUnitarioItem.getText().toString());

        pedido.setDescricaoItem(spItem.getSelectedItem().toString());
        pedido.setQuantidadeItem(quantidade);
        pedido.setValorUnitarioItem(valorUnitario);
        pedido.setCodigoItem(0);
        Controller.getInstance().salvarPedido(pedido);

        tvItensAdicionados.setText(tvItensAdicionados.getText().toString() + "Nome: " + itemSelecionado+
                "\nQuantidade: " + quantidade + "\nValor Unitário: " + valorUnitario + "\n");

        tvValorTotal.setText("Valor total: " + String.valueOf(calcularValorTotal("normal")));
        tvQuantidadeTotal.setText("Quantidade total: " + String.valueOf(calcularQuantidadeTotal()));

        if(rbAVista.isChecked()){
            tvParcelas.setText("Total a pagar a vista: " + calcularValorTotal("vista"));
        }
        if(rbAPrazo.isChecked()){
            double valorParcela = calcularValorTotal("prazo") / Double.parseDouble(edParcelas.getText().toString());
            String texto = "";
            for(int i=0; i<Integer.parseInt(edParcelas.getText().toString());i++){
                texto = texto + "\nParcela atual:" + (i + 1) +
                        " Valor: " + valorParcela + "\n";
            }
            tvParcelas.setText(texto);
        }

    }

    public double calcularValorTotal(String condicao){
        ArrayList<Pedido> pedido = Controller.getInstance().retornarPedido();
        double valorTotal = 0;
        if(condicao == "prazo"){
            for(Pedido pd : pedido){
                valorTotal = (valorTotal) + (pd.getQuantidadeItem() * pd.getValorUnitarioItem() * 1.05);
            }
            return valorTotal;
        }
        if(condicao == "vista"){
            for(Pedido pd : pedido){
                valorTotal = (valorTotal) + (pd.getQuantidadeItem() * pd.getValorUnitarioItem() * 0.95);
            }
            return valorTotal;
        }
        if(condicao == "normal"){
            for(Pedido pd : pedido){
                valorTotal = (valorTotal) + (pd.getQuantidadeItem() * pd.getValorUnitarioItem());
            }
            return valorTotal;
        }
        return valorTotal;
    }

    public int calcularQuantidadeTotal(){
        ArrayList<Pedido> pedido = Controller.getInstance().retornarPedido();
        int quantidadeTotal = 0;
        for(Pedido pd : pedido){
            quantidadeTotal = (quantidadeTotal) + pd.getQuantidadeItem();
        }
        return quantidadeTotal;
    }

    private void abrirActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}