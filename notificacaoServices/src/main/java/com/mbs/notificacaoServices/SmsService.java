package com.mbs.notificacaoServices;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

	// Substitua pelo seu token gerado no painel da Zenvia
	private static final String ZENVIA_API_TOKEN = "SEU_TOKEN_AQUI";
	private static final String FROM = "MeuApp"; // Nome curto da aplicação (até 11 caracteres)

	public void enviarSms(String numeroDestino, String mensagem) {
		try {
			JSONObject body = new JSONObject().put("from", FROM).put("to", numeroDestino).put("contents",
					new org.json.JSONArray().put(new JSONObject().put("type", "text").put("text", mensagem)));

			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI("https://api.zenvia.com/v2/channels/sms/messages"))
					.header("Content-Type", "application/json").header("X-API-TOKEN", ZENVIA_API_TOKEN)
					.POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();

			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			System.out.println("Resposta Zenvia: " + response.statusCode());
			System.out.println(response.body());

		} catch (Exception e) {
			System.err.println("Erro ao enviar SMS pela Zenvia:");
			e.printStackTrace();
		}
	}
}
