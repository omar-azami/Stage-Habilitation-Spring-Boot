package org.telio.portail_societe.dto.entities;

import java.util.Set;

import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceJointeDTO {
	
	private String pieceNom;
	private String pieceType;
	@Lob
	private byte[] pieceData ;
}
