package org.winners.app.presentation.user.response;

public record SignInClientUserResponseDTO(boolean successSignIn,
                                          boolean notExistUser,
                                          boolean blockUser) {
}
