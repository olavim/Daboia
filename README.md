Daboia
======

**The most niche game of them all**

Daboia is a game of snake, where players can program their own AIs to do their dirty work, and put these state-of-the-art, intelligent snakes to compete with each other.

Though writing the best imaginable algorithm to win every game is the primary goal of this game, writing a keyboard-operable "AI" for a more traditional gameplay is entirely possible, and even made convenient for the faint of heart.

If programming is not one of your skills, fear not; a WASD-operated "AI" ~~is~~ will be included with the game together with various tutorials and examples of how writing similar AIs, and AIs for this game in general, can be accomplished. One could argue that this is a fantastic way of hopping in to the world of programming!

The game is written in Java 8. The source code is wrapped inside a NetBeans-project.

![Image of Daboia](/docs/snapshot.png?raw=true)

### Version history

##### Current Version (0.0.9)

As of now, the game consists of a functional local single- and multiplayer. That being said, the GUI is only *nearly there*, so playing an actual game with it needs some hacking. An online multiplayer is on its way as well in the future. The multiplayer is/will be accomplished with client-server technology. Users will mainly connect to each other via a lobby (which is a separate project), but direct connecting will be supported in the future.

The current version will remain as 0.0.9 until fully functional local gameplay is accomplished, which I see as an appropriate milestone that can be shown off to those who are interested in this project (hi grandma). By *fully functional local gameplay* I mean being able to:

- [x] Select an AI from a list that is populated by external .jar files that reside in ~~some appropriate~~ `logic/` folder. A .jar is recognized as an AI if it extends the abstract class `DaboiaLogic`.
- [x] Aquire an adequate amount of visual satisfaction when opening the game.
- [x] Configure each game appropriately. This includes:
  - [x] Being able to add up to 6 players into a single game. This is a practical number as the starting-positions of all players must be hardcoded.
  - [x] Being able to configure the speed of the game.
  - [x] Being able to configure the size of the game area.
  - [x] Setup and launch the game according to these configurations.
- [x] Preload each game.
- [ ] Show the preloader's progress.
- [x] Examine the game back and forth. That is, after the game has been preloaded, it should be possible to play the game as a recording that can be rewinded, fast forwarded and paused at will. **Core functionality exists**: some tinkering and visual enchancements are still appropriate.
