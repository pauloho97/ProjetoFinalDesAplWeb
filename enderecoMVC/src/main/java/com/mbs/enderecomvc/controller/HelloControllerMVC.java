package com.mbs.enderecomvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloControllerMVC {

	// exemplo de mvc

	// endpoint, seria a chave para acessar a página, no caso é "hello"
   @GetMapping("/hello")
   //Name é o parametro ao qual aparece na pagina "hello name", se não for passado nada na url localhost/hello 
   //automaticamente será colocado "Hello MVC", por isso required(requerido) é false e o defaultValue(valor) é "MVC" 
	public String hello(@RequestParam(name="name", required=false, defaultValue="MVC") String name, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("name2","ph");
		return "hello";
	}
   
}