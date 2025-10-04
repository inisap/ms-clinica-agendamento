package br.com.postech.ms_agendamento.config;

import br.com.postech.ms_agendamento.adapter.out.kafka.dto.MessageAgendamento;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = kafkaConfig.class)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=broker:29092"
})
public class kafkaConfigTest {

    @Autowired
    private Map<String, Object> producerConfig;

    @Autowired
    private ProducerFactory<String, MessageAgendamento> producerFactory;

    @Autowired
    private KafkaTemplate<String, MessageAgendamento> kafkaTemplate;

    @Test
    void deveCarregarProducerConfigCorretamente() {
        assertThat(producerConfig).isNotNull();
        assertThat(producerConfig.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG))
                .isEqualTo("broker:29092");
        assertThat(producerConfig.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG))
                .isEqualTo(StringSerializer.class);
        assertThat(producerConfig.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG))
                .isEqualTo(JsonSerializer.class);
    }

    @Test
    void deveCriarProducerFactory() {
        assertThat(producerFactory).isNotNull();
    }

    @Test
    void deveCriarKafkaTemplate() {
        assertThat(kafkaTemplate).isNotNull();
    }
}
