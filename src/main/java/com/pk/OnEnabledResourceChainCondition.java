//package com.pk;
//
//import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
//import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.boot.bind.PropertySourcesPropertyValues;
//import org.springframework.boot.bind.RelaxedDataBinder;
//import org.springframework.context.annotation.ConditionContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.type.AnnotatedTypeMetadata;
//import org.springframework.util.ClassUtils;
//
//class OnEnabledResourceChainCondition extends SpringBootCondition {
//
//  public static final String WEBJAR_ASSERT_LOCATOR = "org.webjars.WebJarAssetLocator";
//
//  @Override
//  public ConditionOutcome getMatchOutcome(ConditionContext context,
//      AnnotatedTypeMetadata metadata) {
//    ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
//    ResourceProperties properties = new ResourceProperties();
//    RelaxedDataBinder binder = new RelaxedDataBinder(properties, "spring.resources");
//    binder.bind(new PropertySourcesPropertyValues(environment.getPropertySources()));
//    Boolean match = properties.getChain().getEnabled();
//    if (match == null) {
//      boolean webJarsLocatorPresent =
//          ClassUtils.isPresent(WEBJAR_ASSERT_LOCATOR, getClass().getClassLoader());
//      return new ConditionOutcome(webJarsLocatorPresent, "Webjars locator (" + WEBJAR_ASSERT_LOCATOR
//          + ") is " + (webJarsLocatorPresent ? "present" : "absent"));
//    }
//    return new ConditionOutcome(match, "Resource chain is " + (match ? "enabled" : "disabled"));
//  }
//
//}
