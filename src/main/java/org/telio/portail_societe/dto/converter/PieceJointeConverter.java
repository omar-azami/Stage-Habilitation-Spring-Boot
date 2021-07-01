package org.telio.portail_societe.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.MenuDTO;
import org.telio.portail_societe.dto.entities.PieceJointeDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Menu;
import org.telio.portail_societe.model.PieceJointe;

@Component

public class PieceJointeConverter implements MapperMethods <PieceJointeDTO, PieceJointe>{

	@Override
	public PieceJointeDTO toVo(PieceJointe data) {
		PieceJointeDTO pieceJointeDTO = new PieceJointeDTO();
		pieceJointeDTO.setPieceData(data.getPieceData());
		pieceJointeDTO.setPieceNom(data.getPieceNom());
		pieceJointeDTO.setPieceType(data.getPieceType());
		return pieceJointeDTO;
	}

	@Override
	public PieceJointe toBo(PieceJointeDTO data) {
		PieceJointe pieceJointe = new PieceJointe();
		pieceJointe.setPieceData(data.getPieceData());
		pieceJointe.setPieceNom(data.getPieceNom());
		pieceJointe.setPieceType(data.getPieceType());
		return pieceJointe;
	}

	@Override
	public List<PieceJointeDTO> toVoList(List<PieceJointe> listData) {
		List<PieceJointeDTO> pieceJointeDTO = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->pieceJointeDTO.add(toVo(data)));
        }
        return pieceJointeDTO;
	}

	@Override
	public List<PieceJointe> toBoList(List<PieceJointeDTO> listData) {
		List<PieceJointe> pieceJointes = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->pieceJointes.add(toBo(data)));
        }
        return pieceJointes;
	}

}
