package Umc.replendar.user.entity;

import lombok.Getter;

@Getter
public enum AcademicYear {
    YEAR_1(1),
    YEAR_2(2),
    YEAR_3(3),
    YEAR_4(4),
    YEAR_5(5);

    private final int value;

    AcademicYear(int value) {
        this.value = value;
    }

    public static AcademicYear fromValue(int value) {
        for (AcademicYear year : AcademicYear.values()) {
            if (year.value == value) {
                return year;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

