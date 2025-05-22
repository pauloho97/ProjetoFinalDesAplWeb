package com.mbs.clienteServices.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mbs.clienteServices.entidades.Cliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
//import com.mbs.enderecomvc.entidades.Endereco;

@Tag(name = "projeto cliente API", description = "Projeto para aula de ddesenvolvimento web cliente API")

@Controller
@CrossOrigin(origins = "http://localhost:9005")
public class ClienteControllerAPI {

	private List<Cliente> listaCliente = new ArrayList<Cliente>();
	private static Integer id = 0;

	@Operation(summary = "Salvar cliente", description = "função para salvar os dados dos clientes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "salvo com sucesso"),
			@ApiResponse(responseCode = "400", description = "erro de validação do cliente") })

	@RequestMapping(value = "/v1/cliente", method = RequestMethod.POST)
	public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
		System.out.println("executando salvar " + cliente);

		// simples validacao de negocio
		if (cliente.getNome() == null || (cliente.getNome() != null && cliente.getNome().length() <= 2)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome do cliente deve ter no minimo 3 caracteres");
		}
		// cria um id para o cliente
		cliente.setId(++id);
		// adiciona na lista
		listaCliente.add(cliente);
		// retorna para o cliente o status ok e o id do cliente cadastrado.
		return ResponseEntity.ok(id.toString());
	}

	private ApiResponse ApiResponse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Operation(summary = "listar clientes")
	@ApiResponses(value = {@ApiResponse(responseCode = "200",description = "retorna a lista de clientes")})
	@RequestMapping(value = "/v1/cliente", method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listar() {
		System.out.println("executando listar ");
		// retorna a lista de clientes
		return ResponseEntity.ok(listaCliente);
	}
	
	@Operation(summary = "deletar clientes")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleta um cliente da lista"),
			@ApiResponse(responseCode = "404", description = "retorna erro ao deletar um cliente da lista")})
	@RequestMapping(value = "/v1/cliente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		System.out.println("executando deletar de cliente id " + id);
		// deleta o cliente pelo id, caso ele exista
		boolean resultado = listaCliente.removeIf((obj) -> obj.getId().equals(id));
		if (resultado == true) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		// retorna a lista de clientes
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	// SEGUIR IMPLEMENTACAO
	@RequestMapping(value = "v1/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Integer id) {

		// Verifica se o código existe na lista de endereços
		for (Cliente e : listaCliente) {
			if (e.getId().equals(id)) {
				return ResponseEntity.ok(e);

			}
		}
		// NOT_FOUND, pois esse status é o 404, quando um recurso não foi enconrtado, no
		// caso o cliente
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// Nesse método a diferença é que ele retorna apenas se o usuário existe, sendo
	// true
	@RequestMapping(value = "/v1/cliente/existe/{id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> existeCliente(@PathVariable Integer id) {
		// Percorre a lista de clientes e verifica se existe algum com o ID informado
		for (Cliente cliente : listaCliente) {
			if (cliente.getId().equals(id)) {
				return ResponseEntity.ok(true); // Cliente encontrado, retorna true
			}
		}

		return ResponseEntity.ok(false); // Cliente não encontrado, retorna false
	}

	@RequestMapping(value = "/v1/cliente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> atualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
		// Percorre a lista de clientes
		for (Cliente cliente : listaCliente) {
			// Verifica se o id do cliente existe
			if (cliente.getId().equals(id)) {

				if (clienteAtualizado.getNome() == null || clienteAtualizado.getNome().length() < 3) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Erro: O nome do cliente deve ter no mínimo 3 caracteres.");
				} else {
					// Atualiza o campo que o usuário deseja
					if (clienteAtualizado.getNome() != null) {
						cliente.setNome(clienteAtualizado.getNome());
					}
					if (clienteAtualizado.getEmail() != null) {
						cliente.setEmail(clienteAtualizado.getEmail());
					}
					if (clienteAtualizado.getCpf() != null) {
						cliente.setCpf(clienteAtualizado.getCpf());
					}
					if (clienteAtualizado.getCep() != null) {
						cliente.setCep(clienteAtualizado.getCep());
					}

					// Retorna sucesso
					return ResponseEntity.ok("Cliente atualizado! ");
				}

			}
		}

		// Retorna erro caso não exista o id
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Cliente com ID " + id + " não encontrado.");
	}

}
