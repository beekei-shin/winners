package com.winners.appApi.presentation.user.v1;

import com.winners.appApi.presentation.AppController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.winners.core.config.version.ApiVersion;

@ApiVersion(1)
@RestController
@RequiredArgsConstructor
@Tag(name = AppController.CLIENT_USER_TAG_NAME)
@RequestMapping(path = AppController.CLIENT_USER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientUserController {
}
