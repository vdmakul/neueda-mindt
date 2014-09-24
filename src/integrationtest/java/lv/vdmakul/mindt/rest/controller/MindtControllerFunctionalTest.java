package lv.vdmakul.mindt.rest.controller;

import lv.vdmakul.mindt.domain.ReferenceTestPlanHelper;
import lv.vdmakul.mindt.rest.WebApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApp.class})
@WebAppConfiguration
public class MindtControllerFunctionalTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldTestFeature() throws Exception {
        String localAddFeature = ReferenceTestPlanHelper.ADD_FEATURE.replace("Neueda", "local");
        mockMvc.perform(
                post("/test?feature=" + localAddFeature).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK (12 tests)")))
                .andExpect(content().string(containsString("Time:")));
    }

    @Test
    public void shouldNotTestFeatureOnGET() throws Exception {
        String localAddFeature = ReferenceTestPlanHelper.ADD_FEATURE.replace("Neueda", "local");
        mockMvc.perform(
                get("/test?feature=" + localAddFeature).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    @Test
    public void shouldNotTestFeatureWithoutParameter() throws Exception {
        mockMvc.perform(
                post("/test").accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldGenerateFeatures() throws Exception {
        mockMvc.perform(
                fileUpload("/generate")
                        .file("file", Files.readAllBytes(Paths.get("src/integrationtest/resources/calc_tests.mm"))))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Feature: Add")))
                .andExpect(content().string(containsString("Feature: Subtract")))
                .andExpect(content().string(containsString("Feature: Multiply")))
                .andExpect(content().string(containsString("Feature: Divide")));
    }
}