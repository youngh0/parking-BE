package com.example.parking.domain.parking;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationTypeTest {

    @Test
    void description에_맞는_OperationType_리스트를_반환() {
        List<String> descriptions = List.of("공영");

        List<OperationType> operationTypes = OperationType.collectMatch(descriptions);
        assertAll(
                () -> Assertions.assertThat(operationTypes).hasSize(1),
                () -> Assertions.assertThat(operationTypes.get(0)).isEqualTo(OperationType.PUBLIC)
        );
    }

    @Test
    void description에_맞는_OperationType이_없으변_빈_리스트를_반환() {
        List<String> descriptions = List.of("공");

        List<OperationType> operationTypes = OperationType.collectMatch(descriptions);
        Assertions.assertThat(operationTypes).hasSize(0);
    }
}
