package com.mbs.notificacaoServices;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    // Token exposto apenas para testes
    private static final String ZENVIA_API_TOKEN = "eq0KGVteSOrxY32I8bzAWDgK_Lo4E8NHmVZ8";
    private static final String FROM = "MeuApp"; // Máx. 11 caracteres, sem acento ou espaço

    public void enviarSms(String numeroDestino, String mensagem) {
        try {
            // Monta o corpo da requisição
            JSONObject body = new JSONObject()
                .put("from", FROM)
                .put("to", numeroDestino)
                .put("contents", new org.json.JSONArray()
                    .put(new JSONObject()
                        .put("type", "text")
                        .put("text", mensagem)));

            // Cria requisição com HTTP/1.1 (evita erro de EOF)
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.zenvia.com/v2/channels/sms/messages"))
                .header("Content-Type", "application/json")
                .header("X-API-TOKEN", ZENVIA_API_TOKEN)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

            // Envia requisição
            HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Exibe resposta
            System.out.println("Resposta Zenvia: " + response.statusCode());
            System.out.println(response.body());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("✅ SMS enviado com sucesso.");
            } else {
                System.err.println("❌ Falha no envio do SMS. Verifique a resposta da API.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao enviar SMS pela Zenvia:");
            e.printStackTrace();
        }
    }

}
