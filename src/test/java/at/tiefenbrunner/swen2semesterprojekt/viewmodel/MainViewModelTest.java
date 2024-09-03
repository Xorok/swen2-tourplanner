package at.tiefenbrunner.swen2semesterprojekt.viewmodel;

import at.tiefenbrunner.swen2semesterprojekt.data.service.ConfigService;
import at.tiefenbrunner.swen2semesterprojekt.data.service.TourService;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Event;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Publisher;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.event.Subscriber;
import at.tiefenbrunner.swen2semesterprojekt.viewmodel.viewmodels.MainViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("FieldCanBeLocal")
class MainViewModelTest {

    private MainViewModel viewModel;
    private Publisher publisher;
    private ConfigService configService;

    @Mock
    TourService tourService;

    @BeforeEach
    public void beforeEach() {
        publisher = new Publisher();
    }

    @Test
    public void should_set_darkTheme_based_on_config() throws IOException {
        configService = new ConfigService("src/test/resources/config-dark-theme.properties");
        viewModel = new MainViewModel(publisher, configService, tourService);

        assertTrue(viewModel.darkThemeProperty().get());
    }

    @Test
    public void should_set_lightTheme_based_on_config() throws IOException {
        configService = new ConfigService("src/test/resources/config-light-theme.properties");
        viewModel = new MainViewModel(publisher, configService, tourService);

        assertFalse(viewModel.darkThemeProperty().get());
    }

    @Test
    public void should_propagate_themeChange_event() {
        configService = mock(ConfigService.class);
        viewModel = new MainViewModel(publisher, configService, tourService);

        Subscriber listener = mock(Subscriber.class);
        InOrder inOrder = inOrder(listener);

        publisher.subscribe(Event.DARK_THEME, listener);

        viewModel.darkThemeProperty().set(true);
        viewModel.darkThemeProperty().set(false);

        inOrder.verify(listener).notify("true");
        inOrder.verify(listener).notify("false");
    }
}