/**
 * 
 * Hand.java class contains cards of a hand and number of cards in the hand
 * take a card to the hand or play a card from the hand
 * 
 * @author  Max Halbert
 * @author  Ricardo Barbosa
 *          
 * @version November 7, 2019
 * 
*/

public class Hand
{
  
   public static final  int MAX_CARDS = 52; // maximum 52 cards only
   
   private Card[] myCards;
   private int numCards;
   
   /**
    * Overload default constructor to initialize size of myCards array
    */
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }
   
   /**
    * Remove all the cards from the hand
    */
   public void resetHand()
   {
      // Just set the numCards to zero means no more cards in hand
      numCards = 0;  
   }
   
   /**
    * Adds a card to the next available position in the myCards array
    * @param card that will be copy to the myCards array
    * @return true if card is a valid card and myCards array have room
    * @return false if card is invalid or myCards array is full
    */
   public boolean takeCard(Card card)
   {
      if(numCards < MAX_CARDS && card != null && !card.getErrorFlag())
      {
         Card tempCard = new Card(card);  // prevent privacy leaks
         myCards[numCards] = tempCard;
         numCards++;
         return true;     
      }
      return false;
   }
   
   /**
    * Return the top card from a hand if any
    * @return If there are cards in the hand, return the last card in the hand
    *         otherwise return a null card
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
   
   public String toString()
   {
      String myHand = "";
      if(numCards > 0)
      {
         myHand += myCards[0];
         for(int i = 1 ; i < numCards; i++)
         {
            myHand += ", " + myCards[i];
         }
      }
      return myHand;
   }
   
   /**
    * Returns the number of cards left in a hand.
    * @return the number of cards left in the hand
    */
   public int getNumCards()
   {
      return numCards;
   }
   
   /**
    * Accessor for an individual card. Returns a card at index k if exist
    * otherwise a card with an error flag.
    * @return the card at index k if k is within bound
    * @return a card with with an error flag if k is out of bound
    */
   public Card inspectCard(int k)
   {

      if (k >= 0 && k < numCards)  // check k is from 0 to numCards-1 inclusive
      {
         Card aCard = new Card(myCards[k]);  // prevent privacy leaks
         return aCard;
      }
      
      // return a dummy invalid card
      Card badCard = new Card(' ', Card.Suit.SPADES); 
      return badCard;

   }
}
