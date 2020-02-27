//package com.mobiquity.movieReviewApp.security.tutorial.service.user;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.Ordered;
//import org.springframework.test.context.TestContext;
//import org.springframework.test.context.TestExecutionListener;
//
//public class CustomTestExecutionListener implements TestExecutionListener, Ordered {
//    private static final Logger logger = LoggerFactory.getLogger(CustomTestExecutionListener.class);
//
//    @Override
//    public void beforeTestClass(TestContext testContext) throws Exception {
//        logger.info("beforeTestClass : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void prepareTestInstance(TestContext testContext) throws Exception {
//        logger.info("perpareTestInstance : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void beforeTestMethod(TestContext testContext) throws Exception {
//        logger.info("beforeTestMethod : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void beforeTestExecution(TestContext testContext) throws Exception {
//        logger.info("beforeTestExecution : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void afterTestExecution(TestContext testContext) throws Exception {
//        logger.info("afterTestExecution : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void afterTestMethod(TestContext testContext) throws Exception {
//        logger.info("afterTestMethod : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public void afterTestClass(TestContext testContext) throws Exception {
//        logger.info("afterTestClass : {}", testContext.getTestClass());
//    }
//
//    @Override
//    public int getOrder() {
//        return Integer.MAX_VALUE;
//    }
//}
