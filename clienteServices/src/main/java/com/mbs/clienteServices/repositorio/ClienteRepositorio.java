package com.mbs.clienteServices.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.mbs.clienteServices.entidades.Cliente;

						//Esta classe faz CRUD tudo através da chave primária CPF.
@Repository
public class ClienteRepositorio {

	private List<Cliente> listaCliente = new ArrayList<Cliente>();
	private static String cpf;

	public String salvar(Cliente cliente) {
	    if (existeCliente(cliente.getCpf())) {
	        throw new RuntimeException("CPF já cadastrado.");
	    }
	    listaCliente.add(cliente);
	    return cliente.getCpf();
	}


	public List<Cliente> listar() {
		return listaCliente;
	}

	public boolean deletar(String cpf) {
		boolean resultado = listaCliente.removeIf((obj) -> obj.getCpf().equals(cpf));
		return resultado;
	}

	public Boolean existeCliente(String cpf) {
		boolean resultado = listaCliente.stream().filter(obj -> obj.getCpf().equals(cpf)).findFirst().isPresent();
		
		return resultado;
	}

	public Cliente buscar(String cpf) {

		Optional <Cliente> resultado = listaCliente.stream().filter(obj -> obj.getCpf().equals(cpf)).findFirst();
		if (resultado.isEmpty()) {
			return null;
		}
		return resultado.get();
	}

	public void atualizar(Cliente cliente) {
	    for (int i = 0; i < listaCliente.size(); i++) {
	        Cliente cli = listaCliente.get(i);
	        if (cli.getCpf().equals(cliente.getCpf())) {
	            listaCliente.set(i, cliente);
	        }
	    }
	}

}
