package org.winners.app.presentation.user.response;

public record SignUpClientUserResponseDTO(boolean successSignUp,
                                          boolean duplicatedPhoneNumber,
                                          boolean duplicatedCi) {
}
