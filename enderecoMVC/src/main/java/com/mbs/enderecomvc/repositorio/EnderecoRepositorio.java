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
		// A variável "e" a ponta para o getCodigo e verifica caso tenha ela na lista
		boolean removido = listaEndereco.removeIf(e -> e.getCodigo().equals(id));

		if (removido) {
			return "Endereço removido com sucesso";
		} else {
			return "Endereço não encontrado";
		}
	}

	public List<Endereco> buscar() {
		List<Endereco> resultadoBusca = new ArrayList<>();

		// Verifica se o código existe na lista de endereços
		for (Endereco e : listaEndereco) {
			if (e.getCodigo().equals(id)) {
				resultadoBusca.add(e);
				break; // Encontrou o código, então sai do loop
			}
		}

		return resultadoBusca;
	}

}
