package com.mobiquity.movieReviewApp.security.controllers.content;

import com.mobiquity.movieReviewApp.domain.content.controller.ContentController;
import com.mobiquity.movieReviewApp.domain.content.controller.WatchlistController;
import com.mobiquity.movieReviewApp.domain.content.service.MovieService;
import com.mobiquity.movieReviewApp.domain.content.service.SeriesService;
import com.mobiquity.movieReviewApp.domain.content.service.WatchlistService;
import com.mobiquity.movieReviewApp.security.controllers.ControllerSecurityTest;
import com.mobiquity.movieReviewApp.security.controllers.PrivateEndpointRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.mobiquity.movieReviewApp.domain.content.controller")
@WebMvcTest(controllers = {
        ContentController.class,
        WatchlistController.class
})
@MockBeans(value = {
        @MockBean(MovieService.class),
        @MockBean(SeriesService.class),
        @MockBean(WatchlistService.class),
})
class ContentEndpointSecurityTests extends ControllerSecurityTest
        implements PrivateEndpointRule {

    @Override
    @ParameterizedTest
    @ValueSource(strings = {
            "/content/addMovie",
            "/content/movie",
            "/content/addSeries",
            "/content/series",
            "/watchlist/addMovie",
            "/watchlist/removeMovie",
            "/watchlist/addSeries",
            "/watchlist/removeSeries"
    })
    public void privateEndpoint_unauthorizedUser_isUnauthorizedResponse(String url) throws Exception {
        assertFalse(super.isAuthenticated(url));
    }

    @Override
    @WithMockUser(roles = "USER")
    @ParameterizedTest
    @ValueSource(strings = {
            "/content/addMovie",
            "/content/movie",
            "/content/addSeries",
            "/content/series",
            "/watchlist/addMovie",
            "/watchlist/removeMovie",
            "/watchlist/addSeries",
            "/watchlist/removeSeries"
    })
    public void privateEndpoint_authorizedUser_hasAccess(String url) throws Exception {
        assertTrue(super.isAuthenticated(url));
    }

}
