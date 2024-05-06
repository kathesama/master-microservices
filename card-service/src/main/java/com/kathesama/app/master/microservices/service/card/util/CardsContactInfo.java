package com.kathesama.app.master.microservices.service.card.util;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfo(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}
