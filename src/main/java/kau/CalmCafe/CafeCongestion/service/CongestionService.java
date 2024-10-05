package kau.CalmCafe.CafeCongestion.service;

import kau.CalmCafe.CafeCongestion.converter.CongestionConverter;
import kau.CalmCafe.CafeCongestion.domain.Congestion;
import kau.CalmCafe.CafeCongestion.dto.CongestionDto;
import kau.CalmCafe.CafeCongestion.repository.CongestionRepository;
import kau.CalmCafe.global.api_payload.ErrorCode;
import kau.CalmCafe.global.exception.GeneralException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CongestionService {

    private final CongestionRepository congestionRepository;
    private final CongestionConverter congestionConverter;

    public CongestionDto.Response createCafe(CongestionDto.Request request) {
        if (congestionRepository.existsByCafeName(request.getCafeName())) {
            throw new GeneralException(ErrorCode.CAFE_ALREADY_EXISTS);
        }
        Congestion congestion = congestionConverter.toEntity(request);
        Congestion savedCongestion = congestionRepository.save(congestion);
        return congestionConverter.toDto(savedCongestion);
    }

    public CongestionDto.Response updateCongestion(CongestionDto.Request request) {
        Congestion congestion = congestionRepository.findByCafeName(request.getCafeName())
                .orElseThrow(() -> new GeneralException(ErrorCode.CAFE_NOT_FOUND));

        congestion.setCongestionLevel(request.getCongestionLevel());
        Congestion updatedCongestion = congestionRepository.save(congestion);
        return congestionConverter.toDto(updatedCongestion);
    }

    @Transactional(readOnly = true)
    public List<CongestionDto.Response> getAllCafes() {
        List<Congestion> congestions = congestionRepository.findAll();
        if (congestions.isEmpty()) {
            throw new GeneralException(ErrorCode.NO_CAFES_FOUND);
        }
        return congestions.stream()
                .map(congestionConverter::toDto)
                .collect(Collectors.toList());
    }
}