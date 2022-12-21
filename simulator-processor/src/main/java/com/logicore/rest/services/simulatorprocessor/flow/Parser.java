package com.logicore.rest.services.simulatorprocessor.flow;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Parser {

    LinkedHashMap<String, String> entryPoints = new LinkedHashMap<>();
    LinkedHashMap<String, String> exitPoints = new LinkedHashMap<>();
    LinkedHashMap<String, Map<String, String>> tasks = new LinkedHashMap<>();
    String commitLogTopic = null;
    List<String> template = null;

    public Parser(HashMap<String, Object> flow) {
        this.entryPoints = (LinkedHashMap<String, String>) flow.get("EntryPoints");
        this.exitPoints = (LinkedHashMap<String, String>) flow.get("ExitPoints");
        this.tasks = (LinkedHashMap<String, Map<String, String>>) flow.get("Tasks");
        this.commitLogTopic = (String) flow.get("CommitLogTopic");
        this.template = (List<String>) flow.get("Templates");

    }


    public static void main(String[] args) throws IOException {
        Selector selector = new Selector("tenant1");
        HashMap<String, Object> flow = selector.loadFlow();
        Parser parser = new Parser(flow);
        log.info("Parser");
    }
}
