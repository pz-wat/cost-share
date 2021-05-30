package com.wat.pz.costshare.service.impl;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupExpense;
import com.wat.pz.costshare.dto.response.GroupResponseDto;
import com.wat.pz.costshare.dto.response.GroupUser;
import com.wat.pz.costshare.entity.Group;
import com.wat.pz.costshare.entity.User;
import com.wat.pz.costshare.entity.UserGroup;
import com.wat.pz.costshare.repository.GroupRepository;
import com.wat.pz.costshare.repository.UserRepository;
import com.wat.pz.costshare.service.GroupService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
    public void addUserToGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        List<UserGroup> userGroups = group.getUserGroups().stream()
                .filter(userGroup -> userGroup.getGroup().getId().equals(group.getId()) &&
                        userGroup.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());

        if(!userGroups.isEmpty()) {
            throw new IllegalArgumentException("User with the provided id already belongs to the group!");
        }

        group.addCommonUser(user);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public GroupResponseDto findGroupById(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        List<UserGroup> userGroups = user.getUserGroups().stream()
                .filter(userGroup -> userGroup.getGroup().getId().equals(group.getId()))
                .collect(Collectors.toList());

        return new GroupResponseDto(group.getId(), group.getName(), group.getAccessCode(), group.getDateCreated(),
                userGroups.get(0).isAdmin(), getGroupExpenses(group), getGroupUsers(group));
    }

    @Override
    @Transactional
    public GroupResponseDto findGroupByAccessCode(String accessCode) {
        Group group = groupRepository.findByAccessCode(accessCode)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided access code does not exist!"));

        return new GroupResponseDto(group.getId(), group.getName(), group.getAccessCode(), group.getDateCreated(),
                false, getGroupExpenses(group), getGroupUsers(group));
    }

    @Override
    @Transactional
    public List<GroupResponseDto> findAllGroupsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));

        List<GroupResponseDto> groups = new ArrayList<>();
        user.getUserGroups()
        .forEach(ug -> {
            Group group = ug.getGroup();
            groups.add(new GroupResponseDto(group.getId(), group.getName(), group.getAccessCode(), group.getDateCreated(),
                    ug.isAdmin(), getGroupExpenses(group), getGroupUsers(group)));
        });

        groups.sort(Comparator.comparing(GroupResponseDto::getName));
        return groups;
    }

    private List<GroupExpense> getGroupExpenses(Group group) {
        List<GroupExpense> groupExpenses = new ArrayList<>();
        group.getExpenses()
                .forEach(expense -> groupExpenses.add(new GroupExpense(expense.getId(),
                        expense.getName(), expense.getDateCreated())));
        return groupExpenses;
    }

    private List<GroupUser> getGroupUsers(Group group) {
        List<GroupUser> groupUsers = new ArrayList<>();
        group.getUserGroups()
                .forEach(userGroup -> groupUsers.add(new GroupUser(userGroup.getUser().getId(),
                        userGroup.getUser().getUsername())));
        return groupUsers;
    }

    @Override
    @Transactional
    public GroupResponseDto createGroup(GroupPostRequestDto groupPostRequestDto) {
        User user = userRepository.findById(groupPostRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));

        Group group = new Group();
        group.setName(groupPostRequestDto.getName());
        group.setAccessCode(generateGroupAccessCode());
        group.addAdminUser(user);
        groupRepository.save(group);

        return new GroupResponseDto(group.getId(), group.getName(), group.getAccessCode(),
                group.getDateCreated(), true, List.of(), List.of());
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

    @Override
    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));
        groupRepository.delete(group);
    }

    @Override
    @Transactional
    public void deleteUserFromGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        boolean removedSuccessfully = group.removeUser(user);

        if(!removedSuccessfully) {
            throw new IllegalArgumentException("User with the provided id wasn't in the group");
        }
    }
}
