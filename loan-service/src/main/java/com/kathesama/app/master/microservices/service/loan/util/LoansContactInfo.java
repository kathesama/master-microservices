package com.kathesama.app.master.microservices.service.loan.util;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "loans")
public record LoansContactInfo(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}
