package org.telio.portail_societe.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceJointe implements Serializable {
	
	private String pieceNom;
	private String pieceType;
	@Lob
	private byte[] pieceData ;	
	

}
