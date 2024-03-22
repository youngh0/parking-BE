package com.example.parking.config.argumentresolver.parking;

import com.example.parking.application.parking.dto.ParkingSearchConditionRequest;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ParkingSearchConditionArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParkingSearchCondition.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String[] operationTypes = webRequest.getParameterValues("operationTypes");
        String[] parkingTypes = webRequest.getParameterValues("parkingTypes");
        String[] feeTypes = webRequest.getParameterValues("feeTypes");
        String[] payTypes = webRequest.getParameterValues("payTypes");
        Integer hours = Integer.parseInt(webRequest.getParameter("hours"));
        String priority = webRequest.getParameter("priority");
        Long memberId = Long.parseLong(webRequest.getParameter("JSESSIONID"));

        if (containsNull(operationTypes, parkingTypes, feeTypes, payTypes, hours)) {
            return ParkingSearchConditionRequest.base(memberId);
        }

        return new ParkingSearchConditionRequest(
                toCollection(operationTypes),
                toCollection(parkingTypes),
                toCollection(feeTypes),
                toCollection(payTypes),
                hours,
                priority,
                memberId
        );
    }

    private boolean containsNull(String[] operationTypes, String[] parkingTypes, String[] feeTypes,
                                 String[] payTypes, Integer hours) {
        if (operationTypes == null || parkingTypes == null || feeTypes == null || payTypes == null || hours == null) {
            return true;
        }
        return false;
    }

    private List<String> toCollection(String[] parameters) {
        return Arrays.stream(parameters)
                .toList();
    }
}
