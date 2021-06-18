package org.telio.portail_societe.generic.interfaces;

import java.util.List;

public interface MapperMethods <T,D> {
 T toVo (D data);
 D toBo (T data);
 List<T> toVoList (List<D> listData);
 List<D> toBoList (List<T> listData);
}
