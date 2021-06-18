package org.telio.portail_societe.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Connexion {

    private String login;
    private String password;
}
