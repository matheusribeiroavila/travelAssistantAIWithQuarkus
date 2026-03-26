package com.agenciaai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ToneGuardrail implements OutputGuardrail {

    @Inject
    ToneJudge judge; //Injetando o "juiz" da conversa

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        if (!judge.isProfessional(responseFromLLM.text())){
            return reprompt(responseFromLLM.text(), """
                        Sua resposta foi detectada como rude ou informal de mais.
                        Reescreva-a mantendo a polidez e formalidade de um agente de viagem sênior
                        """);
        }
        return OutputGuardrailResult.success();
    }
}
