package com.demo.codingtask.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotebookDto {
	public NotebookDto(String searchword, Long frequency) {
		this.searchword = searchword;
		this.frequency = frequency;
	}
	public NotebookDto(String searchword, List<String> similarWords) {
		this.searchword = searchword;
		this.similarWords = similarWords;
	}
	private String searchword;
	private List<String> similarWords;
	private Long frequency;
}
