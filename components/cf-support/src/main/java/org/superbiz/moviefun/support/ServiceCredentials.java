package org.superbiz.moviefun.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class ServiceCredentials {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String vcapServices;

    public ServiceCredentials(String vcapServices) {
        this.vcapServices = vcapServices;
    }

    public String getCredential(String serviceName, String serviceType, String credentialKey) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode root;

        try {
            logger.debug("VCAP: {}", vcapServices);
            root = objectMapper.readTree(vcapServices);
        } catch (IOException e) {
            throw new IllegalStateException("No VCAP_SERVICES found", e);
        }

        JsonNode services = root.path(serviceType);

        for (JsonNode service : services) {
            logger.debug("VCAP service: {}", service);
            if (Objects.equals(service.get("name").asText(), serviceName)) {
                logger.debug("VCAP credentials for service {}: {}", serviceName, service.get("credentials").get(credentialKey).asText());
                return service.get("credentials").get(credentialKey).asText();
            }
        }

        throw new IllegalStateException("No "+ serviceName + " found in VCAP_SERVICES");
    }
}
