import de.mcmics.sample.HelloService;
import de.mcmics.sample.NameProvider;
import de.mcmics.annotation.NameCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class HelloServiceTest {

    private final HelloService helloService = new HelloService();

    @Test
    void executeSingleTest() {
        final String hello = helloService.hello(() -> "Me");
        Assertions.assertEquals("Hello Me!", hello);
    }

    @Test
    void executeGenerated() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        final NameCreator annotation = NameProvider.class.getAnnotation(NameCreator.class);
        final int createdClasses = annotation.number();
        for (int i = 0; i < createdClasses; i++) {
            final var clazz = Class.forName("de.mcmics.sample.gen.NameProvider" + i);
            Assertions.assertNotNull(clazz);
            final NameProvider nameProvider = (NameProvider) clazz.getDeclaredConstructor().newInstance();
            final String hello = helloService.hello(nameProvider);
            Assertions.assertEquals("Hello Name " + i + "!", hello);
        }
        final String hello = helloService.hello(() -> "Me");
        Assertions.assertEquals("Hello Me!", hello);
    }
}
