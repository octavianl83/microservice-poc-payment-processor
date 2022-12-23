package com.logicore.rest.services.serviceflowprocessor.flow;

import java.io.IOException;
import java.util.HashMap;

public interface Selector {
    HashMap<String, Object> loadFlow() throws IOException;
}
