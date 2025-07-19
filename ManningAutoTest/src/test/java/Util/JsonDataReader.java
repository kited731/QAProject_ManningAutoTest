package Util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import TestingData.PurchaseTestCase;
import TestingData.PurchaseTestCaseData;
import TestingData.RegistrationTestCase;
import TestingData.RegistrationTestCaseData;

public class JsonDataReader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<PurchaseTestCase> readPurchaseTestData(String fileName) throws IOException {
        File jsonFile = new File("src\\test\\java\\resources\\" + fileName);
        PurchaseTestCaseData purchaseTestData = objectMapper.readValue(jsonFile,
                new TypeReference<PurchaseTestCaseData>() {
                });
        return purchaseTestData.getPurchaseTestCases();
    }

    public List<RegistrationTestCase> readRegistrationTestData(String fileName) throws IOException {
        File jsonFile = new File("src\\test\\java\\resources\\" + fileName);

        RegistrationTestCaseData registrationTestCaseData = objectMapper.readValue(jsonFile,
                new TypeReference<RegistrationTestCaseData>() {
                });
        return registrationTestCaseData.getRegistrationTestCases();
    }
}
