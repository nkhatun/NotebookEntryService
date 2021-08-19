package com.demo.codingtask.service;

import com.demo.codingtask.dto.NewEntryRequestDto;
import com.demo.codingtask.dto.ResponseDto;

public interface NotebookEntryService {
	ResponseDto getFreqOccurence(String searchWord);
	ResponseDto getSimilarWords(String searchWord);
	ResponseDto createEntry(NewEntryRequestDto requestDto);
}
