package org.sagebionetworks.repo;

import java.util.Map;

import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

// The hope here is that I can specify that the ActivityLogger should be spied on
// and then Autowire the TestClass and call a method on it, then verify that the 
// ActivityLogger method was called
public class MockitoSpyPostProcessor implements BeanPostProcessor, Ordered {

    private Map<String, Object> spyNames; 

    public void setSpyNames(Set<Object> spyNames) {
        this.spyNames = spyNames;
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (spyNames != null && spyNames.get(beanName)) {
            Object spy = Mockito.spy(bean);
            spyNames.put(beanName, spy);
            return spy;
        }
        return bean;
    }

    @Override
    @SuppressWarnings("unused") 
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
