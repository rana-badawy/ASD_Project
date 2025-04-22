package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.UserRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.UserResponseDto;
import edu.miu.cs.cs489.surgeries.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses={RoleMapper.class})
public interface UserMapper {
    @Mapping(source = "roleRequestDto", target = "role")
    User UserRequestDtoToUser(UserRequestDto userRequestDto);

    @Mapping(source = "role", target = "roleResponseDto")
    UserResponseDto UserToUserResponseDto(User user);

    @Mapping(source = "role", target = "roleResponseDto")
    List<UserResponseDto> UserToUserResponseDtoList(List<User> users);
}
