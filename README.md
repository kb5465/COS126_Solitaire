# Solitaire
Java implementation of the Solitaire card game with a graphical user interface. The game allows users to play Solitaire by interacting with cards on the screen, moving them according to the rules of the game until all cards are sorted into the foundation piles.

# Features
Start screen: A welcome screen where users can start the game by clicking anywhere.
Gameplay: Full Solitaire gameplay, including card shuffling, dealing, and movement by clicking on cards and their target locations.
Win Screen: Displays a winner's screen when the game is completed, and the user can restart by clicking.

# Files
Solitaire.java: Main class that initializes the game and handles the transition between different screens (start, game, and win).
Deck.java: Manages the deck of cards, including shuffling, dealing, and card movements during the game.
StartScreen.java: Displays the start screen of the game.
WinScreen.java: Displays the winner's screen once the game is won.
Stock.java: Handles the stock pile, allowing players to iterate through remaining cards.
Pile.java: Represents the tableau piles where cards are temporarily placed and moved during gameplay.
Foundation.java: Represents the foundation piles where cards are moved in sequential order according to their suits.
Card.java: Represents an individual card with properties like suit, number, and its state (flipped, selected).
