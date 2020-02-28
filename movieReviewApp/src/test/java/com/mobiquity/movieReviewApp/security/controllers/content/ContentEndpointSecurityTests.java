package com.mobiquity.movieReviewApp.security.controllers.content;

import com.mobiquity.movieReviewApp.domain.content.controller.ContentController;
import com.mobiquity.movieReviewApp.domain.content.controller.WatchlistController;
import com.mobiquity.movieReviewApp.security.controllers.ControllerSecurityTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.mobiquity.movieReviewApp.domain.content.controller")
@WebMvcTest(controllers = {
        ContentController.class,
        WatchlistController.class
})
public class ContentEndpointSecurityTests extends ControllerSecurityTest {

}
