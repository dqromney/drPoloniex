package com.dqr.poloniex.dto;

import lombok.Data;

/**
 * Troll Box Message DTO.
 * <p>
 * Message Structure:
 * type, messageNumber, username, message, reputation
 * <p>
 * Ex. Data
 * ['trollboxMessage',2094211,'boxOfTroll','Trololol',4]
 * <p>
 * Created by dqromney on 10/28/17.
 */
@Data
public class Trollbox {
    private String type;
    private Long messageNumber;
    private String username;
    private String message;
    private Integer reputation;
}
