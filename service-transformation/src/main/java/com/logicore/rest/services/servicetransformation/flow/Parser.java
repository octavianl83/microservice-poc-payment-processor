package com.logicore.rest.services.servicetransformation.flow;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class Parser {

    LinkedHashMap<String, LinkedHashMap<String, String>> entryPoints = new LinkedHashMap<String, LinkedHashMap<String, String>>();

    public Parser(HashMap<String, Object> flow) {
        this.entryPoints = (LinkedHashMap<String, LinkedHashMap<String, String>>) flow.get("EntryPoints");
    }

    public static void main(String[] args) throws IOException {
        Selector selector = new LocalSelector();
        HashMap<String, Object> flow = selector.loadFlow("tenant1");
        Parser parser = new Parser(flow);
        log.info("Parser");
    }
}
