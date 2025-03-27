package com.mbs.enderecomvc.servico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.mbs.enderecomvc.entidades.Endereco;
import com.mbs.enderecomvc.repositorio.EnderecoRepositorio;

public class EnderecoServico {

	private EnderecoRepositorio enderecoRepositorio = new EnderecoRepositorio();

	public String salvar(Endereco endereco) {
		if (endereco.getRua().length() <= 3) {

			return "[ERRO] - campo rua deve ter mais de 3 caracteres";

		}
		
		if (endereco.getCep().trim().contains("-")) {
			return "Cep não pode conter o caracter '-'";

		}

		int resultado = enderecoRepositorio.salvar(endereco);
		
		if(resultado <= 0) {
			return "não foi possível salvar no banco de dados";
		}
		return "";
	}
	
	public List<Endereco> listar(){
		return enderecoRepositorio.listar();
	}
	
	public String remover(Integer id) {
		if(id == null) {
			return "id inexistente";
		}
		return enderecoRepositorio.remover(id);
	}
	
	public List<Endereco> buscar(Integer id){
		List<Endereco> resultadosList = enderecoRepositorio.buscar();
		
		// Adiciona os resultados ou erro ao modelo
	    if (resultadosList == null || resultadosList.isEmpty()) {
	       
	    	//função da classe Collections para retornar lista vazia
	        return Collections.emptyList(); 
	        
	    }
		return resultadosList;
	}

}
