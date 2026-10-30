
package br.com.merito.project_merito.messaging.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.merito.project_merito.messaging.event.AbastecimentoCriadoEvent;

@Service
public class AbastecimentoConsumer {

  // Escuta o tópico "abastecimentos" usando este grupo de consumidores.
  // TOPICO ANTIGO
  // @KafkaListener(topics = "abastecimentos", groupId = "merito-consumidores")
  // TOPICO NOVO COM EVENTOS
  @KafkaListener(topics = "abastecimentos-criados", groupId = "merito-consumidores")

  public void consumir(AbastecimentoCriadoEvent evento) {

    // Exibe os dados do evento recebido.
    System.out.println("Abastecimento recebido do Kafka:");
    System.out.println("ID: " + evento.abastecimentoId());
    System.out.println("Bomba: " + evento.bombaId());
    System.out.println("Volume: " + evento.volume());
    System.out.println("Valor total: " + evento.valorTotal());
    System.out.println("Data: " + evento.dataAbastecimento());

  }
}
