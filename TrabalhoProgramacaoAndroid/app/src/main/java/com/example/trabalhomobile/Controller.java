package com.example.trabalhomobile;

import java.util.ArrayList;

public class Controller {

    private static Controller instancia;
    private ArrayList<Cliente> listaCliente;
    private ArrayList<Item> listaItens;
    private ArrayList<Pedido> listaPedido;

    public static Controller getInstance(){
        if(instancia == null){
            return instancia = new Controller();
        }else{
            return instancia;
        }
    }
    private Controller() {
        listaCliente = new ArrayList<>();
        listaItens = new ArrayList<>();
        listaPedido = new ArrayList<>();
    }
    public void salvarCliente(Cliente cliente){
        listaCliente.add(cliente);
    }
    public void salvarItem(Item item){
        listaItens.add(item);
    }
    public void salvarPedido(Pedido pedido){
        listaPedido.add(pedido);
    }

    public ArrayList<Cliente> retornarCliente(){
        return listaCliente;
    }
    public ArrayList<Item> retornarItem(){
        return listaItens;
    }

    public ArrayList<Pedido> retornarPedido(){
        return listaPedido;
    }




}
