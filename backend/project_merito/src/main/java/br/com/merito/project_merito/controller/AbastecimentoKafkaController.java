
package br.com.merito.project_merito.controller;

import br.com.merito.project_merito.dto.request.PublicarAbastecimentoKafkaRequest;
import br.com.merito.project_merito.messaging.event.AbastecimentoCriadoEvent;
import br.com.merito.project_merito.messaging.kafka.AbastecimentoProducer;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Informa ao Spring que esta classe atende requisições HTTP.
@RequestMapping("/kafka") // Define o prefixo das rotas deste controller.
public class AbastecimentoKafkaController {

  // Dependência responsável por publicar mensagens no Kafka.
  private final AbastecimentoProducer producer;

  // O Spring injeta automaticamente o Producer pelo construtor.
  public AbastecimentoKafkaController(AbastecimentoProducer producer) {
    this.producer = producer;
  }

  // Endpoint POST /kafka/abastecimentos.
  @PostMapping("/abastecimentos")
  public String publicar(@Valid @RequestBody PublicarAbastecimentoKafkaRequest request) {

    // Converte o DTO da API para o evento que será enviado ao Kafka.
    AbastecimentoCriadoEvent evento = new AbastecimentoCriadoEvent(
        request.abastecimentoId(),
        request.bombaId(),
        request.litragem(), // DTO: litragem → Evento: volume
        request.valorTotal(),
        request.dataAbastecimento().atStartOfDay() // LocalDate → LocalDateTime
    );

    producer.publicar(evento);

    return "Evento de abastecimento enviado para o Kafka.";
  }
}
