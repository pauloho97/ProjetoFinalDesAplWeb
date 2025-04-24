package com.mbs.vendasServices.service;

import java.util.ArrayList;
import java.util.Date;
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
		// validacoes
		if (venda.getCodCliente() == null) {
			throw new Exception("cod cliente invalido");
		}
		if (venda.getNomeProduto() == null || venda.getNomeProduto().equals("")) {
			throw new Exception("nome produto invalido");
		}

		if (venda.getQuantidade() == null) {
			throw new Exception("quantidadevenda invalido");
		}

		if (venda.getPrecoProduto() == null) {
			throw new Exception("preco venda invalido");
		}
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
