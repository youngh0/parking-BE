package com.example.parking.application.parking;

//@RequiredArgsConstructor
//@Service
//public class ParkingService {
//
//    private static final String DISTANCE_ORDER_CONDITION = "가까운 순";
//
//    private final ParkingRepository parkingRepository;
//    private final ParkingFilteringService parkingFilteringService;
//    private final FavoriteRepository favoriteRepository;
//    private final SearchConditionMapper searchConditionMapper;
//    private final ParkingFeeCalculator parkingFeeCalculator;
//
//    @Transactional(readOnly = true)
//    public ParkingLotsResponse findParkingLots(ParkingQueryRequest parkingQueryRequest,
//                                               ParkingSearchConditionRequest parkingSearchConditionRequest,
//                                               Long memberId) {
//        LocalDateTime now = LocalDateTime.now();
//        Location destination = Location.of(parkingQueryRequest.getLongitude(), parkingQueryRequest.getLatitude());
//
//        // 반경 주차장 조회
//        List<Favorite> favorites = findMemberFavorites(memberId);
//        List<Parking> parkingLots = findParkingLotsByOrderCondition(parkingSearchConditionRequest.getPriority(),
//                parkingQueryRequest, destination);
//
//        // 조회조건 기반 필터링
//        SearchingCondition searchingCondition = toSearchingCondition(parkingSearchConditionRequest);
//        List<Parking> filteredParkingLots = parkingFilteringService.filterByCondition(parkingLots, searchingCondition,
//                now);
//
//        // 응답 dto 변환
//        List<ParkingResponse> parkingResponses = collectParkingInfo(filteredParkingLots,
//                parkingSearchConditionRequest.getHours(), destination, favorites, now);
//
//        return new ParkingLotsResponse(parkingResponses);
//    }
//
//    private SearchingCondition toSearchingCondition(ParkingSearchConditionRequest request) {
//        List<ParkingType> parkingTypes = searchConditionMapper.toEnums(ParkingType.class, request.getParkingTypes());
//        List<OperationType> operationTypes = searchConditionMapper.toEnums(OperationType.class,
//                request.getOperationTypes());
//        List<PayType> payTypes = searchConditionMapper.toEnums(PayType.class, request.getPayTypes());
//        FeeType feeType = searchConditionMapper.toEnum(FeeType.class, request.getFeeType());
//
//        return new SearchingCondition(operationTypes, parkingTypes, payTypes, feeType, request.getHours());
//    }
//
//    private List<Favorite> findMemberFavorites(Long memberId) {
//        if (memberId == null) {
//            return Collections.emptyList();
//        }
//        return favoriteRepository.findByMemberId(Association.from(memberId));
//    }
//
//    private List<Parking> findParkingLotsByOrderCondition(String priority, ParkingQueryRequest parkingQueryRequest,
//                                                          Location middleLocation) {
//        if (priority.equals(DISTANCE_ORDER_CONDITION)) {
//            return parkingRepository.findAroundParkingLotsOrderByDistance(middleLocation.getPoint(),
//                    parkingQueryRequest.getRadius());
//        }
//        return parkingRepository.findAroundParkingLots(middleLocation.getPoint(), parkingQueryRequest.getRadius());
//    }
//
//    private List<ParkingResponse> collectParkingInfo(List<Parking> parkingLots, int hours,
//                                                     Location destination, List<Favorite> memberFavorites,
//                                                     LocalDateTime now) {
//        Set<Long> favoriteParkingIds = extractFavoriteParkingIds(memberFavorites);
//        return calculateParkingInfo(parkingLots, destination, hours, favoriteParkingIds, now);
//    }
//
//    private List<ParkingResponse> calculateParkingInfo(List<Parking> parkingLots, Location destination, int hours,
//                                                       Set<Long> favoriteParkingIds, LocalDateTime now) {
//        return parkingLots.stream()
//                .map(parking -> toParkingResponse(
//                                parking,
//                                parkingFeeCalculator.calculateParkingFee(parking, now, now.plusHours(hours)),
//                                parking.calculateWalkingTime(destination),
//                                favoriteParkingIds.contains(parking.getId())
//                        )
//                ).toList();
//    }
//
//    private Set<Long> extractFavoriteParkingIds(List<Favorite> memberFavorites) {
//        return memberFavorites.stream()
//                .map(Favorite::getParkingId)
//                .map(Association::getId)
//                .collect(Collectors.toSet());
//    }
//
//    private ParkingResponse toParkingResponse(Parking parking, Fee fee, int walkingTime, boolean isFavorite) {
//        return new ParkingResponse(
//                parking.getId(),
//                parking.getBaseInformation().getName(),
//                fee.getFee(),
//                walkingTime,
//                parking.getBaseInformation().getParkingType().getDescription(),
//                isFavorite,
//                parking.getLocation().getLatitude(),
//                parking.getLocation().getLongitude()
//        );
//    }
//
//    @Transactional
//    public void saveAll(List<Parking> parkingLots) {
//        parkingRepository.saveAll(parkingLots);
//    }
//
//    @Transactional(readOnly = true)
//    public Set<Parking> getParkingLots(Set<String> parkingNames) {
//        return parkingRepository.findAllByBaseInformationNameIn(parkingNames);
//    }
//}
