package Umc.replendar.user.dto.res;

public class TestRes {
    private String message;

    public TestRes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class CreateProfileResultDTO {
    }
}
