package com.example.parking.application.busan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class BusanParkingResponse {

    @JsonProperty("getPblcPrkngInfo")
    private BusanParkingInfo busanParkingInfo;

    public BusanParkingResponse(BusanParkingInfo busanParkingInfo) {
        this.busanParkingInfo = busanParkingInfo;
    }

    @Getter
    @NoArgsConstructor
    public static class BusanParkingInfo {

        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;

        public BusanParkingInfo(Header header, Body body) {
            this.header = header;
            this.body = body;
        }

        @NoArgsConstructor
        public static class Header {
            private String resultCode;
            private String resultMsg;

            public Header(String resultCode, String resultMsg) {
                this.resultCode = resultCode;
                this.resultMsg = resultMsg;
            }
        }

        @Getter
        @NoArgsConstructor
        public static class Body {

            @JsonProperty("items")
            private Items items;

            public Body(Items items) {
                this.items = items;
            }

            @Getter
            @NoArgsConstructor
            public static class Items {

                @JsonProperty("item")
                private List<BusanParkingData> item;

                public Items(List<BusanParkingData> item) {
                    this.item = item;
                }

                @RequiredArgsConstructor
                @Getter
                public static class BusanParkingData {

                    private final String guNm;
                    private final String pkNam;
                    private final String mgntNum;
                    private final String doroAddr;
                    private final String jibunAddr;
                    private final String tponNum;
                    private final String pkFm;
                    private final Integer pkCnt;
                    private final String svcSrtTe;
                    private final String svcEndTe;
                    private final String satSrtTe;
                    private final String satEndTe;
                    private final String hldSrtTe;
                    private final String hldEndTe;
                    private final String ldRtg;
                    private final String tenMin;
                    private final String ftDay;
                    private final String ftMon;
                    private final String xCdnt;
                    private final String yCdnt;
                    private final String fnlDt;
                    private final String pkGubun;
                    private final String bujeGubun;
                    private final String oprDay;
                    private final String feeInfo;
                    private final Integer pkBascTime;
                    private final Integer pkAddTime;
                    private final Integer feeAdd;
                    private final String ftDayApplytime;
                    private final String payMtd;
                    private final String spclNote;
                    private final String currava;
                    private final String oprt_fm;
                }
            }
        }
    }
}
