package tech.arthur.agregadordeinvestimentos.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String username,
        String email,
        String password,
        Instant creationTimeStamp,
        Instant updatedTimeStamp,
        List<AccountResponseDto> accounts
) {}

