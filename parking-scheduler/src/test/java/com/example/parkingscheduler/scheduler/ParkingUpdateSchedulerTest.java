package com.example.parkingscheduler.scheduler;

import com.example.parkingscheduler.coordinate.CoordinateService;
import com.example.parkingscheduler.fake.ExceptionParkingApiService;
import com.example.parkingscheduler.fake.FakeCoordinateService;
import com.example.parkingscheduler.fake.NotOfferCurrentParkingApiService;
import com.example.parkingscheduler.fake.OfferCurrentParkingApiService;
import fake.BasicParkingRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingUpdateSchedulerTest {

    private final BasicParkingRepository parkingRepository = new BasicParkingRepository();
    private final CoordinateService coordinateService = new FakeCoordinateService();

    @DisplayName("실시간 주차 대수를 제공하는 API에서 주차장이 0~4까지 저장되어 있는 상태에서 0~9까지 주차장을 읽어와 업데이트한다.")
    @Test
    void autoUpdateOfferCurrentParking() {
        //given
        OfferCurrentParkingApiService offerCurrentParkingApiService = new OfferCurrentParkingApiService(5);
        parkingRepository.saveAll(offerCurrentParkingApiService.read());
        int readSize = 10;
        offerCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(offerCurrentParkingApiService),
                new ParkingMapper(),
                new ParkingService(parkingRepository),
                parkingRepository,
                coordinateService
        );

        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(readSize);
    }

    @DisplayName("실시간 주차 대수를 제공하지 않는 API에서 주차장이 0~4까지 저장되어 있는 상태에서 0~9까지 주차장을 읽어와 업데이트한다.")
    @Test
    void autoUpdateNotOfferCurrentParking() {
        //given
        NotOfferCurrentParkingApiService notOfferCurrentParkingApiService = new NotOfferCurrentParkingApiService(
                5);
        parkingRepository.saveAll(notOfferCurrentParkingApiService.read());
        int readSize = 10;
        notOfferCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(notOfferCurrentParkingApiService),
                new ParkingMapper(),
                new ParkingService(parkingRepository),
                parkingRepository,
                coordinateService
        );

        //when
        scheduler.autoUpdateNotOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(readSize);
    }

    @DisplayName("실시간 주차 대수를 제공하는 API와 제공하지 않는 API는 영향을 안준다.")
    @Test
    void notAffectBetweenOfferAndNotOfferCurrentParking() {
        //given
        OfferCurrentParkingApiService offerCurrentParkingApiService = new OfferCurrentParkingApiService(5);
        NotOfferCurrentParkingApiService notOfferCurrentParkingApiService = new NotOfferCurrentParkingApiService(
                5);
        parkingRepository.saveAll(offerCurrentParkingApiService.read());
        int readSize = 10;
        notOfferCurrentParkingApiService.setReadSize(readSize);

        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(offerCurrentParkingApiService, notOfferCurrentParkingApiService),
                new ParkingMapper(),
                new ParkingService(parkingRepository),
                parkingRepository,
                coordinateService
        );

        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(5);
    }

    @DisplayName("특정 API에서 예외 발생시, 해당 API는 log를 남기고 무시한다.")
    @Test
    void autoUpdateWithExceptionApi() {
        //given
        ParkingUpdateScheduler scheduler = new ParkingUpdateScheduler(
                List.of(new OfferCurrentParkingApiService(5), new ExceptionParkingApiService()),
                new ParkingMapper(),
                new ParkingService(parkingRepository),
                parkingRepository,
                coordinateService
        );

        //when
        scheduler.autoUpdateOfferCurrentParking();

        //then
        Assertions.assertThat(parkingRepository.count()).isEqualTo(5);
    }
}
