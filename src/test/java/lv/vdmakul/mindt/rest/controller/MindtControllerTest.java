package lv.vdmakul.mindt.rest.controller;

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
public class MindtControllerTest {


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
        mockMvc.perform(
                post("/test?feature=" + ADD_FEATURE).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK (12 tests)")))
                .andExpect(content().string(containsString("Time:")));
    }

    @Test
    public void shouldNotTestFeatureOnGET() throws Exception {
        mockMvc.perform(
                get("/test?feature=" + ADD_FEATURE).accept(MediaType.APPLICATION_JSON))
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
                        .file("file", Files.readAllBytes(Paths.get("src/test/resources/calc_tests.mm"))))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Feature: Add")))
                .andExpect(content().string(containsString("Feature: Subtract")))
                .andExpect(content().string(containsString("Feature: Multiply")))
                .andExpect(content().string(containsString("Feature: Divide")));
    }

    private final static String ADD_FEATURE = "Feature: Add\n" +
            "\n" +
            "Background: \n" +
            "\tGiven A local calculator\n" +
            "\tAnd request path is \"/rest/add\"\n" +
            "\tAnd request method is \"POST\"\n" +
            "\n" +
            "Scenario: simple addition\n" +
            "\tWhen I enter 6 and 8\n" +
            "\tThen result is 14\n" +
            "\n" +
            "Scenario: adding a negative number\n" +
            "\tWhen I enter -5.34 and 3.95\n" +
            "\tThen result is -1.39\n" +
            "\n";
}