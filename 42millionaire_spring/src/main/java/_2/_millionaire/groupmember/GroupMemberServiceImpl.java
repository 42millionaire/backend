package _2._millionaire.groupmember;

import _2._millionaire.group.GroupRepository;
import _2._millionaire.group.Groups;
import _2._millionaire.groupjoin.GroupJoin;
import _2._millionaire.groupjoin.GroupJoinRepository;
import _2._millionaire.groupjoin.GroupJoinServiceImpl;
import _2._millionaire.groupmember.dto.*;
import _2._millionaire.group.exception.GroupCustomException;
import _2._millionaire.group.exception.GroupErrorCode;
import _2._millionaire.groupmember.exception.GroupMemberCustomException;
import _2._millionaire.groupmember.exception.GroupMemberErrorCode;
import _2._millionaire.member.Member;
import _2._millionaire.member.MemberRepository;
import _2._millionaire.task.Task;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Group;
import org.hibernate.metamodel.model.domain.internal.MapMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberServiceImpl implements  GroupMemberSerivce{

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupJoinRepository groupJoinRepository;
    private final GroupJoinServiceImpl groupJoinService;

    public SearchGroupMemberListResponse searchAllGroupMembers(Long groupId) {
        // groupId로 그룹을 찾고 없으면 예외를 던짐

        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));

        // 그룹에 속한 멤버들을 가져옴
        List<GroupMember> groupMembers = groups.getGroupMembers();

        // 각 그룹 멤버를 SearchGroupMemberResponse 객체로 변환
        List<SearchGroupMemberResponse> searchGroupMemberResponses = groupMembers.stream()
                .map(groupMember -> SearchGroupMemberResponse.builder()
                        .memberId(groupMember.getMember().getId())
                        .name(groupMember.getMember().getName())
                        .grade(groupMember.getRole())
                        .build())  // 빌더 패턴을 이용해 객체를 생성
                .collect(Collectors.toList());  // 변환된 스트림을 리스트로 수집

        // SearchGroupMemberListResponse 객체를 생성하여 반환
        return SearchGroupMemberListResponse.builder()
                .groupMembers(searchGroupMemberResponses)
                .build();
    }

    @Transactional
    public void joinGroupMember (GroupMemberRequest groupMemberRequest, HttpSession session) {
//        Member loginMember = (Member) session.getAttribute("user");

        Member member = memberRepository.findById(groupMemberRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("멤버가 존재하지 않습니다."));
        Groups group = groupRepository.findById(groupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));
//
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }

        List<GroupJoin> groupJoins = member.getGroupJoins();
        // memberId와 member.getId()가 같은 GroupJoin 찾기
        GroupJoin groupJoin = groupJoins.stream()
                .filter(gj -> gj.getGroups().getId().equals(group.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 가입 요청이 없습니다."));

        GroupMember groupMember = GroupMember.builder()
                .member(groupJoin.getMember())
                .groups(groupJoin.getGroups())
                .role("member")
                .build();
        groupJoinRepository.delete(groupJoin);
        groupMemberRepository.save(groupMember);
    }

    @Transactional
    public void deleteGroupMember (GroupMemberRequest groupMemberRequest, HttpSession session) {
//        Member loginUser = (Member) session.getAttribute("user");

        Member member = memberRepository.findById(groupMemberRequest.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        Groups group = groupRepository.findById(groupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginUser));
//
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }

        List<GroupMember> groupMembers = member.getGroupMembers();

        GroupMember targetGroupMember = groupMembers.stream()
                .filter(groupMember -> groupMember.getGroups().getId().equals(group.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹멤버가 없습니다."));

        groupMemberRepository.delete(targetGroupMember);
    }

    @Transactional
    public void changeRoleGroupMember (RollGroupMemberRequest rollGroupMemberRequest, HttpSession session) {
//        Member loginMember = (Member) session.getAttribute("user");
        Groups group = groupRepository.findById(rollGroupMemberRequest.groupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));
        List<GroupMember> groupMembers = group.getGroupMembers();

//        boolean isAdmin = group.getGroupMembers().stream()
//                .anyMatch(groupMember -> groupMember.getRole().equals("admin") && groupMember.getMember().equals(loginMember));
//        if (!isAdmin) {
//            throw new IllegalStateException("권한이 없습니다. 관리자만 그룹에 멤버를 추가할 수 있습니다.");
//        }

        GroupMember targetGroupMember = groupMembers.stream()
                .filter(groupMember -> groupMember.getMember().getId().equals(rollGroupMemberRequest.memberId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버는 그룹에 속해 있지 않습니다."));

        if (Objects.equals(rollGroupMemberRequest.role(), "up"))
            targetGroupMember.setRole("admin");
        else
            targetGroupMember.setRole("member");
    }

    public GroupMemberPenaltyListResponse calculatePenaltyByGroupAndDate(Long groupId,
                                                                         Integer year,
                                                                         Integer month,
                                                                         HttpSession session) {

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));

        List<GroupMemberPenaltyResponse> groupMemberPenaltyResponses = new ArrayList<>();
        YearMonth targetDate = YearMonth.of(year, month);

        for (GroupMember groupmember : group.getGroupMembers()) {
            Long penalty = 0L;

            // task 타입별로 이미 카운트했는지 여부를 저장할 플래그
            boolean monthlyCounted = false;
            Set<LocalDate> countedWeeks = new HashSet<>();
            Set<LocalDate> countedDays = new HashSet<>();

            for (Task task : groupmember.getTasks()) {
                YearMonth taskYearMonth = YearMonth.from(task.getDueDate());

                if (task.getStatus().equals("deny") && taskYearMonth.equals(targetDate)) {
                    // Monthly 타입: 한 달에 한 번만 패널티 적용
                    if (task.getType().equals("monthly") && !monthlyCounted) {
                        penalty += group.getMonthPenalty();
                        monthlyCounted = true; // 패널티 한 번 계산 후 플래그를 true로 설정
                    }
                    // Weekly 타입: 동일한 주에 한 번만 패널티 적용
                    if (task.getType().equals("weekly")) {
                        LocalDate taskWeek = task.getDueDate().with(DayOfWeek.SUNDAY); // 해당 주의 시작일(일요일) 기준
                        if (!countedWeeks.contains(taskWeek)) {
                            penalty += group.getWeeklyPenalty();
                            countedWeeks.add(taskWeek); // 해당 주가 이미 카운트되었는지 기록
                        }
                    }
                    // Daily 타입: 동일한 날짜에 한 번만 패널티 적용
                    if (task.getType().equals("daily")) {
                        LocalDate taskDay = task.getDueDate();
                        if (!countedDays.contains(taskDay)) {
                            penalty += group.getDailyPenalty();
                            countedDays.add(taskDay); // 해당 날짜가 이미 카운트되었는지 기록
                        }
                    }
                }
            }
            groupMemberPenaltyResponses.add(
                    GroupMemberPenaltyResponse.builder()
                            .memberId(groupmember.getMember().getId())
                            .memberName(groupmember.getMember().getName())
                            .penalty(penalty)
                            .build()
            );
        }

        return GroupMemberPenaltyListResponse.builder()
                .members(groupMemberPenaltyResponses)
                .build();
    }

    public boolean checkGroupMember(Long groupId, HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        Groups groups = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupCustomException(GroupErrorCode.GROUP_NOT_FOUND));

        boolean isMember = groups.getGroupMembers().stream()
                .anyMatch(member -> member.getId().equals(user.getId()));

        if (isMember) {
            return true;
        } else {
            List<Member> membersWithJoinHistory = groupJoinRepository.findMembersByGroupId(groupId);
            boolean hasJoinHistory = membersWithJoinHistory.stream()
                    .anyMatch(member -> member.getId().equals(user.getId()));

            if (hasJoinHistory) {
                throw new GroupCustomException(GroupErrorCode.ALREADY_JOIN_REQUEST_MEMBER);
            }

            throw new GroupMemberCustomException(GroupMemberErrorCode.MEMBER_NOT_IN_GROUP);
        }
    }

}
