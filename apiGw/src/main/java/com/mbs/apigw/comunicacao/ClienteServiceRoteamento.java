package com.mbs.apigw.comunicacao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mbs.clienteServices.entidades.Cliente;

@FeignClient(value = "ClienteService", url = "http://localhost:9003/")
public interface ClienteServiceRoteamento {

    @RequestMapping(value = "/v1/cliente", method = RequestMethod.POST)
    public ResponseEntity<String> salvar(@RequestBody Cliente cliente);

    @RequestMapping(value = "/v1/cliente/existe/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> existeCliente(@PathVariable String cpf);

    @RequestMapping(value = "/v1/cliente", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> listar();

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable String cpf);

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.PUT)
    public ResponseEntity<String> atualizar(@PathVariable String cpf, @RequestBody Cliente clienteAtualizado);

    @RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> buscar(@PathVariable String cpf);
}
