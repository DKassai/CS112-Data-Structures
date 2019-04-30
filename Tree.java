package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	
	
	
	
	
	private String lobrack(String expr) {
		return expr.substring(1,expr.length()-1);
	}
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	
	
	
	public void build() {
	startitup();
	}
	private void startitup() {
		Stack<TagNode> build = new Stack<TagNode>();	
		String words = null;
		boolean isthere= false;
			//if it is empty then return 
			if (!sc.hasNextLine()) {
					return;
				}
				
			// this is the base of the html and the lobrack makes sure there are no brackets where it starts 
			root= new TagNode(lobrack(sc.nextLine()),null,null);
			
			build.push(root);
			// if there is something on the line then we go into it 
				while (sc.hasNextLine()) {
				// sets the string to what is on the line and sees if there is a tag there
					 words = sc.nextLine();
					 isthere = false;
					// this checks if there is a tag
					if (words.charAt(0)=='<') {
						isthere= true;
						// makes sure there is no brackets and if there is then it gets rid of them
						words = (lobrack(words));
					}
					// when it finds the forward slash it removes what was in teh stack 
					if ((isthere) && words.charAt(0)=='/') 
						build.pop();
					else {
					TagNode ptr= new TagNode (words,null,null);
						// it checks the child of the first one and if it is empty then you set it to the temp
						if (build.peek().firstChild==null) {
							build.peek().firstChild=ptr;
						}else {
							TagNode babynode = build.peek().firstChild;
							
							// while the child node doesnt equal null then you set it to the sibling node.
							// and you set the sibling to the ptr that was created earlier
							// this is the while loop that allows it to go thrhough the whole html
							//for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
							while (babynode.sibling!=null) 
								babynode=babynode.sibling;
								babynode.sibling=ptr;
							}
						
						if (isthere) {
							build.push(ptr);
						}
					}
					
				}
			}
		
	
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	
	
	public void replaceTag(String oldTag, String newTag) {
		
		getitout(root,oldTag,newTag);
	//	System.out.println(getHTML());
	}
	
	private void getitout(TagNode mom, String ot, String nt) {
		if (mom==null) {
			return;
		}
		else if (mom!=null) { 
			if (mom.tag.equals(ot)){ 
			mom.tag=nt;
			}
		}
		getitout(mom.sibling,ot,nt);
		getitout(mom.firstChild,ot,nt);
		}
	
		
	
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		boldrownull(root,row);
	//	System.out.println(getHTML());
	}
	
	// one method finds the table and one method changes something within the table 	
	private void boldrownull(TagNode lroot,int row) {
		if (lroot==null) {
			return;
		}
		if (lroot.tag.equals("table")) {
			bigbadbold(row,lroot);
		}
		boldrownull( lroot.firstChild,row);
		boldrownull(lroot.sibling,row);
	}
	
	
	private void bigbadbold(int line, TagNode lroot ) {
		line = line - 1; // this is because of ......
		
		TagNode rownum = lroot.firstChild;
		for (int count = 0; count < line; count++) {
			if (rownum.sibling!=null) {
				rownum=rownum.sibling;
			}else  {
				return;
			}
		}
			TagNode column =rownum.firstChild;
			while (column!=null) {
				
				TagNode bold = new TagNode ("b", column.firstChild,null);
				column.firstChild=bold;
				column=column.sibling;
		}
	}
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */

	public void removeTag(String tag) {
		if (tag == null) {
			return;
		}
		
			
			if (tag.equals("p") || tag.equals("em") || tag.equals("b")) {
			// case 1
			
				case1(root,root,tag);
			
			
			}else if (tag.equals("ul") || tag.equals("ol") ) {
				
					method2(tag,root);
					
					case2(root,root,tag);
//					
				
				
			}

	}

private void method2(String tag, TagNode mom) {
	if (mom==null) {
		return;
	}
	
	if (mom.firstChild!=null && mom.firstChild.tag.equals(tag) ) {

		if  (tag.equals("ol") || tag.equals("ul")) {
			TagNode extra= null;
			if (mom.firstChild.firstChild.tag.equals("li")) {
				extra = mom.firstChild.firstChild;
			}else {
				extra = mom.sibling.firstChild;
			}
			while (extra!=null) {
				if (extra.tag.equals("li")) {
					extra.tag="p";
				}
				extra=extra.sibling;
			}
			TagNode dad = mom.firstChild.firstChild;
			if (dad!=null) {
				while(dad.sibling!=null) {
					dad=dad.sibling;
				}
				dad.sibling=mom.firstChild.sibling;
			}
			mom.firstChild=mom.firstChild.firstChild;

		}
	}
	if (mom.sibling!=null && mom.sibling.tag.equals(tag)) {
		if  (tag.equals("ol") || tag.equals("ul")) {
			TagNode father= null;
			if (mom.sibling.firstChild.tag.equals("li")){
				father=mom.sibling.firstChild;
			}else {
				father=mom.sibling.sibling;
			}
			while (father!=null) {
				if (father.tag.equals("li")) {
					father.tag="p";
				}
				father=father.sibling;
			}
			TagNode mother = mom.sibling.firstChild;
			if (mother!=null) {
				while(mother.sibling!=null) {
					mother=mother.sibling;
				}
				mom.sibling=mom.sibling.firstChild;
				if (mom.sibling==null) {
					mom=mom.firstChild.sibling;
					
				}
			}
	}
	}
	method2(tag,mom.firstChild);
	method2(tag,mom.sibling);
}
	
	private void case1(TagNode past,TagNode current,String tag) {
		if (current == null) {
			return;
		}
		
		if(current.tag.equals(tag)) {
			// check if its a first child or sibling
			//current=past.firstChild;
			
			if(past.firstChild == current) {
				// you know to change the first child
				// 
				past.firstChild = current.firstChild;
				//past.firstChild.sibling=current.sibling.sibling;
				
				if (current.sibling != null || current.firstChild != null) {
					changeSibling(past,current.firstChild,current.sibling);
					current = root.firstChild;
					//case1(root,root,tag);
				}
				
			} else {
				// if sibling to parent
//				System.out.println(past.toString());
//				System.out.println(current.toString());
//				System.out.println(current.sibling.toString());
//				
				TagNode tempSibling = current.sibling;
				//System.out.println(tempSibling);
				past.sibling = current.firstChild;
				
				
				TagNode temp = past.sibling ;
				if (temp == null) {
					return;
				}
				
				
				while(temp.sibling != null) {
					temp = temp.sibling;
				}
				temp.sibling = tempSibling;
				current = root;
				//past.firstChild.sibling=current.sibling.sibling;
				//case1(root,root,tag);
				
				
				
				
				
				
				
			}
		}
		
		// firstchild is right 
		// sibling is down	
		case1(current,current.firstChild,tag);
		case1(current,current.sibling,tag);
	}
	
	private void case2(TagNode past,TagNode current,String tag) {
		if (current == null) {
			return;
		}
		//System.out.println("hithere");
		TagNode mom = root.firstChild.firstChild;
		while (mom!=null) {
			if (mom.tag.equals("li")) {
				mom.tag="p";
			}
			mom=mom.sibling;
		}
		if(current.tag.equals(tag)) {
			// check if its a first child or sibling
			//current=past.firstChild;
			
			if(past.firstChild == current) {
				// you know to change the first child
				// 
				past.firstChild = current.firstChild;
				//past.firstChild.sibling=current.sibling.sibling;
				
				if (current.sibling != null || current.firstChild != null) {
					changeSibling(past,current.firstChild,current.sibling);
					current = root;
					//case1(root,root,tag);
				}
				
			} else {
				// if sibling to parent

				
				TagNode tempSibling = current.sibling;
				//System.out.println(tempSibling);
				past.sibling = current.firstChild;
				
				
				TagNode temp = past.sibling ;
				if (temp == null) {
					return;
				}
				
				while(temp.sibling != null) {
					temp = temp.sibling;
				}
				temp.sibling = tempSibling;
				current = root;
				//past.firstChild.sibling=current.sibling.sibling;
				//case1(root,root,tag);
			
			}
		}
		
		// firstchild is right 
		// sibling is down	
		case2(current,current.firstChild,tag);
		case2(current,current.sibling,tag);
	}
	

	private void changeSibling(TagNode past,TagNode current,TagNode sibling) {
		if(sibling == null || current == null) {
			return;
		}
		TagNode tempSibling = current.sibling;
		current.sibling = sibling;
		
		if (current.firstChild != null) {
			sibling = tempSibling;
		} else {
			sibling = null;
		}
		
		changeSibling(current,current.firstChild,sibling);
	}

	public void addTag(String word, String tag) {
		if (tag.equals("em") || tag.equals("b")) {
			add(word,root,tag);
		}
			
		}
	private TagNode morestuff(String word, TagNode mom, String tag) {
		if (mom.sibling!=null) {
			mom.sibling=this.morestuff(word,mom.sibling,tag);
		}
		if (mom.firstChild!=null) {
			mom.firstChild=this.morestuff(word, mom.firstChild, tag);
		}
		//if (mom.tag.substring(0, mom.firstChild.tag.length()-1).equals(tag)) {
			if (mom.tag.equals(word)) {
				
				TagNode thingy = new TagNode(tag, mom, mom.sibling);
				mom.sibling = null;
				return thingy;		
		//	}
		}
		
		return mom;
		
	}
	private boolean punt (char j) {
		if ((j == '!') || (j == '?') || (j == '.') || (j == ',') || (j == ';')|| (j == ':')){
			return true;
		}else {
			return false;
		}
	}
	
private TagNode seperate( TagNode mom) {
	String dad = mom.tag;
	TagNode damn = null;
	int fuckthisshit=0;
	TagNode putnext = null;
	TagNode done = null;
	for (int i = 0; i<=dad.length();i++) {
		if (i ==dad.length() || Character.isSpace(dad.charAt(i))) {
			if (damn==null) {
				damn = new TagNode (dad.substring(fuckthisshit, i),null,null);
				fuckthisshit=i+1;
					putnext=damn;
				
			}else {
				//set the node to what the word at that place is	
				putnext.sibling= new TagNode(dad.substring(fuckthisshit,i),null,null);
				fuckthisshit=i+1;
					putnext=putnext.sibling;
			}
			
		}
		
	}
	return damn;
}
		private void add (String hi,TagNode lroot, String tag ) {
			if (lroot==null  ) {
				return;
			}
			
			if (lroot.firstChild!=null && lroot.firstChild.firstChild == null) {
				lroot.firstChild=seperate(lroot.firstChild);
			}
			
			
			boolean addeds= true;
			boolean addedf = true;
			
			if (lroot.sibling!=null && (lroot.sibling.tag.equals(hi)|| (lroot.sibling.tag.substring(0, lroot.sibling.tag.length()-1).equals(hi) && punt(lroot.sibling.tag.charAt(lroot.sibling.tag.length()-1))))) {
				TagNode mom = new TagNode (tag,lroot.sibling,lroot.sibling.sibling);
				lroot.sibling.sibling=null;
				lroot.sibling=mom;
				addeds=false;
				
			}
			if (lroot.firstChild!=null && (lroot.firstChild.tag.equals(hi)|| lroot.firstChild.tag.substring(0, lroot.firstChild.tag.length()-1).equals(hi) && punt(lroot.firstChild.tag.charAt((lroot.firstChild.tag.length()-1))))){
				TagNode dad = new TagNode(tag,lroot.firstChild,lroot.firstChild.sibling);
				lroot.firstChild.sibling=null;
				lroot.firstChild=dad;
				addedf=false;
			}
			
			if (addeds) {
				add(hi,lroot.sibling,tag);
			} else if (lroot.sibling.sibling!=null) {
				add(hi,lroot.sibling.sibling,tag);
			}
			if (addedf) {
			add(hi,lroot.firstChild,tag);
			}
		}
//		
//private void seperate (TagNode mom) {
//	String split = mom.tag.toString();
//	for (int i =0;i<split.length() ;i++) {
//	
//			
//	}
//}
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
		
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}

