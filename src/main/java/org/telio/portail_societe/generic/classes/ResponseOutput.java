package org.telio.portail_societe.generic.classes;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseOutput <T> {

    private String code;
    private String statut;
    private String message;
    private String typeOperation;
    private T data;
    private List<T> collection;
}
