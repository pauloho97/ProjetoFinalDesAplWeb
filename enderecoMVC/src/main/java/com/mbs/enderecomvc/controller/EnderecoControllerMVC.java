package com.mbs.enderecomvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mbs.enderecomvc.entidades.Endereco;
import com.mbs.enderecomvc.servico.EnderecoServico;

@Controller
public class EnderecoControllerMVC {
	EnderecoServico enderecoServico = new EnderecoServico();

	// Aqui o client acessa a página inicio_cadastrar, pois está em método via get
	@GetMapping("/inicio_cadastrar")
	public String inicio(Model model) {
		model.addAttribute("endereco", new Endereco());// aqui é criado um objeto da classe endereço
		return "cadastrar";
	}

	// Na mesma página o client envia os dados, por isso agora está em método post
	@PostMapping(path = "/cadastrar")
	public String salvar(@ModelAttribute Endereco endereco, Model model) {

		String resultado = enderecoServico.salvar(endereco);

		// isEmpty significa que teve 0 erros
		if (resultado.isEmpty()) {
			model.addAttribute("lista_endereco", enderecoServico.listar());
			return "resultado";
		} else {
			model.addAttribute("erro", resultado); // adiciona na request para a view pegar.
			return "cadastrar";
		}

	}

	// CONTINUAR AS IMPLEMENTACOES
	// O "deletar" irá acionar o método, no html será em action "@{/deletar}"
	@GetMapping("/deletar")
	public String delete(@RequestParam("id") Integer id, Model model) {
	    String mensagem = enderecoServico.remover(id);
	    model.addAttribute("mensagem", mensagem); // Exibir feedback para o usuário
	    model.addAttribute("lista_endereco", enderecoServico.listar()); // Atualizar a lista

	    return "resultado";
	}

	// método para acessar a página
	@GetMapping("/buscar")
	public String BuscarPeloId(Integer id, Model model) {
		List<Endereco> resultado = enderecoServico.buscar(id);
		
		model.addAttribute("endereco", new Endereco());

		if (!resultado.isEmpty()) {
			model.addAttribute("resultado", resultado); // Passa o resultado para a view
		} else {
			model.addAttribute("erro", "Endereço não encontrado"); // Adiciona erro caso não encontre
		}
		// Retornar a view onde a tabela será exibida
		return "filtrarPeloId";
	}

	@PostMapping("/resultadoBuscado")
	public String buscarCodigo(@RequestParam("codigo") Integer id, Model model) {
		 System.out.println("Buscando ID: " + id);

		    List<Endereco> resultado = enderecoServico.buscar(id); // Busca pelo ID

		    if (!resultado.isEmpty()) {
		        model.addAttribute("resultado", resultado);
		    } else {
		        model.addAttribute("erro", "Endereço não encontrado!");
		    }
		return "resultadoFiltroId";
	}

}
