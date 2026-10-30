package br.com.merito.project_relatorios.messaging.kafka;

import br.com.merito.project_relatorios.dto.event.AbastecimentoCriadoEvent;
import br.com.merito.project_relatorios.service.AbastecimentoRelatorioService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AbastecimentoConsumer {

  private final AbastecimentoRelatorioService service;

  public AbastecimentoConsumer(AbastecimentoRelatorioService service) {
    this.service = service;
  }

  // Escuta os eventos publicados no tópico de abastecimentos.
  @KafkaListener(topics = "abastecimentos-criados", groupId = "relatorios-consumidores")

  public void consumir(AbastecimentoCriadoEvent evento) {

    service.salvar(evento);

    System.out.println(
        "Abastecimento " + evento.abastecimentoId()
            + " processado pelo Backend de Relatórios.");
  }
}