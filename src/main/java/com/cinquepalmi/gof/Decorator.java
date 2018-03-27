package com.cinquepalmi.gof;

import java.util.function.Function;

public class Decorator {

//	Function<> f = new Function<T, R>() {
//
//		@Override
//		public R apply(T t) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	};
	
	public static interface DecoratorInterface{
		public StringBuilder builder = new StringBuilder();
		void doSomething();
		
		default void print() {
			System.out.println(builder.toString());
		}
	}
	
	public static class DecoratorBase implements DecoratorInterface{
			
		@Override
		public void doSomething() {
			builder.append(DecoratorBase.class);
		}
	}
	
	public static abstract class DecoratorClassAbstract implements DecoratorInterface{
		public abstract String doSub();
		public DecoratorInterface inner;
		
		public DecoratorClassAbstract(DecoratorInterface inner) {
			this.inner = inner;
		}
		
		@Override
		public void doSomething() {
			inner.doSomething();
			builder.append("-").append(doSub());
		}
	}
	
	public static class DecoratorClass extends DecoratorClassAbstract{

		public DecoratorClass(DecoratorInterface inner) {
			super(inner);
		}

		@Override
		public String doSub() {
			return DecoratorClass.class + ".doSub";
		}
	}
	
	public static void main(String[] args) {
		DecoratorClass DecoratorClass = new DecoratorClass(
				new DecoratorBase()
				);
		DecoratorClass.doSomething();
		DecoratorClass.print();
		
		
		
	}
	
}
