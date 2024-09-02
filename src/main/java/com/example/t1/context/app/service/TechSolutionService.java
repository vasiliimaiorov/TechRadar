package com.example.t1.context.app.service;

import com.example.t1.api.exeptions.InvalidUrlException;
import com.example.t1.context.app.dto.TechSolutionDTO;
import com.example.t1.context.app.mapper.TechSolutionMapper;
import com.example.t1.context.app.model.TechSolution;
import com.example.t1.context.app.model.enums.Category;
import com.example.t1.context.app.model.enums.Status;
import com.example.t1.context.app.repository.TechSolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TechSolutionService {

    private final TechSolutionRepository techSolutionRepository;
    private final RangeCheckerService rangeCheckerService;

    public Long createTechSolution(TechSolutionDTO techSolutionDTO) throws Exception {
        TechSolution techSolution = TechSolutionMapper.toEntityTechSolution(techSolutionDTO);
//        if (techSolutionDTO.getDocumentationUrl() != null
//                && !techSolutionDTO.getDocumentationUrl().isBlank()
//                && !(isAvailable(techSolutionDTO.getDocumentationUrl()))) {
//            throw new InvalidUrlException("Некорректная ссылка на документацию");
//        }
        techSolution.setCurrentEffectiveness(0d);
        return techSolutionRepository.save(techSolution).getId();
    }

    public boolean deleteTechSolution(Long techSolutionId) {
        return techSolutionRepository.findById(techSolutionId)
                .map(techSolution -> {
                    techSolutionRepository.delete(techSolution);
                    return true;
                })
                .orElse(false);
    }

    public List<TechSolution> getAllTechSolutions(){
        return new ArrayList<>(techSolutionRepository.findAll());
    }


    public boolean updateTechSolution(TechSolution updatedTechSolution) throws Exception {
//        if (updatedTechSolution.getDocumentationUrl() != null
//                && !updatedTechSolution.getDocumentationUrl().isBlank()
//                && !(isAvailable(updatedTechSolution.getDocumentationUrl()))) {
//            throw new InvalidUrlException("Некорректная ссылка на документацию");
//        }
        return techSolutionRepository.findById(updatedTechSolution.getId())
                .map(existingTechSolution -> {
                    existingTechSolution.setName(updatedTechSolution.getName());
                    existingTechSolution.setDocumentationUrl(updatedTechSolution.getDocumentationUrl());
                    existingTechSolution.setCategory(updatedTechSolution.getCategory());
                    techSolutionRepository.save(existingTechSolution);
                    return true;
                })
                .orElse(false);
    }

    public List<TechSolution> getTechSolutionsByCategory(Category category) {
        return new ArrayList<>(techSolutionRepository.findByCategory(category));
    }

    public List<Long> getTechSolutionsIdByCategory(Category category) {
        return techSolutionRepository.findByCategory(category).stream()
                .map(TechSolution::getId)
                .collect(Collectors.toList());
    }

    public void updateFields(TechSolution techSolution) {
        techSolutionRepository.save(techSolution);
    }

    public void setNewStatus(TechSolution techSolution, Double newEffectiveness) {
        var oldRingNum = rangeCheckerService.determineRange(techSolution.getCurrentEffectiveness());
        var newRingNum = rangeCheckerService.determineRange(newEffectiveness);
        var difference = oldRingNum - newRingNum;

        if (difference > 0) {
            techSolution.setStatus(Status.MOVED_UP);
        } else if (difference < 0) {
            techSolution.setStatus(Status.MOVED_DOWN);
        }
    }

    public Optional<TechSolution> getTechSolutionById(Long techSolutionId) {
        return techSolutionRepository.findById(techSolutionId);
    }

    public boolean checkPresenceById(Long techSolutionId) {
        return techSolutionRepository.findById(techSolutionId).isPresent();
    }

    public List<TechSolution> getAllNotNullSolutions(){
        return techSolutionRepository.findAll();
    }

    public boolean isAvailable(String url) throws InvalidUrlException {

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("Неверный формат URL: " + url);
        } catch (IOException e) {
            throw new InvalidUrlException("Ошибка при подключении к URL: " + url);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
