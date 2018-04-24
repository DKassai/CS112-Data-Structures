package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
			throws FileNotFoundException {
	
		// if the document is null 
		if (docFile.length()==0 || docFile.isEmpty()) {
			throw new FileNotFoundException("This doesn't exist");
		}
		// if it is not null get this method 
		
		return loadkeys(docFile);
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
	}
	private HashMap<String,Occurrence> loadkeys(String docFile) throws FileNotFoundException {
		HashMap<String, Occurrence> amount = new HashMap<String,Occurrence> ();
		Scanner sc  = new Scanner ( new File(docFile));
		
		while (sc.hasNext()) {
			String word = getKeyword(sc.next());
			if (word == null) {
				continue;
			}
			else if (amount.get(word)!=null) {
				
					amount.get(word).frequency++;
				} 
			else if (amount.get(word)==null) {
				Occurrence orig = new Occurrence (docFile, 1);
					
					amount.put(word,orig);
				}
			}
		
		sc.close();
		return amount;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a docaument
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		
		
			methodmerge(kws);
			
		}

	private  void methodmerge(HashMap<String,Occurrence> kws) {
		for (String keyword: kws.keySet()) {
			 if (!(keywordsIndex.containsKey(keyword))){
				ArrayList<Occurrence> check = new ArrayList<Occurrence>();
				Occurrence ptr = kws.get(keyword);
				check.add(ptr);
				insertLastOccurrence(check);
				keywordsIndex.put(keyword, check);
				
			}else if (keywordsIndex.containsKey(keyword)) {
				Occurrence present = kws.get(keyword);
				ArrayList<Occurrence> appeared = keywordsIndex.get(keyword);
				appeared.add(present);
				insertLastOccurrence(appeared);
				keywordsIndex.put(keyword, appeared);
			}
		}
		
	}

	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		// if it is null 
		if (word.equals(null)|| word==null) {
			return null;
		}
		word= word.toLowerCase();
		
		if (characteristhere(word)) {
			return null;
			}
		word= ridofpunc(word);
		
		if (noiseWords.contains(word)) {
			return null;
			
		}
		//if the word length doesnt exist then neither does the word 
		if (word.length()<0) {
			return null;
		}
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return word;
	}
	private boolean characteristhere(String string) {
		int count=0;
		boolean hello= false;
		for (count=0; count<string.length();count++) {
			char t = string.charAt(count);
			if (!(Character.isLetter(t))) {
				hello=true;
			}
			if (hello && Character.isLetter(t)) {
				return true;
			}
				
			}
		return false;
	}
	
	private String ridofpunc(String string) {
		int count=0;
		for (count =0;count<string.length();count++) {
			char t = string.charAt(count);
			if (!(Character.isLetter(t))){
				break;
			}
			
		}
	
		return string.substring(0,count);
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		if (occs.size()<2) {
			return null;
		}
		int min =  0; int mid=0;
		int max = occs.size()-2;
		
		int key = occs.get(occs.size()-1).frequency;
		ArrayList<Integer> middle = new ArrayList<Integer>();
		while (max>=min) {
			mid = (max+min)/2;
			int info = occs.get(mid).frequency;
			middle.add(mid);
			if (info==key) {
				break;
			}
			
			else if (info<key){
				max=mid-1;
			}
			else if (info>key) {
				min = mid+1;
				if (max<=min) {
					min=mid+1;
				}
			}
		}
		
		Occurrence tmp = occs.remove(occs.size()-1);
		if (occs.get(occs.size()-1).frequency>key) {
			occs.add(occs.size(),tmp);
			return middle;
		}
		occs.add(middle.get(middle.size()-1),tmp);
		return middle;
	}
		
		//	int total;
//		if (occs.size()<2) {
//			return null;
//		}
//		ArrayList<Integer> middle = new ArrayList<Integer>(); 
//		for (int i = 0; i<occs.size()-1;i++) {
//			middle.add(occs.get(i).frequency);
//			
//		}
//		total = occs.get(occs.size()-1).frequency;
//		ArrayList<Integer> endresult = binary(middle,total,0,middle.size()-1);
//		return endresult;
	
	
//	private ArrayList <Integer> binary (ArrayList<Integer> array, int key, int max, int min ) {
//		ArrayList<Integer> middle = new ArrayList<Integer>(); 
//		while (max>min) {
//			int mid=(max+min)/2;
//			middle.add(mid);
//			if (array.get(mid)<key) {
//				max = mid-1;
//			}
//			else if (middle.get(mid)>key) {
//				max = mid+1;
//			}else {
//				break;
//			}
//		}
//		return middle;
//	}
//	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		ArrayList<String> BIG5 = new ArrayList<String>();
		ArrayList<Occurrence> one= keywordsIndex.get(kw1);
		ArrayList<Occurrence> two = keywordsIndex.get(kw2);
		ArrayList<Occurrence> everything = new ArrayList<Occurrence>();
		int counter = 0;
		if (one==null && two==null) {
			return null;
		}
		if (two!=null) {
			int k =0;
			while (k<two.size()) {
				if (everything.contains(two.get(k))) {
					int freq = two.get(k).frequency;
					int happen = everything.get(k).frequency;
					if(freq>happen) {
						everything.remove(two.get(k));
					}else{
						continue;
					}
				}
				everything.add(two.get(k));
				insertLastOccurrence(everything);
				k++;
			}
	}
		if (one!=null) {
			int i =0;
			while (i<one.size()) {
				if (everything.contains(one.get(i))) {
					int often = one.get(i).frequency;
					int yes = everything.get(i).frequency;
					if(often>yes) {
						everything.remove(one.get(i));
					}else if (often<=yes) {
						continue;
					}
				}
				everything.add(one.get(i));
				insertLastOccurrence(everything);
				i++;
			}
		}
		if (everything.size()<5) {
			counter = everything.size();
		}else if (everything.size()>=5) {
			counter=5;
		}
		int j = 0;
	
		while (j<everything.size()) {
			if(everything.get(j)==null) {
				break;
			}
			else if (!(checker(BIG5,everything.get(j),BIG5.size()))) {
		//		System.out.println(everything.get(j).frequency);
			BIG5.add(everything.get(j).document);
			}
			if (BIG5.size() == 5) {
				break;
			}
			j++;
			
			
		}
	return BIG5;
	}


private boolean checker (ArrayList<String> words,Occurrence total, int g ) {
	boolean there = false;
	int c = 0;
	if (words.size()==0) {
		return there;
	}
	while (c<g) {
		// this is the correct one done fuck this up 
		if (words.get(c)!=null && words.get(c).equals(total.document)) {	
		there = true;
		break;
			
		}
		c++;
	}
	return there;
}
}
	

