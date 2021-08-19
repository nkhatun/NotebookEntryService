package com.demo.codingtask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.codingtask.dto.NewEntryRequestDto;
import com.demo.codingtask.dto.NoteEntryDto;
import com.demo.codingtask.dto.ResponseDto;
import com.demo.codingtask.service.NotebookEntryService;
@TestMethodOrder(OrderAnnotation.class)
public class ControllerUnitTest extends ParentUnitTest {
	@MockBean
    NotebookEntryService entryService;
	
	private static final String BASE_URI = "/api/v1/notebook/";
	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();
	}

	// fetch frequency fails when no entry is created
	@Test
//	@Order(1)
	public void getFrequencyNotPresent() throws Exception {
		//prepare
		/*
		 * NewEntryRequestDto requestDto = new NewEntryRequestDto(); ResponseDto
		 * expectedResponse = new ResponseDto();
		 * when(entryService.createEntry(requestDto)).thenReturn(
		 * expectedResponse);
		 */
		String searchword = "here";
		String uri = BASE_URI + "frequency?searchword=" + searchword;
		//execute
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		//verify
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("content not present " + content);
		ResponseDto responseDto = super.mapFromJson(content, ResponseDto.class);
		assertTrue(responseDto.getStatus().equalsIgnoreCase("fail"));
	}
	// fetch similar words fails when no entry is created
	@Test
	@Order(2)
	public void getSimilarWordsNotPresent() throws Exception {
		String searchword = "here";
		String uri = BASE_URI + "similarwords?searchword=" + searchword;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("content not present " + content);
		ResponseDto responseDto = super.mapFromJson(content, ResponseDto.class);
		assertTrue(responseDto.getStatus().equalsIgnoreCase("fail"));
	}

	// Testing creation of entry success
	@Test
	@Order(3)
	public void createEntry() throws Exception {
		String uri = BASE_URI + "createEntry";
		List<NoteEntryDto> entryList = new ArrayList<NoteEntryDto>();
		NoteEntryDto entry1 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go!");
		NoteEntryDto entry2 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go again!");
		entryList.add(entry1);
		entryList.add(entry2);
		NewEntryRequestDto entryRequest = new NewEntryRequestDto()
				.setProjectName("Test1").setNoteEntryDtoList(entryList);
		String inputJson = super.mapToJson(entryRequest);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ResponseDto responseDto = super.mapFromJson(content, ResponseDto.class);
		assertEquals(responseDto.getStatus(), "success");
	}

	// fetch frequency fails when no entry is created
	@Test
	@Order(4)
	public void getFrequencyPresent() throws Exception {
		String searchword = "your";
		String uri = BASE_URI + "frequency?searchword=" + searchword;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		ResponseDto responseDto = super.mapFromJson(content, ResponseDto.class);
		LinkedHashMap map = (LinkedHashMap) responseDto.getData().getDataItems()
				.get(0);
		int freq = ((Number) map.get("frequency")).intValue();
		assertEquals(0, freq);
	}

	// fetch similar words fails when no entry is created
	@Test
	@Order(4)
	public void getSimilarWordsPresent() throws Exception {
		String searchword = "her";
		String uri = BASE_URI + "similarwords?searchword=" + searchword;
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		ResponseDto responseDto = super.mapFromJson(content, ResponseDto.class);
		LinkedHashMap map = (LinkedHashMap) responseDto.getData().getDataItems()
				.get(0);
		List<String>  similarWords = (List<String>) map.get("similarWords");
		assert(similarWords.size() > 0);
	}
}
