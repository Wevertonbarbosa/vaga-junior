
package br.com.merito.project_merito.messaging.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.merito.project_merito.messaging.event.AbastecimentoCriadoEvent;

@Service
public class AbastecimentoProducer {

  // Nome do tópico para o qual o evento será enviado.
  private static final String TOPIC = "abastecimentos-criados";

  // KafkaTemplate responsável por publicar eventos no Kafka.
  private final KafkaTemplate<String, AbastecimentoCriadoEvent> kafkaTemplate;

  //construtor
  public AbastecimentoProducer(
      KafkaTemplate<String, AbastecimentoCriadoEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publicar(AbastecimentoCriadoEvent event) {
    kafkaTemplate.send(TOPIC, event);
  }
}
