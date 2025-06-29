package com.mbs.clienteServices.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mbs.clienteServices.entidades.Cliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "projeto cliente API", description = "Projeto final para cliente do ateliê")
@RestController
@CrossOrigin(origins = "http://localhost:9005")
public class ClienteControllerAPI {

	private List<Cliente> listaCliente = new ArrayList<>();

	@Operation(summary = "Salvar cliente", description = "função para salvar os dados dos clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "salvo com sucesso"),
			@ApiResponse(responseCode = "400", description = "erro de validação do cliente")
	})
	@RequestMapping(value = "/v1/cliente", method = RequestMethod.POST)
	public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
		System.out.println("executando salvar " + cliente);

		if (cliente.getNome() == null || cliente.getNome().length() <= 2) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Nome do cliente deve ter no mínimo 3 caracteres");
		}
		if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("CPF deve ter exatamente 11 caracteres.");
		}

		// Verifica se o CPF já existe
		for (Cliente c : listaCliente) {
			if (c.getCpf().equals(cliente.getCpf())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("CPF já cadastrado.");
			}
		}

		listaCliente.add(cliente);
		return ResponseEntity.ok(cliente.getCpf());
	}

	@Operation(summary = "listar clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "retorna a lista de clientes")
	})
	@RequestMapping(value = "/v1/cliente", method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> listar() {
		System.out.println("executando listar ");
		return ResponseEntity.ok(listaCliente);
	}

	@Operation(summary = "deletar cliente por CPF")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "deleta um cliente da lista"),
			@ApiResponse(responseCode = "404", description = "cliente não encontrado")
	})
	@RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable String cpf) {
		System.out.println("executando deletar cliente com CPF " + cpf);
		boolean resultado = listaCliente.removeIf(obj -> obj.getCpf().equals(cpf));
		if (resultado) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> buscarCliente(@PathVariable String cpf) {
		for (Cliente c : listaCliente) {
			if (c.getCpf().equals(cpf)) {
				return ResponseEntity.ok(c);
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@RequestMapping(value = "/v1/cliente/existe/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> existeCliente(@PathVariable String cpf) {
		for (Cliente cliente : listaCliente) {
			if (cliente.getCpf().equals(cpf)) {
				return ResponseEntity.ok(true);
			}
		}
		return ResponseEntity.ok(false);
	}

	@RequestMapping(value = "/v1/cliente/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<String> atualizarCliente(@PathVariable String cpf, @RequestBody Cliente clienteAtualizado) {
		for (Cliente cliente : listaCliente) {
			if (cliente.getCpf().equals(cpf)) {

				if (clienteAtualizado.getNome() == null || clienteAtualizado.getNome().length() < 3) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Erro: O nome do cliente deve ter no mínimo 3 caracteres.");
				}

				// Atualiza os campos permitidos
				if (clienteAtualizado.getNome() != null) {
					cliente.setNome(clienteAtualizado.getNome());
				}
				if (clienteAtualizado.getCelular() != null) {
					cliente.setCelular(clienteAtualizado.getCelular());
				}
				if (clienteAtualizado.getCep() != null) {
					cliente.setCep(clienteAtualizado.getCep());
				}
				if (clienteAtualizado.getNumero() != null) {
					cliente.setNumero(clienteAtualizado.getNumero());
				}
				if (clienteAtualizado.getComplemento() != null) {
					cliente.setComplemento(clienteAtualizado.getComplemento());
				}

				return ResponseEntity.ok("Cliente atualizado!");
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Cliente com CPF " + cpf + " não encontrado.");
	}
}
