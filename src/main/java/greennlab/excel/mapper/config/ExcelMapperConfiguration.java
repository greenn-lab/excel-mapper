package greennlab.excel.mapper.config;

import greennlab.excel.mapper.ExcelMapper;
import greennlab.excel.mapper.annotation.ExcelSheet;
import greennlab.excel.mapper.translator.ValueTranslator;
import greennlab.excel.mapper.translator.ValueTranslatorMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class ExcelMapperConfiguration implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

  private ApplicationContext context;
  private Map<Class<?>, ValueTranslator<?>> valueTranslatorMap;

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
    initializeValueTranslatorMatcher();
    registryAllExcelMapperInClasspath(registry);
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
  }

  @Override
  public void setApplicationContext(final ApplicationContext context) {
    this.context = context;
  }


  private void registryAllExcelMapperInClasspath(BeanDefinitionRegistry registry) {
    scanWithFilterInRootClasspath(
        new AnnotationTypeFilter(ExcelSheet.class),
        mapper -> {
          try {
            Class<?> type = Class.forName(mapper.getBeanClassName());
            RootBeanDefinition bean =
                new RootBeanDefinition(ExcelMapper.class, () -> {
                  ValueTranslatorMatcher matcher = new ValueTranslatorMatcher(type, valueTranslatorMap);
                  return new ExcelMapper<>(type, matcher.getColumns());
                });
            bean.setTargetType(ResolvableType.forClassWithGenerics(ExcelMapper.class, type));

            registry.registerBeanDefinition(
                type.getSimpleName() + "ExcelMapper",
                bean
            );
          }
          catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
          }
        }
    );
  }

  private void initializeValueTranslatorMatcher() {
    if (valueTranslatorMap == null) {
      valueTranslatorMap = new HashMap<>();

      scanWithFilterInRootClasspath(
          new AssignableTypeFilter(ValueTranslator.class),
          valueTranslator -> {
            try {
              final Class<?> type = Class.forName(valueTranslator.getBeanClassName());
              final Type genericInterface = type.getGenericInterfaces()[0];
              final ParameterizedType genericType = (ParameterizedType) genericInterface;
              final Type valueType = genericType.getActualTypeArguments()[0];
              final Class<?> valueClass = (Class<?>) valueType;

              final ValueTranslator<?> newTranslator = (ValueTranslator<?>) type.getDeclaredConstructor().newInstance();

              valueTranslatorMap.put(valueClass, newTranslator);
              resolvePrimitiveTypes(valueTranslatorMap, valueClass, newTranslator);
            }
            catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
              logger.error(e.getMessage(), e);
            }
          }
      );
    }
  }

  private void scanWithFilterInRootClasspath(TypeFilter filter, Consumer<? super BeanDefinition> consumer) {
    ClassPathScanningCandidateComponentProvider provider =
        new ClassPathScanningCandidateComponentProvider(false);

    provider.addIncludeFilter(filter);

    AutoConfigurationPackages.get(context)
        .stream()
        .map(provider::findCandidateComponents)
        .forEach(beanDefinitions -> beanDefinitions.forEach(consumer));

  }


  private void resolvePrimitiveTypes(Map<Class<?>, ValueTranslator<?>> valueTranslatorMap, Class<?> valueType, ValueTranslator<?> newTranslator) {
    if (Integer.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(int.class, newTranslator);
    }
    else if (Long.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(long.class, newTranslator);
    }
    else if (Double.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(double.class, newTranslator);
    }
    else if (Short.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(short.class, newTranslator);
    }
    else if (Float.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(float.class, newTranslator);
    }
    else if (Byte.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(byte.class, newTranslator);
    }
    else if (Boolean.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(boolean.class, newTranslator);
    }
    else if (Character.class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(char.class, newTranslator);
    }
    else if (Integer[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(int[].class, newTranslator);
    }
    else if (Long[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(long[].class, newTranslator);
    }
    else if (Double[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(double[].class, newTranslator);
    }
    else if (Short[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(short[].class, newTranslator);
    }
    else if (Float[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(float[].class, newTranslator);
    }
    else if (Byte[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(byte[].class, newTranslator);
    }
    else if (Boolean[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(boolean[].class, newTranslator);
    }
    else if (Character[].class.isAssignableFrom(valueType)) {
      valueTranslatorMap.put(char[].class, newTranslator);
    }
  }

}
