package com.mbs.apigw.controller;

import java.util.List;

import org.bouncycastle.oer.its.ieee1609dot2.basetypes.PublicEncryptionKey;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mbs.apigw.comunicacao.ClienteServiceRoteamento;
import com.mbs.apigw.comunicacao.VendasServiceRoteamento;
import com.mbs.vendasServices.entidades.Venda;

import ch.qos.logback.core.status.Status;

@CrossOrigin(origins = "http://localhost:9005")
@RestController
public class VendasControllerAPI {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ClienteServiceRoteamento clienteServiceRoteamento;

	@Autowired
	private VendasServiceRoteamento vendasServiceRoteamento;

	@RequestMapping(value = "/v1/processarVenda", method = RequestMethod.POST)
	public ResponseEntity<String> processarVenda(@RequestBody Venda venda) {
		System.out.println("Processando venda");
		ResponseEntity<Boolean> existeCliente = clienteServiceRoteamento.existeCliente(venda.getCpf());

		if (existeCliente.getBody().equals(false)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado! ");
		}

		// chegando aqui, eu tenho um cliente
		ResponseEntity<String> resultadoVenda = vendasServiceRoteamento.salvar(venda);

		if (resultadoVenda.getStatusCode() != HttpStatus.OK) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível realizar a venda");
		}

		venda.setStatus("aprovado");
		// String vendaJson = new Gson().toJson(venda);
		//System.out.println("Enviando mensagem para o Brocker..");
		//rabbitTemplate.convertAndSend("vendas", "routing-key-teste", venda);

		// retorno o id da venda realizada no body.
		return ResponseEntity.status(HttpStatus.CREATED).body(resultadoVenda.getBody());

		// aqui seria caso a resposta sempre fosse ok, independente do caminho do
		// usuário
		// return ResponseEntity.ok("venda feita com sucesso");

	}

	// Método listas vendas
	@RequestMapping(value = "/v1/venda", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listar() {
		System.out.println("Processando lista de vendas ");
		return vendasServiceRoteamento.listar();
	}

	// Método mostrar valor total das vendas
	@RequestMapping(value = "/v1/venda/total_venda", method = RequestMethod.GET)
	public ResponseEntity<Integer> totalVenda() {
		System.out.println("Valor total das vendas processado ");
		return vendasServiceRoteamento.totalVenda();
	}

	// Método mostrar maior venda
	@RequestMapping(value = "/v1/venda/maior_venda", method = RequestMethod.GET)
	public ResponseEntity<Double> vendaMaior() {
		System.out.println("Maior venda processada");
		return vendasServiceRoteamento.vendaMaior();
	}

	// Método mostrar menor venda
	@RequestMapping(value = "/v1/venda/menor_venda", method = RequestMethod.GET)
	public ResponseEntity<Double> vendaMenor() {
		System.out.println("Menor venda processada");
		return vendasServiceRoteamento.vendaMenor();
	}

}
