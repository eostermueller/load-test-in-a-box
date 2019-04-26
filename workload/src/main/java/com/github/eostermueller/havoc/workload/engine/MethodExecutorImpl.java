package com.github.eostermueller.havoc.workload.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.eostermueller.havoc.workload.model.MethodWrapper;

public class MethodExecutorImpl implements MethodExecutor {
	private MethodWrapper methodWrapper;
	private Object instance;
	private Method myMethod;
	
	
	public Method getMyMethod() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		
		if (this.myMethod==null) {
			Class noparams[] = {};
			Class cls = getMyClass();
			
			System.out.println("about to execute II[" + cls.getName() + "]");
			
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
		System.out.println("about to execute [" + c.getName() + "]");
		return c;
	}
	private Class getMyClass() throws ClassNotFoundException {
		Class cls = null;
		if (this.getInstance()!=null) {
			cls = this.getInstanceClass();
		} else {
			cls = Class.forName( this.getMethodWrapper().getDeclaringClassName() );
		}
		return cls;
	}
	private void invoke() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		
		Method m = this.getMyMethod();
		m.invoke( this.getInstance() );
	}

	private void instantiate() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class cls = this.getMyClass();
		
		if (this.getInstance() == null) {
			Object object = cls.newInstance();
			this.setInstance( object );
		}
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
