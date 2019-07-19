package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		
		CardNode ptr = deckRear;
		
		while(true) {
			
			if(ptr.next.cardValue == 27 && ptr.next == deckRear) {
				CardNode jokerA = ptr.next; //jokerA is deckRear
				
				ptr.next = jokerA.next;
				jokerA.next = ptr.next.next;
				deckRear = ptr.next;
				deckRear.next = jokerA;
				return;
				
			}
			
			if(ptr.next.cardValue == 27) {
				
				CardNode jokerA = ptr.next;
				
				ptr.next = ptr.next.next;
				jokerA.next = ptr.next.next;
				ptr.next.next = jokerA;
				
				return;
			}
			
			
			ptr = ptr.next;
			
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {			//POSSIBLE SOURCE OF ERROR
	    CardNode ptr = deckRear;
	    
	    while(true) {
	    	
	    	if(ptr.next.cardValue == 28 && deckRear.cardValue == 28) {
	    		CardNode jokerB = ptr.next;
	    		ptr.next = jokerB.next;
	    		jokerB.next = jokerB.next.next.next;
	    		ptr.next.next.next = jokerB;
	    		deckRear = ptr.next;
	    		return;
	    	}
	    	
	    	if(ptr.next.cardValue == 28 && ptr.next.next.cardValue == deckRear.cardValue) {
	    		CardNode jokerB = ptr.next;
	    		ptr.next = jokerB.next;
	    		jokerB.next = jokerB.next.next.next;
	    		ptr.next.next.next = jokerB;
	    		deckRear = ptr.next.next;
	    		return;
	    	}
	    	
	    	if(ptr.next.cardValue == 28) {
	    		CardNode jokerB = ptr.next;
	    		ptr.next = jokerB.next;
	    		jokerB.next = jokerB.next.next.next;
	    		ptr.next.next.next = jokerB;
	    		return;
	    		
	    	}
	    	
	    	ptr = ptr.next;
	    }
	    
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		
		CardNode firstJoker = null;
		CardNode secondJoker = null;
		CardNode tmp1 = null;
		CardNode tmp2 = null;
		int counter = 0;
		int usedValue = 0;
		
		CardNode ptr = deckRear.next;
		
		while(true) {
			
			if(counter == 0) {
				if(ptr.next.cardValue == 27 || ptr.next.cardValue == 28) {
					
					usedValue = ptr.next.cardValue;
					firstJoker = ptr.next;
					tmp1  = ptr;
					counter = 1;
					/*System.out.println(tmp1.cardValue);*/
				}
			}
			
			if(counter == 1) {
				if((ptr.next.cardValue == 27 || ptr.next.cardValue == 28) && ptr.next.cardValue != usedValue) {
				secondJoker = ptr.next;
				tmp2  = secondJoker.next;
				
				
				/*System.out.println(tmp2.cardValue);*/
				break;
				}
			}
			ptr = ptr.next;
			
		}
		
			if(secondJoker.cardValue == deckRear.cardValue) {
				if(firstJoker.cardValue == deckRear.next.cardValue) {
					
					return;
			}
		
		}
			
		if(deckRear.next.cardValue == firstJoker.cardValue) {
			for(CardNode something = deckRear.next; something.next != deckRear.next; something = something.next) {
				if(something.next == secondJoker) {
				something.next = secondJoker.next;	//check
				secondJoker.next = firstJoker;
				deckRear.next = secondJoker;
				deckRear = secondJoker;
				}
			}
		}
		
		if(deckRear.cardValue == secondJoker.cardValue) {
			tmp2.next = firstJoker.next;
			firstJoker.next = deckRear.next;
			deckRear.next = firstJoker;
		}
		
		
		tmp1.next = tmp2;
		secondJoker.next = deckRear.next;
		deckRear.next = firstJoker;
		deckRear = tmp1;
		
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {
		int counter = 1;	//changed
		CardNode subDeckHead = deckRear.next;	//changed
		CardNode subDeckTail = null;
		CardNode ptr = deckRear.next;
		boolean errorDodge = false;
		
		if(deckRear.cardValue == 28 || deckRear.cardValue == 27) {
			errorDodge = false;
			return;
		}

		while(true) {
			
			if(counter == deckRear.cardValue) {
				subDeckTail = ptr;
				errorDodge = true;
			}
			
			if(ptr.next == deckRear && errorDodge == true) {
				deckRear.next = subDeckTail.next;
				subDeckTail.next = deckRear;
				ptr.next = subDeckHead;
				break;
				
			}
			
			
			ptr = ptr.next;
			counter++;
		}
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		int counter = 1;
		
		jokerA();
		jokerB();
		tripleCut();
		countCut(); 
		
		
		for(CardNode ptr = deckRear.next; ptr.next != deckRear.next; ptr = ptr.next) {
			
			if(counter == deckRear.next.cardValue) {
				if(ptr.next.cardValue <= 26) {
					/*System.out.println(ptr.next.cardValue);*/
					return ptr.next.cardValue;
					
				} else if(ptr.next.cardValue == 27 || ptr.next.cardValue == 28) {
					jokerA();
					jokerB();
					tripleCut();
					countCut(); 
					
					ptr = deckRear.next;
					counter = 1;
					continue;
				}
			
			}
			
			counter++;
		}
		
		return -1;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {			//POSSIBLE SOURCE OF ERROR
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		printList(deckRear);
		jokerA();
		printList(deckRear);
		jokerB();
		printList(deckRear);
		tripleCut();
		printList(deckRear);
		countCut();
		printList(deckRear);
		
		int charToInt = 0;
		String encryptedString = "";
		int key = 0;
		int sum = 0;
		
		for(int i = 0; i < message.length(); i++) {
			
			if(Character.isLetter(message.charAt(i)) == false) {
				continue;
				
			}
				char letter = Character.toUpperCase(message.charAt(i));
				charToInt = (int)letter - 64;
				key = getKey();
				
				sum = charToInt + key;
				/*System.out.println(sum);*/
				
					if(sum > 26) {
						sum = sum - 26;
						
					}
					
					letter = (char)(sum + 64);
					encryptedString += letter;
					
			}
		
		return encryptedString;
	    //return null;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {		//POSSIBLE SOURCE OF ERROR
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	
		String decryptedString = "";
		int charToInt = 0;
		int key = 0;
		int sum = 0;

		for(int i = 0;i < message.length();i++) {
			
			if(Character.isLetter(message.charAt(i)) == false) {
				continue;
			}
			
			char letter = Character.toUpperCase(message.charAt(i));
			charToInt = (int)letter - 64; 
		
			key = getKey();
			sum = charToInt - key;

			if(sum <= 0) {
				sum = sum + 26;
			}
			
			letter = (char)(sum + 64);
			decryptedString += letter;
		}
		
	    return decryptedString;
	}

	
	
	public static void main(String[] args) {
		/*Solitaire tmp = new Solitaire();
		tmp.makeDeck();
		tmp.jokerA();
		tmp.printList(mtp.deckRear);*/
	}
}
