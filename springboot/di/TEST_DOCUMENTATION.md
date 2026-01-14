# Dependency Injection Framework - Test Suite

## Overview

This test suite provides comprehensive coverage for the custom Dependency Injection (DI) framework implemented in this project. The DI framework supports:

- Automatic component scanning and registration
- Constructor-based bean creation  
- Field injection using `@MyInject` annotation
- Singleton pattern for all beans
- Circular dependency detection
- Type-safe bean retrieval

## Test Structure

### Test Files

1. **DependencyInjectionTest.java** - Main test suite with 10 comprehensive tests
2. **CycleDependencyInjectionTest.java** - Original test file (legacy)

### Test Components

The test suite uses dedicated test component classes organized in packages:

#### `di_test.basic` - Basic DI Tests
- `SimpleRepository` - A simple repository component
- `SimpleService` - A service that depends on SimpleRepository

#### `di_test.chain` - Dependency Chain Tests  
- `ChainC` - Leaf component
- `ChainB` - Depends on ChainC
- `ChainA` - Depends on ChainB (tests A → B → C dependency chain)

#### `di_test.circular` - Circular Dependency Tests
- `ServiceA` - Depends on ServiceB
- `ServiceB` - Depends on ServiceA (creates circular dependency)

#### `di_test.independent` - Independent Components Tests
- `ComponentA` - Standalone component
- `ComponentB` - Another standalone component

## Running the Tests

### Run All DI Tests
```bash
mvn test -Dtest=DependencyInjectionTest
```

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=DependencyInjectionTest#testBasicComponentRegistration
```

## Test Coverage

### Test 1: Basic Component Registration
**Purpose**: Verifies that components annotated with `@MyComponent` are automatically discovered and registered in the context.

```java
@Test
public void testBasicComponentRegistration()
```

**Validates**:
- Component scanning works correctly
- Beans can be retrieved from context by their class type
- Multiple components in the same package are all registered

### Test 2: Dependency Injection
**Purpose**: Verifies that dependencies marked with `@MyInject` are properly injected.

```java
@Test  
public void testDependencyInjection()
```

**Validates**:
- Field injection works correctly
- Injected dependencies are not null
- Injected dependencies are of the correct type
- Methods can be called on injected dependencies

### Test 3: Circular Dependency Detection
**Purpose**: Verifies that circular dependencies are detected and prevented.

```java
@Test
public void testCircularDependencyDetection()
```

**Validates**:
- Circular dependencies throw RuntimeException
- Exception message indicates "Cycle detected in dependency graph"
- Application fails fast with clear error message

### Test 4: Bean Not Found
**Purpose**: Verifies behavior when requesting non-existent beans.

```java
@Test
public void testBeanNotFound()
```

**Validates**:
- Requesting unregistered bean returns null
- No exceptions thrown for missing beans
- Multiple non-existent bean requests behave consistently

### Test 5: Singleton Behavior
**Purpose**: Verifies that beans follow the singleton pattern.

```java
@Test
public void testSingletonBehavior()
```

**Validates**:
- Multiple calls to `getBean()` return the same instance
- Singleton behavior applies to all component types
- Instance identity is preserved

### Test 6: Multiple Independent Components
**Purpose**: Verifies that multiple independent components can coexist.

```java
@Test
public void testMultipleIndependentComponents()
```

**Validates**:
- Multiple components in same package are all registered
- Different component types are different instances
- Each component maintains its own state and behavior

### Test 7: Chain of Dependencies
**Purpose**: Verifies that transitive dependencies work (A depends on B, B depends on C).

```java
@Test
public void testChainOfDependencies()
```

**Validates**:
- Multi-level dependency injection works
- Entire dependency chain is resolved correctly
- Methods can be called through the dependency chain

### Test 8: Injected Dependency is Singleton
**Purpose**: Verifies that injected dependencies are the same singleton instances.

```java
@Test
public void testInjectedDependencyIsSingleton()
```

**Validates**:
- Injected dependency matches bean from context
- Same instance is used everywhere
- No duplicate instances created

### Test 9: Empty Package
**Purpose**: Verifies behavior with empty packages (no components).

```java
@Test
public void testEmptyPackage()
```

**Validates**:
- Context creation doesn't fail for empty packages
- Requesting beans from empty context returns null
- No errors or exceptions thrown

### Test 10: Complete Context
**Purpose**: Integration test verifying multiple contexts work independently.

```java
@Test
public void testCompleteContext()
```

**Validates**:
- Multiple contexts can be created
- Each context has its own set of beans
- Contexts don't interfere with each other

## Framework Annotations

### @MyComponent
Marks a class as a component that should be registered in the DI container.

```java
@MyComponent
public class UserRepository {
    // ...
}
```

### @MyInject
Marks a field for dependency injection.

```java
@MyComponent
public class UserService {
    @MyInject
    private UserRepository userRepository;
}
```

## Example Usage

### Creating a Context
```java
MyContext context = new MyContext("tech.pp.app");
```

### Retrieving a Bean
```java
UserService service = context.getBean(UserService.class);
```

### Using Injected Dependencies
```java
@MyComponent
public class OrderService {
    @MyInject
    private UserService userService;
    
    @MyInject
    private OrderRepository orderRepository;
    
    public void processOrder() {
        userService.save();
        orderRepository.save();
    }
}
```

## Test Results

When all tests pass, you should see output similar to:

```
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
```

All 10 tests validate different aspects of the DI framework ensuring it works correctly for:
- ✅ Component scanning and registration
- ✅ Field injection
- ✅ Singleton pattern
- ✅ Circular dependency detection
- ✅ Transitive dependencies
- ✅ Multiple independent components
- ✅ Error handling

## Notes

- The framework uses reflection for component scanning and dependency injection
- All beans are singletons by default
- Circular dependencies are detected using graph cycle detection algorithm
- The framework scans packages recursively
- Only classes with no-arg constructors are supported
