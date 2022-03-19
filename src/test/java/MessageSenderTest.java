import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {

    @Test
    public void messageSenderTestRush() {
        LocalizationService localizationServiceImpl = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceImpl.locale(Country.RUSSIA))
               .thenReturn("Добро пожаловать");

        GeoService geoServiceImpl = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceImpl.byIp(Mockito.contains("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceImpl, localizationServiceImpl);

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Assertions.assertEquals(messageSender.send(headers), messageSenderExp.send(headers));
  }

    @Test
    public void messageSenderTestEng() {
        LocalizationService localizationServiceImpl = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceImpl.locale(Country.USA))
                .thenReturn("Welcome");

        GeoService geoServiceImpl = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceImpl.byIp(Mockito.contains("96.")))
                .thenReturn(new Location("New York", Country.USA, null,  0));

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.100.167");

        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceImpl, localizationServiceImpl);

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Assertions.assertEquals(messageSender.send(headers), messageSenderExp.send(headers));
    }

    @Test
    public void byIpTest() {
        GeoService geoServiceImpl = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceImpl.byIp(Mockito.contains("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoServiceImpl.byIp(Mockito.contains("96.")))
                .thenReturn(new Location("New York", Country.USA, null,  0));
        Location locationRusImpl = geoServiceImpl.byIp("172.103.772.09");
        Location locationEngImpl = geoServiceImpl.byIp("96.99.082.66");

        GeoService geoService = new GeoServiceImpl();
        Location locationRus = geoService.byIp("172.103.772.09");
        Location locationEng = geoService.byIp("96.99.082.66");

        Assertions.assertEquals(locationRus.getCountry(), locationRusImpl.getCountry());
        Assertions.assertEquals(locationEng.getCountry(), locationEngImpl.getCountry());
    }

    @Test
    public void localeTest() {
        LocalizationService localizationServiceImpl = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceImpl.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationServiceImpl.locale(Country.USA))
                .thenReturn("Welcome");

        LocalizationService localizationService = new LocalizationServiceImpl();
        Assertions.assertEquals(localizationService.locale(Country.RUSSIA), localizationServiceImpl.locale((Country.RUSSIA)));
        Assertions.assertEquals(localizationService.locale(Country.USA), localizationServiceImpl.locale((Country.USA)));

    }
}
