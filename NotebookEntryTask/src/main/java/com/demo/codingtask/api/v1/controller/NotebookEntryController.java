package com.demo.codingtask.api.v1.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.codingtask.dto.NewEntryRequestDto;
import com.demo.codingtask.dto.ResponseDto;
import com.demo.codingtask.service.NotebookEntryService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/v1/notebook/")
public class NotebookEntryController {
	private static Logger logger = LoggerFactory
			.getLogger(NotebookEntryController.class);

	@Autowired
	private NotebookEntryService entryService;

	@GetMapping(value = "frequency", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto getFreqOcuurence(
			@RequestParam @Valid @NotNull String searchword) {
		logger.info("API call to get frequency of the word '" + searchword
				+ "' started");
		return entryService.getFreqOccurence(searchword);
	}
	@GetMapping(value = "similarwords", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto getSimilarWords(
			@RequestParam @Valid @NotNull String searchword) {
		logger.info("API call to get similar words for '" + searchword
				+ "' started");
		return entryService.getSimilarWords(searchword);
	}
	@PostMapping(value = "createEntry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDto createNotebookEntry(
			@RequestBody @Valid NewEntryRequestDto entryRequest) {
		logger.info(
				"API call to create notebook entry started with the entries: "
						+ entryRequest.getNoteEntryDtoList());
		return entryService.createEntry(entryRequest);
	}

}
