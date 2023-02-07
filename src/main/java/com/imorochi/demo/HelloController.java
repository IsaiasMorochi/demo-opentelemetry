package com.imorochi.demo;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
public class HelloController {

    private final Tracer tracer;

    @Autowired
    public HelloController(OpenTelemetry openTelemetry) {
        this.tracer = openTelemetry.getTracer(DemoApplication.class.getName());
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<?> helloWorld() {
        log.info("GETTING MESSAGE HELLO ... {}", Math.random());
        addCustomLog();
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Hello World OpenTelemetry !!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void addCustomLog() {
        // 1.----
        final Span currentSpan = Span.current();
        AttributesBuilder builder = Attributes.builder();
        if (currentSpan != null) {
            builder.put("logger", "LOGGER")
                    .put("level", "INFO")
                    .put("message", "DEMO LOG");
            currentSpan.addEvent("Logs", builder.build());
        }

        //2.---
        Span span = this.tracer.spanBuilder("Attribute").startSpan();
        try (Scope ignored = span.makeCurrent()) {
            span.setAttribute("TAG_1", "V1.0.1");
            span.setAttribute("TAG_2", "V2.0.1");
        } finally {
            span.end();
        }

    }

}
