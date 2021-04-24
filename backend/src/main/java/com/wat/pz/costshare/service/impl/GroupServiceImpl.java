package com.wat.pz.costshare.service.impl;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupDto;
import com.wat.pz.costshare.dto.response.GroupPostResponseDto;
import com.wat.pz.costshare.entity.Group;
import com.wat.pz.costshare.entity.User;
import com.wat.pz.costshare.entity.UserGroup;
import com.wat.pz.costshare.repository.GroupRepository;
import com.wat.pz.costshare.repository.UserRepository;
import com.wat.pz.costshare.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public GroupServiceImpl(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public GroupPostResponseDto createGroup(GroupPostRequestDto groupPostRequestDto) {
        User user = userRepository.findById(groupPostRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Error: User with the provided id does not exist!"));

        Group group = new Group();
        group.setName(groupPostRequestDto.getName());
        group.setAccessCode(generateGroupAccessCode());
        group.addAdminUser(user);
        groupRepository.save(group);

        return new GroupPostResponseDto(group.getAccessCode());
    }

    @Override
    public List<GroupDto> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User with the provided id does not exist!"));

        List<GroupDto> groups = new ArrayList<>();
        user.getUserGroups()
        .forEach(ug -> groups.add(new GroupDto(ug.getGroup().getId(), ug.getGroup().getName())));

        return groups;
    }

    private String generateGroupAccessCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        SecureRandom random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
