package org.tkit.quarkus.log.cdi.test;

import io.quarkus.test.QuarkusUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.tkit.quarkus.log.cdi.test.app.ErrorDataService;
import org.tkit.quarkus.log.cdi.test.app.ErrorWrapperService;

import javax.inject.Inject;

public class ErrorDataServiceTest extends AbstractTest {

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addClasses(ErrorDataService.class, ErrorWrapperService.class)
                    .addAsResource("application.properties", "application.properties"));

    @Inject
    ErrorDataService service;

    @Inject
    ErrorWrapperService wrapper;

    @Test
    public void error1Test() {
        Assertions.assertThrows(RuntimeException.class, () -> service.error1("Error"));
        assertLogs()
                .assertLines(90)
                .assertContains(0, "ERROR [org.tki.qua.log.cdi.tes.app.ErrorDataService] (main) error1(Error) throw java.lang.RuntimeException: Error");
    }

    @Test
    public void noStacktraceTest() {
        Assertions.assertThrows(RuntimeException.class, () -> service.noStacktrace());
        assertLogs().assertLines(1)
                .assertContains(0, "ERROR [org.tki.qua.log.cdi.tes.app.ErrorDataService] (main) noStacktrace() throw java.lang.RuntimeException: Error1");
    }

    @Test
    public void wrapperTest() {
        Assertions.assertThrows(RuntimeException.class, () -> wrapper.wrapperMethod("WrapperError"));
        assertLogs().assertLines(103)
                .assertContains(0, "ERROR [org.tki.qua.log.cdi.tes.app.ErrorDataService] (main) error1(WrapperError) throw java.lang.RuntimeException: WrapperError");
    }

}
