package itmo.localpiper.iblab1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._-]{1,255}$")
    private String login;

    @NotNull
    @NotBlank
    @Size(min=6, max=255)
    private String password;
}
