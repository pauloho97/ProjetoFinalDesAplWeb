package com.mbs.clienteServices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbs.clienteServices.entidades.Cliente;
import com.mbs.clienteServices.repositorio.ClienteRepositorio;

@Service
public class ClienteServico {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	public String salvar( Cliente cliente) 
		throws Exception{
		// simples validacao de negocio
		if(cliente.getNome() == null 
				|| ( cliente.getNome() != null 
				&& cliente.getNome().length() <=2 )){
			throw new Exception("Nome do cliente deve ter no minimo 3 caracteres");
		}
		 
		return clienteRepositorio.salvar(cliente);
		
	}
	
	
	public List<Cliente> listar() {		
		return clienteRepositorio.listar();
	}
	

	public boolean deletar( String cpf) {
		return clienteRepositorio.deletar(cpf);		
	}
	
	public Boolean existeCliente(String cpf) {		
		return clienteRepositorio.existeCliente(cpf);
	}
	
	
	public Cliente buscar( String cpf) {
		System.out.println("buscar cliente id " + cpf );
		return clienteRepositorio.buscar(cpf);		
	}
	
	
	public void atualizar( Cliente cliente) throws Exception{
		System.out.println("executando atualizar " + cliente);
		if(cliente.getCpf() == null) {
			throw new Exception("cliente deve ter um cpf");
		}
		
		// simples validacao de negocio
		if(cliente.getNome() == null || ( cliente.getNome() != null && cliente.getNome().length() <=2 )){			
			throw new Exception("Nome do cliente deve ter no minimo 3 caracteres");
		}
		clienteRepositorio.atualizar(cliente);		
	}
}