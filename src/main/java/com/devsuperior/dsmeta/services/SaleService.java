package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.projections.SaleReportProjection;
import com.devsuperior.dsmeta.projections.SaleSummaryProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleReportDTO> getReport(String minDate, String maxDate, String name) {
		LocalDate initialDate;
		LocalDate finalDate;

		if (maxDate == null) {
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finalDate = LocalDate.parse(maxDate, formatter);
		}

		if (minDate == null) {
			initialDate = finalDate.minusYears(1L);
		} else {
			initialDate = LocalDate.parse(minDate, formatter);
		}

		if (name == null) {
			name = "";
		}

		List<SaleReportProjection> result = repository.getReport(initialDate, finalDate, name);

		return result.stream().map(SaleReportDTO::new).toList();
	}


	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate) {
		LocalDate initialDate;
		LocalDate finalDate;

		if (maxDate == null) {
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finalDate = LocalDate.parse(maxDate, formatter);
		}

		if (minDate == null) {
			initialDate = finalDate.minusYears(1L);
		} else {
			initialDate = LocalDate.parse(minDate, formatter);
		}

		List<SaleSummaryProjection> result = repository.getSummary(initialDate, finalDate);

		return result.stream().map(SaleSummaryDTO::new).toList();
	}
}
