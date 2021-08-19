package com.demo.codingtask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.codingtask.api.v1.controller.NotebookEntryController;
import com.demo.codingtask.dto.NewEntryRequestDto;
import com.demo.codingtask.dto.NoteEntryDto;
import com.demo.codingtask.service.NotebookEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NotebookEntryController.class)
public class NotebookEntryControllerTest {
	private static final String BASE_URI = "/api/v1/notebook/";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private NotebookEntryService entryService;
	
	@Test
	@DisplayName("test to get create new notebook entry")
    public void test_create_new_notebook_entry() throws Exception {
		List<NoteEntryDto> entryList = new ArrayList<NoteEntryDto>();
		NoteEntryDto entry1 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go!");
		NoteEntryDto entry2 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go again!");
		entryList.add(entry1);
		entryList.add(entry2);
		NewEntryRequestDto entryRequest = new NewEntryRequestDto()
				.setProjectName("Demo project 1").setNoteEntryDtoList(entryList);

	    mockMvc.perform(post(BASE_URI+"/createEntry")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(entryRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("test to get similar words present")
    public void test_get_similar_words() throws Exception {
		List<NoteEntryDto> entryList = new ArrayList<NoteEntryDto>();
		NoteEntryDto entry1 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go!");
		NoteEntryDto entry2 = new NoteEntryDto().setNoteType("TEXT")
				.setNoteContent("Here you go again!");
		entryList.add(entry1);
		entryList.add(entry2);
		NewEntryRequestDto entryRequest = new NewEntryRequestDto()
				.setProjectName("Demo project 1").setNoteEntryDtoList(entryList);

	    mockMvc.perform(post(BASE_URI+"/createEntry")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic("demo", "demo"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(entryRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	    
	    String searchword = "here";
		String uri = BASE_URI + "similarwords?searchword=" + searchword;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.with(httpBasic("demo", "demo"))
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	@DisplayName("test to get words frequency")
    public void test_get_frequency_occurence() throws Exception {
		String searchword = "her";
		String uri = BASE_URI + "similarwords?searchword=" + searchword;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.with(httpBasic("demo", "demo"))
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		System.out.println("COntent here"+content);
		/*
		 * ResponseDto responseDto = super.mapFromJson(content,
		 * ResponseDto.class); LinkedHashMap map = (LinkedHashMap)
		 * responseDto.getData().getDataItems() .get(0); List<String>
		 * similarWords = (List<String>) map.get("similarWords");
		 * assert(similarWords.size() > 0);
		 */
	}
}
