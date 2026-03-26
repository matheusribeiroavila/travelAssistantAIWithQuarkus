package com.agenciaai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

public class JsonStructureGuard implements OutputGuardrail {

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        String response = responseFromLLM.text();

        try(JsonReader reader = Json.createReader(new StringReader(response))){
            //Tenta fazer o parse. Se falhar, o JSON é inválido
            JsonObject jsonObject = reader.readObject();
            return OutputGuardrailResult.success();
        }catch (Exception e){
            return reprompt(responseFromLLM.text(), """
                    Erro: Sua resposta não é um JSON válido.
                    Problema encontrado: """+ e.getMessage()+"""
                    . Gere NOVAMENTE apenas o JSON, sem blocos de código markdown ou textos adicionais
                    """);

        }
    }
}
