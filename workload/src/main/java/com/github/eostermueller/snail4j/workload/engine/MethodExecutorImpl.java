package com.github.eostermueller.snail4j.workload.engine;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.eostermueller.snail4j.workload.model.MethodWrapper;

public class MethodExecutorImpl implements MethodExecutor {
//	private @Autowired AutowireCapableBeanFactory beanFactory; 
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	private MethodWrapper methodWrapper;
	private Object instance;
	private Method myMethod;
	
	
	public Method getMyMethod() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		
		if (this.myMethod==null) {
			Class noparams[] = {};
			Class cls = getMyClass();
			
			LOGGER.debug("about to execute II{}",cls.getName());
			
			this.myMethod = cls.getDeclaredMethod( this.getMethodWrapper().getMethodName(), noparams);
			
		}
		return myMethod;
	}

	@Override
	public MethodWrapper getMethodWrapper() {
		return methodWrapper;
	}

	@Override
	public void execute() throws WorkloadInvocationException {
			try {
				if (this.getInstance()==null)
					instantiate();
				invoke();
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
				throw new WorkloadInvocationException(this.getMethodWrapper(), e);
			}
	}

	Class getInstanceClass() {
		Class c = this.getInstance().getClass();
		return c;
	}
	private Class getMyClass() throws ClassNotFoundException {
		Class cls = null;
		if (this.getInstance()!=null) {
			cls = this.getInstanceClass();
		} else {
			cls = Class.forName( this.getMethodWrapper().getDeclaringClassName() );
			LOGGER.debug("just instantiated [" + cls.getName() + "]");
		}
		return cls;
	}
	private void invoke() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		
		Method m = this.getMyMethod();
		m.invoke( this.getInstance() );
	}

	private void instantiate() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class cls = this.getMyClass();
		LOGGER.debug("instantiate{}",cls.getName());
		
		if (this.getInstance() == null) {
			Object object = cls.newInstance();
			LOGGER.debug("instantiate() newInstance() return object:{}", object );
			
			
//			ApplicationContext ctx = SpringContextHolder.getContext();
//			LOGGER.debug("instantiate() applicationContext:{}", ctx);
//			AutowireCapableBeanFactory factory = ctx.getAutowireCapableBeanFactory();
//			LOGGER.debug("instantiate() return factory from this.applicationContext.getAutowireCapableBeanFactory():{}", factory );
//			factory.autowireBean(object);
			
			//beanFactory.autowireBean(object); // obj will now have its dependencies autowired.			
			this.setInstance( object );
			LOGGER.debug("instantiate() object is null:{}", object==null );
		}
		LOGGER.debug("instantiate() exit", true );

	}

	@Override
	public void setMethodWrapper(MethodWrapper methodWrapper) {
		this.methodWrapper = methodWrapper;
	}

	public MethodExecutorImpl(MethodWrapper val) {
		this.methodWrapper = val;
	}

	
	@Override
	public void setInstance(Object val) {
		this.instance = val;
	}
	@Override
	public Object getInstance() {
		return this.instance;
	}


}
