package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.Stock;
import com.jk.cashregister.domain.dto.StockDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockDTOMapper {
		StockDTOMapper INSTANCE = Mappers.getMapper(StockDTOMapper.class);

		StockDTO stockToStockDTO(Stock stock);
		Stock stockDTOToStock(StockDTO stockDTO);
}
