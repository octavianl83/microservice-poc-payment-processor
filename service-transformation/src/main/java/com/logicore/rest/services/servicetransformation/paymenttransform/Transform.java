package com.logicore.rest.services.servicetransformation.paymenttransform;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.payment.PaymentMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Transform {

    public static PaymentMessage transformPaymentMessage(String jarName, PaymentMessage paymentMessage) throws IOException, InterruptedException {
        // Create a ProcessBuilder object to run the JAR file
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", jarName);

        // Start the process and obtain a Process object
        Process p = pb.start();

        // Collect the output of the process
        String output = collectOutput(p);

        // Wait for the process to finish
        int exitCode = p.waitFor();

        // Print the output and exit code
        System.out.println(output);
        System.out.println("Exit code: " + exitCode);

        ObjectMapper objectMapper = new ObjectMapper();
        PaymentMessage paymentMessageTransform = objectMapper.readValue(output, PaymentMessage.class);

        return paymentMessageTransform;
    }

    private static String collectOutput(Process p) throws IOException {
        // Collect the output of the process into a string
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }

        return sb.toString();
    }

}
