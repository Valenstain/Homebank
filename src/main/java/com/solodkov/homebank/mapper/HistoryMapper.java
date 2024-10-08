package com.solodkov.homebank.mapper;

import com.solodkov.homebank.dto.HistoryDto;
import com.solodkov.homebank.dto.TransferDto;
import com.solodkov.homebank.enums.AccountOperation;
import com.solodkov.homebank.model.History;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = ComponentModel.SPRING,
    imports = {AccountOperation.class})
public interface HistoryMapper {

  @Mapping(target = "user", ignore = true)
  @Mapping(target = "accountFrom", source = "fromAccountId")
  @Mapping(target = "accountTo", source = "toAccountId")
  History toHistory(TransferDto transferDto);

  @Mapping(target = "username", source = "user.name")
  List<HistoryDto> toHistoryDto(List<History> history);
}
