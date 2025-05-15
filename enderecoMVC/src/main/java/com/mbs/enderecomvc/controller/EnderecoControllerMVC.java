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
	public String delete(@RequestParam("id") String id, Model model) {
		String mensagem = enderecoServico.remover(Integer.parseInt(id));
	    model.addAttribute("mensagem", mensagem); // Exibir feedback para o usuário
	    model.addAttribute("lista_endereco", enderecoServico.listar()); // Atualizar a lista

	    return "resultado";
	}

	// método para acessar a página
	@GetMapping("/buscar")
	public String BuscarPeloId(Integer id, Model model) {
		List<Endereco> resultado = enderecoServico.buscar(id, null);
		
		model.addAttribute("endereco", new Endereco());

		if (!resultado.isEmpty()) {
			model.addAttribute("resultado", resultado); // Passa o resultado para a view
		} else {
	    	 model.addAttribute("houveBusca", false); // Indica que ainda não houve busca		
		}
		// Retornar a view onde a tabela será exibida
		return "filtrarPeloIdCep";
	}

	@PostMapping("/resultadoBuscado")
	public String buscarCodigoOuCep(@RequestParam(name = "codigo", required = false) Integer id,
	                                @RequestParam(name = "cep", required = false) String cep,
	                                Model model) {
	    model.addAttribute("houveBusca", true); // Indica que houve uma busca

	    List<Endereco> resultado = new ArrayList<>();

	    if (id != null) {
	        resultado = enderecoServico.buscar(id, cep); // busca por ID
	    } else if (cep != null && !cep.isEmpty()) {
	        resultado = enderecoServico.buscar(id, cep); // busca por CEP (você precisa ter esse método no serviço)
	    }

	    if (!resultado.isEmpty()) {
	        model.addAttribute("lista_endereco", resultado);
	    } else {
	        model.addAttribute("erro", "Endereço não encontrado!");
	    }

	    return "resultadoFiltroIdCep";
	}

}
