package di_test;

import org.junit.jupiter.api.Test;

import di_test.basic.SimpleRepository;
import di_test.basic.SimpleService;
import di_test.independent.ComponentA;
import di_test.independent.ComponentB;
import tech.pp.core.MyContext;
import di_test.chain.ChainA;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for the custom Dependency Injection framework.
 * Tests cover:
 * - Basic dependency injection
 * - Circular dependency detection
 * - Singleton behavior
 * - Chain of dependencies
 * - Multiple independent components
 */
public class DependencyInjectionTest {

    // Test 1: Basic component registration and retrieval
    @Test
    public void testBasicComponentRegistration()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.basic");

        SimpleRepository repository = context.getBean(SimpleRepository.class);
        assertNotNull(repository, "SimpleRepository should be registered and retrievable");

        SimpleService service = context.getBean(SimpleService.class);
        assertNotNull(service, "SimpleService should be registered and retrievable");
    }

    // Test 2: Verify dependency injection works
    @Test
    public void testDependencyInjection()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException,
            NoSuchFieldException {
        MyContext context = new MyContext("di_test.basic");

        SimpleService service = context.getBean(SimpleService.class);
        assertNotNull(service, "Service should be injected");

        // Use reflection to verify the repository field is injected
        Field repositoryField = SimpleService.class.getDeclaredField("repository");
        repositoryField.setAccessible(true);
        Object injectedRepository = repositoryField.get(service);

        assertNotNull(injectedRepository, "Repository should be injected into Service");
        assertInstanceOf(SimpleRepository.class, injectedRepository, "Injected object should be SimpleRepository");

        // Test that injected dependency works
        assertEquals("test data", service.fetchData(), "Should be able to use injected repository");
    }

    // Test 3: Test circular dependency detection
    @Test
    public void testCircularDependencyDetection() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new MyContext("di_test.circular");
        });

        assertEquals("Cycle detected in dependency graph", exception.getMessage(),
                "Should detect circular dependencies and throw RuntimeException");
    }

    // Test 4: Test bean not found returns null
    @Test
    public void testBeanNotFound()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.basic");

        String nonExistentBean = context.getBean(String.class);
        assertNull(nonExistentBean, "Non-existent bean should return null");

        Integer anotherNonExistent = context.getBean(Integer.class);
        assertNull(anotherNonExistent, "Another non-existent bean should also return null");
    }

    // Test 5: Test singleton behavior - same instance returned
    @Test
    public void testSingletonBehavior()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.basic");

        SimpleService service1 = context.getBean(SimpleService.class);
        SimpleService service2 = context.getBean(SimpleService.class);

        assertSame(service1, service2, "Should return the same instance (singleton pattern)");

        SimpleRepository repo1 = context.getBean(SimpleRepository.class);
        SimpleRepository repo2 = context.getBean(SimpleRepository.class);

        assertSame(repo1, repo2, "Repository should also be singleton");
    }

    // Test 6: Test multiple independent components
    @Test
    public void testMultipleIndependentComponents()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.independent");

        ComponentA componentA = context.getBean(ComponentA.class);
        ComponentB componentB = context.getBean(ComponentB.class);

        assertNotNull(componentA, "ComponentA should be injected");
        assertNotNull(componentB, "ComponentB should be injected");
        assertNotSame(componentA, componentB, "Different component types should be different instances");

        assertEquals("ComponentA", componentA.getName(), "ComponentA should have correct name");
        assertEquals("ComponentB", componentB.getName(), "ComponentB should have correct name");
    }

    // Test 7: Test chain of dependencies (A -> B -> C)
    @Test
    public void testChainOfDependencies()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.chain");

        ChainA chainA = context.getBean(ChainA.class);
        assertNotNull(chainA, "ChainA should be injected");
        assertNotNull(chainA.getChainB(), "ChainB should be injected into ChainA");
        assertNotNull(chainA.getChainB().getChainC(), "ChainC should be injected into ChainB");
        assertEquals("ChainC", chainA.getChainB().getChainC().getValue(),
                "Should be able to access methods through chain of dependencies");
    }

    // Test 8: Test that injected dependency is the same singleton instance
    @Test
    public void testInjectedDependencyIsSingleton()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.basic");

        SimpleService service = context.getBean(SimpleService.class);
        SimpleRepository repositoryFromContext = context.getBean(SimpleRepository.class);
        SimpleRepository repositoryFromService = service.getRepository();

        assertSame(repositoryFromContext, repositoryFromService,
                "Injected repository should be the same instance as the one from context");
    }

    // Test 9: Test empty package (no components)
    @Test
    public void testEmptyPackage()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext context = new MyContext("di_test.empty");

        assertNotNull(context, "Context should be created even for empty package");

        // Try to get a bean that doesn't exist
        Object bean = context.getBean(Object.class);
        assertNull(bean, "Should return null for non-existent beans in empty context");
    }

    // Test 10: Test context with all component types
    @Test
    public void testCompleteContext()
            throws URISyntaxException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyContext basicContext = new MyContext("di_test.basic");
        MyContext independentContext = new MyContext("di_test.independent");
        MyContext chainContext = new MyContext("di_test.chain");

        // All contexts should be created successfully
        assertNotNull(basicContext, "Basic context should be created");
        assertNotNull(independentContext, "Independent context should be created");
        assertNotNull(chainContext, "Chain context should be created");

        // Each context should have its own beans
        assertNotNull(basicContext.getBean(SimpleService.class));
        assertNotNull(independentContext.getBean(ComponentA.class));
        assertNotNull(chainContext.getBean(ChainA.class));
    }
}
