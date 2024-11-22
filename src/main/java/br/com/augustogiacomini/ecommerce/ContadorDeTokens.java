package br.com.augustogiacomini.ecommerce;

import java.math.BigDecimal;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;

/**
 * @author Augusto Giacomini
 * @since 1.0 (21/11/24)
 */
public class ContadorDeTokens {

    public static void main(String[] args) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);
        var qtd = enc.countTokens("Identifique o perfil de compra de cada cliente");

        System.out.println("QTD de Tokens: " +qtd);
        var custo = new BigDecimal(qtd)
                .divide(new BigDecimal("1000"))
                .multiply(new BigDecimal("0.0010"));
        System.out.println("Custo da requisição: R$" +custo);
    }
}
