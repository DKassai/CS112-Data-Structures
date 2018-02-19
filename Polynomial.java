package poly;

import java.io.IOException;
import java.util.Scanner;



/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */

	private static Node remove(Node poly) {
		Node end = null;
		Node ptr = poly;
		Node ptr1 = null;
		while (ptr != null) {
			if (!(ptr.term.coeff == 0)) {
				Node newNode = new Node(ptr.term.coeff,ptr.term.degree,end);
				end = newNode;	
			}
			ptr = ptr.next;
		}
		return sort(end);
	}
	
	
	
	private static Node condense(Node poly1) {
		Node ptr= sort(poly1);
		Node end = null;
		Node ptr2 = null; 
		while (ptr !=null) {
			if (end==null) {
				float x =  ptr.term.coeff ;
				int y = ptr.term.degree;
				Node newNode = new Node(x,y,null);
				end=newNode;
			}else {
				
				ptr2=end;
				while (ptr2 != null) {
					if (ptr.term.degree==ptr2.term.degree) {
						ptr2.term.coeff=ptr2.term.coeff + ptr.term.coeff;
						break;
					}
					
					ptr2=ptr2.next;
				}
				if (ptr2 == null) {
					end = new Node(ptr.term.coeff,ptr.term.degree,end);
				}
			}
			ptr=ptr.next;
		}
	
		end=sort(end);
		if (end.term.coeff==0) {
			end=end.next;
		}
		return sort(remove(end));
	}
//	
		// while loop for when there multiple ones with the same degree 
			//test case for only two 
			// test case for mulitple
			// test case for when you need to move the pointers. 
	
		//when they are not equal 
		// when ptr2 is null 
		//when they are not equal you add the higher one to the end
		// when 

	private static Node sort( Node poly1) {
		for (Node ptr =  poly1; ptr !=null; ptr= ptr.next ) {
			for (Node ptr2 =  ptr.next; ptr2 !=null; ptr2= ptr2.next ) {
				if (ptr.term.degree >=ptr2.term.degree) {
					float sawpcoff = ptr.term.coeff;
					int swapdegree = ptr.term.degree;
					ptr.term.coeff = ptr2.term.coeff;
					ptr.term.degree = ptr2.term.degree;
					ptr2.term.coeff = sawpcoff;
					ptr2.term.degree = swapdegree;
				}
			}
		}
		return poly1;
	}
	
	

	
//	private static Node same(Node poly1, Node poly2) {
//		Node same= null;
//		Node Firstpointer = condense(poly1);
//		Node Secondpointer = condense(poly2);
//		float x= 0;
//		int y = 0;
//		
//		while(Firstpointer!=null && Secondpointer!=null && Firstpointer==Secondpointer) {
//			 x = Firstpointer.term.coeff+Secondpointer.term.coeff;
//			 y = Firstpointer.term.degree+ Secondpointer.term.degree;
//		
//			Node newNode = new Node(x,y,null);
//			same = newNode;
//			Firstpointer=Firstpointer.next;
//			Secondpointer= Secondpointer.next;
//			
//		}
//	return condense(same);
	
	
	
	public static Node add(Node poly1, Node poly2) {
		if (poly1 == null) {
			return sort(poly2);
		}
		// when two is empty test case 2 
		if (poly2 == null) {
			return sort(poly1);
		}
//		if (poly1==poly2) {
//			return same(poly1,poly2);
//			}
		
		
		
		Node firstPointer = poly1;
		Node secondPointer = poly2;
		Node finalPoly = null;
		Node ptr= null;
		
		
		//when one is empty test case 1

//		if (poly1==null && poly2==null) {
//			return null;
//		}
//		
		// when there are more than one degree in each of the polynomial 
		
		
		/** COMPLETE THIS METhOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// if there is a null that you want to add to something or there is a constant
		// equal greater than less empty 
		
		
		while (firstPointer!=null && secondPointer!=null) {
			// when the degree is the same.
			if (firstPointer.term.degree==secondPointer.term.degree) {
				float x =  firstPointer.term.coeff + secondPointer.term.coeff;
				int y = firstPointer.term.degree;
				Node newNode = new Node(x,y,null);
				if (finalPoly==null) {
					finalPoly=newNode;
				}else {
					 ptr=finalPoly;
					while (ptr.next!=null) {
						ptr=ptr.next;
					}
					if (ptr.next==null) {
						ptr.next=newNode;
					}
					
				}
				firstPointer=firstPointer.next;
				secondPointer=secondPointer.next;
			}
			else if (firstPointer.term.degree>secondPointer.term.degree) {
				float x =  firstPointer.term.coeff;
				int y = firstPointer.term.degree;
				Node newNode = new Node(x,y,null);
				if (finalPoly==null) {
					finalPoly=newNode;
				}else {
					 ptr=finalPoly;
					while (ptr.next!=null) {
						ptr=ptr.next;
					}
					if (ptr.next==null) {
						ptr.next=newNode;
					}
					
				}
				firstPointer=firstPointer.next;
			}
			else if (firstPointer.term.degree<secondPointer.term.degree) {
				float x =  secondPointer.term.coeff;
				int y = secondPointer.term.degree;
				Node newNode = new Node(x,y,null);
				if (finalPoly==null) {
					finalPoly=newNode;
				}else {
					 ptr=finalPoly;
					while (ptr.next!=null) {
						ptr=ptr.next;
					}
					if (ptr.next==null) {
						ptr.next=newNode;
					}
					
				}
				secondPointer=secondPointer.next;
			}
			
		}
//		
		while (firstPointer!=null && secondPointer==null) {
			float x =  firstPointer.term.coeff;
			int y = firstPointer.term.degree;
			Node newNode = new Node(x,y,null);
			 ptr=finalPoly;
			while (ptr.next!=null) {
				ptr=ptr.next;
			}
			ptr.next=newNode;
			firstPointer = firstPointer.next;
		}
		while (secondPointer!=null && firstPointer==null) {
			float x =  secondPointer.term.coeff;
			int y = secondPointer.term.degree;
			Node newNode = new Node(x,y,null);
			while (ptr.next!=null) {
				ptr=ptr.next;
			}
			ptr.next=newNode;
			secondPointer = secondPointer.next;
		}
		 return condense(finalPoly);
		
		
		
			
	}

	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		
		
		
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// when you mulitply a polynomial by an empty polynomial it should be null
		//when one is empty test case 1
		if (poly1 == null || poly2==null) {
			return null;
		}
		// when two is empty test case 2 
//		if (poly2 == null) {
//			return condense(poly1);
//		}
//		
		
		Node firstPointer = sort(poly1);
		Node secondPointer = sort(poly2);
		Node finalPoly = null;
		/** COMPLETE THIS METhOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// if there is a null that you want to add to something or there is a constant
		// equal greater than less empty 
		
		for (Node ptr =  poly1; ptr !=null; ptr= ptr.next ) {
			for (Node ptr2 =  poly2; ptr2 !=null; ptr2= ptr2.next ) {
				float z = ptr2.term.coeff*ptr.term.coeff;
				int t = ptr2.term.degree+ptr.term.degree;
				Node newNode4= new Node ( z,t, finalPoly);
				finalPoly = newNode4;	 
			}
		}
		if (finalPoly.next == null) {
			return sort(finalPoly);
		}
	
		
		
		
		return condense(finalPoly);

		
	
		
	//	return sort(finalPoly);		
		
	}
	
//	}
//		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		if (poly == null) {
			return 0;
		}
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		// for every coeff+(degree*x) +=sum
		
		Node firstPointer = condense(poly);
		float sum = 0;
		while(firstPointer != null) {
				sum+= firstPointer.term.coeff * (Math.pow(x, firstPointer.term.degree));
			firstPointer = firstPointer.next;
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
