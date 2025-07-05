package com.mbs.notificacaoServices;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mbs.vendasServices.entidades.Venda;

@Component
public class ProdutoListener {

    @Value("${queue}")
    private String queue;

    private final SmsService smsService;

    public ProdutoListener(SmsService smsService) {
        this.smsService = smsService;
    }

    @RabbitListener(queues = { "${queue}" })
    public void receive(@Payload String fileBody) {
        System.out.println("Mensagem recebida da fila " + queue + ": " + fileBody);

        // ❗ Apenas essa linha é suficiente
        Venda vendas = new Gson().fromJson(fileBody, Venda.class);

        System.out.println("Nome do Produto: " + vendas.getNomeProduto());
        System.out.println("Status: " + vendas.getStatus());
        System.out.println("Celular: " + vendas.getCelular());
        System.out.println("##############################");

        String celular = vendas.getCelular();
        if (celular != null && celular.matches("\\+55\\d{10,11}")) {
            smsService.enviarSms(celular, criarMensagemSms(vendas));
        } else {
            System.err.println("Número de celular inválido ou não informado: " + celular);
        }
    }



    private String criarMensagemSms(Venda venda) {
        return String.format(
            "Olá! Sua compra do produto '%s' foi registrada. Status: %s.",
            venda.getNomeProduto(),
            venda.getStatus()
        );
    }
}
