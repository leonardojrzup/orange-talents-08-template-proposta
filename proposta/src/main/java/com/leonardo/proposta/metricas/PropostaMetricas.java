package com.leonardo.proposta.metricas;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PropostaMetricas {

    private final MeterRegistry meterRegistry;

    public PropostaMetricas(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void contador() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));
        Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
        Counter contadorPropostas = this.meterRegistry.counter("proposta_criada", tags);
        contadorPropostas.increment();
    }
}
