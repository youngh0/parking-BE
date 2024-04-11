package com.example.parking.domain.searchcondition;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.parking.infra.converter.FeeTypeConverter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnumListConverterTest {

    @DisplayName("DB에 Enum을 List형식으로 저장할 때, Enum의 값 이름과 " + '"' + ", " + '"'+ " 구분자를 이용해서 저장된다.")
    @Test
    void convertTest() {
        //given
        FeeTypeConverter feeTypeConverter = new FeeTypeConverter();
        List<FeeType> expectedFeeType = List.of(FeeType.FREE, FeeType.PAID);
        String expectedDatabaseColumn = expectedFeeType.stream().map(FeeType::name)
                .collect(Collectors.joining(", "));

        //when
        String actualDatabaseField = feeTypeConverter.convertToDatabaseColumn(expectedFeeType);
        List<FeeType> actualFeeTypes = feeTypeConverter.convertToEntityAttribute(actualDatabaseField);

        //then
        assertThat(actualDatabaseField).isEqualTo(expectedDatabaseColumn);
        assertThat(actualFeeTypes).isEqualTo(expectedFeeType);
    }
}
