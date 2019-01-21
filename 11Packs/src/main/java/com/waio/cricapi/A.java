package com.waio.cricapi;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class A extends B{

	public static void main(String[] args) {
		
		B b = new B();
		B c  = new A();
		b.a(5);
		c.a(6);
	}
	public void a(int a) {
		System.out.println("a -"+a);
	}public void a(String a) {
		System.out.println("ab -"+a);
	}
}
class B{
	public void a(int a) {
		System.out.println("B -"+a);
	}
	
}
