package com.inai.todolist.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PriorityEnum {

    VERY_HIGH("VeryHigh", 5),
    HIGH("High", 4),
    MEDIUM("Medium", 3),
    LOW("Low", 2),
    NO_PRIORITY("No-Priority",1);

    private final String priority;
    private final int priorityId;

    PriorityEnum(String priority, int priorityId){
        this.priorityId = priorityId;
        this.priority = priority;
    }

    public static PriorityEnum fromRoleName(String roleName) {
        return Arrays.stream(PriorityEnum.values()).filter(role -> role.getPriority().equals(roleName))
                .findFirst().orElse(null);
    }




}

