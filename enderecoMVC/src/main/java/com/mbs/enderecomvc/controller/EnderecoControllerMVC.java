package com.mbs.enderecomvc.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.mbs.enderecomvc.entidades.Endereco;


@Controller
public class EnderecoControllerMVC {

	private List<Endereco> listaEndereco = new ArrayList<Endereco>();
	private static int id = 0;
	
	//Aqui o client acessa a página inicio_cadastrar, pois está em método via get
	@GetMapping("/inicio_cadastrar")
	public String inicio( Model model) {
		model.addAttribute("endereco", new Endereco());// aqui é criado um objeto da classe endereço
		return "cadastrar";
	}
	
	//Na mesma página o client envia os dados, por isso agora está em método post
	@PostMapping(path="/cadastrar")
	public String salvar(@ModelAttribute Endereco endereco,Model model) {
		
		if (endereco.getRua().length() <= 3) {
			model.addAttribute("erro", "campo rua deve ter mais de 3 caracteres");
			return "cadastrar";
		}else {
			endereco.setCodigo(++id);
			listaEndereco.add(endereco); // adiciona na lista.
			System.out.println("cadastrado endereco: " + endereco.getRua());
			model.addAttribute("lista_endereco",listaEndereco); // adiciona na request para a view pegar.
			return "resultado";
		}
		
	}
	
	// CONTINUAR AS IMPLEMENTACOES
	//O "deletar" irá acionar o método, no html será em action "@{/deletar}"
	@PostMapping("/deletar")
	public String delete(@ModelAttribute Endereco endereco, Model model){
		//A variável "e" a ponta para o getCodigo e verifica caso tenha ela na lista
		listaEndereco.removeIf(e -> e.getCodigo() == endereco.getCodigo());
		model.addAttribute("lista_endereco", listaEndereco); // atualizar a lista na view
		System.out.println("item código: " + endereco.getCodigo() + " Removido com sucesso");
		return "resultado";
	}
	
	//método para acessar a página
	@GetMapping("/buscar")
	public String BuscarPeloId(Model model) {
		model.addAttribute("endereco", new Endereco());
	    // Retornar a view onde a tabela será exibida
	    return "filtrarPeloId";
	}
	
	@PostMapping("/resultadoBuscado")
	public String buscarCodigo(@ModelAttribute Endereco endereco, Model model) {
	    List<Endereco> resultadoBusca = new ArrayList<>();

	    // Verifica se o código existe na lista de endereços
	    for (Endereco e : listaEndereco) {
	        if (e.getCodigo().equals(endereco.getCodigo())) {
	            resultadoBusca.add(e);
	            break;  // Encontrou o código, então sai do loop
	        }
	    }

	    // Adiciona os resultados ou erro ao modelo
	    if (resultadoBusca.isEmpty()) {
	        model.addAttribute("erro", "id inexistente");
	        return "filtrarPeloId"; // Retorna para a mesma página de busca
	        
	    } else {
	        model.addAttribute("lista_endereco", resultadoBusca); // Atualiza corretamente
	    }

	    return "resultadoFiltroId";  // Retorna a página de resultados
	}

	
}
