package com.mbs.apigw.comunicacao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mbs.vendasServices.entidades.Venda;

@FeignClient(value = "VendasService", url = "http://localhost:9002/")
public interface VendasServiceRoteamento {
	
	@RequestMapping(value = "/v1/venda", method = RequestMethod.POST)
	public ResponseEntity<String> salvar(@RequestBody Venda venda);
	
	@RequestMapping(value = "/v1/venda", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listar();
	
	@RequestMapping(value = "/v1/venda/total_venda", method = RequestMethod.GET)
	public ResponseEntity<Integer> totalVenda();
	
	@RequestMapping(value = "v1/venda/maior_venda", method = RequestMethod.GET)
	public ResponseEntity<Double> vendaMaior();
	
	@RequestMapping(value = "v1/venda/menor_venda", method = RequestMethod.GET)
	public ResponseEntity<Double> vendaMenor();
	
}
