package com.pdfen.spring_example.api;

import com.pdfen.spring_example.models.CreateFileRequest;
import com.pdfen.spring_example.models.CreateFileResponse;
import com.pdfen.spring_example.models.CreateSessionRequest;
import com.pdfen.spring_example.models.CreateSessionResponse;
import com.pdfen.spring_example.models.FileSettings;
import com.pdfen.spring_example.models.PdfEnResult;
import com.pdfen.spring_example.models.ProcessRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
@Component
public class PDFEnAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pdfen.credentials.username}")
    private String userName;

    @Value("${pdfen.credentials.password}")
    private String password;

    @Value("${pdfen.api.endpoints.sessions}")
    private String sessionsEndpoint;

    @Value("${pdfen.api.endpoints.files}")
    private String filesEndpoint;

    @Value("${pdfen.api.endpoints.processes}")
    private String processesEndpoint;

    @Value("${pdfen.api.endpoints.file-data}")
    private String fileUploadEndpoint;


    public CreateSessionResponse createSession() {
        CreateSessionRequest request = new CreateSessionRequest(userName, password);
        return restTemplate.postForObject(sessionsEndpoint, request, CreateSessionResponse.class);
    }

    public CreateFileResponse createFile(String sessionId, String title, String inputFileFormat) {
        FileSettings fileSettings = new FileSettings();
        fileSettings.setTitle(title);
        fileSettings.setExtension(inputFileFormat);
        CreateFileRequest request = new CreateFileRequest(fileSettings);
        return restTemplate.postForObject(filesEndpoint, request, CreateFileResponse.class, sessionId);
    }

    public void uploadFile(String sessionId, String fileId, Path filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        HttpEntity<byte[]> entity = new HttpEntity<>(bytes);
        restTemplate.put(fileUploadEndpoint, entity, sessionId, fileId);
    }

    public PdfEnResult process(String sessionId, ProcessRequest processRequest) {
        return restTemplate.postForObject(processesEndpoint, processRequest, PdfEnResult.class, sessionId);
    }

    public void downloadTo(String url, Path path) {
        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        ResponseExtractor<Void> responseExtractor = saveDownloadedFile(path);
        restTemplate.execute(URI.create(url), HttpMethod.GET, requestCallback, responseExtractor);
    }

    private ResponseExtractor<Void> saveDownloadedFile(Path path) {
        return response -> {
            Files.deleteIfExists(path);
            Files.copy(response.getBody(), path);
            log.info("Successfully downloaded the converted file at: {}", path.toString());
            return null;
        };
    }

}
