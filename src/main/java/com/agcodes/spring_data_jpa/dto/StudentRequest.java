package com.agcodes.spring_data_jpa.dto;

import java.util.List;

public record StudentRequest(String firstName,
                             String lastName,
                             String email,
                             int age,
                             String cardNumber,
                             List<String> books) {

}
