package lv.vdmakul.mindt.rest.controller;

import lv.vdmakul.mindt.rest.controller.response.ErrorResponse;
import lv.vdmakul.mindt.rest.controller.response.MindtException;
import lv.vdmakul.mindt.rest.controller.response.TestResultResponse;
import lv.vdmakul.mindt.service.MindtFacade;
import lv.vdmakul.mindt.service.testing.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class MindtController {

    @Autowired private MindtFacade mindtFacade;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public TestResultResponse test(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            InputStream mindMapInputStream;
            try {
                mindMapInputStream = file.getInputStream();
            } catch (IOException e) {
                throw new MindtException("Failed to process file: " + e.getMessage(), e);
            }
            List<TestResult> testResults = mindtFacade.testByPlanFromMindMap(mindMapInputStream);
            return TestResultResponse.createFromResults(testResults);
        } else {
            throw new MindtException("MindMap file is empty");
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(httpStatus, ex.getMessage()), httpStatus);
    }

}