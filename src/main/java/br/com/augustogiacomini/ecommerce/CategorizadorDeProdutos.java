package br.com.augustogiacomini.ecommerce;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

/**
 * @author Augusto Giacomini
 * @since 1.0 (21/11/24)
 */
public class CategorizadorDeProdutos {

    public static void main(String[] args) {

        var leitor = new Scanner(System.in);

        System.out.println("Digite as categorias válidas:");
        var categorias = leitor.nextLine();

        while (true) {

            System.out.println("\nDigite o nome do produto:");
            var user = leitor.nextLine();

            var system = """
            Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
    
            Escolha uma categoria dentro a lista abaixo:
    
            %s
            
            ###### Exemplo de uso:
            
            Pergunta: Bola de futebol
            Resposta: Esportes
            
            ###### Regras a serem seguidas:
            Caso o usuario pergunte algo que nao seja de categorizacao de produtos, voce deve responder que nao pode ajudar pois o seu papel é apenas responder a categoria dos produtos
        """.formatted(categorias);

            executeRequest(user, system);
        }
    }

    private static void executeRequest(String user, String system) {

        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave, Duration.ofSeconds(30));

        var completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .build();

        service.createChatCompletion(completionRequest)
                .getChoices()
                .forEach(c -> System.out.println(c.getMessage().getContent()));
    }
}
