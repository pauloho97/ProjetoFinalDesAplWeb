package com.mbs.vendasServices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbs.vendasServices.entidades.Venda;
import com.mbs.vendasServices.repositorio.VendasRepositorio;

@Service
public class VendasService {

	@Autowired
	private VendasRepositorio vendasRepositorio;

	public Integer salvar(Venda venda) throws Exception {
		validacao(venda);
		venda.setDataVenda(new Date());
		return vendasRepositorio.salvar(venda);
	}

	private void validacao(Venda venda) throws Exception {
		
		//matriz do tipo Object que contém: [funções boolean, String].
		Object funcoesParaEntradasInvalidasObject[][] = {
				{ cpfInvalido(venda), "cpf inválido" },
				{ nomeProdutoInvalido(venda), "Nome da obra inválido" },
				{ quantidadeMenorqueZeroOuVazia(venda), "Quantidade inválida" },
				{ precoProdutoMenorQueZeroOuNulo(venda), "Preço da obra inválido" },
				{ celularNuloOuVazio(venda), "Celular inválido" },
				{ artistaNuloOuvazio(venda), "Nome do artista inválido" }, };
		
		for (Object[] listandoObject : funcoesParaEntradasInvalidasObject) {
			//se o primeiro elemento da lista for true, ele exibe seu par String
			if((boolean) listandoObject [0]) {
				throw new Exception((String) listandoObject[1]);
			}
		}

	}

	// pequenas funções com regras de negócios
	
	private boolean cpfInvalido(Venda venda) {
		return venda.getCpf() == null || venda.getCpf().trim().isEmpty();
	}

	private boolean nomeProdutoInvalido(Venda venda) {
		return venda.getNomeProduto() == null || venda.getNomeProduto().trim().isEmpty();
	}

	private boolean quantidadeMenorqueZeroOuVazia(Venda venda) {
		return venda.getQuantidade() == null || venda.getQuantidade() <= 0;
	}

	private boolean precoProdutoMenorQueZeroOuNulo(Venda venda) {
		return venda.getPrecoProduto() == null || venda.getPrecoProduto() <= 0;
	}

	private boolean celularNuloOuVazio(Venda venda) {
		return venda.getCelular() == null || venda.getCelular().trim().isEmpty();
	}

	private boolean artistaNuloOuvazio(Venda venda) {
		return venda.getArtista() == null || venda.getArtista().trim().isEmpty();
	}

	public List<Venda> listar() {
		return vendasRepositorio.listar();
	}

	public Double totalValoresVenda() {
		Double somador = 0d;
		for (Venda venda : vendasRepositorio.listar()) {
			Double totalVenda = venda.getPrecoProduto() * venda.getQuantidade();
			somador = somador + totalVenda;

		}
		return somador;
	}

	public Double maiorVenda() throws Exception {
		List<Venda> vendas = vendasRepositorio.listar();

		if (vendas == null || vendas.isEmpty()) {
			throw new Exception("Lista está vazia");
		}

		// Inicializa o maior valor com o primeiro elemento da lista
		Double maiorVenda = vendas.get(0).getPrecoProduto() * vendas.get(0).getQuantidade();

		// Itera sobre a lista e encontra o maior valor
		for (Venda venda : vendas) {
			Double valorVenda = venda.getPrecoProduto() * venda.getQuantidade();

			// comparação exemplo: 3 é maior que 5? então não troca o valorVenda
			if (valorVenda > maiorVenda) {
				maiorVenda = valorVenda;
			}
		}
		// 3,5,1
		return maiorVenda;
	}

	public Double menorVenda() throws Exception {
		List<Venda> vendas = vendasRepositorio.listar();

		if (vendas == null || vendas.isEmpty()) {
			throw new Exception("Lista está vazia");
		}

		// Inicializa o maior valor com o primeiro elemento da lista
		Double menorVenda = vendas.get(0).getPrecoProduto() * vendas.get(0).getQuantidade();

		// Itera sobre a lista e encontra o maior valor
		for (Venda venda : vendas) {
			double valorVenda = venda.getPrecoProduto() * venda.getQuantidade();

			if (valorVenda < menorVenda) {
				menorVenda = valorVenda;
			}
		}

		return menorVenda;
	}

}
