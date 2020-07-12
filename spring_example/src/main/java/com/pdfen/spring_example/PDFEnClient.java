package com.pdfen.spring_example;

import com.pdfen.spring_example.api.PDFEnAPI;
import com.pdfen.spring_example.models.CreateFileResponse;
import com.pdfen.spring_example.models.CreateSessionResponse;
import com.pdfen.spring_example.models.PdfEnResult;
import com.pdfen.spring_example.models.ProcessRequest;
import com.pdfen.spring_example.models.ProcessSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class PDFEnClient {
    private static final String PDF_EXTENSION = ".pdf";

    @Autowired
    private PDFEnAPI pdfEnAPI;

    public void convert(String inputFile) {
        Path inputPath = Paths.get(inputFile);
        Path outputPath = Paths.get(inputFile + PDF_EXTENSION);
        try {
            convertAndDownloadFile(inputPath, outputPath, FilenameUtils.getExtension(inputFile));
        } catch (Exception e) {
            log.error("Failed to convert and download file", e);
        }
    }

    private void convertAndDownloadFile(Path inputPath, Path outputPath, String inputFileFormat) throws IOException {
        String sessionId = createSession();
        String title = FilenameUtils.removeExtension(inputPath.getFileName().toString());
        String fileId = createFile(title, inputFileFormat, sessionId);
        uploadFile(inputPath, sessionId, fileId);
        PdfEnResult result = convertFile(sessionId);
        downloadFile(outputPath, result);
    }

    private String createSession() {
        CreateSessionResponse session = pdfEnAPI.createSession();
        String sessionId = session.getSessionId();
        log.info("Session ID is: {}", sessionId);
        return sessionId;
    }

    private String createFile(String title, String inputFileFormat, String sessionId) {
        CreateFileResponse createFileResponse = pdfEnAPI.createFile(sessionId, title, inputFileFormat);
        String fileId = createFileResponse.getFileId();
        log.info("File id is: {}", fileId);
        return fileId;
    }

    private void uploadFile(Path path, String sessionId, String fileId) throws IOException {
        log.info("Uploading the file...");
        pdfEnAPI.uploadFile(sessionId, fileId, path);
        log.info("File is uploaded successfully...");
    }

    private PdfEnResult convertFile(String sessionId) {
        ProcessRequest processRequest = buildProcessRequest();
        PdfEnResult result = pdfEnAPI.process(sessionId, processRequest);
        log.info("Messages: {}", result.getProcessResult().getMessages());
        log.info("Used credits: " + result.getProcessResult().getCredits());
        log.info("Remaining credits: " + result.getProcessResult().getCreditsLeft());
        return result;
    }

    private ProcessRequest buildProcessRequest() {
        ProcessRequest processRequest = new ProcessRequest();
        ProcessSettings processSettings = new ProcessSettings();
        processSettings.setImmediate(true);
        processSettings.setProcessSynchronous(true);
        processRequest.setProcessSettings(processSettings);
        return processRequest;
    }

    private void downloadFile(Path outputPath, PdfEnResult result) {
        String url = result.getProcessResult().getUrl();
        pdfEnAPI.downloadTo(url, outputPath);
    }
}
