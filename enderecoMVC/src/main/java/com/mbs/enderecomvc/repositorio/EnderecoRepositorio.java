package com.mbs.enderecomvc.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.TextOutputCallback;

import com.mbs.enderecomvc.entidades.Endereco;

public class EnderecoRepositorio {
	private List<Endereco> listaEndereco = new ArrayList<Endereco>();
	private static int id = 0;

	Endereco endereco = new Endereco();

	public int salvar(Endereco endereco) {

		endereco.setCodigo(++id);
		listaEndereco.add(endereco); // adiciona na lista.
		return id;

	}

	public List<Endereco> listar() {
		return listaEndereco;
	}

	public String remover(Integer id) {
		// A variável "e" aponta para o getCodigo e verifica caso tenha ela na lista
		boolean removido = listaEndereco.removeIf((e) -> e.getCodigo().equals(id));

		if (removido) {
			return "Endereço removido com sucesso";
		} else {
			return "Endereço não encontrado";
		}
	}

	public List<Endereco> buscar(Integer id, String cep) {
	    List<Endereco> resultadoBusca = new ArrayList<>();

	    for (Endereco e : listaEndereco) {
	        boolean idConfere = (id != null && e.getCodigo().equals(id));
	        boolean cepConfere = (cep != null && !cep.trim().isEmpty() && e.getCep().equalsIgnoreCase(cep));

	        if (idConfere || cepConfere) {
	            resultadoBusca.add(e);
	        }
	    }

	    return resultadoBusca;
	}

}
