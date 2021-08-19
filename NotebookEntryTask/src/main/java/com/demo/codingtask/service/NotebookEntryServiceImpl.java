package com.demo.codingtask.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.codingtask.dto.DataDto;
import com.demo.codingtask.dto.NewEntryRequestDto;
import com.demo.codingtask.dto.NoteEntryDto;
import com.demo.codingtask.dto.NoteEntryType;
import com.demo.codingtask.dto.NotebookDto;
import com.demo.codingtask.dto.ResponseDto;
import com.demo.codingtask.utility.Constants;
import com.demo.codingtask.utility.ServiceUtilities;

@Service
public class NotebookEntryServiceImpl implements NotebookEntryService {
    private static Logger logger = LoggerFactory.getLogger(NotebookEntryServiceImpl.class);
    
    //the below map to store each words present in notebook as key along with count
	private static Map<String, Integer> map = new HashMap<>();
    //the below list to store similar words present in notebook for a given search string
	private static List<String> similarWords = new ArrayList<>();;

	@Override
	public ResponseDto getFreqOccurence(String searchWord) {
		if (map.isEmpty()) {
			logger.info("Empty map:getFreqOccurence  no notebook entry has created still");
			return new ResponseDto(Constants.REST_STATUS_FAIL, null,
					"No Entry Is Created ");
		}
		if(map.containsKey(searchWord)) {
		Long freqCount = map.get(searchWord).longValue();
		logger.info("Frequency count for the search string '"+searchWord+"' :: "+freqCount);
		return new ResponseDto(Constants.REST_STATUS_SUCCESS,
				new DataDto<>(Collections
						.singletonList(new NotebookDto(searchWord, freqCount))),
				"Frequency of occurence for '" + searchWord
						+ "' is fetched successfully");
		}
		else {
			logger.info("Map doesnt contain the search string, hence return 0 count");
			return new ResponseDto(Constants.REST_STATUS_SUCCESS,
					new DataDto<>(Collections
							.singletonList(new NotebookDto(searchWord, 0L))),
					"Frequency of occurence for '" + searchWord
							+ "' is fetched successfully");
		}
	}

	@Override
	public ResponseDto getSimilarWords(String searchWord) {
		if (map.isEmpty()) {
			logger.info("Empty getSimilarWords  no notebook entry has created still");
			return new ResponseDto(Constants.REST_STATUS_FAIL, null,
					"No Entry Is Created");
		}
		// 3. get the levenshtein distance for each string and search string
		// 4. collect all the strings with min distance <=1
		// 5. filter the similar words
		similarWords = map.keySet().stream().filter(compareString -> {
			if(compareString.equals(searchWord)) {
				return false;
			}
			int distance = ServiceUtilities.getLavenshteinDistance(searchWord,
					compareString);
			return distance <= 1 ? true : false;
		}).collect(Collectors.toList());
		logger.info("Similar words for the search string '"+searchWord+"' :: "+similarWords);
		if (!similarWords.isEmpty()) {
			return new ResponseDto(Constants.REST_STATUS_SUCCESS,
					new DataDto<>(Collections.singletonList(
							new NotebookDto(searchWord, similarWords))),
					"Simlar Words Are fetched successfully For: " + searchWord);

		}
		logger.info("Map doesnt contain anysimilar word the search string");
		return new ResponseDto(Constants.REST_STATUS_FAIL, null,
				"No Simlar Words Are Available For: " + searchWord);
	}

	@Override
	public ResponseDto createEntry(NewEntryRequestDto requestDto) {
		try {
			if(requestDto != null) {
			//Initialising the map for each entry
			 map = new HashMap<>();
			String projectName = requestDto.getProjectName();
			logger.info("Request for creating a new entry :: project name "+ projectName);
			List<NoteEntryDto> entryList = requestDto.getNoteEntryDtoList();
			logger.info("Request for creating a new entry :: note entry list "+ entryList);

			if (!entryList.isEmpty()) {
				// 1. check the entry type - text
				// 2. map the entry text as map of separate strings
				entryList.stream()
						.filter(typeFilter -> typeFilter.getNoteType()
								.equals(NoteEntryType.TEXT.name()))
						.map(mapper -> {
							String[] split = mapper.getNoteContent().trim()
									.split("[ .,?!]+");
							Arrays.stream(split).map(key -> {
								map.put(key,
										map.containsKey(key)
												? map.get(key) + 1
												: 1);
								return key;
							}).collect(Collectors.toList());
							return mapper;
						}).collect(Collectors.toList());
			}
			logger.info("Request for creating a new entry :: no note content present");
			if (!map.isEmpty()) {
				return new ResponseDto(Constants.REST_STATUS_SUCCESS, null,
						"New Entry Has Been Created Under Project: "
								+ projectName);
			}
			return new ResponseDto(Constants.REST_STATUS_FAIL, null,
					"No Entry Is Created Under Project: " + projectName);
			}
			return new ResponseDto(Constants.REST_STATUS_ERROR, null,
					Constants.INVALID_REQUEST);

		} catch (Exception e) {
			return new ResponseDto(Constants.REST_STATUS_ERROR, null,
					e.getMessage());
		}
	}
}
