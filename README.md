Daboia
======

Daboia is a game of snake, where players can program their own AIs to do their dirty work, and put their state-of-the-art AIs to compete with each other.

The game is written in Java 8. The source code is wrapped inside a NetBeans-project.

### Version history

##### Current Version (0.1)

As of now, the game consists of a functional local single- and multiplayer. That being said, the GUI is only *nearly there*, so playing an actual game with it needs some hacking. A multiplayer is on its way as well. The multiplayer is/will be accomplished with client-server technology. Users will mainly connect to each other via a lobby (which is a separate project), but direct connecting will be supported in the future.

The current version will remain as 0.1 until fully functional local gameplay is accomplished, which I see as an appropriate milestone that can be shown off to those who are interested in this project (hi grandma). By `fully functional local gameplay` I mean being able to:

- [x] Select an AI from a list that is populated by external .jar files that reside in ~~some appropriate~~ `logic/` folder. A .jar is recognized as an AI if it extends the abstract class `DaboiaLogic`
- [ ] Configure each game appropriately. This includes:
  - [x] Being able to add up to 6 players into a single game.
  - [x] Being able to configure the speed of the game.
  - [x] Being able to configure the size of the game area.
  - [ ] Setup and launch the game according to these configurations.
