package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Main {

    static HashSet<Card> createDeck(){
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()){
            for (Card.Rank rank : Card.Rank.values()){
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck){
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2){
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3){
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4){
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static boolean isFlush (HashSet<Card> hand){
        HashSet<Card.Suit> suits = hand.stream()
                .map(card -> {
                    return card.suit;
                })
                .collect(Collectors.toCollection(HashSet::new));
                return suits.size() == 1;
    }

    static boolean isFourOfAKind (HashSet<Card> hand){
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card -> {
                    return card.rank;
                })
                .collect(Collectors.toCollection(HashSet::new));
        return ranks.size() == 1;
    }
 /*   static boolean isThreeOfAKind (HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card -> {
                    return card.rank;
                })
                .collect(Collectors.toCollection(HashSet::new)); //ranks is a hashset of cards based on rank


    } */


    static boolean isTwoPair (HashSet<Card> hand){
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card ->{
                    return card.rank;
                })
                .collect(Collectors.toCollection(HashSet::new)); //get the ranks of the cards

        return ranks.size() == 2; // this will work for 2 pair and 3 of a kinds...simultaenously is not good example
    }


    static boolean isStraight (ArrayList<Card> hand){
        ArrayList<Integer> ranks = hand.stream()
                .map(card ->{
                    return card.rank.ordinal();
                })
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));//get the ranks of the cards
       // HashSet<Card.Rank> sortedRanks = new HashSet<>();
        //need to sort the cards via rank and check to makesure they're in order
        ArrayList<Integer> rankCheck = new ArrayList<>();
        int i = ranks.get(0);
        for (int r = 0; r < ranks.size(); r++){
            rankCheck.add(i + r);
        }
        return ranks.equals(rankCheck);
    }






    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        Card aceOfSpades = new Card(Card.Suit.SPADES, Card.Rank.ACE);

        HashSet<Card> deck = createDeck();
        HashSet<Card> deck2 = createDeck();
        HashSet<Card> deck3 = createDeck();
        //System.out.println(deck.size());
        //System.out.println(deck.contains(aceOfSpades));
        HashSet<HashSet<Card>> hands = createHands(deck);
        HashSet<HashSet<Card>> hands2 = createHands(deck2);
        HashSet<HashSet<Card>> hands3 = createHands(deck3);
        hands = hands.stream().filter(Main::isFlush).collect(Collectors.toCollection(HashSet::new));
        hands2 = hands2.stream().filter(Main::isFourOfAKind).collect(Collectors.toCollection(HashSet::new));
        hands3 = hands3.stream().filter(Main::isTwoPair).collect(Collectors.toCollection(HashSet::new));
        System.out.println(hands.size());
        System.out.println(hands2.size());
        System.out.println(hands3.size());

        System.out.println(Card.Suit.CLUBS.ordinal());
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Elapsed time %d millsecs", endTime-beginTime));
    }
}
