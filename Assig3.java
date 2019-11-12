
import java.util.Random;
import java.util.Scanner;

/************************************************************
 * Phase 3
 * @author Lindsey Reynolds
 * @author Dan Sedano 
 * @version 11/11/2019
 * Description: Testing the Deck class 
 ************************************************************/

public class Assig3
{
   public static final int ONE_PACK = 1;
   public static final int TWO_PACK = 2;
   public static void main(String[] args)
   {

      Scanner keyboard = new Scanner(System.in);

      System.out.println(
         "\n--------------------------------------------------------");
      //creates a deck of 104 cards (two packs)
      Deck deck2Pack = new Deck(TWO_PACK);
      int twoPacksofCards = deck2Pack.getTopCard();

      //prints out the dealt cards in an unshuffled deck
      for(int x = 1; x < twoPacksofCards + 1; ++x)
      {
         System.out.print(deck2Pack.dealCard() + " / ");
         if((x % 4) == 0)
            System.out.println();
      }         

      System.out.println();
      //refills the deck
      deck2Pack.init(TWO_PACK);

      //shuffles the deck
      deck2Pack.shuffle();

      //prints out the dealt cars of a shuffled deck
      for(int x = 1; x < twoPacksofCards + 1; ++x)
      {
         System.out.print(deck2Pack.dealCard() + " / ");
         if((x % 4) == 0)
            System.out.println();
      }             
      System.out.println(
         "\n--------------------------------------------------------"); 
      //creates a deck of 52 cards
      Deck deck1Pack = new Deck();
      int onePackOfCards = deck1Pack.getTopCard();
      //prints out the dealt cards of an unshuffled deck
      for(int x = 1; x < onePackOfCards + 1; ++x)
      {
         System.out.print(deck1Pack.dealCard() + " / ");
         if((x % 4) == 0)
            System.out.println();
      }         

      System.out.println("\n");

      //refills the deck with one set of 52 cards
      deck1Pack.init(ONE_PACK);

      //shuffles the deck
      deck1Pack.shuffle();

      //prints out the dealt cards of the shuffled deck
      for(int x = 1; x < onePackOfCards + 1; ++x)
      {
         System.out.print(deck1Pack.dealCard() + " / ");
         if((x % 4) == 0)
            System.out.println();
      }     

      System.out.println();
      System.out.print("\nPress enter to continue...");
      try
      {
         System.in.read();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }

      /******************************************************************
       * Phase 4
       * @author Ricardo Barbosa 
       * @author Dan Sedano 
       * @author Max Halbert
       * @author Lindsey Reynolds
       * @version 11/11/2019
       * Description: Testing the Deck and Hand class 
       *****************************************************************/

      boolean validHand = false;
      int numHands = 0;

      //Loops until valid number of players are selected 
      while (!validHand)
      {
         System.out.print("How many hands? (1-10, please): ");
         numHands = keyboard.nextInt();
         if ((numHands > 0) & (numHands < 11))
         {
            validHand = true;
         }
      }

      // Create a new deck, and a hand for each player
      // No suffling before the deal
      Deck d1 = new Deck();
      Hand[] myHands = new Hand[numHands];
      int thisCard = d1.getTopCard();  // start with the top card of the deck
      for (int thisHand = 0; thisHand < numHands; ++thisHand)
      {
         myHands[thisHand] = new Hand();
      }
      
      // deal cards to each hand until all the cards are dealt from the deck
      // one card at a time to each hand
      System.out.println("Here are our hands, from unshuffled deck: ");
      while (d1.inspectCard(thisCard-1).getErrorFlag() == false)
      {
         // the card looks good, so give a card to each hand
         // each hand will take turn to get a card
         for (int thisHand = 0; thisHand < numHands; ++thisHand)
         {
            if (thisCard == 0)
            {
               // no more cards left in the deck, so stop dealing cards out
               break;
            }
            myHands[thisHand].takeCard(d1.dealCard()); // give a card to a hand
            --thisCard;  // next card from the deck
         }
      }
      
      // print out the cards in each hand
      for (int thisHand = 0; thisHand < numHands; ++thisHand)
      {
         System.out.println((thisHand+1) + ") " + myHands[thisHand].toString());
         
         // remove the cards from the hand, prepare it for next deal
         myHands[thisHand].resetHand(); 
      }
      System.out.println();

      //Reset the deck to its original cards,
      // then shuffle it before dealing the cards 
      d1.init(1); 
      d1.shuffle(); //Shuffled deck

      thisCard = d1.getTopCard(); // starts from the top card of the deck
      System.out.println("Here are our hands from a shuffled deck: ");
      while (d1.inspectCard(thisCard-1).getErrorFlag() == false)
      {
         // the card looks good, so give a card to each hand
         // each hand will take turn to get a card
         for(int thisHand = 0; thisHand < numHands; ++thisHand)
         {
            if(thisCard == 0)
            {
               // no more cards left in the deck, so stop dealing cards out
               break;  
            }
            myHands[thisHand].takeCard(d1.dealCard()); // give a card to a hand
            --thisCard; // next card from the deck
         }
      }
      
      // print out the cards in each hand
      for(int thisHand = 0; thisHand < numHands; ++thisHand)
      {
         System.out.println((thisHand+1) + ") " + myHands[thisHand].toString());
      }
      keyboard.close();

   }

}

/*******************************************************
 * 
 * Card.java
 * 
 * @author Ricardo Barbosa 
 * @author Max Halbert
 * @version November 6, 2019
 * 
 * Description: The structure for the Card class 
 * Usage: 
 *******************************************************/

class Card
{
   public enum Suit
   {
      CLUBS, DIAMONDS, HEARTS, SPADES
   };

   // card values
   private char value;
   private Suit suit;

   // Checks for illegal card data
   private boolean errorFlag;

   /**
    * Constructor 
    * @param value
    * @param suit
    */
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /**
    * Overloaded constructor 
    */
   public Card()
   {
      this('A', Suit.SPADES);
   }

   /**
    * Copy Constructor 
    * @param otherCard
    */
   public Card(Card otherCard)
   {
      if (otherCard == null)
      {
         System.out.println("Fatal Error!");
         System.exit(0);
      }
      set(otherCard.value, otherCard.suit);
   }

   // Accessors
   public Suit getSuit()
   {
      return suit;
   }

   public char getValue()
   {
      return value;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   // Mutators
   public boolean set(char value, Suit suit)
   {
      if (isValid(value, suit))
      {
         this.value = value;
         this.suit = suit;
         errorFlag = false;
         return true;
      }
      errorFlag = true;
      return false;
   }

   // Valid card data returned
   public String toString()
   {
      if (errorFlag)
         return "[Card Not Valid]";
      return value + " of " + suit;

   }

   // Returns true if all field members are identical, false otherwise
   public boolean equals(Card card)
   {
      if (card == null)
         return false;

      // comparing member values
      if (this.value == card.value)
      {
         if (this.suit == card.suit)
            return true;
      }
      return false;
   }

   // Determine validity for the value
   private boolean isValid(char value, Suit suit)
   {
      // valid card values
      char[] validCardValues =
      { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K' };

      // checks if value is in the field of valid values
      for (int i = 0; i < validCardValues.length; ++i)
      {
         if (value == validCardValues[i])
            return true;
      }
      // invalid value
      return false;
   }
}

/************************************************************
 * Deck.java
 * 
 * @author Lindsey Reynolds
 * @author Dan Sedano
 * @version 11/8/19
 * 
 * Description: Creates a deck of cards 
 * Usage: Holds up to six decks of 52 playing cards 
 ************************************************************/

class Deck 
{
   //playing card pack values 
   public static final int MAX_CARDS = 312; // 6 packs x 52 cards
   public static final int ONE_PACK = 52; // Standard 52 card deck
   public static boolean beenHereBefore = false; 

   //creates a new object with one pack of cards 
   private static Card[] masterPack = new Card[ONE_PACK];

   private Card[] cards;
   private int topCard;

   /**
    * Constructor that takes in a number of packs as an argument and then 
    * creates a deck of cards with that many packs of cards (52 x numPacks)
    * @param numPacks
    */
   public Deck(int numPacks)
   {
      // maximum packs is 6
      if((numPacks * ONE_PACK) > MAX_CARDS)
         numPacks = 6;

      allocateMasterPack();

      // create the cards array with 52 x numPacks cards
      cards = new Card[numPacks * ONE_PACK];

      // populate the cards in the deck 
      init(numPacks);
   }

   /*
    * Overloaded no argument constructor that creates a pack of cards using 
    * just one deck
    */
   public Deck() 
   {
      allocateMasterPack();

      // Create the cards array using one pack of cards
      cards = new Card[ONE_PACK];

      // Initialize the last index of the array to be the top card of the deck  
      topCard = cards.length;

      // Loop through the cards array, populating it with Cards
      for(int i = 0; i < cards.length; i++)
      {
         // Create a new Card Object, copying it from the masterPack
         cards[i] = new Card(masterPack[i]);
      }
   }

   /**
    * Method to re-populate cards[] with 52 x numPacks cards.
    * @param numPacks
    */
   public void init(int numPacks) 
   {

      // Initialize the last index of the array to be the top card of the deck  
      topCard = cards.length;

      // Populate the card array with Card objects, copying values from 
      //masterPack
      for(int masterCounter = 0, i = 0; i < cards.length; i++, masterCounter++)
      {
         // Create a new Card Object, copying it from the masterPack
         cards[i] = new Card(masterPack[masterCounter]);

         // If the cards array is more than one pack, reset the index of 
         //masterPack 
         // in order to loop through it again
         if(masterCounter == ONE_PACK - 1) 
            masterCounter = -1;
      }
   }

   /**
    * Shuffles the deck of Cards
    */
   public void shuffle() 
   {
      Random shuffle = new Random();
      Card tempCard;
      int randCard;

      //loops through the entire deck
      for(int x = 0; x < cards.length; x++)
      {
         //Picks a random card from the deck
         randCard = shuffle.nextInt(ONE_PACK);
         //assigns the random card to a placeholder
         tempCard = cards[randCard];
         //assigns the random card to the next card in the deck
         cards[randCard] = cards[x];
         //assigns the next card in the deck to the card in 
         //the place holder
         cards[x] = tempCard;
      }      
   }

   /**
    * Deals a card by taking the top of the deck and
    * makes sure there are still cards available.
    * @return the top Card from the deck.
    */
   public Card dealCard()
   {
      Card dealCard;

      //checks if there are cards in the deck
      if(topCard > 0)
      {
         //assigns the top card to the dealCard variable
         dealCard = inspectCard(topCard - 1);

         //removes the topcard from the deck
         cards[topCard-1] = null;

         //decreases card count
         topCard--;
         return dealCard;
      }
      //returns null if no more cards
      return null;
   }

   /**
    * Returns the number of cards in a deck.
    * @return the number of cards in the deck
    */
   public int getTopCard()
   {
      return topCard;
   }

   /**
    * Accessor for an individual card. Returns a card or
    * returns a card with an error flag.
    * @return the card at index k
    * @return a card with with an error flag
    */
   public Card inspectCard(int k)
   {
      Card returnCard;

      // If k is out of bounds, return a card with an error flag
      if(k < 0 || k >= topCard)
      {
         // Create an invalid card with errorFlag = true
         returnCard = new Card('E', Card.Suit.CLUBS);
      }
      else 
      {
         // Otherwise return the card at k index
         returnCard = cards[k];
      }
      return returnCard;
   }

   /**
    * This method creates new cards and fills the masterPack.
    */
   private static void allocateMasterPack()
   {
      // Check if this method has already been run. Return if it has.
      if(beenHereBefore) return;

      char[] value = 
      {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};

      Card.Suit[] suits = 
      {Card.Suit.DIAMONDS, Card.Suit.SPADES, Card.Suit.HEARTS, Card.Suit.CLUBS};

      int curIndex = 0;

      // Loop through the value array
      for(int x = 0; x < value.length; x++)
      { 
         // Loop through the suits array
         for(int y = 0; y < suits.length; y++)
         {
            // Create a new Card object with the correct suit and value
            masterPack[curIndex] = new Card(value[x], suits[y]);
            curIndex++;
         }
      }

      beenHereBefore = true;
   }
}

/*****************************************************************
 * Hand.java 
 * 
 * @author  Max Halbert
 * @author  Ricardo Barbosa      
 * @version November 7, 2019
 * 
 * Description: Class contains cards in a hand 
 * 
 * Usage: Take a card or a play a card from that hand 
 *******************************************************************/

class Hand
{
   // a hand can only have 52 cards maximum
   public static final  int MAX_CARDS = 52; 

   private Card[] myCards;
   private int numCards;

   //Constructor
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * resets hand 
    */
   public void resetHand()
   {
      numCards = 0;  
   }

   /**
    * adds a card to the next available position 
    * @param card
    * @return boolean
    */
   public boolean takeCard(Card card)
   {
      if(numCards < MAX_CARDS && card != null && !card.getErrorFlag())
      {
         Card tempCard = new Card(card);  
         myCards[numCards] = tempCard;
         numCards++;
         return true;     
      }
      return false;
   }

   /**
    * plays the card until nno more in hand 
    * @return null
    */
   public Card playCard()
   {
      if(numCards > 0)
      {
         numCards--;
         return myCards[numCards];
      }

      return null;  // since no more cards in the hand
   }

   //stringifies the hand 
   public String toString()
   {
      String myHand = "";
      if(numCards > 0)
      {
         
         myHand += myCards[0];
         for(int i = 1 ; i < numCards; i++)
         {
            myHand += " , " + myCards[i];
            if(i % 4 == 0)
                myHand += "\n";
         }
         
      }
      return "Hand = " + "(" + myHand + ")";
   }

   //Accessor
   public int getNumCards()
   {
      return numCards;
   }

   //Accessor 
   public Card inspectCard(int k)
   {

      if (k >= 0 && k < numCards)  // assume valid k starts from 0
      {
         Card aCard = new Card(myCards[k]);  // prevent privacy leaks
         return aCard;
      }

      // return a dummy invalid card
      Card badCard = new Card(' ', Card.Suit.SPADES); 
      return badCard;

   }
}
/**************************OUTPUT 1******************************
 --------------------------------------------------------
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS / 
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS / 
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS / 
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS / 
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS / 
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS / 
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS / 
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS / 
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS / 
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS / 
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS / 
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS / 
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS / 
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS / 
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS / 
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS / 
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS / 
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS / 
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS / 
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS / 
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS / 
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS / 
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS /
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS / 

Q of SPADES / Q of DIAMONDS / 9 of HEARTS / T of SPADES /
7 of HEARTS / 6 of SPADES / 8 of HEARTS / A of CLUBS /
K of HEARTS / 9 of CLUBS / 7 of SPADES / 2 of CLUBS /
A of SPADES / 7 of CLUBS / 3 of CLUBS / 5 of CLUBS /
8 of DIAMONDS / Q of HEARTS / A of DIAMONDS / T of CLUBS /
6 of DIAMONDS / 8 of SPADES / 7 of HEARTS / 6 of DIAMONDS /
3 of HEARTS / 8 of HEARTS / 3 of CLUBS / 3 of DIAMONDS /
T of DIAMONDS / 4 of CLUBS / 9 of DIAMONDS / 8 of CLUBS /
2 of SPADES / 3 of SPADES / A of DIAMONDS / Q of DIAMONDS /
4 of SPADES / 5 of CLUBS / A of SPADES / J of HEARTS /
5 of DIAMONDS / K of DIAMONDS / J of CLUBS / T of HEARTS /
K of CLUBS / 5 of HEARTS / K of SPADES / 2 of HEARTS /
5 of SPADES / 2 of CLUBS / J of SPADES / 7 of DIAMONDS /
4 of SPADES / J of SPADES / 6 of CLUBS / K of CLUBS /
6 of SPADES / 2 of DIAMONDS / A of HEARTS / Q of HEARTS /
6 of HEARTS / T of CLUBS / 3 of SPADES / Q of CLUBS /
6 of HEARTS / 4 of CLUBS / 9 of SPADES / 9 of SPADES /
A of CLUBS / 2 of DIAMONDS / 3 of HEARTS / J of DIAMONDS /
8 of SPADES / 8 of DIAMONDS / 9 of HEARTS / 4 of DIAMONDS / 
J of CLUBS / 7 of CLUBS / A of HEARTS / K of HEARTS /
4 of HEARTS / K of DIAMONDS / 4 of HEARTS / T of HEARTS /
T of SPADES / 5 of HEARTS / 9 of DIAMONDS / 7 of DIAMONDS /
J of DIAMONDS / 2 of HEARTS / 6 of CLUBS / 4 of DIAMONDS /
2 of SPADES / Q of SPADES / 5 of SPADES / 5 of DIAMONDS /
9 of CLUBS / T of DIAMONDS / Q of CLUBS / K of SPADES /
J of HEARTS / 3 of DIAMONDS / 8 of CLUBS / 7 of SPADES /

--------------------------------------------------------
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS /
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS /
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS /
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS /
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS /
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS /
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS /
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS /
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS /
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS /
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS /
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS /


T of HEARTS / 7 of HEARTS / 8 of HEARTS / 3 of SPADES /
K of HEARTS / A of SPADES / 9 of HEARTS / 2 of DIAMONDS /
7 of SPADES / 5 of CLUBS / 6 of HEARTS / 5 of DIAMONDS /
9 of SPADES / J of DIAMONDS / Q of SPADES / 4 of SPADES /
8 of CLUBS / T of CLUBS / 8 of SPADES / K of SPADES /
T of SPADES / 4 of DIAMONDS / 6 of SPADES / 3 of CLUBS /
2 of CLUBS / 5 of HEARTS / K of DIAMONDS / 4 of CLUBS /
9 of DIAMONDS / Q of DIAMONDS / Q of HEARTS / A of HEARTS /
8 of DIAMONDS / 6 of CLUBS / 3 of DIAMONDS / Q of CLUBS /
3 of HEARTS / K of CLUBS / 5 of SPADES / A of DIAMONDS /
2 of SPADES / T of DIAMONDS / 6 of DIAMONDS / 2 of HEARTS /
7 of DIAMONDS / A of CLUBS / 9 of CLUBS / J of CLUBS /
7 of CLUBS / J of SPADES / J of HEARTS / 4 of HEARTS /


Press enter to continue...
How many hands? (1-10, please): 2
Here are our hands, from unshuffled deck: 
1) Hand = (K of CLUBS , K of SPADES , Q of CLUBS , Q of SPADES , J of CLUBS
 , J of SPADES , T of CLUBS , T of SPADES , 9 of CLUBS
 , 9 of SPADES , 8 of CLUBS , 8 of SPADES , 7 of CLUBS
 , 7 of SPADES , 6 of CLUBS , 6 of SPADES , 5 of CLUBS
 , 5 of SPADES , 4 of CLUBS , 4 of SPADES , 3 of CLUBS
 , 3 of SPADES , 2 of CLUBS , 2 of SPADES , A of CLUBS
 , A of SPADES)
2) Hand = (K of HEARTS , K of DIAMONDS , Q of HEARTS , Q of DIAMONDS , J of HEARTS
 , J of DIAMONDS , T of HEARTS , T of DIAMONDS , 9 of HEARTS
 , 9 of DIAMONDS , 8 of HEARTS , 8 of DIAMONDS , 7 of HEARTS
 , 7 of DIAMONDS , 6 of HEARTS , 6 of DIAMONDS , 5 of HEARTS
 , 5 of DIAMONDS , 4 of HEARTS , 4 of DIAMONDS , 3 of HEARTS
 , 3 of DIAMONDS , 2 of HEARTS , 2 of DIAMONDS , A of HEARTS
 , A of DIAMONDS)

Here are our hands from a shuffled deck:
1) Hand = (Q of SPADES , T of SPADES , Q of HEARTS , J of SPADES , 5 of DIAMONDS
 , 2 of DIAMONDS , Q of CLUBS , A of HEARTS , A of DIAMONDS
 , 6 of CLUBS , 9 of HEARTS , 7 of CLUBS , 3 of DIAMONDS
 , Q of DIAMONDS , K of HEARTS , T of HEARTS , J of HEARTS
 , 3 of CLUBS , 8 of HEARTS , 7 of HEARTS , 4 of CLUBS
 , 4 of DIAMONDS , 7 of SPADES , 4 of HEARTS , 2 of HEARTS
 , 9 of CLUBS)
2) Hand = (T of CLUBS , J of CLUBS , 5 of SPADES , 4 of SPADES , 5 of CLUBS
 , 7 of DIAMONDS , 8 of DIAMONDS , 8 of CLUBS , 8 of SPADES
 , 2 of SPADES , K of SPADES , 6 of DIAMONDS , 3 of SPADES
 , T of DIAMONDS , 9 of SPADES , 6 of SPADES , 5 of HEARTS
 , K of CLUBS , J of DIAMONDS , A of CLUBS , 3 of HEARTS
 , A of SPADES , 6 of HEARTS , K of DIAMONDS , 9 of DIAMONDS
 , 2 of CLUBS)
 */

 /************************OutPut 2*********************************
  * --------------------------------------------------------
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS / 
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS / 
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS / 
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS / 
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS / 
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS / 
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS / 
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS / 
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS / 
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS / 
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS / 
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS / 
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS / 
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS / 
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS / 
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS / 
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS / 
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS / 
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS / 
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS / 
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS / 
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS / 
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS /
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS / 

8 of HEARTS / 4 of CLUBS / 9 of HEARTS / 9 of CLUBS /
T of CLUBS / A of HEARTS / 9 of DIAMONDS / 7 of DIAMONDS /
T of SPADES / 5 of SPADES / 4 of HEARTS / T of SPADES /
7 of SPADES / 4 of HEARTS / 7 of DIAMONDS / A of DIAMONDS /
7 of CLUBS / 7 of HEARTS / J of SPADES / J of HEARTS /
5 of CLUBS / 3 of SPADES / A of CLUBS / A of CLUBS /
J of CLUBS / 4 of SPADES / K of SPADES / 8 of SPADES /
3 of SPADES / T of HEARTS / 7 of CLUBS / 4 of CLUBS /
Q of CLUBS / 2 of HEARTS / A of SPADES / 6 of SPADES /
8 of CLUBS / 2 of HEARTS / 4 of DIAMONDS / 4 of DIAMONDS /
2 of DIAMONDS / Q of HEARTS / K of CLUBS / 2 of SPADES /
K of DIAMONDS / 4 of SPADES / T of DIAMONDS / 9 of HEARTS /
3 of CLUBS / 9 of CLUBS / K of HEARTS / 8 of DIAMONDS /
Q of CLUBS / 2 of CLUBS / 8 of CLUBS / 6 of DIAMONDS /
5 of DIAMONDS / 2 of DIAMONDS / J of DIAMONDS / 6 of CLUBS /
3 of HEARTS / Q of SPADES / 5 of CLUBS / T of DIAMONDS /
3 of DIAMONDS / J of HEARTS / J of SPADES / 6 of DIAMONDS /
T of CLUBS / T of HEARTS / A of HEARTS / 5 of HEARTS /
7 of HEARTS / J of DIAMONDS / 6 of HEARTS / 8 of DIAMONDS /
6 of SPADES / 2 of SPADES / J of CLUBS / A of DIAMONDS /
K of DIAMONDS / 6 of CLUBS / Q of SPADES / K of CLUBS /
8 of SPADES / 2 of CLUBS / 3 of CLUBS / K of SPADES /
5 of SPADES / Q of DIAMONDS / 6 of HEARTS / 3 of HEARTS /
5 of HEARTS / 9 of SPADES / 3 of DIAMONDS / A of SPADES /
9 of SPADES / Q of HEARTS / 9 of DIAMONDS / K of HEARTS /
7 of SPADES / 5 of DIAMONDS / 8 of HEARTS / Q of DIAMONDS / 

--------------------------------------------------------
K of CLUBS / K of HEARTS / K of SPADES / K of DIAMONDS /
Q of CLUBS / Q of HEARTS / Q of SPADES / Q of DIAMONDS /
J of CLUBS / J of HEARTS / J of SPADES / J of DIAMONDS / 
T of CLUBS / T of HEARTS / T of SPADES / T of DIAMONDS /
9 of CLUBS / 9 of HEARTS / 9 of SPADES / 9 of DIAMONDS /
8 of CLUBS / 8 of HEARTS / 8 of SPADES / 8 of DIAMONDS / 
7 of CLUBS / 7 of HEARTS / 7 of SPADES / 7 of DIAMONDS /
6 of CLUBS / 6 of HEARTS / 6 of SPADES / 6 of DIAMONDS /
5 of CLUBS / 5 of HEARTS / 5 of SPADES / 5 of DIAMONDS /
4 of CLUBS / 4 of HEARTS / 4 of SPADES / 4 of DIAMONDS /
3 of CLUBS / 3 of HEARTS / 3 of SPADES / 3 of DIAMONDS /
2 of CLUBS / 2 of HEARTS / 2 of SPADES / 2 of DIAMONDS /
A of CLUBS / A of HEARTS / A of SPADES / A of DIAMONDS /


8 of DIAMONDS / A of DIAMONDS / 5 of SPADES / K of SPADES /
6 of HEARTS / 5 of HEARTS / 2 of DIAMONDS / T of SPADES /
6 of DIAMONDS / 4 of CLUBS / 3 of CLUBS / K of HEARTS /
3 of SPADES / 8 of HEARTS / 7 of DIAMONDS / 9 of HEARTS /
4 of HEARTS / A of CLUBS / 8 of SPADES / 9 of SPADES / 
J of HEARTS / 3 of HEARTS / K of DIAMONDS / J of SPADES /
T of DIAMONDS / 4 of SPADES / 2 of CLUBS / K of CLUBS /
T of CLUBS / Q of SPADES / A of HEARTS / 8 of CLUBS /
9 of CLUBS / 2 of HEARTS / 7 of SPADES / Q of DIAMONDS /
6 of SPADES / 2 of SPADES / 6 of CLUBS / 5 of CLUBS /
Q of CLUBS / 4 of DIAMONDS / T of HEARTS / 7 of HEARTS /
Q of HEARTS / 7 of CLUBS / 5 of DIAMONDS / J of DIAMONDS /
3 of DIAMONDS / 9 of DIAMONDS / A of SPADES / J of CLUBS /


Press enter to continue...
How many hands? (1-10, please): 8
Here are our hands, from unshuffled deck: 
1) Hand = (K of CLUBS , J of CLUBS , 9 of CLUBS , 7 of CLUBS , 5 of CLUBS
 , 3 of CLUBS , A of CLUBS)
2) Hand = (K of HEARTS , J of HEARTS , 9 of HEARTS , 7 of HEARTS , 5 of HEARTS
 , 3 of HEARTS , A of HEARTS)
3) Hand = (K of SPADES , J of SPADES , 9 of SPADES , 7 of SPADES , 5 of SPADES
 , 3 of SPADES , A of SPADES)
4) Hand = (K of DIAMONDS , J of DIAMONDS , 9 of DIAMONDS , 7 of DIAMONDS , 5 of DIAMONDS
 , 3 of DIAMONDS , A of DIAMONDS)
5) Hand = (Q of CLUBS , T of CLUBS , 8 of CLUBS , 6 of CLUBS , 4 of CLUBS
 , 2 of CLUBS)
6) Hand = (Q of HEARTS , T of HEARTS , 8 of HEARTS , 6 of HEARTS , 4 of HEARTS
 , 2 of HEARTS)
7) Hand = (Q of SPADES , T of SPADES , 8 of SPADES , 6 of SPADES , 4 of SPADES
 , 2 of SPADES)
8) Hand = (Q of DIAMONDS , T of DIAMONDS , 8 of DIAMONDS , 6 of DIAMONDS , 4 of DIAMONDS
 , 2 of DIAMONDS)

Here are our hands from a shuffled deck:
1) Hand = (3 of SPADES , A of SPADES , K of CLUBS , J of CLUBS , Q of HEARTS
 , A of HEARTS , T of CLUBS)
2) Hand = (6 of SPADES , 3 of DIAMONDS , T of HEARTS , T of DIAMONDS , 2 of DIAMONDS
 , 7 of DIAMONDS , 4 of HEARTS)
3) Hand = (Q of CLUBS , A of DIAMONDS , K of DIAMONDS , 7 of CLUBS , 6 of DIAMONDS
 , 5 of HEARTS , K of SPADES)
4) Hand = (2 of SPADES , 8 of CLUBS , 5 of SPADES , 5 of CLUBS , 4 of DIAMONDS
 , 5 of DIAMONDS , 2 of HEARTS)
5) Hand = (9 of CLUBS , 9 of DIAMONDS , A of CLUBS , 9 of SPADES , 4 of CLUBS
 , 4 of SPADES)
6) Hand = (7 of HEARTS , 3 of CLUBS , 6 of CLUBS , 9 of HEARTS , Q of DIAMONDS
 , 2 of CLUBS)
7) Hand = (T of SPADES , 6 of HEARTS , J of DIAMONDS , J of HEARTS , 7 of SPADES
 , J of SPADES)
8) Hand = (K of HEARTS , 8 of SPADES , 8 of HEARTS , Q of SPADES , 8 of DIAMONDS
 , 3 of HEARTS)
  *********************************************/
