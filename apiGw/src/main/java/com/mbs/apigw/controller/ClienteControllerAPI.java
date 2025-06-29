package com.mbs.apigw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mbs.apigw.comunicacao.ClienteServiceRoteamento;
import com.mbs.clienteServices.entidades.Cliente;

@RestController
@CrossOrigin(origins = "http://localhost:9005")
public class ClienteControllerAPI {

    @Autowired
    private ClienteServiceRoteamento clienteServiceRoteamento;

    @RequestMapping(value = "/v1/cliente", method = RequestMethod.POST)
    public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
        System.out.println("Processando roteamento salvar");
        return clienteServiceRoteamento.salvar(cliente);
    }

    @RequestMapping(value = "/v1/cliente/existe/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> existeCliente(@PathVariable String cpf) {
        System.out.println("Processando roteamento existe cliente " + cpf);
        return clienteServiceRoteamento.existeCliente(cpf);
    }

    @RequestMapping(value = "/v1/cliente", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> listar() {
        System.out.println("Processando roteamento listar ");
        return clienteServiceRoteamento.listar();
    }

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        System.out.println("Processando roteamento deletar " + cpf);
        return clienteServiceRoteamento.deletar(cpf);
    }

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.PUT)
    public ResponseEntity<String> atualizar(@PathVariable String cpf, @RequestBody Cliente clienteAtualizado) {
        System.out.println("Processando roteamento atualizar " + cpf);
        return clienteServiceRoteamento.atualizar(cpf, clienteAtualizado);
    }

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> buscar(@PathVariable String cpf) {
        System.out.println("Processando roteamento buscar " + cpf);
        return clienteServiceRoteamento.buscar(cpf);
    }
}
