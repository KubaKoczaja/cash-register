package com.jk.cashregister.service.mapper;

import com.jk.cashregister.domain.User;
import com.jk.cashregister.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper {
		UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);
		UserDTO userToUserDTO(User user);
		User userDTOToUser(UserDTO userDTO);
}
